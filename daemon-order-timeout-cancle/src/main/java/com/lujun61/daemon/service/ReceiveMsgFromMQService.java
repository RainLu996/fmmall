package com.lujun61.daemon.service;

import com.github.wxpay.sdk.WXPay;
import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.config.WxPayConfig;
import com.lujun61.daemon.service.feign.OrderCloseClient;
import com.lujun61.daemon.service.feign.OrderQueryClient;
import com.lujun61.daemon.service.feign.OrderStatusUpdateClient;
import com.lujun61.daemon.service.feign.OrderTimeoutQueryClient;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "delay_queue2")
public class ReceiveMsgFromMQService {

    @Resource
    private OrderTimeoutQueryClient orderTimeoutQueryClient;

    @Resource
    private OrderStatusUpdateClient orderStatusUpdateClient;

    @Resource
    private OrderCloseClient orderCloseClient;

    @Resource
    private OrderQueryClient orderQueryClient;

    private WXPay wxPay = new WXPay(new WxPayConfig());

    @RabbitHandler
    public void checkAndCloseOrder(String orderId, Message message, Channel channel) throws IOException {

        try {

            // 1、查询超时待支付订单：查询完后不着急还原库存，需要确定这个订单是否是由网络问题所导致的超时
            Orders order = orderQueryClient.queryOrder(orderId);
            if ("1".equals(order.getStatus())) {

                // 2、访问微信平台接口，确认当前订单最终的支付状态
                HashMap<String, String> params = new HashMap<>();
                params.put("out_trade_no", orderId);
                Map<String, String> resp = wxPay.orderQuery(params);

                if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))) {
                    //2.1 如果订单已经支付，则修改订单状态为"代发货/已支付"  status = 2
                    // 说明是回调接口的调用出了问题，需要在这里进行订单状态修改
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(orderId);
                    updateOrder.setStatus("2");

                    orderStatusUpdateClient.orderStatusUpdate(updateOrder);
                } else if ("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))) {
                    //2.2 如果确实未支付 则取消订单：
                    //  a.向微信支付平台发送请求，关闭当前订单的支付链接。避免用户恰好在关闭订单之前扫码付款
                    Map<String, String> map = wxPay.closeOrder(params);

                    // b.修改订单、还原库存
                    // 在定时任务中，只是为了查询超时订单，所以，订单关闭类型为“超时未支付->1”
                    orderCloseClient.closeOrder(orderId, 1);
                }
            }
            // 参数二：是否批量确认接收消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();

            // 参数二：是否批量接收消息；参数三：是否重新将失败处理的消息重存进队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
