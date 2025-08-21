package org.acra.util;

import android.content.Context;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.sender.HttpSender;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/* loaded from: classes.dex */
public final class HttpRequest {
    private Map<String, String> headers;
    private String login;
    private String password;
    private int connectionTimeOut = ACRAConstants.DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeOut = ACRAConstants.DEFAULT_CONNECTION_TIMEOUT;
    private int maxNrRetries = 3;

    private static class SocketTimeOutRetryHandler implements HttpRequestRetryHandler {
        private final HttpParams httpParams;
        private final int maxNrRetries;

        private SocketTimeOutRetryHandler(HttpParams httpParams, int i) {
            this.httpParams = httpParams;
            this.maxNrRetries = i;
        }

        @Override // org.apache.http.client.HttpRequestRetryHandler
        public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
            if (!(iOException instanceof SocketTimeoutException)) {
                return false;
            }
            if (i > this.maxNrRetries) {
                ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut but exceeded max number of retries : " + this.maxNrRetries);
                return false;
            }
            if (this.httpParams == null) {
                ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut - no HttpParams, cannot increase time out. Trying again with current settings");
                return true;
            }
            int soTimeout = HttpConnectionParams.getSoTimeout(this.httpParams) * 2;
            HttpConnectionParams.setSoTimeout(this.httpParams, soTimeout);
            ACRA.log.d(ACRA.LOG_TAG, "SocketTimeOut - increasing time out to " + soTimeout + " millis and trying again");
            return true;
        }
    }

    private UsernamePasswordCredentials getCredentials() {
        if (this.login == null && this.password == null) {
            return null;
        }
        return new UsernamePasswordCredentials(this.login, this.password);
    }

    private HttpClient getHttpClient(Context context) {
        Scheme scheme;
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        basicHttpParams.setParameter("http.protocol.cookie-policy", "rfc2109");
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.connectionTimeOut);
        HttpConnectionParams.setSoTimeout(basicHttpParams, this.socketTimeOut);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", new PlainSocketFactory(), 80));
        if (!ACRA.getConfig().disableSSLCertValidation()) {
            if (ACRA.getConfig().keyStore() != null) {
                try {
                    SSLSocketFactory sSLSocketFactory = new SSLSocketFactory(ACRA.getConfig().keyStore());
                    sSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                    schemeRegistry.register(new Scheme("https", sSLSocketFactory, 443));
                } catch (KeyManagementException unused) {
                    scheme = new Scheme("https", SSLSocketFactory.getSocketFactory(), 443);
                } catch (KeyStoreException unused2) {
                    scheme = new Scheme("https", SSLSocketFactory.getSocketFactory(), 443);
                } catch (NoSuchAlgorithmException unused3) {
                    scheme = new Scheme("https", SSLSocketFactory.getSocketFactory(), 443);
                } catch (UnrecoverableKeyException unused4) {
                    scheme = new Scheme("https", SSLSocketFactory.getSocketFactory(), 443);
                }
            } else {
                schemeRegistry.register(new Scheme("https", ACRA.getConfig().getHttpSocketFactoryFactory().create(context), 443));
            }
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new SingleClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            defaultHttpClient.setHttpRequestRetryHandler(new SocketTimeOutRetryHandler(basicHttpParams, this.maxNrRetries));
            return defaultHttpClient;
        }
        scheme = new Scheme("https", new FakeSocketFactory(), 443);
        schemeRegistry.register(scheme);
        DefaultHttpClient defaultHttpClient2 = new DefaultHttpClient(new SingleClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        defaultHttpClient2.setHttpRequestRetryHandler(new SocketTimeOutRetryHandler(basicHttpParams, this.maxNrRetries));
        return defaultHttpClient2;
    }

    private HttpEntityEnclosingRequestBase getHttpRequest(URL url, HttpSender.Method method, String str, HttpSender.Type type) {
        HttpEntityEnclosingRequestBase httpPost;
        switch (method) {
            case POST:
                httpPost = new HttpPost(url.toString());
                break;
            case PUT:
                httpPost = new HttpPut(url.toString());
                break;
            default:
                throw new UnsupportedOperationException("Unknown method: " + method.name());
        }
        UsernamePasswordCredentials credentials = getCredentials();
        if (credentials != null) {
            httpPost.addHeader(BasicScheme.authenticate(credentials, "UTF-8", false));
        }
        httpPost.setHeader("User-Agent", "Android");
        httpPost.setHeader("Accept", "text/html,application/xml,application/json,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        httpPost.setHeader("Content-Type", type.getContentType());
        if (this.headers != null) {
            for (String str2 : this.headers.keySet()) {
                httpPost.setHeader(str2, this.headers.get(str2));
            }
        }
        httpPost.setEntity(new StringEntity(str, "UTF-8"));
        return httpPost;
    }

    public static String getParamsAsFormString(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : map.keySet()) {
            if (sb.length() != 0) {
                sb.append('&');
            }
            Object obj2 = map.get(obj);
            if (obj2 == null) {
                obj2 = "";
            }
            sb.append(URLEncoder.encode(obj.toString(), "UTF-8"));
            sb.append('=');
            sb.append(URLEncoder.encode(obj2.toString(), "UTF-8"));
        }
        return sb.toString();
    }

    public void send(Context context, URL url, HttpSender.Method method, String str, HttpSender.Type type) throws Throwable {
        HttpResponse httpResponseExecute;
        HttpClient httpClient = getHttpClient(context);
        HttpEntityEnclosingRequestBase httpRequest = getHttpRequest(url, method, str, type);
        ACRA.log.d(ACRA.LOG_TAG, "Sending request to " + url);
        try {
            httpResponseExecute = httpClient.execute(httpRequest, new BasicHttpContext());
            if (httpResponseExecute != null) {
                try {
                    if (httpResponseExecute.getStatusLine() != null) {
                        String string = Integer.toString(httpResponseExecute.getStatusLine().getStatusCode());
                        if (!string.equals("409") && !string.equals("403") && (string.startsWith("4") || string.startsWith("5"))) {
                            throw new IOException("Host returned error code " + string);
                        }
                    }
                    EntityUtils.toString(httpResponseExecute.getEntity());
                } catch (Throwable th) {
                    th = th;
                    if (httpResponseExecute != null) {
                        httpResponseExecute.getEntity().consumeContent();
                    }
                    throw th;
                }
            }
            if (httpResponseExecute != null) {
                httpResponseExecute.getEntity().consumeContent();
            }
        } catch (Throwable th2) {
            th = th2;
            httpResponseExecute = null;
        }
    }

    public void setConnectionTimeOut(int i) {
        this.connectionTimeOut = i;
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public void setLogin(String str) {
        this.login = str;
    }

    public void setMaxNrRetries(int i) {
        this.maxNrRetries = i;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setSocketTimeOut(int i) {
        this.socketTimeOut = i;
    }
}
