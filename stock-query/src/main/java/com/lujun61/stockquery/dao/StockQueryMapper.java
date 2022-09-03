package com.lujun61.stockquery.dao;

import com.lujun61.beans.entity.DetailShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockQueryMapper {

    List<DetailShoppingCart> selectShopcartByCartIds(List<String> cartIds);

}
