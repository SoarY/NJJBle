package com.njj.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

/**
 * @作者
 * @创建日期 2021/10/14
 * @类名 AlbumUtils
 * @所在包 com.njj.mactivepro.util/CameraUtils.java
 * @描述 Android系统相机工具类，适配到Android11
 */
public class CameraUtils {



    /**
     * 将uri转换为file
     * uri类型为file的直接转换出路径
     * uri类型为content的将对应的文件复制到沙盒内的cache目录下进行操作
     * @param context 上下文
     * @param uri uri
     * @return file
     */
    public static File uriToFile(Context context, Uri uri) {
        if (uri == null) {
            Log.e("TAG", "uri为空");
            return null;
        }
        File file = null;
        if (uri.getScheme() != null) {
            Log.e("TAG", "uri.getScheme()：" + uri.getScheme());
            if (uri.getScheme().equals(ContentResolver.SCHEME_FILE) && uri.getPath() != null) {
                //此uri为文件，并且path不为空(保存在沙盒内的文件可以随意访问，外部文件path则为空)
                file = new File(uri.getPath());
            } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                //此uri为content类型，将该文件复制到沙盒内
                ContentResolver resolver = context.getContentResolver();
                @SuppressLint("Recycle")
                Cursor cursor = resolver.query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    try {
                        InputStream inputStream = resolver.openInputStream(uri);
                        if (context.getExternalCacheDir() != null) {
                            //该文件放入cache缓存文件夹中
                            File cache = new File(context.getExternalCacheDir(), fileName);
                            FileOutputStream fileOutputStream = new FileOutputStream(cache);
                            if (inputStream != null) {
//                                    FileUtils.copy(inputStream, fileOutputStream);
                                //上面的copy方法在低版本的手机中会报java.lang.NoSuchMethodError错误，使用原始的读写流操作进行复制
                                byte[] len = new byte[Math.min(inputStream.available(), 1024 * 1024)];
                                int read;
                                while ((read = inputStream.read(len)) != -1) {
                                    fileOutputStream.write(len, 0, read);
                                }
                                file = cache;
                                fileOutputStream.close();
                                inputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file;
    }


}
