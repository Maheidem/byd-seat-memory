package br.com.rory.electro.database.a;

import android.database.sqlite.SQLiteDatabase;
import com.rory.electro.NativeLoader;
import java.util.List;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private SQLiteDatabase f123a;

    static {
        NativeLoader.classesInit0(234);
    }

    public a(SQLiteDatabase sQLiteDatabase) {
        this.f123a = sQLiteDatabase;
    }

    private native String a();

    private native String[] b();

    public native long a(br.com.rory.electro.database.b.a aVar);

    public native List<br.com.rory.electro.database.b.a> a(long j);
}
