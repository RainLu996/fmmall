package com.lujun61.updateorderstatus.dao;

import com.lujun61.beans.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    int updateByOrderIdSelective(Orders order);

}
