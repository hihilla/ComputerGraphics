// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.jogamp.common.util.PropertyAccess;
import jogamp.common.Debug;
import java.util.StringTokenizer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import com.jogamp.common.util.IOUtil;
import java.io.File;
import java.net.URISyntaxException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class Uri
{
    private static final boolean DEBUG;
    private static final boolean DEBUG_SHOWFIX;
    private static final int PARSE_HINT_FIX_PATH = 1;
    private static final String DIGITS = "0123456789ABCDEF";
    private static final String ENCODING = "UTF8";
    private static final String MSG_ENCODING_NA = "Charset UTF8 not available";
    private static final Pattern patternSingleFS;
    public static final String UNRESERVED = "_-.~";
    private static final String punct = ",;:$&+=";
    public static final String RESERVED = ",;:$&+=!*'()@/?#[]";
    public static final String RESERVED_2 = ",;:$&+=!*'()@/?[]";
    public static final String USERINFO_LEGAL = "_-.~,;:$&+=";
    public static final String AUTHORITY_LEGAL = "@[]_-.~,;:$&+=";
    public static final String PATH_LEGAL = "/!_-.~";
    public static final String QUERY_LEGAL = "_-.~,;:$&+=!*'()@/?[]\\\"";
    public static final String SSP_LEGAL = "_-.~,;:$&+=!*'()@/?[]\\\"";
    public static final String FRAG_LEGAL = "_-.~,;:$&+=!*'()@/?#[]";
    public static final char SCHEME_SEPARATOR = ':';
    public static final char QUERY_SEPARATOR = '?';
    public static final char FRAGMENT_SEPARATOR = '#';
    public static final String FILE_SCHEME = "file";
    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";
    public static final String JAR_SCHEME = "jar";
    public static final char JAR_SCHEME_SEPARATOR = '!';
    public final Encoded input;
    private final Object lazyLock;
    private ASCIIEncoded inputASCII;
    private int hash;
    public final Encoded scheme;
    public final Encoded schemeSpecificPart;
    public final Encoded path;
    public final boolean hasAuthority;
    public final Encoded authority;
    public final Encoded userInfo;
    public final Encoded host;
    public final int port;
    public final Encoded query;
    public final Encoded fragment;
    public final boolean absolute;
    public final boolean opaque;
    
    private static void encodeChar2UTF8(final StringBuilder sb, final char c) {
        byte[] bytes;
        try {
            bytes = new String(new char[] { c }).getBytes("UTF8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Charset UTF8 not available", ex);
        }
        for (int i = 0; i < bytes.length; ++i) {
            final byte b = bytes[i];
            sb.append('%');
            sb.append("0123456789ABCDEF".charAt((b & 0xF0) >> 4));
            sb.append("0123456789ABCDEF".charAt(b & 0xF));
        }
    }
    
    public static String encode(final String s, final String s2) {
        if (null == s) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ((char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z') || (char1 >= '0' && char1 <= '9') || s2.indexOf(char1) > -1 || (char1 > '\u007f' && !Character.isSpaceChar(char1) && !Character.isISOControl(char1))) {
                sb.append(char1);
            }
            else {
                encodeChar2UTF8(sb, char1);
            }
        }
        return sb.toString();
    }
    
    public static String encodeToASCIIString(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 <= '\u007f') {
                sb.append(char1);
            }
            else {
                encodeChar2UTF8(sb, char1);
            }
        }
        return sb.toString();
    }
    
    public static String decode(final Encoded encoded) {
        return (null != encoded) ? encoded.decode() : null;
    }
    
    public static String decode(final String s) {
        if (null == s) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final byte[] array = new byte[32];
        int i = 0;
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == '%') {
                int n = 0;
                while (i + 2 < s.length()) {
                    final int digit = Character.digit(s.charAt(i + 1), 16);
                    final int digit2 = Character.digit(s.charAt(i + 2), 16);
                    if (digit == -1 || digit2 == -1) {
                        throw new IllegalArgumentException("invalid hex-digits at index " + i + ": " + s.substring(i, i + 3));
                    }
                    array[n++] = (byte)((digit << 4) + digit2);
                    if (32 == n) {
                        appendUTF8(sb, array, n);
                        n = 0;
                    }
                    i += 3;
                    if (i < s.length() && s.charAt(i) == '%') {
                        continue;
                    }
                    if (0 < n) {
                        appendUTF8(sb, array, n);
                        continue Label_0253;
                    }
                    continue Label_0253;
                }
                throw new IllegalArgumentException("missing '%' hex-digits at index " + i);
            }
            sb.append(char1);
            ++i;
            Label_0253:;
        }
        return sb.toString();
    }
    
    private static void appendUTF8(final StringBuilder sb, final byte[] array, final int n) {
        try {
            sb.append(new String(array, 0, n, "UTF8"));
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Charset UTF8 not available", ex);
        }
    }
    
    public static Uri create(final String s, final String s2, final String s3) throws URISyntaxException {
        if (emptyString(s) && emptyString(s2) && emptyString(s3)) {
            throw new URISyntaxException("", "all empty parts");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(s)) {
            sb.append(s);
            sb.append(':');
        }
        if (!emptyString(s2)) {
            sb.append(encode(s2, "_-.~,;:$&+=!*'()@/?[]\\\""));
        }
        if (!emptyString(s3)) {
            sb.append('#');
            sb.append(encode(s3, "_-.~,;:$&+=!*'()@/?#[]"));
        }
        return new Uri(new Encoded(sb.toString()), false, 0);
    }
    
    public static Uri create(final Encoded encoded, final Encoded encoded2, final Encoded encoded3) throws URISyntaxException {
        if (emptyString(encoded) && emptyString(encoded2) && emptyString(encoded3)) {
            throw new URISyntaxException("", "all empty parts");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(encoded)) {
            sb.append(encoded);
            sb.append(':');
        }
        if (!emptyString(encoded2)) {
            sb.append(encoded2.get());
        }
        if (!emptyString(encoded3)) {
            sb.append('#');
            sb.append(encoded3.get());
        }
        return new Uri(new Encoded(sb.toString()), false, 0);
    }
    
    public static Uri create(final String s, final String s2, String string, final int n, final String s3, final String s4, final String s5) throws URISyntaxException {
        if (emptyString(s) && emptyString(s2) && emptyString(string) && emptyString(s3) && emptyString(s4) && emptyString(s5)) {
            throw new URISyntaxException("", "all empty parts");
        }
        if (!emptyString(s) && !emptyString(s3) && s3.length() > 0 && s3.charAt(0) != '/') {
            throw new URISyntaxException(s3, "path doesn't start with '/'");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(s)) {
            sb.append(s);
            sb.append(':');
        }
        if (!emptyString(s2) || !emptyString(string) || n != -1) {
            sb.append("//");
        }
        if (!emptyString(s2)) {
            sb.append(encode(s2, "_-.~,;:$&+="));
            sb.append('@');
        }
        if (!emptyString(string)) {
            if (string.indexOf(58) != -1 && string.indexOf(93) == -1 && string.indexOf(91) == -1) {
                string = "[" + string + "]";
            }
            sb.append(string);
        }
        if (n != -1) {
            sb.append(':');
            sb.append(n);
        }
        if (!emptyString(s3)) {
            sb.append(encode(s3, "/!_-.~"));
        }
        if (!emptyString(s4)) {
            sb.append('?');
            sb.append(encode(s4, "_-.~,;:$&+=!*'()@/?[]\\\""));
        }
        if (!emptyString(s5)) {
            sb.append('#');
            sb.append(encode(s5, "_-.~,;:$&+=!*'()@/?#[]"));
        }
        return new Uri(new Encoded(sb.toString()), true, 0);
    }
    
    public static Uri create(final Encoded encoded, final Encoded encoded2, final Encoded encoded3, final int n, final Encoded encoded4, final Encoded encoded5, final Encoded encoded6) throws URISyntaxException {
        if (emptyString(encoded) && emptyString(encoded2) && emptyString(encoded3) && emptyString(encoded4) && emptyString(encoded5) && emptyString(encoded6)) {
            throw new URISyntaxException("", "all empty parts");
        }
        if (!emptyString(encoded) && !emptyString(encoded4) && encoded4.length() > 0 && encoded4.charAt(0) != '/') {
            throw new URISyntaxException(encoded4.get(), "path doesn't start with '/'");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(encoded)) {
            sb.append(encoded);
            sb.append(':');
        }
        if (!emptyString(encoded2) || !emptyString(encoded3) || n != -1) {
            sb.append("//");
        }
        if (!emptyString(encoded2)) {
            sb.append(encoded2.get());
            sb.append('@');
        }
        if (!emptyString(encoded3)) {
            sb.append(encoded3.get());
        }
        if (n != -1) {
            sb.append(':');
            sb.append(n);
        }
        if (!emptyString(encoded4)) {
            sb.append(encoded4.get());
        }
        if (!emptyString(encoded5)) {
            sb.append('?');
            sb.append(encoded5.get());
        }
        if (!emptyString(encoded6)) {
            sb.append('#');
            sb.append(encoded6.get());
        }
        return new Uri(new Encoded(sb.toString()), true, 0);
    }
    
    public static Uri create(final String s, final String s2, final String s3, final String s4) throws URISyntaxException {
        return create(s, null, s2, -1, s3, null, s4);
    }
    
    public static Uri create(final Encoded encoded, final Encoded encoded2, final Encoded encoded3, final Encoded encoded4) throws URISyntaxException {
        return create(encoded, null, encoded2, -1, encoded3, null, encoded4);
    }
    
    public static Uri create(final String s, final String s2, final String s3, final String s4, final String s5) throws URISyntaxException {
        if (emptyString(s) && emptyString(s2) && emptyString(s3) && emptyString(s4) && emptyString(s5)) {
            throw new URISyntaxException("", "all empty parts");
        }
        if (!emptyString(s) && !emptyString(s3) && s3.length() > 0 && s3.charAt(0) != '/') {
            throw new URISyntaxException(s3, "path doesn't start with '/'");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(s)) {
            sb.append(s);
            sb.append(':');
        }
        if (!emptyString(s2)) {
            sb.append("//");
            sb.append(encode(s2, "@[]_-.~,;:$&+="));
        }
        if (!emptyString(s3)) {
            sb.append(encode(s3, "/!_-.~"));
        }
        if (!emptyString(s4)) {
            sb.append('?');
            sb.append(encode(s4, "_-.~,;:$&+=!*'()@/?[]\\\""));
        }
        if (!emptyString(s5)) {
            sb.append('#');
            sb.append(encode(s5, "_-.~,;:$&+=!*'()@/?#[]"));
        }
        return new Uri(new Encoded(sb.toString()), false, 0);
    }
    
    public static Uri create(final Encoded encoded, final Encoded encoded2, final Encoded encoded3, final Encoded encoded4, final Encoded encoded5) throws URISyntaxException {
        if (emptyString(encoded) && emptyString(encoded2) && emptyString(encoded3) && emptyString(encoded4) && emptyString(encoded5)) {
            throw new URISyntaxException("", "all empty parts");
        }
        if (!emptyString(encoded) && !emptyString(encoded3) && encoded3.length() > 0 && encoded3.charAt(0) != '/') {
            throw new URISyntaxException(encoded3.get(), "path doesn't start with '/'");
        }
        final StringBuilder sb = new StringBuilder();
        if (!emptyString(encoded)) {
            sb.append(encoded);
            sb.append(':');
        }
        if (!emptyString(encoded2)) {
            sb.append("//");
            sb.append(encoded2.get());
        }
        if (!emptyString(encoded3)) {
            sb.append(encoded3.get());
        }
        if (!emptyString(encoded4)) {
            sb.append('?');
            sb.append(encoded4.get());
        }
        if (!emptyString(encoded5)) {
            sb.append('#');
            sb.append(encoded5.get());
        }
        return new Uri(new Encoded(sb.toString()), false, 0);
    }
    
    public static Uri cast(final String s) throws URISyntaxException {
        return new Uri(Encoded.cast(s));
    }
    
    public static Uri valueOfFilepath(final String s) throws URISyntaxException {
        if (emptyString(s)) {
            throw new URISyntaxException("", "empty path");
        }
        if (s.charAt(0) != '/') {
            throw new URISyntaxException(s, "path doesn't start with '/'");
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("file");
        sb.append(':');
        sb.append(encode(s, "/!_-.~"));
        return new Uri(new Encoded(sb.toString()), false, 0);
    }
    
    public static Uri valueOf(final File file) throws URISyntaxException {
        return valueOfFilepath(IOUtil.slashify(file.getAbsolutePath(), true, file.isDirectory()));
    }
    
    public static Uri valueOf(final URI uri) throws URISyntaxException {
        if (uri.isOpaque()) {
            return new Uri(new Encoded(uri.toString()), false, 0);
        }
        return create(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
    }
    
    public static Uri valueOf(final URL url) throws URISyntaxException {
        return valueOf(url.toURI());
    }
    
    public Uri(final Encoded encoded) throws URISyntaxException {
        this(encoded, false, 0);
    }
    
    public final boolean isFileScheme() {
        return null != this.scheme && "file".equals(this.scheme.get());
    }
    
    public final boolean isJarScheme() {
        return null != this.scheme && "jar".equals(this.scheme.get());
    }
    
    public final Encoded getEncoded() {
        return this.input;
    }
    
    @Override
    public final String toString() {
        return this.input.get();
    }
    
    public ASCIIEncoded toASCIIString() {
        synchronized (this.lazyLock) {
            if (null == this.inputASCII) {
                this.inputASCII = new ASCIIEncoded(this.input.get());
            }
            return this.inputASCII;
        }
    }
    
    public final URI toURI() {
        try {
            return new URI(this.input.get());
        }
        catch (URISyntaxException ex) {
            throw new Error(ex);
        }
    }
    
    public final URI toURIReencoded() throws URISyntaxException {
        URI uri;
        if (this.opaque) {
            uri = new URI(decode(this.scheme), decode(this.schemeSpecificPart), decode(this.fragment));
        }
        else if (null != this.host) {
            uri = new URI(decode(this.scheme), decode(this.userInfo), decode(this.host), this.port, decode(this.path), decode(this.query), decode(this.fragment));
        }
        else {
            uri = new URI(decode(this.scheme), decode(this.authority), decode(this.path), decode(this.query), decode(this.fragment));
        }
        return uri;
    }
    
    public final URL toURL() throws MalformedURLException {
        if (!this.absolute) {
            throw new IllegalArgumentException("Cannot convert relative Uri: " + (Object)this.input);
        }
        return new URL(this.input.get());
    }
    
    public final File toFile() {
        if (!this.isFileScheme() || emptyString(this.path)) {
            return null;
        }
        String string;
        if (null == this.authority) {
            string = "";
        }
        else {
            string = "//" + this.authority.decode();
        }
        final String string2 = string + this.path.decode();
        if (!File.separator.equals("\\")) {
            return new File(string2);
        }
        final String replaceAll = Uri.patternSingleFS.matcher(string2).replaceAll("\\\\");
        if (replaceAll.startsWith("\\") && !replaceAll.startsWith("\\\\")) {
            return new File(replaceAll.substring(1));
        }
        return new File(replaceAll);
    }
    
    public final Uri getContainedUri() throws URISyntaxException {
        if (!emptyString(this.schemeSpecificPart)) {
            final StringBuilder sb = new StringBuilder();
            if (this.isJarScheme()) {
                final int lastIndex = this.schemeSpecificPart.lastIndexOf(33);
                if (0 > lastIndex) {
                    throw new URISyntaxException(this.input.get(), "missing jar separator");
                }
                sb.append(this.schemeSpecificPart.get().substring(0, lastIndex));
            }
            else {
                sb.append(this.schemeSpecificPart.get());
            }
            if (!emptyString(this.fragment)) {
                sb.append('#');
                sb.append(this.fragment);
            }
            try {
                final Uri uri = new Uri(new Encoded(sb.toString()), false, this.opaque ? 1 : 0);
                if (null != uri.scheme) {
                    return uri;
                }
            }
            catch (URISyntaxException ex) {
                if (Uri.DEBUG) {
                    System.err.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    
    private static final boolean cutoffLastPathSegementImpl(final StringBuilder sb, final boolean b, final boolean b2, final Encoded encoded) throws URISyntaxException {
        final String string = sb.toString();
        if (0 > string.indexOf("/") && emptyString(encoded)) {
            return false;
        }
        sb.setLength(0);
        sb.append(IOUtil.cleanPathString(string));
        final boolean b3 = sb.length() != string.length();
        final String string2 = sb.toString();
        final int lastIndex = string2.lastIndexOf(33);
        final int lastIndex2 = string2.lastIndexOf("/");
        if (0 > lastIndex || lastIndex2 - 1 > lastIndex) {
            if (b && lastIndex2 < string2.length() - 1) {
                sb.setLength(0);
                sb.append(string2.substring(0, lastIndex2 + 1));
            }
            else if (b2) {
                final int lastIndex3 = string2.lastIndexOf("/", lastIndex2 - 1);
                if (lastIndex3 >= 0) {
                    sb.setLength(0);
                    sb.append(string2.substring(0, lastIndex3 + 1));
                }
            }
        }
        if (sb.length() == string2.length() && (b2 || !b3) && emptyString(encoded)) {
            return false;
        }
        if (!emptyString(encoded)) {
            sb.append(encoded.get());
            final String string3 = sb.toString();
            sb.setLength(0);
            sb.append(IOUtil.cleanPathString(string3));
        }
        return true;
    }
    
    private final Uri cutoffLastPathSegementImpl(final boolean b, final boolean b2, final Encoded encoded) throws URISyntaxException {
        if (this.opaque) {
            if (emptyString(this.schemeSpecificPart)) {
                if (!emptyString(encoded)) {
                    return create(this.scheme, encoded, this.fragment);
                }
                return null;
            }
            else {
                final StringBuilder sb = new StringBuilder();
                final int lastIndex = this.schemeSpecificPart.lastIndexOf(63);
                Encoded substring;
                if (lastIndex >= 0) {
                    substring = this.schemeSpecificPart.substring(lastIndex + 1);
                    sb.append(this.schemeSpecificPart.substring(0, lastIndex).get());
                }
                else {
                    substring = null;
                    sb.append(this.schemeSpecificPart.get());
                }
                if (!cutoffLastPathSegementImpl(sb, b, b2, encoded)) {
                    return null;
                }
                if (!emptyString(substring)) {
                    sb.append('?');
                    sb.append(substring.get());
                }
                return create(this.scheme, new Encoded(sb.toString()), this.fragment);
            }
        }
        else {
            if (emptyString(this.path)) {
                return null;
            }
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(this.path.get());
            if (!cutoffLastPathSegementImpl(sb2, b, b2, encoded)) {
                return null;
            }
            return create(this.scheme, this.userInfo, this.host, this.port, new Encoded(sb2.toString()), this.query, this.fragment);
        }
    }
    
    public final Uri getNormalized() {
        try {
            final Uri cutoffLastPathSegementImpl = this.cutoffLastPathSegementImpl(false, false, null);
            return (null != cutoffLastPathSegementImpl) ? cutoffLastPathSegementImpl : this;
        }
        catch (URISyntaxException ex) {
            if (Uri.DEBUG) {
                System.err.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                ex.printStackTrace();
            }
            return this;
        }
    }
    
    public Uri getDirectory() {
        try {
            final Uri cutoffLastPathSegementImpl = this.cutoffLastPathSegementImpl(true, false, null);
            return (null != cutoffLastPathSegementImpl) ? cutoffLastPathSegementImpl : this;
        }
        catch (URISyntaxException ex) {
            if (Uri.DEBUG) {
                System.err.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                ex.printStackTrace();
            }
            return this;
        }
    }
    
    public final Uri getParent() {
        try {
            return this.cutoffLastPathSegementImpl(true, true, null);
        }
        catch (URISyntaxException ex) {
            if (Uri.DEBUG) {
                System.err.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                ex.printStackTrace();
            }
            return null;
        }
    }
    
    public Uri getRelativeOf(final Encoded encoded) throws URISyntaxException {
        if (emptyString(encoded)) {
            return this.getNormalized();
        }
        return this.cutoffLastPathSegementImpl(true, false, encoded);
    }
    
    public final Uri concat(final Encoded encoded) throws URISyntaxException {
        if (null == encoded) {
            return this;
        }
        return new Uri(this.input.concat(encoded));
    }
    
    public final Uri getNewQuery(final Encoded encoded) throws URISyntaxException {
        if (this.opaque) {
            throw new URISyntaxException(this.input.decode(), "Opaque Uri cannot permute by query");
        }
        return create(this.scheme, this.userInfo, this.host, this.port, this.path, encoded, this.fragment);
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof Uri)) {
            return false;
        }
        final Uri uri = (Uri)o;
        if ((uri.fragment == null && this.fragment != null) || (uri.fragment != null && this.fragment == null)) {
            return false;
        }
        if (uri.fragment != null && this.fragment != null && !this.equalsHexCaseInsensitive(uri.fragment, this.fragment)) {
            return false;
        }
        if ((uri.scheme == null && this.scheme != null) || (uri.scheme != null && this.scheme == null)) {
            return false;
        }
        if (uri.scheme != null && this.scheme != null && !uri.scheme.equalsIgnoreCase(this.scheme)) {
            return false;
        }
        if (uri.opaque && this.opaque) {
            return this.equalsHexCaseInsensitive(uri.schemeSpecificPart, this.schemeSpecificPart);
        }
        if (uri.opaque || this.opaque) {
            return false;
        }
        if (!this.equalsHexCaseInsensitive(this.path, uri.path)) {
            return false;
        }
        if ((uri.query != null && this.query == null) || (uri.query == null && this.query != null)) {
            return false;
        }
        if (uri.query != null && this.query != null && !this.equalsHexCaseInsensitive(uri.query, this.query)) {
            return false;
        }
        if ((uri.authority != null && this.authority == null) || (uri.authority == null && this.authority != null)) {
            return false;
        }
        if (uri.authority == null || this.authority == null) {
            return true;
        }
        if ((uri.host != null && this.host == null) || (uri.host == null && this.host != null)) {
            return false;
        }
        if (uri.host == null && this.host == null) {
            return this.equalsHexCaseInsensitive(uri.authority, this.authority);
        }
        return this.host.equalsIgnoreCase(uri.host) && this.port == uri.port && (uri.userInfo == null || this.userInfo != null) && (uri.userInfo != null || this.userInfo == null) && (uri.userInfo == null || this.userInfo == null || this.equalsHexCaseInsensitive(this.userInfo, uri.userInfo));
    }
    
    @Override
    public final int hashCode() {
        synchronized (this.lazyLock) {
            if (this.hash == -1) {
                this.hash = this.getHashString().hashCode();
            }
            return this.hash;
        }
    }
    
    private String convertHexToLowerCase(final String s) {
        if (s.indexOf(37) == -1) {
            return s;
        }
        final StringBuilder sb = new StringBuilder("");
        int index;
        for (int n = 0; (index = s.indexOf(37, n)) != -1; n = index) {
            sb.append(s.substring(n, index + 1));
            sb.append(s.substring(index + 1, index + 3).toLowerCase());
            index += 3;
        }
        return sb.toString();
    }
    
    private boolean equalsHexCaseInsensitive(final Encoded encoded, final Encoded encoded2) {
        if (encoded.indexOf(37) != encoded2.indexOf(37)) {
            return encoded.equals(encoded2);
        }
        int n;
        int index;
        for (n = 0; (index = encoded.indexOf(37, n)) != -1 && encoded2.indexOf(37, n) == index; index += 3, n = index) {
            if (!encoded.get().substring(n, index).equals(encoded2.get().substring(n, index))) {
                return false;
            }
            if (!encoded.get().substring(index + 1, index + 3).equalsIgnoreCase(encoded2.get().substring(index + 1, index + 3))) {
                return false;
            }
        }
        return encoded.get().substring(n).equals(encoded2.get().substring(n));
    }
    
    private String getHashString() {
        final StringBuilder sb = new StringBuilder();
        if (this.scheme != null) {
            sb.append(this.scheme.get().toLowerCase());
            sb.append(':');
        }
        if (this.opaque) {
            sb.append(this.schemeSpecificPart.get());
        }
        else {
            if (this.authority != null) {
                sb.append("//");
                if (this.host == null) {
                    sb.append(this.authority.get());
                }
                else {
                    if (this.userInfo != null) {
                        sb.append(this.userInfo.get() + "@");
                    }
                    sb.append(this.host.get().toLowerCase());
                    if (this.port != -1) {
                        sb.append(58 + this.port);
                    }
                }
            }
            if (this.path != null) {
                sb.append(this.path.get());
            }
            if (this.query != null) {
                sb.append('?');
                sb.append(this.query.get());
            }
        }
        if (this.fragment != null) {
            sb.append('#');
            sb.append(this.fragment.get());
        }
        return this.convertHexToLowerCase(sb.toString());
    }
    
    private Uri(final Encoded encoded, final boolean b, final int n) throws URISyntaxException {
        this.lazyLock = new Object();
        if (emptyString(encoded)) {
            throw new URISyntaxException(encoded.get(), "empty input");
        }
        String s = encoded.get();
        final int index = s.indexOf(35);
        if (index != -1) {
            validateFragment(encoded, this.fragment = new Encoded(s.substring(index + 1)), index + 1);
            s = s.substring(0, index);
        }
        else {
            this.fragment = null;
        }
        String s2 = encoded.get();
        final int index2 = s.indexOf(58);
        final int index3 = s.indexOf(47);
        final int index4 = s.indexOf(63);
        String s3;
        if (index2 != -1 && (index3 >= index2 || index3 == -1) && (index4 >= index2 || index4 == -1)) {
            this.absolute = true;
            this.scheme = new Encoded(s.substring(0, index2));
            if (this.scheme.length() == 0) {
                failExpecting(encoded, "scheme", index2);
            }
            validateScheme(encoded, this.scheme, 0);
            s3 = s.substring(index2 + 1);
            if (s3.length() == 0) {
                failExpecting(encoded, "scheme-specific-part", index2);
            }
        }
        else {
            this.absolute = false;
            this.scheme = null;
            s3 = s;
        }
        if (this.scheme == null || (s3.length() > 0 && s3.charAt(0) == '/')) {
            this.opaque = false;
            String substring = s3;
            final int index5 = substring.indexOf(63);
            if (index5 != -1) {
                this.query = new Encoded(substring.substring(index5 + 1));
                substring = substring.substring(0, index5);
                validateQuery(encoded, this.query, index3 + 1 + index5);
            }
            else {
                this.query = null;
            }
            String s5;
            int n2;
            if (substring.startsWith("//")) {
                final int index6 = substring.indexOf(47, 2);
                String s4;
                if (index6 != -1) {
                    s4 = substring.substring(2, index6);
                    s5 = substring.substring(index6);
                    n2 = index6;
                }
                else {
                    s4 = substring.substring(2);
                    if (s4.length() == 0 && this.query == null && this.fragment == null) {
                        failExpecting(encoded, "authority, path [, query, fragment]", index6);
                    }
                    s5 = "";
                    n2 = -1;
                }
                if (emptyString(s4)) {
                    this.authority = null;
                }
                else {
                    validateAuthority(encoded, this.authority = new Encoded(s4), index2 + 3);
                }
            }
            else {
                s5 = substring;
                n2 = 0;
                this.authority = null;
            }
            int n3 = 0;
            if (index3 > -1) {
                n3 += index3;
            }
            if (n2 > -1) {
                n3 += n2;
            }
            final int validateEncoded = validateEncoded(s5, "/!_-.~");
            if (0 <= validateEncoded) {
                if (0x0 != (n & 0x1)) {
                    if (Uri.DEBUG_SHOWFIX) {
                        System.err.println("Uri FIX_FILEPATH: input at index " + (n3 + validateEncoded) + ": " + s2);
                        System.err.println("Uri FIX_FILEPATH: ssp at index   " + (n2 + validateEncoded) + ": " + s3);
                        System.err.println("Uri FIX_FILEPATH: path  at index " + validateEncoded + ": " + s5);
                    }
                    final int length = s5.length();
                    s5 = encode(decode(s5), "/!_-.~");
                    validatePath(encoded, s5, n3);
                    final StringBuilder sb = new StringBuilder();
                    if (n2 > 0) {
                        sb.append(s3.substring(0, n2));
                    }
                    sb.append(s5).append(s3.substring(n2 + length));
                    s3 = sb.toString();
                    sb.setLength(0);
                    if (n3 > 0) {
                        sb.append(s2.substring(0, n3));
                    }
                    sb.append(s5).append(s2.substring(n3 + length));
                    s2 = sb.toString();
                    if (Uri.DEBUG_SHOWFIX) {
                        System.err.println("Uri FIX_FILEPATH: result          : " + s5);
                        System.err.println("Uri FIX_FILEPATH: ssp after       : " + s3);
                        System.err.println("Uri FIX_FILEPATH: input after     : " + s2);
                    }
                }
                else {
                    fail(encoded, "invalid path", n3 + validateEncoded);
                }
            }
            this.path = new Encoded(s5);
        }
        else {
            this.opaque = true;
            this.query = null;
            this.path = null;
            this.authority = null;
            validateSsp(encoded, s3, index2 + 1);
        }
        this.schemeSpecificPart = new Encoded(s3);
        this.input = ((s2 == encoded.get()) ? encoded : new Encoded(s2));
        Encoded userInfo = null;
        Encoded host = null;
        int int1 = -1;
        int n4;
        if (null != this.authority) {
            n4 = 1;
            int n5 = 0;
            String s6 = this.authority.get();
            final int index7 = s6.indexOf(64);
            if (index7 != -1) {
                userInfo = new Encoded(s6.substring(0, index7));
                validateUserinfo(this.authority, userInfo, 0);
                s6 = s6.substring(index7 + 1);
                n5 = index7 + 1;
            }
            final int lastIndex = s6.lastIndexOf(58);
            final int index8 = s6.indexOf(93);
            if (lastIndex != -1 && index8 < lastIndex) {
                host = new Encoded(s6.substring(0, lastIndex));
                if (lastIndex < s6.length() - 1) {
                    try {
                        int1 = Integer.parseInt(s6.substring(lastIndex + 1));
                        if (int1 < 0) {
                            if (b) {
                                fail(this.authority, "invalid port <" + (Object)this.authority + ">", n5 + lastIndex + 1);
                            }
                            n4 = 0;
                        }
                    }
                    catch (NumberFormatException ex) {
                        if (b) {
                            fail(this.authority, "invalid port <" + (Object)this.authority + ">, " + ex.getMessage(), n5 + lastIndex + 1);
                        }
                        n4 = 0;
                    }
                }
            }
            else {
                host = new Encoded(s6);
            }
            if (n4 != 0) {
                if (emptyString(host)) {
                    if (b) {
                        fail(this.authority, "empty host <" + (Object)this.authority + ">", n5);
                    }
                    n4 = 0;
                }
                else if (!this.isValidHost(b, host)) {
                    if (b) {
                        fail(this.authority, "invalid host <" + (Object)host + ">", n5);
                    }
                    n4 = 0;
                }
            }
        }
        else {
            n4 = 0;
        }
        if (n4 != 0) {
            this.userInfo = userInfo;
            this.host = host;
            this.port = int1;
            this.hasAuthority = true;
        }
        else {
            this.userInfo = null;
            this.host = null;
            this.port = -1;
            this.hasAuthority = false;
        }
    }
    
    private static void validateScheme(final Encoded encoded, final Encoded encoded2, final int n) throws URISyntaxException {
        final char char1 = encoded2.charAt(0);
        if ((char1 < 'a' || char1 > 'z') && (char1 < 'A' || char1 > 'Z')) {
            fail(encoded, "invalid scheme", n);
        }
        final int validateAlphaNum = validateAlphaNum(encoded2.get(), "+-.");
        if (0 <= validateAlphaNum) {
            fail(encoded, "invalid scheme", n + validateAlphaNum);
        }
    }
    
    private static void validateSsp(final Encoded encoded, final String s, final int n) throws URISyntaxException {
        final int validateEncoded = validateEncoded(s, "_-.~,;:$&+=!*'()@/?[]\\\"");
        if (0 <= validateEncoded) {
            fail(encoded, "invalid scheme-specific-part", n + validateEncoded);
        }
    }
    
    private static void validateAuthority(final Encoded encoded, final Encoded encoded2, final int n) throws URISyntaxException {
        final int validateEncoded = validateEncoded(encoded2.get(), "@[]_-.~,;:$&+=");
        if (0 <= validateEncoded) {
            fail(encoded, "invalid authority", n + validateEncoded);
        }
    }
    
    private static void validatePath(final Encoded encoded, final String s, final int n) throws URISyntaxException {
        final int validateEncoded = validateEncoded(s, "/!_-.~");
        if (0 <= validateEncoded) {
            fail(encoded, "invalid path", n + validateEncoded);
        }
    }
    
    private static void validateQuery(final Encoded encoded, final Encoded encoded2, final int n) throws URISyntaxException {
        final int validateEncoded = validateEncoded(encoded2.get(), "_-.~,;:$&+=!*'()@/?[]\\\"");
        if (0 <= validateEncoded) {
            fail(encoded, "invalid query", n + validateEncoded);
        }
    }
    
    private static void validateFragment(final Encoded encoded, final Encoded encoded2, final int n) throws URISyntaxException {
        final int validateEncoded = validateEncoded(encoded2.get(), "_-.~,;:$&+=!*'()@/?#[]");
        if (0 <= validateEncoded) {
            fail(encoded, "invalid fragment", n + validateEncoded);
        }
    }
    
    private static void validateUserinfo(final Encoded encoded, final Encoded encoded2, final int n) throws URISyntaxException {
        for (int i = 0; i < encoded2.length(); ++i) {
            final char char1 = encoded2.charAt(i);
            if (char1 == ']' || char1 == '[') {
                fail(encoded, "invalid userinfo", n + i);
            }
        }
    }
    
    private boolean isValidHost(final boolean b, final Encoded encoded) throws URISyntaxException {
        if (encoded.charAt(0) == '[') {
            if (encoded.charAt(encoded.length() - 1) != ']') {
                fail(this.input, "invalid host, missing closing ipv6: " + (Object)encoded, 0);
            }
            if (!isValidIP6Address(encoded.get())) {
                fail(this.input, "invalid ipv6: " + (Object)encoded, 0);
            }
            return true;
        }
        if (encoded.indexOf(91) != -1 || encoded.indexOf(93) != -1) {
            fail(this.input, "invalid host: " + (Object)encoded, 0);
        }
        final int lastIndex = encoded.lastIndexOf(46);
        if (lastIndex < 0 || lastIndex == encoded.length() - 1 || !Character.isDigit(encoded.charAt(lastIndex + 1))) {
            if (isValidDomainName(encoded)) {
                return true;
            }
            if (b) {
                fail(this.input, "invalid host, invalid domain-name or ipv4: " + (Object)encoded, 0);
            }
            return false;
        }
        else {
            if (isValidIPv4Address(encoded.get())) {
                return true;
            }
            if (b) {
                fail(this.input, "invalid host, invalid ipv4: " + (Object)encoded, 0);
            }
            return false;
        }
    }
    
    private static boolean isValidDomainName(final Encoded encoded) {
        final String value = encoded.get();
        if (0 <= validateAlphaNum(value, "-.")) {
            return false;
        }
        String nextToken = null;
        final StringTokenizer stringTokenizer = new StringTokenizer(value, ".");
        while (stringTokenizer.hasMoreTokens()) {
            nextToken = stringTokenizer.nextToken();
            if (nextToken.startsWith("-") || nextToken.endsWith("-")) {
                return false;
            }
        }
        if (!nextToken.equals(value)) {
            final char char1 = nextToken.charAt(0);
            if (char1 >= '0' && char1 <= '9') {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isValidIPv4Address(final String s) {
        try {
            final int index = s.indexOf(46);
            final int int1 = Integer.parseInt(s.substring(0, index));
            if (int1 < 0 || int1 > 255) {
                return false;
            }
            final int index2 = s.indexOf(46, index + 1);
            final int int2 = Integer.parseInt(s.substring(index + 1, index2));
            if (int2 < 0 || int2 > 255) {
                return false;
            }
            final int index3 = s.indexOf(46, index2 + 1);
            final int int3 = Integer.parseInt(s.substring(index2 + 1, index3));
            if (int3 < 0 || int3 > 255) {
                return false;
            }
            final int int4 = Integer.parseInt(s.substring(index3 + 1));
            if (int4 < 0 || int4 > 255) {
                return false;
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    private static boolean isValidIP6Address(final String s) {
        final int length = s.length();
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        String string = "";
        char char1 = '\0';
        int n4 = 0;
        if (length < 2) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final char c = char1;
            char1 = s.charAt(i);
            switch (char1) {
                case 91: {
                    if (i != 0) {
                        return false;
                    }
                    if (s.charAt(length - 1) != ']') {
                        return false;
                    }
                    if (s.charAt(1) == ':' && s.charAt(2) != ':') {
                        return false;
                    }
                    n4 = 1;
                    if (length < 4) {
                        return false;
                    }
                    break;
                }
                case 93: {
                    if (i != length - 1) {
                        return false;
                    }
                    if (s.charAt(0) != '[') {
                        return false;
                    }
                    break;
                }
                case 46: {
                    if (++n3 > 3) {
                        return false;
                    }
                    if (!isValidIP4Word(string)) {
                        return false;
                    }
                    if (n2 != 6 && n == 0) {
                        return false;
                    }
                    if (n2 == 7 && s.charAt(0 + n4) != ':' && s.charAt(1 + n4) != ':') {
                        return false;
                    }
                    string = "";
                    break;
                }
                case 58: {
                    if (++n2 > 7) {
                        return false;
                    }
                    if (n3 > 0) {
                        return false;
                    }
                    if (c == ':') {
                        if (n != 0) {
                            return false;
                        }
                        n = 1;
                    }
                    string = "";
                    break;
                }
                default: {
                    if (string.length() > 3) {
                        return false;
                    }
                    if (!isValidHexChar(char1)) {
                        return false;
                    }
                    string += char1;
                    break;
                }
            }
        }
        if (n3 > 0) {
            if (n3 != 3 || !isValidIP4Word(string)) {
                return false;
            }
        }
        else {
            if (n2 != 7 && n == 0) {
                return false;
            }
            if (string == "" && s.charAt(length - 1 - n4) != ':' && s.charAt(length - 2 - n4) != ':') {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isValidIP4Word(final String s) {
        if (s.length() < 1 || s.length() > 3) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 < '0' || char1 > '9') {
                return false;
            }
        }
        return Integer.parseInt(s) <= 255;
    }
    
    private static int validateEncoded(final String s, final String s2) {
        int i = 0;
    Label_0002:
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == '%') {
                while (i + 2 < s.length()) {
                    final int digit = Character.digit(s.charAt(i + 1), 16);
                    final int digit2 = Character.digit(s.charAt(i + 2), 16);
                    if (digit == -1 || digit2 == -1) {
                        throw new IllegalArgumentException("invalid hex-digits at index " + i + ": " + s.substring(i, i + 3));
                    }
                    i += 3;
                    if (i >= s.length()) {
                        continue Label_0002;
                    }
                    if (s.charAt(i) != '%') {
                        continue Label_0002;
                    }
                }
                throw new IllegalArgumentException("missing '%' hex-digits at index " + i);
            }
            if ((char1 < 'a' || char1 > 'z') && (char1 < 'A' || char1 > 'Z') && (char1 < '0' || char1 > '9') && s2.indexOf(char1) <= -1 && (char1 <= '\u007f' || Character.isSpaceChar(char1) || Character.isISOControl(char1))) {
                return i;
            }
            ++i;
        }
        return -1;
    }
    
    private static int validateAlphaNum(final String s, final String s2) {
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ((char1 < 'a' || char1 > 'z') && (char1 < 'A' || char1 > 'Z') && (char1 < '0' || char1 > '9') && s2.indexOf(char1) <= -1) {
                return i;
            }
        }
        return -1;
    }
    
    private static boolean isValidHexChar(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }
    
    private static boolean emptyString(final Encoded encoded) {
        return null == encoded || 0 == encoded.length();
    }
    
    private static boolean emptyString(final String s) {
        return null == s || 0 == s.length();
    }
    
    private static void fail(final Encoded encoded, final String s, final int n) throws URISyntaxException {
        throw new URISyntaxException(encoded.get(), s, n);
    }
    
    private static void failExpecting(final Encoded encoded, final String s, final int n) throws URISyntaxException {
        fail(encoded, "Expecting " + s, n);
    }
    
    static {
        Debug.initSingleton();
        DEBUG = (IOUtil.DEBUG || Debug.debug("Uri"));
        DEBUG_SHOWFIX = PropertyAccess.isPropertyDefined("jogamp.debug.Uri.ShowFix", true);
        patternSingleFS = Pattern.compile("/{1}");
    }
    
    public static class Encoded implements Comparable<Encoded>, CharSequence
    {
        private final String s;
        
        public static Encoded cast(final String s) {
            return new Encoded(s);
        }
        
        Encoded(final String s) {
            this.s = s;
        }
        
        public Encoded(final String s, final String s2) {
            this.s = Uri.encode(s, s2);
        }
        
        public boolean isASCII() {
            return false;
        }
        
        public final String get() {
            return this.s;
        }
        
        public final String decode() {
            return Uri.decode(this.s);
        }
        
        @Override
        public final String toString() {
            return this.s;
        }
        
        @Override
        public final int hashCode() {
            return this.s.hashCode();
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof Encoded) {
                return this.s.equals(((Encoded)o).s);
            }
            return this.s.equals(o);
        }
        
        @Override
        public final int length() {
            return this.s.length();
        }
        
        @Override
        public final char charAt(final int n) {
            return this.s.charAt(n);
        }
        
        @Override
        public final CharSequence subSequence(final int n, final int n2) {
            return this.s.subSequence(n, n2);
        }
        
        @Override
        public final int compareTo(final Encoded encoded) {
            return this.s.compareTo(encoded.s);
        }
        
        public Encoded concat(final Encoded encoded) {
            return new Encoded(this.s.concat(encoded.s));
        }
        
        public final Encoded substring(final int n) {
            return new Encoded(this.s.substring(n));
        }
        
        public final Encoded substring(final int n, final int n2) {
            return new Encoded(this.s.substring(n, n2));
        }
        
        public final int indexOf(final int n) {
            return this.s.indexOf(n);
        }
        
        public final int indexOf(final int n, final int n2) {
            return this.s.indexOf(n, n2);
        }
        
        public final int indexOf(final String s) {
            return this.s.indexOf(s);
        }
        
        public final int indexOf(final String s, final int n) {
            return this.s.indexOf(s, n);
        }
        
        public final int lastIndexOf(final int n) {
            return this.s.lastIndexOf(n);
        }
        
        public int lastIndexOf(final int n, final int n2) {
            return this.s.lastIndexOf(n, n2);
        }
        
        public int lastIndexOf(final String s) {
            return this.s.lastIndexOf(s);
        }
        
        public int lastIndexOf(final String s, final int n) {
            return this.s.lastIndexOf(s, n);
        }
        
        public boolean startsWith(final String s) {
            return this.s.startsWith(s);
        }
        
        public boolean startsWith(final String s, final int n) {
            return this.s.startsWith(s, n);
        }
        
        public boolean endsWith(final String s) {
            return this.s.endsWith(s);
        }
        
        public final boolean equalsIgnoreCase(final Encoded encoded) {
            return this.s.equalsIgnoreCase(encoded.s);
        }
    }
    
    public static class ASCIIEncoded extends Encoded
    {
        public static ASCIIEncoded cast(final String s) {
            return new ASCIIEncoded(s, (Object)null);
        }
        
        private ASCIIEncoded(final String s, final Object o) {
            super(s);
        }
        
        public ASCIIEncoded(final String s) {
            super(Uri.encodeToASCIIString(s));
        }
        
        @Override
        public boolean isASCII() {
            return true;
        }
    }
}
