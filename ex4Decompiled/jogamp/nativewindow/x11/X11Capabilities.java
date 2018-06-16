// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.Capabilities;

public class X11Capabilities extends Capabilities
{
    private final XVisualInfo xVisualInfo;
    
    public X11Capabilities(final XVisualInfo xVisualInfo) {
        this.xVisualInfo = xVisualInfo;
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
            throw new NativeWindowException(ex);
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
    
    @Override
    public final int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        switch (vidType) {
            case INTRINSIC:
            case NATIVE:
            case X11_XVISUAL: {
                return this.getXVisualID();
            }
            case X11_FBCONFIG: {
                return 0;
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
        sb.append("x11 vid ");
        if (this.hasXVisualInfo()) {
            sb.append("0x").append(Long.toHexString(this.xVisualInfo.getVisualid()));
        }
        else {
            sb.append("----");
        }
        sb.append(": ");
        return super.toString(sb);
    }
}
