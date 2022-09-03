package com.lujun61.orderitem.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.OrderItem;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.orderitem.dao.SaveOrderItemMapper;
import com.lujun61.orderitem.service.SaveOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("saveOrderitemService")
public class SaveOrderItemServiceImpl implements SaveOrderItemService {

    @Resource
    SaveOrderItemMapper saveOrderitemMapper;

    @Override
    public Integer saveOrderitem(List<DetailShoppingCart> list, String orderId) {

        int ret = 1;

        for (DetailShoppingCart item : list) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 根据每个购物车信息来生成粒度为商品的商品快照信息
            OrderItem orderItem = null;
            try {
                orderItem = new OrderItem(
                        //(System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000)).substring(startIndex, endIndex),
                        UUIDUtils.getUUID(),
                        orderId,
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
            int res = saveOrderitemMapper.insert(orderItem);

            // 只要有一个订单快照保存失败，就会返回0
            ret *= res;
        }

        return ret;
    }
}
