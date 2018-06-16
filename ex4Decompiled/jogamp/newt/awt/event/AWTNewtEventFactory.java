// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.awt.event;

import java.awt.event.KeyEvent;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.newt.Window;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentEvent;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import java.awt.event.WindowEvent;
import com.jogamp.newt.event.InputEvent;

public class AWTNewtEventFactory
{
    private static int[] awtButtonDownMasks;
    
    public static final short eventTypeAWT2NEWT(final int n) {
        switch (n) {
            case 201: {
                return 102;
            }
            case 202: {
                return 106;
            }
            case 205: {
                return 103;
            }
            case 207: {
                return 103;
            }
            case 1004: {
                return 103;
            }
            case 206: {
                return 104;
            }
            case 208: {
                return 104;
            }
            case 1005: {
                return 104;
            }
            case 100: {
                return 101;
            }
            case 101: {
                return 100;
            }
            case 500: {
                return 200;
            }
            case 501: {
                return 203;
            }
            case 502: {
                return 204;
            }
            case 503: {
                return 205;
            }
            case 504: {
                return 201;
            }
            case 505: {
                return 202;
            }
            case 506: {
                return 206;
            }
            case 507: {
                return 207;
            }
            case 401: {
                return 300;
            }
            case 402: {
                return 301;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static int getAWTButtonDownMaskImpl(final int n) {
        int n2 = 0;
        switch (n) {
            case 0: {
                n2 = 0;
                break;
            }
            case 1: {
                n2 = 1024;
                break;
            }
            case 2: {
                n2 = 2048;
                break;
            }
            case 3: {
                n2 = 4096;
                break;
            }
            default: {
                if (n <= 16) {
                    n2 = 1 << 10 + n;
                    break;
                }
                n2 = 0;
                break;
            }
        }
        return n2;
    }
    
    public static int getAWTButtonDownMask(final int n) {
        if (0 < n && n <= AWTNewtEventFactory.awtButtonDownMasks.length) {
            return AWTNewtEventFactory.awtButtonDownMasks[n - 1];
        }
        return 0;
    }
    
    public static final short awtButton2Newt(final int n) {
        if (0 < n && n <= 16) {
            return (short)n;
        }
        return 0;
    }
    
    public static final int awtModifiers2Newt(final int n, final int n2) {
        int n3 = 0;
        if ((n2 & 0x40) != 0x0) {
            n3 |= 0x1;
        }
        if ((n2 & 0x80) != 0x0) {
            n3 |= 0x2;
        }
        if ((n2 & 0x100) != 0x0) {
            n3 |= 0x4;
        }
        if ((n2 & 0x200) != 0x0) {
            n3 |= 0x8;
        }
        if ((n2 & 0x2000) != 0x0) {
            n3 |= 0x10;
        }
        if (0 != n2) {
            for (int i = 0; i < AWTNewtEventFactory.awtButtonDownMasks.length; ++i) {
                if ((n2 & AWTNewtEventFactory.awtButtonDownMasks[i]) != 0x0) {
                    n3 |= InputEvent.getButtonMask(i + 1);
                }
            }
        }
        return n3;
    }
    
    public static short awtKeyCode2NewtKeyCode(final int n) {
        final short n2 = (short)n;
        switch (n) {
            case 36: {
                return 2;
            }
            case 35: {
                return 3;
            }
            case 24: {
                return 4;
            }
            case 154: {
                return 5;
            }
            case 8: {
                return 8;
            }
            case 9: {
                return 9;
            }
            case 10: {
                return 13;
            }
            case 34: {
                return 11;
            }
            case 12: {
                return 12;
            }
            case 16: {
                return 15;
            }
            case 33: {
                return 16;
            }
            case 17: {
                return 17;
            }
            case 18: {
                return 18;
            }
            case 65406: {
                return 19;
            }
            case 20: {
                return 20;
            }
            case 19: {
                return 22;
            }
            case 145: {
                return 23;
            }
            case 3: {
                return 24;
            }
            case 155: {
                return 26;
            }
            case 27: {
                return 27;
            }
            case 28: {
                return 28;
            }
            case 29: {
                return 29;
            }
            case 30: {
                return 30;
            }
            case 31: {
                return 31;
            }
            case 32: {
                return 32;
            }
            case 517: {
                return 33;
            }
            case 152: {
                return 34;
            }
            case 520: {
                return 35;
            }
            case 515: {
                return 36;
            }
            case 150: {
                return 38;
            }
            case 222: {
                return 39;
            }
            case 519: {
                return 40;
            }
            case 522: {
                return 41;
            }
            case 151: {
                return 42;
            }
            case 521: {
                return 43;
            }
            case 44: {
                return 44;
            }
            case 45: {
                return 45;
            }
            case 46: {
                return 46;
            }
            case 47: {
                return 47;
            }
            case 48: {
                return 48;
            }
            case 49: {
                return 49;
            }
            case 50: {
                return 50;
            }
            case 51: {
                return 51;
            }
            case 52: {
                return 52;
            }
            case 53: {
                return 53;
            }
            case 54: {
                return 54;
            }
            case 55: {
                return 55;
            }
            case 56: {
                return 56;
            }
            case 57: {
                return 57;
            }
            case 513: {
                return 58;
            }
            case 59: {
                return 59;
            }
            case 153: {
                return 60;
            }
            case 61: {
                return 61;
            }
            case 160: {
                return 62;
            }
            case 63: {
                return 63;
            }
            case 512: {
                return 64;
            }
            case 65: {
                return 65;
            }
            case 66: {
                return 66;
            }
            case 67: {
                return 67;
            }
            case 68: {
                return 68;
            }
            case 69: {
                return 69;
            }
            case 70: {
                return 70;
            }
            case 71: {
                return 71;
            }
            case 72: {
                return 72;
            }
            case 73: {
                return 73;
            }
            case 74: {
                return 74;
            }
            case 75: {
                return 75;
            }
            case 76: {
                return 76;
            }
            case 77: {
                return 77;
            }
            case 78: {
                return 78;
            }
            case 79: {
                return 79;
            }
            case 80: {
                return 80;
            }
            case 81: {
                return 81;
            }
            case 82: {
                return 82;
            }
            case 83: {
                return 83;
            }
            case 84: {
                return 84;
            }
            case 85: {
                return 85;
            }
            case 86: {
                return 86;
            }
            case 87: {
                return 87;
            }
            case 88: {
                return 88;
            }
            case 89: {
                return 89;
            }
            case 90: {
                return 90;
            }
            case 91: {
                return 91;
            }
            case 92: {
                return 92;
            }
            case 93: {
                return 93;
            }
            case 514: {
                return 94;
            }
            case 523: {
                return 95;
            }
            case 192: {
                return 96;
            }
            case 112: {
                return 97;
            }
            case 113: {
                return 98;
            }
            case 114: {
                return 99;
            }
            case 115: {
                return 100;
            }
            case 116: {
                return 101;
            }
            case 117: {
                return 102;
            }
            case 118: {
                return 103;
            }
            case 119: {
                return 104;
            }
            case 120: {
                return 105;
            }
            case 121: {
                return 106;
            }
            case 122: {
                return 107;
            }
            case 123: {
                return 108;
            }
            case 61440: {
                return 109;
            }
            case 61441: {
                return 110;
            }
            case 61442: {
                return 111;
            }
            case 61443: {
                return 112;
            }
            case 61444: {
                return 113;
            }
            case 61445: {
                return 114;
            }
            case 61446: {
                return 115;
            }
            case 61447: {
                return 116;
            }
            case 61448: {
                return 117;
            }
            case 61449: {
                return 118;
            }
            case 61450: {
                return 119;
            }
            case 61451: {
                return 120;
            }
            case 161: {
                return 123;
            }
            case 124: {
                return 124;
            }
            case 162: {
                return 125;
            }
            case 131: {
                return 126;
            }
            case 127: {
                return 147;
            }
            case 96: {
                return 128;
            }
            case 97: {
                return 129;
            }
            case 98: {
                return 130;
            }
            case 99: {
                return 131;
            }
            case 100: {
                return 132;
            }
            case 101: {
                return 133;
            }
            case 102: {
                return 134;
            }
            case 103: {
                return 135;
            }
            case 104: {
                return 136;
            }
            case 105: {
                return 137;
            }
            case 110: {
                return 138;
            }
            case 108: {
                return 127;
            }
            case 107: {
                return 139;
            }
            case 109: {
                return 140;
            }
            case 106: {
                return 141;
            }
            case 111: {
                return 142;
            }
            case 144: {
                return 148;
            }
            case 37:
            case 226: {
                return 149;
            }
            case 38:
            case 224: {
                return 150;
            }
            case 39:
            case 227: {
                return 151;
            }
            case 40:
            case 225: {
                return 152;
            }
            case 525: {
                return 153;
            }
            case 524: {
                return 154;
            }
            case 157: {
                return 155;
            }
            case 156: {
                return 156;
            }
            case 65312: {
                return 157;
            }
            case 65368: {
                return 158;
            }
            case 65480: {
                return 159;
            }
            case 518: {
                return 161;
            }
            case 516: {
                return 8364;
            }
            case 65489: {
                return -1927;
            }
            case 65485: {
                return -1926;
            }
            case 65487: {
                return -1925;
            }
            case 65483: {
                return -1924;
            }
            case 65481: {
                return -1923;
            }
            case 65488: {
                return -1922;
            }
            case 65482: {
                return -1921;
            }
            case 263: {
                return -1904;
            }
            case 258: {
                return -1903;
            }
            case 245: {
                return -1902;
            }
            case 256: {
                return -1901;
            }
            case 257: {
                return -1900;
            }
            case 240: {
                return -1899;
            }
            case 241: {
                return -1898;
            }
            case 242: {
                return -1897;
            }
            case 243: {
                return -1896;
            }
            case 244: {
                return -1894;
            }
            case 259: {
                return -1893;
            }
            case 260: {
                return -1892;
            }
            case 261: {
                return -1891;
            }
            case 262: {
                return -1889;
            }
            default: {
                return n2;
            }
        }
    }
    
    public static int newtKeyCode2AWTKeyCode(final short n) {
        final int n2 = 0xFFFF & n;
        switch (n) {
            case 2: {
                return 36;
            }
            case 3: {
                return 35;
            }
            case 4: {
                return 24;
            }
            case 5: {
                return 154;
            }
            case 8: {
                return 8;
            }
            case 9: {
                return 9;
            }
            case 13: {
                return 10;
            }
            case 11: {
                return 34;
            }
            case 12: {
                return 12;
            }
            case 15: {
                return 16;
            }
            case 16: {
                return 33;
            }
            case 17: {
                return 17;
            }
            case 18: {
                return 18;
            }
            case 19: {
                return 65406;
            }
            case 20: {
                return 20;
            }
            case 22: {
                return 19;
            }
            case 23: {
                return 145;
            }
            case 24: {
                return 3;
            }
            case 26: {
                return 155;
            }
            case 27: {
                return 27;
            }
            case 28: {
                return 28;
            }
            case 29: {
                return 29;
            }
            case 30: {
                return 30;
            }
            case 31: {
                return 31;
            }
            case 32: {
                return 32;
            }
            case 33: {
                return 517;
            }
            case 34: {
                return 152;
            }
            case 35: {
                return 520;
            }
            case 36: {
                return 515;
            }
            case 37: {
                return n2;
            }
            case 38: {
                return 150;
            }
            case 39: {
                return 222;
            }
            case 40: {
                return 519;
            }
            case 41: {
                return 522;
            }
            case 42: {
                return 151;
            }
            case 43: {
                return 521;
            }
            case 44: {
                return 44;
            }
            case 45: {
                return 45;
            }
            case 46: {
                return 46;
            }
            case 47: {
                return 47;
            }
            case 48: {
                return 48;
            }
            case 49: {
                return 49;
            }
            case 50: {
                return 50;
            }
            case 51: {
                return 51;
            }
            case 52: {
                return 52;
            }
            case 53: {
                return 53;
            }
            case 54: {
                return 54;
            }
            case 55: {
                return 55;
            }
            case 56: {
                return 56;
            }
            case 57: {
                return 57;
            }
            case 58: {
                return 513;
            }
            case 59: {
                return 59;
            }
            case 60: {
                return 153;
            }
            case 61: {
                return 61;
            }
            case 62: {
                return 160;
            }
            case 63: {
                return n2;
            }
            case 64: {
                return 512;
            }
            case 65: {
                return 65;
            }
            case 66: {
                return 66;
            }
            case 67: {
                return 67;
            }
            case 68: {
                return 68;
            }
            case 69: {
                return 69;
            }
            case 70: {
                return 70;
            }
            case 71: {
                return 71;
            }
            case 72: {
                return 72;
            }
            case 73: {
                return 73;
            }
            case 74: {
                return 74;
            }
            case 75: {
                return 75;
            }
            case 76: {
                return 76;
            }
            case 77: {
                return 77;
            }
            case 78: {
                return 78;
            }
            case 79: {
                return 79;
            }
            case 80: {
                return 80;
            }
            case 81: {
                return 81;
            }
            case 82: {
                return 82;
            }
            case 83: {
                return 83;
            }
            case 84: {
                return 84;
            }
            case 85: {
                return 85;
            }
            case 86: {
                return 86;
            }
            case 87: {
                return 87;
            }
            case 88: {
                return 88;
            }
            case 89: {
                return 89;
            }
            case 90: {
                return 90;
            }
            case 91: {
                return 91;
            }
            case 92: {
                return 92;
            }
            case 93: {
                return 93;
            }
            case 94: {
                return 514;
            }
            case 95: {
                return 523;
            }
            case 96: {
                return 192;
            }
            case 97: {
                return 112;
            }
            case 98: {
                return 113;
            }
            case 99: {
                return 114;
            }
            case 100: {
                return 115;
            }
            case 101: {
                return 116;
            }
            case 102: {
                return 117;
            }
            case 103: {
                return 118;
            }
            case 104: {
                return 119;
            }
            case 105: {
                return 120;
            }
            case 106: {
                return 121;
            }
            case 107: {
                return 122;
            }
            case 108: {
                return 123;
            }
            case 109: {
                return 61440;
            }
            case 110: {
                return 61441;
            }
            case 111: {
                return 61442;
            }
            case 112: {
                return 61443;
            }
            case 113: {
                return 61444;
            }
            case 114: {
                return 61445;
            }
            case 115: {
                return 61446;
            }
            case 116: {
                return 61447;
            }
            case 117: {
                return 61448;
            }
            case 118: {
                return 61449;
            }
            case 119: {
                return 61450;
            }
            case 120: {
                return 61451;
            }
            case 123: {
                return 161;
            }
            case 124: {
                return n2;
            }
            case 125: {
                return 162;
            }
            case 126: {
                return 131;
            }
            case 147: {
                return 127;
            }
            case 128: {
                return 96;
            }
            case 129: {
                return 97;
            }
            case 130: {
                return 98;
            }
            case 131: {
                return 99;
            }
            case 132: {
                return 100;
            }
            case 133: {
                return 101;
            }
            case 134: {
                return 102;
            }
            case 135: {
                return 103;
            }
            case 136: {
                return 104;
            }
            case 137: {
                return 105;
            }
            case 138: {
                return 110;
            }
            case 127: {
                return 108;
            }
            case 139: {
                return 107;
            }
            case 140: {
                return 109;
            }
            case 141: {
                return 106;
            }
            case 142: {
                return 111;
            }
            case 148: {
                return 144;
            }
            case 149: {
                return 37;
            }
            case 150: {
                return 38;
            }
            case 151: {
                return 39;
            }
            case 152: {
                return 40;
            }
            case 153: {
                return 525;
            }
            case 154: {
                return 524;
            }
            case 155: {
                return 157;
            }
            case 156: {
                return 156;
            }
            case 157: {
                return 65312;
            }
            case 158: {
                return 65368;
            }
            case 159: {
                return 65480;
            }
            case 161: {
                return 518;
            }
            case 8364: {
                return 516;
            }
            case -1927: {
                return 65489;
            }
            case -1926: {
                return 65485;
            }
            case -1925: {
                return 65487;
            }
            case -1924: {
                return 65483;
            }
            case -1923: {
                return 65481;
            }
            case -1922: {
                return 65488;
            }
            case -1921: {
                return 65482;
            }
            case -1904: {
                return 263;
            }
            case -1903: {
                return 258;
            }
            case -1902: {
                return 245;
            }
            case -1901: {
                return 256;
            }
            case -1900: {
                return 257;
            }
            case -1899: {
                return 240;
            }
            case -1898: {
                return 241;
            }
            case -1897: {
                return 242;
            }
            case -1896: {
                return 243;
            }
            case -1894: {
                return 244;
            }
            case -1893: {
                return 259;
            }
            case -1892: {
                return 260;
            }
            case -1891: {
                return 261;
            }
            case -1889: {
                return 262;
            }
            default: {
                return n2;
            }
        }
    }
    
    public static final com.jogamp.newt.event.WindowEvent createWindowEvent(final WindowEvent windowEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        final short eventTypeAWT2NEWT = eventTypeAWT2NEWT(windowEvent.getID());
        if (eventTypeAWT2NEWT != 0) {
            return new com.jogamp.newt.event.WindowEvent(eventTypeAWT2NEWT, nativeSurfaceHolder, System.currentTimeMillis());
        }
        return null;
    }
    
    public static final com.jogamp.newt.event.WindowEvent createWindowEvent(final ComponentEvent componentEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        final short eventTypeAWT2NEWT = eventTypeAWT2NEWT(componentEvent.getID());
        if (eventTypeAWT2NEWT != 0) {
            return new com.jogamp.newt.event.WindowEvent(eventTypeAWT2NEWT, nativeSurfaceHolder, System.currentTimeMillis());
        }
        return null;
    }
    
    public static final com.jogamp.newt.event.WindowEvent createWindowEvent(final FocusEvent focusEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        final short eventTypeAWT2NEWT = eventTypeAWT2NEWT(focusEvent.getID());
        if (eventTypeAWT2NEWT != 0) {
            return new com.jogamp.newt.event.WindowEvent(eventTypeAWT2NEWT, nativeSurfaceHolder, System.currentTimeMillis());
        }
        return null;
    }
    
    public static final com.jogamp.newt.event.MouseEvent createMouseEvent(final MouseEvent mouseEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        final short eventTypeAWT2NEWT = eventTypeAWT2NEWT(mouseEvent.getID());
        if (eventTypeAWT2NEWT != 0) {
            float n = 0.0f;
            if (mouseEvent instanceof MouseWheelEvent) {
                n = -1.0f * ((MouseWheelEvent)mouseEvent).getWheelRotation();
            }
            final short awtButton2Newt = awtButton2Newt(mouseEvent.getButton());
            int n2 = awtModifiers2Newt(mouseEvent.getModifiers(), mouseEvent.getModifiersEx()) | InputEvent.getButtonMask(awtButton2Newt);
            final NativeSurface nativeSurface = nativeSurfaceHolder.getNativeSurface();
            int[] convertToPixelUnits;
            if (null != nativeSurface) {
                if (nativeSurface instanceof Window) {
                    final Window window = (Window)nativeSurface;
                    if (window.isPointerConfined()) {
                        n2 |= 0x40000000;
                    }
                    if (!window.isPointerVisible()) {
                        n2 |= Integer.MIN_VALUE;
                    }
                }
                convertToPixelUnits = nativeSurface.convertToPixelUnits(new int[] { mouseEvent.getX(), mouseEvent.getY() });
            }
            else {
                convertToPixelUnits = new int[] { mouseEvent.getX(), mouseEvent.getY() };
            }
            return new com.jogamp.newt.event.MouseEvent(eventTypeAWT2NEWT, nativeSurfaceHolder, mouseEvent.getWhen(), n2, convertToPixelUnits[0], convertToPixelUnits[1], (short)mouseEvent.getClickCount(), awtButton2Newt, com.jogamp.newt.event.MouseEvent.getRotationXYZ(n, n2), 1.0f);
        }
        return null;
    }
    
    public static final com.jogamp.newt.event.KeyEvent createKeyEvent(final KeyEvent keyEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        return createKeyEvent(eventTypeAWT2NEWT(keyEvent.getID()), keyEvent, nativeSurfaceHolder);
    }
    
    public static final com.jogamp.newt.event.KeyEvent createKeyEvent(final short n, final KeyEvent keyEvent, final NativeSurfaceHolder nativeSurfaceHolder) {
        if (0 != n) {
            final short awtKeyCode2NewtKeyCode = awtKeyCode2NewtKeyCode(keyEvent.getKeyCode());
            return com.jogamp.newt.event.KeyEvent.create(n, nativeSurfaceHolder, keyEvent.getWhen(), awtModifiers2Newt(keyEvent.getModifiers(), keyEvent.getModifiersEx()), awtKeyCode2NewtKeyCode, awtKeyCode2NewtKeyCode, keyEvent.getKeyChar());
        }
        return null;
    }
    
    static {
        AWTNewtEventFactory.awtButtonDownMasks = new int[16];
        for (int i = 0; i < AWTNewtEventFactory.awtButtonDownMasks.length; ++i) {
            AWTNewtEventFactory.awtButtonDownMasks[i] = getAWTButtonDownMaskImpl(i + 1);
        }
    }
}
