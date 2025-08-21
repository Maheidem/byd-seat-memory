package br.com.rory.electro.g;

import com.rory.electro.NativeLoader;
import java.io.File;

/* loaded from: classes.dex */
public class b {
    static {
        NativeLoader.classesInit0(60);
    }

    public static native File a(String str);

    public static native String a(File file);

    public static native String a(String str, File file);
}
