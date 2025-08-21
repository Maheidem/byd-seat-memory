package org.acra.sender;

import android.content.Context;
import org.acra.collector.CrashReportData;

/* loaded from: classes.dex */
public interface ReportSender {
    void send(Context context, CrashReportData crashReportData);
}
