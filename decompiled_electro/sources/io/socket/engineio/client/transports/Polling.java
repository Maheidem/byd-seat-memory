package io.socket.engineio.client.transports;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.parser.Packet;
import io.socket.engineio.parser.Parser;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import io.socket.yeast.Yeast;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class Polling extends Transport {
    public static final String EVENT_POLL = "poll";
    public static final String EVENT_POLL_COMPLETE = "pollComplete";
    public static final String NAME = "polling";
    private static final Logger logger = Logger.getLogger(Polling.class.getName());
    private boolean polling;

    public Polling(Transport.Options options) {
        super(options);
        this.name = NAME;
    }

    private void _onData(Object obj) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("polling got data %s", obj));
        }
        Parser.decodePayload((String) obj, new Parser.DecodePayloadCallback() { // from class: io.socket.engineio.client.transports.Polling.2
            @Override // io.socket.engineio.parser.Parser.DecodePayloadCallback
            public boolean call(Packet packet, int i, int i2) {
                if (this.readyState == Transport.ReadyState.OPENING && "open".equals(packet.type)) {
                    this.onOpen();
                }
                if ("close".equals(packet.type)) {
                    this.onClose();
                    return false;
                }
                this.onPacket(packet);
                return true;
            }
        });
        if (this.readyState != Transport.ReadyState.CLOSED) {
            this.polling = false;
            emit(EVENT_POLL_COMPLETE, new Object[0]);
            if (this.readyState == Transport.ReadyState.OPEN) {
                poll();
            } else if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("ignoring poll - transport state '%s'", this.readyState));
            }
        }
    }

    private void poll() {
        logger.fine(NAME);
        this.polling = true;
        doPoll();
        emit(EVENT_POLL, new Object[0]);
    }

    @Override // io.socket.engineio.client.Transport
    protected void doClose() {
        Emitter.Listener listener = new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                Polling.logger.fine("writing close packet");
                this.write(new Packet[]{new Packet("close")});
            }
        };
        if (this.readyState == Transport.ReadyState.OPEN) {
            logger.fine("transport open - closing");
            listener.call(new Object[0]);
        } else {
            logger.fine("transport not open - deferring close");
            once("open", listener);
        }
    }

    @Override // io.socket.engineio.client.Transport
    protected void doOpen() {
        poll();
    }

    protected abstract void doPoll();

    protected abstract void doWrite(String str, Runnable runnable);

    @Override // io.socket.engineio.client.Transport
    protected void onData(String str) {
        _onData(str);
    }

    @Override // io.socket.engineio.client.Transport
    protected void onData(byte[] bArr) {
        _onData(bArr);
    }

    public void pause(final Runnable runnable) {
        EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.Polling.1
            @Override // java.lang.Runnable
            public void run() {
                final Polling polling = Polling.this;
                Polling.this.readyState = Transport.ReadyState.PAUSED;
                final Runnable runnable2 = new Runnable() { // from class: io.socket.engineio.client.transports.Polling.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Polling.logger.fine("paused");
                        polling.readyState = Transport.ReadyState.PAUSED;
                        runnable.run();
                    }
                };
                if (!Polling.this.polling && Polling.this.writable) {
                    runnable2.run();
                    return;
                }
                final int[] iArr = {0};
                if (Polling.this.polling) {
                    Polling.logger.fine("we are currently polling - waiting to pause");
                    iArr[0] = iArr[0] + 1;
                    Polling.this.once(Polling.EVENT_POLL_COMPLETE, new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.1.2
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... objArr) {
                            Polling.logger.fine("pre-pause polling complete");
                            int[] iArr2 = iArr;
                            int i = iArr2[0] - 1;
                            iArr2[0] = i;
                            if (i == 0) {
                                runnable2.run();
                            }
                        }
                    });
                }
                if (Polling.this.writable) {
                    return;
                }
                Polling.logger.fine("we are currently writing - waiting to pause");
                iArr[0] = iArr[0] + 1;
                Polling.this.once("drain", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.Polling.1.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Polling.logger.fine("pre-pause writing complete");
                        int[] iArr2 = iArr;
                        int i = iArr2[0] - 1;
                        iArr2[0] = i;
                        if (i == 0) {
                            runnable2.run();
                        }
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
        String str2 = this.secure ? "https" : "http";
        String str3 = "";
        if (this.timestampRequests) {
            map.put(this.timestampParam, Yeast.yeast());
        }
        String strEncode = ParseQS.encode(map);
        if (this.port > 0 && (("https".equals(str2) && this.port != 443) || ("http".equals(str2) && this.port != 80))) {
            str3 = ":" + this.port;
        }
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
        final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.transports.Polling.4
            @Override // java.lang.Runnable
            public void run() {
                this.writable = true;
                this.emit("drain", new Object[0]);
            }
        };
        Parser.encodePayload(packetArr, new Parser.EncodeCallback<String>() { // from class: io.socket.engineio.client.transports.Polling.5
            @Override // io.socket.engineio.parser.Parser.EncodeCallback
            public void call(String str) {
                this.doWrite(str, runnable);
            }
        });
    }
}
