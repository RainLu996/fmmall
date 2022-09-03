package com.lujun61.orderadd.dao;

import com.lujun61.beans.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddMapper {

    int insertOrder(Orders order);

}
