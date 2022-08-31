package com.lujun61.login.service.feign;

import com.lujun61.beans.entity.User;
import com.lujun61.login.service.feign.fallback.UserCheckFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-check", fallback = UserCheckFallback.class)
public interface UserCheckClient {

    @GetMapping("user/check")
    User getUserInfo(@RequestParam("username") String name);

}
