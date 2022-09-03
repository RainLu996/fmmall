package com.lujun61.updateorderstatus.service;

import com.lujun61.beans.entity.Orders;

public interface OrderStatusUpdateService {

    int resetByOrderIdSelective(Orders order);

}
