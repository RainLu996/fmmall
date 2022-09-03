package com.lujun61.orderitemquery.dao;

import com.lujun61.beans.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemQueryMapper {

    List<OrderItem> selectOrderItemsByOrderId(String orderId);

}
