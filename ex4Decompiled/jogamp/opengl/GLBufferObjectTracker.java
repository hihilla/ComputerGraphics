// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLBufferStorage;
import java.nio.ByteBuffer;
import com.jogamp.common.ExceptionUtils;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLException;
import java.nio.Buffer;
import com.jogamp.opengl.GL;
import com.jogamp.common.util.IntObjectHashMap;

public class GLBufferObjectTracker
{
    protected static final boolean DEBUG;
    private final IntObjectHashMap bufferName2StorageMap;
    private static final String GL_INVALID_OPERATION = "GL_INVALID_OPERATION";
    private static final String GL_INVALID_VALUE = "GL_INVALID_VALUE";
    private static final String warning = "WARNING";
    private static final String msgClazzName = "GLBufferObjectTracker";
    private static final String msgUnmapped = "notifyBufferUnmapped()";
    private static final String msgCreateBound = "createBoundBufferStorage()";
    private static final String msgCreateNamed = "createNamedBufferStorage()";
    private static final String msgMapBuffer = "mapBuffer()";
    
    public GLBufferObjectTracker() {
        (this.bufferName2StorageMap = new IntObjectHashMap()).setKeyNotFoundValue(null);
    }
    
    public final synchronized void createBufferStorage(final GLBufferStateTracker glBufferStateTracker, final GL gl, final int n, final long n2, final Buffer buffer, final int n3, final int n4, final CreateStorageDispatch createStorageDispatch) throws GLException {
        final int glGetError = gl.glGetError();
        if (GLBufferObjectTracker.DEBUG && glGetError) {
            System.err.printf("%s.%s glerr-pre 0x%X%n", "GLBufferObjectTracker", "createBoundBufferStorage()", glGetError);
        }
        final int boundBufferObject = glBufferStateTracker.getBoundBufferObject(n, gl);
        if (boundBufferObject == 0) {
            throw new GLException(String.format("%s: Buffer for target 0x%X not bound", "GL_INVALID_OPERATION", n));
        }
        final boolean b = 0 != n3;
        if ((b && 0L > n2) || (!b && 0L >= n2)) {
            throw new GLException(String.format("%s: Invalid size %d for buffer %d on target 0x%X", "GL_INVALID_VALUE", n2, boundBufferObject, n));
        }
        createStorageDispatch.create(n, n2, buffer, b ? n3 : n4);
        final int glGetError2 = gl.glGetError();
        if (glGetError2 != 0) {
            throw new GLException(String.format("GL-Error 0x%X while creating %s storage for target 0x%X -> buffer %d of size %d with data %s", glGetError2, b ? "mutable" : "immutable", n, boundBufferObject, n2, buffer));
        }
        final GLBufferStorageImpl glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.get(boundBufferObject);
        if (null != glBufferStorageImpl) {
            glBufferStorageImpl.reset(n2, n3, n4);
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s target: 0x%X -> reset %d: %s%n", "GLBufferObjectTracker", "createBoundBufferStorage()", n, boundBufferObject, glBufferStorageImpl);
            }
        }
        else {
            final GLBufferStorageImpl glBufferStorageImpl2 = new GLBufferStorageImpl(boundBufferObject, n2, n3, n4);
            this.bufferName2StorageMap.put(boundBufferObject, glBufferStorageImpl2);
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s target: 0x%X -> new %d: %s%n", "GLBufferObjectTracker", "createBoundBufferStorage()", n, boundBufferObject, glBufferStorageImpl2);
            }
        }
    }
    
    public final synchronized void createBufferStorage(final GL gl, final int n, final long n2, final Buffer buffer, final int n3, final int n4, final CreateStorageDispatch createStorageDispatch) throws GLException {
        final int glGetError = gl.glGetError();
        if (GLBufferObjectTracker.DEBUG && glGetError) {
            System.err.printf("%s.%s glerr-pre 0x%X%n", "GLBufferObjectTracker", "createNamedBufferStorage()", glGetError);
        }
        if (0L > n2) {
            throw new GLException(String.format("%s: Invalid size %d for buffer %d", "GL_INVALID_VALUE", n2, n));
        }
        if (0 == n3) {
            throw new InternalError("Immutable glNamedBufferStorage not supported yet");
        }
        createStorageDispatch.create(n, n2, buffer, n3);
        final int glGetError2 = gl.glGetError();
        if (glGetError2 != 0) {
            throw new GLException(String.format("GL-Error 0x%X while creating %s storage for buffer %d of size %d with data %s", glGetError2, "mutable", n, n2, buffer));
        }
        final GLBufferStorageImpl glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.get(n);
        if (null != glBufferStorageImpl) {
            glBufferStorageImpl.reset(n2, n3, n4);
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s direct: reset %d: %s%n", "GLBufferObjectTracker", "createNamedBufferStorage()", n, glBufferStorageImpl);
            }
        }
        else {
            final GLBufferStorageImpl glBufferStorageImpl2 = new GLBufferStorageImpl(n, n2, n3, n4);
            this.bufferName2StorageMap.put(n, glBufferStorageImpl2);
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s direct: new %d: %s%n", "GLBufferObjectTracker", "createNamedBufferStorage()", n, glBufferStorageImpl2);
            }
        }
    }
    
    public final synchronized void notifyBuffersDeleted(final int n, final int[] array, final int n2) {
        for (int i = 0; i < n; ++i) {
            this.notifyBufferDeleted(array[i + n2], i, n);
        }
    }
    
    public final synchronized void notifyBuffersDeleted(final int n, final IntBuffer intBuffer) {
        final int position = intBuffer.position();
        for (int i = 0; i < n; ++i) {
            this.notifyBufferDeleted(intBuffer.get(i + position), i, n);
        }
    }
    
    private final synchronized void notifyBufferDeleted(final int n, final int n2, final int n3) {
        final GLBufferStorageImpl glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.put(n, null);
        if (GLBufferObjectTracker.DEBUG) {
            System.err.printf("%s.notifyBuffersDeleted()[%d/%d]: %d: %s -> null%n", "GLBufferObjectTracker", n2 + 1, n3, n, glBufferStorageImpl);
        }
        if (null == glBufferStorageImpl) {
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s: %s.notifyBuffersDeleted()[%d/%d]: Buffer %d not tracked%n", "WARNING", "GLBufferObjectTracker", n2 + 1, n3, n);
                ExceptionUtils.dumpStack(System.err);
            }
            return;
        }
        glBufferStorageImpl.setMappedBuffer(null);
    }
    
    public final synchronized GLBufferStorage mapBuffer(final GLBufferStateTracker glBufferStateTracker, final GL gl, final int n, final int n2, final MapBufferAllDispatch mapBufferAllDispatch) throws GLException {
        return this.mapBufferImpl(glBufferStateTracker, gl, n, false, 0L, 0L, n2, mapBufferAllDispatch);
    }
    
    public final synchronized GLBufferStorage mapBuffer(final GLBufferStateTracker glBufferStateTracker, final GL gl, final int n, final long n2, final long n3, final int n4, final MapBufferRangeDispatch mapBufferRangeDispatch) throws GLException {
        return this.mapBufferImpl(glBufferStateTracker, gl, n, true, n2, n3, n4, mapBufferRangeDispatch);
    }
    
    public final synchronized GLBufferStorage mapBuffer(final int n, final int n2, final MapBufferAllDispatch mapBufferAllDispatch) throws GLException {
        return this.mapBufferImpl(0, n, true, false, 0L, 0L, n2, mapBufferAllDispatch);
    }
    
    public final synchronized GLBufferStorage mapBuffer(final int n, final long n2, final long n3, final int n4, final MapBufferRangeDispatch mapBufferRangeDispatch) throws GLException {
        return this.mapBufferImpl(0, n, true, true, n2, n3, n4, mapBufferRangeDispatch);
    }
    
    private final synchronized GLBufferStorage mapBufferImpl(final GLBufferStateTracker glBufferStateTracker, final GL gl, final int n, final boolean b, final long n2, final long n3, final int n4, final MapBufferDispatch mapBufferDispatch) throws GLException {
        final int boundBufferObject = glBufferStateTracker.getBoundBufferObject(n, gl);
        if (boundBufferObject == 0) {
            throw new GLException(String.format("%s.%s: %s Buffer for target 0x%X not bound", "GLBufferObjectTracker", "mapBuffer()", "GL_INVALID_OPERATION", n));
        }
        return this.mapBufferImpl(n, boundBufferObject, false, b, n2, n3, n4, mapBufferDispatch);
    }
    
    private final synchronized GLBufferStorage mapBufferImpl(final int n, final int n2, final boolean b, final boolean b2, long n3, long n4, final int n5, final MapBufferDispatch mapBufferDispatch) throws GLException {
        final GLBufferStorageImpl glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.get(n2);
        if (null == glBufferStorageImpl) {
            throw new GLException("Buffer with name " + n2 + " not tracked");
        }
        if (null != glBufferStorageImpl.getMappedBuffer()) {
            throw new GLException(String.format("%s.%s: %s Buffer storage of target 0x%X -> %d: %s is already mapped", "GLBufferObjectTracker", "mapBuffer()", "GL_INVALID_OPERATION", n, n2, glBufferStorageImpl));
        }
        final long size = glBufferStorageImpl.getSize();
        if (0L > size) {
            throw new GLException(String.format("%s.%s: %s Buffer storage of target 0x%X -> %d: %s is of less-than zero", "GLBufferObjectTracker", "mapBuffer()", "GL_INVALID_OPERATION", n, n2, glBufferStorageImpl));
        }
        if (!b2) {
            n4 = size;
            n3 = 0L;
        }
        if (n4 + n3 > size) {
            throw new GLException(String.format("%s.%s: %s Out of range: offset %d, length %d, buffer storage of target 0x%X -> %d: %s", "GLBufferObjectTracker", "mapBuffer()", "GL_INVALID_VALUE", n3, n4, n, n2, glBufferStorageImpl));
        }
        if (0L >= n4 || 0L > n3) {
            throw new GLException(String.format("%s.%s: %s Invalid values: offset %d, length %d, buffer storage of target 0x%X -> %d: %s", "GLBufferObjectTracker", "mapBuffer()", "GL_INVALID_VALUE", n3, n4, n, n2, glBufferStorageImpl));
        }
        if (0L == size) {
            return glBufferStorageImpl;
        }
        long n6;
        if (b) {
            if (b2) {
                n6 = ((MapBufferRangeDispatch)mapBufferDispatch).mapBuffer(n2, n3, n4, n5);
            }
            else {
                n6 = ((MapBufferAllDispatch)mapBufferDispatch).mapBuffer(n2, n5);
            }
        }
        else if (b2) {
            n6 = ((MapBufferRangeDispatch)mapBufferDispatch).mapBuffer(n, n3, n4, n5);
        }
        else {
            n6 = ((MapBufferAllDispatch)mapBufferDispatch).mapBuffer(n, n5);
        }
        if (0L == n6) {
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s: %s MapBuffer null result for target 0x%X -> %d: %s, off %d, len %d, acc 0x%X%n", "GLBufferObjectTracker", "mapBuffer()", "WARNING", n, n2, glBufferStorageImpl, n3, n4, n5);
                ExceptionUtils.dumpStack(System.err);
            }
        }
        else {
            final ByteBuffer allocNioByteBuffer = mapBufferDispatch.allocNioByteBuffer(n6, n4);
            Buffers.nativeOrder(allocNioByteBuffer);
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s.%s: Target 0x%X -> %d: %s, off %d, len %d, acc 0x%X%n", "GLBufferObjectTracker", "GLBufferObjectTracker", n, n2, glBufferStorageImpl.toString(false), n3, n4, n5);
            }
            glBufferStorageImpl.setMappedBuffer(allocNioByteBuffer);
        }
        return glBufferStorageImpl;
    }
    
    public final synchronized boolean unmapBuffer(final GLBufferStateTracker glBufferStateTracker, final GL gl, final int n, final UnmapBufferDispatch unmapBufferDispatch) {
        final int boundBufferObject = glBufferStateTracker.getBoundBufferObject(n, gl);
        GLBufferStorageImpl glBufferStorageImpl;
        if (boundBufferObject == 0) {
            if (GLBufferObjectTracker.DEBUG) {
                System.err.printf("%s: %s.%s: Buffer for target 0x%X not bound%n", "WARNING", "GLBufferObjectTracker", "notifyBufferUnmapped()", n);
                ExceptionUtils.dumpStack(System.err);
            }
            glBufferStorageImpl = null;
        }
        else {
            glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.get(boundBufferObject);
            if (GLBufferObjectTracker.DEBUG && null == glBufferStorageImpl) {
                System.err.printf("%s: %s.%s: Buffer %d not tracked%n", "WARNING", "GLBufferObjectTracker", "notifyBufferUnmapped()", boundBufferObject);
                ExceptionUtils.dumpStack(System.err);
            }
        }
        final boolean unmap = unmapBufferDispatch.unmap(n);
        if (unmap && null != glBufferStorageImpl) {
            glBufferStorageImpl.setMappedBuffer(null);
        }
        if (GLBufferObjectTracker.DEBUG) {
            System.err.printf("%s.%s %s target: 0x%X -> %d: %s%n", "GLBufferObjectTracker", "notifyBufferUnmapped()", unmap ? "OK" : "Failed", n, boundBufferObject, glBufferStorageImpl.toString(false));
            if (!unmap) {
                ExceptionUtils.dumpStack(System.err);
            }
        }
        return unmap;
    }
    
    public final synchronized boolean unmapBuffer(final int n, final UnmapBufferDispatch unmapBufferDispatch) {
        final GLBufferStorageImpl glBufferStorageImpl = (GLBufferStorageImpl)this.bufferName2StorageMap.get(n);
        if (GLBufferObjectTracker.DEBUG && null == glBufferStorageImpl) {
            System.err.printf("%s: %s.%s: Buffer %d not tracked%n", "WARNING", "GLBufferObjectTracker", "notifyBufferUnmapped()", n);
            ExceptionUtils.dumpStack(System.err);
        }
        final boolean unmap = unmapBufferDispatch.unmap(n);
        if (unmap && null != glBufferStorageImpl) {
            glBufferStorageImpl.setMappedBuffer(null);
        }
        if (GLBufferObjectTracker.DEBUG) {
            System.err.printf("%s.%s %s %d: %s%n", "GLBufferObjectTracker", "notifyBufferUnmapped()", unmap ? "OK" : "Failed", n, glBufferStorageImpl.toString(false));
            if (!unmap) {
                ExceptionUtils.dumpStack(System.err);
            }
        }
        return unmap;
    }
    
    public final synchronized GLBufferStorage getBufferStorage(final int n) {
        return (GLBufferStorageImpl)this.bufferName2StorageMap.get(n);
    }
    
    public final synchronized void clear() {
        if (GLBufferObjectTracker.DEBUG) {
            System.err.printf("%s.clear() - Thread %s%n", "GLBufferObjectTracker", Thread.currentThread().getName());
        }
        this.bufferName2StorageMap.clear();
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.GLBufferObjectTracker", true);
    }
    
    static final class GLBufferStorageImpl extends GLBufferStorage
    {
        GLBufferStorageImpl(final int n, final long n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        protected final void reset(final long n, final int n2, final int n3) {
            super.reset(n, n2, n3);
        }
        
        @Override
        protected final void setMappedBuffer(final ByteBuffer mappedBuffer) {
            super.setMappedBuffer(mappedBuffer);
        }
    }
    
    public interface UnmapBufferDispatch
    {
        boolean unmap(final int p0);
    }
    
    public interface MapBufferAllDispatch extends MapBufferDispatch
    {
        long mapBuffer(final int p0, final int p1);
    }
    
    public interface MapBufferRangeDispatch extends MapBufferDispatch
    {
        long mapBuffer(final int p0, final long p1, final long p2, final int p3);
    }
    
    public interface MapBufferDispatch
    {
        ByteBuffer allocNioByteBuffer(final long p0, final long p1);
    }
    
    public interface CreateStorageDispatch
    {
        void create(final int p0, final long p1, final Buffer p2, final int p3);
    }
}
