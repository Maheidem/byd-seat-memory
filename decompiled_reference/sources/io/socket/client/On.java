package io.socket.client;

import io.socket.emitter.Emitter;

/* loaded from: classes.dex */
public class On {

    public interface Handle {
        void destroy();
    }

    private On() {
    }

    public static Handle on(final Emitter emitter, final String str, final Emitter.Listener listener) {
        emitter.on(str, listener);
        return new Handle() { // from class: io.socket.client.On.1
            @Override // io.socket.client.On.Handle
            public void destroy() {
                emitter.off(str, listener);
            }
        };
    }
}
