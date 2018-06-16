// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public abstract class PiggybackURLConnection<I extends PiggybackURLContext> extends URLConnection
{
    protected URL subUrl;
    protected URLConnection subConn;
    protected I context;
    
    protected PiggybackURLConnection(final URL url, final I context) {
        super(url);
        this.context = context;
    }
    
    @Override
    public synchronized void connect() throws IOException {
        if (!this.connected) {
            this.subConn = this.context.resolve(this.url.getPath());
            this.subUrl = this.subConn.getURL();
            this.connected = true;
        }
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if (!this.connected) {
            throw new IOException("not connected");
        }
        return this.subConn.getInputStream();
    }
    
    public abstract String getEntryName() throws IOException;
    
    public URL getSubProtocol() throws IOException {
        if (!this.connected) {
            throw new IOException("not connected");
        }
        return this.subUrl;
    }
}
