package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private final ExecutorService f138a = Executors.newSingleThreadExecutor();
    private final ExecutorService b = Executors.newCachedThreadPool();
    private final Map<String, Consumer<String>> c = new ConcurrentHashMap();
    private Process d;
    private Context e;

    /* renamed from: br.com.rory.electro.g.d$1, reason: invalid class name */
    class AnonymousClass1 implements Consumer<String> {
        static {
            NativeLoader.classesInit0(49);
        }

        AnonymousClass1() {
        }

        @Override // java.util.function.Consumer
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public native void accept(String str);
    }

    /* renamed from: br.com.rory.electro.g.d$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        static {
            NativeLoader.classesInit0(50);
        }

        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.d$3, reason: invalid class name */
    class AnonymousClass3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Consumer f141a;
        final /* synthetic */ String b;

        static {
            NativeLoader.classesInit0(48);
        }

        AnonymousClass3(Consumer consumer, String str) {
            this.f141a = consumer;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(56);
    }

    public d(Context context) {
        this.e = context;
        a();
    }

    static final /* synthetic */ String a(String str) {
        return "-e \"" + str.replace("\"", "\\\"") + "\"";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native String d();

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public native void c();

    public native void a();

    public native void a(String str, Consumer<String> consumer);
}
