package com.lujun61.orderclose.service.impl;

import com.lujun61.beans.entity.OrderItem;
import com.lujun61.beans.entity.Orders;
import com.lujun61.beans.entity.ProductSku;
import com.lujun61.orderclose.service.OrderCloseService;
import com.lujun61.orderclose.service.feign.OrderItemQueryClient;
import com.lujun61.orderclose.service.feign.OrderStatusUpdateClient;
import com.lujun61.orderclose.service.feign.ProductSkuQueryClient;
import com.lujun61.orderclose.service.feign.StockUpdateClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("orderCloseService")
public class OrderCloseServiceImpl implements OrderCloseService {

    @Resource
    OrderItemQueryClient orderItemQueryClient;

    @Resource
    OrderStatusUpdateClient orderStatusUpdateClient;

    @Resource
    StockUpdateClient stockUpdateClient;

    @Resource
    ProductSkuQueryClient productSkuQueryClient;

    @Override
    public int closeOrder(String orderId, Integer closeType) {

        // 1、调用order-status-update服务，修改订单状态为6
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setStatus("6");
        order.setCloseType(closeType);   // 有许多种订单关闭类型
        int isOrderStatus = orderStatusUpdateClient.orderStatusUpdate(order);

        if (isOrderStatus > 0) {

            // 2、调用orderitem-query服务，查询当前订单包含的商品信息。以获取已经消减的库存
            List<OrderItem> orderItems = orderItemQueryClient.queryOrderItems(orderId);

            // 3、还原库存：调用stock-update服务
            if (orderItems != null && orderItems.size() > 0) {
                List<ProductSku> productSkuList = new LinkedList<>();
                for (OrderItem orderItem : orderItems) {
                    String skuId = orderItem.getSkuId();
                    // 调用product-sku-query服务，查询当前商品库存
                    ProductSku productSku = productSkuQueryClient.queryProductSku(skuId);
                    int newStock = productSku.getStock() + orderItem.getBuyCounts();
                    productSku.setStock(newStock);
                    productSkuList.add(productSku);
                }
                return stockUpdateClient.updateStock(productSkuList);
            }
        }

        return 0;
    }
}
