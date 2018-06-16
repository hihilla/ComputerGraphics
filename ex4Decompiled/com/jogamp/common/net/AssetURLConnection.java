// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.net.JarURLConnection;
import java.io.IOException;
import java.net.URL;

public class AssetURLConnection extends PiggybackURLConnection<AssetURLContext>
{
    public AssetURLConnection(final URL url, final AssetURLContext assetURLContext) {
        super(url, assetURLContext);
    }
    
    @Override
    public String getEntryName() throws IOException {
        if (!this.connected) {
            throw new IOException("not connected");
        }
        String s;
        if (this.subConn instanceof JarURLConnection) {
            s = ((JarURLConnection)this.subConn).getEntryName();
        }
        else {
            s = this.subConn.getURL().getPath();
        }
        if (s.startsWith("assets/")) {
            return s.substring("assets/".length());
        }
        return s;
    }
}
