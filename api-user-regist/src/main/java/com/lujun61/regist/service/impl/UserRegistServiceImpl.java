package com.lujun61.regist.service.impl;

import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.utils.MD5Utils;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.fmmall.vo.ResultVo;
import com.lujun61.regist.service.UserRegistService;
import com.lujun61.regist.service.feign.UserCheckClient;
import com.lujun61.regist.service.feign.UserSaveClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("userRegistService")
@Scope("singleton")
public class UserRegistServiceImpl implements UserRegistService {

    @Resource
    UserSaveClient userSaveClient;

    @Resource
    UserCheckClient userCheckClient;

    @Override
    @Transactional
    public ResultVo saveUser(User userInfo) {

        /**
         * 使用【聚合模式】调用服务：在当前消费者服务中心调用所有与业务相关的服务提供者
         * 还有【链式模式】调用服务：与业务相关的所有[服务提供者]  A->B->C->D；调用结果将汇总至A，最后由消费者服务调用A获取服务结果
         */

        // 原本还需要此方法去校验用户信息，但是微服务架构中，此功能被分割了。
        User user = userCheckClient.getUserInfo(userInfo.getUsername());

        System.out.println("user：" + user);

        // 只负责保存用户信息。
        synchronized (this) {

            if (user == null) {

                user = new User();
                user.setUserId(UUIDUtils.getUUID());
                user.setUsername(userInfo.getUsername());
                user.setPassword(MD5Utils.getMD5(userInfo.getPassword()));
                user.setUserImg("images/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());

                Integer i = userSaveClient.saveUserInfo(user);

                if (i > 0) {
                    return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "注册成功！", null);
                } else {
                    return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "系统繁忙，请稍后重试……", null);
                }

            //    如果user对象不为空，但是属性为空，则说明是通过降级响应获取的空User对象（看业务）
            } else if(user.getUsername() == null) {
                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "系统繁忙，请稍后重试……", null);
            } else {

                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "用户名已被注册！", null);

            }
        }


    }
}
