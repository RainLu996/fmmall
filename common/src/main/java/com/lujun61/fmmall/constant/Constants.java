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
     * 保存当前用户的key
     */
    public static final String SESSION_USER = "sessionUser";

    /*
        */
/**
     * 备注的修改标记
     */    /*

    public static final String REMARK_EDIT_FLAG_NO_EDITED = "0";//0---没有修改过
    public static final String REMARK_EDIT_FLAG_YES_EDITED = "1";//1--已经被修改过
    */

}
