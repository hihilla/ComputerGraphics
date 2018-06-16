// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.common.util.IntIntHashMap;

public class GLBufferStateTracker
{
    protected static final boolean DEBUG;
    private final IntIntHashMap bindingMap;
    private static final int bindingNotFound = -1;
    private final int[] bufTmp;
    
    public GLBufferStateTracker() {
        this.bufTmp = new int[1];
        (this.bindingMap = new IntIntHashMap()).setKeyNotFoundValue(-1);
        this.setBoundBufferObject(34962, 0);
        this.setBoundBufferObject(36671, 0);
        this.setBoundBufferObject(34963, 0);
        this.setBoundBufferObject(35051, 0);
        this.setBoundBufferObject(35052, 0);
    }
    
    private static final int getQueryName(final int n) {
        switch (n) {
            case 34962: {
                return 34964;
            }
            case 37568: {
                return 37569;
            }
            case 36662: {
                return 36662;
            }
            case 36663: {
                return 36663;
            }
            case 36671: {
                return 36675;
            }
            case 37102: {
                return 37103;
            }
            case 34963: {
                return 34965;
            }
            case 35051: {
                return 35053;
            }
            case 35052: {
                return 35055;
            }
            case 37266: {
                return 37267;
            }
            case 37074: {
                return 37075;
            }
            case 35882: {
                return 35884;
            }
            case 35982: {
                return 35983;
            }
            case 35345: {
                return 35368;
            }
            case 34229: {
                return 34229;
            }
            default: {
                throw new GLException(String.format("GL_INVALID_ENUM\u200b: Invalid binding target 0x%X", n));
            }
        }
    }
    
    private static final void checkTargetName(final int n) {
        switch (n) {
            case 34229:
            case 34962:
            case 34963:
            case 35051:
            case 35052:
            case 35345:
            case 35882:
            case 35982:
            case 36662:
            case 36663:
            case 36671:
            case 37074:
            case 37102:
            case 37266:
            case 37568: {}
            default: {
                throw new GLException(String.format("GL_INVALID_ENUM\u200b: Invalid binding target 0x%X", n));
            }
        }
    }
    
    public final void setBoundBufferObject(final int n, final int n2) {
        checkTargetName(n);
        final int put = this.bindingMap.put(n, n2);
        if (GLBufferStateTracker.DEBUG) {
            System.err.println("GLBufferStateTracker.setBoundBufferObject() target " + this.toHexString(n) + ": " + this.toHexString(put) + " -> " + this.toHexString(n2));
        }
    }
    
    public final int getBoundBufferObject(final int n, final GL gl) {
        final int value = this.bindingMap.get(n);
        if (-1 != value) {
            if (GLBufferStateTracker.DEBUG) {
                System.err.println("GLBufferStateTracker.getBoundBufferObject() [mapped value]: target 0x" + Integer.toHexString(n) + " -> mapped bound buffer 0x" + Integer.toHexString(value));
            }
            return value;
        }
        final int queryName = getQueryName(n);
        if (queryName != 0) {
            final int glGetError = gl.glGetError();
            gl.glGetIntegerv(queryName, this.bufTmp, 0);
            final int glGetError2 = gl.glGetError();
            int n2;
            if (glGetError2 == 0) {
                n2 = this.bufTmp[0];
            }
            else {
                n2 = 0;
            }
            if (GLBufferStateTracker.DEBUG) {
                System.err.println("GLBufferStateTracker.getBoundBufferObject() glerr[pre " + this.toHexString(glGetError) + ", post " + this.toHexString(glGetError2) + "], [queried value]: target " + this.toHexString(n) + " / query " + this.toHexString(queryName) + " -> mapped bound buffer " + this.toHexString(n2));
            }
            this.setBoundBufferObject(n, n2);
            return n2;
        }
        return 0;
    }
    
    public final void clear() {
        if (GLBufferStateTracker.DEBUG) {
            System.err.println("GLBufferStateTracker.clear() - Thread " + Thread.currentThread().getName());
        }
        this.bindingMap.clear();
    }
    
    private final String toHexString(final int n) {
        return Integer.toHexString(n);
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.GLBufferStateTracker", true);
    }
}
