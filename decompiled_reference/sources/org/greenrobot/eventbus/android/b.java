package org.greenrobot.eventbus.android;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class b {
    public static boolean a() {
        try {
            return Class.forName("android.os.Looper").getDeclaredMethod("getMainLooper", new Class[0]).invoke(null, new Object[0]) != null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }

    public static boolean b() throws ClassNotFoundException {
        try {
            Class.forName("org.greenrobot.eventbus.android.AndroidComponentsImpl");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static a c() {
        try {
            return (a) Class.forName("org.greenrobot.eventbus.android.AndroidComponentsImpl").getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }
}
