package com.lujun61.regist.service.feign;

import com.lujun61.beans.entity.User;
import com.lujun61.regist.service.feign.fallback.UserSaveClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-regist", fallback = UserSaveClientImpl.class)
public interface UserSaveClient {

    @PostMapping("user/save")
    Integer saveUserInfo(@RequestBody User user);

}
