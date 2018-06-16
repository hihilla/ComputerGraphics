// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import jogamp.nativewindow.x11.XVisualInfo;
import com.jogamp.opengl.GLCapabilities;

public class X11GLCapabilities extends GLCapabilities
{
    private final XVisualInfo xVisualInfo;
    private final long fbcfg;
    private final int fbcfgid;
    
    public X11GLCapabilities(final XVisualInfo xVisualInfo, final long fbcfg, final int fbcfgid, final GLProfile glProfile) {
        super(glProfile);
        this.xVisualInfo = xVisualInfo;
        this.fbcfg = fbcfg;
        this.fbcfgid = fbcfgid;
    }
    
    public X11GLCapabilities(final XVisualInfo xVisualInfo, final GLProfile glProfile) {
        super(glProfile);
        this.xVisualInfo = xVisualInfo;
        this.fbcfg = 0L;
        this.fbcfgid = 0;
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
    
    public final XVisualInfo getXVisualInfo() {
        return this.xVisualInfo;
    }
    
    public final int getXVisualID() {
        return (null != this.xVisualInfo) ? ((int)this.xVisualInfo.getVisualid()) : 0;
    }
    
    public final boolean hasXVisualInfo() {
        return null != this.xVisualInfo;
    }
    
    public final long getFBConfig() {
        return this.fbcfg;
    }
    
    public final int getFBConfigID() {
        return this.fbcfgid;
    }
    
    public final boolean hasFBConfig() {
        return 0L != this.fbcfg && this.fbcfgid != 0;
    }
    
    @Override
    public final int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        switch (vidType) {
            case INTRINSIC:
            case NATIVE:
            case X11_XVISUAL: {
                return this.getXVisualID();
            }
            case X11_FBCONFIG: {
                return this.getFBConfigID();
            }
            default: {
                throw new NativeWindowException("Invalid type <" + vidType + ">");
            }
        }
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("glx vid ");
        if (this.hasXVisualInfo()) {
            sb.append("0x").append(Long.toHexString(this.xVisualInfo.getVisualid()));
        }
        else {
            sb.append("----");
        }
        sb.append(", fbc ");
        if (this.hasFBConfig()) {
            sb.append("0x").append(Integer.toHexString(this.fbcfgid));
        }
        else {
            sb.append("----");
        }
        sb.append(": ");
        return super.toString(sb);
    }
}
