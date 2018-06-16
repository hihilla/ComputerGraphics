// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

class FFMPEGv08Natives extends FFMPEGNatives
{
    @Override
    native boolean initSymbols0(final Object p0, final long[] p1, final int p2);
    
    @Override
    native int getAvUtilMajorVersionCC0();
    
    @Override
    native int getAvFormatMajorVersionCC0();
    
    @Override
    native int getAvCodecMajorVersionCC0();
    
    @Override
    native int getAvResampleMajorVersionCC0();
    
    @Override
    native int getSwResampleMajorVersionCC0();
    
    @Override
    native long createInstance0(final FFMPEGMediaPlayer p0, final boolean p1, final boolean p2, final boolean p3);
    
    @Override
    native void destroyInstance0(final long p0);
    
    @Override
    native void setStream0(final long p0, final String p1, final boolean p2, final int p3, final String p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    @Override
    native void setGLFuncs0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    @Override
    native int getVideoPTS0(final long p0);
    
    @Override
    native int getAudioPTS0(final long p0);
    
    @Override
    native int readNextPacket0(final long p0, final int p1, final int p2, final int p3);
    
    @Override
    native int play0(final long p0);
    
    @Override
    native int pause0(final long p0);
    
    @Override
    native int seek0(final long p0, final int p1);
}
