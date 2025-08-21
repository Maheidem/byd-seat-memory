package br.com.rory.electro.b.b;

import com.rory.electro.NativeLoader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Socket f74a;
    private PrintWriter b;
    private BufferedReader c;

    static {
        NativeLoader.classesInit0(246);
    }

    public a() {
        c();
    }

    public a(int i) {
        a(i);
    }

    private native void a(int i);

    private static native String b();

    private native void c();

    public native synchronized String a(String str);

    public native synchronized void a();
}
