// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.opengl.egl.EGLClientPixmapHI;
import java.nio.LongBuffer;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.egl.EGLExt;

public class EGLExtImpl implements EGLExt
{
    private final EGLContext _context;
    private final EGLExtProcAddressTable _table;
    
    public boolean eglChooseConfig(final long n, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final int n2, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"configs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"num_config\" is not a direct buffer");
        }
        final long addressof_eglChooseConfig = this._table._addressof_eglChooseConfig;
        if (addressof_eglChooseConfig == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglChooseConfig"));
        }
        return this.dispatch_eglChooseConfig0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_eglChooseConfig);
    }
    
    private native boolean dispatch_eglChooseConfig0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final long p8);
    
    public boolean eglCopyBuffers(final long n, final long n2, final long n3) {
        final long addressof_eglCopyBuffers = this._table._addressof_eglCopyBuffers;
        if (addressof_eglCopyBuffers == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCopyBuffers"));
        }
        return this.dispatch_eglCopyBuffers0(n, n2, n3, addressof_eglCopyBuffers);
    }
    
    private native boolean dispatch_eglCopyBuffers0(final long p0, final long p1, final long p2, final long p3);
    
    public long eglCreateContext(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateContext = this._table._addressof_eglCreateContext;
        if (addressof_eglCreateContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateContext"));
        }
        return this.dispatch_eglCreateContext0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateContext);
    }
    
    private native long dispatch_eglCreateContext0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public long eglCreatePbufferSurface(final long n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePbufferSurface = this._table._addressof_eglCreatePbufferSurface;
        if (addressof_eglCreatePbufferSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePbufferSurface"));
        }
        return this.dispatch_eglCreatePbufferSurface0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePbufferSurface);
    }
    
    private native long dispatch_eglCreatePbufferSurface0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    public long eglCreatePixmapSurface(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePixmapSurface = this._table._addressof_eglCreatePixmapSurface;
        if (addressof_eglCreatePixmapSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePixmapSurface"));
        }
        return this.dispatch_eglCreatePixmapSurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePixmapSurface);
    }
    
    private native long dispatch_eglCreatePixmapSurface0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public long eglCreateWindowSurface(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateWindowSurface = this._table._addressof_eglCreateWindowSurface;
        if (addressof_eglCreateWindowSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateWindowSurface"));
        }
        return this.dispatch_eglCreateWindowSurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateWindowSurface);
    }
    
    private native long dispatch_eglCreateWindowSurface0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public boolean eglDestroyContext(final long n, final long n2) {
        final long addressof_eglDestroyContext = this._table._addressof_eglDestroyContext;
        if (addressof_eglDestroyContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyContext"));
        }
        return this.dispatch_eglDestroyContext0(n, n2, addressof_eglDestroyContext);
    }
    
    private native boolean dispatch_eglDestroyContext0(final long p0, final long p1, final long p2);
    
    public boolean eglDestroySurface(final long n, final long n2) {
        final long addressof_eglDestroySurface = this._table._addressof_eglDestroySurface;
        if (addressof_eglDestroySurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySurface"));
        }
        return this.dispatch_eglDestroySurface0(n, n2, addressof_eglDestroySurface);
    }
    
    private native boolean dispatch_eglDestroySurface0(final long p0, final long p1, final long p2);
    
    public boolean eglGetConfigAttrib(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetConfigAttrib = this._table._addressof_eglGetConfigAttrib;
        if (addressof_eglGetConfigAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetConfigAttrib"));
        }
        return this.dispatch_eglGetConfigAttrib0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetConfigAttrib);
    }
    
    private native boolean dispatch_eglGetConfigAttrib0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public boolean eglGetConfigs(final long n, final PointerBuffer pointerBuffer, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"configs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"num_config\" is not a direct buffer");
        }
        final long addressof_eglGetConfigs = this._table._addressof_eglGetConfigs;
        if (addressof_eglGetConfigs == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetConfigs"));
        }
        return this.dispatch_eglGetConfigs0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetConfigs);
    }
    
    private native boolean dispatch_eglGetConfigs0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    public long eglGetCurrentDisplay() {
        final long addressof_eglGetCurrentDisplay = this._table._addressof_eglGetCurrentDisplay;
        if (addressof_eglGetCurrentDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentDisplay"));
        }
        return this.dispatch_eglGetCurrentDisplay0(addressof_eglGetCurrentDisplay);
    }
    
    private native long dispatch_eglGetCurrentDisplay0(final long p0);
    
    public long eglGetCurrentSurface(final int n) {
        final long addressof_eglGetCurrentSurface = this._table._addressof_eglGetCurrentSurface;
        if (addressof_eglGetCurrentSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentSurface"));
        }
        return this.dispatch_eglGetCurrentSurface0(n, addressof_eglGetCurrentSurface);
    }
    
    private native long dispatch_eglGetCurrentSurface0(final int p0, final long p1);
    
    public long eglGetDisplay(final long n) {
        final long addressof_eglGetDisplay = this._table._addressof_eglGetDisplay;
        if (addressof_eglGetDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetDisplay"));
        }
        return this.dispatch_eglGetDisplay0(n, addressof_eglGetDisplay);
    }
    
    private native long dispatch_eglGetDisplay0(final long p0, final long p1);
    
    public int eglGetError() {
        final long addressof_eglGetError = this._table._addressof_eglGetError;
        if (addressof_eglGetError == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetError"));
        }
        return this.dispatch_eglGetError0(addressof_eglGetError);
    }
    
    private native int dispatch_eglGetError0(final long p0);
    
    public boolean eglInitialize(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"major\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"minor\" is not a direct buffer");
        }
        final long addressof_eglInitialize = this._table._addressof_eglInitialize;
        if (addressof_eglInitialize == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglInitialize"));
        }
        return this.dispatch_eglInitialize0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_eglInitialize);
    }
    
    private native boolean dispatch_eglInitialize0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public boolean eglMakeCurrent(final long n, final long n2, final long n3, final long n4) {
        final long addressof_eglMakeCurrent = this._table._addressof_eglMakeCurrent;
        if (addressof_eglMakeCurrent == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglMakeCurrent"));
        }
        return this.dispatch_eglMakeCurrent0(n, n2, n3, n4, addressof_eglMakeCurrent);
    }
    
    private native boolean dispatch_eglMakeCurrent0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    public boolean eglQueryContext(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryContext = this._table._addressof_eglQueryContext;
        if (addressof_eglQueryContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryContext"));
        }
        return this.dispatch_eglQueryContext0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQueryContext);
    }
    
    private native boolean dispatch_eglQueryContext0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public String eglQueryString(final long n, final int n2) {
        final long addressof_eglQueryString = this._table._addressof_eglQueryString;
        if (addressof_eglQueryString == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryString"));
        }
        return this.dispatch_eglQueryString0(n, n2, addressof_eglQueryString);
    }
    
    private native String dispatch_eglQueryString0(final long p0, final int p1, final long p2);
    
    public boolean eglQuerySurface(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQuerySurface = this._table._addressof_eglQuerySurface;
        if (addressof_eglQuerySurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQuerySurface"));
        }
        return this.dispatch_eglQuerySurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQuerySurface);
    }
    
    private native boolean dispatch_eglQuerySurface0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public boolean eglSwapBuffers(final long n, final long n2) {
        final long addressof_eglSwapBuffers = this._table._addressof_eglSwapBuffers;
        if (addressof_eglSwapBuffers == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffers"));
        }
        return this.dispatch_eglSwapBuffers0(n, n2, addressof_eglSwapBuffers);
    }
    
    private native boolean dispatch_eglSwapBuffers0(final long p0, final long p1, final long p2);
    
    public boolean eglTerminate(final long n) {
        final long addressof_eglTerminate = this._table._addressof_eglTerminate;
        if (addressof_eglTerminate == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglTerminate"));
        }
        return this.dispatch_eglTerminate0(n, addressof_eglTerminate);
    }
    
    private native boolean dispatch_eglTerminate0(final long p0, final long p1);
    
    public boolean eglWaitGL() {
        final long addressof_eglWaitGL = this._table._addressof_eglWaitGL;
        if (addressof_eglWaitGL == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitGL"));
        }
        return this.dispatch_eglWaitGL0(addressof_eglWaitGL);
    }
    
    private native boolean dispatch_eglWaitGL0(final long p0);
    
    public boolean eglWaitNative(final int n) {
        final long addressof_eglWaitNative = this._table._addressof_eglWaitNative;
        if (addressof_eglWaitNative == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitNative"));
        }
        return this.dispatch_eglWaitNative0(n, addressof_eglWaitNative);
    }
    
    private native boolean dispatch_eglWaitNative0(final int p0, final long p1);
    
    public boolean eglBindTexImage(final long n, final long n2, final int n3) {
        final long addressof_eglBindTexImage = this._table._addressof_eglBindTexImage;
        if (addressof_eglBindTexImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglBindTexImage"));
        }
        return this.dispatch_eglBindTexImage0(n, n2, n3, addressof_eglBindTexImage);
    }
    
    private native boolean dispatch_eglBindTexImage0(final long p0, final long p1, final int p2, final long p3);
    
    public boolean eglReleaseTexImage(final long n, final long n2, final int n3) {
        final long addressof_eglReleaseTexImage = this._table._addressof_eglReleaseTexImage;
        if (addressof_eglReleaseTexImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglReleaseTexImage"));
        }
        return this.dispatch_eglReleaseTexImage0(n, n2, n3, addressof_eglReleaseTexImage);
    }
    
    private native boolean dispatch_eglReleaseTexImage0(final long p0, final long p1, final int p2, final long p3);
    
    public boolean eglSurfaceAttrib(final long n, final long n2, final int n3, final int n4) {
        final long addressof_eglSurfaceAttrib = this._table._addressof_eglSurfaceAttrib;
        if (addressof_eglSurfaceAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSurfaceAttrib"));
        }
        return this.dispatch_eglSurfaceAttrib0(n, n2, n3, n4, addressof_eglSurfaceAttrib);
    }
    
    private native boolean dispatch_eglSurfaceAttrib0(final long p0, final long p1, final int p2, final int p3, final long p4);
    
    public boolean eglSwapInterval(final long n, final int n2) {
        final long addressof_eglSwapInterval = this._table._addressof_eglSwapInterval;
        if (addressof_eglSwapInterval == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapInterval"));
        }
        return this.dispatch_eglSwapInterval0(n, n2, addressof_eglSwapInterval);
    }
    
    private native boolean dispatch_eglSwapInterval0(final long p0, final int p1, final long p2);
    
    public boolean eglBindAPI(final int n) {
        final long addressof_eglBindAPI = this._table._addressof_eglBindAPI;
        if (addressof_eglBindAPI == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglBindAPI"));
        }
        return this.dispatch_eglBindAPI0(n, addressof_eglBindAPI);
    }
    
    private native boolean dispatch_eglBindAPI0(final int p0, final long p1);
    
    public int eglQueryAPI() {
        final long addressof_eglQueryAPI = this._table._addressof_eglQueryAPI;
        if (addressof_eglQueryAPI == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryAPI"));
        }
        return this.dispatch_eglQueryAPI0(addressof_eglQueryAPI);
    }
    
    private native int dispatch_eglQueryAPI0(final long p0);
    
    public long eglCreatePbufferFromClientBuffer(final long n, final int n2, final Buffer buffer, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"buffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePbufferFromClientBuffer = this._table._addressof_eglCreatePbufferFromClientBuffer;
        if (addressof_eglCreatePbufferFromClientBuffer == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePbufferFromClientBuffer"));
        }
        return this.dispatch_eglCreatePbufferFromClientBuffer0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePbufferFromClientBuffer);
    }
    
    private native long dispatch_eglCreatePbufferFromClientBuffer0(final long p0, final int p1, final Object p2, final int p3, final long p4, final Object p5, final int p6, final long p7);
    
    public boolean eglReleaseThread() {
        final long addressof_eglReleaseThread = this._table._addressof_eglReleaseThread;
        if (addressof_eglReleaseThread == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglReleaseThread"));
        }
        return this.dispatch_eglReleaseThread0(addressof_eglReleaseThread);
    }
    
    private native boolean dispatch_eglReleaseThread0(final long p0);
    
    public boolean eglWaitClient() {
        final long addressof_eglWaitClient = this._table._addressof_eglWaitClient;
        if (addressof_eglWaitClient == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitClient"));
        }
        return this.dispatch_eglWaitClient0(addressof_eglWaitClient);
    }
    
    private native boolean dispatch_eglWaitClient0(final long p0);
    
    public long eglGetCurrentContext() {
        final long addressof_eglGetCurrentContext = this._table._addressof_eglGetCurrentContext;
        if (addressof_eglGetCurrentContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentContext"));
        }
        return this.dispatch_eglGetCurrentContext0(addressof_eglGetCurrentContext);
    }
    
    private native long dispatch_eglGetCurrentContext0(final long p0);
    
    public ByteBuffer eglCreateSync(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateSync = this._table._addressof_eglCreateSync;
        if (addressof_eglCreateSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateSync"));
        }
        final ByteBuffer dispatch_eglCreateSync0 = this.dispatch_eglCreateSync0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreateSync);
        if (dispatch_eglCreateSync0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateSync0);
        return dispatch_eglCreateSync0;
    }
    
    private native ByteBuffer dispatch_eglCreateSync0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    public boolean eglDestroySync(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglDestroySync = this._table._addressof_eglDestroySync;
        if (addressof_eglDestroySync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySync"));
        }
        return this.dispatch_eglDestroySync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroySync);
    }
    
    private native boolean dispatch_eglDestroySync0(final long p0, final Object p1, final int p2, final long p3);
    
    public int eglClientWaitSync(final long n, final Buffer buffer, final int n2, final long n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglClientWaitSync = this._table._addressof_eglClientWaitSync;
        if (addressof_eglClientWaitSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglClientWaitSync"));
        }
        return this.dispatch_eglClientWaitSync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, addressof_eglClientWaitSync);
    }
    
    private native int dispatch_eglClientWaitSync0(final long p0, final Object p1, final int p2, final int p3, final long p4, final long p5);
    
    public boolean eglGetSyncAttrib(final long n, final Buffer buffer, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetSyncAttrib = this._table._addressof_eglGetSyncAttrib;
        if (addressof_eglGetSyncAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSyncAttrib"));
        }
        return this.dispatch_eglGetSyncAttrib0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglGetSyncAttrib);
    }
    
    private native boolean dispatch_eglGetSyncAttrib0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    public ByteBuffer eglCreateImage(final long n, final long n2, final int n3, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"buffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateImage = this._table._addressof_eglCreateImage;
        if (addressof_eglCreateImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateImage"));
        }
        final ByteBuffer dispatch_eglCreateImage0 = this.dispatch_eglCreateImage0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreateImage);
        if (dispatch_eglCreateImage0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateImage0);
        return dispatch_eglCreateImage0;
    }
    
    private native ByteBuffer dispatch_eglCreateImage0(final long p0, final long p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    public boolean eglDestroyImage(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"image\" is not a direct buffer");
        }
        final long addressof_eglDestroyImage = this._table._addressof_eglDestroyImage;
        if (addressof_eglDestroyImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyImage"));
        }
        return this.dispatch_eglDestroyImage0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroyImage);
    }
    
    private native boolean dispatch_eglDestroyImage0(final long p0, final Object p1, final int p2, final long p3);
    
    public long eglGetPlatformDisplay(final int n, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_display\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglGetPlatformDisplay = this._table._addressof_eglGetPlatformDisplay;
        if (addressof_eglGetPlatformDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetPlatformDisplay"));
        }
        return this.dispatch_eglGetPlatformDisplay0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglGetPlatformDisplay);
    }
    
    private native long dispatch_eglGetPlatformDisplay0(final int p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public long eglCreatePlatformWindowSurface(final long n, final long n2, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_window\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformWindowSurface = this._table._addressof_eglCreatePlatformWindowSurface;
        if (addressof_eglCreatePlatformWindowSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformWindowSurface"));
        }
        return this.dispatch_eglCreatePlatformWindowSurface0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreatePlatformWindowSurface);
    }
    
    private native long dispatch_eglCreatePlatformWindowSurface0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    public long eglCreatePlatformPixmapSurface(final long n, final long n2, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_pixmap\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformPixmapSurface = this._table._addressof_eglCreatePlatformPixmapSurface;
        if (addressof_eglCreatePlatformPixmapSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformPixmapSurface"));
        }
        return this.dispatch_eglCreatePlatformPixmapSurface0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreatePlatformPixmapSurface);
    }
    
    private native long dispatch_eglCreatePlatformPixmapSurface0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    public boolean eglWaitSync(final long n, final Buffer buffer, final int n2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglWaitSync = this._table._addressof_eglWaitSync;
        if (addressof_eglWaitSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitSync"));
        }
        return this.dispatch_eglWaitSync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, addressof_eglWaitSync);
    }
    
    private native boolean dispatch_eglWaitSync0(final long p0, final Object p1, final int p2, final int p3, final long p4);
    
    @Override
    public long eglCreateSync64KHR(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateSync64KHR = this._table._addressof_eglCreateSync64KHR;
        if (addressof_eglCreateSync64KHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateSync64KHR"));
        }
        return this.dispatch_eglCreateSync64KHR0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreateSync64KHR);
    }
    
    private native long dispatch_eglCreateSync64KHR0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglQueryDebugKHR(final int n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryDebugKHR = this._table._addressof_eglQueryDebugKHR;
        if (addressof_eglQueryDebugKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryDebugKHR"));
        }
        return this.dispatch_eglQueryDebugKHR0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryDebugKHR);
    }
    
    private native boolean dispatch_eglQueryDebugKHR0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public int eglLabelObjectKHR(final long n, final int n2, final Buffer buffer, final Buffer buffer2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"object\" is not a direct buffer");
        }
        if (!Buffers.isDirect(buffer2)) {
            throw new RuntimeException("Argument \"label\" is not a direct buffer");
        }
        final long addressof_eglLabelObjectKHR = this._table._addressof_eglLabelObjectKHR;
        if (addressof_eglLabelObjectKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglLabelObjectKHR"));
        }
        return this.dispatch_eglLabelObjectKHR0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), buffer2, Buffers.getDirectBufferByteOffset(buffer2), addressof_eglLabelObjectKHR);
    }
    
    private native int dispatch_eglLabelObjectKHR0(final long p0, final int p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long eglCreateSyncKHR(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateSyncKHR = this._table._addressof_eglCreateSyncKHR;
        if (addressof_eglCreateSyncKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateSyncKHR"));
        }
        return this.dispatch_eglCreateSyncKHR0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateSyncKHR);
    }
    
    private native long dispatch_eglCreateSyncKHR0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglDestroySyncKHR(final long n, final long n2) {
        final long addressof_eglDestroySyncKHR = this._table._addressof_eglDestroySyncKHR;
        if (addressof_eglDestroySyncKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySyncKHR"));
        }
        return this.dispatch_eglDestroySyncKHR0(n, n2, addressof_eglDestroySyncKHR);
    }
    
    private native boolean dispatch_eglDestroySyncKHR0(final long p0, final long p1, final long p2);
    
    @Override
    public int eglClientWaitSyncKHR(final long n, final long n2, final int n3, final long n4) {
        final long addressof_eglClientWaitSyncKHR = this._table._addressof_eglClientWaitSyncKHR;
        if (addressof_eglClientWaitSyncKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglClientWaitSyncKHR"));
        }
        return this.dispatch_eglClientWaitSyncKHR0(n, n2, n3, n4, addressof_eglClientWaitSyncKHR);
    }
    
    private native int dispatch_eglClientWaitSyncKHR0(final long p0, final long p1, final int p2, final long p3, final long p4);
    
    @Override
    public boolean eglGetSyncAttribKHR(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetSyncAttribKHR = this._table._addressof_eglGetSyncAttribKHR;
        if (addressof_eglGetSyncAttribKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSyncAttribKHR"));
        }
        return this.dispatch_eglGetSyncAttribKHR0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetSyncAttribKHR);
    }
    
    private native boolean dispatch_eglGetSyncAttribKHR0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public long eglCreateImageKHR(final long n, final long n2, final int n3, final Buffer buffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"buffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateImageKHR = this._table._addressof_eglCreateImageKHR;
        if (addressof_eglCreateImageKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateImageKHR"));
        }
        return this.dispatch_eglCreateImageKHR0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateImageKHR);
    }
    
    private native long dispatch_eglCreateImageKHR0(final long p0, final long p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    @Override
    public boolean eglDestroyImageKHR(final long n, final long n2) {
        final long addressof_eglDestroyImageKHR = this._table._addressof_eglDestroyImageKHR;
        if (addressof_eglDestroyImageKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyImageKHR"));
        }
        return this.dispatch_eglDestroyImageKHR0(n, n2, addressof_eglDestroyImageKHR);
    }
    
    private native boolean dispatch_eglDestroyImageKHR0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean eglLockSurfaceKHR(final long n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglLockSurfaceKHR = this._table._addressof_eglLockSurfaceKHR;
        if (addressof_eglLockSurfaceKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglLockSurfaceKHR"));
        }
        return this.dispatch_eglLockSurfaceKHR0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglLockSurfaceKHR);
    }
    
    private native boolean dispatch_eglLockSurfaceKHR0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglUnlockSurfaceKHR(final long n, final long n2) {
        final long addressof_eglUnlockSurfaceKHR = this._table._addressof_eglUnlockSurfaceKHR;
        if (addressof_eglUnlockSurfaceKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglUnlockSurfaceKHR"));
        }
        return this.dispatch_eglUnlockSurfaceKHR0(n, n2, addressof_eglUnlockSurfaceKHR);
    }
    
    private native boolean dispatch_eglUnlockSurfaceKHR0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean eglQuerySurface64KHR(final long n, final long n2, final int n3, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQuerySurface64KHR = this._table._addressof_eglQuerySurface64KHR;
        if (addressof_eglQuerySurface64KHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQuerySurface64KHR"));
        }
        return this.dispatch_eglQuerySurface64KHR0(n, n2, n3, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQuerySurface64KHR);
    }
    
    private native boolean dispatch_eglQuerySurface64KHR0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglSetDamageRegionKHR(final long n, final long n2, final IntBuffer intBuffer, final int n3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"rects\" is not a direct buffer");
        }
        final long addressof_eglSetDamageRegionKHR = this._table._addressof_eglSetDamageRegionKHR;
        if (addressof_eglSetDamageRegionKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSetDamageRegionKHR"));
        }
        return this.dispatch_eglSetDamageRegionKHR0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n3, addressof_eglSetDamageRegionKHR);
    }
    
    private native boolean dispatch_eglSetDamageRegionKHR0(final long p0, final long p1, final Object p2, final int p3, final int p4, final long p5);
    
    @Override
    public boolean eglSignalSyncKHR(final long n, final long n2, final int n3) {
        final long addressof_eglSignalSyncKHR = this._table._addressof_eglSignalSyncKHR;
        if (addressof_eglSignalSyncKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSignalSyncKHR"));
        }
        return this.dispatch_eglSignalSyncKHR0(n, n2, n3, addressof_eglSignalSyncKHR);
    }
    
    private native boolean dispatch_eglSignalSyncKHR0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public ByteBuffer eglCreateStreamKHR(final long n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateStreamKHR = this._table._addressof_eglCreateStreamKHR;
        if (addressof_eglCreateStreamKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateStreamKHR"));
        }
        final ByteBuffer dispatch_eglCreateStreamKHR0 = this.dispatch_eglCreateStreamKHR0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateStreamKHR);
        if (dispatch_eglCreateStreamKHR0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateStreamKHR0);
        return dispatch_eglCreateStreamKHR0;
    }
    
    private native ByteBuffer dispatch_eglCreateStreamKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglDestroyStreamKHR(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglDestroyStreamKHR = this._table._addressof_eglDestroyStreamKHR;
        if (addressof_eglDestroyStreamKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyStreamKHR"));
        }
        return this.dispatch_eglDestroyStreamKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroyStreamKHR);
    }
    
    private native boolean dispatch_eglDestroyStreamKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglStreamAttribKHR(final long n, final Buffer buffer, final int n2, final int n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglStreamAttribKHR = this._table._addressof_eglStreamAttribKHR;
        if (addressof_eglStreamAttribKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglStreamAttribKHR"));
        }
        return this.dispatch_eglStreamAttribKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, addressof_eglStreamAttribKHR);
    }
    
    private native boolean dispatch_eglStreamAttribKHR0(final long p0, final Object p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public boolean eglQueryStreamKHR(final long n, final Buffer buffer, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryStreamKHR = this._table._addressof_eglQueryStreamKHR;
        if (addressof_eglQueryStreamKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryStreamKHR"));
        }
        return this.dispatch_eglQueryStreamKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQueryStreamKHR);
    }
    
    private native boolean dispatch_eglQueryStreamKHR0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean eglQueryStreamu64KHR(final long n, final Buffer buffer, final int n2, final LongBuffer longBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryStreamu64KHR = this._table._addressof_eglQueryStreamu64KHR;
        if (addressof_eglQueryStreamu64KHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryStreamu64KHR"));
        }
        return this.dispatch_eglQueryStreamu64KHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), addressof_eglQueryStreamu64KHR);
    }
    
    private native boolean dispatch_eglQueryStreamu64KHR0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean eglStreamConsumerGLTextureExternalKHR(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglStreamConsumerGLTextureExternalKHR = this._table._addressof_eglStreamConsumerGLTextureExternalKHR;
        if (addressof_eglStreamConsumerGLTextureExternalKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglStreamConsumerGLTextureExternalKHR"));
        }
        return this.dispatch_eglStreamConsumerGLTextureExternalKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglStreamConsumerGLTextureExternalKHR);
    }
    
    private native boolean dispatch_eglStreamConsumerGLTextureExternalKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglStreamConsumerAcquireKHR(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglStreamConsumerAcquireKHR = this._table._addressof_eglStreamConsumerAcquireKHR;
        if (addressof_eglStreamConsumerAcquireKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglStreamConsumerAcquireKHR"));
        }
        return this.dispatch_eglStreamConsumerAcquireKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglStreamConsumerAcquireKHR);
    }
    
    private native boolean dispatch_eglStreamConsumerAcquireKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglStreamConsumerReleaseKHR(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglStreamConsumerReleaseKHR = this._table._addressof_eglStreamConsumerReleaseKHR;
        if (addressof_eglStreamConsumerReleaseKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglStreamConsumerReleaseKHR"));
        }
        return this.dispatch_eglStreamConsumerReleaseKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglStreamConsumerReleaseKHR);
    }
    
    private native boolean dispatch_eglStreamConsumerReleaseKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public int eglGetStreamFileDescriptorKHR(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        final long addressof_eglGetStreamFileDescriptorKHR = this._table._addressof_eglGetStreamFileDescriptorKHR;
        if (addressof_eglGetStreamFileDescriptorKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetStreamFileDescriptorKHR"));
        }
        return this.dispatch_eglGetStreamFileDescriptorKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglGetStreamFileDescriptorKHR);
    }
    
    private native int dispatch_eglGetStreamFileDescriptorKHR0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public ByteBuffer eglCreateStreamFromFileDescriptorKHR(final long n, final int n2) {
        final long addressof_eglCreateStreamFromFileDescriptorKHR = this._table._addressof_eglCreateStreamFromFileDescriptorKHR;
        if (addressof_eglCreateStreamFromFileDescriptorKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateStreamFromFileDescriptorKHR"));
        }
        final ByteBuffer dispatch_eglCreateStreamFromFileDescriptorKHR0 = this.dispatch_eglCreateStreamFromFileDescriptorKHR0(n, n2, addressof_eglCreateStreamFromFileDescriptorKHR);
        if (dispatch_eglCreateStreamFromFileDescriptorKHR0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateStreamFromFileDescriptorKHR0);
        return dispatch_eglCreateStreamFromFileDescriptorKHR0;
    }
    
    private native ByteBuffer dispatch_eglCreateStreamFromFileDescriptorKHR0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean eglQueryStreamTimeKHR(final long n, final Buffer buffer, final int n2, final LongBuffer longBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryStreamTimeKHR = this._table._addressof_eglQueryStreamTimeKHR;
        if (addressof_eglQueryStreamTimeKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryStreamTimeKHR"));
        }
        return this.dispatch_eglQueryStreamTimeKHR0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), addressof_eglQueryStreamTimeKHR);
    }
    
    private native boolean dispatch_eglQueryStreamTimeKHR0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long eglCreateStreamProducerSurfaceKHR(final long n, final long n2, final Buffer buffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateStreamProducerSurfaceKHR = this._table._addressof_eglCreateStreamProducerSurfaceKHR;
        if (addressof_eglCreateStreamProducerSurfaceKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateStreamProducerSurfaceKHR"));
        }
        return this.dispatch_eglCreateStreamProducerSurfaceKHR0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateStreamProducerSurfaceKHR);
    }
    
    private native long dispatch_eglCreateStreamProducerSurfaceKHR0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean eglSwapBuffersWithDamageKHR(final long n, final long n2, final IntBuffer intBuffer, final int n3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"rects\" is not a direct buffer");
        }
        final long addressof_eglSwapBuffersWithDamageKHR = this._table._addressof_eglSwapBuffersWithDamageKHR;
        if (addressof_eglSwapBuffersWithDamageKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffersWithDamageKHR"));
        }
        return this.dispatch_eglSwapBuffersWithDamageKHR0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n3, addressof_eglSwapBuffersWithDamageKHR);
    }
    
    private native boolean dispatch_eglSwapBuffersWithDamageKHR0(final long p0, final long p1, final Object p2, final int p3, final int p4, final long p5);
    
    @Override
    public int eglWaitSyncKHR(final long n, final long n2, final int n3) {
        final long addressof_eglWaitSyncKHR = this._table._addressof_eglWaitSyncKHR;
        if (addressof_eglWaitSyncKHR == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitSyncKHR"));
        }
        return this.dispatch_eglWaitSyncKHR0(n, n2, n3, addressof_eglWaitSyncKHR);
    }
    
    private native int dispatch_eglWaitSyncKHR0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public int eglDupNativeFenceFDANDROID(final long n, final long n2) {
        final long addressof_eglDupNativeFenceFDANDROID = this._table._addressof_eglDupNativeFenceFDANDROID;
        if (addressof_eglDupNativeFenceFDANDROID == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDupNativeFenceFDANDROID"));
        }
        return this.dispatch_eglDupNativeFenceFDANDROID0(n, n2, addressof_eglDupNativeFenceFDANDROID);
    }
    
    private native int dispatch_eglDupNativeFenceFDANDROID0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean eglQuerySurfacePointerANGLE(final long n, final long n2, final int n3, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQuerySurfacePointerANGLE = this._table._addressof_eglQuerySurfacePointerANGLE;
        if (addressof_eglQuerySurfacePointerANGLE == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQuerySurfacePointerANGLE"));
        }
        return this.dispatch_eglQuerySurfacePointerANGLE0(n, n2, n3, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQuerySurfacePointerANGLE);
    }
    
    private native boolean dispatch_eglQuerySurfacePointerANGLE0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglQueryDeviceAttribEXT(final Buffer buffer, final int n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"device\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryDeviceAttribEXT = this._table._addressof_eglQueryDeviceAttribEXT;
        if (addressof_eglQueryDeviceAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryDeviceAttribEXT"));
        }
        return this.dispatch_eglQueryDeviceAttribEXT0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryDeviceAttribEXT);
    }
    
    private native boolean dispatch_eglQueryDeviceAttribEXT0(final Object p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public ByteBuffer eglQueryDeviceStringEXT(final Buffer buffer, final int n) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"device\" is not a direct buffer");
        }
        final long addressof_eglQueryDeviceStringEXT = this._table._addressof_eglQueryDeviceStringEXT;
        if (addressof_eglQueryDeviceStringEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryDeviceStringEXT"));
        }
        final ByteBuffer dispatch_eglQueryDeviceStringEXT0 = this.dispatch_eglQueryDeviceStringEXT0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, addressof_eglQueryDeviceStringEXT);
        if (dispatch_eglQueryDeviceStringEXT0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglQueryDeviceStringEXT0);
        return dispatch_eglQueryDeviceStringEXT0;
    }
    
    private native ByteBuffer dispatch_eglQueryDeviceStringEXT0(final Object p0, final int p1, final int p2, final long p3);
    
    @Override
    public boolean eglQueryDevicesEXT(final int n, final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"devices\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"num_devices\" is not a direct buffer");
        }
        final long addressof_eglQueryDevicesEXT = this._table._addressof_eglQueryDevicesEXT;
        if (addressof_eglQueryDevicesEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryDevicesEXT"));
        }
        return this.dispatch_eglQueryDevicesEXT0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQueryDevicesEXT);
    }
    
    private native boolean dispatch_eglQueryDevicesEXT0(final int p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglQueryDisplayAttribEXT(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryDisplayAttribEXT = this._table._addressof_eglQueryDisplayAttribEXT;
        if (addressof_eglQueryDisplayAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryDisplayAttribEXT"));
        }
        return this.dispatch_eglQueryDisplayAttribEXT0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryDisplayAttribEXT);
    }
    
    private native boolean dispatch_eglQueryDisplayAttribEXT0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglGetOutputLayersEXT(final long n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer2)) {
            throw new RuntimeException("Argument \"layers\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"num_layers\" is not a direct buffer");
        }
        final long addressof_eglGetOutputLayersEXT = this._table._addressof_eglGetOutputLayersEXT;
        if (addressof_eglGetOutputLayersEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetOutputLayersEXT"));
        }
        return this.dispatch_eglGetOutputLayersEXT0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), (pointerBuffer2 != null) ? pointerBuffer2.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer2), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetOutputLayersEXT);
    }
    
    private native boolean dispatch_eglGetOutputLayersEXT0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean eglGetOutputPortsEXT(final long n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer2)) {
            throw new RuntimeException("Argument \"ports\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"num_ports\" is not a direct buffer");
        }
        final long addressof_eglGetOutputPortsEXT = this._table._addressof_eglGetOutputPortsEXT;
        if (addressof_eglGetOutputPortsEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetOutputPortsEXT"));
        }
        return this.dispatch_eglGetOutputPortsEXT0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), (pointerBuffer2 != null) ? pointerBuffer2.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer2), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetOutputPortsEXT);
    }
    
    private native boolean dispatch_eglGetOutputPortsEXT0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean eglOutputLayerAttribEXT(final long n, final Buffer buffer, final int n2, final long n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"layer\" is not a direct buffer");
        }
        final long addressof_eglOutputLayerAttribEXT = this._table._addressof_eglOutputLayerAttribEXT;
        if (addressof_eglOutputLayerAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglOutputLayerAttribEXT"));
        }
        return this.dispatch_eglOutputLayerAttribEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, addressof_eglOutputLayerAttribEXT);
    }
    
    private native boolean dispatch_eglOutputLayerAttribEXT0(final long p0, final Object p1, final int p2, final int p3, final long p4, final long p5);
    
    @Override
    public boolean eglQueryOutputLayerAttribEXT(final long n, final Buffer buffer, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"layer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryOutputLayerAttribEXT = this._table._addressof_eglQueryOutputLayerAttribEXT;
        if (addressof_eglQueryOutputLayerAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryOutputLayerAttribEXT"));
        }
        return this.dispatch_eglQueryOutputLayerAttribEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryOutputLayerAttribEXT);
    }
    
    private native boolean dispatch_eglQueryOutputLayerAttribEXT0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public ByteBuffer eglQueryOutputLayerStringEXT(final long n, final Buffer buffer, final int n2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"layer\" is not a direct buffer");
        }
        final long addressof_eglQueryOutputLayerStringEXT = this._table._addressof_eglQueryOutputLayerStringEXT;
        if (addressof_eglQueryOutputLayerStringEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryOutputLayerStringEXT"));
        }
        final ByteBuffer dispatch_eglQueryOutputLayerStringEXT0 = this.dispatch_eglQueryOutputLayerStringEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, addressof_eglQueryOutputLayerStringEXT);
        if (dispatch_eglQueryOutputLayerStringEXT0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglQueryOutputLayerStringEXT0);
        return dispatch_eglQueryOutputLayerStringEXT0;
    }
    
    private native ByteBuffer dispatch_eglQueryOutputLayerStringEXT0(final long p0, final Object p1, final int p2, final int p3, final long p4);
    
    @Override
    public boolean eglOutputPortAttribEXT(final long n, final Buffer buffer, final int n2, final long n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"port\" is not a direct buffer");
        }
        final long addressof_eglOutputPortAttribEXT = this._table._addressof_eglOutputPortAttribEXT;
        if (addressof_eglOutputPortAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglOutputPortAttribEXT"));
        }
        return this.dispatch_eglOutputPortAttribEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, addressof_eglOutputPortAttribEXT);
    }
    
    private native boolean dispatch_eglOutputPortAttribEXT0(final long p0, final Object p1, final int p2, final int p3, final long p4, final long p5);
    
    @Override
    public boolean eglQueryOutputPortAttribEXT(final long n, final Buffer buffer, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"port\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryOutputPortAttribEXT = this._table._addressof_eglQueryOutputPortAttribEXT;
        if (addressof_eglQueryOutputPortAttribEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryOutputPortAttribEXT"));
        }
        return this.dispatch_eglQueryOutputPortAttribEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryOutputPortAttribEXT);
    }
    
    private native boolean dispatch_eglQueryOutputPortAttribEXT0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public ByteBuffer eglQueryOutputPortStringEXT(final long n, final Buffer buffer, final int n2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"port\" is not a direct buffer");
        }
        final long addressof_eglQueryOutputPortStringEXT = this._table._addressof_eglQueryOutputPortStringEXT;
        if (addressof_eglQueryOutputPortStringEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryOutputPortStringEXT"));
        }
        final ByteBuffer dispatch_eglQueryOutputPortStringEXT0 = this.dispatch_eglQueryOutputPortStringEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, addressof_eglQueryOutputPortStringEXT);
        if (dispatch_eglQueryOutputPortStringEXT0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglQueryOutputPortStringEXT0);
        return dispatch_eglQueryOutputPortStringEXT0;
    }
    
    private native ByteBuffer dispatch_eglQueryOutputPortStringEXT0(final long p0, final Object p1, final int p2, final int p3, final long p4);
    
    @Override
    public long eglGetPlatformDisplayEXT(final int n, final Buffer buffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_display\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglGetPlatformDisplayEXT = this._table._addressof_eglGetPlatformDisplayEXT;
        if (addressof_eglGetPlatformDisplayEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetPlatformDisplayEXT"));
        }
        return this.dispatch_eglGetPlatformDisplayEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetPlatformDisplayEXT);
    }
    
    private native long dispatch_eglGetPlatformDisplayEXT0(final int p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public long eglCreatePlatformWindowSurfaceEXT(final long n, final long n2, final Buffer buffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_window\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformWindowSurfaceEXT = this._table._addressof_eglCreatePlatformWindowSurfaceEXT;
        if (addressof_eglCreatePlatformWindowSurfaceEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformWindowSurfaceEXT"));
        }
        return this.dispatch_eglCreatePlatformWindowSurfaceEXT0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePlatformWindowSurfaceEXT);
    }
    
    private native long dispatch_eglCreatePlatformWindowSurfaceEXT0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long eglCreatePlatformPixmapSurfaceEXT(final long n, final long n2, final Buffer buffer, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_pixmap\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformPixmapSurfaceEXT = this._table._addressof_eglCreatePlatformPixmapSurfaceEXT;
        if (addressof_eglCreatePlatformPixmapSurfaceEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformPixmapSurfaceEXT"));
        }
        return this.dispatch_eglCreatePlatformPixmapSurfaceEXT0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePlatformPixmapSurfaceEXT);
    }
    
    private native long dispatch_eglCreatePlatformPixmapSurfaceEXT0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean eglStreamConsumerOutputEXT(final long n, final Buffer buffer, final Buffer buffer2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(buffer2)) {
            throw new RuntimeException("Argument \"layer\" is not a direct buffer");
        }
        final long addressof_eglStreamConsumerOutputEXT = this._table._addressof_eglStreamConsumerOutputEXT;
        if (addressof_eglStreamConsumerOutputEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglStreamConsumerOutputEXT"));
        }
        return this.dispatch_eglStreamConsumerOutputEXT0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), buffer2, Buffers.getDirectBufferByteOffset(buffer2), addressof_eglStreamConsumerOutputEXT);
    }
    
    private native boolean dispatch_eglStreamConsumerOutputEXT0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglSwapBuffersWithDamageEXT(final long n, final long n2, final IntBuffer intBuffer, final int n3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"rects\" is not a direct buffer");
        }
        final long addressof_eglSwapBuffersWithDamageEXT = this._table._addressof_eglSwapBuffersWithDamageEXT;
        if (addressof_eglSwapBuffersWithDamageEXT == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffersWithDamageEXT"));
        }
        return this.dispatch_eglSwapBuffersWithDamageEXT0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n3, addressof_eglSwapBuffersWithDamageEXT);
    }
    
    private native boolean dispatch_eglSwapBuffersWithDamageEXT0(final long p0, final long p1, final Object p2, final int p3, final int p4, final long p5);
    
    @Override
    public long eglCreatePixmapSurfaceHI(final long n, final long n2, final EGLClientPixmapHI eglClientPixmapHI) {
        final long addressof_eglCreatePixmapSurfaceHI = this._table._addressof_eglCreatePixmapSurfaceHI;
        if (addressof_eglCreatePixmapSurfaceHI == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePixmapSurfaceHI"));
        }
        return this.dispatch_eglCreatePixmapSurfaceHI0(n, n2, (eglClientPixmapHI == null) ? null : eglClientPixmapHI.getBuffer(), addressof_eglCreatePixmapSurfaceHI);
    }
    
    private native long dispatch_eglCreatePixmapSurfaceHI0(final long p0, final long p1, final ByteBuffer p2, final long p3);
    
    @Override
    public long eglCreateDRMImageMESA(final long n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateDRMImageMESA = this._table._addressof_eglCreateDRMImageMESA;
        if (addressof_eglCreateDRMImageMESA == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateDRMImageMESA"));
        }
        return this.dispatch_eglCreateDRMImageMESA0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateDRMImageMESA);
    }
    
    private native long dispatch_eglCreateDRMImageMESA0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglExportDRMImageMESA(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"name\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"handle\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new RuntimeException("Argument \"stride\" is not a direct buffer");
        }
        final long addressof_eglExportDRMImageMESA = this._table._addressof_eglExportDRMImageMESA;
        if (addressof_eglExportDRMImageMESA == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglExportDRMImageMESA"));
        }
        return this.dispatch_eglExportDRMImageMESA0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), addressof_eglExportDRMImageMESA);
    }
    
    private native boolean dispatch_eglExportDRMImageMESA0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean eglExportDMABUFImageQueryMESA(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final LongBuffer longBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"fourcc\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"num_planes\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer)) {
            throw new RuntimeException("Argument \"modifiers\" is not a direct buffer");
        }
        final long addressof_eglExportDMABUFImageQueryMESA = this._table._addressof_eglExportDMABUFImageQueryMESA;
        if (addressof_eglExportDMABUFImageQueryMESA == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglExportDMABUFImageQueryMESA"));
        }
        return this.dispatch_eglExportDMABUFImageQueryMESA0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), addressof_eglExportDMABUFImageQueryMESA);
    }
    
    private native boolean dispatch_eglExportDMABUFImageQueryMESA0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean eglExportDMABUFImageMESA(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"fds\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"strides\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new RuntimeException("Argument \"offsets\" is not a direct buffer");
        }
        final long addressof_eglExportDMABUFImageMESA = this._table._addressof_eglExportDMABUFImageMESA;
        if (addressof_eglExportDMABUFImageMESA == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglExportDMABUFImageMESA"));
        }
        return this.dispatch_eglExportDMABUFImageMESA0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), addressof_eglExportDMABUFImageMESA);
    }
    
    private native boolean dispatch_eglExportDMABUFImageMESA0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean eglSwapBuffersRegionNOK(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"rects\" is not a direct buffer");
        }
        final long addressof_eglSwapBuffersRegionNOK = this._table._addressof_eglSwapBuffersRegionNOK;
        if (addressof_eglSwapBuffersRegionNOK == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffersRegionNOK"));
        }
        return this.dispatch_eglSwapBuffersRegionNOK0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglSwapBuffersRegionNOK);
    }
    
    private native boolean dispatch_eglSwapBuffersRegionNOK0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglSwapBuffersRegion2NOK(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"rects\" is not a direct buffer");
        }
        final long addressof_eglSwapBuffersRegion2NOK = this._table._addressof_eglSwapBuffersRegion2NOK;
        if (addressof_eglSwapBuffersRegion2NOK == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffersRegion2NOK"));
        }
        return this.dispatch_eglSwapBuffersRegion2NOK0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglSwapBuffersRegion2NOK);
    }
    
    private native boolean dispatch_eglSwapBuffersRegion2NOK0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean eglQueryNativeDisplayNV(final long n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"display_id\" is not a direct buffer");
        }
        final long addressof_eglQueryNativeDisplayNV = this._table._addressof_eglQueryNativeDisplayNV;
        if (addressof_eglQueryNativeDisplayNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryNativeDisplayNV"));
        }
        return this.dispatch_eglQueryNativeDisplayNV0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryNativeDisplayNV);
    }
    
    private native boolean dispatch_eglQueryNativeDisplayNV0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean eglQueryNativeWindowNV(final long n, final long n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"window\" is not a direct buffer");
        }
        final long addressof_eglQueryNativeWindowNV = this._table._addressof_eglQueryNativeWindowNV;
        if (addressof_eglQueryNativeWindowNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryNativeWindowNV"));
        }
        return this.dispatch_eglQueryNativeWindowNV0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryNativeWindowNV);
    }
    
    private native boolean dispatch_eglQueryNativeWindowNV0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglQueryNativePixmapNV(final long n, final long n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"pixmap\" is not a direct buffer");
        }
        final long addressof_eglQueryNativePixmapNV = this._table._addressof_eglQueryNativePixmapNV;
        if (addressof_eglQueryNativePixmapNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryNativePixmapNV"));
        }
        return this.dispatch_eglQueryNativePixmapNV0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglQueryNativePixmapNV);
    }
    
    private native boolean dispatch_eglQueryNativePixmapNV0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglPostSubBufferNV(final long n, final long n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_eglPostSubBufferNV = this._table._addressof_eglPostSubBufferNV;
        if (addressof_eglPostSubBufferNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglPostSubBufferNV"));
        }
        return this.dispatch_eglPostSubBufferNV0(n, n2, n3, n4, n5, n6, addressof_eglPostSubBufferNV);
    }
    
    private native boolean dispatch_eglPostSubBufferNV0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public long eglCreateStreamSyncNV(final long n, final Buffer buffer, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"stream\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateStreamSyncNV = this._table._addressof_eglCreateStreamSyncNV;
        if (addressof_eglCreateStreamSyncNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateStreamSyncNV"));
        }
        return this.dispatch_eglCreateStreamSyncNV0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateStreamSyncNV);
    }
    
    private native long dispatch_eglCreateStreamSyncNV0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public ByteBuffer eglCreateFenceSyncNV(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateFenceSyncNV = this._table._addressof_eglCreateFenceSyncNV;
        if (addressof_eglCreateFenceSyncNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateFenceSyncNV"));
        }
        final ByteBuffer dispatch_eglCreateFenceSyncNV0 = this.dispatch_eglCreateFenceSyncNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateFenceSyncNV);
        if (dispatch_eglCreateFenceSyncNV0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateFenceSyncNV0);
        return dispatch_eglCreateFenceSyncNV0;
    }
    
    private native ByteBuffer dispatch_eglCreateFenceSyncNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean eglDestroySyncNV(final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglDestroySyncNV = this._table._addressof_eglDestroySyncNV;
        if (addressof_eglDestroySyncNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySyncNV"));
        }
        return this.dispatch_eglDestroySyncNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroySyncNV);
    }
    
    private native boolean dispatch_eglDestroySyncNV0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean eglFenceNV(final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglFenceNV = this._table._addressof_eglFenceNV;
        if (addressof_eglFenceNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglFenceNV"));
        }
        return this.dispatch_eglFenceNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglFenceNV);
    }
    
    private native boolean dispatch_eglFenceNV0(final Object p0, final int p1, final long p2);
    
    @Override
    public int eglClientWaitSyncNV(final Buffer buffer, final int n, final long n2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglClientWaitSyncNV = this._table._addressof_eglClientWaitSyncNV;
        if (addressof_eglClientWaitSyncNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglClientWaitSyncNV"));
        }
        return this.dispatch_eglClientWaitSyncNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, n2, addressof_eglClientWaitSyncNV);
    }
    
    private native int dispatch_eglClientWaitSyncNV0(final Object p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public boolean eglSignalSyncNV(final Buffer buffer, final int n) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglSignalSyncNV = this._table._addressof_eglSignalSyncNV;
        if (addressof_eglSignalSyncNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSignalSyncNV"));
        }
        return this.dispatch_eglSignalSyncNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, addressof_eglSignalSyncNV);
    }
    
    private native boolean dispatch_eglSignalSyncNV0(final Object p0, final int p1, final int p2, final long p3);
    
    @Override
    public boolean eglGetSyncAttribNV(final Buffer buffer, final int n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetSyncAttribNV = this._table._addressof_eglGetSyncAttribNV;
        if (addressof_eglGetSyncAttribNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSyncAttribNV"));
        }
        return this.dispatch_eglGetSyncAttribNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetSyncAttribNV);
    }
    
    private native boolean dispatch_eglGetSyncAttribNV0(final Object p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public long eglGetSystemTimeFrequencyNV() {
        final long addressof_eglGetSystemTimeFrequencyNV = this._table._addressof_eglGetSystemTimeFrequencyNV;
        if (addressof_eglGetSystemTimeFrequencyNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSystemTimeFrequencyNV"));
        }
        return this.dispatch_eglGetSystemTimeFrequencyNV0(addressof_eglGetSystemTimeFrequencyNV);
    }
    
    private native long dispatch_eglGetSystemTimeFrequencyNV0(final long p0);
    
    @Override
    public long eglGetSystemTimeNV() {
        final long addressof_eglGetSystemTimeNV = this._table._addressof_eglGetSystemTimeNV;
        if (addressof_eglGetSystemTimeNV == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSystemTimeNV"));
        }
        return this.dispatch_eglGetSystemTimeNV0(addressof_eglGetSystemTimeNV);
    }
    
    private native long dispatch_eglGetSystemTimeNV0(final long p0);
    
    public EGLExtImpl(final EGLContext context, final EGLExtProcAddressTable table) {
        this._context = context;
        this._table = table;
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this._context.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this._context.isExtensionAvailable(s);
    }
    
    final EGLExtProcAddressTable getProcAdressTable() {
        return this._table;
    }
}
