// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import jogamp.opengl.Debug;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.common.util.VersionNumber;

public class GLXUtil
{
    public static final boolean DEBUG;
    private static boolean clientMultisampleAvailable;
    private static String clientVendorName;
    private static VersionNumber clientVersionNumber;
    
    public static synchronized boolean isGLXAvailableOnServer(final X11GraphicsDevice x11GraphicsDevice) {
        if (null == x11GraphicsDevice) {
            throw new IllegalArgumentException("null X11GraphicsDevice");
        }
        if (0L == x11GraphicsDevice.getHandle()) {
            throw new IllegalArgumentException("null X11GraphicsDevice display handle");
        }
        boolean glXQueryExtension = false;
        x11GraphicsDevice.lock();
        try {
            glXQueryExtension = GLX.glXQueryExtension(x11GraphicsDevice.getHandle(), null, null);
        }
        catch (Throwable t) {}
        finally {
            x11GraphicsDevice.unlock();
        }
        return glXQueryExtension;
    }
    
    public static String getGLXClientString(final X11GraphicsDevice x11GraphicsDevice, final int n) {
        x11GraphicsDevice.lock();
        try {
            return GLX.glXGetClientString(x11GraphicsDevice.getHandle(), n);
        }
        finally {
            x11GraphicsDevice.unlock();
        }
    }
    
    public static String queryGLXServerString(final X11GraphicsDevice x11GraphicsDevice, final int n, final int n2) {
        x11GraphicsDevice.lock();
        try {
            return GLX.glXQueryServerString(x11GraphicsDevice.getHandle(), n, n2);
        }
        finally {
            x11GraphicsDevice.unlock();
        }
    }
    
    public static String queryGLXExtensionsString(final X11GraphicsDevice x11GraphicsDevice, final int n) {
        x11GraphicsDevice.lock();
        try {
            return GLX.glXQueryExtensionsString(x11GraphicsDevice.getHandle(), n);
        }
        finally {
            x11GraphicsDevice.unlock();
        }
    }
    
    public static VersionNumber getGLXServerVersionNumber(final X11GraphicsDevice x11GraphicsDevice) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        x11GraphicsDevice.lock();
        try {
            if (!GLX.glXQueryVersion(x11GraphicsDevice.getHandle(), directIntBuffer, directIntBuffer2)) {
                throw new GLException("glXQueryVersion failed");
            }
            if (directIntBuffer.get(0) == 1 && directIntBuffer2.get(0) == 2) {
                final String glXGetClientString = GLX.glXGetClientString(x11GraphicsDevice.getHandle(), 2);
                try {
                    directIntBuffer.put(0, Integer.parseInt(glXGetClientString.substring(0, 1)));
                    directIntBuffer2.put(0, Integer.parseInt(glXGetClientString.substring(2, 3)));
                }
                catch (Exception ex) {
                    directIntBuffer.put(0, 1);
                    directIntBuffer2.put(0, 2);
                }
            }
        }
        finally {
            x11GraphicsDevice.unlock();
        }
        return new VersionNumber(directIntBuffer.get(0), directIntBuffer2.get(0), 0);
    }
    
    public static boolean isMultisampleAvailable(final String s) {
        return s != null && s.indexOf("GLX_ARB_multisample") >= 0;
    }
    
    public static boolean isVendorNVIDIA(final String s) {
        return s != null && s.startsWith("NVIDIA");
    }
    
    public static boolean isVendorATI(final String s) {
        return s != null && s.startsWith("ATI");
    }
    
    public static boolean isClientMultisampleAvailable() {
        return GLXUtil.clientMultisampleAvailable;
    }
    
    public static String getClientVendorName() {
        return GLXUtil.clientVendorName;
    }
    
    public static VersionNumber getClientVersionNumber() {
        return GLXUtil.clientVersionNumber;
    }
    
    public static synchronized void initGLXClientDataSingleton(final X11GraphicsDevice x11GraphicsDevice) {
        if (null != GLXUtil.clientVendorName) {
            return;
        }
        if (null == x11GraphicsDevice) {
            throw new IllegalArgumentException("null X11GraphicsDevice");
        }
        if (0L == x11GraphicsDevice.getHandle()) {
            throw new IllegalArgumentException("null X11GraphicsDevice display handle");
        }
        GLXUtil.clientMultisampleAvailable = isMultisampleAvailable(GLX.glXGetClientString(x11GraphicsDevice.getHandle(), 3));
        GLXUtil.clientVendorName = GLX.glXGetClientString(x11GraphicsDevice.getHandle(), 1);
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final String glXGetClientString = GLX.glXGetClientString(x11GraphicsDevice.getHandle(), 2);
        try {
            array[0] = Integer.parseInt(glXGetClientString.substring(0, 1));
            array2[0] = Integer.parseInt(glXGetClientString.substring(2, 3));
        }
        catch (Exception ex) {
            array[0] = 1;
            array2[0] = 2;
        }
        GLXUtil.clientVersionNumber = new VersionNumber(array[0], array2[0], 0);
    }
    
    static {
        DEBUG = Debug.debug("GLXUtil");
        GLXUtil.clientMultisampleAvailable = false;
        GLXUtil.clientVendorName = null;
        GLXUtil.clientVersionNumber = null;
    }
}
