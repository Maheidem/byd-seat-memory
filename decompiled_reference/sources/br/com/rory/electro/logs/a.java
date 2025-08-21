package br.com.rory.electro.logs;

import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a {
    static {
        NativeLoader.classesInit0(236);
    }

    public static native void saveJson(int i, String str);

    public native int getType();

    public native void save();

    public native String toJSON();
}
