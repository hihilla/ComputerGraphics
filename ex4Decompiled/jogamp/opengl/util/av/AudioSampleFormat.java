// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

public enum AudioSampleFormat
{
    U8, 
    S16, 
    S32, 
    FLT, 
    DBL, 
    U8P, 
    S16P, 
    S32P, 
    FLTP, 
    DBLP, 
    COUNT;
    
    public static AudioSampleFormat valueOf(final int n) throws IllegalArgumentException {
        final AudioSampleFormat[] values = values();
        if (0 <= n && n < values.length) {
            return values[n];
        }
        throw new IllegalArgumentException("Ordinal " + n + " out of range of SampleFormat.values()[0.." + (values.length - 1) + "]");
    }
}
