// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

import com.jogamp.common.util.VersionNumber;

class FFMPEGStaticNatives
{
    static VersionNumber getAVVersion(final int n) {
        return new VersionNumber(n >> 16 & 0xFF, n >> 8 & 0xFF, n >> 0 & 0xFF);
    }
    
    static native boolean initIDs0();
    
    static native int getAvVersion0(final long p0);
}
