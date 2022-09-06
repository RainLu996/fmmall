package com.lujun61.order.service;

import com.lujun61.beans.entity.Orders;

public interface OrderService {

    Orders queryOrderByOrderId(String orderId);

}
