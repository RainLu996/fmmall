package com.lujun61.daemon.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class WxPayConfig implements WXPayConfig {

    @Override
    public String getMchID() {
        return "1497984412";     //商户编号
    }

    @Override
    public String getAppID() {
        return "wx632c8f211f8122c6";  //商户账号AppID
    }

    @Override
    public String getKey() {
        return "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";     //商户Key
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
