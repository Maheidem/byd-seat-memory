package br.com.rory.electro.receiver.push.action;

import android.content.Context;
import org.json.JSONObject;

/* loaded from: classes.dex */
public interface PushAction {
    void execute(Context context, JSONObject jSONObject);
}
