package com.lujun61.orderadd.service;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;

import java.util.List;

public interface OrderAddService {

    List<DetailShoppingCart> insertOrder(Orders order, String cids);

}
