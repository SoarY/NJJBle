package com.njj.njjsdk.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";


    public synchronized static String id(Context context) {
        if (sID == null) {
            File installtion = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installtion.exists()) {
                    writeInstallationFile(installtion);
                }
                sID = readInstallationFile(installtion);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sID;
    }


    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }


}
