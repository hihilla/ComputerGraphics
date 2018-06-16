// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.nativewindow.MutableSurface;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.common.nio.PointerBuffer;
import jogamp.nativewindow.windows.BITMAPINFOHEADER;
import jogamp.nativewindow.windows.BITMAPINFO;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class WindowsBitmapWGLDrawable extends WindowsWGLDrawable
{
    private long origbitmap;
    private long hbitmap;
    
    private WindowsBitmapWGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, false);
    }
    
    protected static WindowsBitmapWGLDrawable create(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
        final AbstractGraphicsDevice device = windowsWGLGraphicsConfiguration.getScreen().getDevice();
        if (!GLProfile.isAvailable(device, "GL2")) {
            throw new GLException("GLProfile GL2 n/a on " + device + " but required for Windows BITMAP");
        }
        final GLProfile value = GLProfile.get("GL2");
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
        final GLCapabilitiesImmutable fixGLProfile = GLGraphicsConfigurationUtil.fixGLProfile(GLGraphicsConfigurationUtil.clipRGBAGLCapabilities(glCapabilitiesImmutable, false, false), value);
        if (glCapabilitiesImmutable != fixGLProfile) {
            windowsWGLGraphicsConfiguration.setChosenCapabilities(fixGLProfile);
            if (WindowsBitmapWGLDrawable.DEBUG) {
                System.err.println("WindowsBitmapWGLDrawable: " + glCapabilitiesImmutable + " -> " + fixGLProfile);
            }
        }
        return new WindowsBitmapWGLDrawable(glDrawableFactory, nativeSurface);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            this.createBitmap();
        }
        else {
            this.destroyBitmap();
        }
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new WindowsWGLContext(this, glContext);
    }
    
    @Override
    public boolean isGLOriented() {
        return false;
    }
    
    private void createBitmap() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        if (WindowsBitmapWGLDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": WindowsBitmapWGLDrawable (1): " + nativeSurface);
        }
        final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
        final int surfaceWidth = this.getSurfaceWidth();
        final int surfaceHeight = this.getSurfaceHeight();
        final BITMAPINFO create = BITMAPINFO.create();
        final BITMAPINFOHEADER bmiHeader = create.getBmiHeader();
        final int n = glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits();
        final int n2 = 24;
        bmiHeader.setBiSize(BITMAPINFOHEADER.size());
        bmiHeader.setBiWidth(surfaceWidth);
        bmiHeader.setBiHeight(-1 * surfaceHeight);
        bmiHeader.setBiPlanes((short)1);
        bmiHeader.setBiBitCount((short)n2);
        bmiHeader.setBiXPelsPerMeter(0);
        bmiHeader.setBiYPelsPerMeter(0);
        bmiHeader.setBiClrUsed(0);
        bmiHeader.setBiClrImportant(0);
        bmiHeader.setBiCompression(0);
        final int biSizeImage = surfaceWidth * surfaceHeight * (n2 >> 3);
        bmiHeader.setBiSizeImage(biSizeImage);
        final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(1);
        this.hbitmap = GDI.CreateDIBSection(0L, create, 0, allocateDirect, 0L, 0);
        final int getLastError = GDI.GetLastError();
        if (WindowsBitmapWGLDrawable.DEBUG) {
            System.err.println("WindowsBitmapWGLDrawable: pb sz/ptr " + allocateDirect.capacity() + ", " + GLDrawableImpl.toHexString((allocateDirect.capacity() > 0) ? allocateDirect.get(0) : 0L));
            System.err.println("WindowsBitmapWGLDrawable: " + surfaceWidth + "x" + surfaceHeight + ", bpp " + n + " -> " + n2 + ", bytes " + biSizeImage + ", header sz " + BITMAPINFOHEADER.size() + ", DIB ptr num " + allocateDirect.capacity() + ", " + glCapabilitiesImmutable + ", werr " + getLastError);
        }
        if (this.hbitmap == 0L) {
            throw new GLException("Error creating offscreen bitmap of " + nativeSurface + ", werr " + getLastError);
        }
        final long createCompatibleDC = GDI.CreateCompatibleDC(0L);
        final int getLastError2 = GDI.GetLastError();
        if (createCompatibleDC == 0L) {
            GDI.DeleteObject(this.hbitmap);
            this.hbitmap = 0L;
            throw new GLException("Error creating device context for offscreen OpenGL context, werr " + getLastError2);
        }
        ((MutableSurface)nativeSurface).setSurfaceHandle(createCompatibleDC);
        if (WindowsBitmapWGLDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": WindowsBitmapWGLDrawable (2): " + nativeSurface);
        }
        final long selectObject = GDI.SelectObject(createCompatibleDC, this.hbitmap);
        this.origbitmap = selectObject;
        if (selectObject == 0L) {
            GDI.DeleteObject(this.hbitmap);
            this.hbitmap = 0L;
            GDI.DeleteDC(createCompatibleDC);
            throw new GLException("Error selecting bitmap into new device context");
        }
        windowsWGLGraphicsConfiguration.updateGraphicsConfiguration(this.getFactory(), nativeSurface, null);
    }
    
    protected void destroyBitmap() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        if (nativeSurface.getSurfaceHandle() != 0L) {
            GDI.SelectObject(nativeSurface.getSurfaceHandle(), this.origbitmap);
            GDI.DeleteObject(this.hbitmap);
            GDI.DeleteDC(nativeSurface.getSurfaceHandle());
            this.origbitmap = 0L;
            this.hbitmap = 0L;
            ((MutableSurface)nativeSurface).setSurfaceHandle(0L);
        }
    }
}
