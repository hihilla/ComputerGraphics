// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import com.jogamp.nativewindow.AbstractGraphicsScreen;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;

public class AWTGraphicsScreen extends DefaultGraphicsScreen implements Cloneable
{
    public AWTGraphicsScreen(final AWTGraphicsDevice awtGraphicsDevice) {
        super(awtGraphicsDevice, findScreenIndex(awtGraphicsDevice.getGraphicsDevice()));
    }
    
    public static GraphicsDevice getScreenDevice(final int n) {
        if (n < 0) {
            return null;
        }
        final GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (n < screenDevices.length) {
            return screenDevices[n];
        }
        return null;
    }
    
    public static int findScreenIndex(final GraphicsDevice graphicsDevice) {
        if (null == graphicsDevice) {
            return -1;
        }
        final GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < screenDevices.length; ++i) {
            if (screenDevices[i] == graphicsDevice) {
                return i;
            }
        }
        return -1;
    }
    
    public static AbstractGraphicsScreen createScreenDevice(final GraphicsDevice graphicsDevice, final int n) {
        return new AWTGraphicsScreen(new AWTGraphicsDevice(graphicsDevice, n));
    }
    
    public static AbstractGraphicsScreen createScreenDevice(final int n, final int n2) {
        return createScreenDevice(getScreenDevice(n), n2);
    }
    
    public static AbstractGraphicsScreen createDefault() {
        return new AWTGraphicsScreen(AWTGraphicsDevice.createDefault());
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
}
