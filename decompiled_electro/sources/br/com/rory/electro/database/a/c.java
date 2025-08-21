package br.com.rory.electro.database.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rory.electro.NativeLoader;
import java.util.List;

/* loaded from: classes.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private SQLiteDatabase f125a;

    static {
        NativeLoader.classesInit0(232);
    }

    public c(SQLiteDatabase sQLiteDatabase) {
        this.f125a = sQLiteDatabase;
    }

    private native br.com.rory.electro.database.b.c a(Cursor cursor);

    private native String[] d();

    public native long a();

    public native br.com.rory.electro.database.b.c a(long j, long j2);

    public native List<br.com.rory.electro.database.b.c> a(int i);

    public native boolean a(long j);

    public native boolean a(long j, double d, double d2, int i);

    public native br.com.rory.electro.database.b.c b(long j);

    public native void b();

    public native List<br.com.rory.electro.database.b.c> c();

    public native List<br.com.rory.electro.database.b.c> c(long j);
}
