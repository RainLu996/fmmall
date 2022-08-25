package com.lujun61.fmmall.service;

import com.lujun61.beans.entity.Orders;
import com.lujun61.fmmall.vo.ResultVo;

import java.util.HashMap;

public interface OrderService {

    /**
     * @param orderId 订单id
     * @description 将定时任务中关闭订单的操作剥离出来，方便添加事务以及加JVM锁
     * @author Jun Lu
     * @date 2022-08-11 22:13:23
     */
    void closeOrder(String orderId);

    /**
     * @param cids 前端提交的用户所选中提交的购物车商品信息id
     * @param order 前端提交的一部分订单信息
     * @return java.util.HashMap<java.lang.String, java.lang.String>
     * @description 根据用户提交的订单，生成、增加一笔订单信息。为了处理在进行数据库操作时可能出现的异常，可以进行异常上抛
     * @author Jun Lu
     * @date 2022-08-10 15:33:06
     */
    ResultVo addOrder(String cids, Orders order);


    /**
     * @param orderInfo 订单基本信息map集合
     * @param order 订单对象
     * @return java.util.HashMap<java.lang.String, java.lang.String>
     * @description 向微信支付平台申请支付链接
     * @author Jun Lu
     * @date 2022-08-10 22:22:59
     */
    HashMap<String, String> applyForWxPayLink(HashMap<String, String> orderInfo, Orders order) throws Exception;


    int setOrderStatusByOrderId(String orderId, String status);


    ResultVo queryOrderStatusByOrderId(String orderId);

}
