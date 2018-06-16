// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.x11;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.lang.reflect.Method;

public class X11SunJDKReflection
{
    private static Class<?> x11GraphicsDeviceClass;
    private static Method x11GraphicsDeviceGetDisplayMethod;
    private static Class<?> x11GraphicsConfigClass;
    private static Method x11GraphicsConfigGetVisualMethod;
    private static boolean initialized;
    
    public static long graphicsDeviceGetDisplay(final GraphicsDevice graphicsDevice) {
        if (!X11SunJDKReflection.initialized) {
            return 0L;
        }
        try {
            return (long)X11SunJDKReflection.x11GraphicsDeviceGetDisplayMethod.invoke(graphicsDevice, (Object[])null);
        }
        catch (Exception ex) {
            return 0L;
        }
    }
    
    public static int graphicsConfigurationGetVisualID(final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        try {
            if (abstractGraphicsConfiguration instanceof AWTGraphicsConfiguration) {
                return graphicsConfigurationGetVisualID(((AWTGraphicsConfiguration)abstractGraphicsConfiguration).getAWTGraphicsConfiguration());
            }
            return 0;
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public static int graphicsConfigurationGetVisualID(final GraphicsConfiguration graphicsConfiguration) {
        if (!X11SunJDKReflection.initialized) {
            return 0;
        }
        try {
            return (int)X11SunJDKReflection.x11GraphicsConfigGetVisualMethod.invoke(graphicsConfiguration, (Object[])null);
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    X11SunJDKReflection.x11GraphicsDeviceClass = Class.forName("sun.awt.X11GraphicsDevice");
                    X11SunJDKReflection.x11GraphicsDeviceGetDisplayMethod = X11SunJDKReflection.x11GraphicsDeviceClass.getDeclaredMethod("getDisplay", (Class[])new Class[0]);
                    X11SunJDKReflection.x11GraphicsDeviceGetDisplayMethod.setAccessible(true);
                    X11SunJDKReflection.x11GraphicsConfigClass = Class.forName("sun.awt.X11GraphicsConfig");
                    X11SunJDKReflection.x11GraphicsConfigGetVisualMethod = X11SunJDKReflection.x11GraphicsConfigClass.getDeclaredMethod("getVisual", (Class[])new Class[0]);
                    X11SunJDKReflection.x11GraphicsConfigGetVisualMethod.setAccessible(true);
                    X11SunJDKReflection.initialized = true;
                }
                catch (Exception ex) {}
                return null;
            }
        });
    }
}
