package org.acra.util;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;
import org.acra.ACRA;

/* loaded from: classes.dex */
public class Installation {
    private static final String INSTALLATION = "ACRA-INSTALLATION";
    private static String sID;

    public static synchronized String id(Context context) {
        if (sID == null) {
            File file = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!file.exists()) {
                    writeInstallationFile(file);
                }
                sID = readInstallationFile(file);
            } catch (IOException e) {
                Log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
                return "Couldn't retrieve InstallationId";
            } catch (RuntimeException e2) {
                Log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e2);
                return "Couldn't retrieve InstallationId";
            }
        }
        return sID;
    }

    private static String readInstallationFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] bArr = new byte[(int) randomAccessFile.length()];
        try {
            randomAccessFile.readFully(bArr);
            randomAccessFile.close();
            return new String(bArr);
        } catch (Throwable th) {
            randomAccessFile.close();
            throw th;
        }
    }

    private static void writeInstallationFile(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            fileOutputStream.write(UUID.randomUUID().toString().getBytes());
        } finally {
            fileOutputStream.close();
        }
    }
}
