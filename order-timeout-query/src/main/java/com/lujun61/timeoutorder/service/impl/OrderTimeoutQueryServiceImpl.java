package com.lujun61.timeoutorder.service.impl;

import com.lujun61.beans.entity.Orders;
import com.lujun61.timeoutorder.dao.OrderTimeoutQueryMapper;
import com.lujun61.timeoutorder.service.OrderTimeoutQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("orderTimeoutQueryService")
public class OrderTimeoutQueryServiceImpl implements OrderTimeoutQueryService {

    @Resource
    OrderTimeoutQueryMapper orderTimeoutQueryMapper;

    @Override
    public List<Orders> queryTimeoutOrder() {
        return orderTimeoutQueryMapper.selectTimeOutOrders(new Date(System.currentTimeMillis() - 30 * 60 * 1000));
    }
}
