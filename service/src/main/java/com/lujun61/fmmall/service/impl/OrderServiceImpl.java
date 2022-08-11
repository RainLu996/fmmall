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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ResultVo addOrder(String cids, Orders order) throws Exception {

        HashMap<String, String> res = new HashMap<>();

        // 1、检验库存：根据cids查询用户选中的购物车商品详细信息
        String[] cidArry = cids.split(",");
        List<DetailShoppingCart> sc = shoppingCartMapper.selectShopcartByCartIds(Arrays.asList(cidArry));

        boolean isCheck = false;
        StringBuilder untitle = new StringBuilder();  // 订单表中的untitle字段信息
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
                OrderItem orderItem = new OrderItem(
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

            HashMap<String, String> finallyOrderInfo = applyForWxPayLink(res, order);

            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "提交订单成功！", finallyOrderInfo);
        } else {
            // 库存不足
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "提交订单失败！", null);
        }
    }

    @Override
    public HashMap<String, String> applyForWxPayLink(HashMap<String, String> orderInfo, Orders order) throws Exception {
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
        Map<String, String> resp = wxPay.unifiedOrder(data);    // 向微信支付平台发送支付订单请求，并携带参数data
        orderInfo.put("payUrl", resp.get("code_url"));         // 微信支付平台会将支付链接返回

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
