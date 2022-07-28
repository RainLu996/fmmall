package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /**
     * @param user
     * @return int
     * @description  添加用户
     * @author Jun Lu
     * @date 2022-07-19 08:32:54
     */
    int insertUser(User user);


    /**
     * @param name
     * @return com.lujun61.beans.entity.User
     * @description
     * @author Jun Lu
     * @date 2022-07-19 10:33:03
     */
    User selectUserByName(String name);
}
