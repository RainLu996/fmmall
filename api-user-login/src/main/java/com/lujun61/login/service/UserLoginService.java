package com.lujun61.login.service;

import com.lujun61.fmmall.vo.ResultVo;

public interface UserLoginService {

    ResultVo checkLogin(String name, String password);

}
