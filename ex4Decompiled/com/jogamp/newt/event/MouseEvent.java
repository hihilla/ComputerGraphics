// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import java.util.Arrays;

public class MouseEvent extends InputEvent
{
    public static final short BUTTON1 = 1;
    public static final short BUTTON2 = 2;
    public static final short BUTTON3 = 3;
    public static final short BUTTON4 = 4;
    public static final short BUTTON5 = 5;
    public static final short BUTTON6 = 6;
    public static final short BUTTON7 = 7;
    public static final short BUTTON8 = 8;
    public static final short BUTTON9 = 9;
    public static final short BUTTON_COUNT = 16;
    private final PointerType[] pointerType;
    private final short[] pointerID;
    private final int[] x;
    private final int[] y;
    private final float[] pressure;
    private final short clickCount;
    private final short button;
    private final float[] rotationXYZ;
    private final float rotationScale;
    private final float maxPressure;
    private static final float[] constMousePressure0;
    private static final float[] constMousePressure1;
    private static final PointerType[] constMousePointerTypes;
    public static final short EVENT_MOUSE_CLICKED = 200;
    public static final short EVENT_MOUSE_ENTERED = 201;
    public static final short EVENT_MOUSE_EXITED = 202;
    public static final short EVENT_MOUSE_PRESSED = 203;
    public static final short EVENT_MOUSE_RELEASED = 204;
    public static final short EVENT_MOUSE_MOVED = 205;
    public static final short EVENT_MOUSE_DRAGGED = 206;
    public static final short EVENT_MOUSE_WHEEL_MOVED = 207;
    
    public static final float[] getRotationXYZ(final float n, final int n2) {
        final float[] array = { 0.0f, 0.0f, 0.0f };
        if (0x0 != (n2 & 0x1)) {
            array[0] = n;
        }
        else {
            array[1] = n;
        }
        return array;
    }
    
    public static final short getClickTimeout() {
        return 300;
    }
    
    public MouseEvent(final short n, final Object o, final long n2, final int n3, final int n4, final int n5, final short clickCount, final short button, final float[] rotationXYZ, final float rotationScale) {
        super(n, o, n2, n3);
        this.x = new int[] { n4 };
        this.y = new int[] { n5 };
        switch (n) {
            case 200:
            case 203:
            case 206: {
                this.pressure = MouseEvent.constMousePressure1;
                break;
            }
            default: {
                this.pressure = MouseEvent.constMousePressure0;
                break;
            }
        }
        this.maxPressure = 1.0f;
        this.pointerID = new short[] { 0 };
        this.clickCount = clickCount;
        this.button = button;
        this.rotationXYZ = rotationXYZ;
        this.rotationScale = rotationScale;
        this.pointerType = MouseEvent.constMousePointerTypes;
    }
    
    public MouseEvent(final short n, final Object o, final long n2, final int n3, final PointerType[] pointerType, final short[] pointerID, final int[] x, final int[] y, final float[] pressure, final float maxPressure, final short button, final short clickCount, final float[] rotationXYZ, final float rotationScale) {
        super(n, o, n2, n3);
        this.x = x;
        this.y = y;
        final int length = pointerType.length;
        if (length != pointerID.length || length != x.length || length != y.length || length != pressure.length) {
            throw new IllegalArgumentException("All multiple pointer arrays must be of same size");
        }
        if (0.0f >= maxPressure) {
            throw new IllegalArgumentException("maxPressure must be > 0.0f");
        }
        this.pressure = pressure;
        this.maxPressure = maxPressure;
        this.pointerID = pointerID;
        this.clickCount = clickCount;
        this.button = button;
        this.rotationXYZ = rotationXYZ;
        this.rotationScale = rotationScale;
        this.pointerType = pointerType;
    }
    
    public final MouseEvent createVariant(final short n) {
        return new MouseEvent(n, this.source, this.getWhen(), this.getModifiers(), this.pointerType, this.pointerID, this.x, this.y, this.pressure, this.maxPressure, this.button, this.clickCount, this.rotationXYZ, this.rotationScale);
    }
    
    public final int getPointerCount() {
        return this.pointerType.length;
    }
    
    public final PointerType getPointerType(final int n) {
        if (0 > n || n >= this.pointerType.length) {
            return null;
        }
        return this.pointerType[n];
    }
    
    public final PointerType[] getAllPointerTypes() {
        return this.pointerType;
    }
    
    public final short getPointerId(final int n) {
        if (0 > n || n >= this.pointerID.length) {
            return -1;
        }
        return this.pointerID[n];
    }
    
    public final int getPointerIdx(final short n) {
        if (n >= 0) {
            for (int i = this.pointerID.length - 1; i >= 0; --i) {
                if (this.pointerID[i] == n) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public final short[] getAllPointerIDs() {
        return this.pointerID;
    }
    
    public final short getButton() {
        return this.button;
    }
    
    public final short getClickCount() {
        return this.clickCount;
    }
    
    public final int getX() {
        return this.x[0];
    }
    
    public final int getY() {
        return this.y[0];
    }
    
    public final int getX(final int n) {
        return this.x[n];
    }
    
    public final int getY(final int n) {
        return this.y[n];
    }
    
    public final int[] getAllX() {
        return this.x;
    }
    
    public final int[] getAllY() {
        return this.y;
    }
    
    public final float getPressure(final boolean b) {
        return b ? (this.pressure[0] / this.maxPressure) : this.pressure[0];
    }
    
    public final float getPressure(final int n, final boolean b) {
        return b ? (this.pressure[n] / this.maxPressure) : this.pressure[n];
    }
    
    public final float[] getAllPressures() {
        return this.pressure;
    }
    
    public final float getMaxPressure() {
        return this.maxPressure;
    }
    
    public final float[] getRotation() {
        return this.rotationXYZ;
    }
    
    public final float getRotationScale() {
        return this.rotationScale;
    }
    
    @Override
    public final String toString() {
        return this.toString(null).toString();
    }
    
    @Override
    public final StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("MouseEvent[").append(getEventTypeString(this.getEventType())).append(", ").append(Arrays.toString(this.x)).append("/").append(Arrays.toString(this.y)).append(", button ").append(this.button).append(", count ").append(this.clickCount).append(", rotation [").append(this.rotationXYZ[0]).append(", ").append(this.rotationXYZ[1]).append(", ").append(this.rotationXYZ[2]).append("] * ").append(this.rotationScale);
        if (this.pointerID.length > 0) {
            sb.append(", pointer<").append(this.pointerID.length).append(">[");
            for (int i = 0; i < this.pointerID.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.pointerID[i]).append("/").append(this.pointerType[i]).append(": ").append(this.x[i]).append("/").append(this.y[i]).append(", ").append("p[").append(this.pressure[i]).append("/").append(this.maxPressure).append("=").append(this.pressure[i] / this.maxPressure).append("]");
            }
            sb.append("]");
        }
        sb.append(", ");
        return super.toString(sb).append("]");
    }
    
    public static String getEventTypeString(final short n) {
        switch (n) {
            case 200: {
                return "EVENT_MOUSE_CLICKED";
            }
            case 201: {
                return "EVENT_MOUSE_ENTERED";
            }
            case 202: {
                return "EVENT_MOUSE_EXITED";
            }
            case 203: {
                return "EVENT_MOUSE_PRESSED";
            }
            case 204: {
                return "EVENT_MOUSE_RELEASED";
            }
            case 205: {
                return "EVENT_MOUSE_MOVED";
            }
            case 206: {
                return "EVENT_MOUSE_DRAGGED";
            }
            case 207: {
                return "EVENT_MOUSE_WHEEL_MOVED";
            }
            default: {
                return "unknown (" + n + ")";
            }
        }
    }
    
    static {
        constMousePressure0 = new float[] { 0.0f };
        constMousePressure1 = new float[] { 1.0f };
        constMousePointerTypes = new PointerType[] { PointerType.Mouse };
    }
    
    public enum PointerClass implements InputClass
    {
        Offscreen, 
        Onscreen, 
        Undefined;
    }
    
    public enum PointerType implements InputType
    {
        Mouse(PointerClass.Offscreen), 
        TouchPad(PointerClass.Offscreen), 
        TouchScreen(PointerClass.Onscreen), 
        Pen(PointerClass.Onscreen), 
        Undefined(PointerClass.Undefined);
        
        PointerClass pc;
        
        public PointerClass getPointerClass() {
            return this.pc;
        }
        
        public static PointerType valueOf(final int n) throws IllegalArgumentException {
            final PointerType[] values = values();
            if (0 <= n && n < values.length) {
                return values[n];
            }
            throw new IllegalArgumentException("Ordinal " + n + " out of range of PointerType.values()[0.." + (values.length - 1) + "]");
        }
        
        public static PointerType[] valuesOf(final int[] array) throws IllegalArgumentException {
            final int length = array.length;
            final PointerType[] array2 = new PointerType[length];
            for (int i = length - 1; i >= 0; --i) {
                array2[i] = valueOf(array[i]);
            }
            return array2;
        }
        
        private PointerType(final PointerClass pc) {
            this.pc = pc;
        }
    }
}
