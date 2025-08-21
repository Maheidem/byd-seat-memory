package com.rory.electro;

/* loaded from: classes.dex */
public class NativeLoader {
    static {
        System.loadLibrary("electropkg");
    }

    public static native void classesInit0(int i);
}
