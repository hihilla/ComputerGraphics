// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.windows;

import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.lang.reflect.Method;

public class Win32SunJDKReflection
{
    private static Class win32GraphicsDeviceClass;
    private static Class win32GraphicsConfigClass;
    private static Method win32GraphicsConfigGetConfigMethod;
    private static Method win32GraphicsConfigGetVisualMethod;
    private static boolean initted;
    
    public static GraphicsConfiguration graphicsConfigurationGet(final GraphicsDevice graphicsDevice, final int n) {
        if (!Win32SunJDKReflection.initted) {
            return null;
        }
        try {
            return (GraphicsConfiguration)Win32SunJDKReflection.win32GraphicsConfigGetConfigMethod.invoke(null, graphicsDevice, n);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static int graphicsConfigurationGetPixelFormatID(final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        try {
            if (abstractGraphicsConfiguration instanceof AWTGraphicsConfiguration) {
                return graphicsConfigurationGetPixelFormatID(((AWTGraphicsConfiguration)abstractGraphicsConfiguration).getAWTGraphicsConfiguration());
            }
            return 0;
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public static int graphicsConfigurationGetPixelFormatID(final GraphicsConfiguration graphicsConfiguration) {
        if (!Win32SunJDKReflection.initted) {
            return 0;
        }
        try {
            return (int)Win32SunJDKReflection.win32GraphicsConfigGetVisualMethod.invoke(graphicsConfiguration, (Object[])null);
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    Win32SunJDKReflection.win32GraphicsDeviceClass = Class.forName("sun.awt.Win32GraphicsDevice");
                    Win32SunJDKReflection.win32GraphicsConfigClass = Class.forName("sun.awt.Win32GraphicsConfig");
                    Win32SunJDKReflection.win32GraphicsConfigGetConfigMethod = Win32SunJDKReflection.win32GraphicsConfigClass.getDeclaredMethod("getConfig", Win32SunJDKReflection.win32GraphicsDeviceClass, Integer.TYPE);
                    Win32SunJDKReflection.win32GraphicsConfigGetConfigMethod.setAccessible(true);
                    Win32SunJDKReflection.win32GraphicsConfigGetVisualMethod = Win32SunJDKReflection.win32GraphicsConfigClass.getDeclaredMethod("getVisual", (Class[])new Class[0]);
                    Win32SunJDKReflection.win32GraphicsConfigGetVisualMethod.setAccessible(true);
                    Win32SunJDKReflection.initted = true;
                }
                catch (Exception ex) {}
                return null;
            }
        });
    }
}
