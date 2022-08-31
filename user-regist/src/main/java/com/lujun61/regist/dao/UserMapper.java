package com.lujun61.regist.dao;

import com.lujun61.beans.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insertUser(User user);

}
