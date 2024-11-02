package com.njj.njjsdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ClassName Utils
 * @Description TODO
 * @Author Darcy
 * @Date 2021/4/23 15:46
 * @Version 1.0
 */
public class NjjUtils {
    /**
     * 将String形式的二进制，转为int
     *
     * @param binaryString
     * @return
     */
    public static int getCycleInt(String binaryString) {
        BigInteger bi = new BigInteger(binaryString, 2); //转换为BigInteger类型
        return Integer.parseInt(bi.toString());
    }
    /**
     * 将String形式的二进制通过char[]返回
     *
     * @param binary String形式的二进制，比如：1111001
     * @return 返回的顺序是char[] = {周六，周五，周四，周三，周二，周一，周日}
     */
    public static char[] getCycleChar(String binary) {
        char[] cycle = new char[7];
        if (TextUtils.isEmpty(binary) || binary.length() > cycle.length) {
            for (int i = 0; i < cycle.length; i++) {
                cycle[i] = '0';
            }
        } else {
            int index = cycle.length - binary.length();
            if (index > 0) {
                for (int i = 0; i < index; i++) {
                    cycle[i] = '0';
                }
            }
            for (int i = index, j = 0; j < binary.length(); i++, j++) {
                cycle[i] = binary.charAt(j);
            }
        }
        return cycle;
    }

    /**
     * 获得一串数据的平均值，最大值，最小值
     *
     * @param string
     * @return 返回的结果顺序是平均值，最大值，最小值
     */
    public static int[] getAverageAndMaxValue(String string) {
        int[] result = new int[3];
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        if (TextUtils.isEmpty(string)) {
            result = new int[]{0, 0, 0};
        } else {
            String[] values = string.split(",");
            int count = 0;
            for (int i = 0; i < values.length; i++) {
                if (max < Integer.valueOf(values[i])) {
                    max = Integer.valueOf(values[i]);
                }

                if (min > Integer.valueOf(values[i])) {
                    min = Integer.valueOf(values[i]);
                }

                count += Integer.valueOf(values[i]);

            }
            result = new int[]{count / values.length, max, min};
        }

        return result;
    }

    /**
     * 将一个String里的Float数据，计算出平均值，最大值，和最小值，并保存指定的精度
     *
     * @param s
     * @param digit 保存小数的精度
     * @return new float[]{平均值，最大值，最小值}
     */
    public static float[] getAverageMaxMinValuesFloat(String s, int digit) {
        float[] values = new float[]{0.0f, 0.0f, 0.0f};
        if (TextUtils.isEmpty(s)) {
            return values;
        }
        String[] valueStrArr = s.split(",");
        int length = valueStrArr.length;
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        float sum = 0.0f;
        for (int i = 0; i < length; i++) {
            float value = NjjUtils.getFloatScale(digit, Float.valueOf(valueStrArr[i]));
            if (max < value) {
                max = value;
            }
            if (min > value) {
                min = value;
            }

            sum += value;
        }
        values[0] = NjjUtils.getFloatScale(digit, sum / length);
        values[1] = NjjUtils.getFloatScale(digit, max);
        values[2] = NjjUtils.getFloatScale(digit, min);
        return values;
    }


    /**
     * 将
     *
     * @param time
     * @return
     */
    public static String long2String(long time) {
        int rawOffset = TimeZone.getDefault().getOffset(time);
        long newTime = time - rawOffset;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mFormat.format(new Date(newTime));
    }

    /**
     * 用于求出心率的最小值
     *
     * @param data
     * @return
     */
    public static int getHeartMinValue(String data) {
        int mMinValue = Integer.MAX_VALUE;
        if (TextUtils.isEmpty(data)) {
            mMinValue = 1000;
            return mMinValue;
        }

        String[] valueArr = data.split(",");
        if (valueArr.length > 0) {
            for (int i = 0; i < valueArr.length; i++) {
                int value = Integer.valueOf(valueArr[i]);
                if (mMinValue > value) {
                    mMinValue = value;
                }
            }
        } else {
            mMinValue = 1000;
        }
        return mMinValue;
    }


    /**
     * 获得一串除了0以外的数据的平均值，最大值，最小值，总和
     *
     * @param string
     * @return 返回的结果顺序是平均值，最大值，最小值，总和
     */
    public static int[] getAverageAndMaxExceptZone(String string) {
        int[] result;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        if (TextUtils.isEmpty(string)) {
            result = new int[]{0, 0, 0, 0};
        } else {
            String[] values = string.split(",");
            int sum = 0;
            int count = 0;
            for (int i = 0; i < values.length; i++) {
                int value = Integer.valueOf(values[i]);

                if (value != 0) {
                    if (max < Integer.valueOf(values[i])) {
                        max = Integer.valueOf(values[i]);
                    }

                    if (min > Integer.valueOf(values[i])) {
                        min = Integer.valueOf(values[i]);
                    }

                    sum += Integer.valueOf(values[i]);
                    count += 1;
                }


            }
            if (count == 0) {
                result = new int[]{0, 0, 0, 0};
            } else {
                result = new int[]{sum / count, max, min, sum};
            }

        }

        return result;
    }

    /**
     * 将float数据四舍五入保留digit位小数
     *
     * @param digit 保留几位小数
     * @param value 要取舍的数字
     * @return float
     */
    public static float getFloatScale(int digit, float value) {
        BigDecimal b = new BigDecimal(value);
        return (float) b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
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

    public static Bitmap getSmallBitmap(Bitmap bitmap, int needWidth, int needHeight) {
        float width = (((float) needWidth) * 1.0f) / ((float) bitmap.getWidth());
        float height = (((float) needHeight) * 1.0f) / ((float) bitmap.getHeight());
//        Matrix matrix = new Matrix();
//        matrix.postScale(width, height, 0.0f, 0.0f);
        Bitmap createBitmap = Bitmap.createBitmap(needWidth, needHeight, Bitmap.Config.RGB_565);

        bitmap = scaleBitmap(bitmap, needWidth, needHeight);

        Canvas canvas = new Canvas(createBitmap);

        final Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));


        canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), 40, 40, paint);


//        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
//        if (!origin.isRecycled()) {
//            origin.recycle();
//            origin=null;
//        }

        return newBM;
    }
}
