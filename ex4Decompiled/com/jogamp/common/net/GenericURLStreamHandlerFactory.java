// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net;

import java.security.AccessController;
import java.net.URL;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.net.URLStreamHandler;
import java.util.Map;
import java.net.URLStreamHandlerFactory;

public class GenericURLStreamHandlerFactory implements URLStreamHandlerFactory
{
    private static GenericURLStreamHandlerFactory factory;
    private final Map<String, URLStreamHandler> protocolHandlers;
    
    private GenericURLStreamHandlerFactory() {
        this.protocolHandlers = new HashMap<String, URLStreamHandler>();
    }
    
    public final synchronized URLStreamHandler setHandler(final String s, final URLStreamHandler urlStreamHandler) {
        return this.protocolHandlers.put(s, urlStreamHandler);
    }
    
    public final synchronized URLStreamHandler getHandler(final String s) {
        return this.protocolHandlers.get(s);
    }
    
    @Override
    public final synchronized URLStreamHandler createURLStreamHandler(final String s) {
        return this.getHandler(s);
    }
    
    public static synchronized GenericURLStreamHandlerFactory register() {
        if (null == GenericURLStreamHandlerFactory.factory) {
            GenericURLStreamHandlerFactory.factory = AccessController.doPrivileged((PrivilegedAction<GenericURLStreamHandlerFactory>)new PrivilegedAction<GenericURLStreamHandlerFactory>() {
                @Override
                public GenericURLStreamHandlerFactory run() {
                    boolean b = false;
                    final GenericURLStreamHandlerFactory urlStreamHandlerFactory = new GenericURLStreamHandlerFactory(null);
                    try {
                        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
                        b = true;
                    }
                    catch (Throwable t) {
                        System.err.println("GenericURLStreamHandlerFactory: Setting URLStreamHandlerFactory failed: " + t.getMessage());
                    }
                    return b ? urlStreamHandlerFactory : null;
                }
            });
        }
        return GenericURLStreamHandlerFactory.factory;
    }
    
    static {
        GenericURLStreamHandlerFactory.factory = null;
    }
}
