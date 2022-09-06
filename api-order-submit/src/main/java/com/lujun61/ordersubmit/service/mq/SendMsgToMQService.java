package com.lujun61.ordersubmit.service.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SendMsgToMQService {

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMsg(String orderId) {
        amqpTemplate.convertAndSend("delay_exchange", "key1", orderId);
    }

}
