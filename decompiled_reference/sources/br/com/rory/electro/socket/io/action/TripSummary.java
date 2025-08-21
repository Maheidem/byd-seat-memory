package br.com.rory.electro.socket.io.action;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TripSummary implements Action {
    private static final String TAG = "TripSummary";

    static {
        NativeLoader.classesInit0(117);
    }

    private native void sendPartialResponse(d dVar, JSONArray jSONArray);

    @Override // br.com.rory.electro.socket.io.action.Action
    public native void execute(Context context, d dVar, JSONObject jSONObject);
}
