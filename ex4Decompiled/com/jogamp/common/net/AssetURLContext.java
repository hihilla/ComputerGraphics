// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.io.FileNotFoundException;
import java.io.File;
import com.jogamp.common.os.AndroidVersion;
import java.net.URISyntaxException;
import com.jogamp.common.util.IOUtil;
import java.io.IOException;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URLStreamHandler;
import java.net.URL;

public abstract class AssetURLContext implements PiggybackURLContext
{
    private static final boolean DEBUG;
    public static final String asset_protocol = "asset";
    public static final String asset_protocol_prefix = "asset:";
    public static final String assets_folder = "assets/";
    
    public static AssetURLContext create(final ClassLoader classLoader) {
        return new AssetURLContext() {
            @Override
            public ClassLoader getClassLoader() {
                return classLoader;
            }
        };
    }
    
    public static AssetURLStreamHandler createHandler(final ClassLoader classLoader) {
        return new AssetURLStreamHandler(create(classLoader));
    }
    
    public static URL createURL(final String s, final ClassLoader classLoader) throws MalformedURLException {
        return new URL(null, s.startsWith("asset:") ? s : ("asset:" + s), createHandler(classLoader));
    }
    
    public static URL createURL(final String s) throws MalformedURLException {
        return new URL(s.startsWith("asset:") ? s : ("asset:" + s));
    }
    
    public static URLStreamHandler getRegisteredHandler() {
        final GenericURLStreamHandlerFactory register = GenericURLStreamHandlerFactory.register();
        return (null != register) ? register.getHandler("asset") : null;
    }
    
    public static boolean registerHandler(final ClassLoader classLoader) {
        final GenericURLStreamHandlerFactory register = GenericURLStreamHandlerFactory.register();
        if (null != register) {
            register.setHandler("asset", createHandler(classLoader));
            return true;
        }
        return false;
    }
    
    public abstract ClassLoader getClassLoader();
    
    @Override
    public String getImplementedProtocol() {
        return "asset";
    }
    
    @Override
    public URLConnection resolve(final String s) throws IOException {
        return resolve(s, this.getClassLoader());
    }
    
    public static URLConnection resolve(String cleanPathString, final ClassLoader classLoader) throws IOException {
        URL url = null;
        URLConnection urlConnection = null;
        int n = -1;
        if (AssetURLContext.DEBUG) {
            System.err.println("AssetURLContext.resolve: <" + cleanPathString + ">");
        }
        try {
            cleanPathString = IOUtil.cleanPathString(cleanPathString);
        }
        catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
        try {
            url = new URL(cleanPathString);
            urlConnection = open(url);
            n = ((null != urlConnection) ? 1 : -1);
        }
        catch (MalformedURLException ex2) {
            if (AssetURLContext.DEBUG) {
                System.err.println("FAIL(1): " + ex2.getMessage());
            }
        }
        if (null == urlConnection && null != classLoader) {
            String substring;
            for (substring = cleanPathString; substring.startsWith("/"); substring = substring.substring(1)) {}
            if (AndroidVersion.isAvailable) {
                substring = (substring.startsWith("assets/") ? substring : ("assets/" + substring));
            }
            url = classLoader.getResource(substring);
            urlConnection = open(url);
            n = ((null != urlConnection) ? 2 : -1);
        }
        if (null == urlConnection) {
            try {
                final File file = new File(cleanPathString);
                if (file.exists()) {
                    url = Uri.valueOf(file).toURL();
                    urlConnection = open(url);
                    n = ((null != urlConnection) ? 3 : -1);
                }
            }
            catch (Throwable t) {
                if (AssetURLContext.DEBUG) {
                    System.err.println("FAIL(3): " + t.getMessage());
                }
            }
        }
        if (AssetURLContext.DEBUG) {
            System.err.println("AssetURLContext.resolve: type " + n + ": url <" + url + ">, conn <" + urlConnection + ">, connURL <" + ((null != urlConnection) ? urlConnection.getURL() : null) + ">");
        }
        if (null == urlConnection) {
            throw new FileNotFoundException("Could not look-up: " + cleanPathString + " as URL, w/ ClassLoader or as File");
        }
        return urlConnection;
    }
    
    private static URLConnection open(final URL url) {
        if (null == url) {
            return null;
        }
        try {
            final URLConnection openConnection = url.openConnection();
            openConnection.connect();
            return openConnection;
        }
        catch (IOException ex) {
            if (AssetURLContext.DEBUG) {
                System.err.println("FAIL(2): " + ex.getMessage());
            }
            return null;
        }
    }
    
    static {
        DEBUG = IOUtil.DEBUG;
    }
}
