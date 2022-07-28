package com.lujun61.fmmall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对Date类型数据进行处理的工具类
 */
public class DateUtils {
    /**
     * @param date
     * @return java.lang.String
     * @description 对指定的date对象进行格式化: yyyy-MM-dd HH:mm:ss
     * @author Jun Lu
     * @date 2022-07-19 15:09:07
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=sdf.format(date);
        return dateStr;
    }

    /**
     * @param date
     * @return java.lang.String
     * @description 对指定的date对象进行格式化: yyyy-MM-dd
     * @author Jun Lu
     * @date 2022-07-19 15:08:55
     */
    public static String formateDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(date);
        return dateStr;
    }

    /**
     * @param date
     * @return java.lang.String
     * @description 对指定的date对象进行格式化: HH:mm:ss
     * @author Jun Lu
     * @date 2022-07-19 15:08:43
     */
    public static String formateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String dateStr=sdf.format(date);
        return dateStr;
    }
}
