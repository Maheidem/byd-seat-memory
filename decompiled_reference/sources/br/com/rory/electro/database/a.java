package br.com.rory.electro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private Context f122a;

    static {
        NativeLoader.classesInit0(23);
    }

    public a(Context context) {
        super(context, a(), (SQLiteDatabase.CursorFactory) null, 9);
        this.f122a = context;
    }

    private static native String a();

    @Override // android.database.sqlite.SQLiteOpenHelper
    public native void onCreate(SQLiteDatabase sQLiteDatabase);

    @Override // android.database.sqlite.SQLiteOpenHelper
    public native void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);
}
