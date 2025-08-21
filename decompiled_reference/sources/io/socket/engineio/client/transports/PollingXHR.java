package io.socket.engineio.client.transports;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.thread.EventThread;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes.dex */
public class PollingXHR extends Polling {
    private static final Logger logger = Logger.getLogger(PollingXHR.class.getName());
    private static boolean LOGGABLE_FINE = logger.isLoggable(Level.FINE);

    public static class Request extends Emitter {
        public static final String EVENT_DATA = "data";
        public static final String EVENT_ERROR = "error";
        public static final String EVENT_REQUEST_HEADERS = "requestHeaders";
        public static final String EVENT_RESPONSE_HEADERS = "responseHeaders";
        public static final String EVENT_SUCCESS = "success";
        private static final String TEXT_CONTENT_TYPE = "text/plain;charset=UTF-8";
        private static final MediaType TEXT_MEDIA_TYPE = MediaType.parse(TEXT_CONTENT_TYPE);
        private Call.Factory callFactory;
        private String data;
        private Map<String, List<String>> extraHeaders;
        private String method;
        private Call requestCall;
        private Response response;
        private String uri;

        public static class Options {
            public Call.Factory callFactory;
            public String data;
            public Map<String, List<String>> extraHeaders;
            public String method;
            public String uri;
        }

        public Request(Options options) {
            this.method = options.method != null ? options.method : "GET";
            this.uri = options.uri;
            this.data = options.data;
            this.callFactory = options.callFactory;
            this.extraHeaders = options.extraHeaders;
        }

        private void onData(String str) {
            emit("data", str);
            onSuccess();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onError(Exception exc) {
            emit("error", exc);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onLoad() {
            try {
                onData(this.response.body().string());
            } catch (IOException e) {
                onError(e);
            }
        }

        private void onRequestHeaders(Map<String, List<String>> map) {
            emit("requestHeaders", map);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onResponseHeaders(Map<String, List<String>> map) {
            emit("responseHeaders", map);
        }

        private void onSuccess() {
            emit(EVENT_SUCCESS, new Object[0]);
        }

        public void create() {
            if (PollingXHR.LOGGABLE_FINE) {
                PollingXHR.logger.fine(String.format("xhr open %s: %s", this.method, this.uri));
            }
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            if (this.extraHeaders != null) {
                treeMap.putAll(this.extraHeaders);
            }
            if ("POST".equals(this.method)) {
                treeMap.put("Content-type", new LinkedList(Collections.singletonList(TEXT_CONTENT_TYPE)));
            }
            treeMap.put("Accept", new LinkedList(Collections.singletonList("*/*")));
            onRequestHeaders(treeMap);
            if (PollingXHR.LOGGABLE_FINE) {
                PollingXHR.logger.fine(String.format("sending xhr with url %s | data %s", this.uri, this.data));
            }
            Request.Builder builder = new Request.Builder();
            for (Map.Entry<String, List<String>> entry : treeMap.entrySet()) {
                Iterator<String> it = entry.getValue().iterator();
                while (it.hasNext()) {
                    builder.addHeader(entry.getKey(), it.next());
                }
            }
            this.requestCall = this.callFactory.newCall(builder.url(HttpUrl.parse(this.uri)).method(this.method, this.data != null ? RequestBody.create(TEXT_MEDIA_TYPE, this.data) : null).build());
            this.requestCall.enqueue(new Callback() { // from class: io.socket.engineio.client.transports.PollingXHR.Request.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    this.onError(iOException);
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    this.response = response;
                    this.onResponseHeaders(response.headers().toMultimap());
                    try {
                        if (response.isSuccessful()) {
                            this.onLoad();
                        } else {
                            this.onError(new IOException(Integer.toString(response.code())));
                        }
                    } finally {
                        response.close();
                    }
                }
            });
        }
    }

    public PollingXHR(Transport.Options options) {
        super(options);
    }

    @Override // io.socket.engineio.client.transports.Polling
    protected void doPoll() {
        logger.fine("xhr poll");
        Request request = request();
        request.on("data", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.5
            @Override // io.socket.emitter.Emitter.Listener
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.PollingXHR.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData((String) (objArr.length > 0 ? objArr[0] : null));
                    }
                });
            }
        });
        request.on("error", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.6
            @Override // io.socket.emitter.Emitter.Listener
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.PollingXHR.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onError("xhr poll error", (objArr.length <= 0 || !(objArr[0] instanceof Exception)) ? null : (Exception) objArr[0]);
                    }
                });
            }
        });
        request.create();
    }

    @Override // io.socket.engineio.client.transports.Polling
    protected void doWrite(String str, final Runnable runnable) {
        Request.Options options = new Request.Options();
        options.method = "POST";
        options.data = str;
        options.extraHeaders = this.extraHeaders;
        Request request = request(options);
        request.on(Request.EVENT_SUCCESS, new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.3
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.PollingXHR.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        runnable.run();
                    }
                });
            }
        });
        request.on("error", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.4
            @Override // io.socket.emitter.Emitter.Listener
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.PollingXHR.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onError("xhr post error", (objArr.length <= 0 || !(objArr[0] instanceof Exception)) ? null : (Exception) objArr[0]);
                    }
                });
            }
        });
        request.create();
    }

    protected Request request() {
        return request(null);
    }

    protected Request request(Request.Options options) {
        if (options == null) {
            options = new Request.Options();
        }
        options.uri = uri();
        options.callFactory = this.callFactory;
        options.extraHeaders = this.extraHeaders;
        Request request = new Request(options);
        request.on("requestHeaders", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.2
            @Override // io.socket.emitter.Emitter.Listener
            public void call(Object... objArr) {
                this.emit("requestHeaders", objArr[0]);
            }
        }).on("responseHeaders", new Emitter.Listener() { // from class: io.socket.engineio.client.transports.PollingXHR.1
            @Override // io.socket.emitter.Emitter.Listener
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.PollingXHR.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.emit("responseHeaders", objArr[0]);
                    }
                });
            }
        });
        return request;
    }
}
