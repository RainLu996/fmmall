package com.lujun61.fmmall.service;

import com.lujun61.beans.pojo.UserParams;
import com.lujun61.fmmall.vo.ResultVo;

public interface UserService {

    ResultVo userRegist(String name, String password);

    ResultVo checkLogin(String name, String password);

    ResultVo updateUser(UserParams user);

    ResultVo queryUser(String userId);
}
