package br.com.rory.electro.b.d;

import com.rory.electro.NativeLoader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private Socket f89a;
    private PrintWriter b;
    private BufferedReader c;
    private int d;
    private int e;

    static {
        NativeLoader.classesInit0(98);
    }

    public a() {
        this(10);
    }

    public a(int i) {
        this.d = 0;
        this.e = i;
        if ("RELEASE".equals("EMULATOR")) {
            return;
        }
        c();
    }

    private static native String b();

    private native void c();

    public native synchronized String a(String str);

    public native synchronized void a();
}
