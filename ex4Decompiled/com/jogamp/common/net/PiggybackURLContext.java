// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.io.IOException;
import java.net.URLConnection;

public interface PiggybackURLContext
{
    String getImplementedProtocol();
    
    URLConnection resolve(final String p0) throws IOException;
}
