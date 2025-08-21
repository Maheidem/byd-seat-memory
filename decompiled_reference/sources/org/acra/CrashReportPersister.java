package org.acra;

import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.helper.ItemTouchHelper;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import org.acra.collector.CrashReportData;

/* loaded from: classes.dex */
final class CrashReportPersister {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private final Context context;

    CrashReportPersister(Context context) {
        this.context = context;
    }

    private void dumpString(StringBuilder sb, String str, boolean z) {
        int i;
        String hexString;
        if (z || str.length() <= 0 || str.charAt(0) != ' ') {
            i = 0;
        } else {
            sb.append("\\ ");
            i = 1;
        }
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            switch (cCharAt) {
                case '\t':
                    hexString = "\\t";
                    sb.append(hexString);
                    break;
                case '\n':
                    hexString = "\\n";
                    sb.append(hexString);
                    break;
                case 11:
                default:
                    if ("\\#!=:".indexOf(cCharAt) >= 0 || (z && cCharAt == ' ')) {
                        sb.append('\\');
                    }
                    if (cCharAt < ' ' || cCharAt > '~') {
                        hexString = Integer.toHexString(cCharAt);
                        sb.append("\\u");
                        for (int i2 = 0; i2 < 4 - hexString.length(); i2++) {
                            sb.append("0");
                        }
                        sb.append(hexString);
                        break;
                    } else {
                        sb.append(cCharAt);
                        break;
                    }
                case '\f':
                    hexString = "\\f";
                    sb.append(hexString);
                    break;
                case '\r':
                    hexString = "\\r";
                    sb.append(hexString);
                    break;
            }
            i++;
        }
    }

    private boolean isEbcdic(BufferedInputStream bufferedInputStream) {
        byte b;
        do {
            b = (byte) bufferedInputStream.read();
            if (b == -1 || b == 35 || b == 10 || b == 61) {
                return false;
            }
        } while (b != 21);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x00b1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0158 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x00f3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e1 A[PHI: r4
      0x00e1: PHI (r4v13 char) = (r4v4 char), (r4v4 char), (r4v15 char) binds: [B:119:0x015a, B:122:0x015f, B:64:0x00e0] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized org.acra.collector.CrashReportData load(java.io.Reader r19) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.CrashReportPersister.load(java.io.Reader):org.acra.collector.CrashReportData");
    }

    public CrashReportData load(String str) throws IOException {
        FileInputStream fileInputStreamOpenFileInput = this.context.openFileInput(str);
        if (fileInputStreamOpenFileInput == null) {
            throw new IllegalArgumentException("Invalid crash report fileName : " + str);
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStreamOpenFileInput, 8192);
            bufferedInputStream.mark(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            boolean zIsEbcdic = isEbcdic(bufferedInputStream);
            bufferedInputStream.reset();
            return !zIsEbcdic ? load(new InputStreamReader(bufferedInputStream, "ISO8859-1")) : load(new InputStreamReader(bufferedInputStream));
        } finally {
            fileInputStreamOpenFileInput.close();
        }
    }

    public void store(CrashReportData crashReportData, String str) throws IOException {
        FileOutputStream fileOutputStreamOpenFileOutput = this.context.openFileOutput(str, 0);
        try {
            StringBuilder sb = new StringBuilder(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStreamOpenFileOutput, "ISO8859_1");
            for (Map.Entry<ReportField, String> entry : crashReportData.entrySet()) {
                dumpString(sb, entry.getKey().toString(), true);
                sb.append('=');
                dumpString(sb, entry.getValue(), false);
                sb.append(LINE_SEPARATOR);
                outputStreamWriter.write(sb.toString());
                sb.setLength(0);
            }
            outputStreamWriter.flush();
        } finally {
            fileOutputStreamOpenFileOutput.close();
        }
    }
}
