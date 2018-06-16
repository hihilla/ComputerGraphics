// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

abstract class FFMPEGNatives
{
    private static final Object mutex_avcodec_openclose_jni;
    
    final boolean initSymbols0(final long[] array, final int n) {
        return this.initSymbols0(FFMPEGNatives.mutex_avcodec_openclose_jni, array, n);
    }
    
    abstract boolean initSymbols0(final Object p0, final long[] p1, final int p2);
    
    abstract int getAvUtilMajorVersionCC0();
    
    abstract int getAvFormatMajorVersionCC0();
    
    abstract int getAvCodecMajorVersionCC0();
    
    abstract int getAvResampleMajorVersionCC0();
    
    abstract int getSwResampleMajorVersionCC0();
    
    abstract long createInstance0(final FFMPEGMediaPlayer p0, final boolean p1, final boolean p2, final boolean p3);
    
    abstract void destroyInstance0(final long p0);
    
    abstract void setStream0(final long p0, final String p1, final boolean p2, final int p3, final String p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    abstract void setGLFuncs0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    abstract int getVideoPTS0(final long p0);
    
    abstract int getAudioPTS0(final long p0);
    
    abstract int readNextPacket0(final long p0, final int p1, final int p2, final int p3);
    
    abstract int play0(final long p0);
    
    abstract int pause0(final long p0);
    
    abstract int seek0(final long p0, final int p1);
    
    static {
        mutex_avcodec_openclose_jni = new Object();
    }
}
