package br.com.rory.electro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static a f127a;
    private static SQLiteDatabase b;

    static {
        NativeLoader.classesInit0(21);
    }

    public static native synchronized SQLiteDatabase a();

    public static native synchronized void a(Context context);
}
