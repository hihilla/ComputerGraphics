// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.util.IdentityHashMap;

public class GLRendererQuirks
{
    public static final int NoDoubleBufferedPBuffer = 0;
    public static final int NoDoubleBufferedBitmap = 1;
    public static final int NoSetSwapInterval = 2;
    public static final int NoOffscreenBitmap = 3;
    public static final int NoSetSwapIntervalPostRetarget = 4;
    public static final int GLSLBuggyDiscard = 5;
    public static final int GLNonCompliant = 6;
    public static final int GLFlushBeforeRelease = 7;
    public static final int DontCloseX11Display = 8;
    public static final int NeedCurrCtx4ARBPixFmtQueries = 9;
    public static final int NeedCurrCtx4ARBCreateContext = 10;
    public static final int NoFullFBOSupport = 11;
    public static final int GLSLNonCompliant = 12;
    public static final int GL4NeedsGL3Request = 13;
    public static final int GLSharedContextBuggy = 14;
    public static final int GLES3ViaEGLES2Config = 15;
    public static final int SingletonEGLDisplayOnly = 16;
    public static final int NoMultiSamplingBuffers = 17;
    public static final int BuggyColorRenderbuffer = 18;
    public static final int NoPBufferWithAccum = 19;
    public static final int NeedSharedObjectSync = 20;
    public static final int NoARBCreateContext = 21;
    public static final int NoSurfacelessCtx = 22;
    private static final String[] _names;
    private static final IdentityHashMap<String, GLRendererQuirks> stickyDeviceQuirks;
    private int _bitmask;
    
    public static final int getCount() {
        return 23;
    }
    
    public static GLRendererQuirks getStickyDeviceQuirks(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final String uniqueID = abstractGraphicsDevice.getUniqueID();
        final GLRendererQuirks glRendererQuirks = GLRendererQuirks.stickyDeviceQuirks.get(uniqueID);
        GLRendererQuirks glRendererQuirks2;
        if (null == glRendererQuirks) {
            glRendererQuirks2 = new GLRendererQuirks();
            GLRendererQuirks.stickyDeviceQuirks.put(uniqueID, glRendererQuirks2);
        }
        else {
            glRendererQuirks2 = glRendererQuirks;
        }
        return glRendererQuirks2;
    }
    
    public static boolean areSameStickyDevice(final AbstractGraphicsDevice abstractGraphicsDevice, final AbstractGraphicsDevice abstractGraphicsDevice2) {
        return abstractGraphicsDevice.getUniqueID() == abstractGraphicsDevice2.getUniqueID();
    }
    
    public static void addStickyDeviceQuirk(final AbstractGraphicsDevice abstractGraphicsDevice, final int n) throws IllegalArgumentException {
        getStickyDeviceQuirks(abstractGraphicsDevice).addQuirk(n);
    }
    
    public static void addStickyDeviceQuirks(final AbstractGraphicsDevice abstractGraphicsDevice, final int[] array, final int n, final int n2) throws IllegalArgumentException {
        getStickyDeviceQuirks(abstractGraphicsDevice).addQuirks(array, n, n2);
    }
    
    public static void addStickyDeviceQuirks(final AbstractGraphicsDevice abstractGraphicsDevice, final GLRendererQuirks glRendererQuirks) throws IllegalArgumentException {
        getStickyDeviceQuirks(abstractGraphicsDevice).addQuirks(glRendererQuirks);
    }
    
    public static boolean existStickyDeviceQuirk(final AbstractGraphicsDevice abstractGraphicsDevice, final int n) {
        return getStickyDeviceQuirks(abstractGraphicsDevice).exist(n);
    }
    
    public static void pushStickyDeviceQuirks(final AbstractGraphicsDevice abstractGraphicsDevice, final GLRendererQuirks glRendererQuirks) {
        glRendererQuirks.addQuirks(getStickyDeviceQuirks(abstractGraphicsDevice));
    }
    
    public GLRendererQuirks() {
        this._bitmask = 0;
    }
    
    public GLRendererQuirks(final int[] array, final int n, final int n2) throws IllegalArgumentException {
        this();
        this.addQuirks(array, n, n2);
    }
    
    public final void addQuirk(final int n) throws IllegalArgumentException {
        validateQuirk(n);
        this._bitmask |= 1 << n;
    }
    
    public final void addQuirks(final int[] array, final int n, final int n2) throws IllegalArgumentException {
        int n3 = 0;
        if (0 > n + n2 || n + n2 > array.length) {
            throw new IllegalArgumentException("offset and len out of bounds: offset " + n + ", len " + n2 + ", array-len " + array.length);
        }
        for (int i = n; i < n + n2; ++i) {
            final int n4 = array[i];
            validateQuirk(n4);
            n3 |= 1 << n4;
        }
        this._bitmask |= n3;
    }
    
    public final void addQuirks(final GLRendererQuirks glRendererQuirks) {
        this._bitmask |= glRendererQuirks._bitmask;
    }
    
    public final boolean exist(final int n) throws IllegalArgumentException {
        validateQuirk(n);
        return 0x0 != (1 << n & this._bitmask);
    }
    
    public final StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("[");
        int n = 1;
        for (int i = 0; i < getCount(); ++i) {
            if (0x0 != (this._bitmask & 1 << i)) {
                if (n == 0) {
                    sb.append(", ");
                }
                sb.append(toString(i));
                n = 0;
            }
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public final String toString() {
        return this.toString(null).toString();
    }
    
    public static void validateQuirk(final int n) throws IllegalArgumentException {
        if (0 > n || n >= getCount()) {
            throw new IllegalArgumentException("Quirks must be in range [0.." + getCount() + "[, but quirk: " + n);
        }
    }
    
    public static final String toString(final int n) throws IllegalArgumentException {
        validateQuirk(n);
        return GLRendererQuirks._names[n];
    }
    
    static {
        _names = new String[] { "NoDoubleBufferedPBuffer", "NoDoubleBufferedBitmap", "NoSetSwapInterval", "NoOffscreenBitmap", "NoSetSwapIntervalPostRetarget", "GLSLBuggyDiscard", "GLNonCompliant", "GLFlushBeforeRelease", "DontCloseX11Display", "NeedCurrCtx4ARBPixFmtQueries", "NeedCurrCtx4ARBCreateContext", "NoFullFBOSupport", "GLSLNonCompliant", "GL4NeedsGL3Request", "GLSharedContextBuggy", "GLES3ViaEGLES2Config", "SingletonEGLDisplayOnly", "NoMultiSamplingBuffers", "BuggyColorRenderbuffer", "NoPBufferWithAccum", "NeedSharedObjectSync", "NoARBCreateContext", "NoSurfacelessCtx" };
        stickyDeviceQuirks = new IdentityHashMap<String, GLRendererQuirks>();
    }
}
