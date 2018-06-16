// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.opengl.GLException;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLProfile;
import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;
import com.jogamp.opengl.GLCapabilities;

public class WGLGLCapabilities extends GLCapabilities
{
    private final PIXELFORMATDESCRIPTOR pfd;
    private final int pfdID;
    private int arb_pixelformat;
    
    public WGLGLCapabilities(final PIXELFORMATDESCRIPTOR pfd, final int pfdID, final GLProfile glProfile) {
        super(glProfile);
        this.pfd = pfd;
        this.pfdID = pfdID;
        this.arb_pixelformat = 0;
    }
    
    public boolean setValuesByGDI() {
        this.arb_pixelformat = -1;
        this.setRedBits(this.pfd.getCRedBits());
        this.setGreenBits(this.pfd.getCGreenBits());
        this.setBlueBits(this.pfd.getCBlueBits());
        this.setAlphaBits(this.pfd.getCAlphaBits());
        this.setAccumRedBits(this.pfd.getCAccumRedBits());
        this.setAccumGreenBits(this.pfd.getCAccumGreenBits());
        this.setAccumBlueBits(this.pfd.getCAccumBlueBits());
        this.setAccumAlphaBits(this.pfd.getCAccumAlphaBits());
        this.setDepthBits(this.pfd.getCDepthBits());
        this.setStencilBits(this.pfd.getCStencilBits());
        final int dwFlags = this.pfd.getDwFlags();
        this.setDoubleBuffered((dwFlags & 0x1) != 0x0);
        this.setStereo((dwFlags & 0x2) != 0x0);
        this.setHardwareAccelerated((dwFlags & 0x40) == 0x0 || (dwFlags & 0x1000) != 0x0);
        return true;
    }
    
    public static final String PFD2String(final PIXELFORMATDESCRIPTOR pixelformatdescriptor, final int n) {
        final int dwFlags = pixelformatdescriptor.getDwFlags();
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        if (0x0 != (0x4 & dwFlags)) {
            n2 = 1;
            sb.append("window");
        }
        if (0x0 != (0x8 & dwFlags)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            n2 = 1;
            sb.append("bitmap");
        }
        if (0x0 != (0x20 & dwFlags)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            n2 = 1;
            sb.append("opengl");
        }
        if ((0x1 & dwFlags) != 0x0) {
            if (n2 != 0) {
                sb.append(", ");
            }
            n2 = 1;
            sb.append("dblbuf");
        }
        if (0x0 != (0x2 & dwFlags)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            n2 = 1;
            sb.append("stereo");
        }
        if (0x0 == (0x40 & dwFlags) || 0x0 == (0x1000 & dwFlags)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("hw-accel");
        }
        return "PFD[id = " + n + " (0x" + Integer.toHexString(n) + "), colorBits " + pixelformatdescriptor.getCColorBits() + ", rgba " + pixelformatdescriptor.getCRedBits() + "/" + pixelformatdescriptor.getCGreenBits() + "/" + pixelformatdescriptor.getCBlueBits() + "/" + pixelformatdescriptor.getCAlphaBits() + ", accum-rgba " + pixelformatdescriptor.getCAccumRedBits() + "/" + pixelformatdescriptor.getCAccumGreenBits() + "/" + pixelformatdescriptor.getCAccumBlueBits() + "/" + pixelformatdescriptor.getCAccumAlphaBits() + ", dp/st/ms: " + pixelformatdescriptor.getCDepthBits() + "/" + pixelformatdescriptor.getCStencilBits() + "/" + "0" + ", flags: " + sb.toString();
    }
    
    public boolean setValuesByARB(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.arb_pixelformat = 1;
        int alphaBits = 0;
        for (int i = 0; i < n; ++i) {
            final int value = intBuffer.get(i);
            final int value2 = intBuffer2.get(i);
            switch (value) {
                case 8193:
                case 8194:
                case 8237: {
                    break;
                }
                case 8195: {
                    this.setHardwareAccelerated(value2 == 8231);
                    break;
                }
                case 8208: {
                    if (value2 != 1) {
                        return false;
                    }
                    break;
                }
                case 8226: {
                    this.setDepthBits(value2);
                    break;
                }
                case 8227: {
                    this.setStencilBits(value2);
                    break;
                }
                case 8209: {
                    this.setDoubleBuffered(value2 == 1);
                    break;
                }
                case 8210: {
                    this.setStereo(value2 == 1);
                    break;
                }
                case 8211: {
                    if (value2 == 8236) {
                        return false;
                    }
                    if (value2 == 8608) {
                        return false;
                    }
                    break;
                }
                case 8213: {
                    this.setRedBits(value2);
                    break;
                }
                case 8215: {
                    this.setGreenBits(value2);
                    break;
                }
                case 8217: {
                    this.setBlueBits(value2);
                    break;
                }
                case 8219: {
                    alphaBits = value2;
                    break;
                }
                case 8222: {
                    this.setAccumRedBits(value2);
                    break;
                }
                case 8223: {
                    this.setAccumGreenBits(value2);
                    break;
                }
                case 8224: {
                    this.setAccumBlueBits(value2);
                    break;
                }
                case 8225: {
                    this.setAccumAlphaBits(value2);
                    break;
                }
                case 8257: {
                    this.setSampleBuffers(value2 != 0);
                    break;
                }
                case 8258: {
                    this.setNumSamples(value2);
                    break;
                }
                default: {
                    throw new GLException("Unknown pixel format attribute " + value);
                }
            }
        }
        this.setAlphaBits(alphaBits);
        return true;
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
    
    public final PIXELFORMATDESCRIPTOR getPFD() {
        return this.pfd;
    }
    
    public final int getPFDID() {
        return this.pfdID;
    }
    
    public final boolean isSetByARB() {
        return 0 < this.arb_pixelformat;
    }
    
    public final boolean isSetByGDI() {
        return 0 > this.arb_pixelformat;
    }
    
    public final boolean isSet() {
        return 0 != this.arb_pixelformat;
    }
    
    @Override
    public final int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        switch (vidType) {
            case INTRINSIC:
            case NATIVE:
            case WIN32_PFD: {
                return this.getPFDID();
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
        sb.append("wgl vid ").append(this.pfdID).append(" ");
        switch (this.arb_pixelformat) {
            case -1: {
                sb.append("gdi");
                break;
            }
            case 0: {
                sb.append("nop");
                break;
            }
            case 1: {
                sb.append("arb");
                break;
            }
            default: {
                throw new InternalError("invalid arb_pixelformat: " + this.arb_pixelformat);
            }
        }
        sb.append(": ");
        return super.toString(sb);
    }
}
