package com.lujun61.regist.service;

import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.vo.ResultVo;

public interface UserRegistService {

    ResultVo saveUser(User user);

}
