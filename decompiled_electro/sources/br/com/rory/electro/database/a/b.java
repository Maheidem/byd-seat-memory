package br.com.rory.electro.database.a;

import android.database.sqlite.SQLiteDatabase;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private SQLiteDatabase f124a;

    static {
        NativeLoader.classesInit0(233);
    }

    public b(SQLiteDatabase sQLiteDatabase) {
        this.f124a = sQLiteDatabase;
    }

    private native String b();

    private native String[] c();

    public native br.com.rory.electro.database.b.b a();

    public native void a(br.com.rory.electro.database.b.b bVar);
}
