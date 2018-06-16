// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.opengl.util.stereo;

import java.util.List;
import com.jogamp.newt.Screen;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.util.MonitorModeUtil;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.newt.NewtFactory;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.opengl.util.stereo.StereoDevice;

public class StereoDeviceUtil
{
    public static MonitorDevice getMonitorDevice(final StereoDevice stereoDevice, final boolean b) {
        final PointImmutable position = stereoDevice.getPosition();
        final DimensionImmutable surfaceSize = stereoDevice.getSurfaceSize();
        final int requiredRotation = stereoDevice.getRequiredRotation();
        final Rectangle rectangle = new Rectangle(position.getX(), position.getY(), 128, 128);
        final Screen screen = NewtFactory.createScreen(NewtFactory.createDisplay(null), 0);
        screen.addReference();
        final MonitorDevice mainMonitor = screen.getMainMonitor(rectangle);
        System.err.println("StereoDevice Monitor: " + mainMonitor);
        final MonitorMode currentMode = mainMonitor.getCurrentMode();
        if (b && requiredRotation != currentMode.getRotation()) {
            System.err.println("StereoDevice Current Mode: " + currentMode + ", requires rotation: " + requiredRotation);
            DimensionImmutable dimensionImmutable;
            if (90 == requiredRotation || 270 == requiredRotation) {
                dimensionImmutable = new Dimension(surfaceSize.getHeight(), surfaceSize.getWidth());
            }
            else {
                dimensionImmutable = surfaceSize;
            }
            final List<MonitorMode> filterByRotation = MonitorModeUtil.filterByRotation(MonitorModeUtil.filterByResolution(mainMonitor.getSupportedModes(), dimensionImmutable), requiredRotation);
            if (filterByRotation.size() > 0) {
                final MonitorMode currentMode2 = filterByRotation.get(0);
                System.err.println("StereoDevice Set Mode: " + currentMode2);
                mainMonitor.setCurrentMode(currentMode2);
            }
            System.err.println("StereoDevice Post-Set Mode: " + mainMonitor.queryCurrentMode());
        }
        else {
            System.err.println("StereoDevice Keeps Mode: " + currentMode);
        }
        return mainMonitor;
    }
}
