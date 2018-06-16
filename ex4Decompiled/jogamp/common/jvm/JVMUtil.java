// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.jvm;

import com.jogamp.common.nio.Buffers;
import jogamp.common.Debug;
import java.nio.ByteBuffer;

public class JVMUtil
{
    private static final boolean DEBUG;
    
    public static void initSingleton() {
    }
    
    private static native boolean initialize(final ByteBuffer p0);
    
    static {
        DEBUG = Debug.debug("JVMUtil");
        if (!initialize(Buffers.newDirectByteBuffer(64))) {
            throw new RuntimeException("Failed to initialize the JVMUtil " + Thread.currentThread().getName());
        }
        if (JVMUtil.DEBUG) {
            new Exception("JVMUtil.initSingleton() .. initialized " + Thread.currentThread().getName()).printStackTrace();
        }
    }
}
