// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import com.jogamp.common.util.Bitfield;

public abstract class InputEvent extends NEWTEvent
{
    public static final int SHIFT_MASK = 1;
    public static final int CTRL_MASK = 2;
    public static final int META_MASK = 4;
    public static final int ALT_MASK = 8;
    public static final int ALT_GRAPH_MASK = 16;
    public static final int BUTTON1_MASK = 32;
    public static final int BUTTON2_MASK = 64;
    public static final int BUTTON3_MASK = 128;
    public static final int BUTTON4_MASK = 256;
    public static final int BUTTON5_MASK = 512;
    public static final int BUTTON6_MASK = 1024;
    public static final int BUTTON7_MASK = 2048;
    public static final int BUTTON8_MASK = 4096;
    public static final int BUTTON9_MASK = 8192;
    public static final int BUTTONLAST_MASK = 1048576;
    public static final int BUTTONALL_MASK = 2097120;
    public static final int AUTOREPEAT_MASK = 536870912;
    public static final int CONFINED_MASK = 1073741824;
    public static final int INVISIBLE_MASK = Integer.MIN_VALUE;
    private final int modifiers;
    
    public static final int getButtonMask(final int n) {
        if (0 < n && n <= 16) {
            return 1 << 4 + n;
        }
        return 0;
    }
    
    protected InputEvent(final short n, final Object o, final long n2, final int modifiers) {
        super(n, o, n2);
        this.modifiers = modifiers;
    }
    
    public final int getModifiers() {
        return this.modifiers;
    }
    
    public final boolean isAltDown() {
        return (this.modifiers & 0x8) != 0x0;
    }
    
    public final boolean isAltGraphDown() {
        return (this.modifiers & 0x10) != 0x0;
    }
    
    public final boolean isControlDown() {
        return (this.modifiers & 0x2) != 0x0;
    }
    
    public final boolean isMetaDown() {
        return (this.modifiers & 0x4) != 0x0;
    }
    
    public final boolean isShiftDown() {
        return (this.modifiers & 0x1) != 0x0;
    }
    
    public final boolean isAutoRepeat() {
        return (this.modifiers & 0x20000000) != 0x0;
    }
    
    public final boolean isConfined() {
        return (this.modifiers & 0x40000000) != 0x0;
    }
    
    public final boolean isInvisible() {
        return (this.modifiers & Integer.MIN_VALUE) != 0x0;
    }
    
    public final StringBuilder getModifiersString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("[");
        int n = 1;
        if (this.isShiftDown()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("shift");
        }
        if (this.isControlDown()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("ctrl");
        }
        if (this.isMetaDown()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("meta");
        }
        if (this.isAltDown()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("alt");
        }
        if (this.isAltGraphDown()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("altgr");
        }
        if (this.isAutoRepeat()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("repeat");
        }
        for (int i = 1; i <= 16; ++i) {
            if (this.isButtonDown(i)) {
                if (n == 0) {
                    sb.append(", ");
                }
                n = 0;
                sb.append("button").append(i);
            }
        }
        if (this.isConfined()) {
            if (n == 0) {
                sb.append(", ");
            }
            n = 0;
            sb.append("confined");
        }
        if (this.isInvisible()) {
            if (n == 0) {
                sb.append(", ");
            }
            sb.append("invisible");
        }
        sb.append("]");
        return sb;
    }
    
    public final short[] getButtonsDown() {
        final short[] array = new short[this.getButtonDownCount()];
        int n = 0;
        for (int i = 1; i <= 16; ++i) {
            if (this.isButtonDown(i)) {
                array[n++] = (short)(0 + i);
            }
        }
        return array;
    }
    
    public final boolean isButtonDown(final int n) {
        return (this.modifiers & getButtonMask(n)) != 0x0;
    }
    
    public final int getButtonDownCount() {
        return Bitfield.Util.bitCount(this.modifiers & 0x1FFFE0);
    }
    
    public final boolean isAnyButtonDown() {
        return 0x0 != (this.modifiers & 0x1FFFE0);
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("InputEvent[modifiers: ");
        this.getModifiersString(sb);
        sb.append(", ");
        super.toString(sb).append("]");
        return sb;
    }
    
    public interface InputType
    {
    }
    
    public interface InputClass
    {
    }
}
