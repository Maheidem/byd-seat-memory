package io.socket.emitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class Emitter {
    private ConcurrentMap<String, ConcurrentLinkedQueue<Listener>> callbacks = new ConcurrentHashMap();

    public interface Listener {
        void call(Object... objArr);
    }

    private class OnceListener implements Listener {
        public final String event;
        public final Listener fn;

        public OnceListener(String str, Listener listener) {
            this.event = str;
            this.fn = listener;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            Emitter.this.off(this.event, this);
            this.fn.call(objArr);
        }
    }

    private static boolean sameAs(Listener listener, Listener listener2) {
        if (listener.equals(listener2)) {
            return true;
        }
        if (listener2 instanceof OnceListener) {
            return listener.equals(((OnceListener) listener2).fn);
        }
        return false;
    }

    public Emitter emit(String str, Object... objArr) {
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueue = this.callbacks.get(str);
        if (concurrentLinkedQueue != null) {
            Iterator<Listener> it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                it.next().call(objArr);
            }
        }
        return this;
    }

    public boolean hasListeners(String str) {
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueue = this.callbacks.get(str);
        return (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) ? false : true;
    }

    public List<Listener> listeners(String str) {
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueue = this.callbacks.get(str);
        return concurrentLinkedQueue != null ? new ArrayList(concurrentLinkedQueue) : new ArrayList(0);
    }

    public Emitter off() {
        this.callbacks.clear();
        return this;
    }

    public Emitter off(String str) {
        this.callbacks.remove(str);
        return this;
    }

    public Emitter off(String str, Listener listener) {
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueue = this.callbacks.get(str);
        if (concurrentLinkedQueue != null) {
            Iterator<Listener> it = concurrentLinkedQueue.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (sameAs(listener, it.next())) {
                    it.remove();
                    break;
                }
            }
        }
        return this;
    }

    public Emitter on(String str, Listener listener) {
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueuePutIfAbsent;
        ConcurrentLinkedQueue<Listener> concurrentLinkedQueue = this.callbacks.get(str);
        if (concurrentLinkedQueue == null && (concurrentLinkedQueuePutIfAbsent = this.callbacks.putIfAbsent(str, (concurrentLinkedQueue = new ConcurrentLinkedQueue<>()))) != null) {
            concurrentLinkedQueue = concurrentLinkedQueuePutIfAbsent;
        }
        concurrentLinkedQueue.add(listener);
        return this;
    }

    public Emitter once(String str, Listener listener) {
        on(str, new OnceListener(str, listener));
        return this;
    }
}
