package com.lujun61.shopcartdel.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ShopcartDelMapper {

    int deleteShopcartByCid(String cid);

}
