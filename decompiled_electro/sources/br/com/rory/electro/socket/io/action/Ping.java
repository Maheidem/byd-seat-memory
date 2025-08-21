package br.com.rory.electro.socket.io.action;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class Ping implements Action {
    static {
        NativeLoader.classesInit0(238);
    }

    @Override // br.com.rory.electro.socket.io.action.Action
    public native void execute(Context context, d dVar, JSONObject jSONObject);
}
