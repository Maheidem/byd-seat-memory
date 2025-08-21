package br.com.rory.electro.database.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rory.electro.NativeLoader;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private SQLiteDatabase f126a;

    static {
        NativeLoader.classesInit0(231);
    }

    public d(SQLiteDatabase sQLiteDatabase) {
        this.f126a = sQLiteDatabase;
    }

    private native br.com.rory.electro.database.b.d a(Cursor cursor);

    private native String[] a();

    public native long a(br.com.rory.electro.database.b.d dVar);

    public native List<br.com.rory.electro.database.b.d> a(long j);

    public native Map<Long, Integer> a(List<br.com.rory.electro.database.b.c> list);

    public native br.com.rory.electro.database.b.d b(long j);
}
