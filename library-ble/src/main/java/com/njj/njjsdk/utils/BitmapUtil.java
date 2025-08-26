package com.njj.njjsdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * NAME：YONG_
 * Created at: 2025/8/26 16
 * Describe:
 */
public class BitmapUtil {

    private static final int MAX_FILE_SIZE_KB = 30;

    public static byte[] resizeImage(String srcPath, int w, int h,int reducedRade) {
        //        w=352;
        //        h=352;
        // 先进行中心裁剪
        Bitmap bitmapOrg = centerCropHW(srcPath, w, h);
        if (bitmapOrg == null) {
            return null;
        }

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 缩放图片
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
        bitmapOrg.recycle(); // 释放原始 bitmap 资源

        if (reducedRade!=0){
            // 添加圆角效果
            Bitmap roundedBitmap = getRoundedCornerBitmap(scaledBitmap, reducedRade);
            scaledBitmap.recycle(); // 释放缩放后的 bitmap 资源
            return compressBitmapToMaxSizeAsByteArray(roundedBitmap);
        }

        // 压缩并返回
        return compressBitmapToMaxSizeAsByteArray(scaledBitmap);
    }

    public static Bitmap centerCropHW(String path, float width, float height) {
        Bitmap srcBitmap = BitmapFactory.decodeFile(path);
        int desWidth = srcBitmap.getWidth();
        int desHeight = srcBitmap.getHeight();
        float desRate = (float) desWidth / desHeight;
        float rate = width / height;
        if (desRate > rate) {//宽有多余
            desWidth = (int) (desHeight * width / height);
        } else {//宽有不够，裁剪高度
            desHeight = (int) (desWidth * height / width);
        }
        return centerCrop(srcBitmap, desWidth, desHeight);
    }

    public static Bitmap centerCrop(Bitmap srcBitmap, int desWidth, int desHeight) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int newWidth = srcWidth;
        int newHeight = srcHeight;
        float srcRate = (float) srcWidth / srcHeight;
        float desRate = (float) desWidth / desHeight;
        int dx = 0, dy = 0;
        if (srcRate == desRate) {
            return srcBitmap;
        } else if (srcRate > desRate) {
            newWidth = (int) (srcHeight * desRate);
            dx = (srcWidth - newWidth) / 2;
        } else {
            newHeight = (int) (srcWidth / desRate);
            dy = (srcHeight - newHeight) / 2;
        }
        //创建目标Bitmap，并用选取的区域来绘制
        Bitmap desBitmap = Bitmap.createBitmap(srcBitmap, dx, dy, newWidth, newHeight);
        return desBitmap;
    }

    /**
     * 为图片添加圆角
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int radius) {
        // 创建一个相同大小的空白 bitmap
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas canvas = new Canvas(output);

        // 定义画笔和路径
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true); // 抗锯齿
        canvas.drawARGB(0, 0, 0, 0); // 透明背景
        paint.setColor(Color.BLACK);

        // 绘制圆角矩形
        canvas.drawRoundRect(rectF, radius, radius, paint);

        // 设置混合模式，只在圆角矩形区域内绘制图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 压缩图片到指定大小(50KB)并返回byte[]
     * @param bitmap 原始Bitmap
     * @return 压缩后的byte[]，如果压缩失败则返回null
     */
    public static byte[] compressBitmapToMaxSizeAsByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int quality = 100; // 起始质量
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // 先尝试以较高质量压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            // 如果压缩后的大小仍然超过限制，则逐步降低质量
            while (outputStream.size() / 1024 > MAX_FILE_SIZE_KB && quality > 10) {
                quality -= 5; // 每次降低5%的质量
                outputStream.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            }

            // 返回压缩后的byte[]
            return outputStream.toByteArray();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
