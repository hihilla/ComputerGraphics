// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.image.ColorModel;
import jogamp.nativewindow.Debug;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.Capabilities;
import com.jogamp.nativewindow.NativeWindowException;
import java.awt.Component;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.DefaultGraphicsConfiguration;

public class AWTGraphicsConfiguration extends DefaultGraphicsConfiguration implements Cloneable
{
    private final GraphicsConfiguration config;
    AbstractGraphicsConfiguration encapsulated;
    
    public AWTGraphicsConfiguration(final AWTGraphicsScreen awtGraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final GraphicsConfiguration config, final AbstractGraphicsConfiguration encapsulated) {
        super(awtGraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
        this.config = config;
        this.encapsulated = encapsulated;
    }
    
    private AWTGraphicsConfiguration(final AWTGraphicsScreen awtGraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final GraphicsConfiguration config) {
        super(awtGraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
        this.config = config;
        this.encapsulated = null;
    }
    
    public static AWTGraphicsConfiguration create(final Component component, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2) {
        if (null == component) {
            throw new IllegalArgumentException("Null AWT Component");
        }
        final GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
        if (null == graphicsConfiguration) {
            throw new NativeWindowException("Null AWT GraphicsConfiguration @ " + component);
        }
        return create(graphicsConfiguration, capabilitiesImmutable, capabilitiesImmutable2);
    }
    
    public static AWTGraphicsConfiguration create(final GraphicsConfiguration graphicsConfiguration, CapabilitiesImmutable setupCapabilitiesRGBABits, CapabilitiesImmutable capabilitiesImmutable) {
        if (null == graphicsConfiguration) {
            throw new IllegalArgumentException("Null AWT GraphicsConfiguration");
        }
        final GraphicsDevice device = graphicsConfiguration.getDevice();
        if (null == device) {
            throw new NativeWindowException("Null AWT GraphicsDevice @ " + graphicsConfiguration);
        }
        final AWTGraphicsDevice awtGraphicsDevice = new AWTGraphicsDevice(device, 0);
        final AWTGraphicsScreen awtGraphicsScreen = new AWTGraphicsScreen(awtGraphicsDevice);
        if (null == capabilitiesImmutable) {
            capabilitiesImmutable = new Capabilities();
        }
        if (null == setupCapabilitiesRGBABits) {
            setupCapabilitiesRGBABits = setupCapabilitiesRGBABits(capabilitiesImmutable, graphicsConfiguration);
        }
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = GraphicsConfigurationFactory.getFactory(awtGraphicsDevice, setupCapabilitiesRGBABits).chooseGraphicsConfiguration(setupCapabilitiesRGBABits, capabilitiesImmutable, null, awtGraphicsScreen, 0);
        if (chooseGraphicsConfiguration instanceof AWTGraphicsConfiguration) {
            return (AWTGraphicsConfiguration)chooseGraphicsConfiguration;
        }
        return new AWTGraphicsConfiguration(awtGraphicsScreen, setupCapabilitiesRGBABits, capabilitiesImmutable, graphicsConfiguration);
    }
    
    public void setChosenCapabilities(final CapabilitiesImmutable chosenCapabilities) {
        super.setChosenCapabilities(chosenCapabilities);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public GraphicsConfiguration getAWTGraphicsConfiguration() {
        return this.config;
    }
    
    @Override
    public AbstractGraphicsConfiguration getNativeGraphicsConfiguration() {
        return (null != this.encapsulated) ? this.encapsulated : this;
    }
    
    public static CapabilitiesImmutable setupCapabilitiesRGBABits(final CapabilitiesImmutable capabilitiesImmutable, final GraphicsConfiguration graphicsConfiguration) {
        final Capabilities capabilities = (Capabilities)capabilitiesImmutable.cloneMutable();
        final ColorModel colorModel = graphicsConfiguration.getColorModel();
        if (null == colorModel) {
            throw new NativeWindowException("Could not determine AWT ColorModel");
        }
        final int pixelSize = colorModel.getPixelSize();
        int n = 0;
        final int[] componentSize = colorModel.getComponentSize();
        if (componentSize.length >= 3) {
            capabilities.setRedBits(componentSize[0]);
            final int n2 = n + componentSize[0];
            capabilities.setGreenBits(componentSize[1]);
            final int n3 = n2 + componentSize[1];
            capabilities.setBlueBits(componentSize[2]);
            n = n3 + componentSize[2];
        }
        if (componentSize.length >= 4) {
            capabilities.setAlphaBits(componentSize[3]);
            n += componentSize[3];
        }
        else {
            capabilities.setAlphaBits(0);
        }
        if (Debug.debugAll() && pixelSize != n) {
            System.err.println("AWT Colormodel bits per components/pixel mismatch: " + n + " != " + pixelSize);
        }
        return capabilities;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getScreen() + ",\n\tchosen    " + this.capabilitiesChosen + ",\n\trequested " + this.capabilitiesRequested + ",\n\t" + this.config + ",\n\tencapsulated " + this.encapsulated + "]";
    }
}
