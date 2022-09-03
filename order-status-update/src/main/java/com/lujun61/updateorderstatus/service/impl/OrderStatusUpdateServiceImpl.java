package com.lujun61.updateorderstatus.service.impl;

import com.lujun61.beans.entity.Orders;
import com.lujun61.updateorderstatus.dao.OrderMapper;
import com.lujun61.updateorderstatus.service.OrderStatusUpdateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("orderStatusUpdateService")
public class OrderStatusUpdateServiceImpl implements OrderStatusUpdateService {

    @Resource
    OrderMapper orderMapper;

    @Override
    public int resetByOrderIdSelective(Orders order) {
        return orderMapper.updateByOrderIdSelective(order);
    }
}
