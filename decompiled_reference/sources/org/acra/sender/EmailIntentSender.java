package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;

/* loaded from: classes.dex */
public class EmailIntentSender implements ReportSender {
    private final Context mContext;

    public EmailIntentSender(Context context) {
        this.mContext = context;
    }

    private String buildBody(CrashReportData crashReportData) {
        ReportField[] reportFieldArrCustomReportContent = ACRA.getConfig().customReportContent();
        if (reportFieldArrCustomReportContent.length == 0) {
            reportFieldArrCustomReportContent = ACRAConstants.DEFAULT_MAIL_REPORT_FIELDS;
        }
        StringBuilder sb = new StringBuilder();
        for (ReportField reportField : reportFieldArrCustomReportContent) {
            sb.append(reportField.toString());
            sb.append("=");
            sb.append((String) crashReportData.get(reportField));
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override // org.acra.sender.ReportSender
    public void send(Context context, CrashReportData crashReportData) {
        String str = this.mContext.getPackageName() + " Crash Report";
        String strBuildBody = buildBody(crashReportData);
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.fromParts("mailto", ACRA.getConfig().mailTo(), null));
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.TEXT", strBuildBody);
        this.mContext.startActivity(intent);
    }
}
