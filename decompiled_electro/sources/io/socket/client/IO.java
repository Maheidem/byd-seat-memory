package io.socket.client;

import io.socket.client.Manager;
import io.socket.client.Url;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.WebSocket;

/* loaded from: classes.dex */
public class IO {
    private static final Logger logger = Logger.getLogger(IO.class.getName());
    private static final ConcurrentHashMap<String, Manager> managers = new ConcurrentHashMap<>();
    public static int protocol = 5;

    public static class Options extends Manager.Options {
        public boolean forceNew;
        public boolean multiplex = true;

        public static SocketOptionBuilder builder() {
            return SocketOptionBuilder.builder();
        }
    }

    private IO() {
    }

    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        Manager.defaultCallFactory = factory;
    }

    public static void setDefaultOkHttpWebSocketFactory(WebSocket.Factory factory) {
        Manager.defaultWebSocketFactory = factory;
    }

    public static Socket socket(String str) {
        return socket(str, (Options) null);
    }

    public static Socket socket(String str, Options options) {
        return socket(new URI(str), options);
    }

    public static Socket socket(URI uri) {
        return socket(uri, (Options) null);
    }

    public static Socket socket(URI uri, Options options) {
        Manager manager;
        if (options == null) {
            options = new Options();
        }
        Url.ParsedURI parsedURI = Url.parse(uri);
        URI uri2 = parsedURI.uri;
        String str = parsedURI.id;
        boolean z = options.forceNew || !options.multiplex || (managers.containsKey(str) && managers.get(str).nsps.containsKey(uri2.getPath()));
        String query = uri2.getQuery();
        if (query != null && (options.query == null || options.query.isEmpty())) {
            options.query = query;
        }
        if (z) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("ignoring socket cache for %s", uri2));
            }
            manager = new Manager(uri2, options);
        } else {
            if (!managers.containsKey(str)) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(String.format("new io instance for %s", uri2));
                }
                managers.putIfAbsent(str, new Manager(uri2, options));
            }
            manager = managers.get(str);
        }
        return manager.socket(uri2.getPath(), options);
    }
}
