package okhttp3;

import a.f;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public interface WebSocket {

    public interface Factory {
        WebSocket newWebSocket(Request request, WebSocketListener webSocketListener);
    }

    void cancel();

    boolean close(int i, @Nullable String str);

    long queueSize();

    Request request();

    boolean send(f fVar);

    boolean send(String str);
}
