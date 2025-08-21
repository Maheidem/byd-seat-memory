package io.socket.client;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class Url {
    private static Pattern PATTERN_AUTHORITY = Pattern.compile("^(.*@)?([^:]+)(:\\d+)?$");

    static class ParsedURI {
        public final String id;
        public final URI uri;

        public ParsedURI(URI uri, String str) {
            this.uri = uri;
            this.id = str;
        }
    }

    private Url() {
    }

    private static String extractHostFromAuthorityPart(String str) {
        if (str == null) {
            throw new RuntimeException("unable to parse the host from the authority");
        }
        Matcher matcher = PATTERN_AUTHORITY.matcher(str);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        throw new RuntimeException("unable to parse the host from the authority");
    }

    public static ParsedURI parse(URI uri) {
        String str;
        String str2;
        String str3;
        String str4;
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.matches("^https?|wss?$")) {
            scheme = "https";
        }
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equals(scheme) || "ws".equals(scheme)) {
                port = 80;
            } else if ("https".equals(scheme) || "wss".equals(scheme)) {
                port = 443;
            }
        }
        String rawPath = uri.getRawPath();
        if (rawPath == null || rawPath.length() == 0) {
            rawPath = "/";
        }
        String rawUserInfo = uri.getRawUserInfo();
        String rawQuery = uri.getRawQuery();
        String rawFragment = uri.getRawFragment();
        String host = uri.getHost();
        if (host == null) {
            host = extractHostFromAuthorityPart(uri.getRawAuthority());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(scheme);
        sb.append("://");
        if (rawUserInfo != null) {
            str = rawUserInfo + "@";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(host);
        if (port != -1) {
            str2 = ":" + port;
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(rawPath);
        if (rawQuery != null) {
            str3 = "?" + rawQuery;
        } else {
            str3 = "";
        }
        sb.append(str3);
        if (rawFragment != null) {
            str4 = "#" + rawFragment;
        } else {
            str4 = "";
        }
        sb.append(str4);
        return new ParsedURI(URI.create(sb.toString()), scheme + "://" + host + ":" + port);
    }
}
