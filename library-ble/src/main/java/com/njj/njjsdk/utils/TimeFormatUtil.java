package com.njj.njjsdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式工具
 *
 * @ClassName TimeFormatUtil
 * @Description TODO
 * @Author Darcy
 * @Date 2021/12/20 13:59
 * @Version 1.0
 */
public class TimeFormatUtil {

    /**
     * 获取时间戳
     *
     * @param time yyyy-MM-dd  HH:MM:SS
     * @return int
     * @date 2021-12-20 13:59:44
     */
    public static int getTimeMill(String time)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = null;
        try {
            parse = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int l = (int) (parse.getTime() / 1000);
        return l;
    }

}
