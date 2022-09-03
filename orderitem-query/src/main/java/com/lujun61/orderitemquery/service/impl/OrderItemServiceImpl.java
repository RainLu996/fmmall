package com.lujun61.orderitemquery.service.impl;

import com.lujun61.beans.entity.OrderItem;
import com.lujun61.orderitemquery.dao.OrderItemQueryMapper;
import com.lujun61.orderitemquery.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemQueryMapper orderItemQueryMapper;

    @Override
    public List<OrderItem> queryOrderItems(String orderId) {
        return orderItemQueryMapper.selectOrderItemsByOrderId(orderId);
    }
}
