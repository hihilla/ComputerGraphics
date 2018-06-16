// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.io.IOException;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLStreamHandler;

public class AssetURLStreamHandler extends URLStreamHandler
{
    AssetURLContext ctx;
    
    public AssetURLStreamHandler(final AssetURLContext ctx) {
        this.ctx = ctx;
    }
    
    @Override
    protected URLConnection openConnection(final URL url) throws IOException {
        final AssetURLConnection assetURLConnection = new AssetURLConnection(url, this.ctx);
        assetURLConnection.connect();
        return assetURLConnection;
    }
}
