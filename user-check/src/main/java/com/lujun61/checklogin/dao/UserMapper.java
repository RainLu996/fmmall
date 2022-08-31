package com.lujun61.checklogin.dao;

import com.lujun61.beans.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User selectUserByUsername(String name);

}
