// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import com.jogamp.nativewindow.util.RectangleImmutable;
import java.util.List;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.nativewindow.util.DimensionImmutable;

public abstract class MonitorDevice
{
    protected final Screen screen;
    protected final int nativeId;
    protected final DimensionImmutable sizeMM;
    protected final MonitorMode originalMode;
    protected final ArrayHashSet<MonitorMode> supportedModes;
    protected final float[] pixelScale;
    protected final Rectangle viewportPU;
    protected final Rectangle viewportWU;
    protected boolean isClone;
    protected boolean isPrimary;
    protected MonitorMode currentMode;
    protected boolean modeChanged;
    
    protected MonitorDevice(final Screen screen, final int nativeId, final boolean isClone, final boolean isPrimary, final DimensionImmutable sizeMM, final MonitorMode monitorMode, final float[] array, final Rectangle viewportPU, final Rectangle viewportWU, final ArrayHashSet<MonitorMode> supportedModes) {
        this.screen = screen;
        this.nativeId = nativeId;
        this.sizeMM = sizeMM;
        this.originalMode = monitorMode;
        this.supportedModes = supportedModes;
        float[] pixelScale;
        if (null != array) {
            pixelScale = array;
        }
        else {
            final float[] array2 = pixelScale = new float[2];
            array2[1] = (array2[0] = 1.0f);
        }
        this.pixelScale = pixelScale;
        this.viewportPU = viewportPU;
        this.viewportWU = viewportWU;
        this.isClone = isClone;
        this.isPrimary = isPrimary;
        this.currentMode = monitorMode;
        this.modeChanged = false;
    }
    
    public final Screen getScreen() {
        return this.screen;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o || (o instanceof MonitorDevice && ((MonitorDevice)o).nativeId == this.nativeId);
    }
    
    @Override
    public final int hashCode() {
        return this.nativeId;
    }
    
    public final int getId() {
        return this.nativeId;
    }
    
    public final boolean isClone() {
        return this.isClone;
    }
    
    public final boolean isPrimary() {
        return this.isPrimary;
    }
    
    public final DimensionImmutable getSizeMM() {
        return this.sizeMM;
    }
    
    public final float[] getPixelsPerMM(final float[] array) {
        return this.getPixelsPerMM(this.getCurrentMode(), array);
    }
    
    public final float[] getPixelsPerMM(final MonitorMode monitorMode, final float[] array) {
        final DimensionImmutable sizeMM = this.getSizeMM();
        final DimensionImmutable resolution = monitorMode.getSurfaceSize().getResolution();
        array[0] = resolution.getWidth() / sizeMM.getWidth();
        array[1] = resolution.getHeight() / sizeMM.getHeight();
        return array;
    }
    
    public final MonitorMode getOriginalMode() {
        return this.originalMode;
    }
    
    public final List<MonitorMode> getSupportedModes() {
        return this.supportedModes.getData();
    }
    
    public final RectangleImmutable getViewport() {
        return this.viewportPU;
    }
    
    public final RectangleImmutable getViewportInWindowUnits() {
        return this.viewportWU;
    }
    
    public float[] getPixelScale(final float[] array) {
        System.arraycopy(this.pixelScale, 0, array, 0, 2);
        return array;
    }
    
    public final boolean contains(final int n, final int n2) {
        return n >= this.viewportPU.getX() && n < this.viewportPU.getX() + this.viewportPU.getWidth() && n2 >= this.viewportPU.getY() && n2 < this.viewportPU.getY() + this.viewportPU.getHeight();
    }
    
    public static void unionOfViewports(final Rectangle rectangle, final Rectangle rectangle2, final List<MonitorDevice> list) {
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int min3 = Integer.MAX_VALUE;
        int min4 = Integer.MAX_VALUE;
        int max3 = Integer.MIN_VALUE;
        int max4 = Integer.MIN_VALUE;
        for (int i = list.size() - 1; i >= 0; --i) {
            if (null != rectangle) {
                final RectangleImmutable viewport = list.get(i).getViewport();
                min = Math.min(min, viewport.getX());
                max = Math.max(max, viewport.getX() + viewport.getWidth());
                min2 = Math.min(min2, viewport.getY());
                max2 = Math.max(max2, viewport.getY() + viewport.getHeight());
            }
            if (null != rectangle2) {
                final RectangleImmutable viewportInWindowUnits = list.get(i).getViewportInWindowUnits();
                min3 = Math.min(min3, viewportInWindowUnits.getX());
                max3 = Math.max(max3, viewportInWindowUnits.getX() + viewportInWindowUnits.getWidth());
                min4 = Math.min(min4, viewportInWindowUnits.getY());
                max4 = Math.max(max4, viewportInWindowUnits.getY() + viewportInWindowUnits.getHeight());
            }
        }
        if (null != rectangle) {
            rectangle.set(min, min2, max - min, max2 - min2);
        }
        if (null != rectangle2) {
            rectangle2.set(min3, min4, max3 - min3, max4 - min4);
        }
    }
    
    public final boolean isOriginalMode() {
        return this.currentMode.hashCode() == this.originalMode.hashCode();
    }
    
    public final boolean isModeChangedByUs() {
        return this.modeChanged && !this.isOriginalMode();
    }
    
    public final MonitorMode getCurrentMode() {
        return this.currentMode;
    }
    
    public abstract MonitorMode queryCurrentMode() throws IllegalStateException;
    
    public abstract boolean setCurrentMode(final MonitorMode p0) throws IllegalStateException;
    
    @Override
    public String toString() {
        boolean b = false;
        final StringBuilder sb = new StringBuilder();
        sb.append("Monitor[Id ").append(Display.toHexString(this.nativeId)).append(" [");
        if (this.isClone()) {
            sb.append("clone");
            b = true;
        }
        if (this.isPrimary()) {
            if (b) {
                sb.append(", ");
            }
            sb.append("primary");
        }
        sb.append("], ").append(this.sizeMM).append(" mm, pixelScale [").append(this.pixelScale[0]).append(", ").append(this.pixelScale[1]).append("], viewport ").append(this.viewportPU).append(" [pixels], ").append(this.viewportWU).append(" [window], orig ").append(this.originalMode).append(", curr ").append(this.currentMode).append(", modeChanged ").append(this.modeChanged).append(", modeCount ").append(this.supportedModes.size()).append("]");
        return sb.toString();
    }
}
