// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class UriQueryProps
{
    private static final String QMARK = "?";
    private static final char ASSIG = '=';
    private static final String EMPTY = "";
    private final String query_separator;
    private final HashMap<String, String> properties;
    
    private UriQueryProps(final char c) {
        this.properties = new HashMap<String, String>();
        this.query_separator = String.valueOf(c);
    }
    
    public final Map<String, String> getProperties() {
        return this.properties;
    }
    
    public final char getQuerySeparator() {
        return this.query_separator.charAt(0);
    }
    
    public final Uri.Encoded appendQuery(Uri.Encoded substring) {
        int n = 0;
        final StringBuilder sb = new StringBuilder();
        if (null != substring) {
            if (substring.startsWith("?")) {
                substring = substring.substring(1);
            }
            sb.append(substring.get());
            if (!substring.endsWith(this.query_separator)) {
                n = 1;
            }
        }
        final Iterator<Map.Entry<String, String>> iterator = this.properties.entrySet().iterator();
        while (iterator.hasNext()) {
            if (n != 0) {
                sb.append(this.query_separator);
            }
            final Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey());
            if ("" != entry.getValue()) {
                sb.append('=').append(entry.getValue());
            }
            n = 1;
        }
        return new Uri.Encoded(sb.toString(), "_-.~,;:$&+=!*'()@/?[]\\\"");
    }
    
    public final Uri appendQuery(final Uri uri) throws URISyntaxException {
        return uri.getNewQuery(this.appendQuery(uri.query));
    }
    
    public static final UriQueryProps create(final Uri uri, final char c) throws IllegalArgumentException {
        if (';' != c && '&' != c) {
            throw new IllegalArgumentException("querySeparator is invalid: " + c);
        }
        final UriQueryProps uriQueryProps = new UriQueryProps(c);
        final String decode = Uri.decode(uri.query);
        final int n = (null != decode) ? decode.length() : -1;
        int i = -1;
        while (i < n) {
            final int n2 = i + 1;
            i = decode.indexOf(c, n2);
            if (i == 0) {
                continue;
            }
            if (0 > i) {
                i = n;
            }
            final String substring = decode.substring(n2, i);
            final int index = substring.indexOf(61);
            if (0 < index) {
                uriQueryProps.properties.put(substring.substring(0, index), substring.substring(index + 1));
            }
            else {
                uriQueryProps.properties.put(substring, "");
            }
        }
        return uriQueryProps;
    }
}
