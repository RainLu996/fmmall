package com.lujun61.order.service.impl;

import com.lujun61.beans.entity.Orders;
import com.lujun61.order.mapper.OrderQueryMapper;
import com.lujun61.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderQueryMapper orderQueryMapper;

    @Override
    public Orders queryOrderByOrderId(String orderId) {
        return orderQueryMapper.selectOrderByOrderId(orderId);
    }
}
