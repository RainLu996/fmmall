package com.lujun61.login.service.feign.fallback;

import com.lujun61.beans.entity.User;
import com.lujun61.login.service.feign.UserCheckClient;
import org.springframework.stereotype.Component;

@Component
public class UserCheckFallback implements UserCheckClient {
    @Override
    public User getUserInfo(String name) {
        //如果调用user-check服务失败，则返回null
        System.out.println("user/check-----服务降级");
        return new User();
    }
}
