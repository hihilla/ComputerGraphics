// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import java.lang.reflect.Constructor;
import com.jogamp.nativewindow.ToolkitLock;
import com.jogamp.nativewindow.NativeWindowFactory;

public class NativeWindowFactoryImpl extends NativeWindowFactory
{
    private static final ToolkitLock nullToolkitLock;
    private Constructor<?> nativeWindowConstructor;
    
    public NativeWindowFactoryImpl() {
        this.nativeWindowConstructor = null;
    }
    
    public static ToolkitLock getNullToolkitLock() {
        return NativeWindowFactoryImpl.nullToolkitLock;
    }
    
    @Override
    protected NativeWindow getNativeWindowImpl(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) throws IllegalArgumentException {
        if (o instanceof NativeWindow) {
            return (NativeWindow)o;
        }
        if (null == abstractGraphicsConfiguration) {
            throw new IllegalArgumentException("AbstractGraphicsConfiguration is null with a non NativeWindow object");
        }
        if (NativeWindowFactory.isAWTAvailable() && ReflectionUtil.instanceOf(o, "java.awt.Component")) {
            return this.getAWTNativeWindow(o, abstractGraphicsConfiguration);
        }
        throw new IllegalArgumentException("Target window object type " + o.getClass().getName() + " is unsupported; expected " + "com.jogamp.nativewindow.NativeWindow or " + "java.awt.Component");
    }
    
    private NativeWindow getAWTNativeWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        if (this.nativeWindowConstructor == null) {
            try {
                final String nativeWindowType = NativeWindowFactory.getNativeWindowType(true);
                String s;
                if (NativeWindowFactoryImpl.TYPE_WINDOWS == nativeWindowType) {
                    s = "jogamp.nativewindow.jawt.windows.WindowsJAWTWindow";
                }
                else if (NativeWindowFactoryImpl.TYPE_MACOSX == nativeWindowType) {
                    s = "jogamp.nativewindow.jawt.macosx.MacOSXJAWTWindow";
                }
                else {
                    if (NativeWindowFactoryImpl.TYPE_X11 != nativeWindowType) {
                        throw new IllegalArgumentException("Native windowing type " + nativeWindowType + " (custom) not yet supported, platform reported native windowing type: " + NativeWindowFactory.getNativeWindowType(false));
                    }
                    s = "jogamp.nativewindow.jawt.x11.X11JAWTWindow";
                }
                this.nativeWindowConstructor = ReflectionUtil.getConstructor(s, new Class[] { Object.class, AbstractGraphicsConfiguration.class }, true, this.getClass().getClassLoader());
            }
            catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        try {
            return (NativeWindow)this.nativeWindowConstructor.newInstance(o, abstractGraphicsConfiguration);
        }
        catch (Exception ex2) {
            throw new IllegalArgumentException(ex2);
        }
    }
    
    static {
        nullToolkitLock = new NullToolkitLock();
    }
}
