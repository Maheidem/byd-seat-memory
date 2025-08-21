package br.com.rory.electro.socket.io.action;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TripPoints implements Action {
    private static final String TAG = "TripPoints";
    private Map<String, Object> lastAddedValueMap;

    static {
        NativeLoader.classesInit0(119);
    }

    private native void diffPut(JSONObject jSONObject, String str, Object obj);

    private native void sendPartialResponse(d dVar, long j, long j2, JSONArray jSONArray);

    @Override // br.com.rory.electro.socket.io.action.Action
    public native void execute(Context context, d dVar, JSONObject jSONObject);
}
