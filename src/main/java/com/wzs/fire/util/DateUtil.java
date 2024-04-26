package com.wzs.fire.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static java.sql.Date getSqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }
    public static String getNowDate (){
        Date now = new Date();
        // 定义日期时间格式，符合MySQL TIMESTAMP类型
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将Date转换为String
        String formattedDateTime = formatter.format(now);
        return formattedDateTime;
    }
}
