// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

public class EGLGLCapabilities extends GLCapabilities
{
    private long eglcfg;
    private final int eglcfgid;
    private final int renderableType;
    private final int nativeVisualID;
    
    public EGLGLCapabilities(final long eglcfg, final int eglcfgid, final int nativeVisualID, final GLProfile glProfile, final int renderableType) {
        super(glProfile);
        this.eglcfg = eglcfg;
        this.eglcfgid = eglcfgid;
        if (!isCompatible(glProfile, renderableType)) {
            throw new GLException("Requested GLProfile " + glProfile + " not compatible with EGL-RenderableType[" + (Object)renderableTypeToString(null, renderableType) + "]");
        }
        this.renderableType = renderableType;
        this.nativeVisualID = nativeVisualID;
    }
    
    @Override
    public Object cloneMutable() {
        return this.clone();
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (RuntimeException ex) {
            throw new GLException(ex);
        }
    }
    
    protected final void setEGLConfig(final long eglcfg) {
        this.eglcfg = eglcfg;
    }
    
    public final long getEGLConfig() {
        return this.eglcfg;
    }
    
    public final int getEGLConfigID() {
        return this.eglcfgid;
    }
    
    public final int getRenderableType() {
        return this.renderableType;
    }
    
    public final int getNativeVisualID() {
        return this.nativeVisualID;
    }
    
    @Override
    public final int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        switch (vidType) {
            case INTRINSIC:
            case EGL_CONFIG: {
                return this.getEGLConfigID();
            }
            case NATIVE: {
                return this.getNativeVisualID();
            }
            default: {
                throw new NativeWindowException("Invalid type <" + vidType + ">");
            }
        }
    }
    
    public static boolean isCompatible(final GLProfile glProfile, final int n) {
        return null == glProfile || (0x0 != (n & 0x40) && glProfile.usesNativeGLES3()) || (0x0 != (n & 0x4) && glProfile.usesNativeGLES2()) || (0x0 != (n & 0x1) && glProfile.usesNativeGLES1()) || (0x0 != (n & 0x8) && !glProfile.usesNativeGLES());
    }
    
    public static GLProfile getCompatible(final EGLGraphicsDevice eglGraphicsDevice, final int n) {
        if (0x0 != (n & 0x40) && GLProfile.isAvailable(eglGraphicsDevice, "GLES3")) {
            return GLProfile.get(eglGraphicsDevice, "GLES3");
        }
        if (0x0 != (n & 0x4) && GLProfile.isAvailable(eglGraphicsDevice, "GLES2")) {
            return GLProfile.get(eglGraphicsDevice, "GLES2");
        }
        if (0x0 != (n & 0x1) && GLProfile.isAvailable(eglGraphicsDevice, "GLES1")) {
            return GLProfile.get(eglGraphicsDevice, "GLES1");
        }
        if (0x0 != (n & 0x8)) {
            return GLProfile.getDefault(eglGraphicsDevice);
        }
        return null;
    }
    
    public static StringBuilder renderableTypeToString(StringBuilder sb, final int n) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        int n2 = 1;
        sb.append("0x").append(Integer.toHexString(n)).append(": ");
        if (0x0 != (n & 0x8)) {
            sb.append("GL");
            n2 = 0;
        }
        if (0x0 != (n & 0x1)) {
            if (n2 == 0) {
                sb.append(", ");
            }
            sb.append("GLES1");
            n2 = 0;
        }
        if (0x0 != (n & 0x4)) {
            if (n2 == 0) {
                sb.append(", ");
            }
            sb.append("GLES2");
            n2 = 0;
        }
        if (0x0 != (n & 0x40)) {
            if (n2 == 0) {
                sb.append(", ");
            }
            sb.append("GLES3");
            n2 = 0;
        }
        if (0x0 != (n & 0x30A1)) {
            if (n2 == 0) {
                sb.append(", ");
            }
            sb.append("VG");
        }
        return sb;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("egl cfg 0x").append(Integer.toHexString(this.eglcfgid));
        sb.append(", vid 0x").append(Integer.toHexString(this.nativeVisualID)).append(": ");
        super.toString(sb);
        sb.append(", [");
        renderableTypeToString(sb, this.renderableType);
        return sb.append("]");
    }
}
