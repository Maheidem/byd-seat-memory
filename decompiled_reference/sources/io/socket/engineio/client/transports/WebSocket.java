package io.socket.engineio.client.transports;

import a.f;
import io.socket.engineio.client.Transport;
import io.socket.engineio.parser.Packet;
import io.socket.engineio.parser.Parser;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import io.socket.yeast.Yeast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;

/* loaded from: classes.dex */
public class WebSocket extends Transport {
    public static final String NAME = "websocket";
    private static final Logger logger = Logger.getLogger(PollingXHR.class.getName());
    private okhttp3.WebSocket ws;

    public WebSocket(Transport.Options options) {
        super(options);
        this.name = NAME;
    }

    @Override // io.socket.engineio.client.Transport
    protected void doClose() {
        if (this.ws != null) {
            this.ws.close(1000, "");
            this.ws = null;
        }
    }

    @Override // io.socket.engineio.client.Transport
    protected void doOpen() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        if (this.extraHeaders != null) {
            treeMap.putAll(this.extraHeaders);
        }
        emit("requestHeaders", treeMap);
        Request.Builder builderUrl = new Request.Builder().url(uri());
        for (Map.Entry entry : treeMap.entrySet()) {
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                builderUrl.addHeader((String) entry.getKey(), (String) it.next());
            }
        }
        this.ws = this.webSocketFactory.newWebSocket(builderUrl.build(), new WebSocketListener() { // from class: io.socket.engineio.client.transports.WebSocket.1
            @Override // okhttp3.WebSocketListener
            public void onClosed(okhttp3.WebSocket webSocket, int i, String str) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onClose();
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(okhttp3.WebSocket webSocket, final Throwable th, Response response) {
                if (th instanceof Exception) {
                    EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.5
                        @Override // java.lang.Runnable
                        public void run() {
                            this.onError("websocket error", (Exception) th);
                        }
                    });
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final f fVar) {
                if (fVar == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(fVar.i());
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final String str) {
                if (str == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(str);
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                final Map<String, List<String>> multimap = response.headers().toMultimap();
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.emit("responseHeaders", multimap);
                        this.onOpen();
                    }
                });
            }
        });
    }

    protected String uri() {
        String str;
        Map map = this.query;
        if (map == null) {
            map = new HashMap();
        }
        String str2 = this.secure ? "wss" : "ws";
        String str3 = "";
        if (this.port > 0 && (("wss".equals(str2) && this.port != 443) || ("ws".equals(str2) && this.port != 80))) {
            str3 = ":" + this.port;
        }
        if (this.timestampRequests) {
            map.put(this.timestampParam, Yeast.yeast());
        }
        String strEncode = ParseQS.encode(map);
        if (strEncode.length() > 0) {
            strEncode = "?" + strEncode;
        }
        boolean zContains = this.hostname.contains(":");
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("://");
        if (zContains) {
            str = "[" + this.hostname + "]";
        } else {
            str = this.hostname;
        }
        sb.append(str);
        sb.append(str3);
        sb.append(this.path);
        sb.append(strEncode);
        return sb.toString();
    }

    @Override // io.socket.engineio.client.Transport
    protected void write(Packet[] packetArr) {
        this.writable = false;
        final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2
            @Override // java.lang.Runnable
            public void run() {
                EventThread.nextTick(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.writable = true;
                        this.emit("drain", new Object[0]);
                    }
                });
            }
        };
        final int[] iArr = {packetArr.length};
        for (Packet packet : packetArr) {
            if (this.readyState != Transport.ReadyState.OPENING && this.readyState != Transport.ReadyState.OPEN) {
                return;
            }
            Parser.encodePacket(packet, new Parser.EncodeCallback() { // from class: io.socket.engineio.client.transports.WebSocket.3
                @Override // io.socket.engineio.parser.Parser.EncodeCallback
                public void call(Object obj) {
                    try {
                        if (obj instanceof String) {
                            this.ws.send((String) obj);
                        } else if (obj instanceof byte[]) {
                            this.ws.send(f.a((byte[]) obj));
                        }
                    } catch (IllegalStateException unused) {
                        WebSocket.logger.fine("websocket closed before we could write");
                    }
                    int[] iArr2 = iArr;
                    int i = iArr2[0] - 1;
                    iArr2[0] = i;
                    if (i == 0) {
                        runnable.run();
                    }
                }
            });
        }
    }
}
