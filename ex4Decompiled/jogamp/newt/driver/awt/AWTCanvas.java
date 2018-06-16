// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.awt;

import java.awt.Toolkit;
import java.security.AccessController;
import java.io.Serializable;
import java.security.PrivilegedAction;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.newt.Window;
import java.awt.Graphics;
import com.jogamp.nativewindow.NativeWindowException;
import java.lang.reflect.Method;
import com.jogamp.nativewindow.awt.JAWTWindow;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import java.awt.Canvas;

public class AWTCanvas extends Canvas
{
    private final WindowDriver driver;
    private final CapabilitiesImmutable capabilities;
    private final CapabilitiesChooser chooser;
    private final UpstreamScalable upstreamScale;
    private GraphicsConfiguration chosen;
    private volatile GraphicsDevice device;
    private volatile AWTGraphicsConfiguration awtConfig;
    private volatile JAWTWindow jawtWindow;
    private boolean displayConfigChanged;
    private static boolean disableBackgroundEraseInitialized;
    private static Method disableBackgroundEraseMethod;
    
    public AWTCanvas(final WindowDriver driver, final CapabilitiesImmutable capabilities, final CapabilitiesChooser chooser, final UpstreamScalable upstreamScale) {
        this.jawtWindow = null;
        this.displayConfigChanged = false;
        if (null == capabilities) {
            throw new NativeWindowException("Capabilities null");
        }
        if (null == driver) {
            throw new NativeWindowException("driver null");
        }
        this.driver = driver;
        this.capabilities = capabilities;
        this.chooser = chooser;
        this.upstreamScale = upstreamScale;
    }
    
    public AWTGraphicsConfiguration getAWTGraphicsConfiguration() {
        return this.awtConfig;
    }
    
    @Override
    public void update(final Graphics graphics) {
    }
    
    @Override
    public void paint(final Graphics graphics) {
    }
    
    public boolean hasDeviceChanged() {
        final boolean displayConfigChanged = this.displayConfigChanged;
        this.displayConfigChanged = false;
        return displayConfigChanged;
    }
    
    @Override
    public void addNotify() {
        this.disableBackgroundErase();
        this.awtConfig = chooseGraphicsConfiguration(this.capabilities, this.capabilities, this.chooser, this.device);
        if (Window.DEBUG_IMPLEMENTATION) {
            System.err.println(this.getThreadName() + ": AWTCanvas.addNotify.0: Created Config: " + this.awtConfig);
        }
        if (null == this.awtConfig) {
            throw new NativeWindowException("Error: NULL AWTGraphicsConfiguration");
        }
        this.chosen = this.awtConfig.getAWTGraphicsConfiguration();
        this.setAWTGraphicsConfiguration(this.awtConfig);
        super.addNotify();
        this.disableBackgroundErase();
        (this.jawtWindow = (JAWTWindow)NativeWindowFactory.getNativeWindow(this, this.awtConfig)).lockSurface();
        try {
            this.jawtWindow.setSurfaceScale(this.upstreamScale.getReqPixelScale());
            this.upstreamScale.setHasPixelScale(this.jawtWindow.getCurrentSurfaceScale(new float[2]));
        }
        finally {
            this.jawtWindow.unlockSurface();
        }
        final GraphicsConfiguration graphicsConfiguration = super.getGraphicsConfiguration();
        if (null != graphicsConfiguration) {
            this.device = graphicsConfiguration.getDevice();
        }
        this.driver.localCreate();
        if (Window.DEBUG_IMPLEMENTATION) {
            System.err.println(this.getThreadName() + ": AWTCanvas.addNotify.X");
        }
    }
    
    public NativeWindow getNativeWindow() {
        final JAWTWindow jawtWindow = this.jawtWindow;
        return (null != jawtWindow) ? jawtWindow : null;
    }
    
    public boolean isOffscreenLayerSurfaceEnabled() {
        return null != this.jawtWindow && this.jawtWindow.isOffscreenLayerSurfaceEnabled();
    }
    
    private void setAWTGraphicsConfiguration(final AWTGraphicsConfiguration awtGraphicsConfiguration) {
        this.awtConfig = awtGraphicsConfiguration;
        if (null != this.jawtWindow) {
            this.jawtWindow.setAWTGraphicsConfiguration(awtGraphicsConfiguration);
        }
    }
    
    @Override
    public void removeNotify() {
        if (Window.DEBUG_IMPLEMENTATION) {
            System.err.println(this.getThreadName() + ": AWTCanvas.removeNotify.0: Created Config: " + this.awtConfig);
        }
        try {
            this.driver.localDestroy();
        }
        finally {
            super.removeNotify();
        }
    }
    
    void dispose() {
        if (null != this.jawtWindow) {
            this.jawtWindow.destroy();
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println(this.getThreadName() + ": AWTCanvas.disposeJAWTWindowAndAWTDeviceOnEDT(): post JAWTWindow: " + this.jawtWindow);
            }
            this.jawtWindow = null;
        }
        if (null != this.awtConfig) {
            final AbstractGraphicsDevice device = this.awtConfig.getNativeGraphicsConfiguration().getScreen().getDevice();
            String string = null;
            if (Window.DEBUG_IMPLEMENTATION) {
                string = device.toString();
            }
            final boolean close = device.close();
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println(this.getThreadName() + ": AWTCanvas.dispose(): closed GraphicsDevice: " + string + ", result: " + close);
            }
        }
        this.awtConfig = null;
    }
    
    private String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    @Override
    public GraphicsConfiguration getGraphicsConfiguration() {
        final GraphicsConfiguration graphicsConfiguration = super.getGraphicsConfiguration();
        if (graphicsConfiguration != null && this.chosen != null && !this.chosen.equals(graphicsConfiguration)) {
            if (!this.chosen.getDevice().getIDstring().equals(graphicsConfiguration.getDevice().getIDstring())) {
                final AWTGraphicsConfiguration chooseGraphicsConfiguration = chooseGraphicsConfiguration(this.awtConfig.getChosenCapabilities(), this.awtConfig.getRequestedCapabilities(), this.chooser, graphicsConfiguration.getDevice());
                final GraphicsConfiguration chosen = (null != chooseGraphicsConfiguration) ? chooseGraphicsConfiguration.getAWTGraphicsConfiguration() : null;
                if (Window.DEBUG_IMPLEMENTATION) {
                    new Exception("Info: Call Stack: " + Thread.currentThread().getName()).printStackTrace();
                    System.err.println("Created Config (n): HAVE    GC " + this.chosen);
                    System.err.println("Created Config (n): THIS    GC " + graphicsConfiguration);
                    System.err.println("Created Config (n): Choosen GC " + chosen);
                    System.err.println("Created Config (n): HAVE    CF " + this.awtConfig);
                    System.err.println("Created Config (n): Choosen CF " + chooseGraphicsConfiguration);
                    System.err.println("Created Config (n): EQUALS CAPS " + chooseGraphicsConfiguration.getChosenCapabilities().equals(this.awtConfig.getChosenCapabilities()));
                }
                if (chosen != null) {
                    this.chosen = chosen;
                    if (!chooseGraphicsConfiguration.getChosenCapabilities().equals(this.awtConfig.getChosenCapabilities())) {
                        this.displayConfigChanged = true;
                    }
                    this.setAWTGraphicsConfiguration(chooseGraphicsConfiguration);
                }
            }
            return this.chosen;
        }
        if (graphicsConfiguration == null) {
            return this.chosen;
        }
        return graphicsConfiguration;
    }
    
    private static AWTGraphicsConfiguration chooseGraphicsConfiguration(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final GraphicsDevice graphicsDevice) {
        final AWTGraphicsConfiguration awtGraphicsConfiguration = (AWTGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(AWTGraphicsDevice.class, capabilitiesImmutable.getClass()).chooseGraphicsConfiguration(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, (null != graphicsDevice) ? AWTGraphicsScreen.createScreenDevice(graphicsDevice, 0) : AWTGraphicsScreen.createDefault(), 0);
        if (awtGraphicsConfiguration == null) {
            throw new NativeWindowException("Error: Couldn't fetch AWTGraphicsConfiguration");
        }
        return awtGraphicsConfiguration;
    }
    
    private void disableBackgroundErase() {
        if (!AWTCanvas.disableBackgroundEraseInitialized) {
            try {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        try {
                            Serializable s = AWTCanvas.this.getToolkit().getClass();
                            while (s != null && AWTCanvas.disableBackgroundEraseMethod == null) {
                                try {
                                    AWTCanvas.disableBackgroundEraseMethod = ((Class)s).getDeclaredMethod("disableBackgroundErase", Canvas.class);
                                    AWTCanvas.disableBackgroundEraseMethod.setAccessible(true);
                                }
                                catch (Exception ex) {
                                    s = ((Class<? extends Toolkit>)s).getSuperclass();
                                }
                            }
                        }
                        catch (Exception ex2) {}
                        return null;
                    }
                });
            }
            catch (Exception ex2) {}
            AWTCanvas.disableBackgroundEraseInitialized = true;
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("AWTCanvas: TK disableBackgroundErase method found: " + (null != AWTCanvas.disableBackgroundEraseMethod));
            }
        }
        if (AWTCanvas.disableBackgroundEraseMethod != null) {
            Object o = null;
            try {
                AWTCanvas.disableBackgroundEraseMethod.invoke(this.getToolkit(), this);
            }
            catch (Exception ex) {
                o = ex;
            }
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("AWTCanvas: TK disableBackgroundErase error: " + o);
            }
        }
    }
    
    public interface UpstreamScalable
    {
        float[] getReqPixelScale();
        
        void setHasPixelScale(final float[] p0);
    }
}
