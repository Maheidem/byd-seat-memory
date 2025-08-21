package br.com.rory.electro.socket.io.action;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class LoadSeatMemory implements Action {

    /* renamed from: br.com.rory.electro.socket.io.action.LoadSeatMemory$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ int val$position;
        final /* synthetic */ d val$socketIOManager;
        final /* synthetic */ String val$thisClassName;

        static {
            NativeLoader.classesInit0(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        }

        AnonymousClass1(String str, Context context, int i, d dVar) {
            this.val$thisClassName = str;
            this.val$context = context;
            this.val$position = i;
            this.val$socketIOManager = dVar;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(140);
    }

    @Override // br.com.rory.electro.socket.io.action.Action
    public native void execute(Context context, d dVar, JSONObject jSONObject);
}
