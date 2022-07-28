package com.lujun61.fmmall.utils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * @return java.lang.String UUID
     * @description 获取UUID的值
     * @author Jun Lu
     * @date 2022-07-19 15:06:48
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
