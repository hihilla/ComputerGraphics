// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import java.nio.ByteBuffer;

public class JAWTFactory
{
    public static final int JNIInvalidRefType = 0;
    public static final int JNILocalRefType = 1;
    public static final int JNIGlobalRefType = 2;
    public static final int JNIWeakGlobalRefType = 3;
    public static final int JNI_ENOMEM = -4;
    public static final int JNI_EEXIST = -5;
    public static final int JNI_ABORT = 2;
    public static final int JAWT_LOCK_CLIP_CHANGED = 2;
    public static final int JAWT_VERSION_1_3 = 65539;
    public static final int JNI_ERR = -1;
    public static final int JAWT_LOCK_ERROR = 1;
    public static final int JAWT_VERSION_1_4 = 65540;
    public static final int JAWT_LOCK_SURFACE_CHANGED = 8;
    public static final int JNI_EVERSION = -3;
    public static final int JNI_VERSION_1_1 = 65537;
    public static final int JNI_VERSION_1_2 = 65538;
    public static final int JNI_OK = 0;
    public static final int JNI_VERSION_1_6 = 65542;
    public static final int JNI_VERSION_1_4 = 65540;
    public static final int JAWT_LOCK_BOUNDS_CHANGED = 4;
    public static final int JNI_FALSE = 0;
    public static final int JNI_EDETACHED = -2;
    public static final int JNI_EINVAL = -6;
    public static final int JNI_TRUE = 1;
    public static final int JNI_COMMIT = 1;
    
    public static boolean JAWT_GetAWT(final JAWT jawt) {
        return JAWT_GetAWT1((jawt == null) ? null : jawt.getBuffer());
    }
    
    private static native boolean JAWT_GetAWT1(final ByteBuffer p0);
}