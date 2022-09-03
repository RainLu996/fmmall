package com.lujun61.orderitem.dao;

import com.lujun61.beans.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveOrderItemMapper {

    Integer insert(OrderItem orderItem);

}
