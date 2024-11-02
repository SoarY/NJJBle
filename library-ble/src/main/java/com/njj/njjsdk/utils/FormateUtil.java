package com.njj.njjsdk.utils;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @ClassName FormateUtil
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/30 17:02
 * @Version 1.0
 */
public class FormateUtil {


    /**
     * 将double数据转化为2位小数的字符串
     *
     * @param data 数据
     * @return
     * @date 2021-4-30 17:02:48
     */
    public static String formatedDoubleNum(double data) {
        //把double类型累加结果--保持2位小数点
        Locale.setDefault(Locale.US);
        DecimalFormat df = new DecimalFormat("#####0.00");
        String str = df.format(data);

        return str;
    }

    public static boolean stringIsMac(String val) {
        String trueMacAddress = "([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}";
        // 这是真正的MAV地址；正则表达式；
        if (val.matches(trueMacAddress)) {
            return true;
        } else {
            return false;
        }
    }
}
