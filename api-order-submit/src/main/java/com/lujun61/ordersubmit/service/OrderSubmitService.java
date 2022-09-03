package com.lujun61.ordersubmit.service;

import com.lujun61.beans.entity.Orders;

import java.util.Map;

public interface OrderSubmitService {

    Map<String, String> orderSubmit(Orders orders, String cids);

}
