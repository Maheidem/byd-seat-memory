package org.acra.sender;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.util.HttpRequest;
import org.acra.util.JSONReportBuilder;

/* loaded from: classes.dex */
public class HttpSender implements ReportSender {
    private final Uri mFormUri;
    private final Map<ReportField, String> mMapping;
    private final Method mMethod;
    private String mPassword;
    private final Type mType;
    private String mUsername;

    /* renamed from: org.acra.sender.HttpSender$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$sender$HttpSender$Type;

        static {
            try {
                $SwitchMap$org$acra$sender$HttpSender$Method[Method.POST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$sender$HttpSender$Method[Method.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $SwitchMap$org$acra$sender$HttpSender$Type = new int[Type.values().length];
            try {
                $SwitchMap$org$acra$sender$HttpSender$Type[Type.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$acra$sender$HttpSender$Type[Type.FORM.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum Method {
        POST,
        PUT
    }

    public enum Type {
        FORM { // from class: org.acra.sender.HttpSender.Type.1
            @Override // org.acra.sender.HttpSender.Type
            public String getContentType() {
                return "application/x-www-form-urlencoded";
            }
        },
        JSON { // from class: org.acra.sender.HttpSender.Type.2
            @Override // org.acra.sender.HttpSender.Type
            public String getContentType() {
                return "application/json";
            }
        };

        /* synthetic */ Type(AnonymousClass1 anonymousClass1) {
            this();
        }

        public abstract String getContentType();
    }

    public HttpSender(Method method, Type type, String str, Map<ReportField, String> map) {
        this.mMethod = method;
        this.mFormUri = Uri.parse(str);
        this.mMapping = map;
        this.mType = type;
        this.mUsername = null;
        this.mPassword = null;
    }

    public HttpSender(Method method, Type type, Map<ReportField, String> map) {
        this.mMethod = method;
        this.mFormUri = null;
        this.mMapping = map;
        this.mType = type;
        this.mUsername = null;
        this.mPassword = null;
    }

    private Map<String, String> remap(Map<ReportField, String> map) {
        ReportField[] reportFieldArrCustomReportContent = ACRA.getConfig().customReportContent();
        if (reportFieldArrCustomReportContent.length == 0) {
            reportFieldArrCustomReportContent = ACRAConstants.DEFAULT_REPORT_FIELDS;
        }
        HashMap map2 = new HashMap(map.size());
        for (ReportField reportField : reportFieldArrCustomReportContent) {
            map2.put((this.mMapping == null || this.mMapping.get(reportField) == null) ? reportField.toString() : this.mMapping.get(reportField), map.get(reportField));
        }
        return map2;
    }

    @Override // org.acra.sender.ReportSender
    public void send(Context context, CrashReportData crashReportData) throws Throwable {
        URL url;
        try {
            URL url2 = this.mFormUri == null ? new URL(ACRA.getConfig().formUri()) : new URL(this.mFormUri.toString());
            Log.d(ACRA.LOG_TAG, "Connect to " + url2.toString());
            String strFormUriBasicAuthPassword = null;
            String strFormUriBasicAuthLogin = this.mUsername != null ? this.mUsername : ACRAConfiguration.isNull(ACRA.getConfig().formUriBasicAuthLogin()) ? null : ACRA.getConfig().formUriBasicAuthLogin();
            if (this.mPassword != null) {
                strFormUriBasicAuthPassword = this.mPassword;
            } else if (!ACRAConfiguration.isNull(ACRA.getConfig().formUriBasicAuthPassword())) {
                strFormUriBasicAuthPassword = ACRA.getConfig().formUriBasicAuthPassword();
            }
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setConnectionTimeOut(ACRA.getConfig().connectionTimeout());
            httpRequest.setSocketTimeOut(ACRA.getConfig().socketTimeout());
            httpRequest.setMaxNrRetries(ACRA.getConfig().maxNumberOfRequestRetries());
            httpRequest.setLogin(strFormUriBasicAuthLogin);
            httpRequest.setPassword(strFormUriBasicAuthPassword);
            httpRequest.setHeaders(ACRA.getConfig().getHttpHeaders());
            String paramsAsFormString = AnonymousClass1.$SwitchMap$org$acra$sender$HttpSender$Type[this.mType.ordinal()] != 1 ? HttpRequest.getParamsAsFormString(remap(crashReportData)) : crashReportData.toJSON().toString();
            switch (this.mMethod) {
                case POST:
                    url = url2;
                    break;
                case PUT:
                    url = new URL(url2.toString() + '/' + crashReportData.getProperty(ReportField.REPORT_ID));
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown method: " + this.mMethod.name());
            }
            httpRequest.send(context, url, this.mMethod, paramsAsFormString, this.mType);
        } catch (IOException e) {
            throw new ReportSenderException("Error while sending " + ACRA.getConfig().reportType() + " report via Http " + this.mMethod.name(), e);
        } catch (JSONReportBuilder.JSONReportException e2) {
            throw new ReportSenderException("Error while sending " + ACRA.getConfig().reportType() + " report via Http " + this.mMethod.name(), e2);
        }
    }

    public void setBasicAuth(String str, String str2) {
        this.mUsername = str;
        this.mPassword = str2;
    }
}
