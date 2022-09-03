package com.lujun61.stockquery.service;

import com.lujun61.beans.entity.DetailShoppingCart;

import java.util.List;

public interface StockQueryService {

    List<DetailShoppingCart> queryShopcartByCartIds(String cIdstr);

}
