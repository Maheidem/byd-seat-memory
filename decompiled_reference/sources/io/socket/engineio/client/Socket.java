package io.socket.engineio.client;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.client.transports.Polling;
import io.socket.engineio.client.transports.PollingXHR;
import io.socket.engineio.parser.Packet;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import org.json.JSONException;

/* loaded from: classes.dex */
public class Socket extends Emitter {
    public static final String EVENT_CLOSE = "close";
    public static final String EVENT_DATA = "data";
    public static final String EVENT_DRAIN = "drain";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_FLUSH = "flush";
    public static final String EVENT_HANDSHAKE = "handshake";
    public static final String EVENT_HEARTBEAT = "heartbeat";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_OPEN = "open";
    public static final String EVENT_PACKET = "packet";
    public static final String EVENT_PACKET_CREATE = "packetCreate";
    public static final String EVENT_PING = "ping";
    public static final String EVENT_PONG = "pong";
    public static final String EVENT_TRANSPORT = "transport";
    public static final String EVENT_UPGRADE = "upgrade";
    public static final String EVENT_UPGRADE_ERROR = "upgradeError";
    public static final String EVENT_UPGRADING = "upgrading";
    private static final String PROBE_ERROR = "probe error";
    public static final int PROTOCOL = 4;
    private static Call.Factory defaultCallFactory;
    private static OkHttpClient defaultOkHttpClient;
    private static WebSocket.Factory defaultWebSocketFactory;
    private static boolean priorWebsocketSuccess;
    private Call.Factory callFactory;
    private final Map<String, List<String>> extraHeaders;
    private ScheduledExecutorService heartbeatScheduler;
    String hostname;
    private String id;
    private final Emitter.Listener onHeartbeatAsListener;
    private String path;
    private long pingInterval;
    private long pingTimeout;
    private Future pingTimeoutTimer;
    private int policyPort;
    int port;
    private int prevBufferLen;
    private Map<String, String> query;
    private ReadyState readyState;
    private boolean rememberUpgrade;
    private boolean secure;
    private String timestampParam;
    private boolean timestampRequests;
    Transport transport;
    private Map<String, Transport.Options> transportOptions;
    private List<String> transports;
    private boolean upgrade;
    private List<String> upgrades;
    private boolean upgrading;
    private WebSocket.Factory webSocketFactory;
    LinkedList<Packet> writeBuffer;
    private static final Logger logger = Logger.getLogger(Socket.class.getName());
    private static final AtomicInteger HEARTBEAT_THREAD_COUNTER = new AtomicInteger();

    /* renamed from: io.socket.engineio.client.Socket$7, reason: invalid class name */
    class AnonymousClass7 implements Emitter.Listener {
        final /* synthetic */ Runnable[] val$cleanup;
        final /* synthetic */ boolean[] val$failed;
        final /* synthetic */ String val$name;
        final /* synthetic */ Socket val$self;
        final /* synthetic */ Transport[] val$transport;

        AnonymousClass7(boolean[] zArr, String str, Transport[] transportArr, Socket socket, Runnable[] runnableArr) {
            this.val$failed = zArr;
            this.val$name = str;
            this.val$transport = transportArr;
            this.val$self = socket;
            this.val$cleanup = runnableArr;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            if (this.val$failed[0]) {
                return;
            }
            if (Socket.logger.isLoggable(Level.FINE)) {
                Socket.logger.fine(String.format("probe transport '%s' opened", this.val$name));
            }
            this.val$transport[0].send(new Packet[]{new Packet("ping", "probe")});
            this.val$transport[0].once("packet", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.7.1
                @Override // io.socket.emitter.Emitter.Listener
                public void call(Object... objArr2) {
                    if (AnonymousClass7.this.val$failed[0]) {
                        return;
                    }
                    Packet packet = (Packet) objArr2[0];
                    if (!"pong".equals(packet.type) || !"probe".equals(packet.data)) {
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Socket.logger.fine(String.format("probe transport '%s' failed", AnonymousClass7.this.val$name));
                        }
                        EngineIOException engineIOException = new EngineIOException(Socket.PROBE_ERROR);
                        engineIOException.transport = AnonymousClass7.this.val$transport[0].name;
                        AnonymousClass7.this.val$self.emit(Socket.EVENT_UPGRADE_ERROR, engineIOException);
                        return;
                    }
                    if (Socket.logger.isLoggable(Level.FINE)) {
                        Socket.logger.fine(String.format("probe transport '%s' pong", AnonymousClass7.this.val$name));
                    }
                    AnonymousClass7.this.val$self.upgrading = true;
                    AnonymousClass7.this.val$self.emit(Socket.EVENT_UPGRADING, AnonymousClass7.this.val$transport[0]);
                    if (AnonymousClass7.this.val$transport[0] == null) {
                        return;
                    }
                    boolean unused = Socket.priorWebsocketSuccess = io.socket.engineio.client.transports.WebSocket.NAME.equals(AnonymousClass7.this.val$transport[0].name);
                    if (Socket.logger.isLoggable(Level.FINE)) {
                        Socket.logger.fine(String.format("pausing current transport '%s'", AnonymousClass7.this.val$self.transport.name));
                    }
                    ((Polling) AnonymousClass7.this.val$self.transport).pause(new Runnable() { // from class: io.socket.engineio.client.Socket.7.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AnonymousClass7.this.val$failed[0] || ReadyState.CLOSED == AnonymousClass7.this.val$self.readyState) {
                                return;
                            }
                            Socket.logger.fine("changing transport and sending upgrade packet");
                            AnonymousClass7.this.val$cleanup[0].run();
                            AnonymousClass7.this.val$self.setTransport(AnonymousClass7.this.val$transport[0]);
                            AnonymousClass7.this.val$transport[0].send(new Packet[]{new Packet("upgrade")});
                            AnonymousClass7.this.val$self.emit("upgrade", AnonymousClass7.this.val$transport[0]);
                            AnonymousClass7.this.val$transport[0] = null;
                            AnonymousClass7.this.val$self.upgrading = false;
                            AnonymousClass7.this.val$self.flush();
                        }
                    });
                }
            });
        }
    }

    public static class Options extends Transport.Options {
        public String host;
        public String query;
        public boolean rememberUpgrade;
        public Map<String, Transport.Options> transportOptions;
        public String[] transports;
        public boolean upgrade = true;

        /* JADX INFO: Access modifiers changed from: private */
        public static Options fromURI(URI uri, Options options) {
            if (options == null) {
                options = new Options();
            }
            options.host = uri.getHost();
            options.secure = "https".equals(uri.getScheme()) || "wss".equals(uri.getScheme());
            options.port = uri.getPort();
            String rawQuery = uri.getRawQuery();
            if (rawQuery != null) {
                options.query = rawQuery;
            }
            return options;
        }
    }

    private enum ReadyState {
        OPENING,
        OPEN,
        CLOSING,
        CLOSED;

        @Override // java.lang.Enum
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public Socket() {
        this(new Options());
    }

    public Socket(Options options) {
        this.writeBuffer = new LinkedList<>();
        this.onHeartbeatAsListener = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.1
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Socket.this.onHeartbeat();
            }
        };
        if (options.host != null) {
            String strSubstring = options.host;
            if (strSubstring.split(":").length > 2) {
                int iIndexOf = strSubstring.indexOf(91);
                strSubstring = iIndexOf != -1 ? strSubstring.substring(iIndexOf + 1) : strSubstring;
                int iLastIndexOf = strSubstring.lastIndexOf(93);
                if (iLastIndexOf != -1) {
                    strSubstring = strSubstring.substring(0, iLastIndexOf);
                }
            }
            options.hostname = strSubstring;
        }
        this.secure = options.secure;
        if (options.port == -1) {
            options.port = this.secure ? 443 : 80;
        }
        this.hostname = options.hostname != null ? options.hostname : "localhost";
        this.port = options.port;
        this.query = options.query != null ? ParseQS.decode(options.query) : new HashMap<>();
        this.upgrade = options.upgrade;
        StringBuilder sb = new StringBuilder();
        sb.append((options.path != null ? options.path : "/engine.io").replaceAll("/$", ""));
        sb.append("/");
        this.path = sb.toString();
        this.timestampParam = options.timestampParam != null ? options.timestampParam : "t";
        this.timestampRequests = options.timestampRequests;
        this.transports = new ArrayList(Arrays.asList(options.transports != null ? options.transports : new String[]{Polling.NAME, io.socket.engineio.client.transports.WebSocket.NAME}));
        this.transportOptions = options.transportOptions != null ? options.transportOptions : new HashMap<>();
        this.policyPort = options.policyPort != 0 ? options.policyPort : 843;
        this.rememberUpgrade = options.rememberUpgrade;
        this.callFactory = options.callFactory != null ? options.callFactory : defaultCallFactory;
        this.webSocketFactory = options.webSocketFactory != null ? options.webSocketFactory : defaultWebSocketFactory;
        if (this.callFactory == null) {
            this.callFactory = getDefaultOkHttpClient();
        }
        if (this.webSocketFactory == null) {
            this.webSocketFactory = getDefaultOkHttpClient();
        }
        this.extraHeaders = options.extraHeaders;
    }

    public Socket(String str) {
        this(str, (Options) null);
    }

    public Socket(String str, Options options) {
        this(str == null ? null : new URI(str), options);
    }

    public Socket(URI uri) {
        this(uri, (Options) null);
    }

    public Socket(URI uri, Options options) {
        this(uri != null ? Options.fromURI(uri, options) : options);
    }

    private ScheduledExecutorService createHeartbeatScheduler() {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: io.socket.engineio.client.Socket.20
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "engine.io-client.heartbeat-" + Socket.HEARTBEAT_THREAD_COUNTER.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Transport createTransport(String str) {
        Transport pollingXHR;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("creating transport '%s'", str));
        }
        HashMap map = new HashMap(this.query);
        map.put("EIO", String.valueOf(4));
        map.put("transport", str);
        if (this.id != null) {
            map.put("sid", this.id);
        }
        Transport.Options options = this.transportOptions.get(str);
        Transport.Options options2 = new Transport.Options();
        options2.query = map;
        options2.socket = this;
        options2.hostname = options != null ? options.hostname : this.hostname;
        options2.port = options != null ? options.port : this.port;
        options2.secure = options != null ? options.secure : this.secure;
        options2.path = options != null ? options.path : this.path;
        options2.timestampRequests = options != null ? options.timestampRequests : this.timestampRequests;
        options2.timestampParam = options != null ? options.timestampParam : this.timestampParam;
        options2.policyPort = options != null ? options.policyPort : this.policyPort;
        options2.callFactory = options != null ? options.callFactory : this.callFactory;
        options2.webSocketFactory = options != null ? options.webSocketFactory : this.webSocketFactory;
        options2.extraHeaders = this.extraHeaders;
        if (io.socket.engineio.client.transports.WebSocket.NAME.equals(str)) {
            pollingXHR = new io.socket.engineio.client.transports.WebSocket(options2);
        } else {
            if (!Polling.NAME.equals(str)) {
                throw new RuntimeException();
            }
            pollingXHR = new PollingXHR(options2);
        }
        emit("transport", pollingXHR);
        return pollingXHR;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flush() {
        if (this.readyState == ReadyState.CLOSED || !this.transport.writable || this.upgrading || this.writeBuffer.size() == 0) {
            return;
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("flushing %d packets in socket", Integer.valueOf(this.writeBuffer.size())));
        }
        this.prevBufferLen = this.writeBuffer.size();
        this.transport.send((Packet[]) this.writeBuffer.toArray(new Packet[this.writeBuffer.size()]));
        emit(EVENT_FLUSH, new Object[0]);
    }

    private static OkHttpClient getDefaultOkHttpClient() {
        if (defaultOkHttpClient == null) {
            defaultOkHttpClient = new OkHttpClient.Builder().readTimeout(1L, TimeUnit.MINUTES).build();
        }
        return defaultOkHttpClient;
    }

    private ScheduledExecutorService getHeartbeatScheduler() {
        if (this.heartbeatScheduler == null || this.heartbeatScheduler.isShutdown()) {
            this.heartbeatScheduler = createHeartbeatScheduler();
        }
        return this.heartbeatScheduler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClose(String str) {
        onClose(str, null);
    }

    private void onClose(String str, Exception exc) {
        if (ReadyState.OPENING == this.readyState || ReadyState.OPEN == this.readyState || ReadyState.CLOSING == this.readyState) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("socket close with reason: %s", str));
            }
            if (this.pingTimeoutTimer != null) {
                this.pingTimeoutTimer.cancel(false);
            }
            if (this.heartbeatScheduler != null) {
                this.heartbeatScheduler.shutdown();
            }
            this.transport.off("close");
            this.transport.close();
            this.transport.off();
            this.readyState = ReadyState.CLOSED;
            this.id = null;
            emit("close", str, exc);
            this.writeBuffer.clear();
            this.prevBufferLen = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDrain() {
        for (int i = 0; i < this.prevBufferLen; i++) {
            this.writeBuffer.poll();
        }
        this.prevBufferLen = 0;
        if (this.writeBuffer.size() == 0) {
            emit("drain", new Object[0]);
        } else {
            flush();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(Exception exc) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("socket error %s", exc));
        }
        priorWebsocketSuccess = false;
        emit("error", exc);
        onClose("transport error", exc);
    }

    private void onHandshake(HandshakeData handshakeData) {
        emit(EVENT_HANDSHAKE, handshakeData);
        this.id = handshakeData.sid;
        this.transport.query.put("sid", handshakeData.sid);
        this.upgrades = filterUpgrades(Arrays.asList(handshakeData.upgrades));
        this.pingInterval = handshakeData.pingInterval;
        this.pingTimeout = handshakeData.pingTimeout;
        onOpen();
        if (ReadyState.CLOSED == this.readyState) {
            return;
        }
        onHeartbeat();
        off(EVENT_HEARTBEAT, this.onHeartbeatAsListener);
        on(EVENT_HEARTBEAT, this.onHeartbeatAsListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHeartbeat() {
        if (this.pingTimeoutTimer != null) {
            this.pingTimeoutTimer.cancel(false);
        }
        this.pingTimeoutTimer = getHeartbeatScheduler().schedule(new Runnable() { // from class: io.socket.engineio.client.Socket.15
            @Override // java.lang.Runnable
            public void run() {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.15.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (this.readyState == ReadyState.CLOSED) {
                            return;
                        }
                        this.onClose("ping timeout");
                    }
                });
            }
        }, this.pingInterval + this.pingTimeout, TimeUnit.MILLISECONDS);
    }

    private void onOpen() {
        logger.fine("socket open");
        this.readyState = ReadyState.OPEN;
        priorWebsocketSuccess = io.socket.engineio.client.transports.WebSocket.NAME.equals(this.transport.name);
        emit("open", new Object[0]);
        flush();
        if (this.readyState == ReadyState.OPEN && this.upgrade && (this.transport instanceof Polling)) {
            logger.fine("starting upgrade probes");
            Iterator<String> it = this.upgrades.iterator();
            while (it.hasNext()) {
                probe(it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void onPacket(Packet packet) {
        String str;
        Object[] objArr;
        if (this.readyState != ReadyState.OPENING && this.readyState != ReadyState.OPEN && this.readyState != ReadyState.CLOSING) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("packet received with socket readyState '%s'", this.readyState));
                return;
            }
            return;
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("socket received: type '%s', data '%s'", packet.type, packet.data));
        }
        emit("packet", packet);
        emit(EVENT_HEARTBEAT, new Object[0]);
        if ("open".equals(packet.type)) {
            try {
                onHandshake(new HandshakeData((String) packet.data));
                return;
            } catch (JSONException e) {
                str = "error";
                objArr = new Object[]{new EngineIOException(e)};
            }
        } else if ("ping".equals(packet.type)) {
            emit("ping", new Object[0]);
            EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.14
                @Override // java.lang.Runnable
                public void run() {
                    Socket.this.sendPacket("pong", (Runnable) null);
                }
            });
            return;
        } else if ("error".equals(packet.type)) {
            EngineIOException engineIOException = new EngineIOException("server error");
            engineIOException.code = packet.data;
            onError(engineIOException);
            return;
        } else {
            if (!"message".equals(packet.type)) {
                return;
            }
            emit("data", packet.data);
            str = "message";
            objArr = new Object[]{packet.data};
        }
        emit(str, objArr);
    }

    private void probe(final String str) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("probing transport '%s'", str));
        }
        final Transport[] transportArr = {createTransport(str)};
        final boolean[] zArr = {false};
        priorWebsocketSuccess = false;
        final Runnable[] runnableArr = {new Runnable() { // from class: io.socket.engineio.client.Socket.13
            @Override // java.lang.Runnable
            public void run() {
                transportArr[0].off("open", anonymousClass7);
                transportArr[0].off("error", listener);
                transportArr[0].off("close", listener);
                this.off("close", listener);
                this.off(Socket.EVENT_UPGRADING, listener);
            }
        }};
        final Emitter.Listener anonymousClass7 = new AnonymousClass7(zArr, str, transportArr, this, runnableArr);
        final Emitter.Listener listener = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.8
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                if (zArr[0]) {
                    return;
                }
                zArr[0] = true;
                runnableArr[0].run();
                transportArr[0].close();
                transportArr[0] = null;
            }
        };
        final Emitter.Listener listener2 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.9
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                EngineIOException engineIOException;
                Object obj = objArr[0];
                if (obj instanceof Exception) {
                    engineIOException = new EngineIOException(Socket.PROBE_ERROR, (Exception) obj);
                } else if (obj instanceof String) {
                    engineIOException = new EngineIOException("probe error: " + ((String) obj));
                } else {
                    engineIOException = new EngineIOException(Socket.PROBE_ERROR);
                }
                engineIOException.transport = transportArr[0].name;
                listener.call(new Object[0]);
                if (Socket.logger.isLoggable(Level.FINE)) {
                    Socket.logger.fine(String.format("probe transport \"%s\" failed because of error: %s", str, obj));
                }
                this.emit(Socket.EVENT_UPGRADE_ERROR, engineIOException);
            }
        };
        final Emitter.Listener listener3 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.10
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                listener2.call("transport closed");
            }
        };
        final Emitter.Listener listener4 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.11
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                listener2.call("socket closed");
            }
        };
        final Emitter.Listener listener5 = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.12
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Transport transport = (Transport) objArr[0];
                if (transportArr[0] == null || transport.name.equals(transportArr[0].name)) {
                    return;
                }
                if (Socket.logger.isLoggable(Level.FINE)) {
                    Socket.logger.fine(String.format("'%s' works - aborting '%s'", transport.name, transportArr[0].name));
                }
                listener.call(new Object[0]);
            }
        };
        transportArr[0].once("open", anonymousClass7);
        transportArr[0].once("error", listener2);
        transportArr[0].once("close", listener3);
        once("close", listener4);
        once(EVENT_UPGRADING, listener5);
        transportArr[0].open();
    }

    private void sendPacket(Packet packet, final Runnable runnable) {
        if (ReadyState.CLOSING == this.readyState || ReadyState.CLOSED == this.readyState) {
            return;
        }
        emit(EVENT_PACKET_CREATE, packet);
        this.writeBuffer.offer(packet);
        if (runnable != null) {
            once(EVENT_FLUSH, new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.18
                @Override // io.socket.emitter.Emitter.Listener
                public void call(Object... objArr) {
                    runnable.run();
                }
            });
        }
        flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, Runnable runnable) {
        sendPacket(new Packet(str), runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, String str2, Runnable runnable) {
        sendPacket(new Packet(str, str2), runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPacket(String str, byte[] bArr, Runnable runnable) {
        sendPacket(new Packet(str, bArr), runnable);
    }

    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        defaultCallFactory = factory;
    }

    public static void setDefaultOkHttpWebSocketFactory(WebSocket.Factory factory) {
        defaultWebSocketFactory = factory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTransport(Transport transport) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("setting transport %s", transport.name));
        }
        if (this.transport != null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("clearing existing transport %s", this.transport.name));
            }
            this.transport.off();
        }
        this.transport = transport;
        transport.on("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.6
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onDrain();
            }
        }).on("packet", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.5
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onPacket(objArr.length > 0 ? (Packet) objArr[0] : null);
            }
        }).on("error", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.4
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onError(objArr.length > 0 ? (Exception) objArr[0] : null);
            }
        }).on("close", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.onClose("transport close");
            }
        });
    }

    public Socket close() {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.19
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.readyState == ReadyState.OPENING || Socket.this.readyState == ReadyState.OPEN) {
                    Socket.this.readyState = ReadyState.CLOSING;
                    final Socket socket = Socket.this;
                    final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.Socket.19.1
                        @Override // java.lang.Runnable
                        public void run() {
                            socket.onClose("forced close");
                            Socket.logger.fine("socket closing - telling transport to close");
                            socket.transport.close();
                        }
                    };
                    final Emitter.Listener[] listenerArr = new Emitter.Listener[1];
                    listenerArr[0] = new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.19.2
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... objArr) {
                            socket.off("upgrade", listenerArr[0]);
                            socket.off(Socket.EVENT_UPGRADE_ERROR, listenerArr[0]);
                            runnable.run();
                        }
                    };
                    final Runnable runnable2 = new Runnable() { // from class: io.socket.engineio.client.Socket.19.3
                        @Override // java.lang.Runnable
                        public void run() {
                            socket.once("upgrade", listenerArr[0]);
                            socket.once(Socket.EVENT_UPGRADE_ERROR, listenerArr[0]);
                        }
                    };
                    if (Socket.this.writeBuffer.size() > 0) {
                        Socket.this.once("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.Socket.19.4
                            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
                            @Override // io.socket.emitter.Emitter.Listener
                            public void call(Object... objArr) {
                                (Socket.this.upgrading ? runnable2 : runnable).run();
                            }
                        });
                    } else if (Socket.this.upgrading) {
                        runnable2.run();
                    } else {
                        runnable.run();
                    }
                }
            }
        });
        return this;
    }

    List<String> filterUpgrades(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (this.transports.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public String id() {
        return this.id;
    }

    public Socket open() {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.2
            @Override // java.lang.Runnable
            public void run() {
                String str;
                if (Socket.this.rememberUpgrade && Socket.priorWebsocketSuccess && Socket.this.transports.contains(io.socket.engineio.client.transports.WebSocket.NAME)) {
                    str = io.socket.engineio.client.transports.WebSocket.NAME;
                } else {
                    if (Socket.this.transports.size() == 0) {
                        final Socket socket = Socket.this;
                        EventThread.nextTick(new Runnable() { // from class: io.socket.engineio.client.Socket.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                socket.emit("error", new EngineIOException("No transports available"));
                            }
                        });
                        return;
                    }
                    str = (String) Socket.this.transports.get(0);
                }
                Socket.this.readyState = ReadyState.OPENING;
                Transport transportCreateTransport = Socket.this.createTransport(str);
                Socket.this.setTransport(transportCreateTransport);
                transportCreateTransport.open();
            }
        });
        return this;
    }

    public void send(String str) {
        send(str, (Runnable) null);
    }

    public void send(final String str, final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.16
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.sendPacket("message", str, runnable);
            }
        });
    }

    public void send(byte[] bArr) {
        send(bArr, (Runnable) null);
    }

    public void send(final byte[] bArr, final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.Socket.17
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.sendPacket("message", bArr, runnable);
            }
        });
    }

    public void write(String str) {
        write(str, (Runnable) null);
    }

    public void write(String str, Runnable runnable) {
        send(str, runnable);
    }

    public void write(byte[] bArr) {
        write(bArr, (Runnable) null);
    }

    public void write(byte[] bArr, Runnable runnable) {
        send(bArr, runnable);
    }
}
