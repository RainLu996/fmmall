package com.lujun61.fmmall.constant;


/**
 * @author Jun Lu
 * @description 常量类：为了发挥封装思想，一些有特殊意义的字面量不要直接在项目中使用！
 *              因为一旦这些字面量的意义发生了改变，就会导致维护困难。
 *              所以，为了避免这种情况，就得将这些具有特殊意义的字面量封装为常量类。
 * @date 2022-07-19 14:56:03
 */
public class Constants {

    /**
     * 保存ReturnObject类中的Code值
     */
    public static final String RETURN_OBJECT_CODE_SUCCESS = "1";//成功
    public static final String RETURN_OBJECT_CODE_FAIL = "0";//失败


    /**
     * 让用户登录后端说明信息更加具体
     */
    public static final String LOGIN_SUCCESS = "111";
    public static final String LOGIN_FAIL_USER_SIGN_IN_TIMEOUT = "100";
    public static final String LOGIN_FAIL_USER_NOT_SIGN_IN = "101";
    public static final String LOGIN_FAIL_ELSE = "102";
    public static final String LOGIN_FAIL_TOKEN_ILLEGAL = "103";

    /**
     * 购物车状态详情
     */
    public static final String SHOPPINGCART_EMPTY = "9404";

    /**
     * 用户信息识别码
     */
    // 前端使用请注意：以0开头的字符串被转换为数字就是404了，不可以直接在前端以==0404来判断。应该是=="0404"或者==404
    public static final String USER_ADDR_EMPTY = "0404";
}
