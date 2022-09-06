package com.lujun61.ordersubmit.controller;

import com.github.wxpay.sdk.WXPay;
import com.lujun61.beans.entity.Orders;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.vo.ResultVo;
import com.lujun61.ordersubmit.config.WxPayConfig;
import com.lujun61.ordersubmit.service.OrderSubmitService;
import com.lujun61.ordersubmit.service.mq.SendMsgToMQService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderSubmitController {

    @Resource
    private OrderSubmitService orderSubmitService;

    @Resource
    private SendMsgToMQService sendMsgToMQService;

    @PostMapping("/add")
    public ResultVo orderSubmit(@RequestParam("cids") String cids, @RequestBody Orders order) {
        //设置当前订单信息，作为请求WxPay的参数
        HashMap<String, String> data = new HashMap<>();

        // 响应对象
        ResultVo resultVo = null;

        try {
            // 提交订单
            Map<String, String> orderInfo = orderSubmitService.orderSubmit(order, cids);

            // 只有订单提交成功之后，才能向微信支付平台发起支付请求
            if (orderInfo != null && orderInfo.size() > 0) {
                data.put("body", orderInfo.get("productNames")); //商品描述
                data.put("out_trade_no", orderInfo.get("orderId")); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
                data.put("fee_type", "CNY"); //⽀付币种
                //data.put("total_fee", order.getActualAmount() * 100 + "");   // 支付金额：以分为单位
                data.put("total_fee", 1 + "");   // 支付金额：以分为单位
                data.put("trade_type", "NATIVE"); //交易类型
                data.put("notify_url", "http://rainlu.free.idcfengye.com/pay/callback"); //设置⽀付完成时的回调⽅法接⼝

                //发送请求，获取响应
                //微信⽀付：申请⽀付连接
                WXPay wxPay = new WXPay(new WxPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);    // 向微信支付平台发送支付订单请求，并携带参数data
                orderInfo.put("payUrl", resp.get("code_url"));         // 微信支付平台会将支付链接返回


                /* 当订单保存成功之后，将订单id保存至死信队列 */
                sendMsgToMQService.sendMsg(orderInfo.get("orderId"));

                return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "提交订单成功！", orderInfo);
            } else {
                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "提交订单失败！", null);
            }
        } catch (Exception e) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "提交订单失败！", null);
        }

    }

}
