package io.socket.client;

import android.support.v7.widget.ActivityChooserView;
import io.socket.backo.Backoff;
import io.socket.client.On;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket;
import io.socket.parser.DecodingException;
import io.socket.parser.IOParser;
import io.socket.parser.Packet;
import io.socket.parser.Parser;
import io.socket.thread.EventThread;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.WebSocket;

/* loaded from: classes.dex */
public class Manager extends Emitter {
    public static final String EVENT_CLOSE = "close";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_OPEN = "open";
    public static final String EVENT_PACKET = "packet";
    public static final String EVENT_RECONNECT = "reconnect";
    public static final String EVENT_RECONNECT_ATTEMPT = "reconnect_attempt";
    public static final String EVENT_RECONNECT_ERROR = "reconnect_error";
    public static final String EVENT_RECONNECT_FAILED = "reconnect_failed";
    public static final String EVENT_TRANSPORT = "transport";
    static Call.Factory defaultCallFactory;
    static WebSocket.Factory defaultWebSocketFactory;
    private static final Logger logger = Logger.getLogger(Manager.class.getName());
    private double _randomizationFactor;
    private boolean _reconnection;
    private int _reconnectionAttempts;
    private long _reconnectionDelay;
    private long _reconnectionDelayMax;
    private long _timeout;
    private Backoff backoff;
    private Parser.Decoder decoder;
    private Parser.Encoder encoder;
    private boolean encoding;
    io.socket.engineio.client.Socket engine;
    ConcurrentHashMap<String, Socket> nsps;
    private Options opts;
    private List<Packet> packetBuffer;
    ReadyState readyState;
    private boolean reconnecting;
    private boolean skipReconnect;
    private Queue<On.Handle> subs;
    private URI uri;

    /* renamed from: io.socket.client.Manager$7, reason: invalid class name */
    class AnonymousClass7 extends TimerTask {
        final /* synthetic */ Manager val$self;

        AnonymousClass7(Manager manager) {
            this.val$self = manager;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            EventThread.exec(new Runnable() { // from class: io.socket.client.Manager.7.1
                @Override // java.lang.Runnable
                public void run() {
                    if (AnonymousClass7.this.val$self.skipReconnect) {
                        return;
                    }
                    Manager.logger.fine("attempting reconnect");
                    AnonymousClass7.this.val$self.emit(Manager.EVENT_RECONNECT_ATTEMPT, Integer.valueOf(AnonymousClass7.this.val$self.backoff.getAttempts()));
                    if (AnonymousClass7.this.val$self.skipReconnect) {
                        return;
                    }
                    AnonymousClass7.this.val$self.open(new OpenCallback() { // from class: io.socket.client.Manager.7.1.1
                        @Override // io.socket.client.Manager.OpenCallback
                        public void call(Exception exc) {
                            if (exc == null) {
                                Manager.logger.fine("reconnect success");
                                AnonymousClass7.this.val$self.onreconnect();
                            } else {
                                Manager.logger.fine("reconnect attempt error");
                                AnonymousClass7.this.val$self.reconnecting = false;
                                AnonymousClass7.this.val$self.reconnect();
                                AnonymousClass7.this.val$self.emit(Manager.EVENT_RECONNECT_ERROR, exc);
                            }
                        }
                    });
                }
            });
        }
    }

    private static class Engine extends io.socket.engineio.client.Socket {
        Engine(URI uri, Socket.Options options) {
            super(uri, options);
        }
    }

    public interface OpenCallback {
        void call(Exception exc);
    }

    public static class Options extends Socket.Options {
        public Map<String, String> auth;
        public Parser.Decoder decoder;
        public Parser.Encoder encoder;
        public double randomizationFactor;
        public int reconnectionAttempts;
        public long reconnectionDelay;
        public long reconnectionDelayMax;
        public boolean reconnection = true;
        public long timeout = 20000;
    }

    enum ReadyState {
        CLOSED,
        OPENING,
        OPEN
    }

    public Manager() {
        this(null, null);
    }

    public Manager(Options options) {
        this(null, options);
    }

    public Manager(URI uri) {
        this(uri, null);
    }

    public Manager(URI uri, Options options) {
        options = options == null ? new Options() : options;
        if (options.path == null) {
            options.path = "/socket.io";
        }
        if (options.webSocketFactory == null) {
            options.webSocketFactory = defaultWebSocketFactory;
        }
        if (options.callFactory == null) {
            options.callFactory = defaultCallFactory;
        }
        this.opts = options;
        this.nsps = new ConcurrentHashMap<>();
        this.subs = new LinkedList();
        reconnection(options.reconnection);
        reconnectionAttempts(options.reconnectionAttempts != 0 ? options.reconnectionAttempts : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        reconnectionDelay(options.reconnectionDelay != 0 ? options.reconnectionDelay : 1000L);
        reconnectionDelayMax(options.reconnectionDelayMax != 0 ? options.reconnectionDelayMax : 5000L);
        randomizationFactor(options.randomizationFactor != 0.0d ? options.randomizationFactor : 0.5d);
        this.backoff = new Backoff().setMin(reconnectionDelay()).setMax(reconnectionDelayMax()).setJitter(randomizationFactor());
        timeout(options.timeout);
        this.readyState = ReadyState.CLOSED;
        this.uri = uri;
        this.encoding = false;
        this.packetBuffer = new ArrayList();
        this.encoder = options.encoder != null ? options.encoder : new IOParser.Encoder();
        this.decoder = options.decoder != null ? options.decoder : new IOParser.Decoder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanup() {
        logger.fine("cleanup");
        while (true) {
            On.Handle handlePoll = this.subs.poll();
            if (handlePoll == null) {
                this.decoder.onDecoded(null);
                this.packetBuffer.clear();
                this.encoding = false;
                this.decoder.destroy();
                return;
            }
            handlePoll.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeReconnectOnOpen() {
        if (!this.reconnecting && this._reconnection && this.backoff.getAttempts() == 0) {
            reconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onclose(String str) {
        logger.fine("onclose");
        cleanup();
        this.backoff.reset();
        this.readyState = ReadyState.CLOSED;
        emit("close", str);
        if (!this._reconnection || this.skipReconnect) {
            return;
        }
        reconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ondecoded(Packet packet) {
        emit("packet", packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onerror(Exception exc) {
        logger.log(Level.FINE, "error", (Throwable) exc);
        emit("error", exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onopen() {
        logger.fine("open");
        cleanup();
        this.readyState = ReadyState.OPEN;
        emit("open", new Object[0]);
        io.socket.engineio.client.Socket socket = this.engine;
        this.subs.add(On.on(socket, "data", new Emitter.Listener() { // from class: io.socket.client.Manager.2
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Object obj = objArr[0];
                try {
                    if (obj instanceof String) {
                        Manager.this.decoder.add((String) obj);
                    } else if (obj instanceof byte[]) {
                        Manager.this.decoder.add((byte[]) obj);
                    }
                } catch (DecodingException e) {
                    Manager.logger.fine("error while decoding the packet: " + e.getMessage());
                }
            }
        }));
        this.subs.add(On.on(socket, "error", new Emitter.Listener() { // from class: io.socket.client.Manager.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Manager.this.onerror((Exception) objArr[0]);
            }
        }));
        this.subs.add(On.on(socket, "close", new Emitter.Listener() { // from class: io.socket.client.Manager.4
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Manager.this.onclose((String) objArr[0]);
            }
        }));
        this.decoder.onDecoded(new Parser.Decoder.Callback() { // from class: io.socket.client.Manager.5
            @Override // io.socket.parser.Parser.Decoder.Callback
            public void call(Packet packet) {
                Manager.this.ondecoded(packet);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onreconnect() {
        int attempts = this.backoff.getAttempts();
        this.reconnecting = false;
        this.backoff.reset();
        emit(EVENT_RECONNECT, Integer.valueOf(attempts));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processPacketQueue() {
        if (this.packetBuffer.isEmpty() || this.encoding) {
            return;
        }
        packet(this.packetBuffer.remove(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnect() {
        if (this.reconnecting || this.skipReconnect) {
            return;
        }
        if (this.backoff.getAttempts() >= this._reconnectionAttempts) {
            logger.fine("reconnect failed");
            this.backoff.reset();
            emit(EVENT_RECONNECT_FAILED, new Object[0]);
            this.reconnecting = false;
            return;
        }
        long jDuration = this.backoff.duration();
        logger.fine(String.format("will wait %dms before reconnect attempt", Long.valueOf(jDuration)));
        this.reconnecting = true;
        final Timer timer = new Timer();
        timer.schedule(new AnonymousClass7(this), jDuration);
        this.subs.add(new On.Handle() { // from class: io.socket.client.Manager.8
            @Override // io.socket.client.On.Handle
            public void destroy() {
                timer.cancel();
            }
        });
    }

    void close() {
        logger.fine(Socket.EVENT_DISCONNECT);
        this.skipReconnect = true;
        this.reconnecting = false;
        if (this.readyState != ReadyState.OPEN) {
            cleanup();
        }
        this.backoff.reset();
        this.readyState = ReadyState.CLOSED;
        if (this.engine != null) {
            this.engine.close();
        }
    }

    void destroy() {
        synchronized (this.nsps) {
            Iterator<Socket> it = this.nsps.values().iterator();
            while (it.hasNext()) {
                if (it.next().isActive()) {
                    logger.fine("socket is still active, skipping close");
                    return;
                }
            }
            close();
        }
    }

    public boolean isReconnecting() {
        return this.reconnecting;
    }

    public Manager open() {
        return open(null);
    }

    public Manager open(final OpenCallback openCallback) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Manager.1
            @Override // java.lang.Runnable
            public void run() {
                if (Manager.logger.isLoggable(Level.FINE)) {
                    Manager.logger.fine(String.format("readyState %s", Manager.this.readyState));
                }
                if (Manager.this.readyState == ReadyState.OPEN || Manager.this.readyState == ReadyState.OPENING) {
                    return;
                }
                if (Manager.logger.isLoggable(Level.FINE)) {
                    Manager.logger.fine(String.format("opening %s", Manager.this.uri));
                }
                Manager.this.engine = new Engine(Manager.this.uri, Manager.this.opts);
                final io.socket.engineio.client.Socket socket = Manager.this.engine;
                final Manager manager = Manager.this;
                Manager.this.readyState = ReadyState.OPENING;
                Manager.this.skipReconnect = false;
                socket.on("transport", new Emitter.Listener() { // from class: io.socket.client.Manager.1.1
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        manager.emit("transport", objArr);
                    }
                });
                final On.Handle handleOn = On.on(socket, "open", new Emitter.Listener() { // from class: io.socket.client.Manager.1.2
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        manager.onopen();
                        if (openCallback != null) {
                            openCallback.call(null);
                        }
                    }
                });
                On.Handle handleOn2 = On.on(socket, "error", new Emitter.Listener() { // from class: io.socket.client.Manager.1.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Object obj = objArr.length > 0 ? objArr[0] : null;
                        Manager.logger.fine(Socket.EVENT_CONNECT_ERROR);
                        manager.cleanup();
                        manager.readyState = ReadyState.CLOSED;
                        manager.emit("error", obj);
                        if (openCallback != null) {
                            openCallback.call(new SocketIOException("Connection error", obj instanceof Exception ? (Exception) obj : null));
                        } else {
                            manager.maybeReconnectOnOpen();
                        }
                    }
                });
                final long j = Manager.this._timeout;
                final Runnable runnable = new Runnable() { // from class: io.socket.client.Manager.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Manager.logger.fine(String.format("connect attempt timed out after %d", Long.valueOf(j)));
                        handleOn.destroy();
                        socket.close();
                        socket.emit("error", new SocketIOException("timeout"));
                    }
                };
                if (j == 0) {
                    EventThread.exec(runnable);
                    return;
                }
                if (Manager.this._timeout > 0) {
                    Manager.logger.fine(String.format("connection attempt will timeout after %d", Long.valueOf(j)));
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() { // from class: io.socket.client.Manager.1.5
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            EventThread.exec(runnable);
                        }
                    }, j);
                    Manager.this.subs.add(new On.Handle() { // from class: io.socket.client.Manager.1.6
                        @Override // io.socket.client.On.Handle
                        public void destroy() {
                            timer.cancel();
                        }
                    });
                }
                Manager.this.subs.add(handleOn);
                Manager.this.subs.add(handleOn2);
                Manager.this.engine.open();
            }
        });
        return this;
    }

    void packet(Packet packet) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("writing packet %s", packet));
        }
        if (this.encoding) {
            this.packetBuffer.add(packet);
        } else {
            this.encoding = true;
            this.encoder.encode(packet, new Parser.Encoder.Callback() { // from class: io.socket.client.Manager.6
                @Override // io.socket.parser.Parser.Encoder.Callback
                public void call(Object[] objArr) {
                    for (Object obj : objArr) {
                        if (obj instanceof String) {
                            this.engine.write((String) obj);
                        } else if (obj instanceof byte[]) {
                            this.engine.write((byte[]) obj);
                        }
                    }
                    this.encoding = false;
                    this.processPacketQueue();
                }
            });
        }
    }

    public final double randomizationFactor() {
        return this._randomizationFactor;
    }

    public Manager randomizationFactor(double d) {
        this._randomizationFactor = d;
        if (this.backoff != null) {
            this.backoff.setJitter(d);
        }
        return this;
    }

    public Manager reconnection(boolean z) {
        this._reconnection = z;
        return this;
    }

    public boolean reconnection() {
        return this._reconnection;
    }

    public int reconnectionAttempts() {
        return this._reconnectionAttempts;
    }

    public Manager reconnectionAttempts(int i) {
        this._reconnectionAttempts = i;
        return this;
    }

    public final long reconnectionDelay() {
        return this._reconnectionDelay;
    }

    public Manager reconnectionDelay(long j) {
        this._reconnectionDelay = j;
        if (this.backoff != null) {
            this.backoff.setMin(j);
        }
        return this;
    }

    public final long reconnectionDelayMax() {
        return this._reconnectionDelayMax;
    }

    public Manager reconnectionDelayMax(long j) {
        this._reconnectionDelayMax = j;
        if (this.backoff != null) {
            this.backoff.setMax(j);
        }
        return this;
    }

    public Socket socket(String str) {
        return socket(str, null);
    }

    public Socket socket(String str, Options options) {
        Socket socket;
        synchronized (this.nsps) {
            socket = this.nsps.get(str);
            if (socket == null) {
                socket = new Socket(this, str, options);
                this.nsps.put(str, socket);
            }
        }
        return socket;
    }

    public long timeout() {
        return this._timeout;
    }

    public Manager timeout(long j) {
        this._timeout = j;
        return this;
    }
}
