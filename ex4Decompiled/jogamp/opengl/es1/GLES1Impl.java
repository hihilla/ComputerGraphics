// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.es1;

import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLES3;
import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3bc;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLBufferStorage;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GLException;
import jogamp.opengl.GLBufferStateTracker;
import jogamp.opengl.GLBufferObjectTracker;
import jogamp.opengl.GLStateTracker;
import jogamp.opengl.GLContextImpl;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLES1;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLBase;

public class GLES1Impl implements GLBase, GL, GL2ES1, GLES1
{
    private static final int params_offset = 0;
    private final GLProfile glProfile;
    private final GLContextImpl _context;
    private final GLStateTracker glStateTracker;
    private final GLBufferObjectTracker bufferObjectTracker;
    private final GLBufferStateTracker bufferStateTracker;
    private final GLBufferObjectTracker.CreateStorageDispatch glBufferDataDispatch;
    private final GLBufferObjectTracker.UnmapBufferDispatch glUnmapBufferDispatch;
    private final GLBufferObjectTracker.MapBufferAllDispatch glMapBufferDispatch;
    private final GLBufferObjectTracker.MapBufferRangeDispatch glMapBufferRangeDispatch;
    private final GLES1ProcAddressTable _pat;
    private int[] imageSizeTemp;
    
    @Override
    public void glAlphaFunc(final int n, final float n2) {
        final long addressof_glAlphaFunc = this._pat._addressof_glAlphaFunc;
        if (addressof_glAlphaFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glAlphaFunc"));
        }
        this.dispatch_glAlphaFunc1(n, n2, addressof_glAlphaFunc);
    }
    
    private native void dispatch_glAlphaFunc1(final int p0, final float p1, final long p2);
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        final long addressof_glClearColor = this._pat._addressof_glClearColor;
        if (addressof_glClearColor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearColor"));
        }
        this.dispatch_glClearColor1(n, n2, n3, n4, addressof_glClearColor);
    }
    
    private native void dispatch_glClearColor1(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glClearDepthf(final float n) {
        final long addressof_glClearDepthf = this._pat._addressof_glClearDepthf;
        if (addressof_glClearDepthf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearDepthf"));
        }
        this.dispatch_glClearDepthf1(n, addressof_glClearDepthf);
    }
    
    private native void dispatch_glClearDepthf1(final float p0, final long p1);
    
    @Override
    public void glClipPlanef(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glClipPlanef = this._pat._addressof_glClipPlanef;
        if (addressof_glClipPlanef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanef"));
        }
        this.dispatch_glClipPlanef1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glClipPlanef);
    }
    
    private native void dispatch_glClipPlanef1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glClipPlanef(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"equation_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClipPlanef = this._pat._addressof_glClipPlanef;
        if (addressof_glClipPlanef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanef"));
        }
        this.dispatch_glClipPlanef1(n, array, 4 * n2, false, addressof_glClipPlanef);
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, final float n4) {
        final long addressof_glColor4f = this._pat._addressof_glColor4f;
        if (addressof_glColor4f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColor4f"));
        }
        this.dispatch_glColor4f1(n, n2, n3, n4, addressof_glColor4f);
    }
    
    private native void dispatch_glColor4f1(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        final long addressof_glDepthRangef = this._pat._addressof_glDepthRangef;
        if (addressof_glDepthRangef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangef"));
        }
        this.dispatch_glDepthRangef1(n, n2, addressof_glDepthRangef);
    }
    
    private native void dispatch_glDepthRangef1(final float p0, final float p1, final long p2);
    
    @Override
    public void glFogf(final int n, final float n2) {
        final long addressof_glFogf = this._pat._addressof_glFogf;
        if (addressof_glFogf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogf"));
        }
        this.dispatch_glFogf1(n, n2, addressof_glFogf);
    }
    
    private native void dispatch_glFogf1(final int p0, final float p1, final long p2);
    
    @Override
    public void glFogfv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glFogfv = this._pat._addressof_glFogfv;
        if (addressof_glFogfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogfv"));
        }
        this.dispatch_glFogfv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glFogfv);
    }
    
    private native void dispatch_glFogfv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glFogfv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glFogfv = this._pat._addressof_glFogfv;
        if (addressof_glFogfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogfv"));
        }
        this.dispatch_glFogfv1(n, array, 4 * n2, false, addressof_glFogfv);
    }
    
    @Override
    public void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final long addressof_glFrustumf = this._pat._addressof_glFrustumf;
        if (addressof_glFrustumf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFrustumf"));
        }
        this.dispatch_glFrustumf1(n, n2, n3, n4, n5, n6, addressof_glFrustumf);
    }
    
    private native void dispatch_glFrustumf1(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    @Override
    public void glGetClipPlanef(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetClipPlanef = this._pat._addressof_glGetClipPlanef;
        if (addressof_glGetClipPlanef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetClipPlanef"));
        }
        this.dispatch_glGetClipPlanef1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetClipPlanef);
    }
    
    private native void dispatch_glGetClipPlanef1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetClipPlanef(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"equation_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetClipPlanef = this._pat._addressof_glGetClipPlanef;
        if (addressof_glGetClipPlanef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetClipPlanef"));
        }
        this.dispatch_glGetClipPlanef1(n, array, 4 * n2, false, addressof_glGetClipPlanef);
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetFloatv = this._pat._addressof_glGetFloatv;
        if (addressof_glGetFloatv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloatv"));
        }
        this.dispatch_glGetFloatv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetFloatv);
    }
    
    private native void dispatch_glGetFloatv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFloatv = this._pat._addressof_glGetFloatv;
        if (addressof_glGetFloatv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloatv"));
        }
        this.dispatch_glGetFloatv1(n, array, 4 * n2, false, addressof_glGetFloatv);
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetLightfv = this._pat._addressof_glGetLightfv;
        if (addressof_glGetLightfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetLightfv"));
        }
        this.dispatch_glGetLightfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetLightfv);
    }
    
    private native void dispatch_glGetLightfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetLightfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetLightfv = this._pat._addressof_glGetLightfv;
        if (addressof_glGetLightfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetLightfv"));
        }
        this.dispatch_glGetLightfv1(n, n2, array, 4 * n3, false, addressof_glGetLightfv);
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetMaterialfv = this._pat._addressof_glGetMaterialfv;
        if (addressof_glGetMaterialfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMaterialfv"));
        }
        this.dispatch_glGetMaterialfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetMaterialfv);
    }
    
    private native void dispatch_glGetMaterialfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetMaterialfv = this._pat._addressof_glGetMaterialfv;
        if (addressof_glGetMaterialfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMaterialfv"));
        }
        this.dispatch_glGetMaterialfv1(n, n2, array, 4 * n3, false, addressof_glGetMaterialfv);
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetTexEnvfv = this._pat._addressof_glGetTexEnvfv;
        if (addressof_glGetTexEnvfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnvfv"));
        }
        this.dispatch_glGetTexEnvfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetTexEnvfv);
    }
    
    private native void dispatch_glGetTexEnvfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexEnvfv = this._pat._addressof_glGetTexEnvfv;
        if (addressof_glGetTexEnvfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnvfv"));
        }
        this.dispatch_glGetTexEnvfv1(n, n2, array, 4 * n3, false, addressof_glGetTexEnvfv);
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetTexParameterfv = this._pat._addressof_glGetTexParameterfv;
        if (addressof_glGetTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterfv"));
        }
        this.dispatch_glGetTexParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetTexParameterfv);
    }
    
    private native void dispatch_glGetTexParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameterfv = this._pat._addressof_glGetTexParameterfv;
        if (addressof_glGetTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterfv"));
        }
        this.dispatch_glGetTexParameterfv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameterfv);
    }
    
    @Override
    public void glLightModelf(final int n, final float n2) {
        final long addressof_glLightModelf = this._pat._addressof_glLightModelf;
        if (addressof_glLightModelf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelf"));
        }
        this.dispatch_glLightModelf1(n, n2, addressof_glLightModelf);
    }
    
    private native void dispatch_glLightModelf1(final int p0, final float p1, final long p2);
    
    @Override
    public void glLightModelfv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glLightModelfv = this._pat._addressof_glLightModelfv;
        if (addressof_glLightModelfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelfv"));
        }
        this.dispatch_glLightModelfv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glLightModelfv);
    }
    
    private native void dispatch_glLightModelfv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glLightModelfv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLightModelfv = this._pat._addressof_glLightModelfv;
        if (addressof_glLightModelfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelfv"));
        }
        this.dispatch_glLightModelfv1(n, array, 4 * n2, false, addressof_glLightModelfv);
    }
    
    @Override
    public void glLightf(final int n, final int n2, final float n3) {
        final long addressof_glLightf = this._pat._addressof_glLightf;
        if (addressof_glLightf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightf"));
        }
        this.dispatch_glLightf1(n, n2, n3, addressof_glLightf);
    }
    
    private native void dispatch_glLightf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glLightfv = this._pat._addressof_glLightfv;
        if (addressof_glLightfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightfv"));
        }
        this.dispatch_glLightfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glLightfv);
    }
    
    private native void dispatch_glLightfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glLightfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLightfv = this._pat._addressof_glLightfv;
        if (addressof_glLightfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightfv"));
        }
        this.dispatch_glLightfv1(n, n2, array, 4 * n3, false, addressof_glLightfv);
    }
    
    @Override
    public void glLineWidth(final float n) {
        final long addressof_glLineWidth = this._pat._addressof_glLineWidth;
        if (addressof_glLineWidth == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLineWidth"));
        }
        this.dispatch_glLineWidth1(n, addressof_glLineWidth);
    }
    
    private native void dispatch_glLineWidth1(final float p0, final long p1);
    
    @Override
    public void glLoadMatrixf(final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glLoadMatrixf = this._pat._addressof_glLoadMatrixf;
        if (addressof_glLoadMatrixf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadMatrixf"));
        }
        this.dispatch_glLoadMatrixf1(direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glLoadMatrixf);
    }
    
    private native void dispatch_glLoadMatrixf1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glLoadMatrixf(final float[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"m_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLoadMatrixf = this._pat._addressof_glLoadMatrixf;
        if (addressof_glLoadMatrixf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadMatrixf"));
        }
        this.dispatch_glLoadMatrixf1(array, 4 * n, false, addressof_glLoadMatrixf);
    }
    
    @Override
    public void glMaterialf(final int n, final int n2, final float n3) {
        final long addressof_glMaterialf = this._pat._addressof_glMaterialf;
        if (addressof_glMaterialf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialf"));
        }
        this.dispatch_glMaterialf1(n, n2, n3, addressof_glMaterialf);
    }
    
    private native void dispatch_glMaterialf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glMaterialfv = this._pat._addressof_glMaterialfv;
        if (addressof_glMaterialfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialfv"));
        }
        this.dispatch_glMaterialfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glMaterialfv);
    }
    
    private native void dispatch_glMaterialfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glMaterialfv = this._pat._addressof_glMaterialfv;
        if (addressof_glMaterialfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialfv"));
        }
        this.dispatch_glMaterialfv1(n, n2, array, 4 * n3, false, addressof_glMaterialfv);
    }
    
    @Override
    public void glMultMatrixf(final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glMultMatrixf = this._pat._addressof_glMultMatrixf;
        if (addressof_glMultMatrixf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultMatrixf"));
        }
        this.dispatch_glMultMatrixf1(direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glMultMatrixf);
    }
    
    private native void dispatch_glMultMatrixf1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glMultMatrixf(final float[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"m_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glMultMatrixf = this._pat._addressof_glMultMatrixf;
        if (addressof_glMultMatrixf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultMatrixf"));
        }
        this.dispatch_glMultMatrixf1(array, 4 * n, false, addressof_glMultMatrixf);
    }
    
    @Override
    public void glMultiTexCoord4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long addressof_glMultiTexCoord4f = this._pat._addressof_glMultiTexCoord4f;
        if (addressof_glMultiTexCoord4f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiTexCoord4f"));
        }
        this.dispatch_glMultiTexCoord4f1(n, n2, n3, n4, n5, addressof_glMultiTexCoord4f);
    }
    
    private native void dispatch_glMultiTexCoord4f1(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glNormal3f(final float n, final float n2, final float n3) {
        final long addressof_glNormal3f = this._pat._addressof_glNormal3f;
        if (addressof_glNormal3f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNormal3f"));
        }
        this.dispatch_glNormal3f1(n, n2, n3, addressof_glNormal3f);
    }
    
    private native void dispatch_glNormal3f1(final float p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final long addressof_glOrthof = this._pat._addressof_glOrthof;
        if (addressof_glOrthof == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glOrthof"));
        }
        this.dispatch_glOrthof1(n, n2, n3, n4, n5, n6, addressof_glOrthof);
    }
    
    private native void dispatch_glOrthof1(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    @Override
    public void glPointParameterf(final int n, final float n2) {
        final long addressof_glPointParameterf = this._pat._addressof_glPointParameterf;
        if (addressof_glPointParameterf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterf"));
        }
        this.dispatch_glPointParameterf1(n, n2, addressof_glPointParameterf);
    }
    
    private native void dispatch_glPointParameterf1(final int p0, final float p1, final long p2);
    
    @Override
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glPointParameterfv = this._pat._addressof_glPointParameterfv;
        if (addressof_glPointParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterfv"));
        }
        this.dispatch_glPointParameterfv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glPointParameterfv);
    }
    
    private native void dispatch_glPointParameterfv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glPointParameterfv = this._pat._addressof_glPointParameterfv;
        if (addressof_glPointParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterfv"));
        }
        this.dispatch_glPointParameterfv1(n, array, 4 * n2, false, addressof_glPointParameterfv);
    }
    
    @Override
    public void glPointSize(final float n) {
        final long addressof_glPointSize = this._pat._addressof_glPointSize;
        if (addressof_glPointSize == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointSize"));
        }
        this.dispatch_glPointSize1(n, addressof_glPointSize);
    }
    
    private native void dispatch_glPointSize1(final float p0, final long p1);
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        final long addressof_glPolygonOffset = this._pat._addressof_glPolygonOffset;
        if (addressof_glPolygonOffset == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPolygonOffset"));
        }
        this.dispatch_glPolygonOffset1(n, n2, addressof_glPolygonOffset);
    }
    
    private native void dispatch_glPolygonOffset1(final float p0, final float p1, final long p2);
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        final long addressof_glRotatef = this._pat._addressof_glRotatef;
        if (addressof_glRotatef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRotatef"));
        }
        this.dispatch_glRotatef1(n, n2, n3, n4, addressof_glRotatef);
    }
    
    private native void dispatch_glRotatef1(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        final long addressof_glScalef = this._pat._addressof_glScalef;
        if (addressof_glScalef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScalef"));
        }
        this.dispatch_glScalef1(n, n2, n3, addressof_glScalef);
    }
    
    private native void dispatch_glScalef1(final float p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glTexEnvf(final int n, final int n2, final float n3) {
        final long addressof_glTexEnvf = this._pat._addressof_glTexEnvf;
        if (addressof_glTexEnvf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvf"));
        }
        this.dispatch_glTexEnvf1(n, n2, n3, addressof_glTexEnvf);
    }
    
    private native void dispatch_glTexEnvf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glTexEnvfv = this._pat._addressof_glTexEnvfv;
        if (addressof_glTexEnvfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvfv"));
        }
        this.dispatch_glTexEnvfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glTexEnvfv);
    }
    
    private native void dispatch_glTexEnvfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexEnvfv = this._pat._addressof_glTexEnvfv;
        if (addressof_glTexEnvfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvfv"));
        }
        this.dispatch_glTexEnvfv1(n, n2, array, 4 * n3, false, addressof_glTexEnvfv);
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        final long addressof_glTexParameterf = this._pat._addressof_glTexParameterf;
        if (addressof_glTexParameterf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterf"));
        }
        this.dispatch_glTexParameterf1(n, n2, n3, addressof_glTexParameterf);
    }
    
    private native void dispatch_glTexParameterf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glTexParameterfv = this._pat._addressof_glTexParameterfv;
        if (addressof_glTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterfv"));
        }
        this.dispatch_glTexParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glTexParameterfv);
    }
    
    private native void dispatch_glTexParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameterfv = this._pat._addressof_glTexParameterfv;
        if (addressof_glTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterfv"));
        }
        this.dispatch_glTexParameterfv1(n, n2, array, 4 * n3, false, addressof_glTexParameterfv);
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        final long addressof_glTranslatef = this._pat._addressof_glTranslatef;
        if (addressof_glTranslatef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTranslatef"));
        }
        this.dispatch_glTranslatef1(n, n2, n3, addressof_glTranslatef);
    }
    
    private native void dispatch_glTranslatef1(final float p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glActiveTexture(final int n) {
        final long addressof_glActiveTexture = this._pat._addressof_glActiveTexture;
        if (addressof_glActiveTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glActiveTexture"));
        }
        this.dispatch_glActiveTexture1(n, addressof_glActiveTexture);
    }
    
    private native void dispatch_glActiveTexture1(final int p0, final long p1);
    
    @Override
    public void glAlphaFuncx(final int n, final int n2) {
        final long addressof_glAlphaFuncx = this._pat._addressof_glAlphaFuncx;
        if (addressof_glAlphaFuncx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glAlphaFuncx"));
        }
        this.dispatch_glAlphaFuncx1(n, n2, addressof_glAlphaFuncx);
    }
    
    private native void dispatch_glAlphaFuncx1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        final long addressof_glBindBuffer = this._pat._addressof_glBindBuffer;
        if (addressof_glBindBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindBuffer"));
        }
        this.dispatch_glBindBuffer1(n, n2, addressof_glBindBuffer);
        this.bufferStateTracker.setBoundBufferObject(n, n2);
    }
    
    private native void dispatch_glBindBuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        final long addressof_glBindTexture = this._pat._addressof_glBindTexture;
        if (addressof_glBindTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindTexture"));
        }
        this.dispatch_glBindTexture1(n, n2, addressof_glBindTexture);
    }
    
    private native void dispatch_glBindTexture1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        final long addressof_glBlendFunc = this._pat._addressof_glBlendFunc;
        if (addressof_glBlendFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFunc"));
        }
        this.dispatch_glBlendFunc1(n, n2, addressof_glBlendFunc);
    }
    
    private native void dispatch_glBlendFunc1(final int p0, final int p1, final long p2);
    
    private void glBufferDataDelegate(final int n, final long n2, final Buffer buffer, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glBufferData = this._pat._addressof_glBufferData;
        if (addressof_glBufferData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBufferData"));
        }
        this.dispatch_glBufferDataDelegate1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n3, addressof_glBufferData);
    }
    
    private native void dispatch_glBufferDataDelegate1(final int p0, final long p1, final Object p2, final int p3, final boolean p4, final int p5, final long p6);
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glBufferSubData = this._pat._addressof_glBufferSubData;
        if (addressof_glBufferSubData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBufferSubData"));
        }
        this.dispatch_glBufferSubData1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glBufferSubData);
    }
    
    private native void dispatch_glBufferSubData1(final int p0, final long p1, final long p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glClear(final int n) {
        final long addressof_glClear = this._pat._addressof_glClear;
        if (addressof_glClear == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClear"));
        }
        this.dispatch_glClear1(n, addressof_glClear);
    }
    
    private native void dispatch_glClear1(final int p0, final long p1);
    
    @Override
    public void glClearColorx(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glClearColorx = this._pat._addressof_glClearColorx;
        if (addressof_glClearColorx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearColorx"));
        }
        this.dispatch_glClearColorx1(n, n2, n3, n4, addressof_glClearColorx);
    }
    
    private native void dispatch_glClearColorx1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glClearDepthx(final int n) {
        final long addressof_glClearDepthx = this._pat._addressof_glClearDepthx;
        if (addressof_glClearDepthx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearDepthx"));
        }
        this.dispatch_glClearDepthx1(n, addressof_glClearDepthx);
    }
    
    private native void dispatch_glClearDepthx1(final int p0, final long p1);
    
    @Override
    public void glClearStencil(final int n) {
        final long addressof_glClearStencil = this._pat._addressof_glClearStencil;
        if (addressof_glClearStencil == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearStencil"));
        }
        this.dispatch_glClearStencil1(n, addressof_glClearStencil);
    }
    
    private native void dispatch_glClearStencil1(final int p0, final long p1);
    
    @Override
    public void glClientActiveTexture(final int n) {
        final long addressof_glClientActiveTexture = this._pat._addressof_glClientActiveTexture;
        if (addressof_glClientActiveTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClientActiveTexture"));
        }
        this.dispatch_glClientActiveTexture1(n, addressof_glClientActiveTexture);
    }
    
    private native void dispatch_glClientActiveTexture1(final int p0, final long p1);
    
    @Override
    public void glClipPlanex(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glClipPlanex = this._pat._addressof_glClipPlanex;
        if (addressof_glClipPlanex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanex"));
        }
        this.dispatch_glClipPlanex1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glClipPlanex);
    }
    
    private native void dispatch_glClipPlanex1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glClipPlanex(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"equation_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClipPlanex = this._pat._addressof_glClipPlanex;
        if (addressof_glClipPlanex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanex"));
        }
        this.dispatch_glClipPlanex1(n, array, 4 * n2, false, addressof_glClipPlanex);
    }
    
    @Override
    public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        final long addressof_glColor4ub = this._pat._addressof_glColor4ub;
        if (addressof_glColor4ub == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColor4ub"));
        }
        this.dispatch_glColor4ub1(b, b2, b3, b4, addressof_glColor4ub);
    }
    
    private native void dispatch_glColor4ub1(final byte p0, final byte p1, final byte p2, final byte p3, final long p4);
    
    @Override
    public void glColor4x(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glColor4x = this._pat._addressof_glColor4x;
        if (addressof_glColor4x == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColor4x"));
        }
        this.dispatch_glColor4x1(n, n2, n3, n4, addressof_glColor4x);
    }
    
    private native void dispatch_glColor4x1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long addressof_glColorMask = this._pat._addressof_glColorMask;
        if (addressof_glColorMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorMask"));
        }
        this.dispatch_glColorMask1(b, b2, b3, b4, addressof_glColorMask);
    }
    
    private native void dispatch_glColorMask1(final boolean p0, final boolean p1, final boolean p2, final boolean p3, final long p4);
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glColorPointer = this._pat._addressof_glColorPointer;
        if (addressof_glColorPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorPointer"));
        }
        this.dispatch_glColorPointer0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glColorPointer);
    }
    
    private native void dispatch_glColorPointer0(final int p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkArrayVBOBound(true);
        final long addressof_glColorPointer = this._pat._addressof_glColorPointer;
        if (addressof_glColorPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorPointer"));
        }
        this.dispatch_glColorPointer0(n, n2, n3, n4, addressof_glColorPointer);
    }
    
    private native void dispatch_glColorPointer0(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexImage2D = this._pat._addressof_glCompressedTexImage2D;
        if (addressof_glCompressedTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage2D"));
        }
        this.dispatch_glCompressedTexImage2D1(n, n2, n3, n4, n5, n6, n7, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexImage2D);
    }
    
    private native void dispatch_glCompressedTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexImage2D = this._pat._addressof_glCompressedTexImage2D;
        if (addressof_glCompressedTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage2D"));
        }
        this.dispatch_glCompressedTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCompressedTexImage2D);
    }
    
    private native void dispatch_glCompressedTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexSubImage2D = this._pat._addressof_glCompressedTexSubImage2D;
        if (addressof_glCompressedTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage2D"));
        }
        this.dispatch_glCompressedTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexSubImage2D);
    }
    
    private native void dispatch_glCompressedTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexSubImage2D = this._pat._addressof_glCompressedTexSubImage2D;
        if (addressof_glCompressedTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage2D"));
        }
        this.dispatch_glCompressedTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glCompressedTexSubImage2D);
    }
    
    private native void dispatch_glCompressedTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long addressof_glCopyTexImage2D = this._pat._addressof_glCopyTexImage2D;
        if (addressof_glCopyTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTexImage2D"));
        }
        this.dispatch_glCopyTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCopyTexImage2D);
    }
    
    private native void dispatch_glCopyTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long addressof_glCopyTexSubImage2D = this._pat._addressof_glCopyTexSubImage2D;
        if (addressof_glCopyTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTexSubImage2D"));
        }
        this.dispatch_glCopyTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCopyTexSubImage2D);
    }
    
    private native void dispatch_glCopyTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glCullFace(final int n) {
        final long addressof_glCullFace = this._pat._addressof_glCullFace;
        if (addressof_glCullFace == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCullFace"));
        }
        this.dispatch_glCullFace1(n, addressof_glCullFace);
    }
    
    private native void dispatch_glCullFace1(final int p0, final long p1);
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.bufferObjectTracker.notifyBuffersDeleted(n, intBuffer);
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteBuffers = this._pat._addressof_glDeleteBuffers;
        if (addressof_glDeleteBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteBuffers"));
        }
        this.dispatch_glDeleteBuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteBuffers);
    }
    
    private native void dispatch_glDeleteBuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.bufferObjectTracker.notifyBuffersDeleted(n, array, n2);
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteBuffers = this._pat._addressof_glDeleteBuffers;
        if (addressof_glDeleteBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteBuffers"));
        }
        this.dispatch_glDeleteBuffers1(n, array, 4 * n2, false, addressof_glDeleteBuffers);
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteTextures = this._pat._addressof_glDeleteTextures;
        if (addressof_glDeleteTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTextures"));
        }
        this.dispatch_glDeleteTextures1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteTextures);
    }
    
    private native void dispatch_glDeleteTextures1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"textures_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteTextures = this._pat._addressof_glDeleteTextures;
        if (addressof_glDeleteTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTextures"));
        }
        this.dispatch_glDeleteTextures1(n, array, 4 * n2, false, addressof_glDeleteTextures);
    }
    
    @Override
    public void glDepthFunc(final int n) {
        final long addressof_glDepthFunc = this._pat._addressof_glDepthFunc;
        if (addressof_glDepthFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthFunc"));
        }
        this.dispatch_glDepthFunc1(n, addressof_glDepthFunc);
    }
    
    private native void dispatch_glDepthFunc1(final int p0, final long p1);
    
    @Override
    public void glDepthMask(final boolean b) {
        final long addressof_glDepthMask = this._pat._addressof_glDepthMask;
        if (addressof_glDepthMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthMask"));
        }
        this.dispatch_glDepthMask1(b, addressof_glDepthMask);
    }
    
    private native void dispatch_glDepthMask1(final boolean p0, final long p1);
    
    @Override
    public void glDepthRangex(final int n, final int n2) {
        final long addressof_glDepthRangex = this._pat._addressof_glDepthRangex;
        if (addressof_glDepthRangex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangex"));
        }
        this.dispatch_glDepthRangex1(n, n2, addressof_glDepthRangex);
    }
    
    private native void dispatch_glDepthRangex1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisable(final int n) {
        final long addressof_glDisable = this._pat._addressof_glDisable;
        if (addressof_glDisable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisable"));
        }
        this.dispatch_glDisable1(n, addressof_glDisable);
    }
    
    private native void dispatch_glDisable1(final int p0, final long p1);
    
    @Override
    public void glDisableClientState(final int n) {
        final long addressof_glDisableClientState = this._pat._addressof_glDisableClientState;
        if (addressof_glDisableClientState == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableClientState"));
        }
        this.dispatch_glDisableClientState1(n, addressof_glDisableClientState);
    }
    
    private native void dispatch_glDisableClientState1(final int p0, final long p1);
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        final long addressof_glDrawArrays = this._pat._addressof_glDrawArrays;
        if (addressof_glDrawArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArrays"));
        }
        this.dispatch_glDrawArrays1(n, n2, n3, addressof_glDrawArrays);
    }
    
    private native void dispatch_glDrawArrays1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElements = this._pat._addressof_glDrawElements;
        if (addressof_glDrawElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElements"));
        }
        this.dispatch_glDrawElements1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glDrawElements);
    }
    
    private native void dispatch_glDrawElements1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElements = this._pat._addressof_glDrawElements;
        if (addressof_glDrawElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElements"));
        }
        this.dispatch_glDrawElements1(n, n2, n3, n4, addressof_glDrawElements);
    }
    
    private native void dispatch_glDrawElements1(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public void glEnable(final int n) {
        final long addressof_glEnable = this._pat._addressof_glEnable;
        if (addressof_glEnable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnable"));
        }
        this.dispatch_glEnable1(n, addressof_glEnable);
    }
    
    private native void dispatch_glEnable1(final int p0, final long p1);
    
    @Override
    public void glEnableClientState(final int n) {
        final long addressof_glEnableClientState = this._pat._addressof_glEnableClientState;
        if (addressof_glEnableClientState == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableClientState"));
        }
        this.dispatch_glEnableClientState1(n, addressof_glEnableClientState);
    }
    
    private native void dispatch_glEnableClientState1(final int p0, final long p1);
    
    @Override
    public void glFinish() {
        final long addressof_glFinish = this._pat._addressof_glFinish;
        if (addressof_glFinish == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFinish"));
        }
        this.dispatch_glFinish1(addressof_glFinish);
    }
    
    private native void dispatch_glFinish1(final long p0);
    
    @Override
    public void glFlush() {
        final long addressof_glFlush = this._pat._addressof_glFlush;
        if (addressof_glFlush == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFlush"));
        }
        this.dispatch_glFlush1(addressof_glFlush);
    }
    
    private native void dispatch_glFlush1(final long p0);
    
    @Override
    public void glFogx(final int n, final int n2) {
        final long addressof_glFogx = this._pat._addressof_glFogx;
        if (addressof_glFogx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogx"));
        }
        this.dispatch_glFogx1(n, n2, addressof_glFogx);
    }
    
    private native void dispatch_glFogx1(final int p0, final int p1, final long p2);
    
    @Override
    public void glFogxv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glFogxv = this._pat._addressof_glFogxv;
        if (addressof_glFogxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogxv"));
        }
        this.dispatch_glFogxv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glFogxv);
    }
    
    private native void dispatch_glFogxv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glFogxv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glFogxv = this._pat._addressof_glFogxv;
        if (addressof_glFogxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFogxv"));
        }
        this.dispatch_glFogxv1(n, array, 4 * n2, false, addressof_glFogxv);
    }
    
    @Override
    public void glFrontFace(final int n) {
        final long addressof_glFrontFace = this._pat._addressof_glFrontFace;
        if (addressof_glFrontFace == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFrontFace"));
        }
        this.dispatch_glFrontFace1(n, addressof_glFrontFace);
    }
    
    private native void dispatch_glFrontFace1(final int p0, final long p1);
    
    @Override
    public void glFrustumx(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFrustumx = this._pat._addressof_glFrustumx;
        if (addressof_glFrustumx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFrustumx"));
        }
        this.dispatch_glFrustumx1(n, n2, n3, n4, n5, n6, addressof_glFrustumx);
    }
    
    private native void dispatch_glFrustumx1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetBooleanv = this._pat._addressof_glGetBooleanv;
        if (addressof_glGetBooleanv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleanv"));
        }
        this.dispatch_glGetBooleanv1(n, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetBooleanv);
    }
    
    private native void dispatch_glGetBooleanv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBooleanv = this._pat._addressof_glGetBooleanv;
        if (addressof_glGetBooleanv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleanv"));
        }
        this.dispatch_glGetBooleanv1(n, array, n2, false, addressof_glGetBooleanv);
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetBufferParameteriv = this._pat._addressof_glGetBufferParameteriv;
        if (addressof_glGetBufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteriv"));
        }
        this.dispatch_glGetBufferParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetBufferParameteriv);
    }
    
    private native void dispatch_glGetBufferParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBufferParameteriv = this._pat._addressof_glGetBufferParameteriv;
        if (addressof_glGetBufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteriv"));
        }
        this.dispatch_glGetBufferParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetBufferParameteriv);
    }
    
    @Override
    public void glGetClipPlanex(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetClipPlanex = this._pat._addressof_glGetClipPlanex;
        if (addressof_glGetClipPlanex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetClipPlanex"));
        }
        this.dispatch_glGetClipPlanex1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetClipPlanex);
    }
    
    private native void dispatch_glGetClipPlanex1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetClipPlanex(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"eqn_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetClipPlanex = this._pat._addressof_glGetClipPlanex;
        if (addressof_glGetClipPlanex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetClipPlanex"));
        }
        this.dispatch_glGetClipPlanex1(n, array, 4 * n2, false, addressof_glGetClipPlanex);
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenBuffers = this._pat._addressof_glGenBuffers;
        if (addressof_glGenBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenBuffers"));
        }
        this.dispatch_glGenBuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenBuffers);
    }
    
    private native void dispatch_glGenBuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenBuffers = this._pat._addressof_glGenBuffers;
        if (addressof_glGenBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenBuffers"));
        }
        this.dispatch_glGenBuffers1(n, array, 4 * n2, false, addressof_glGenBuffers);
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenTextures = this._pat._addressof_glGenTextures;
        if (addressof_glGenTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTextures"));
        }
        this.dispatch_glGenTextures1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenTextures);
    }
    
    private native void dispatch_glGenTextures1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"textures_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenTextures = this._pat._addressof_glGenTextures;
        if (addressof_glGenTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTextures"));
        }
        this.dispatch_glGenTextures1(n, array, 4 * n2, false, addressof_glGenTextures);
    }
    
    @Override
    public int glGetError() {
        final long addressof_glGetError = this._pat._addressof_glGetError;
        if (addressof_glGetError == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetError"));
        }
        return this.dispatch_glGetError1(addressof_glGetError);
    }
    
    private native int dispatch_glGetError1(final long p0);
    
    @Override
    public void glGetFixedv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetFixedv = this._pat._addressof_glGetFixedv;
        if (addressof_glGetFixedv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFixedv"));
        }
        this.dispatch_glGetFixedv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetFixedv);
    }
    
    private native void dispatch_glGetFixedv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetFixedv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFixedv = this._pat._addressof_glGetFixedv;
        if (addressof_glGetFixedv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFixedv"));
        }
        this.dispatch_glGetFixedv1(n, array, 4 * n2, false, addressof_glGetFixedv);
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        if (this.glStateTracker.getInt(n, intBuffer, 0)) {
            return;
        }
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetIntegerv = this._pat._addressof_glGetIntegerv;
        if (addressof_glGetIntegerv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegerv"));
        }
        this.dispatch_glGetIntegerv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetIntegerv);
    }
    
    private native void dispatch_glGetIntegerv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        if (this.glStateTracker.getInt(n, array, n2)) {
            return;
        }
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetIntegerv = this._pat._addressof_glGetIntegerv;
        if (addressof_glGetIntegerv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegerv"));
        }
        this.dispatch_glGetIntegerv1(n, array, 4 * n2, false, addressof_glGetIntegerv);
    }
    
    @Override
    public void glGetLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetLightxv = this._pat._addressof_glGetLightxv;
        if (addressof_glGetLightxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetLightxv"));
        }
        this.dispatch_glGetLightxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetLightxv);
    }
    
    private native void dispatch_glGetLightxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetLightxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetLightxv = this._pat._addressof_glGetLightxv;
        if (addressof_glGetLightxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetLightxv"));
        }
        this.dispatch_glGetLightxv1(n, n2, array, 4 * n3, false, addressof_glGetLightxv);
    }
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetMaterialxv = this._pat._addressof_glGetMaterialxv;
        if (addressof_glGetMaterialxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMaterialxv"));
        }
        this.dispatch_glGetMaterialxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetMaterialxv);
    }
    
    private native void dispatch_glGetMaterialxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetMaterialxv = this._pat._addressof_glGetMaterialxv;
        if (addressof_glGetMaterialxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMaterialxv"));
        }
        this.dispatch_glGetMaterialxv1(n, n2, array, 4 * n3, false, addressof_glGetMaterialxv);
    }
    
    @Override
    public String glGetString(final int n) {
        if (this._context.isExtensionCacheInitialized() && 7939 == n) {
            return this._context.getGLExtensionsString();
        }
        final long addressof_glGetString = this._pat._addressof_glGetString;
        if (addressof_glGetString == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetString"));
        }
        return this.dispatch_glGetString1(n, addressof_glGetString);
    }
    
    private native String dispatch_glGetString1(final int p0, final long p1);
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexEnviv = this._pat._addressof_glGetTexEnviv;
        if (addressof_glGetTexEnviv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnviv"));
        }
        this.dispatch_glGetTexEnviv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexEnviv);
    }
    
    private native void dispatch_glGetTexEnviv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexEnviv = this._pat._addressof_glGetTexEnviv;
        if (addressof_glGetTexEnviv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnviv"));
        }
        this.dispatch_glGetTexEnviv1(n, n2, array, 4 * n3, false, addressof_glGetTexEnviv);
    }
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexEnvxv = this._pat._addressof_glGetTexEnvxv;
        if (addressof_glGetTexEnvxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnvxv"));
        }
        this.dispatch_glGetTexEnvxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexEnvxv);
    }
    
    private native void dispatch_glGetTexEnvxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexEnvxv = this._pat._addressof_glGetTexEnvxv;
        if (addressof_glGetTexEnvxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexEnvxv"));
        }
        this.dispatch_glGetTexEnvxv1(n, n2, array, 4 * n3, false, addressof_glGetTexEnvxv);
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexParameteriv = this._pat._addressof_glGetTexParameteriv;
        if (addressof_glGetTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameteriv"));
        }
        this.dispatch_glGetTexParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexParameteriv);
    }
    
    private native void dispatch_glGetTexParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameteriv = this._pat._addressof_glGetTexParameteriv;
        if (addressof_glGetTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameteriv"));
        }
        this.dispatch_glGetTexParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameteriv);
    }
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexParameterxv = this._pat._addressof_glGetTexParameterxv;
        if (addressof_glGetTexParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterxv"));
        }
        this.dispatch_glGetTexParameterxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexParameterxv);
    }
    
    private native void dispatch_glGetTexParameterxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameterxv = this._pat._addressof_glGetTexParameterxv;
        if (addressof_glGetTexParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterxv"));
        }
        this.dispatch_glGetTexParameterxv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameterxv);
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        final long addressof_glHint = this._pat._addressof_glHint;
        if (addressof_glHint == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glHint"));
        }
        this.dispatch_glHint1(n, n2, addressof_glHint);
    }
    
    private native void dispatch_glHint1(final int p0, final int p1, final long p2);
    
    @Override
    public boolean glIsBuffer(final int n) {
        final long addressof_glIsBuffer = this._pat._addressof_glIsBuffer;
        if (addressof_glIsBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsBuffer"));
        }
        return this.dispatch_glIsBuffer1(n, addressof_glIsBuffer);
    }
    
    private native boolean dispatch_glIsBuffer1(final int p0, final long p1);
    
    @Override
    public boolean glIsEnabled(final int n) {
        final long addressof_glIsEnabled = this._pat._addressof_glIsEnabled;
        if (addressof_glIsEnabled == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnabled"));
        }
        return this.dispatch_glIsEnabled1(n, addressof_glIsEnabled);
    }
    
    private native boolean dispatch_glIsEnabled1(final int p0, final long p1);
    
    @Override
    public boolean glIsTexture(final int n) {
        final long addressof_glIsTexture = this._pat._addressof_glIsTexture;
        if (addressof_glIsTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsTexture"));
        }
        return this.dispatch_glIsTexture1(n, addressof_glIsTexture);
    }
    
    private native boolean dispatch_glIsTexture1(final int p0, final long p1);
    
    @Override
    public void glLightModelx(final int n, final int n2) {
        final long addressof_glLightModelx = this._pat._addressof_glLightModelx;
        if (addressof_glLightModelx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelx"));
        }
        this.dispatch_glLightModelx1(n, n2, addressof_glLightModelx);
    }
    
    private native void dispatch_glLightModelx1(final int p0, final int p1, final long p2);
    
    @Override
    public void glLightModelxv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glLightModelxv = this._pat._addressof_glLightModelxv;
        if (addressof_glLightModelxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelxv"));
        }
        this.dispatch_glLightModelxv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glLightModelxv);
    }
    
    private native void dispatch_glLightModelxv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glLightModelxv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLightModelxv = this._pat._addressof_glLightModelxv;
        if (addressof_glLightModelxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightModelxv"));
        }
        this.dispatch_glLightModelxv1(n, array, 4 * n2, false, addressof_glLightModelxv);
    }
    
    @Override
    public void glLightx(final int n, final int n2, final int n3) {
        final long addressof_glLightx = this._pat._addressof_glLightx;
        if (addressof_glLightx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightx"));
        }
        this.dispatch_glLightx1(n, n2, n3, addressof_glLightx);
    }
    
    private native void dispatch_glLightx1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glLightxv = this._pat._addressof_glLightxv;
        if (addressof_glLightxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightxv"));
        }
        this.dispatch_glLightxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glLightxv);
    }
    
    private native void dispatch_glLightxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glLightxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLightxv = this._pat._addressof_glLightxv;
        if (addressof_glLightxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLightxv"));
        }
        this.dispatch_glLightxv1(n, n2, array, 4 * n3, false, addressof_glLightxv);
    }
    
    @Override
    public void glLineWidthx(final int n) {
        final long addressof_glLineWidthx = this._pat._addressof_glLineWidthx;
        if (addressof_glLineWidthx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLineWidthx"));
        }
        this.dispatch_glLineWidthx1(n, addressof_glLineWidthx);
    }
    
    private native void dispatch_glLineWidthx1(final int p0, final long p1);
    
    @Override
    public void glLoadIdentity() {
        final long addressof_glLoadIdentity = this._pat._addressof_glLoadIdentity;
        if (addressof_glLoadIdentity == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadIdentity"));
        }
        this.dispatch_glLoadIdentity1(addressof_glLoadIdentity);
    }
    
    private native void dispatch_glLoadIdentity1(final long p0);
    
    @Override
    public void glLoadMatrixx(final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glLoadMatrixx = this._pat._addressof_glLoadMatrixx;
        if (addressof_glLoadMatrixx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadMatrixx"));
        }
        this.dispatch_glLoadMatrixx1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glLoadMatrixx);
    }
    
    private native void dispatch_glLoadMatrixx1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glLoadMatrixx(final int[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"m_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glLoadMatrixx = this._pat._addressof_glLoadMatrixx;
        if (addressof_glLoadMatrixx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadMatrixx"));
        }
        this.dispatch_glLoadMatrixx1(array, 4 * n, false, addressof_glLoadMatrixx);
    }
    
    @Override
    public void glLogicOp(final int n) {
        final long addressof_glLogicOp = this._pat._addressof_glLogicOp;
        if (addressof_glLogicOp == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLogicOp"));
        }
        this.dispatch_glLogicOp1(n, addressof_glLogicOp);
    }
    
    private native void dispatch_glLogicOp1(final int p0, final long p1);
    
    @Override
    public void glMaterialx(final int n, final int n2, final int n3) {
        final long addressof_glMaterialx = this._pat._addressof_glMaterialx;
        if (addressof_glMaterialx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialx"));
        }
        this.dispatch_glMaterialx1(n, n2, n3, addressof_glMaterialx);
    }
    
    private native void dispatch_glMaterialx1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glMaterialxv = this._pat._addressof_glMaterialxv;
        if (addressof_glMaterialxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialxv"));
        }
        this.dispatch_glMaterialxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glMaterialxv);
    }
    
    private native void dispatch_glMaterialxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glMaterialxv = this._pat._addressof_glMaterialxv;
        if (addressof_glMaterialxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMaterialxv"));
        }
        this.dispatch_glMaterialxv1(n, n2, array, 4 * n3, false, addressof_glMaterialxv);
    }
    
    @Override
    public void glMatrixMode(final int n) {
        final long addressof_glMatrixMode = this._pat._addressof_glMatrixMode;
        if (addressof_glMatrixMode == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMatrixMode"));
        }
        this.dispatch_glMatrixMode1(n, addressof_glMatrixMode);
    }
    
    private native void dispatch_glMatrixMode1(final int p0, final long p1);
    
    @Override
    public void glMultMatrixx(final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glMultMatrixx = this._pat._addressof_glMultMatrixx;
        if (addressof_glMultMatrixx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultMatrixx"));
        }
        this.dispatch_glMultMatrixx1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glMultMatrixx);
    }
    
    private native void dispatch_glMultMatrixx1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glMultMatrixx(final int[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"m_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glMultMatrixx = this._pat._addressof_glMultMatrixx;
        if (addressof_glMultMatrixx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultMatrixx"));
        }
        this.dispatch_glMultMatrixx1(array, 4 * n, false, addressof_glMultMatrixx);
    }
    
    @Override
    public void glMultiTexCoord4x(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glMultiTexCoord4x = this._pat._addressof_glMultiTexCoord4x;
        if (addressof_glMultiTexCoord4x == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiTexCoord4x"));
        }
        this.dispatch_glMultiTexCoord4x1(n, n2, n3, n4, n5, addressof_glMultiTexCoord4x);
    }
    
    private native void dispatch_glMultiTexCoord4x1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glNormal3x(final int n, final int n2, final int n3) {
        final long addressof_glNormal3x = this._pat._addressof_glNormal3x;
        if (addressof_glNormal3x == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNormal3x"));
        }
        this.dispatch_glNormal3x1(n, n2, n3, addressof_glNormal3x);
    }
    
    private native void dispatch_glNormal3x1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glNormalPointer(final int n, final int n2, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glNormalPointer = this._pat._addressof_glNormalPointer;
        if (addressof_glNormalPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNormalPointer"));
        }
        this.dispatch_glNormalPointer0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glNormalPointer);
    }
    
    private native void dispatch_glNormalPointer0(final int p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public void glNormalPointer(final int n, final int n2, final long n3) {
        this.checkArrayVBOBound(true);
        final long addressof_glNormalPointer = this._pat._addressof_glNormalPointer;
        if (addressof_glNormalPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNormalPointer"));
        }
        this.dispatch_glNormalPointer0(n, n2, n3, addressof_glNormalPointer);
    }
    
    private native void dispatch_glNormalPointer0(final int p0, final int p1, final long p2, final long p3);
    
    @Override
    public void glOrthox(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glOrthox = this._pat._addressof_glOrthox;
        if (addressof_glOrthox == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glOrthox"));
        }
        this.dispatch_glOrthox1(n, n2, n3, n4, n5, n6, addressof_glOrthox);
    }
    
    private native void dispatch_glOrthox1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        if (n != 3333 && n != 3317) {
            throw new GLException("Unsupported pixel store parameter name 0x" + Integer.toHexString(n));
        }
        this.glStateTracker.setInt(n, n2);
        final long addressof_glPixelStorei = this._pat._addressof_glPixelStorei;
        if (addressof_glPixelStorei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPixelStorei"));
        }
        this.dispatch_glPixelStorei1(n, n2, addressof_glPixelStorei);
    }
    
    private native void dispatch_glPixelStorei1(final int p0, final int p1, final long p2);
    
    @Override
    public void glPointParameterx(final int n, final int n2) {
        final long addressof_glPointParameterx = this._pat._addressof_glPointParameterx;
        if (addressof_glPointParameterx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterx"));
        }
        this.dispatch_glPointParameterx1(n, n2, addressof_glPointParameterx);
    }
    
    private native void dispatch_glPointParameterx1(final int p0, final int p1, final long p2);
    
    @Override
    public void glPointParameterxv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glPointParameterxv = this._pat._addressof_glPointParameterxv;
        if (addressof_glPointParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterxv"));
        }
        this.dispatch_glPointParameterxv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glPointParameterxv);
    }
    
    private native void dispatch_glPointParameterxv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glPointParameterxv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"params_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glPointParameterxv = this._pat._addressof_glPointParameterxv;
        if (addressof_glPointParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointParameterxv"));
        }
        this.dispatch_glPointParameterxv1(n, array, 4 * n2, false, addressof_glPointParameterxv);
    }
    
    @Override
    public void glPointSizex(final int n) {
        final long addressof_glPointSizex = this._pat._addressof_glPointSizex;
        if (addressof_glPointSizex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointSizex"));
        }
        this.dispatch_glPointSizex1(n, addressof_glPointSizex);
    }
    
    private native void dispatch_glPointSizex1(final int p0, final long p1);
    
    @Override
    public void glPolygonOffsetx(final int n, final int n2) {
        final long addressof_glPolygonOffsetx = this._pat._addressof_glPolygonOffsetx;
        if (addressof_glPolygonOffsetx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPolygonOffsetx"));
        }
        this.dispatch_glPolygonOffsetx1(n, n2, addressof_glPolygonOffsetx);
    }
    
    private native void dispatch_glPolygonOffsetx1(final int p0, final int p1, final long p2);
    
    @Override
    public void glPopMatrix() {
        final long addressof_glPopMatrix = this._pat._addressof_glPopMatrix;
        if (addressof_glPopMatrix == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPopMatrix"));
        }
        this.dispatch_glPopMatrix1(addressof_glPopMatrix);
    }
    
    private native void dispatch_glPopMatrix1(final long p0);
    
    @Override
    public void glPushMatrix() {
        final long addressof_glPushMatrix = this._pat._addressof_glPushMatrix;
        if (addressof_glPushMatrix == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPushMatrix"));
        }
        this.dispatch_glPushMatrix1(addressof_glPushMatrix);
    }
    
    private native void dispatch_glPushMatrix1(final long p0);
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.checkPackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n5, n6, n3, n4, 1, true));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glReadPixels = this._pat._addressof_glReadPixels;
        if (addressof_glReadPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadPixels"));
        }
        this.dispatch_glReadPixels1(n, n2, n3, n4, n5, n6, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glReadPixels);
    }
    
    private native void dispatch_glReadPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.checkPackPBOBound(true);
        final long addressof_glReadPixels = this._pat._addressof_glReadPixels;
        if (addressof_glReadPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadPixels"));
        }
        this.dispatch_glReadPixels1(n, n2, n3, n4, n5, n6, n7, addressof_glReadPixels);
    }
    
    private native void dispatch_glReadPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    @Override
    public void glRotatex(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glRotatex = this._pat._addressof_glRotatex;
        if (addressof_glRotatex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRotatex"));
        }
        this.dispatch_glRotatex1(n, n2, n3, n4, addressof_glRotatex);
    }
    
    private native void dispatch_glRotatex1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        final long addressof_glSampleCoverage = this._pat._addressof_glSampleCoverage;
        if (addressof_glSampleCoverage == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSampleCoverage"));
        }
        this.dispatch_glSampleCoverage1(n, b, addressof_glSampleCoverage);
    }
    
    private native void dispatch_glSampleCoverage1(final float p0, final boolean p1, final long p2);
    
    @Override
    public void glSampleCoveragex(final int n, final boolean b) {
        final long addressof_glSampleCoveragex = this._pat._addressof_glSampleCoveragex;
        if (addressof_glSampleCoveragex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSampleCoveragex"));
        }
        this.dispatch_glSampleCoveragex1(n, b, addressof_glSampleCoveragex);
    }
    
    private native void dispatch_glSampleCoveragex1(final int p0, final boolean p1, final long p2);
    
    @Override
    public void glScalex(final int n, final int n2, final int n3) {
        final long addressof_glScalex = this._pat._addressof_glScalex;
        if (addressof_glScalex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScalex"));
        }
        this.dispatch_glScalex1(n, n2, n3, addressof_glScalex);
    }
    
    private native void dispatch_glScalex1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glScissor = this._pat._addressof_glScissor;
        if (addressof_glScissor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissor"));
        }
        this.dispatch_glScissor1(n, n2, n3, n4, addressof_glScissor);
    }
    
    private native void dispatch_glScissor1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glShadeModel(final int n) {
        final long addressof_glShadeModel = this._pat._addressof_glShadeModel;
        if (addressof_glShadeModel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glShadeModel"));
        }
        this.dispatch_glShadeModel1(n, addressof_glShadeModel);
    }
    
    private native void dispatch_glShadeModel1(final int p0, final long p1);
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        final long addressof_glStencilFunc = this._pat._addressof_glStencilFunc;
        if (addressof_glStencilFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilFunc"));
        }
        this.dispatch_glStencilFunc1(n, n2, n3, addressof_glStencilFunc);
    }
    
    private native void dispatch_glStencilFunc1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glStencilMask(final int n) {
        final long addressof_glStencilMask = this._pat._addressof_glStencilMask;
        if (addressof_glStencilMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilMask"));
        }
        this.dispatch_glStencilMask1(n, addressof_glStencilMask);
    }
    
    private native void dispatch_glStencilMask1(final int p0, final long p1);
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        final long addressof_glStencilOp = this._pat._addressof_glStencilOp;
        if (addressof_glStencilOp == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilOp"));
        }
        this.dispatch_glStencilOp1(n, n2, n3, addressof_glStencilOp);
    }
    
    private native void dispatch_glStencilOp1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glTexCoordPointer = this._pat._addressof_glTexCoordPointer;
        if (addressof_glTexCoordPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexCoordPointer"));
        }
        this.dispatch_glTexCoordPointer0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glTexCoordPointer);
    }
    
    private native void dispatch_glTexCoordPointer0(final int p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkArrayVBOBound(true);
        final long addressof_glTexCoordPointer = this._pat._addressof_glTexCoordPointer;
        if (addressof_glTexCoordPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexCoordPointer"));
        }
        this.dispatch_glTexCoordPointer0(n, n2, n3, n4, addressof_glTexCoordPointer);
    }
    
    private native void dispatch_glTexCoordPointer0(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        final long addressof_glTexEnvi = this._pat._addressof_glTexEnvi;
        if (addressof_glTexEnvi == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvi"));
        }
        this.dispatch_glTexEnvi1(n, n2, n3, addressof_glTexEnvi);
    }
    
    private native void dispatch_glTexEnvi1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexEnvx(final int n, final int n2, final int n3) {
        final long addressof_glTexEnvx = this._pat._addressof_glTexEnvx;
        if (addressof_glTexEnvx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvx"));
        }
        this.dispatch_glTexEnvx1(n, n2, n3, addressof_glTexEnvx);
    }
    
    private native void dispatch_glTexEnvx1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexEnviv = this._pat._addressof_glTexEnviv;
        if (addressof_glTexEnviv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnviv"));
        }
        this.dispatch_glTexEnviv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexEnviv);
    }
    
    private native void dispatch_glTexEnviv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexEnviv = this._pat._addressof_glTexEnviv;
        if (addressof_glTexEnviv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnviv"));
        }
        this.dispatch_glTexEnviv1(n, n2, array, 4 * n3, false, addressof_glTexEnviv);
    }
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexEnvxv = this._pat._addressof_glTexEnvxv;
        if (addressof_glTexEnvxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvxv"));
        }
        this.dispatch_glTexEnvxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexEnvxv);
    }
    
    private native void dispatch_glTexEnvxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexEnvxv = this._pat._addressof_glTexEnvxv;
        if (addressof_glTexEnvxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexEnvxv"));
        }
        this.dispatch_glTexEnvxv1(n, n2, array, 4 * n3, false, addressof_glTexEnvxv);
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n7, n8, n4, n5, 1, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexImage2D = this._pat._addressof_glTexImage2D;
        if (addressof_glTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage2D"));
        }
        this.dispatch_glTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexImage2D);
    }
    
    private native void dispatch_glTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexImage2D = this._pat._addressof_glTexImage2D;
        if (addressof_glTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage2D"));
        }
        this.dispatch_glTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glTexImage2D);
    }
    
    private native void dispatch_glTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        final long addressof_glTexParameteri = this._pat._addressof_glTexParameteri;
        if (addressof_glTexParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteri"));
        }
        this.dispatch_glTexParameteri1(n, n2, n3, addressof_glTexParameteri);
    }
    
    private native void dispatch_glTexParameteri1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexParameterx(final int n, final int n2, final int n3) {
        final long addressof_glTexParameterx = this._pat._addressof_glTexParameterx;
        if (addressof_glTexParameterx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterx"));
        }
        this.dispatch_glTexParameterx1(n, n2, n3, addressof_glTexParameterx);
    }
    
    private native void dispatch_glTexParameterx1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexParameteriv = this._pat._addressof_glTexParameteriv;
        if (addressof_glTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteriv"));
        }
        this.dispatch_glTexParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexParameteriv);
    }
    
    private native void dispatch_glTexParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameteriv = this._pat._addressof_glTexParameteriv;
        if (addressof_glTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteriv"));
        }
        this.dispatch_glTexParameteriv1(n, n2, array, 4 * n3, false, addressof_glTexParameteriv);
    }
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexParameterxv = this._pat._addressof_glTexParameterxv;
        if (addressof_glTexParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterxv"));
        }
        this.dispatch_glTexParameterxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexParameterxv);
    }
    
    private native void dispatch_glTexParameterxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameterxv = this._pat._addressof_glTexParameterxv;
        if (addressof_glTexParameterxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterxv"));
        }
        this.dispatch_glTexParameterxv1(n, n2, array, 4 * n3, false, addressof_glTexParameterxv);
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n7, n8, n5, n6, 1, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexSubImage2D = this._pat._addressof_glTexSubImage2D;
        if (addressof_glTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage2D"));
        }
        this.dispatch_glTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexSubImage2D);
    }
    
    private native void dispatch_glTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexSubImage2D = this._pat._addressof_glTexSubImage2D;
        if (addressof_glTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage2D"));
        }
        this.dispatch_glTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glTexSubImage2D);
    }
    
    private native void dispatch_glTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glTranslatex(final int n, final int n2, final int n3) {
        final long addressof_glTranslatex = this._pat._addressof_glTranslatex;
        if (addressof_glTranslatex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTranslatex"));
        }
        this.dispatch_glTranslatex1(n, n2, n3, addressof_glTranslatex);
    }
    
    private native void dispatch_glTranslatex1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glVertexPointer = this._pat._addressof_glVertexPointer;
        if (addressof_glVertexPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexPointer"));
        }
        this.dispatch_glVertexPointer0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glVertexPointer);
    }
    
    private native void dispatch_glVertexPointer0(final int p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkArrayVBOBound(true);
        final long addressof_glVertexPointer = this._pat._addressof_glVertexPointer;
        if (addressof_glVertexPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexPointer"));
        }
        this.dispatch_glVertexPointer0(n, n2, n3, n4, addressof_glVertexPointer);
    }
    
    private native void dispatch_glVertexPointer0(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glViewport = this._pat._addressof_glViewport;
        if (addressof_glViewport == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewport"));
        }
        this.dispatch_glViewport1(n, n2, n3, n4, addressof_glViewport);
    }
    
    private native void dispatch_glViewport1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glPointSizePointerOES(final int n, final int n2, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glPointSizePointerOES = this._pat._addressof_glPointSizePointerOES;
        if (addressof_glPointSizePointerOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPointSizePointerOES"));
        }
        this.dispatch_glPointSizePointerOES1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glPointSizePointerOES);
    }
    
    private native void dispatch_glPointSizePointerOES1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glTexStorage1D = this._pat._addressof_glTexStorage1D;
        if (addressof_glTexStorage1D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage1D"));
        }
        this.dispatch_glTexStorage1D1(n, n2, n3, n4, addressof_glTexStorage1D);
    }
    
    private native void dispatch_glTexStorage1D1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glTexStorage2D = this._pat._addressof_glTexStorage2D;
        if (addressof_glTexStorage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage2D"));
        }
        this.dispatch_glTexStorage2D1(n, n2, n3, n4, n5, addressof_glTexStorage2D);
    }
    
    private native void dispatch_glTexStorage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glTexStorage3D = this._pat._addressof_glTexStorage3D;
        if (addressof_glTexStorage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage3D"));
        }
        this.dispatch_glTexStorage3D1(n, n2, n3, n4, n5, n6, addressof_glTexStorage3D);
    }
    
    private native void dispatch_glTexStorage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glTextureStorage1DEXT = this._pat._addressof_glTextureStorage1DEXT;
        if (addressof_glTextureStorage1DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage1DEXT"));
        }
        this.dispatch_glTextureStorage1DEXT1(n, n2, n3, n4, n5, addressof_glTextureStorage1DEXT);
    }
    
    private native void dispatch_glTextureStorage1DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glTextureStorage2DEXT = this._pat._addressof_glTextureStorage2DEXT;
        if (addressof_glTextureStorage2DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage2DEXT"));
        }
        this.dispatch_glTextureStorage2DEXT1(n, n2, n3, n4, n5, n6, addressof_glTextureStorage2DEXT);
    }
    
    private native void dispatch_glTextureStorage2DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long addressof_glTextureStorage3DEXT = this._pat._addressof_glTextureStorage3DEXT;
        if (addressof_glTextureStorage3DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage3DEXT"));
        }
        this.dispatch_glTextureStorage3DEXT1(n, n2, n3, n4, n5, n6, n7, addressof_glTextureStorage3DEXT);
    }
    
    private native void dispatch_glTextureStorage3DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        final long addressof_glBlendEquationSeparate = this._pat._addressof_glBlendEquationSeparate;
        if (addressof_glBlendEquationSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationSeparate"));
        }
        this.dispatch_glBlendEquationSeparate1(n, n2, addressof_glBlendEquationSeparate);
    }
    
    private native void dispatch_glBlendEquationSeparate1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glBlendFuncSeparate = this._pat._addressof_glBlendFuncSeparate;
        if (addressof_glBlendFuncSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFuncSeparate"));
        }
        this.dispatch_glBlendFuncSeparate1(n, n2, n3, n4, addressof_glBlendFuncSeparate);
    }
    
    private native void dispatch_glBlendFuncSeparate1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glBlendEquation(final int n) {
        final long addressof_glBlendEquation = this._pat._addressof_glBlendEquation;
        if (addressof_glBlendEquation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquation"));
        }
        this.dispatch_glBlendEquation1(n, addressof_glBlendEquation);
    }
    
    private native void dispatch_glBlendEquation1(final int p0, final long p1);
    
    @Override
    public void glDrawTexsOES(final short n, final short n2, final short n3, final short n4, final short n5) {
        final long addressof_glDrawTexsOES = this._pat._addressof_glDrawTexsOES;
        if (addressof_glDrawTexsOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexsOES"));
        }
        this.dispatch_glDrawTexsOES1(n, n2, n3, n4, n5, addressof_glDrawTexsOES);
    }
    
    private native void dispatch_glDrawTexsOES1(final short p0, final short p1, final short p2, final short p3, final short p4, final long p5);
    
    @Override
    public void glDrawTexiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glDrawTexiOES = this._pat._addressof_glDrawTexiOES;
        if (addressof_glDrawTexiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexiOES"));
        }
        this.dispatch_glDrawTexiOES1(n, n2, n3, n4, n5, addressof_glDrawTexiOES);
    }
    
    private native void dispatch_glDrawTexiOES1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glDrawTexxOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glDrawTexxOES = this._pat._addressof_glDrawTexxOES;
        if (addressof_glDrawTexxOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexxOES"));
        }
        this.dispatch_glDrawTexxOES1(n, n2, n3, n4, n5, addressof_glDrawTexxOES);
    }
    
    private native void dispatch_glDrawTexxOES1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glDrawTexsvOES(final ShortBuffer shortBuffer) {
        final boolean direct = Buffers.isDirect(shortBuffer);
        final long addressof_glDrawTexsvOES = this._pat._addressof_glDrawTexsvOES;
        if (addressof_glDrawTexsvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexsvOES"));
        }
        this.dispatch_glDrawTexsvOES1(direct ? shortBuffer : Buffers.getArray(shortBuffer), direct ? Buffers.getDirectBufferByteOffset(shortBuffer) : Buffers.getIndirectBufferByteOffset(shortBuffer), direct, addressof_glDrawTexsvOES);
    }
    
    private native void dispatch_glDrawTexsvOES1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glDrawTexsvOES(final short[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"coords_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDrawTexsvOES = this._pat._addressof_glDrawTexsvOES;
        if (addressof_glDrawTexsvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexsvOES"));
        }
        this.dispatch_glDrawTexsvOES1(array, 2 * n, false, addressof_glDrawTexsvOES);
    }
    
    @Override
    public void glDrawTexivOES(final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDrawTexivOES = this._pat._addressof_glDrawTexivOES;
        if (addressof_glDrawTexivOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexivOES"));
        }
        this.dispatch_glDrawTexivOES1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDrawTexivOES);
    }
    
    private native void dispatch_glDrawTexivOES1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glDrawTexivOES(final int[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"coords_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDrawTexivOES = this._pat._addressof_glDrawTexivOES;
        if (addressof_glDrawTexivOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexivOES"));
        }
        this.dispatch_glDrawTexivOES1(array, 4 * n, false, addressof_glDrawTexivOES);
    }
    
    @Override
    public void glDrawTexxvOES(final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDrawTexxvOES = this._pat._addressof_glDrawTexxvOES;
        if (addressof_glDrawTexxvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexxvOES"));
        }
        this.dispatch_glDrawTexxvOES1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDrawTexxvOES);
    }
    
    private native void dispatch_glDrawTexxvOES1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glDrawTexxvOES(final int[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"coords_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDrawTexxvOES = this._pat._addressof_glDrawTexxvOES;
        if (addressof_glDrawTexxvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexxvOES"));
        }
        this.dispatch_glDrawTexxvOES1(array, 4 * n, false, addressof_glDrawTexxvOES);
    }
    
    @Override
    public void glDrawTexfOES(final float n, final float n2, final float n3, final float n4, final float n5) {
        final long addressof_glDrawTexfOES = this._pat._addressof_glDrawTexfOES;
        if (addressof_glDrawTexfOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexfOES"));
        }
        this.dispatch_glDrawTexfOES1(n, n2, n3, n4, n5, addressof_glDrawTexfOES);
    }
    
    private native void dispatch_glDrawTexfOES1(final float p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glDrawTexfvOES(final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glDrawTexfvOES = this._pat._addressof_glDrawTexfvOES;
        if (addressof_glDrawTexfvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexfvOES"));
        }
        this.dispatch_glDrawTexfvOES1(direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glDrawTexfvOES);
    }
    
    private native void dispatch_glDrawTexfvOES1(final Object p0, final int p1, final boolean p2, final long p3);
    
    @Override
    public void glDrawTexfvOES(final float[] array, final int n) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"coords_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDrawTexfvOES = this._pat._addressof_glDrawTexfvOES;
        if (addressof_glDrawTexfvOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawTexfvOES"));
        }
        this.dispatch_glDrawTexfvOES1(array, 4 * n, false, addressof_glDrawTexfvOES);
    }
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        final long addressof_glEGLImageTargetTexture2DOES = this._pat._addressof_glEGLImageTargetTexture2DOES;
        if (addressof_glEGLImageTargetTexture2DOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEGLImageTargetTexture2DOES"));
        }
        this.dispatch_glEGLImageTargetTexture2DOES1(n, n2, addressof_glEGLImageTargetTexture2DOES);
    }
    
    private native void dispatch_glEGLImageTargetTexture2DOES1(final int p0, final long p1, final long p2);
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        final long addressof_glEGLImageTargetRenderbufferStorageOES = this._pat._addressof_glEGLImageTargetRenderbufferStorageOES;
        if (addressof_glEGLImageTargetRenderbufferStorageOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEGLImageTargetRenderbufferStorageOES"));
        }
        this.dispatch_glEGLImageTargetRenderbufferStorageOES1(n, n2, addressof_glEGLImageTargetRenderbufferStorageOES);
    }
    
    private native void dispatch_glEGLImageTargetRenderbufferStorageOES1(final int p0, final long p1, final long p2);
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        final long addressof_glIsRenderbuffer = this._pat._addressof_glIsRenderbuffer;
        if (addressof_glIsRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsRenderbuffer"));
        }
        return this.dispatch_glIsRenderbuffer1(n, addressof_glIsRenderbuffer);
    }
    
    private native boolean dispatch_glIsRenderbuffer1(final int p0, final long p1);
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        final long addressof_glBindRenderbuffer = this._pat._addressof_glBindRenderbuffer;
        if (addressof_glBindRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindRenderbuffer"));
        }
        this.dispatch_glBindRenderbuffer1(n, n2, addressof_glBindRenderbuffer);
    }
    
    private native void dispatch_glBindRenderbuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteRenderbuffers = this._pat._addressof_glDeleteRenderbuffers;
        if (addressof_glDeleteRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteRenderbuffers"));
        }
        this.dispatch_glDeleteRenderbuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteRenderbuffers);
    }
    
    private native void dispatch_glDeleteRenderbuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteRenderbuffers = this._pat._addressof_glDeleteRenderbuffers;
        if (addressof_glDeleteRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteRenderbuffers"));
        }
        this.dispatch_glDeleteRenderbuffers1(n, array, 4 * n2, false, addressof_glDeleteRenderbuffers);
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenRenderbuffers = this._pat._addressof_glGenRenderbuffers;
        if (addressof_glGenRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenRenderbuffers"));
        }
        this.dispatch_glGenRenderbuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenRenderbuffers);
    }
    
    private native void dispatch_glGenRenderbuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenRenderbuffers = this._pat._addressof_glGenRenderbuffers;
        if (addressof_glGenRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenRenderbuffers"));
        }
        this.dispatch_glGenRenderbuffers1(n, array, 4 * n2, false, addressof_glGenRenderbuffers);
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glRenderbufferStorage = this._pat._addressof_glRenderbufferStorage;
        if (addressof_glRenderbufferStorage == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorage"));
        }
        this.dispatch_glRenderbufferStorage1(n, n2, n3, n4, addressof_glRenderbufferStorage);
    }
    
    private native void dispatch_glRenderbufferStorage1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetRenderbufferParameteriv = this._pat._addressof_glGetRenderbufferParameteriv;
        if (addressof_glGetRenderbufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetRenderbufferParameteriv"));
        }
        this.dispatch_glGetRenderbufferParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetRenderbufferParameteriv);
    }
    
    private native void dispatch_glGetRenderbufferParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetRenderbufferParameteriv = this._pat._addressof_glGetRenderbufferParameteriv;
        if (addressof_glGetRenderbufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetRenderbufferParameteriv"));
        }
        this.dispatch_glGetRenderbufferParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetRenderbufferParameteriv);
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        final long addressof_glIsFramebuffer = this._pat._addressof_glIsFramebuffer;
        if (addressof_glIsFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsFramebuffer"));
        }
        return this.dispatch_glIsFramebuffer1(n, addressof_glIsFramebuffer);
    }
    
    private native boolean dispatch_glIsFramebuffer1(final int p0, final long p1);
    
    @Override
    public void glBindFramebuffer(final int n, int n2) {
        if (0 == n2) {
            if (36160 == n || 36009 == n) {
                n2 = this._context.getDefaultDrawFramebuffer();
            }
            else if (36008 == n) {
                n2 = this._context.getDefaultReadFramebuffer();
            }
        }
        final long addressof_glBindFramebuffer = this._pat._addressof_glBindFramebuffer;
        if (addressof_glBindFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFramebuffer"));
        }
        this.dispatch_glBindFramebuffer1(n, n2, addressof_glBindFramebuffer);
        this._context.setBoundFramebuffer(n, n2);
    }
    
    private native void dispatch_glBindFramebuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteFramebuffers = this._pat._addressof_glDeleteFramebuffers;
        if (addressof_glDeleteFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteFramebuffers"));
        }
        this.dispatch_glDeleteFramebuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteFramebuffers);
    }
    
    private native void dispatch_glDeleteFramebuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteFramebuffers = this._pat._addressof_glDeleteFramebuffers;
        if (addressof_glDeleteFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteFramebuffers"));
        }
        this.dispatch_glDeleteFramebuffers1(n, array, 4 * n2, false, addressof_glDeleteFramebuffers);
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenFramebuffers = this._pat._addressof_glGenFramebuffers;
        if (addressof_glGenFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenFramebuffers"));
        }
        this.dispatch_glGenFramebuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenFramebuffers);
    }
    
    private native void dispatch_glGenFramebuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenFramebuffers = this._pat._addressof_glGenFramebuffers;
        if (addressof_glGenFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenFramebuffers"));
        }
        this.dispatch_glGenFramebuffers1(n, array, 4 * n2, false, addressof_glGenFramebuffers);
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        final long addressof_glCheckFramebufferStatus = this._pat._addressof_glCheckFramebufferStatus;
        if (addressof_glCheckFramebufferStatus == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCheckFramebufferStatus"));
        }
        return this.dispatch_glCheckFramebufferStatus1(n, addressof_glCheckFramebufferStatus);
    }
    
    private native int dispatch_glCheckFramebufferStatus1(final int p0, final long p1);
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glFramebufferRenderbuffer = this._pat._addressof_glFramebufferRenderbuffer;
        if (addressof_glFramebufferRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferRenderbuffer"));
        }
        this.dispatch_glFramebufferRenderbuffer1(n, n2, n3, n4, addressof_glFramebufferRenderbuffer);
    }
    
    private native void dispatch_glFramebufferRenderbuffer1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glFramebufferTexture2D = this._pat._addressof_glFramebufferTexture2D;
        if (addressof_glFramebufferTexture2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2D"));
        }
        this.dispatch_glFramebufferTexture2D1(n, n2, n3, n4, n5, addressof_glFramebufferTexture2D);
    }
    
    private native void dispatch_glFramebufferTexture2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetFramebufferAttachmentParameteriv = this._pat._addressof_glGetFramebufferAttachmentParameteriv;
        if (addressof_glGetFramebufferAttachmentParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferAttachmentParameteriv"));
        }
        this.dispatch_glGetFramebufferAttachmentParameteriv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetFramebufferAttachmentParameteriv);
    }
    
    private native void dispatch_glGetFramebufferAttachmentParameteriv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFramebufferAttachmentParameteriv = this._pat._addressof_glGetFramebufferAttachmentParameteriv;
        if (addressof_glGetFramebufferAttachmentParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferAttachmentParameteriv"));
        }
        this.dispatch_glGetFramebufferAttachmentParameteriv1(n, n2, n3, array, 4 * n4, false, addressof_glGetFramebufferAttachmentParameteriv);
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        final long addressof_glGenerateMipmap = this._pat._addressof_glGenerateMipmap;
        if (addressof_glGenerateMipmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenerateMipmap"));
        }
        this.dispatch_glGenerateMipmap1(n, addressof_glGenerateMipmap);
    }
    
    private native void dispatch_glGenerateMipmap1(final int p0, final long p1);
    
    private long glMapBufferDelegate(final int n, final int n2) {
        final long addressof_glMapBuffer = this._pat._addressof_glMapBuffer;
        if (addressof_glMapBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMapBuffer"));
        }
        return this.dispatch_glMapBufferDelegate1(n, n2, addressof_glMapBuffer);
    }
    
    private native long dispatch_glMapBufferDelegate1(final int p0, final int p1, final long p2);
    
    private boolean glUnmapBufferDelegate(final int n) {
        final long addressof_glUnmapBuffer = this._pat._addressof_glUnmapBuffer;
        if (addressof_glUnmapBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUnmapBuffer"));
        }
        return this.dispatch_glUnmapBufferDelegate1(n, addressof_glUnmapBuffer);
    }
    
    private native boolean dispatch_glUnmapBufferDelegate1(final int p0, final long p1);
    
    @Override
    public void glCurrentPaletteMatrixOES(final int n) {
        final long addressof_glCurrentPaletteMatrixOES = this._pat._addressof_glCurrentPaletteMatrixOES;
        if (addressof_glCurrentPaletteMatrixOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCurrentPaletteMatrixOES"));
        }
        this.dispatch_glCurrentPaletteMatrixOES1(n, addressof_glCurrentPaletteMatrixOES);
    }
    
    private native void dispatch_glCurrentPaletteMatrixOES1(final int p0, final long p1);
    
    @Override
    public void glLoadPaletteFromModelViewMatrixOES() {
        final long addressof_glLoadPaletteFromModelViewMatrixOES = this._pat._addressof_glLoadPaletteFromModelViewMatrixOES;
        if (addressof_glLoadPaletteFromModelViewMatrixOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLoadPaletteFromModelViewMatrixOES"));
        }
        this.dispatch_glLoadPaletteFromModelViewMatrixOES1(addressof_glLoadPaletteFromModelViewMatrixOES);
    }
    
    private native void dispatch_glLoadPaletteFromModelViewMatrixOES1(final long p0);
    
    @Override
    public void glMatrixIndexPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glMatrixIndexPointerOES = this._pat._addressof_glMatrixIndexPointerOES;
        if (addressof_glMatrixIndexPointerOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMatrixIndexPointerOES"));
        }
        this.dispatch_glMatrixIndexPointerOES1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glMatrixIndexPointerOES);
    }
    
    private native void dispatch_glMatrixIndexPointerOES1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glWeightPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glWeightPointerOES = this._pat._addressof_glWeightPointerOES;
        if (addressof_glWeightPointerOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glWeightPointerOES"));
        }
        this.dispatch_glWeightPointerOES1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glWeightPointerOES);
    }
    
    private native void dispatch_glWeightPointerOES1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public int glQueryMatrixxOES(final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glQueryMatrixxOES = this._pat._addressof_glQueryMatrixxOES;
        if (addressof_glQueryMatrixxOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glQueryMatrixxOES"));
        }
        return this.dispatch_glQueryMatrixxOES1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glQueryMatrixxOES);
    }
    
    private native int dispatch_glQueryMatrixxOES1(final Object p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public int glQueryMatrixxOES(final int[] array, final int n, final int[] array2, final int n2) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"mantissa_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n2) {
            throw new GLException("array offset argument \"exponent_offset\" (" + n2 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glQueryMatrixxOES = this._pat._addressof_glQueryMatrixxOES;
        if (addressof_glQueryMatrixxOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glQueryMatrixxOES"));
        }
        return this.dispatch_glQueryMatrixxOES1(array, 4 * n, false, array2, 4 * n2, false, addressof_glQueryMatrixxOES);
    }
    
    @Override
    public void glTexGenf(final int n, final int n2, final float n3) {
        final long addressof_glTexGenf = this._pat._addressof_glTexGenf;
        if (addressof_glTexGenf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenf"));
        }
        this.dispatch_glTexGenf1(n, n2, n3, addressof_glTexGenf);
    }
    
    private native void dispatch_glTexGenf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glTexGenfv = this._pat._addressof_glTexGenfv;
        if (addressof_glTexGenfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenfv"));
        }
        this.dispatch_glTexGenfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glTexGenfv);
    }
    
    private native void dispatch_glTexGenfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexGenfv = this._pat._addressof_glTexGenfv;
        if (addressof_glTexGenfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenfv"));
        }
        this.dispatch_glTexGenfv1(n, n2, array, 4 * n3, false, addressof_glTexGenfv);
    }
    
    @Override
    public void glTexGeni(final int n, final int n2, final int n3) {
        final long addressof_glTexGeni = this._pat._addressof_glTexGeni;
        if (addressof_glTexGeni == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGeni"));
        }
        this.dispatch_glTexGeni1(n, n2, n3, addressof_glTexGeni);
    }
    
    private native void dispatch_glTexGeni1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexGeniv = this._pat._addressof_glTexGeniv;
        if (addressof_glTexGeniv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGeniv"));
        }
        this.dispatch_glTexGeniv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexGeniv);
    }
    
    private native void dispatch_glTexGeniv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexGeniv = this._pat._addressof_glTexGeniv;
        if (addressof_glTexGeniv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGeniv"));
        }
        this.dispatch_glTexGeniv1(n, n2, array, 4 * n3, false, addressof_glTexGeniv);
    }
    
    @Override
    public void glTexGenx(final int n, final int n2, final int n3) {
        final long addressof_glTexGenx = this._pat._addressof_glTexGenx;
        if (addressof_glTexGenx == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenx"));
        }
        this.dispatch_glTexGenx1(n, n2, n3, addressof_glTexGenx);
    }
    
    private native void dispatch_glTexGenx1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexGenxv = this._pat._addressof_glTexGenxv;
        if (addressof_glTexGenxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenxv"));
        }
        this.dispatch_glTexGenxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexGenxv);
    }
    
    private native void dispatch_glTexGenxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexGenxv = this._pat._addressof_glTexGenxv;
        if (addressof_glTexGenxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexGenxv"));
        }
        this.dispatch_glTexGenxv1(n, n2, array, 4 * n3, false, addressof_glTexGenxv);
    }
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetTexGenfv = this._pat._addressof_glGetTexGenfv;
        if (addressof_glGetTexGenfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGenfv"));
        }
        this.dispatch_glGetTexGenfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetTexGenfv);
    }
    
    private native void dispatch_glGetTexGenfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexGenfv = this._pat._addressof_glGetTexGenfv;
        if (addressof_glGetTexGenfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGenfv"));
        }
        this.dispatch_glGetTexGenfv1(n, n2, array, 4 * n3, false, addressof_glGetTexGenfv);
    }
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexGeniv = this._pat._addressof_glGetTexGeniv;
        if (addressof_glGetTexGeniv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGeniv"));
        }
        this.dispatch_glGetTexGeniv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexGeniv);
    }
    
    private native void dispatch_glGetTexGeniv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexGeniv = this._pat._addressof_glGetTexGeniv;
        if (addressof_glGetTexGeniv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGeniv"));
        }
        this.dispatch_glGetTexGeniv1(n, n2, array, 4 * n3, false, addressof_glGetTexGeniv);
    }
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexGenxv = this._pat._addressof_glGetTexGenxv;
        if (addressof_glGetTexGenxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGenxv"));
        }
        this.dispatch_glGetTexGenxv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexGenxv);
    }
    
    private native void dispatch_glGetTexGenxv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexGenxv = this._pat._addressof_glGetTexGenxv;
        if (addressof_glGetTexGenxv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexGenxv"));
        }
        this.dispatch_glGetTexGenxv1(n, n2, array, 4 * n3, false, addressof_glGetTexGenxv);
    }
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        final long addressof_glBindVertexArrayOES = this._pat._addressof_glBindVertexArrayOES;
        if (addressof_glBindVertexArrayOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindVertexArrayOES"));
        }
        this.dispatch_glBindVertexArrayOES1(n, addressof_glBindVertexArrayOES);
    }
    
    private native void dispatch_glBindVertexArrayOES1(final int p0, final long p1);
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteVertexArraysOES = this._pat._addressof_glDeleteVertexArraysOES;
        if (addressof_glDeleteVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArraysOES"));
        }
        this.dispatch_glDeleteVertexArraysOES1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteVertexArraysOES);
    }
    
    private native void dispatch_glDeleteVertexArraysOES1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteVertexArraysOES = this._pat._addressof_glDeleteVertexArraysOES;
        if (addressof_glDeleteVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArraysOES"));
        }
        this.dispatch_glDeleteVertexArraysOES1(n, array, 4 * n2, false, addressof_glDeleteVertexArraysOES);
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenVertexArraysOES = this._pat._addressof_glGenVertexArraysOES;
        if (addressof_glGenVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArraysOES"));
        }
        this.dispatch_glGenVertexArraysOES1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenVertexArraysOES);
    }
    
    private native void dispatch_glGenVertexArraysOES1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenVertexArraysOES = this._pat._addressof_glGenVertexArraysOES;
        if (addressof_glGenVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArraysOES"));
        }
        this.dispatch_glGenVertexArraysOES1(n, array, 4 * n2, false, addressof_glGenVertexArraysOES);
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        final long addressof_glIsVertexArrayOES = this._pat._addressof_glIsVertexArrayOES;
        if (addressof_glIsVertexArrayOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsVertexArrayOES"));
        }
        return this.dispatch_glIsVertexArrayOES1(n, addressof_glIsVertexArrayOES);
    }
    
    private native boolean dispatch_glIsVertexArrayOES1(final int p0, final long p1);
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glCopyTextureLevelsAPPLE = this._pat._addressof_glCopyTextureLevelsAPPLE;
        if (addressof_glCopyTextureLevelsAPPLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTextureLevelsAPPLE"));
        }
        this.dispatch_glCopyTextureLevelsAPPLE1(n, n2, n3, n4, addressof_glCopyTextureLevelsAPPLE);
    }
    
    private native void dispatch_glCopyTextureLevelsAPPLE1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisample = this._pat._addressof_glRenderbufferStorageMultisample;
        if (addressof_glRenderbufferStorageMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisample"));
        }
        this.dispatch_glRenderbufferStorageMultisample1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisample);
    }
    
    private native void dispatch_glRenderbufferStorageMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        final long addressof_glResolveMultisampleFramebuffer = this._pat._addressof_glResolveMultisampleFramebuffer;
        if (addressof_glResolveMultisampleFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glResolveMultisampleFramebuffer"));
        }
        this.dispatch_glResolveMultisampleFramebuffer1(addressof_glResolveMultisampleFramebuffer);
    }
    
    private native void dispatch_glResolveMultisampleFramebuffer1(final long p0);
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDiscardFramebufferEXT = this._pat._addressof_glDiscardFramebufferEXT;
        if (addressof_glDiscardFramebufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDiscardFramebufferEXT"));
        }
        this.dispatch_glDiscardFramebufferEXT1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDiscardFramebufferEXT);
    }
    
    private native void dispatch_glDiscardFramebufferEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"attachments_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDiscardFramebufferEXT = this._pat._addressof_glDiscardFramebufferEXT;
        if (addressof_glDiscardFramebufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDiscardFramebufferEXT"));
        }
        this.dispatch_glDiscardFramebufferEXT1(n, n2, array, 4 * n3, false, addressof_glDiscardFramebufferEXT);
    }
    
    private long glMapBufferRangeDelegate(final int n, final long n2, final long n3, final int n4) {
        final long addressof_glMapBufferRange = this._pat._addressof_glMapBufferRange;
        if (addressof_glMapBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMapBufferRange"));
        }
        return this.dispatch_glMapBufferRangeDelegate1(n, n2, n3, n4, addressof_glMapBufferRange);
    }
    
    private native long dispatch_glMapBufferRangeDelegate1(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        final long addressof_glFlushMappedBufferRange = this._pat._addressof_glFlushMappedBufferRange;
        if (addressof_glFlushMappedBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFlushMappedBufferRange"));
        }
        this.dispatch_glFlushMappedBufferRange1(n, n2, n3, addressof_glFlushMappedBufferRange);
    }
    
    private native void dispatch_glFlushMappedBufferRange1(final int p0, final long p1, final long p2, final long p3);
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisampleEXT = this._pat._addressof_glRenderbufferStorageMultisampleEXT;
        if (addressof_glRenderbufferStorageMultisampleEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisampleEXT"));
        }
        this.dispatch_glRenderbufferStorageMultisampleEXT1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisampleEXT);
    }
    
    private native void dispatch_glRenderbufferStorageMultisampleEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTexture2DMultisampleEXT = this._pat._addressof_glFramebufferTexture2DMultisampleEXT;
        if (addressof_glFramebufferTexture2DMultisampleEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2DMultisampleEXT"));
        }
        this.dispatch_glFramebufferTexture2DMultisampleEXT1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTexture2DMultisampleEXT);
    }
    
    private native void dispatch_glFramebufferTexture2DMultisampleEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public int glGetGraphicsResetStatus() {
        final long addressof_glGetGraphicsResetStatus = this._pat._addressof_glGetGraphicsResetStatus;
        if (addressof_glGetGraphicsResetStatus == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetGraphicsResetStatus"));
        }
        return this.dispatch_glGetGraphicsResetStatus1(addressof_glGetGraphicsResetStatus);
    }
    
    private native int dispatch_glGetGraphicsResetStatus1(final long p0);
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glReadnPixels = this._pat._addressof_glReadnPixels;
        if (addressof_glReadnPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadnPixels"));
        }
        this.dispatch_glReadnPixels1(n, n2, n3, n4, n5, n6, n7, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glReadnPixels);
    }
    
    private native void dispatch_glReadnPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetnUniformfv = this._pat._addressof_glGetnUniformfv;
        if (addressof_glGetnUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformfv"));
        }
        this.dispatch_glGetnUniformfv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetnUniformfv);
    }
    
    private native void dispatch_glGetnUniformfv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetnUniformfv = this._pat._addressof_glGetnUniformfv;
        if (addressof_glGetnUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformfv"));
        }
        this.dispatch_glGetnUniformfv1(n, n2, n3, array, 4 * n4, false, addressof_glGetnUniformfv);
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetnUniformiv = this._pat._addressof_glGetnUniformiv;
        if (addressof_glGetnUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformiv"));
        }
        this.dispatch_glGetnUniformiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetnUniformiv);
    }
    
    private native void dispatch_glGetnUniformiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetnUniformiv = this._pat._addressof_glGetnUniformiv;
        if (addressof_glGetnUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformiv"));
        }
        this.dispatch_glGetnUniformiv1(n, n2, n3, array, 4 * n4, false, addressof_glGetnUniformiv);
    }
    
    @Override
    public void glClipPlanefIMG(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glClipPlanefIMG = this._pat._addressof_glClipPlanefIMG;
        if (addressof_glClipPlanefIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanefIMG"));
        }
        this.dispatch_glClipPlanefIMG1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glClipPlanefIMG);
    }
    
    private native void dispatch_glClipPlanefIMG1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glClipPlanefIMG(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arg1_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClipPlanefIMG = this._pat._addressof_glClipPlanefIMG;
        if (addressof_glClipPlanefIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanefIMG"));
        }
        this.dispatch_glClipPlanefIMG1(n, array, 4 * n2, false, addressof_glClipPlanefIMG);
    }
    
    @Override
    public void glClipPlanexIMG(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glClipPlanexIMG = this._pat._addressof_glClipPlanexIMG;
        if (addressof_glClipPlanexIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanexIMG"));
        }
        this.dispatch_glClipPlanexIMG1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glClipPlanexIMG);
    }
    
    private native void dispatch_glClipPlanexIMG1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glClipPlanexIMG(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arg1_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClipPlanexIMG = this._pat._addressof_glClipPlanexIMG;
        if (addressof_glClipPlanexIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClipPlanexIMG"));
        }
        this.dispatch_glClipPlanexIMG1(n, array, 4 * n2, false, addressof_glClipPlanexIMG);
    }
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisampleIMG = this._pat._addressof_glRenderbufferStorageMultisampleIMG;
        if (addressof_glRenderbufferStorageMultisampleIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisampleIMG"));
        }
        this.dispatch_glRenderbufferStorageMultisampleIMG1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisampleIMG);
    }
    
    private native void dispatch_glRenderbufferStorageMultisampleIMG1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTexture2DMultisampleIMG = this._pat._addressof_glFramebufferTexture2DMultisampleIMG;
        if (addressof_glFramebufferTexture2DMultisampleIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2DMultisampleIMG"));
        }
        this.dispatch_glFramebufferTexture2DMultisampleIMG1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTexture2DMultisampleIMG);
    }
    
    private native void dispatch_glFramebufferTexture2DMultisampleIMG1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetDriverControlsQCOM = this._pat._addressof_glGetDriverControlsQCOM;
        if (addressof_glGetDriverControlsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlsQCOM"));
        }
        this.dispatch_glGetDriverControlsQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetDriverControlsQCOM);
    }
    
    private native void dispatch_glGetDriverControlsQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"num_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"driverControls_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetDriverControlsQCOM = this._pat._addressof_glGetDriverControlsQCOM;
        if (addressof_glGetDriverControlsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlsQCOM"));
        }
        this.dispatch_glGetDriverControlsQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glGetDriverControlsQCOM);
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetDriverControlStringQCOM = this._pat._addressof_glGetDriverControlStringQCOM;
        if (addressof_glGetDriverControlStringQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlStringQCOM"));
        }
        this.dispatch_glGetDriverControlStringQCOM1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetDriverControlStringQCOM);
    }
    
    private native void dispatch_glGetDriverControlStringQCOM1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"driverControlString_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetDriverControlStringQCOM = this._pat._addressof_glGetDriverControlStringQCOM;
        if (addressof_glGetDriverControlStringQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlStringQCOM"));
        }
        this.dispatch_glGetDriverControlStringQCOM1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetDriverControlStringQCOM);
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        final long addressof_glEnableDriverControlQCOM = this._pat._addressof_glEnableDriverControlQCOM;
        if (addressof_glEnableDriverControlQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableDriverControlQCOM"));
        }
        this.dispatch_glEnableDriverControlQCOM1(n, addressof_glEnableDriverControlQCOM);
    }
    
    private native void dispatch_glEnableDriverControlQCOM1(final int p0, final long p1);
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        final long addressof_glDisableDriverControlQCOM = this._pat._addressof_glDisableDriverControlQCOM;
        if (addressof_glDisableDriverControlQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableDriverControlQCOM"));
        }
        this.dispatch_glDisableDriverControlQCOM1(n, addressof_glDisableDriverControlQCOM);
    }
    
    private native void dispatch_glDisableDriverControlQCOM1(final int p0, final long p1);
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetTexturesQCOM = this._pat._addressof_glExtGetTexturesQCOM;
        if (addressof_glExtGetTexturesQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexturesQCOM"));
        }
        this.dispatch_glExtGetTexturesQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetTexturesQCOM);
    }
    
    private native void dispatch_glExtGetTexturesQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"textures_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numTextures_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetTexturesQCOM = this._pat._addressof_glExtGetTexturesQCOM;
        if (addressof_glExtGetTexturesQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexturesQCOM"));
        }
        this.dispatch_glExtGetTexturesQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetTexturesQCOM);
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetBuffersQCOM = this._pat._addressof_glExtGetBuffersQCOM;
        if (addressof_glExtGetBuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBuffersQCOM"));
        }
        this.dispatch_glExtGetBuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetBuffersQCOM);
    }
    
    private native void dispatch_glExtGetBuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numBuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetBuffersQCOM = this._pat._addressof_glExtGetBuffersQCOM;
        if (addressof_glExtGetBuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBuffersQCOM"));
        }
        this.dispatch_glExtGetBuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetBuffersQCOM);
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetRenderbuffersQCOM = this._pat._addressof_glExtGetRenderbuffersQCOM;
        if (addressof_glExtGetRenderbuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetRenderbuffersQCOM"));
        }
        this.dispatch_glExtGetRenderbuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetRenderbuffersQCOM);
    }
    
    private native void dispatch_glExtGetRenderbuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numRenderbuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetRenderbuffersQCOM = this._pat._addressof_glExtGetRenderbuffersQCOM;
        if (addressof_glExtGetRenderbuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetRenderbuffersQCOM"));
        }
        this.dispatch_glExtGetRenderbuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetRenderbuffersQCOM);
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetFramebuffersQCOM = this._pat._addressof_glExtGetFramebuffersQCOM;
        if (addressof_glExtGetFramebuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetFramebuffersQCOM"));
        }
        this.dispatch_glExtGetFramebuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetFramebuffersQCOM);
    }
    
    private native void dispatch_glExtGetFramebuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numFramebuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetFramebuffersQCOM = this._pat._addressof_glExtGetFramebuffersQCOM;
        if (addressof_glExtGetFramebuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetFramebuffersQCOM"));
        }
        this.dispatch_glExtGetFramebuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetFramebuffersQCOM);
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glExtGetTexLevelParameterivQCOM = this._pat._addressof_glExtGetTexLevelParameterivQCOM;
        if (addressof_glExtGetTexLevelParameterivQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexLevelParameterivQCOM"));
        }
        this.dispatch_glExtGetTexLevelParameterivQCOM1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glExtGetTexLevelParameterivQCOM);
    }
    
    private native void dispatch_glExtGetTexLevelParameterivQCOM1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"params_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glExtGetTexLevelParameterivQCOM = this._pat._addressof_glExtGetTexLevelParameterivQCOM;
        if (addressof_glExtGetTexLevelParameterivQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexLevelParameterivQCOM"));
        }
        this.dispatch_glExtGetTexLevelParameterivQCOM1(n, n2, n3, n4, array, 4 * n5, false, addressof_glExtGetTexLevelParameterivQCOM);
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        final long addressof_glExtTexObjectStateOverrideiQCOM = this._pat._addressof_glExtTexObjectStateOverrideiQCOM;
        if (addressof_glExtTexObjectStateOverrideiQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtTexObjectStateOverrideiQCOM"));
        }
        this.dispatch_glExtTexObjectStateOverrideiQCOM1(n, n2, n3, addressof_glExtTexObjectStateOverrideiQCOM);
    }
    
    private native void dispatch_glExtTexObjectStateOverrideiQCOM1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glExtGetTexSubImageQCOM = this._pat._addressof_glExtGetTexSubImageQCOM;
        if (addressof_glExtGetTexSubImageQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexSubImageQCOM"));
        }
        this.dispatch_glExtGetTexSubImageQCOM1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glExtGetTexSubImageQCOM);
    }
    
    private native void dispatch_glExtGetTexSubImageQCOM1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"params\" is not a direct buffer");
        }
        final long addressof_glExtGetBufferPointervQCOM = this._pat._addressof_glExtGetBufferPointervQCOM;
        if (addressof_glExtGetBufferPointervQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBufferPointervQCOM"));
        }
        this.dispatch_glExtGetBufferPointervQCOM0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_glExtGetBufferPointervQCOM);
    }
    
    private native void dispatch_glExtGetBufferPointervQCOM0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetShadersQCOM = this._pat._addressof_glExtGetShadersQCOM;
        if (addressof_glExtGetShadersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetShadersQCOM"));
        }
        this.dispatch_glExtGetShadersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetShadersQCOM);
    }
    
    private native void dispatch_glExtGetShadersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"shaders_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numShaders_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetShadersQCOM = this._pat._addressof_glExtGetShadersQCOM;
        if (addressof_glExtGetShadersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetShadersQCOM"));
        }
        this.dispatch_glExtGetShadersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetShadersQCOM);
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetProgramsQCOM = this._pat._addressof_glExtGetProgramsQCOM;
        if (addressof_glExtGetProgramsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramsQCOM"));
        }
        this.dispatch_glExtGetProgramsQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetProgramsQCOM);
    }
    
    private native void dispatch_glExtGetProgramsQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"programs_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numPrograms_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetProgramsQCOM = this._pat._addressof_glExtGetProgramsQCOM;
        if (addressof_glExtGetProgramsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramsQCOM"));
        }
        this.dispatch_glExtGetProgramsQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetProgramsQCOM);
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        final long addressof_glExtIsProgramBinaryQCOM = this._pat._addressof_glExtIsProgramBinaryQCOM;
        if (addressof_glExtIsProgramBinaryQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtIsProgramBinaryQCOM"));
        }
        return this.dispatch_glExtIsProgramBinaryQCOM1(n, addressof_glExtIsProgramBinaryQCOM);
    }
    
    private native boolean dispatch_glExtIsProgramBinaryQCOM1(final int p0, final long p1);
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer);
        final long addressof_glExtGetProgramBinarySourceQCOM = this._pat._addressof_glExtGetProgramBinarySourceQCOM;
        if (addressof_glExtGetProgramBinarySourceQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramBinarySourceQCOM"));
        }
        this.dispatch_glExtGetProgramBinarySourceQCOM1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, direct2 ? intBuffer : Buffers.getArray(intBuffer), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct2, addressof_glExtGetProgramBinarySourceQCOM);
    }
    
    private native void dispatch_glExtGetProgramBinarySourceQCOM1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"source_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetProgramBinarySourceQCOM = this._pat._addressof_glExtGetProgramBinarySourceQCOM;
        if (addressof_glExtGetProgramBinarySourceQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramBinarySourceQCOM"));
        }
        this.dispatch_glExtGetProgramBinarySourceQCOM1(n, n2, array, n3, false, array2, 4 * n4, false, addressof_glExtGetProgramBinarySourceQCOM);
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glStartTilingQCOM = this._pat._addressof_glStartTilingQCOM;
        if (addressof_glStartTilingQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStartTilingQCOM"));
        }
        this.dispatch_glStartTilingQCOM1(n, n2, n3, n4, n5, addressof_glStartTilingQCOM);
    }
    
    private native void dispatch_glStartTilingQCOM1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glEndTilingQCOM(final int n) {
        final long addressof_glEndTilingQCOM = this._pat._addressof_glEndTilingQCOM;
        if (addressof_glEndTilingQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEndTilingQCOM"));
        }
        this.dispatch_glEndTilingQCOM1(n, addressof_glEndTilingQCOM);
    }
    
    private native void dispatch_glEndTilingQCOM1(final int p0, final long p1);
    
    @Override
    public void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.glOrthof((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6);
    }
    
    @Override
    public void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.glFrustumf((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6);
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.glClearDepthf((float)n);
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.glDepthRangef((float)n, (float)n2);
    }
    
    @Override
    public GLProfile getGLProfile() {
        return this.glProfile;
    }
    
    @Override
    public final int getBoundBuffer(final int n) {
        return this.bufferStateTracker.getBoundBufferObject(n, this);
    }
    
    @Override
    public final GLBufferStorage getBufferStorage(final int n) {
        return this.bufferObjectTracker.getBufferStorage(n);
    }
    
    @Override
    public final boolean isVBOArrayBound() {
        return this.checkArrayVBOBound(false);
    }
    
    @Override
    public final boolean isVBOElementArrayBound() {
        return this.checkElementVBOBound(false);
    }
    
    @Override
    public final GL getDownstreamGL() throws GLException {
        return null;
    }
    
    @Override
    public final GL getRootGL() throws GLException {
        return this;
    }
    
    @Override
    public final boolean isGL() {
        return true;
    }
    
    @Override
    public final GL getGL() throws GLException {
        return this;
    }
    
    @Override
    public final boolean isFunctionAvailable(final String s) {
        return this._context.isFunctionAvailable(s);
    }
    
    @Override
    public final boolean isExtensionAvailable(final String s) {
        return this._context.isExtensionAvailable(s);
    }
    
    @Override
    public final Object getExtension(final String s) {
        return null;
    }
    
    @Override
    public final boolean hasBasicFBOSupport() {
        return this._context.hasBasicFBOSupport();
    }
    
    @Override
    public final boolean hasFullFBOSupport() {
        return this._context.hasFullFBOSupport();
    }
    
    @Override
    public final int getMaxRenderbufferSamples() {
        return this._context.getMaxRenderbufferSamples();
    }
    
    @Override
    public final boolean isTextureFormatBGRA8888Available() {
        return this._context.isTextureFormatBGRA8888Available();
    }
    
    @Override
    public final GLContext getContext() {
        return this._context;
    }
    
    @Override
    public final void setSwapInterval(final int swapInterval) {
        this._context.setSwapInterval(swapInterval);
    }
    
    @Override
    public final int getSwapInterval() {
        return this._context.getSwapInterval();
    }
    
    @Override
    public final Object getPlatformGLExtensions() {
        return this._context.getPlatformGLExtensions();
    }
    
    @Override
    public final int getBoundFramebuffer(final int n) {
        return this._context.getBoundFramebuffer(n);
    }
    
    @Override
    public final int getDefaultDrawFramebuffer() {
        return this._context.getDefaultDrawFramebuffer();
    }
    
    @Override
    public final int getDefaultReadFramebuffer() {
        return this._context.getDefaultReadFramebuffer();
    }
    
    @Override
    public final int getDefaultReadBuffer() {
        return this._context.getDefaultReadBuffer();
    }
    
    @Override
    public final void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.bufferObjectTracker.createBufferStorage(this.bufferStateTracker, this, n, n2, buffer, n3, 0, this.glBufferDataDispatch);
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        return this.bufferObjectTracker.unmapBuffer(this.bufferStateTracker, this, n, this.glUnmapBufferDispatch);
    }
    
    @Override
    public final ByteBuffer glMapBuffer(final int n, final int n2) {
        return this.mapBuffer(n, n2).getMappedBuffer();
    }
    
    @Override
    public final GLBufferStorage mapBuffer(final int n, final int n2) {
        return this.bufferObjectTracker.mapBuffer(this.bufferStateTracker, this, n, n2, this.glMapBufferDispatch);
    }
    
    @Override
    public final ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.mapBufferRange(n, n2, n3, n4).getMappedBuffer();
    }
    
    @Override
    public final GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.bufferObjectTracker.mapBuffer(this.bufferStateTracker, this, n, n2, n3, n4, this.glMapBufferRangeDispatch);
    }
    
    private native ByteBuffer newDirectByteBuffer(final long p0, final long p1);
    
    public GLES1Impl(final GLProfile glProfile, final GLContextImpl context) {
        this.glBufferDataDispatch = new GLBufferObjectTracker.CreateStorageDispatch() {
            @Override
            public final void create(final int n, final long n2, final Buffer buffer, final int n3) {
                GLES1Impl.this.glBufferDataDelegate(n, n2, buffer, n3);
            }
        };
        this.glUnmapBufferDispatch = new GLBufferObjectTracker.UnmapBufferDispatch() {
            @Override
            public final boolean unmap(final int n) {
                return GLES1Impl.this.glUnmapBufferDelegate(n);
            }
        };
        this.glMapBufferDispatch = new GLBufferObjectTracker.MapBufferAllDispatch() {
            @Override
            public final ByteBuffer allocNioByteBuffer(final long n, final long n2) {
                return GLES1Impl.this.newDirectByteBuffer(n, n2);
            }
            
            @Override
            public final long mapBuffer(final int n, final int n2) {
                return GLES1Impl.this.glMapBufferDelegate(n, n2);
            }
        };
        this.glMapBufferRangeDispatch = new GLBufferObjectTracker.MapBufferRangeDispatch() {
            @Override
            public final ByteBuffer allocNioByteBuffer(final long n, final long n2) {
                return GLES1Impl.this.newDirectByteBuffer(n, n2);
            }
            
            @Override
            public final long mapBuffer(final int n, final long n2, final long n3, final int n4) {
                return GLES1Impl.this.glMapBufferRangeDelegate(n, n2, n3, n4);
            }
        };
        this.imageSizeTemp = new int[1];
        this._context = context;
        this._pat = (GLES1ProcAddressTable)this._context.getGLProcAddressTable();
        this.bufferObjectTracker = context.getBufferObjectTracker();
        this.bufferStateTracker = context.getBufferStateTracker();
        this.glStateTracker = context.getGLStateTracker();
        this.glProfile = glProfile;
    }
    
    public final void finalizeInit() {
    }
    
    private final int imageSizeInBytes(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        return GLBuffers.sizeof(this, this.imageSizeTemp, n, n2, n3, n4, n5, b);
    }
    
    @Override
    public final boolean isGL4bc() {
        return false;
    }
    
    @Override
    public final boolean isGL4() {
        return false;
    }
    
    @Override
    public final boolean isGL3bc() {
        return false;
    }
    
    @Override
    public final boolean isGL3() {
        return false;
    }
    
    @Override
    public final boolean isGL2() {
        return false;
    }
    
    @Override
    public final boolean isGLES1() {
        return true;
    }
    
    @Override
    public final boolean isGLES2() {
        return false;
    }
    
    @Override
    public final boolean isGLES3() {
        return false;
    }
    
    @Override
    public final boolean isGLES() {
        return true;
    }
    
    @Override
    public final boolean isGL2ES1() {
        return true;
    }
    
    @Override
    public final boolean isGL2ES2() {
        return false;
    }
    
    @Override
    public final boolean isGL2ES3() {
        return false;
    }
    
    @Override
    public final boolean isGL3ES3() {
        return false;
    }
    
    @Override
    public final boolean isGL4ES3() {
        return false;
    }
    
    @Override
    public final boolean isGL4core() {
        return false;
    }
    
    @Override
    public final boolean isGL3core() {
        return false;
    }
    
    @Override
    public final boolean isGLcore() {
        return false;
    }
    
    @Override
    public final boolean isGLES2Compatible() {
        return false;
    }
    
    @Override
    public final boolean isGLES3Compatible() {
        return false;
    }
    
    @Override
    public final boolean isGLES31Compatible() {
        return false;
    }
    
    @Override
    public final boolean isGLES32Compatible() {
        return false;
    }
    
    @Override
    public final boolean isGL2GL3() {
        return false;
    }
    
    @Override
    public final boolean hasGLSL() {
        return false;
    }
    
    @Override
    public boolean isNPOTTextureAvailable() {
        return false;
    }
    
    @Override
    public final GL4bc getGL4bc() throws GLException {
        throw new GLException("Not a GL4bc implementation");
    }
    
    @Override
    public final GL4 getGL4() throws GLException {
        throw new GLException("Not a GL4 implementation");
    }
    
    @Override
    public final GL3bc getGL3bc() throws GLException {
        throw new GLException("Not a GL3bc implementation");
    }
    
    @Override
    public final GL3 getGL3() throws GLException {
        throw new GLException("Not a GL3 implementation");
    }
    
    @Override
    public final GL2 getGL2() throws GLException {
        throw new GLException("Not a GL2 implementation");
    }
    
    @Override
    public final GLES1 getGLES1() throws GLException {
        return this;
    }
    
    @Override
    public final GLES2 getGLES2() throws GLException {
        throw new GLException("Not a GLES2 implementation");
    }
    
    @Override
    public final GLES3 getGLES3() throws GLException {
        throw new GLException("Not a GLES3 implementation");
    }
    
    @Override
    public final GL2ES1 getGL2ES1() throws GLException {
        return this;
    }
    
    @Override
    public final GL2ES3 getGL2ES3() throws GLException {
        throw new GLException("Not a GL2ES3 implementation");
    }
    
    @Override
    public final GL2ES2 getGL2ES2() throws GLException {
        throw new GLException("Not a GL2ES2 implementation");
    }
    
    @Override
    public final GL3ES3 getGL3ES3() throws GLException {
        throw new GLException("Not a GL3ES3 implementation");
    }
    
    @Override
    public final GL4ES3 getGL4ES3() throws GLException {
        throw new GLException("Not a GL4ES3 implementation");
    }
    
    @Override
    public final GL2GL3 getGL2GL3() throws GLException {
        throw new GLException("Not a GL2GL3 implementation");
    }
    
    private final boolean checkBufferObject(final boolean b, final int n, final String s, final boolean b2) {
        final int boundBufferObject = this.bufferStateTracker.getBoundBufferObject(n, this);
        if (b) {
            if (boundBufferObject == 0) {
                if (b2) {
                    throw new GLException(s + " must be bound to call this method");
                }
                return false;
            }
        }
        else if (boundBufferObject != 0) {
            if (b2) {
                throw new GLException(s + " must be unbound to call this method");
            }
            return false;
        }
        return true;
    }
    
    private final boolean checkArrayVBOUnbound(final boolean b) {
        return this.checkBufferObject(false, 34962, "array vertex_buffer_object", b);
    }
    
    private final boolean checkArrayVBOBound(final boolean b) {
        return this.checkBufferObject(true, 34962, "array vertex_buffer_object", b);
    }
    
    private final boolean checkElementVBOUnbound(final boolean b) {
        return this.checkBufferObject(false, 34963, "element vertex_buffer_object", b);
    }
    
    private final boolean checkElementVBOBound(final boolean b) {
        return this.checkBufferObject(true, 34963, "element vertex_buffer_object", b);
    }
    
    private final boolean checkUnpackPBOUnbound(final boolean b) {
        return true;
    }
    
    private final boolean checkUnpackPBOBound(final boolean b) {
        return false;
    }
    
    private final boolean checkPackPBOUnbound(final boolean b) {
        return true;
    }
    
    private final boolean checkPackPBOBound(final boolean b) {
        return false;
    }
    
    @Override
    public final void glVertexPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() == 0) {
            return;
        }
        if (glArrayData.isVBO()) {
            this.glVertexPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getVBOOffset());
        }
        else {
            this.glVertexPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getBuffer());
        }
    }
    
    @Override
    public final void glColorPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() == 0) {
            return;
        }
        if (glArrayData.isVBO()) {
            this.glColorPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getVBOOffset());
        }
        else {
            this.glColorPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getBuffer());
        }
    }
    
    @Override
    public final void glNormalPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() == 0) {
            return;
        }
        if (glArrayData.getComponentCount() != 3) {
            throw new GLException("Only 3 components per normal allowed");
        }
        if (glArrayData.isVBO()) {
            this.glNormalPointer(glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getVBOOffset());
        }
        else {
            this.glNormalPointer(glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getBuffer());
        }
    }
    
    @Override
    public final void glTexCoordPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() == 0) {
            return;
        }
        if (glArrayData.isVBO()) {
            this.glTexCoordPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getVBOOffset());
        }
        else {
            this.glTexCoordPointer(glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getStride(), glArrayData.getBuffer());
        }
    }
}
