package br.com.rory.electro.receiver.push.action;

import android.content.Context;
import com.rory.electro.NativeLoader;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class RefreshUserList implements PushAction {
    static {
        NativeLoader.classesInit0(242);
    }

    @Override // br.com.rory.electro.receiver.push.action.PushAction
    public native void execute(Context context, JSONObject jSONObject);
}
