package com.lujun61.fmmall.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.lujun61.fmmall.service.OrderService;
import com.lujun61.fmmall.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jun Lu
 * @description ⽀付回调：当⽤户⽀付成功之后，⽀付平台会向我们指定的服务器接⼝发送请求传递订单⽀付状态数据
 * @date 2022-08-10 20:24:21
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    OrderService orderService;

    /**
     * 回调接口：当用户支付成功之后，微信支付平台就会请求这个接口，将当前订单支付状态数据以XML格式传递过来
     */
    @RequestMapping("/callback")
    public String paySuccess(HttpServletRequest request) throws Exception {

        // 1.接收微信⽀付平台传递的数据（使⽤request的输⼊流接收）
        ServletInputStream is = request.getInputStream();
        byte[] words = new byte[1024];

        StringBuilder builder = new StringBuilder();
        int len = -1;
        while ((len = is.read(words)) != -1) {
            builder.append(new String(words, 0, len));
        }

        // 使⽤帮助类将xml接⼝的字符串装换成map
        Map<String, String> map = WXPayUtil.xmlToMap(builder.toString());

        //⽀付成功
        if (map.size() > 0 && "success".equalsIgnoreCase(map.get("result_code"))) {

            //2.修改订单状态为“待发货/已⽀付”
            String orderId = map.get("out_trade_no");

            int i = orderService.setOrderStatusByOrderId(orderId, "2");

            //3.通过WebSocket建立的长连接，向前端推送消息
            WebSocketServer.sendMsg(orderId, "success");

            //4.响应微信⽀付平台
            if (i > 0) {
                HashMap<String, String> resp = new HashMap<>();
                resp.put("return_code", "success");
                resp.put("return_msg", "OK");
                resp.put("appid", map.get("appid"));
                resp.put("result_code", "success");
                return WXPayUtil.mapToXml(resp);
            }
        }
        return null;
    }

}
