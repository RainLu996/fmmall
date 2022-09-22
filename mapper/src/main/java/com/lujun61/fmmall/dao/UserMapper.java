package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.User;
import com.lujun61.beans.pojo.UserParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    /**
     * @return com.lujun61.beans.entity.User
     * @description 查询用户基本信息
     * @author Jun Lu
     * @date 2022-09-22 09:13:26
     */
    User selectUser(@Param("userId") String userId);

    /**
     * @return int
     * @description 更新用户信息
     * @author Jun Lu
     * @date 2022-09-22 09:13:08
     */
    int updateUser(UserParams user);

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
