package com.lujun61.fmmall.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.OrderItem;
import com.lujun61.beans.entity.Orders;
import com.lujun61.beans.entity.ProductSku;
import com.lujun61.fmmall.config.WxPayConfig;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.OrderItemMapper;
import com.lujun61.fmmall.dao.OrdersMapper;
import com.lujun61.fmmall.dao.ProductSkuMapper;
import com.lujun61.fmmall.dao.ShoppingCartMapper;
import com.lujun61.fmmall.service.OrderService;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.fmmall.vo.ResultVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(String orderId) {
        synchronized (this) {   // 锁的是Spring容器中的同一个service对象
            // b.修改订单状态
            Orders cancleOrder = ordersMapper.selectByPrimaryKey(orderId);
            cancleOrder.setStatus("6");    // 订单已关闭
            cancleOrder.setCloseType(1);   // 订单超时已取消
            ordersMapper.updateByPrimaryKeySelective(cancleOrder);

            // c.还原库存（利用商品快照中的信息）
            List<OrderItem> orderItems = orderItemMapper.selectOrderItemsByOrderId(orderId);
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);

                ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
                /**
                 * 由于高并发的存在，可能有多个订单是对同一个商品进行操作。
                 * 此时，如果同时获取当前库存（不准确），并加上商品快照中的购买商品个数，必将会导致数据丢失。
                 * 所以，才需要将隔离级别调整为Serializable
                 */
                productSku.setStock(orderItem.getBuyCounts() + productSku.getStock());
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }
        }
    }

    @Override
    @Transactional
    public ResultVo addOrder(String cids, Orders order) {

        // 未申请支付链接map，只有用户支付界面回馈给用户的一些商品基本信息
        HashMap<String, String> res = new HashMap<>();
        // 已申请支付链接map，在未申请支付链接map的基础上，调用申请支付链接的接口，获取并封装了支付链接
        HashMap<String, String> finallyOrderInfo = new HashMap<>();

        // 根据cids查询用户选中的购物车商品详细信息
        String[] cidArry = cids.split(",");
        List<DetailShoppingCart> sc = shoppingCartMapper.selectShopcartByCartIds(Arrays.asList(cidArry));

        // 0、加分布式锁
        // 保存当前线程中加上的锁
        HashMap<String, RLock> locks = new HashMap<>();
        // 获取当前线程中已经被加上锁的商品。便于释放锁（无论失败与否）
        String[] successLockSkuids = new String[sc.size()];
        boolean isLock = true;
        for (int j = 0; j < sc.size(); j++) {

            String skuId = sc.get(j).getSkuId();

            // 获取非公平锁
            RLock lock = redissonClient.getLock(skuId);
            boolean b = false;
            try {
/*
    如果锁当前被这个或分布式系统中的任何其他进程中的另一个线程持有，则此方法会在放弃并返回false之前一直尝试获取锁直到waitTime。
    如果获得了锁，它会一直保持到unlock被调用，或者直到锁被授予后已经过去了leaseTime时间——以先到者为准
 */
                // 获取非阻塞锁
                b = lock.tryLock(10, 15, TimeUnit.SECONDS);
                // 存放加锁成功的商品信息
                if (b) {
                    successLockSkuids[j] = skuId;
                    locks.put(skuId, lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 如果有一个加锁失败，就视为全盘皆输
            isLock = isLock & b;
        }

        // 只有加锁成功才继续进行业务操作
        try {  // 一整个加try是为了利用finally块去释放锁
            if (isLock) {
                boolean isCheck = false;
                StringBuilder untitle = new StringBuilder();  // 订单表中的untitle字段信息
                // 1、检验库存
                // 需要重新进行数据库查询，以免在加锁之前，商品信息被其它线程所更改
                sc = shoppingCartMapper.selectShopcartByCartIds(Arrays.asList(cidArry));
                for (int i = 0; i < sc.size(); i++) {
                    // 任意一个购物车商品的库存不足，即视为无法继续提交订单
                    if (Integer.parseInt(sc.get(i).getStock()) < Integer.parseInt(sc.get(i).getCartNum())) {
                        isCheck = true;
                    }

                    if (i == sc.size() - 1) {
                        untitle.append(sc.get(i).getProductName());
                    } else {
                        untitle.append(sc.get(i).getProductName()).append(",");
                    }

                }

                // 只有库存足够，才继续进行业务处理
                if (!isCheck) {
                    // 2、进一步封装订单信息并保存订单
                    order.setOrderId(UUIDUtils.getUUID());
                    order.setUntitled(untitle.toString());
                    order.setCreateTime(new Date());
                    order.setStatus("1");  // 初始订单状态为“未付款”

                    ordersMapper.insert(order);

                    // 3、生成商品快照
                    for (DetailShoppingCart item : sc) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        // 根据每个购物车信息来生成粒度为商品的商品快照信息
                        OrderItem orderItem = null;
                        try {
                            orderItem = new OrderItem(
                                    //(System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000)).substring(startIndex, endIndex),
                                    UUIDUtils.getUUID(),
                                    order.getOrderId(),
                                    item.getProductId(),
                                    item.getProductName(),
                                    item.getProductImg(),
                                    item.getSkuId(),
                                    item.getSkuName(),
                                    item.getProductPrice(),
                                    Integer.parseInt(item.getCartNum()),
                                    item.getProductPrice().multiply(new BigDecimal(item.getCartNum())),
                                    format.parse(item.getCartTime()),
                                    new Date(),
                                    0
                            );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        orderItemMapper.insert(orderItem);

                    }

                    // 4、消减库存：根据商品套餐id来修改库存
                    for (DetailShoppingCart item : sc) {
                        String skuId = item.getSkuId();
                        Integer newStock = Integer.parseInt(item.getStock()) - Integer.parseInt(item.getCartNum());

                        ProductSku productSku = new ProductSku();
                        productSku.setSkuId(skuId);
                        productSku.setStock(newStock);
                        productSkuMapper.updateByPrimaryKeySelective(productSku);
                    }

                    // 5、订单创建成功后，对购物车中对应的记录进行删除操作
                    for (String cid : cidArry) {
                        shoppingCartMapper.deleteByPrimaryKey(cid);
                    }

                    res.put("orderId", order.getOrderId());
                    res.put("productNames", untitle.toString());

                    // 获取支付链接
                    finallyOrderInfo = applyForWxPayLink(res, order);

                } else {
                    // 库存不足
                    // 如果没有finally中的释放锁，这里也是需要释放锁的
                    return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "提交订单失败！", null);
                }
            }
        } finally {
            // 释放锁
            for (String skuId : successLockSkuids) {
                if (skuId != null && !"".equals(skuId)) locks.get(skuId).unlock();
            }
        }

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "提交订单成功！", finallyOrderInfo);
    }

    @Override
    public HashMap<String, String> applyForWxPayLink(HashMap<String, String> orderInfo, Orders order) {
        //设置当前订单信息
        HashMap<String, String> data = new HashMap<>();

        data.put("body", orderInfo.get("productNames")); //商品描述
        data.put("out_trade_no", orderInfo.get("orderId")); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
        data.put("fee_type", "CNY"); //⽀付币种
        //data.put("total_fee", order.getActualAmount() * 100 + "");   // 支付金额：以分为单位
        data.put("total_fee", 1 + "");   // 支付金额：以分为单位
        data.put("trade_type", "NATIVE"); //交易类型
        data.put("notify_url", "http://rainlu.free.idcfengye.com/pay/callback"); //设置⽀付完成时的回调⽅法接⼝

        //发送请求，获取响应
        //微信⽀付：申请⽀付连接
        WXPay wxPay = new WXPay(new WxPayConfig());
        Map<String, String> resp = null;    // 向微信支付平台发送支付订单请求，并携带参数data
        try {
            resp = wxPay.unifiedOrder(data);
            orderInfo.put("payUrl", resp.get("code_url"));         // 微信支付平台会将支付链接返回
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderInfo;
    }

    @Override
    public int setOrderStatusByOrderId(String orderId, String status) {
        return ordersMapper.updateOrderStatusByOrderId(orderId, status);
    }

    @Override
    public ResultVo queryOrderStatusByOrderId(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);

        if (orders != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", orders.getStatus());
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);
        }

    }
}
