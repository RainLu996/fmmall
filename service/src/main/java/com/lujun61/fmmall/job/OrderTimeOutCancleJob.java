package com.lujun61.fmmall.job;

import com.github.wxpay.sdk.WXPay;
import com.lujun61.beans.entity.Orders;
import com.lujun61.fmmall.config.WxPayConfig;
import com.lujun61.fmmall.dao.OrderItemMapper;
import com.lujun61.fmmall.dao.OrdersMapper;
import com.lujun61.fmmall.dao.ProductSkuMapper;
import com.lujun61.fmmall.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeOutCancleJob {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new WxPayConfig());

    @Scheduled(cron = "0/60 * * * * ?")
    public void checkAndCloseOrder() {

        System.out.println("Hello");

        // 1、查询超时待支付订单：查询完后不着急还原库存，需要确定这个订单是否是由网络问题所导致的超时
        List<Orders> orders = ordersMapper.selectTimeOutOrders(new Date(System.currentTimeMillis() - 30 * 60 * 1000));

        // 2、访问微信平台接口，确认当前订单最终的支付状态
        try {
            for (int i = 0; i < orders.size(); i++) {
                Orders order = orders.get(i);
                String orderId = order.getOrderId();

                HashMap<String, String> params = new HashMap<>();
                params.put("out_trade_no", orderId);

                Map<String, String> resp = wxPay.orderQuery(params);

                if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))) {
                    //2.1 如果订单已经支付，则修改订单状态为"代发货/已支付"  status = 2
                    // 说明是回调接口的调用出了问题，需要在这里进行订单状态修改
                    orderService.setOrderStatusByOrderId(orderId, "2");
                } else if ("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))) {
                    //2.2 如果确实未支付 则取消订单：
                    //  a.向微信支付平台发送请求，关闭当前订单的支付链接。避免用户恰好在关闭订单之前扫码付款
                    Map<String, String> map = wxPay.closeOrder(params);

                    // b.修改订单、还原库存
                    orderService.closeOrder(orderId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
