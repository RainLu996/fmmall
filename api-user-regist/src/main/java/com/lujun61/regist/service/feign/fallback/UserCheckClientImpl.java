package com.lujun61.regist.service.feign.fallback;

import com.lujun61.beans.entity.User;
import com.lujun61.regist.service.feign.UserCheckClient;
import org.springframework.stereotype.Component;

@Component
public class UserCheckClientImpl implements UserCheckClient {

    /**
     * @description 降级响应具体实现取决于具体业务
     * @author Jun Lu
     * @date 2022-08-31 12:15:58
     */
    @Override
    public User getUserInfo(String name) {
        return new User();
    }
}
