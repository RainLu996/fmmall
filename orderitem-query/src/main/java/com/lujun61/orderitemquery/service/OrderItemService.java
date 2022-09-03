package com.lujun61.orderitemquery.service;

import com.lujun61.beans.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> queryOrderItems(String orderId);

}
