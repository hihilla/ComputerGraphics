// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.cache;

public class TempCacheReg
{
    public static boolean isTempFileCacheUsed() {
        return null != System.getProperty("jnlp.jogamp.tmp.cache.root");
    }
    
    public static boolean isTempJarCacheUsed() {
        return TempJarCache.isInitialized();
    }
}
