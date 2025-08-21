package br.com.rory.electro.c;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a {

    /* renamed from: br.com.rory.electro.c.a$1, reason: invalid class name */
    static class AnonymousClass1 extends ContextWrapper {
        static {
            NativeLoader.classesInit0(166);
        }

        AnonymousClass1(Context context) {
            super(context);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkCallingOrSelfPermission(String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkCallingOrSelfUriPermission(Uri uri, int i);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkCallingPermission(String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkCallingUriPermission(Uri uri, int i);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkPermission(String str, int i, int i2);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkSelfPermission(String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkUriPermission(Uri uri, int i, int i2, int i3);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native int checkUriPermission(Uri uri, String str, String str2, int i, int i2, int i3);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceCallingOrSelfPermission(String str, String str2);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceCallingOrSelfUriPermission(Uri uri, int i, String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceCallingPermission(String str, String str2);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceCallingUriPermission(Uri uri, int i, String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforcePermission(String str, int i, int i2, String str2);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceUriPermission(Uri uri, int i, int i2, int i3, String str);

        @Override // android.content.ContextWrapper, android.content.Context
        public final native void enforceUriPermission(Uri uri, String str, String str2, int i, int i2, int i3, String str3);
    }

    static {
        NativeLoader.classesInit0(194);
    }

    public static native Context a(Context context);

    public static native boolean a(String str);
}
