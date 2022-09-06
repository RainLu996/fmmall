package com.lujun61.order.mapper;

import com.lujun61.beans.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQueryMapper {

    Orders selectOrderByOrderId(String orderId);

}
