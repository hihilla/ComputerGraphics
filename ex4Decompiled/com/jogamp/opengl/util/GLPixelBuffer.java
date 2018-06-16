// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;

public class GLPixelBuffer
{
    public static final GLPixelBufferProvider defaultProviderNoRowStride;
    public static final GLPixelBufferProvider defaultProviderWithRowStride;
    public final GLPixelAttributes pixelAttributes;
    public final int width;
    public final int height;
    public final int depth;
    public final boolean pack;
    public final int byteSize;
    public final Buffer buffer;
    public final int bufferElemSize;
    public final boolean allowRowStride;
    private boolean disposed;
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append(this.pixelAttributes).append(", dim ").append(this.width).append("x").append(this.height).append("x").append(this.depth).append(", pack ").append(this.pack).append(", disposed ").append(this.disposed).append(", valid ").append(this.isValid()).append(", buffer[bytes ").append(this.byteSize).append(", elemSize ").append(this.bufferElemSize).append(", ").append(this.buffer).append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return "GLPixelBuffer[" + this.toString(null).toString() + "]";
    }
    
    public GLPixelBuffer(final GLPixelAttributes pixelAttributes, final boolean pack, final int width, final int height, final int depth, final Buffer buffer, final boolean allowRowStride) {
        this.disposed = false;
        this.pixelAttributes = pixelAttributes;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.pack = pack;
        this.buffer = buffer;
        this.byteSize = Buffers.remainingBytes(buffer);
        this.bufferElemSize = Buffers.sizeOfBufferElem(buffer);
        this.allowRowStride = allowRowStride;
    }
    
    public final boolean getAllowRowStride() {
        return this.allowRowStride;
    }
    
    public boolean isValid() {
        return !this.disposed && 0 < this.byteSize;
    }
    
    public Buffer rewind() {
        return this.buffer.rewind();
    }
    
    public int position() {
        return this.buffer.position() * this.bufferElemSize;
    }
    
    public Buffer position(final int n) {
        return this.buffer.position(n / this.bufferElemSize);
    }
    
    public int capacity() {
        return this.buffer.capacity() * this.bufferElemSize;
    }
    
    public int limit() {
        return this.buffer.limit() * this.bufferElemSize;
    }
    
    public Buffer flip() {
        return this.buffer.flip();
    }
    
    public Buffer clear() {
        return this.buffer.clear();
    }
    
    public boolean requiresNewBuffer(final GL gl, final int n, final int n2, int sizeof) {
        if (!this.isValid()) {
            return true;
        }
        if (0 >= sizeof) {
            sizeof = GLBuffers.sizeof(gl, new int[] { 0 }, this.pixelAttributes.pfmt.comp.bytesPerPixel(), n, n2, 1, true);
        }
        if (this.allowRowStride) {
            return this.byteSize < sizeof;
        }
        return this.byteSize < sizeof || this.width != n;
    }
    
    public void dispose() {
        this.disposed = true;
        this.buffer.clear();
    }
    
    static {
        defaultProviderNoRowStride = new DefaultGLPixelBufferProvider(false);
        defaultProviderWithRowStride = new DefaultGLPixelBufferProvider(true);
    }
    
    public static class DefaultGLPixelBufferProvider implements GLPixelBufferProvider
    {
        private final boolean allowRowStride;
        
        public DefaultGLPixelBufferProvider(final boolean allowRowStride) {
            this.allowRowStride = allowRowStride;
        }
        
        @Override
        public boolean getAllowRowStride() {
            return this.allowRowStride;
        }
        
        @Override
        public GLPixelAttributes getAttributes(final GL gl, final int n, final boolean b) {
            final GLPixelAttributes convert = GLPixelAttributes.convert(gl, n, b);
            if (null == convert) {
                throw new GLException("Unsupported componentCount " + n + ", contact maintainer to enhance");
            }
            return convert;
        }
        
        @Override
        public PixelFormat.Composition getHostPixelComp(final GLProfile glProfile, final int n) {
            return null;
        }
        
        @Override
        public GLPixelBuffer allocate(final GL gl, final PixelFormat.Composition composition, final GLPixelAttributes glPixelAttributes, final boolean b, final int n, final int n2, final int n3, final int n4) {
            if (n4 > 0) {
                return new GLPixelBuffer(glPixelAttributes, b, n, n2, n3, Buffers.newDirectByteBuffer(n4), this.getAllowRowStride());
            }
            return new GLPixelBuffer(glPixelAttributes, b, n, n2, n3, Buffers.newDirectByteBuffer(GLBuffers.sizeof(gl, new int[] { 0 }, glPixelAttributes.pfmt.comp.bytesPerPixel(), n, n2, n3, b)), this.getAllowRowStride());
        }
    }
    
    public static class GLPixelAttributes
    {
        public static final GLPixelAttributes UNDEF;
        public final int format;
        public final int type;
        public final PixelFormat pfmt;
        
        public static final PixelFormat getPixelFormat(final int n, final int n2) {
            PixelFormat pixelFormat = null;
            Label_0271: {
                switch (n) {
                    case 6403:
                    case 6406:
                    case 6409: {
                        pixelFormat = PixelFormat.LUMINANCE;
                        break;
                    }
                    case 6407: {
                        switch (n2) {
                            case 33636: {
                                pixelFormat = PixelFormat.RGB565;
                                break;
                            }
                            case 33635: {
                                pixelFormat = PixelFormat.BGR565;
                                break;
                            }
                            case 5121: {
                                pixelFormat = PixelFormat.RGB888;
                                break;
                            }
                        }
                        break;
                    }
                    case 6408: {
                        switch (n2) {
                            case 33638: {
                                pixelFormat = PixelFormat.RGBA5551;
                                break;
                            }
                            case 32820: {
                                pixelFormat = PixelFormat.ABGR1555;
                                break;
                            }
                            case 5121:
                            case 33639: {
                                pixelFormat = PixelFormat.RGBA8888;
                                break;
                            }
                            case 32821: {
                                pixelFormat = PixelFormat.ABGR8888;
                                break;
                            }
                        }
                        break;
                    }
                    case 32992: {
                        if (5121 == n2) {
                            pixelFormat = PixelFormat.BGR888;
                            break;
                        }
                        break;
                    }
                    case 32993: {
                        switch (n2) {
                            case 32821: {
                                pixelFormat = PixelFormat.ARGB8888;
                                break Label_0271;
                            }
                            case 5121:
                            case 33639: {
                                pixelFormat = PixelFormat.BGRA8888;
                                break Label_0271;
                            }
                        }
                        break;
                    }
                }
            }
            return pixelFormat;
        }
        
        public static GLPixelAttributes convert(final GL gl, final int n, final boolean b) {
            final boolean b2 = b && gl.isGLES();
            int n2;
            int defaultPixelDataType;
            if (1 == n && !b2) {
                if (gl.isGL3ES3()) {
                    n2 = 6403;
                }
                else {
                    n2 = 6406;
                }
                defaultPixelDataType = 5121;
            }
            else if (3 == n && !b2) {
                n2 = 6407;
                defaultPixelDataType = 5121;
            }
            else {
                if (4 != n && !b2) {
                    return null;
                }
                final GLContext context = gl.getContext();
                final int defaultPixelDataFormat = context.getDefaultPixelDataFormat();
                final int componentCount = GLBuffers.componentCount(defaultPixelDataFormat);
                if (componentCount == n || 4 == componentCount) {
                    n2 = defaultPixelDataFormat;
                    defaultPixelDataType = context.getDefaultPixelDataType();
                }
                else {
                    n2 = 6408;
                    defaultPixelDataType = 5121;
                }
            }
            return new GLPixelAttributes(n2, defaultPixelDataType);
        }
        
        public static final GLPixelAttributes convert(final GLProfile glProfile, final PixelFormat pixelFormat, final boolean b) {
            final int[] array = { 0 };
            final int[] array2 = { 0 };
            convert(glProfile, pixelFormat, b, array, array2);
            if (0 != array[0]) {
                return new GLPixelAttributes(null, pixelFormat, array[0], array2[0], true, true);
            }
            return null;
        }
        
        private static final int convert(final GLProfile glProfile, final PixelFormat pixelFormat, final boolean b, final int[] array, final int[] array2) {
            final boolean b2 = b && glProfile.isGLES();
            int n = 0;
            int n2 = 5121;
            switch (pixelFormat) {
                case LUMINANCE: {
                    if (b2) {
                        break;
                    }
                    if (glProfile.isGL3ES3()) {
                        n = 6403;
                        break;
                    }
                    n = 6409;
                    break;
                }
                case RGB565: {
                    if (glProfile.isGL2GL3()) {
                        n = 6407;
                        n2 = 33636;
                        break;
                    }
                    break;
                }
                case BGR565: {
                    if (glProfile.isGL2GL3()) {
                        n = 6407;
                        n2 = 33635;
                        break;
                    }
                    break;
                }
                case RGBA5551: {
                    if (glProfile.isGL2GL3()) {
                        n = 6408;
                        n2 = 33638;
                        break;
                    }
                    break;
                }
                case ABGR1555: {
                    if (glProfile.isGL2GL3()) {
                        n = 6408;
                        n2 = 32820;
                        break;
                    }
                    break;
                }
                case RGB888: {
                    if (!b2) {
                        n = 6407;
                        break;
                    }
                    break;
                }
                case BGR888: {
                    if (glProfile.isGL2GL3()) {
                        n = 32992;
                        break;
                    }
                    break;
                }
                case RGBx8888:
                case RGBA8888: {
                    n = 6408;
                    break;
                }
                case ABGR8888: {
                    if (glProfile.isGL2GL3()) {
                        n = 6408;
                        n2 = 32821;
                        break;
                    }
                    break;
                }
                case ARGB8888: {
                    if (glProfile.isGL2GL3()) {
                        n = 32993;
                        n2 = 32821;
                        break;
                    }
                    break;
                }
                case BGRx8888:
                case BGRA8888: {
                    if (glProfile.isGL2GL3()) {
                        n = 32993;
                        break;
                    }
                    break;
                }
            }
            array[0] = n;
            array2[0] = n2;
            return n;
        }
        
        @Override
        public final int hashCode() {
            final int hashCode = this.pfmt.hashCode();
            final int n = (hashCode << 5) - hashCode + this.format;
            return (n << 5) - n + this.type;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof GLPixelAttributes) {
                final GLPixelAttributes glPixelAttributes = (GLPixelAttributes)o;
                return this.format == glPixelAttributes.format && this.type == glPixelAttributes.type && this.pfmt.equals(glPixelAttributes.pfmt);
            }
            return false;
        }
        
        public GLPixelAttributes(final int n, final int n2) throws GLException {
            this(null, null, n, n2, true, true);
        }
        
        public GLPixelAttributes(final GLProfile glProfile, final PixelFormat pixelFormat, final boolean b) throws GLException {
            this(glProfile, pixelFormat, 0, 0, b, true);
        }
        
        private GLPixelAttributes(final GLProfile glProfile, final PixelFormat pfmt, final int format, final int type, final boolean b, final boolean b2) throws GLException {
            if (b2 && (0 == format || 0 == type)) {
                if (null == pfmt || null == glProfile) {
                    throw new GLException("Zero format and/or type w/o pixFmt or glp: " + this);
                }
                final int[] array = { 0 };
                final int[] array2 = { 0 };
                if (0 == convert(glProfile, pfmt, b, array, array2)) {
                    throw new GLException("Could not find format and type for " + pfmt + " and " + glProfile + ", " + this);
                }
                this.format = array[0];
                this.type = array2[0];
                this.pfmt = pfmt;
            }
            else {
                this.format = format;
                this.type = type;
                this.pfmt = ((null != pfmt) ? pfmt : getPixelFormat(format, type));
                if (null == this.pfmt) {
                    throw new GLException("Could not find PixelFormat for format and/or type: " + this);
                }
            }
            if (b2 && 0 == GLBuffers.bytesPerPixel(this.format, this.type)) {
                throw new GLException("Zero bytesPerPixel: " + this);
            }
        }
        
        @Override
        public String toString() {
            return "PixelAttributes[fmt 0x" + Integer.toHexString(this.format) + ", type 0x" + Integer.toHexString(this.type) + ", " + this.pfmt + "]";
        }
        
        static {
            UNDEF = new GLPixelAttributes(null, PixelFormat.LUMINANCE, 0, 0, true, false);
        }
    }
    
    public interface SingletonGLPixelBufferProvider extends GLPixelBufferProvider
    {
        GLPixelBuffer allocate(final GL p0, final PixelFormat.Composition p1, final GLPixelAttributes p2, final boolean p3, final int p4, final int p5, final int p6, final int p7);
        
        GLPixelBuffer getSingleBuffer(final PixelFormat.Composition p0, final GLPixelAttributes p1, final boolean p2);
        
        GLPixelBuffer initSingleton(final GLProfile p0, final int p1, final boolean p2, final int p3, final int p4, final int p5);
        
        void dispose();
    }
    
    public interface GLPixelBufferProvider
    {
        boolean getAllowRowStride();
        
        GLPixelAttributes getAttributes(final GL p0, final int p1, final boolean p2);
        
        PixelFormat.Composition getHostPixelComp(final GLProfile p0, final int p1);
        
        GLPixelBuffer allocate(final GL p0, final PixelFormat.Composition p1, final GLPixelAttributes p2, final boolean p3, final int p4, final int p5, final int p6, final int p7);
    }
}
