package com.njj.njjsdk.utils;

import com.njj.njjsdk.manger.NjjBleManger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/24 15:41
 * @Version 1.0
 */
public class DateUtil {

    public static int[] getHHmm(int sec) {
        return new int[]{sec / 3600, sec % 3600 / 60};
    }
    /**
     * 将long类似的时间转为String类型
     *
     * @param time
     * @return 返回格式为yyyy-MM-dd
     */
    public static String date2String(Date time) {

        Locale.setDefault(Locale.US);
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }
    public static String long2String(long time, String format) {
        Locale.setDefault(Locale.US);
        SimpleDateFormat mFormat = new SimpleDateFormat(format);
        return mFormat.format(new Date(time));
    }
    /**
     * 将String类型的日期转为long类型
     *
     * @param time 要转化的日期格式为yyyy-MM-dd
     * @return
     */
    public static long String2long(String time) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Locale.setDefault(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long longTime = System.currentTimeMillis();
        try {
            Date date = simpleDateFormat.parse(time);
            longTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    /**
     * 将String类型的日期转为long类型
     *
     * @param time 要转化的日期格式为yyyy-MM-dd
     * @return
     */
    public static long String2YYLong(String time) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Locale.setDefault(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long longTime = System.currentTimeMillis();
        try {
            Date date = simpleDateFormat.parse(time);
            longTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }



    public static long String2long(String time,String format) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Locale.setDefault(Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long longTime = System.currentTimeMillis();
        try {
            Date date = simpleDateFormat.parse(time);
            longTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime/1000;
    }

    public static int getCurDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }


    public static int getOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int rawOffset = tz.getOffset(cal.getTimeInMillis())/1000;
        return rawOffset;
    }
}
