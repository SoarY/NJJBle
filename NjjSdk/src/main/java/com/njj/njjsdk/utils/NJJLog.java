package com.njj.njjsdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NJJLog
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/10/8 14:12
 * @Version 1.0
 */
public class NJJLog {
    private static boolean ENABLE_LOG = true;

    public static final int LEVEL_V = 5;
    public static final int LEVEL_I = 4;
    public static final int LEVEL_D = 3;
    public static final int LEVEL_W = 2;
    public static final int LEVEL_E = 1;
    public static final int LEVEL_NO_LOG = 0;
//    public static ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(50);
    public static int DEBUG_LEVEL = LEVEL_V;

    private static String LOG_PREFIX = "njjLog";

    private static long SAVE_LOG_INTERVAL = 15 * 24 * 60 * 60 * 1000; //本地日志文件过期时间15天

    private static long SAVE_LOG_MAX_SIZE = 100 * 1024 * 1024; //本地日志文件最大文件大小50M

    private static final String ZIP_KEY = "njj_zip_key";

    /**
     * 设置是否是输出日志
     *
     * @param enable
     */
    public static void setEnableLog(boolean enable) {
        ENABLE_LOG = enable;
        Log.i("njjLog", "ENABLE_LOG: " + ENABLE_LOG);
    }

    /**
     * 判断当前是否允许日志输出
     */
    public static boolean isEnableLog() {
        return ENABLE_LOG;
    }

    /**
     * 设置日志显示级别
     *
     * @param level LEVEL_V、LEVEL_I、LEVEL_D、LEVEL_W、LEVEL_E、LEVEL_NO_LOG
     */
    public static void setDebugLevel(int level) {
        DEBUG_LEVEL = level;
    }

    //********************************************************
    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_E) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String eTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String eMsg = getClassMsg().append(msg).toString();
        Log.e(eTag, eMsg);
        saveToSDCard(eMsg, LEVEL_E, SAVE_LOG_INTERVAL);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_E) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String eTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String eMsg = getClassMsg().append(msg).toString();
        Log.e(eTag, eMsg, e);
        saveToSDCard(eMsg, LEVEL_E, SAVE_LOG_INTERVAL);

    }

    //********************************************************
    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_W) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String wTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String wMsg = getClassMsg().append(msg).toString();
        Log.w(wTag, wMsg);
        saveToSDCard(wMsg, LEVEL_W, SAVE_LOG_INTERVAL);

    }

    //********************************************************
    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_D) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String dTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String dMsg = getClassMsg().append(msg).toString();
        Log.d(dTag, dMsg);
        saveToSDCard(dMsg, LEVEL_D, SAVE_LOG_INTERVAL);

    }

    //********************************************************
    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_I) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String iTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String iMsg = getClassMsg().append(msg).toString();
        Log.i(iTag, iMsg);
        saveToSDCard(iMsg, LEVEL_I, SAVE_LOG_INTERVAL);

    }

    //********************************************************
    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!ENABLE_LOG) {
            return;
        }
        if (DEBUG_LEVEL < LEVEL_V) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String vTag = TextUtils.isEmpty(tag) ? LOG_PREFIX : LOG_PREFIX + ":" + tag;
        String vMsg = getClassMsg().append(msg).toString();
        Log.v(vTag, vMsg);
        saveToSDCard(vMsg, LEVEL_V, SAVE_LOG_INTERVAL);

    }

    /**
     * 获取指定异常的全部堆栈信息
     *
     * @param ex
     * @return
     */
    public static String getExceptionAllInfo(Exception ex) {
        if (ex == null) {
            return "";
        }
        String sOut = "" + ex.toString() + "\n";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }

    /**
     * 获取打印日志的详细信息
     *
     * @return
     */
    private static StringBuilder getClassMsg() {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return new StringBuilder().append("[")
                .append(caller.getFileName())
                .append(" ")
                .append(caller.getMethodName())
                .append("() :: line ")
                .append(caller.getLineNumber())
                .append("] ");
    }

    /**
     * 保存log到文件中
     *
     * @param logStr       要保存的内容
     * @param level        要保存的内容等级  1[error]>2[warn]>3[debug]>4[info]>5[verbose]
     * @param intervalTime 文件保存内容的间隔时间(毫秒级)
     */
    private static void saveToSDCard(final String logStr, final int level, final long intervalTime) {
       /* newFixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                saveToSDCard_2(logStr, level, intervalTime);
            }
        });*/


        new Thread(new Runnable() {
            @Override
            public void run() {
                saveToSDCard_2(logStr, level, intervalTime);
            }
        }).start();// TODO 这里要放线程池管理，不然开的线程过多会OOM
    }

    /**
     * 保存log到文件中
     *
     * @param logStr       要保存的内容
     * @param level        要保存的内容等级  1[error]>2[warn]>3[debug]>4[info]>5[verbose]
     * @param intervalTime 文件保存内容的间隔时间(毫秒级)
     */
    private synchronized static void saveToSDCard_2(final String logStr, final int level, final long intervalTime) {
        StringBuilder sb = new StringBuilder();
        Context mContext = ApplicationProxy.getInstance().getApplication();
        boolean isOpenSave = true;// 默认保存日志

        String levelStr = "v";// 默认是 v 等级


        if (!isOpenSave) {
            return;
        }

        int spLevel = 5;
        if ("i".equals(levelStr)) {
            spLevel = 4;
        } else if ("d".equals(levelStr)) {
            spLevel = 3;
        } else if ("w".equals(levelStr)) {
            spLevel = 2;
        } else if ("e".equals(levelStr)) {
            spLevel = 1;
        }

        if (level > spLevel) {
            return;
        }

        try {
            PackageInfo pkInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            sb.append("[applicationID").append(" = ").append(mContext.getPackageName());
            sb.append(",versionName").append(" = ").append(pkInfo.versionName);
            sb.append(",versionCode").append(" = ").append(pkInfo.versionCode).append("]");
            sb.append(format(System.currentTimeMillis(), YYYY_MM_DD2_HH_MM_SS)).append(logStr);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        String PATH="/njj_log/";
       /* if (isCurrentAppProcess(ApplicationProxy.getInstance().getApplicationContext())) {
            PATH = "/njj_log/";// 主进程
        } else {
            PATH = "/njj_push_log/";// push进程
        }*/
        String path = mContext.getFilesDir() + PATH;//文件夹名
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();//创建文件夹
        }

        //log_创建文件的时间_保存多少天的时间.log
        //1.首先从文件夹遍历文件
        File[] files = dir.listFiles();
        String time = format(new Date(), YYYY_MM_DD_HH_MM_SS_SSS);
        String fileName = mContext.getFilesDir().getPath() + PATH + "/log_" + time + "_" + intervalTime + ".log";
        if (files == null || files.length <= 0) {//2.1空文件夹
            //2.1.1那就创建文件
            writeLog(fileName, sb);
        } else {//2.2非空文件夹
            List<String> nameList = new ArrayList<>();
            List<File> fileList = Arrays.asList(files);
            getSortList(fileList);//降序排列这样的话log保存到最新的log文件中
            for (File f : fileList) {//2.2.1将已有的文件名全部放置列表中
                if (f.length() <= SAVE_LOG_MAX_SIZE) {
                    nameList.add(f.getName());
                } else {//超过最大的大小（先不删除，感觉删除最好还是超过期限为主）
                    deleteFile(f.getAbsolutePath());
                }
            }


            //2.2.2，文件名牵涉到三个属性：1.日志文件的创建时间，2.文件保存的日志间隔时间
            List<Long> createTimeList = new ArrayList<>();
            List<Long> intervalTimeList = new ArrayList<>();

            //log_2018-10-29-17:00:12.123_86400000.log
            for (String s : nameList) {
                if (!TextUtils.isEmpty(s)
                        && s.contains("_")) {
                    String[] nameArr = s.split("_");
                    if (nameArr != null && nameArr.length > 0) {
                        createTimeList.add(getTime(nameArr[1], YYYY_MM_DD_HH_MM_SS_SSS));
                        intervalTimeList.add(Long.parseLong(nameArr[2].replace(".log", "")));
                    }
                }
            }
            //2.3.1先判断时间
            List<Integer> indexList = new ArrayList<>();
            for (int i = 0; i < createTimeList.size(); i++) {
                if ((System.currentTimeMillis() - createTimeList.get(i)) < intervalTime) {
                    indexList.add(i);
                } else {
                    //超过最长保存文件时间
                    deleteFile(mContext.getFilesDir().getPath() + PATH + "/" + nameList.get(i));
                }
            }

            if (indexList != null && indexList.size() > 0) {//在时间范围内的
                //判断文件是否存在
                File saveFile = new File(mContext.getFilesDir().getPath() + PATH + "/" + nameList.get(indexList.get(0)));
                if (!saveFile.exists()) {
                    writeLog(fileName, sb);
                } else {
                    writeLog(saveFile.getAbsolutePath(), sb);
                }

            } else {//不在时间范围内的直接创建log
                writeLog(fileName, sb);
            }
        }
    }

    /**
     * 降序
     *
     * @param list
     * @return
     */
    private static List<File> getSortList(List<File> list) {
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() < o2.lastModified()) {
                    return 1;
                }
                if (o1.lastModified() == o2.lastModified()) {
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    /**
     * 写log
     *
     * @param fileName
     * @param sb
     */
    private static void writeLog(String fileName, StringBuilder sb) {
        File file = new File(fileName);
        RandomAccessFile raf = null;
        try {
            if (!file.exists()) {
                boolean isSuccess = file.createNewFile();
                if (!isSuccess) {
                    return;
                }
            }
            raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(sb.toString().getBytes());
            raf.write("\n".getBytes());

            String  PATH_ZIP = "/njj_log_zip/";
          /*  if (isCurrentAppProcess(ApplicationProxy.getInstance().getApplicationContext())) {
                PATH_ZIP = "/njj_log_zip/";// 主进程
            } else {
                PATH_ZIP = "/njj_push_log_zip/";// push进程
            }*/

//            String dest = ApplicationProxy.getInstance().getApplication().getFilesDir().getPath() + PATH_ZIP;
//            CompressOperateUtil.zip(fileName, dest, true, ZIP_KEY); 不再做压缩，故注掉

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    private static void deleteFile(String fileName) {
        // TODO: 2018/11/20 这里只删除了源文件，压缩文件没有删除
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 判断是否为当前的app进程
     *
     * @return 是否为当前的app进程
     */
    public static boolean isCurrentAppProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null && mActivityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid && context.getPackageName().equals(appProcess.processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final String YYYY_MM_DD2_HH_MM_SS = "yyyy年MM月dd日 HH:mm:ss";

    private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd-HH:mm:ss.SSS";

    /**
     * 格式化日期时间
     *
     * @param milliseconds 毫秒数
     * @param format       format
     * @return String
     */
    private static String format(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        return sdf.format(new Date(milliseconds));
    }

    /**
     * 格式化日期时间
     *
     * @param date   date
     * @param format format
     * @return String
     */
    private static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        return sdf.format(date);
    }

    /**
     * 转换时间日期
     *
     * @param time 时间字符串
     * @return long
     */
    private static long getTime(String time, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
