package br.com.rory.electro.socket.io.action;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import org.json.JSONObject;

/* loaded from: classes.dex */
public interface Action {
    void execute(Context context, d dVar, JSONObject jSONObject);
}
