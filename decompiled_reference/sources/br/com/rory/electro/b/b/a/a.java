package br.com.rory.electro.b.b.a;

import android.content.Context;
import br.com.rory.electro.b.b.b;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class a implements b<String> {
    static {
        NativeLoader.classesInit0(206);
    }

    @Override // br.com.rory.electro.b.b.b
    public native String a();

    public native String a(br.com.rory.electro.b.b.a aVar);

    @Override // br.com.rory.electro.b.b.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
