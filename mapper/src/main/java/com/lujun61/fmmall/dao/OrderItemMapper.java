package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper {

    /**
     * @param orderId 订单id
     * @return java.util.List<com.lujun61.beans.entity.OrderItem>
     * @description 根据订单id查询订单快照信息
     * @author Jun Lu
     * @date 2022-08-11 21:04:26
     */
    List<OrderItem> selectOrderItemsByOrderId(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int deleteByPrimaryKey(String itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insert(OrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insertSelective(OrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    OrderItem selectByPrimaryKey(String itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKeySelective(OrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_item
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKey(OrderItem record);
}