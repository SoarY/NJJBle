package com.njj.demo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;


import com.njj.demo.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;

public class FileHelper {
    public static File getAppRootDirPath() {
        return MyApplication.getInstance().getExternalFilesDir(null).getAbsoluteFile();
    }

    public static String CUSTOM_TEST = getAppRootDirPath() + File.separator + "capture" + File.separator + "test.bmp";

    public static String CUSTOM_DIAL_BG_PATH = getAppRootDirPath() + File.separator + "capture" + File.separator + "CUSTBG";
    public static Uri uri;

    public static File createImageFile(Context context, boolean isCrop) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "";
            if (isCrop) {
                fileName = "IMG_" + timeStamp + "_CROP.png";
            } else {
                fileName = "IMG_" + timeStamp + ".png";
            }
            File rootFile = new File(getAppRootDirPath() + File.separator + "capture");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File imgFile;
            if (Build.VERSION.SDK_INT >= 30) {
                imgFile = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + File.separator + fileName);
                // 通过 MediaStore API 插入file 为了拿到系统裁剪要保存到的uri（因为App没有权限不能访问公共存储空间，需要通过 MediaStore API来操作）
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, imgFile.getAbsolutePath());
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                imgFile = new File(rootFile.getAbsolutePath() + File.separator + fileName);
            }
            return imgFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getCropFile(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return new File(path);
        }
        return null;
    }


    public static Bitmap getSmallBitmap(Bitmap bitmap, int needWidth, int needHeight) {
        float width = (((float) needWidth) * 1.0f) / ((float) bitmap.getWidth());
        float height = (((float) needHeight) * 1.0f) / ((float) bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(width, height, 0.0f, 0.0f);
        Bitmap createBitmap = Bitmap.createBitmap(needWidth, needHeight, Bitmap.Config.RGB_565);
        new Canvas(createBitmap).drawBitmap(bitmap, matrix, new Paint());
        return createBitmap;
    }

    public static boolean saveBitmap(Bitmap bitmap, String str) {
        int size = bitmap.getWidth() * bitmap.getHeight() * 2;
        File file = new File(str);
        try {
            if (file.exists()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteBuffer allocate = ByteBuffer.allocate(size);
            Bitmap tmpBitmap = bitmap.copy(Bitmap.Config.RGB_565, false);
            tmpBitmap.copyPixelsToBuffer(allocate);
            fileOutputStream.write(allocate.array());
            fileOutputStream.close();
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }



    /**
     * 以字节流读取文件
     * @param path  文件路径
     * @return 字节数组
     */
    public static byte[] getByteStream(String path){
        // 拿到文件
        File file = new File(path);
        return getByteStream(file);
    }

    /**
     * 以字节流读取文件
     * @param file  文件
     * @return 字节数组
     */
    public static byte[] getByteStream(File file){
        try{
            // 拿到输入流
            FileInputStream input = new FileInputStream(file);
            // 建立存储器
            byte[] buf =new byte[input.available()];
            // 读取到存储器
            input.read(buf);
            // 关闭输入流
            input.close();
            // 返回数据
            return buf;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
