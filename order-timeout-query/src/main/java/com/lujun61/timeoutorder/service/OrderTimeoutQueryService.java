package com.lujun61.timeoutorder.service;

import com.lujun61.beans.entity.Orders;

import java.util.List;

public interface OrderTimeoutQueryService {

    List<Orders> queryTimeoutOrder();

}
