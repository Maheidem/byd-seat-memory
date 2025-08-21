package org.acra.util;

import android.content.Context;
import org.apache.http.conn.scheme.SocketFactory;

/* loaded from: classes.dex */
public final class DefaultHttpsSocketFactoryFactory implements HttpsSocketFactoryFactory {
    public static final HttpsSocketFactoryFactory INSTANCE = new DefaultHttpsSocketFactoryFactory();

    @Override // org.acra.util.HttpsSocketFactoryFactory
    public SocketFactory create(Context context) {
        return new TlsSniSocketFactory();
    }
}
