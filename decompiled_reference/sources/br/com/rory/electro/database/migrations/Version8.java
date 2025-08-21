package br.com.rory.electro.database.migrations;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.helper.ItemTouchHelper;
import br.com.rory.electro.database.c;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class Version8 implements c {
    static {
        NativeLoader.classesInit0(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    @Override // br.com.rory.electro.database.c
    public native void onUpgrade(SQLiteDatabase sQLiteDatabase);
}
