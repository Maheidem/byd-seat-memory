package okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public enum TlsVersion {
    TLS_1_3("TLSv1.3"),
    TLS_1_2("TLSv1.2"),
    TLS_1_1("TLSv1.1"),
    TLS_1_0("TLSv1"),
    SSL_3_0("SSLv3");

    final String javaName;

    TlsVersion(String str) {
        this.javaName = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static okhttp3.TlsVersion forJavaName(java.lang.String r3) {
        /*
            int r0 = r3.hashCode()
            r1 = 79201641(0x4b88569, float:4.338071E-36)
            if (r0 == r1) goto L3a
            r1 = 79923350(0x4c38896, float:4.5969714E-36)
            if (r0 == r1) goto L30
            switch(r0) {
                case -503070503: goto L26;
                case -503070502: goto L1c;
                case -503070501: goto L12;
                default: goto L11;
            }
        L11:
            goto L44
        L12:
            java.lang.String r0 = "TLSv1.3"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L44
            r0 = 0
            goto L45
        L1c:
            java.lang.String r0 = "TLSv1.2"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L44
            r0 = 1
            goto L45
        L26:
            java.lang.String r0 = "TLSv1.1"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L44
            r0 = 2
            goto L45
        L30:
            java.lang.String r0 = "TLSv1"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L44
            r0 = 3
            goto L45
        L3a:
            java.lang.String r0 = "SSLv3"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L44
            r0 = 4
            goto L45
        L44:
            r0 = -1
        L45:
            switch(r0) {
                case 0: goto L6b;
                case 1: goto L68;
                case 2: goto L65;
                case 3: goto L62;
                case 4: goto L5f;
                default: goto L48;
            }
        L48:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unexpected TLS version: "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            r0.<init>(r3)
            throw r0
        L5f:
            okhttp3.TlsVersion r3 = okhttp3.TlsVersion.SSL_3_0
            return r3
        L62:
            okhttp3.TlsVersion r3 = okhttp3.TlsVersion.TLS_1_0
            return r3
        L65:
            okhttp3.TlsVersion r3 = okhttp3.TlsVersion.TLS_1_1
            return r3
        L68:
            okhttp3.TlsVersion r3 = okhttp3.TlsVersion.TLS_1_2
            return r3
        L6b:
            okhttp3.TlsVersion r3 = okhttp3.TlsVersion.TLS_1_3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.TlsVersion.forJavaName(java.lang.String):okhttp3.TlsVersion");
    }

    static List<TlsVersion> forJavaNames(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(forJavaName(str));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public String javaName() {
        return this.javaName;
    }
}
