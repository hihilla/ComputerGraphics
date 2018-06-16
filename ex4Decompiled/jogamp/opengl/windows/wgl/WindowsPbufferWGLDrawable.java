// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLProfile;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.common.nio.Buffers;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.MutableSurface;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.windows.GDI;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class WindowsPbufferWGLDrawable extends WindowsWGLDrawable
{
    private WGLExt cachedWGLExt;
    private long buffer;
    
    protected WindowsPbufferWGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, false);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            this.createPbuffer();
        }
        else {
            this.destroyPbuffer();
        }
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new WindowsWGLContext(this, glContext);
    }
    
    protected void destroyPbuffer() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        if (0L != this.buffer) {
            final WGLExt cachedWGLExt = this.cachedWGLExt;
            if (nativeSurface.getSurfaceHandle() != 0L) {
                if (cachedWGLExt.wglReleasePbufferDCARB(this.buffer, nativeSurface.getSurfaceHandle()) == 0) {
                    throw new GLException("Error releasing pbuffer device context: error code " + GDI.GetLastError());
                }
                ((MutableSurface)nativeSurface).setSurfaceHandle(0L);
            }
            if (!cachedWGLExt.wglDestroyPbufferARB(this.buffer)) {
                throw new GLException("Error destroying pbuffer: error code " + GDI.GetLastError());
            }
            this.buffer = 0L;
        }
    }
    
    public long getPbufferHandle() {
        return this.buffer;
    }
    
    private void createPbuffer() {
        final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)this.getNativeSurface().getGraphicsConfiguration();
        final WindowsWGLDrawableFactory.SharedResource orCreateSharedResourceImpl = ((WindowsWGLDrawableFactory)this.factory).getOrCreateSharedResourceImpl(windowsWGLGraphicsConfiguration.getScreen().getDevice());
        final NativeSurface nativeSurface = orCreateSharedResourceImpl.getDrawable().getNativeSurface();
        if (1 >= nativeSurface.lockSurface()) {
            throw new NativeWindowException("Could not lock (sharedSurface): " + this);
        }
        try {
            final long surfaceHandle = nativeSurface.getSurfaceHandle();
            final WGLExt wglExt = ((WindowsWGLContext)orCreateSharedResourceImpl.getContext()).getWGLExt();
            if (WindowsPbufferWGLDrawable.DEBUG) {
                System.err.println(GLDrawableImpl.getThreadName() + ": Pbuffer config: " + windowsWGLGraphicsConfiguration);
            }
            final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(false, false, true, false);
            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(512);
            final FloatBuffer directFloatBuffer = Buffers.newDirectFloatBuffer(1);
            final int[] array = { 0 };
            final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
            final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
            final AbstractGraphicsDevice device = windowsWGLGraphicsConfiguration.getScreen().getDevice();
            if (WindowsPbufferWGLDrawable.DEBUG) {
                System.err.println(GLDrawableImpl.getThreadName() + ": Pbuffer parentHdc = " + GLDrawableImpl.toHexString(surfaceHandle));
                System.err.println(GLDrawableImpl.getThreadName() + ": Pbuffer chosenCaps: " + glCapabilitiesImmutable);
            }
            if (!WindowsWGLGraphicsConfiguration.GLCapabilities2AttribList(orCreateSharedResourceImpl, glCapabilitiesImmutable, directIntBuffer, -1, array)) {
                throw new GLException("Pbuffer-related extensions not supported");
            }
            final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(256);
            final IntBuffer directIntBuffer3 = Buffers.newDirectIntBuffer(1);
            if (!wglExt.wglChoosePixelFormatARB(surfaceHandle, directIntBuffer, directFloatBuffer, 256, directIntBuffer2, directIntBuffer3)) {
                throw new GLException("pbuffer creation error: wglChoosePixelFormat() failed");
            }
            final int min = Math.min(directIntBuffer3.get(0), 256);
            if (min <= 0) {
                throw new GLException("pbuffer creation error: Couldn't find a suitable pixel format");
            }
            if (WindowsPbufferWGLDrawable.DEBUG) {
                System.err.println("" + min + " suitable pixel formats found");
                for (int i = 0; i < min; ++i) {
                    System.err.println("pixel format " + directIntBuffer2.get(i) + " (index " + i + "): " + WindowsWGLGraphicsConfiguration.wglARBPFID2GLCapabilitiesNoCheck(orCreateSharedResourceImpl, device, glProfile, surfaceHandle, directIntBuffer2.get(i), exclusiveWinAttributeBits));
                }
            }
            long wglCreatePbufferARB = 0L;
            int j;
            for (j = 0; j < min; ++j) {
                final int value = directIntBuffer2.get(j);
                int n = 0;
                directIntBuffer.put(n++, 0);
                wglCreatePbufferARB = wglExt.wglCreatePbufferARB(surfaceHandle, value, this.getSurfaceWidth(), this.getSurfaceHeight(), directIntBuffer);
                if (wglCreatePbufferARB != 0L) {
                    break;
                }
            }
            if (0L == wglCreatePbufferARB) {
                throw new GLException("pbuffer creation error: wglCreatePbuffer() failed: tried " + min + " pixel formats, last error was: " + wglGetLastError());
            }
            final int value2 = directIntBuffer2.get(j);
            final long wglGetPbufferDCARB = wglExt.wglGetPbufferDCARB(wglCreatePbufferARB);
            if (wglGetPbufferDCARB == 0L) {
                throw new GLException("pbuffer creation error: wglGetPbufferDC() failed");
            }
            final NativeSurface nativeSurface2 = this.getNativeSurface();
            this.buffer = wglCreatePbufferARB;
            ((MutableSurface)nativeSurface2).setSurfaceHandle(wglGetPbufferDCARB);
            this.cachedWGLExt = wglExt;
            final WGLGLCapabilities wglARBPFID2GLCapabilities = WindowsWGLGraphicsConfiguration.wglARBPFID2GLCapabilities(orCreateSharedResourceImpl, device, glProfile, surfaceHandle, value2, exclusiveWinAttributeBits);
            if (null == wglARBPFID2GLCapabilities) {
                throw new GLException("pbuffer creation error: unable to re-query chosen PFD ID: " + value2 + ", hdc " + GLDrawableImpl.toHexString(wglGetPbufferDCARB));
            }
            if (wglARBPFID2GLCapabilities.isOnscreen() || !wglARBPFID2GLCapabilities.isPBuffer()) {
                throw new GLException("Error: Selected Onscreen Caps for PBuffer: " + wglARBPFID2GLCapabilities);
            }
            windowsWGLGraphicsConfiguration.setCapsPFD(wglARBPFID2GLCapabilities);
        }
        finally {
            nativeSurface.unlockSurface();
        }
    }
    
    private static String wglGetLastError() {
        return WindowsWGLDrawableFactory.wglGetLastError();
    }
}
