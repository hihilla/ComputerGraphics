// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GL;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;

public class GLBuffers extends Buffers
{
    public static final boolean isSignedGLType(final int n) {
        switch (n) {
            case 5121:
            case 5123:
            case 5125:
            case 34552: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public static final boolean isGLTypeFixedPoint(final int n) {
        switch (n) {
            case 5126:
            case 5130:
            case 5131:
            case 36193: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public static final int sizeOfGLType(final int n) {
        switch (n) {
            case 5120:
            case 5121:
            case 32818:
            case 33634: {
                return 1;
            }
            case 5122:
            case 5123:
            case 5131:
            case 32819:
            case 32820:
            case 33635:
            case 33636:
            case 33637:
            case 33638:
            case 34234:
            case 34235:
            case 36193: {
                return 2;
            }
            case 5124:
            case 5125:
            case 5132:
            case 32821:
            case 33639:
            case 33640:
            case 34042:
            case 34552:
            case 34554:
            case 35899:
            case 35902:
            case 36342: {
                return 4;
            }
            case 36269: {
                return 8;
            }
            case 5126: {
                return 4;
            }
            case 5130: {
                return 8;
            }
            default: {
                return -1;
            }
        }
    }
    
    public static final Buffer newDirectGLBuffer(final int n, final int n2) {
        switch (n) {
            case 5120:
            case 5121:
            case 32818:
            case 33634: {
                return Buffers.newDirectByteBuffer(n2);
            }
            case 5122:
            case 5123:
            case 5131:
            case 32819:
            case 32820:
            case 33635:
            case 33636:
            case 33637:
            case 33638:
            case 34234:
            case 34235:
            case 36193: {
                return Buffers.newDirectShortBuffer(n2);
            }
            case 5124:
            case 5125:
            case 5132:
            case 32821:
            case 33639:
            case 33640:
            case 34042:
            case 34552:
            case 34554:
            case 35899:
            case 35902:
            case 36342: {
                return Buffers.newDirectIntBuffer(n2);
            }
            case 36269: {
                return Buffers.newDirectLongBuffer(n2);
            }
            case 5126: {
                return Buffers.newDirectFloatBuffer(n2);
            }
            case 5130: {
                return Buffers.newDirectDoubleBuffer(n2);
            }
            default: {
                return null;
            }
        }
    }
    
    public static final Buffer sliceGLBuffer(final ByteBuffer byteBuffer, final int n, final int n2, final int n3) {
        if (byteBuffer == null || n2 == 0) {
            return null;
        }
        final int position = byteBuffer.position();
        final int limit = byteBuffer.limit();
        byteBuffer.position(n);
        byteBuffer.limit(n + n2);
        Buffer buffer = null;
        switch (n3) {
            case 5120:
            case 5121:
            case 32818:
            case 33634: {
                buffer = byteBuffer.slice().order(byteBuffer.order());
                break;
            }
            case 5122:
            case 5123:
            case 5131:
            case 32819:
            case 32820:
            case 33635:
            case 33636:
            case 33637:
            case 33638:
            case 34234:
            case 34235:
            case 36193: {
                buffer = byteBuffer.slice().order(byteBuffer.order()).asShortBuffer();
                break;
            }
            case 5124:
            case 5125:
            case 5132:
            case 32821:
            case 33639:
            case 33640:
            case 34042:
            case 34552:
            case 34554:
            case 35899:
            case 35902:
            case 36342: {
                buffer = byteBuffer.slice().order(byteBuffer.order()).asIntBuffer();
                break;
            }
            case 36269: {
                buffer = byteBuffer.slice().order(byteBuffer.order()).asLongBuffer();
                break;
            }
            case 5126: {
                buffer = byteBuffer.slice().order(byteBuffer.order()).asFloatBuffer();
                break;
            }
            case 5130: {
                buffer = byteBuffer.slice().order(byteBuffer.order()).asDoubleBuffer();
                break;
            }
        }
        byteBuffer.position(position).limit(limit);
        return buffer;
    }
    
    private static final int glGetInteger(final GL gl, final int n, final int[] array) {
        gl.glGetIntegerv(n, array, 0);
        return array[0];
    }
    
    public static final int sizeof(final GL gl, final int[] array, final int n, int max, int max2, int max3, final boolean b) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7;
        if (b) {
            n7 = glGetInteger(gl, 3333, array);
            if (gl.isGL2ES3()) {
                n2 = glGetInteger(gl, 3330, array);
                n3 = glGetInteger(gl, 3331, array);
                n4 = glGetInteger(gl, 3332, array);
                if (max3 > 1 && gl.isGL2GL3() && gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0) {
                    n5 = glGetInteger(gl, 32876, array);
                    n6 = glGetInteger(gl, 32875, array);
                }
            }
        }
        else {
            n7 = glGetInteger(gl, 3317, array);
            if (gl.isGL2ES3()) {
                n2 = glGetInteger(gl, 3314, array);
                n3 = glGetInteger(gl, 3315, array);
                n4 = glGetInteger(gl, 3316, array);
                if (max3 > 1 && (gl.isGL3ES3() || (gl.isGL2GL3() && gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0))) {
                    n5 = glGetInteger(gl, 32878, array);
                    n6 = glGetInteger(gl, 32877, array);
                }
            }
        }
        max = Math.max(0, max);
        max2 = Math.max(1, max2);
        max3 = Math.max(1, max3);
        final int max4 = Math.max(0, n3);
        final int max5 = Math.max(0, n4);
        final int max6 = Math.max(1, n7);
        final int max7 = Math.max(0, n6);
        final int n8 = (n5 > 0) ? n5 : max2;
        int n9 = ((n2 > 0) ? n2 : max) * n;
        int n10 = max5 * n;
        switch (max6) {
            case 1: {
                break;
            }
            case 2:
            case 4:
            case 8: {
                final int n11 = n9 & max6 - 1;
                if (n11 > 0) {
                    n9 += max6 - n11;
                }
                final int n12 = n10 & max6 - 1;
                if (n12 > 0) {
                    n10 += max6 - n12;
                }
                break;
            }
            default: {
                throw new GLException("Invalid alignment " + max6 + ", must be 2**n (1,2,4,8). Pls notify the maintainer in case this is our bug.");
            }
        }
        return n10 + (max7 + max3 - 1) * n8 * n9 + (max4 + max2 - 1) * n9 + max * n;
    }
    
    public static final int sizeof(final GL gl, final int[] array, final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) throws GLException {
        if (n3 < 0) {
            return 0;
        }
        if (n4 < 0) {
            return 0;
        }
        if (n5 < 0) {
            return 0;
        }
        return sizeof(gl, array, bytesPerPixel(n, n2), n3, n4, n5, b);
    }
    
    public static final int bytesPerPixel(final int n, final int n2) throws GLException {
        int componentCount = componentCount(n);
        int n3 = 0;
        switch (n2) {
            case 6656: {
                if (6400 == n || 6401 == n) {
                    n3 = 1;
                    break;
                }
                throw new GLException("BITMAP type only supported for format COLOR_INDEX and STENCIL_INDEX, not 0x" + Integer.toHexString(n));
            }
            case 5120:
            case 5121: {
                n3 = 1;
                break;
            }
            case 5122:
            case 5123:
            case 5131:
            case 36193: {
                n3 = 2;
                break;
            }
            case 5124:
            case 5125:
            case 5126:
            case 5132: {
                n3 = 4;
                break;
            }
            case 5130: {
                n3 = 8;
                break;
            }
            case 32818:
            case 33634: {
                n3 = 1;
                componentCount = 1;
                break;
            }
            case 32819:
            case 32820:
            case 33635:
            case 33636:
            case 33637:
            case 33638:
            case 34234:
            case 34235: {
                n3 = 2;
                componentCount = 1;
                break;
            }
            case 34552:
            case 34554: {
                n3 = 2;
                componentCount = 2;
                break;
            }
            case 32821:
            case 33639:
            case 33640:
            case 34042:
            case 35899:
            case 35902:
            case 36342: {
                n3 = 4;
                componentCount = 1;
                break;
            }
            case 36269: {
                n3 = 8;
                componentCount = 1;
                break;
            }
            default: {
                throw new GLException("type 0x" + Integer.toHexString(n2) + "/" + "format 0x" + Integer.toHexString(n) + " not supported [yet], pls notify the maintainer in case this is our bug.");
            }
        }
        return componentCount * n3;
    }
    
    public static final int componentCount(final int n) throws GLException {
        int n2 = 0;
        switch (n) {
            case 6400:
            case 6401:
            case 6402:
            case 6403:
            case 6404:
            case 6405:
            case 6406:
            case 6409:
            case 34041:
            case 36244:
            case 36245:
            case 36246: {
                n2 = 1;
                break;
            }
            case 6410:
            case 33319:
            case 33320:
            case 34548:
            case 34553: {
                n2 = 2;
                break;
            }
            case 6407:
            case 32992:
            case 36248:
            case 36250: {
                n2 = 3;
                break;
            }
            case 34233: {
                n2 = 3;
                break;
            }
            case 6408:
            case 32768:
            case 32993:
            case 36249:
            case 36251: {
                n2 = 4;
                break;
            }
            default: {
                throw new GLException("format 0x" + Integer.toHexString(n) + " not supported [yet], pls notify the maintainer in case this is our bug.");
            }
        }
        return n2;
    }
    
    public static final int getNextPowerOf2(int i) {
        if ((i - 1 & i) == 0x0) {
            return i;
        }
        int n;
        for (n = 0; i > 0; i >>= 1, ++n) {}
        return 1 << n;
    }
    
    public static final float[] getFloatArray(final double[] array) {
        int i;
        float[] array2;
        for (i = array.length, array2 = new float[i--]; i >= 0; --i) {
            array2[i] = (float)array[i];
        }
        return array2;
    }
}
