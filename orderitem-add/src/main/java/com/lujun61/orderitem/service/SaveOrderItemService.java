package com.lujun61.orderitem.service;

import com.lujun61.beans.entity.DetailShoppingCart;

import java.util.List;

public interface SaveOrderItemService {

    Integer saveOrderitem(List<DetailShoppingCart> list, String orderId);

}
