package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.DetailOrders;
import com.lujun61.beans.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersMapper {

    /**
     * @return int 订单明细的数量
     * @description 查询当前用户的不同订单状态下的订单明细总数
     * @author Jun Lu
     * @date 2022-09-13 10:25:33
     */
    int selectDetailOrdersCount(@Param("userId") String userId, @Param("status") String status);

    /**
     * @return java.util.List<com.lujun61.beans.entity.DetailOrders> 一并封装有OrderItem数据的Order对象
     * @description 查询当前用户的订单详情；如果有订单状态，则条件上捎带着订单状态。
     * @author Jun Lu
     * @date 2022-09-13 10:05:04
     */
    List<DetailOrders> selectDetailOrders(@Param("userId") String userId, @Param("status") String status,
                                          @Param("start") Integer start,
                                          @Param("pageSize") Integer pageSize);

    /**
     * @param deadlineTime 超时时刻
     * @return java.util.List<com.lujun61.beans.entity.Orders>
     * @description 查询超过指定时间（小于deadlineTime）并且订单状态为“未支付（1）”的订单
     * @author Jun Lu
     * @date 2022-08-11 20:20:47
     */
    List<Orders> selectTimeOutOrders(Date deadlineTime);

    /**
     * @param orderId 订单id
     * @param status 订单状态
     * @description 修改订单对应的状态
     * @author Jun Lu
     * @date 2022-08-10 22:18:44
     */
    int updateOrderStatusByOrderId(@Param("orderId") String orderId, @Param("status") String status);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insert(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insertSelective(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    Orders selectByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKeySelective(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKey(Orders record);
}