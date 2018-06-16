// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import com.jogamp.common.util.Bitfield;

public class KeyEvent extends InputEvent
{
    private final short keyCode;
    private final short keySym;
    private final char keyChar;
    private final byte flags;
    private static final byte F_MODIFIER_MASK = 1;
    private static final byte F_ACTION_MASK = 2;
    private static final byte F_PRINTABLE_MASK = 4;
    public static final short EVENT_KEY_PRESSED = 300;
    public static final short EVENT_KEY_RELEASED = 301;
    public static final char NULL_CHAR = '\0';
    public static final NonPrintableRange[] nonPrintableKeys;
    public static final short VK_UNDEFINED = 0;
    static final short VK_FREE01 = 1;
    public static final short VK_HOME = 2;
    public static final short VK_END = 3;
    public static final short VK_FINAL = 4;
    public static final short VK_PRINTSCREEN = 5;
    static final short VK_FREE06 = 6;
    static final short VK_FREE07 = 7;
    public static final short VK_BACK_SPACE = 8;
    public static final short VK_TAB = 9;
    static final short VK_FREE0A = 10;
    public static final short VK_PAGE_DOWN = 11;
    public static final short VK_CLEAR = 12;
    public static final short VK_ENTER = 13;
    static final short VK_FREE0E = 14;
    public static final short VK_SHIFT = 15;
    public static final short VK_PAGE_UP = 16;
    public static final short VK_CONTROL = 17;
    public static final short VK_ALT = 18;
    public static final short VK_ALT_GRAPH = 19;
    public static final short VK_CAPS_LOCK = 20;
    static final short VK_FREE15 = 21;
    public static final short VK_PAUSE = 22;
    public static final short VK_SCROLL_LOCK = 23;
    public static final short VK_CANCEL = 24;
    static final short VK_FREE19 = 25;
    public static final short VK_INSERT = 26;
    public static final short VK_ESCAPE = 27;
    public static final short VK_CONVERT = 28;
    public static final short VK_NONCONVERT = 29;
    public static final short VK_ACCEPT = 30;
    public static final short VK_MODECHANGE = 31;
    public static final short VK_SPACE = 32;
    public static final short VK_EXCLAMATION_MARK = 33;
    public static final short VK_QUOTEDBL = 34;
    public static final short VK_NUMBER_SIGN = 35;
    public static final short VK_DOLLAR = 36;
    public static final short VK_PERCENT = 37;
    public static final short VK_AMPERSAND = 38;
    public static final short VK_QUOTE = 39;
    public static final short VK_LEFT_PARENTHESIS = 40;
    public static final short VK_RIGHT_PARENTHESIS = 41;
    public static final short VK_ASTERISK = 42;
    public static final short VK_PLUS = 43;
    public static final short VK_COMMA = 44;
    public static final short VK_MINUS = 45;
    public static final short VK_PERIOD = 46;
    public static final short VK_SLASH = 47;
    public static final short VK_0 = 48;
    public static final short VK_1 = 49;
    public static final short VK_2 = 50;
    public static final short VK_3 = 51;
    public static final short VK_4 = 52;
    public static final short VK_5 = 53;
    public static final short VK_6 = 54;
    public static final short VK_7 = 55;
    public static final short VK_8 = 56;
    public static final short VK_9 = 57;
    public static final short VK_COLON = 58;
    public static final short VK_SEMICOLON = 59;
    public static final short VK_LESS = 60;
    public static final short VK_EQUALS = 61;
    public static final short VK_GREATER = 62;
    public static final short VK_QUESTIONMARK = 63;
    public static final short VK_AT = 64;
    public static final short VK_A = 65;
    public static final short VK_B = 66;
    public static final short VK_C = 67;
    public static final short VK_D = 68;
    public static final short VK_E = 69;
    public static final short VK_F = 70;
    public static final short VK_G = 71;
    public static final short VK_H = 72;
    public static final short VK_I = 73;
    public static final short VK_J = 74;
    public static final short VK_K = 75;
    public static final short VK_L = 76;
    public static final short VK_M = 77;
    public static final short VK_N = 78;
    public static final short VK_O = 79;
    public static final short VK_P = 80;
    public static final short VK_Q = 81;
    public static final short VK_R = 82;
    public static final short VK_S = 83;
    public static final short VK_T = 84;
    public static final short VK_U = 85;
    public static final short VK_V = 86;
    public static final short VK_W = 87;
    public static final short VK_X = 88;
    public static final short VK_Y = 89;
    public static final short VK_Z = 90;
    public static final short VK_OPEN_BRACKET = 91;
    public static final short VK_BACK_SLASH = 92;
    public static final short VK_CLOSE_BRACKET = 93;
    public static final short VK_CIRCUMFLEX = 94;
    public static final short VK_UNDERSCORE = 95;
    public static final short VK_BACK_QUOTE = 96;
    public static final short VK_F1 = 97;
    public static final short VK_F2 = 98;
    public static final short VK_F3 = 99;
    public static final short VK_F4 = 100;
    public static final short VK_F5 = 101;
    public static final short VK_F6 = 102;
    public static final short VK_F7 = 103;
    public static final short VK_F8 = 104;
    public static final short VK_F9 = 105;
    public static final short VK_F10 = 106;
    public static final short VK_F11 = 107;
    public static final short VK_F12 = 108;
    public static final short VK_F13 = 109;
    public static final short VK_F14 = 110;
    public static final short VK_F15 = 111;
    public static final short VK_F16 = 112;
    public static final short VK_F17 = 113;
    public static final short VK_F18 = 114;
    public static final short VK_F19 = 115;
    public static final short VK_F20 = 116;
    public static final short VK_F21 = 117;
    public static final short VK_F22 = 118;
    public static final short VK_F23 = 119;
    public static final short VK_F24 = 120;
    public static final short VK_LEFT_BRACE = 123;
    public static final short VK_PIPE = 124;
    public static final short VK_RIGHT_BRACE = 125;
    public static final short VK_TILDE = 126;
    public static final short VK_SEPARATOR = 127;
    public static final short VK_NUMPAD0 = 128;
    public static final short VK_NUMPAD1 = 129;
    public static final short VK_NUMPAD2 = 130;
    public static final short VK_NUMPAD3 = 131;
    public static final short VK_NUMPAD4 = 132;
    public static final short VK_NUMPAD5 = 133;
    public static final short VK_NUMPAD6 = 134;
    public static final short VK_NUMPAD7 = 135;
    public static final short VK_NUMPAD8 = 136;
    public static final short VK_NUMPAD9 = 137;
    public static final short VK_DECIMAL = 138;
    public static final short VK_ADD = 139;
    public static final short VK_SUBTRACT = 140;
    public static final short VK_MULTIPLY = 141;
    public static final short VK_DIVIDE = 142;
    public static final short VK_DELETE = 147;
    public static final short VK_NUM_LOCK = 148;
    public static final short VK_LEFT = 149;
    public static final short VK_UP = 150;
    public static final short VK_RIGHT = 151;
    public static final short VK_DOWN = 152;
    public static final short VK_CONTEXT_MENU = 153;
    public static final short VK_WINDOWS = 154;
    public static final short VK_META = 155;
    public static final short VK_HELP = 156;
    public static final short VK_COMPOSE = 157;
    public static final short VK_BEGIN = 158;
    public static final short VK_STOP = 159;
    public static final short VK_INVERTED_EXCLAMATION_MARK = 161;
    public static final short VK_EURO_SIGN = 8364;
    public static final short VK_CUT = -1927;
    public static final short VK_COPY = -1926;
    public static final short VK_PASTE = -1925;
    public static final short VK_UNDO = -1924;
    public static final short VK_AGAIN = -1923;
    public static final short VK_FIND = -1922;
    public static final short VK_PROPS = -1921;
    public static final short VK_INPUT_METHOD_ON_OFF = -1904;
    public static final short VK_CODE_INPUT = -1903;
    public static final short VK_ROMAN_CHARACTERS = -1902;
    public static final short VK_ALL_CANDIDATES = -1901;
    public static final short VK_PREVIOUS_CANDIDATE = -1900;
    public static final short VK_ALPHANUMERIC = -1899;
    public static final short VK_KATAKANA = -1898;
    public static final short VK_HIRAGANA = -1897;
    public static final short VK_FULL_WIDTH = -1896;
    public static final short VK_HALF_WIDTH = -1894;
    public static final short VK_JAPANESE_KATAKANA = -1893;
    public static final short VK_JAPANESE_HIRAGANA = -1892;
    public static final short VK_JAPANESE_ROMAN = -1891;
    public static final short VK_KANA_LOCK = -1889;
    public static final short VK_KEYBOARD_INVISIBLE = -1793;
    
    private KeyEvent(final short n, final Object o, final long n2, final int n3, final short keyCode, final short keySym, final int n4, final char keyChar) {
        super(n, o, n2, n3 | n4);
        this.keyCode = keyCode;
        this.keySym = keySym;
        this.keyChar = keyChar;
        final boolean b = false;
        byte flags;
        if (isPrintableKey(keySym, false) && isPrintableKey((short)keyChar, true)) {
            flags = (byte)((b ? 1 : 0) | 0x4);
        }
        else if (0 != n4) {
            flags = (byte)((b | true) ? 1 : 0);
        }
        else {
            flags = (byte)((b ? 1 : 0) | 0x2);
        }
        this.flags = flags;
        final int bitCount = Bitfield.Util.bitCount(this.flags & 0x7);
        if (bitCount == 0) {
            throw new InternalError("Key must be either of type printable, modifier or action - but it is of " + bitCount + " types: " + this);
        }
    }
    
    public static KeyEvent create(final short n, final Object o, final long n2, final int n3, final short n4, final short n5, final char c) {
        return new KeyEvent(n, o, n2, n3, n4, n5, getModifierMask(n5), c);
    }
    
    public final char getKeyChar() {
        return this.keyChar;
    }
    
    public final short getKeySymbol() {
        return this.keySym;
    }
    
    public final short getKeyCode() {
        return this.keyCode;
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
        sb.append("KeyEvent[").append(getEventTypeString(this.getEventType())).append(", code ").append(NEWTEvent.toHexString(this.keyCode)).append(", sym ").append(NEWTEvent.toHexString(this.keySym)).append(", char '").append(this.keyChar).append("' (").append(NEWTEvent.toHexString((short)this.keyChar)).append("), printable ").append(this.isPrintableKey()).append(", modifier ").append(this.isModifierKey()).append(", action ").append(this.isActionKey()).append(", ");
        return super.toString(sb).append("]");
    }
    
    public static String getEventTypeString(final short n) {
        switch (n) {
            case 300: {
                return "EVENT_KEY_PRESSED";
            }
            case 301: {
                return "EVENT_KEY_RELEASED";
            }
            default: {
                return "unknown (" + n + ")";
            }
        }
    }
    
    public static short utf16ToVKey(final char c) {
        if ('a' <= c && c <= 'z') {
            return (short)(c - 'a' + 'A');
        }
        return (short)c;
    }
    
    public static boolean isModifierKey(final short n) {
        switch (n) {
            case 15:
            case 17:
            case 18:
            case 19:
            case 155: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static int getModifierMask(final short n) {
        switch (n) {
            case 15: {
                return 1;
            }
            case 17: {
                return 2;
            }
            case 18:
            case 19: {
                return 8;
            }
            case 155: {
                return 4;
            }
            default: {
                return 0;
            }
        }
    }
    
    public final boolean isModifierKey() {
        return 0x0 != (0x1 & this.flags);
    }
    
    public final boolean isActionKey() {
        return 0x0 != (0x2 & this.flags);
    }
    
    public static boolean isPrintableKey(final short n, final boolean b) {
        if (8 == n || 9 == n || 13 == n) {
            return true;
        }
        if (!b) {
            if ((KeyEvent.nonPrintableKeys[0].min <= n && n <= KeyEvent.nonPrintableKeys[0].max) || (KeyEvent.nonPrintableKeys[1].min <= n && n <= KeyEvent.nonPrintableKeys[1].max) || (KeyEvent.nonPrintableKeys[2].min <= n && n <= KeyEvent.nonPrintableKeys[2].max) || (KeyEvent.nonPrintableKeys[3].min <= n && n <= KeyEvent.nonPrintableKeys[3].max)) {
                return false;
            }
        }
        else if ((KeyEvent.nonPrintableKeys[0].inclKeyChar && KeyEvent.nonPrintableKeys[0].min <= n && n <= KeyEvent.nonPrintableKeys[0].max) || (KeyEvent.nonPrintableKeys[1].inclKeyChar && KeyEvent.nonPrintableKeys[1].min <= n && n <= KeyEvent.nonPrintableKeys[1].max) || (KeyEvent.nonPrintableKeys[2].inclKeyChar && KeyEvent.nonPrintableKeys[2].min <= n && n <= KeyEvent.nonPrintableKeys[2].max) || (KeyEvent.nonPrintableKeys[3].inclKeyChar && KeyEvent.nonPrintableKeys[3].min <= n && n <= KeyEvent.nonPrintableKeys[3].max)) {
            return false;
        }
        return 0 != n;
    }
    
    public final boolean isPrintableKey() {
        return 0x0 != (0x4 & this.flags);
    }
    
    static {
        nonPrintableKeys = new NonPrintableRange[] { new NonPrintableRange((short)0, (short)31, true), new NonPrintableRange((short)97, (short)120, false), new NonPrintableRange((short)143, (short)159, true), new NonPrintableRange((short)(-8192), (short)(-1793), true) };
    }
    
    public static class NonPrintableRange
    {
        public short min;
        public short max;
        public final boolean inclKeyChar;
        
        private NonPrintableRange(final short min, final short max, final boolean inclKeyChar) {
            this.min = min;
            this.max = max;
            this.inclKeyChar = inclKeyChar;
        }
    }
}
