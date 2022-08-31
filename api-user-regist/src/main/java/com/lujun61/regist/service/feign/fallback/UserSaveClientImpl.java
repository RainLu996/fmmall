package com.lujun61.regist.service.feign.fallback;

import com.lujun61.beans.entity.User;
import com.lujun61.regist.service.feign.UserSaveClient;
import org.springframework.stereotype.Component;

@Component
public class UserSaveClientImpl implements UserSaveClient {
    @Override
    public Integer saveUserInfo(User user) {
        return 0;     // 返回0就代表注册失败
    }
}
