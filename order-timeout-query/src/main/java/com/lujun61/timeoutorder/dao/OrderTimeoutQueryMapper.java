package com.lujun61.timeoutorder.dao;

import com.lujun61.beans.entity.Orders;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderTimeoutQueryMapper {

    List<Orders> selectTimeOutOrders(Date deadlineTime);

}
