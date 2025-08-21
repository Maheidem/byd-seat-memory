package io.socket.client;

import io.socket.client.Manager;
import io.socket.client.On;
import io.socket.emitter.Emitter;
import io.socket.parser.Packet;
import io.socket.thread.EventThread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class Socket extends Emitter {
    public static final String EVENT_CONNECT = "connect";
    public static final String EVENT_CONNECT_ERROR = "connect_error";
    public static final String EVENT_DISCONNECT = "disconnect";
    static final String EVENT_MESSAGE = "message";
    private Map<String, String> auth;
    private volatile boolean connected;
    String id;
    private int ids;

    /* renamed from: io, reason: collision with root package name */
    private Manager f209io;
    private String nsp;
    private Queue<On.Handle> subs;
    private static final Logger logger = Logger.getLogger(Socket.class.getName());
    protected static Map<String, Integer> RESERVED_EVENTS = new HashMap<String, Integer>() { // from class: io.socket.client.Socket.1
        {
            put(Socket.EVENT_CONNECT, 1);
            put(Socket.EVENT_CONNECT_ERROR, 1);
            put(Socket.EVENT_DISCONNECT, 1);
            put("disconnecting", 1);
            put("newListener", 1);
            put("removeListener", 1);
        }
    };
    private Map<Integer, Ack> acks = new HashMap();
    private final Queue<List<Object>> receiveBuffer = new ConcurrentLinkedQueue();
    private final Queue<Packet<JSONArray>> sendBuffer = new ConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<Emitter.Listener> onAnyIncomingListeners = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Emitter.Listener> onAnyOutgoingListeners = new ConcurrentLinkedQueue<>();

    public Socket(Manager manager, String str, Manager.Options options) {
        this.f209io = manager;
        this.nsp = str;
        if (options != null) {
            this.auth = options.auth;
        }
    }

    static /* synthetic */ int access$708(Socket socket) {
        int i = socket.ids;
        socket.ids = i + 1;
        return i;
    }

    private Ack ack(final int i) {
        final boolean[] zArr = {false};
        return new Ack() { // from class: io.socket.client.Socket.7
            @Override // io.socket.client.Ack
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (zArr[0]) {
                            return;
                        }
                        zArr[0] = true;
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Socket.logger.fine(String.format("sending ack %s", objArr.length != 0 ? objArr : null));
                        }
                        JSONArray jSONArray = new JSONArray();
                        for (Object obj : objArr) {
                            jSONArray.put(obj);
                        }
                        Packet packet = new Packet(3, jSONArray);
                        packet.id = i;
                        this.packet(packet);
                    }
                });
            }
        };
    }

    private void clearAcks() {
        for (Ack ack : this.acks.values()) {
            if (ack instanceof AckWithTimeout) {
                ((AckWithTimeout) ack).onTimeout();
            }
        }
        this.acks.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroy() {
        if (this.subs != null) {
            Iterator<On.Handle> it = this.subs.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.subs = null;
        }
        this.f209io.destroy();
    }

    private void emitBuffered() {
        while (true) {
            List<Object> listPoll = this.receiveBuffer.poll();
            if (listPoll == null) {
                break;
            } else {
                super.emit((String) listPoll.get(0), listPoll.toArray());
            }
        }
        this.receiveBuffer.clear();
        while (true) {
            Packet<JSONArray> packetPoll = this.sendBuffer.poll();
            if (packetPoll == null) {
                this.sendBuffer.clear();
                return;
            }
            packet(packetPoll);
        }
    }

    private void onack(Packet<JSONArray> packet) {
        Ack ackRemove = this.acks.remove(Integer.valueOf(packet.id));
        if (ackRemove != null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("calling ack %s with %s", Integer.valueOf(packet.id), packet.data));
            }
            ackRemove.call(toArray(packet.data));
        } else if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("bad ack %s", Integer.valueOf(packet.id)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onclose(String str) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("close (%s)", str));
        }
        this.connected = false;
        this.id = null;
        super.emit(EVENT_DISCONNECT, str);
        clearAcks();
    }

    private void onconnect(String str) {
        this.connected = true;
        this.id = str;
        emitBuffered();
        super.emit(EVENT_CONNECT, new Object[0]);
    }

    private void ondisconnect() {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("server disconnect (%s)", this.nsp));
        }
        destroy();
        onclose("io server disconnect");
    }

    private void onevent(Packet<JSONArray> packet) {
        ArrayList arrayList = new ArrayList(Arrays.asList(toArray(packet.data)));
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("emitting event %s", arrayList));
        }
        if (packet.id >= 0) {
            logger.fine("attaching ack callback to event");
            arrayList.add(ack(packet.id));
        }
        if (!this.connected) {
            this.receiveBuffer.add(arrayList);
            return;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (!this.onAnyIncomingListeners.isEmpty()) {
            Object[] array = arrayList.toArray();
            Iterator<Emitter.Listener> it = this.onAnyIncomingListeners.iterator();
            while (it.hasNext()) {
                it.next().call(array);
            }
        }
        super.emit(arrayList.remove(0).toString(), arrayList.toArray());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void onopen() {
        logger.fine("transport is open - connecting");
        packet(this.auth != null ? new Packet(0, new JSONObject((Map) this.auth)) : new Packet(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void onpacket(Packet<?> packet) {
        if (this.nsp.equals(packet.nsp)) {
            switch (packet.type) {
                case 0:
                    if (!(packet.data instanceof JSONObject) || !((JSONObject) packet.data).has("sid")) {
                        super.emit(EVENT_CONNECT_ERROR, new SocketIOException("It seems you are trying to reach a Socket.IO server in v2.x with a v3.x client, which is not possible"));
                        break;
                    } else {
                        try {
                            onconnect(((JSONObject) packet.data).getString("sid"));
                            break;
                        } catch (JSONException unused) {
                            return;
                        }
                    }
                    break;
                case 1:
                    ondisconnect();
                    break;
                case 2:
                case 5:
                    onevent(packet);
                    break;
                case 3:
                case 6:
                    onack(packet);
                    break;
                case 4:
                    destroy();
                    super.emit(EVENT_CONNECT_ERROR, packet.data);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void packet(Packet packet) {
        if (packet.type == 2 && !this.onAnyOutgoingListeners.isEmpty()) {
            Object[] array = toArray((JSONArray) packet.data);
            Iterator<Emitter.Listener> it = this.onAnyOutgoingListeners.iterator();
            while (it.hasNext()) {
                it.next().call(array);
            }
        }
        packet.nsp = this.nsp;
        this.f209io.packet(packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subEvents() {
        if (this.subs != null) {
            return;
        }
        final Manager manager = this.f209io;
        this.subs = new LinkedList<On.Handle>() { // from class: io.socket.client.Socket.2
            {
                add(On.on(manager, "open", new Emitter.Listener() { // from class: io.socket.client.Socket.2.1
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onopen();
                    }
                }));
                add(On.on(manager, "packet", new Emitter.Listener() { // from class: io.socket.client.Socket.2.2
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onpacket((Packet) objArr[0]);
                    }
                }));
                add(On.on(manager, "error", new Emitter.Listener() { // from class: io.socket.client.Socket.2.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        if (Socket.this.connected) {
                            return;
                        }
                        Socket.super.emit(Socket.EVENT_CONNECT_ERROR, objArr[0]);
                    }
                }));
                add(On.on(manager, "close", new Emitter.Listener() { // from class: io.socket.client.Socket.2.4
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onclose(objArr.length > 0 ? (String) objArr[0] : null);
                    }
                }));
            }
        };
    }

    private static Object[] toArray(JSONArray jSONArray) {
        Object obj;
        int length = jSONArray.length();
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            Object obj2 = null;
            try {
                obj = jSONArray.get(i);
            } catch (JSONException e) {
                logger.log(Level.WARNING, "An error occured while retrieving data from JSONArray", (Throwable) e);
                obj = null;
            }
            if (!JSONObject.NULL.equals(obj)) {
                obj2 = obj;
            }
            objArr[i] = obj2;
        }
        return objArr;
    }

    public Socket close() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.8
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected) {
                    if (Socket.logger.isLoggable(Level.FINE)) {
                        Socket.logger.fine(String.format("performing disconnect (%s)", Socket.this.nsp));
                    }
                    Socket.this.packet(new Packet(1));
                }
                Socket.this.destroy();
                if (Socket.this.connected) {
                    Socket.this.onclose("io client disconnect");
                }
            }
        });
        return this;
    }

    public Socket connect() {
        return open();
    }

    public boolean connected() {
        return this.connected;
    }

    public Socket disconnect() {
        return close();
    }

    @Override // io.socket.emitter.Emitter
    public Emitter emit(final String str, final Object... objArr) {
        if (!RESERVED_EVENTS.containsKey(str)) {
            EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.5
                @Override // java.lang.Runnable
                public void run() {
                    Object[] objArr2;
                    Ack ack;
                    int length = objArr.length - 1;
                    if (objArr.length <= 0 || !(objArr[length] instanceof Ack)) {
                        objArr2 = objArr;
                        ack = null;
                    } else {
                        objArr2 = new Object[length];
                        for (int i = 0; i < length; i++) {
                            objArr2[i] = objArr[i];
                        }
                        ack = (Ack) objArr[length];
                    }
                    Socket.this.emit(str, objArr2, ack);
                }
            });
            return this;
        }
        throw new RuntimeException("'" + str + "' is a reserved event name");
    }

    public Emitter emit(final String str, final Object[] objArr, final Ack ack) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.6
            @Override // java.lang.Runnable
            public void run() {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(str);
                if (objArr != null) {
                    for (Object obj : objArr) {
                        jSONArray.put(obj);
                    }
                }
                Packet packet = new Packet(2, jSONArray);
                if (ack != null) {
                    final int i = Socket.this.ids;
                    Socket.logger.fine(String.format("emitting packet with ack id %d", Integer.valueOf(i)));
                    if (ack instanceof AckWithTimeout) {
                        final AckWithTimeout ackWithTimeout = (AckWithTimeout) ack;
                        ackWithTimeout.schedule(new TimerTask() { // from class: io.socket.client.Socket.6.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                Socket.this.acks.remove(Integer.valueOf(i));
                                Iterator it = Socket.this.sendBuffer.iterator();
                                while (it.hasNext()) {
                                    if (((Packet) it.next()).id == i) {
                                        it.remove();
                                    }
                                }
                                ackWithTimeout.onTimeout();
                            }
                        });
                    }
                    Socket.this.acks.put(Integer.valueOf(i), ack);
                    packet.id = Socket.access$708(Socket.this);
                }
                if (Socket.this.connected) {
                    Socket.this.packet(packet);
                } else {
                    Socket.this.sendBuffer.add(packet);
                }
            }
        });
        return this;
    }

    public String id() {
        return this.id;
    }

    public Manager io() {
        return this.f209io;
    }

    public boolean isActive() {
        return this.subs != null;
    }

    public Socket offAnyIncoming() {
        this.onAnyIncomingListeners.clear();
        return this;
    }

    public Socket offAnyIncoming(Emitter.Listener listener) {
        Iterator<Emitter.Listener> it = this.onAnyIncomingListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next() == listener) {
                it.remove();
                break;
            }
        }
        return this;
    }

    public Socket offAnyOutgoing() {
        this.onAnyOutgoingListeners.clear();
        return this;
    }

    public Socket offAnyOutgoing(Emitter.Listener listener) {
        Iterator<Emitter.Listener> it = this.onAnyOutgoingListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next() == listener) {
                it.remove();
                break;
            }
        }
        return this;
    }

    public Socket onAnyIncoming(Emitter.Listener listener) {
        this.onAnyIncomingListeners.add(listener);
        return this;
    }

    public Socket onAnyOutgoing(Emitter.Listener listener) {
        this.onAnyOutgoingListeners.add(listener);
        return this;
    }

    public Socket open() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.3
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected || Socket.this.f209io.isReconnecting()) {
                    return;
                }
                Socket.this.subEvents();
                Socket.this.f209io.open();
                if (Manager.ReadyState.OPEN == Socket.this.f209io.readyState) {
                    Socket.this.onopen();
                }
            }
        });
        return this;
    }

    public Socket send(final Object... objArr) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.4
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.emit("message", objArr);
            }
        });
        return this;
    }
}
