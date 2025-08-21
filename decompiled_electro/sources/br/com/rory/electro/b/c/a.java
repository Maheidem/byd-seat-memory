package br.com.rory.electro.b.c;

import com.rory.electro.NativeLoader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Socket f77a;
    private PrintWriter b;
    private BufferedReader c;

    static {
        NativeLoader.classesInit0(222);
    }

    public a() {
        d();
    }

    public a(int i) {
        a(i);
    }

    private native void a(int i);

    private static native String c();

    private native void d();

    public native synchronized String a();

    public native synchronized String a(String str);

    public native synchronized void b();
}
