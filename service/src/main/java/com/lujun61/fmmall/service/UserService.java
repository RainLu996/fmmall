package com.lujun61.fmmall.service;

import com.lujun61.fmmall.vo.ResultVo;

public interface UserService {

    ResultVo userRegist(String name, String password);

    ResultVo checkLogin(String name, String password);

}
