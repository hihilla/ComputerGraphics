// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.swt.event;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Control;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import org.eclipse.swt.widgets.Event;

public class SWTNewtEventFactory
{
    short dragButtonDown;
    
    public static final short eventTypeSWT2NEWT(final int n) {
        switch (n) {
            case 3: {
                return 203;
            }
            case 4: {
                return 204;
            }
            case 5: {
                return 205;
            }
            case 6: {
                return 201;
            }
            case 7: {
                return 202;
            }
            case 37: {
                return 207;
            }
            case 1: {
                return 300;
            }
            case 2: {
                return 301;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static final int swtModifiers2Newt(final int n, final boolean b) {
        int n2 = 0;
        if ((n & 0x20000) != 0x0) {
            n2 |= 0x1;
        }
        if ((n & 0x40000) != 0x0) {
            n2 |= 0x2;
        }
        if ((n & 0x10000) != 0x0) {
            n2 |= 0x8;
        }
        return n2;
    }
    
    public static short swtKeyCode2NewtKeyCode(final int n) {
        final short n2 = (short)n;
        switch (n) {
            case 16777223: {
                return 2;
            }
            case 16777224: {
                return 3;
            }
            case 16777303: {
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
            case 16777222: {
                return 11;
            }
            case 16777221: {
                return 16;
            }
            case 262144: {
                return 17;
            }
            case 16777298: {
                return 20;
            }
            case 16777301: {
                return 22;
            }
            case 16777300: {
                return 23;
            }
            case 256: {
                return 24;
            }
            case 16777225: {
                return 26;
            }
            case 27: {
                return 27;
            }
            case 32: {
                return 32;
            }
            case 16777226: {
                return 97;
            }
            case 16777227: {
                return 98;
            }
            case 16777228: {
                return 99;
            }
            case 16777229: {
                return 100;
            }
            case 16777230: {
                return 101;
            }
            case 16777231: {
                return 102;
            }
            case 16777232: {
                return 103;
            }
            case 16777233: {
                return 104;
            }
            case 16777234: {
                return 105;
            }
            case 16777235: {
                return 106;
            }
            case 16777236: {
                return 107;
            }
            case 16777237: {
                return 108;
            }
            case 16777238: {
                return 109;
            }
            case 16777239: {
                return 110;
            }
            case 16777240: {
                return 111;
            }
            case 16777241: {
                return 112;
            }
            case 16777242: {
                return 113;
            }
            case 16777243: {
                return 114;
            }
            case 16777244: {
                return 115;
            }
            case 16777245: {
                return 116;
            }
            case 127: {
                return 147;
            }
            case 16777264: {
                return 128;
            }
            case 16777265: {
                return 129;
            }
            case 16777266: {
                return 130;
            }
            case 16777267: {
                return 131;
            }
            case 16777268: {
                return 132;
            }
            case 16777269: {
                return 133;
            }
            case 16777270: {
                return 134;
            }
            case 16777271: {
                return 135;
            }
            case 16777272: {
                return 136;
            }
            case 16777273: {
                return 137;
            }
            case 16777262: {
                return 138;
            }
            case 16777259: {
                return 139;
            }
            case 16777261: {
                return 140;
            }
            case 16777258: {
                return 141;
            }
            case 16777263: {
                return 142;
            }
            case 16777299: {
                return 148;
            }
            case 16777219: {
                return 149;
            }
            case 16777217: {
                return 150;
            }
            case 16777220: {
                return 151;
            }
            case 16777218: {
                return 152;
            }
            case 16777297: {
                return 156;
            }
            default: {
                return n2;
            }
        }
    }
    
    public static int newtKeyCode2SWTKeyCode(final short n) {
        final int n2 = 0xFFFF & n;
        switch (n) {
            case 2: {
                return 16777223;
            }
            case 3: {
                return 16777224;
            }
            case 5: {
                return 16777303;
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
                return 16777222;
            }
            case 16: {
                return 16777221;
            }
            case 17: {
                return 262144;
            }
            case 20: {
                return 16777298;
            }
            case 22: {
                return 16777301;
            }
            case 23: {
                return 16777300;
            }
            case 24: {
                return 256;
            }
            case 26: {
                return 16777225;
            }
            case 27: {
                return 27;
            }
            case 32: {
                return 32;
            }
            case 97: {
                return 16777226;
            }
            case 98: {
                return 16777227;
            }
            case 99: {
                return 16777228;
            }
            case 100: {
                return 16777229;
            }
            case 101: {
                return 16777230;
            }
            case 102: {
                return 16777231;
            }
            case 103: {
                return 16777232;
            }
            case 104: {
                return 16777233;
            }
            case 105: {
                return 16777234;
            }
            case 106: {
                return 16777235;
            }
            case 107: {
                return 16777236;
            }
            case 108: {
                return 16777237;
            }
            case 109: {
                return 16777238;
            }
            case 110: {
                return 16777239;
            }
            case 111: {
                return 16777240;
            }
            case 112: {
                return 16777241;
            }
            case 113: {
                return 16777242;
            }
            case 114: {
                return 16777243;
            }
            case 115: {
                return 16777244;
            }
            case 116: {
                return 16777245;
            }
            case 147: {
                return 127;
            }
            case 128: {
                return 16777264;
            }
            case 129: {
                return 16777265;
            }
            case 130: {
                return 16777266;
            }
            case 131: {
                return 16777267;
            }
            case 132: {
                return 16777268;
            }
            case 133: {
                return 16777269;
            }
            case 134: {
                return 16777270;
            }
            case 135: {
                return 16777271;
            }
            case 136: {
                return 16777272;
            }
            case 137: {
                return 16777273;
            }
            case 138: {
                return 16777262;
            }
            case 139: {
                return 16777259;
            }
            case 140: {
                return 16777261;
            }
            case 141: {
                return 16777258;
            }
            case 142: {
                return 16777263;
            }
            case 148: {
                return 16777299;
            }
            case 149: {
                return 16777219;
            }
            case 150: {
                return 16777217;
            }
            case 151: {
                return 16777220;
            }
            case 152: {
                return 16777218;
            }
            case 156: {
                return 16777297;
            }
            default: {
                return n2;
            }
        }
    }
    
    public static final InputEvent createInputEvent(final Event event, final NativeSurfaceHolder nativeSurfaceHolder) {
        InputEvent inputEvent = createMouseEvent(event, nativeSurfaceHolder);
        if (null == inputEvent) {
            inputEvent = createKeyEvent(event, nativeSurfaceHolder);
        }
        return inputEvent;
    }
    
    public static final MouseEvent createMouseEvent(final Event event, final NativeSurfaceHolder nativeSurfaceHolder) {
        switch (event.type) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 37: {
                final short eventTypeSWT2NEWT = eventTypeSWT2NEWT(event.type);
                if (eventTypeSWT2NEWT != 0) {
                    float n = 0.0f;
                    if (37 == event.type) {
                        n = (float)event.rotation;
                    }
                    int swtModifiers2Newt = swtModifiers2Newt(event.stateMask, true);
                    final NativeSurface nativeSurface = nativeSurfaceHolder.getNativeSurface();
                    int[] convertToPixelUnits;
                    if (null != nativeSurface) {
                        if (nativeSurface instanceof Window) {
                            final Window window = (Window)nativeSurface;
                            if (window.isPointerConfined()) {
                                swtModifiers2Newt |= 0x40000000;
                            }
                            if (!window.isPointerVisible()) {
                                swtModifiers2Newt |= Integer.MIN_VALUE;
                            }
                        }
                        convertToPixelUnits = nativeSurface.convertToPixelUnits(new int[] { event.x, event.y });
                    }
                    else {
                        convertToPixelUnits = new int[] { event.x, event.y };
                    }
                    return new MouseEvent(eventTypeSWT2NEWT, nativeSurfaceHolder, 0xFFFFFFFFL & event.time, swtModifiers2Newt, convertToPixelUnits[0], convertToPixelUnits[1], (short)event.count, (short)event.button, MouseEvent.getRotationXYZ(n, swtModifiers2Newt), 1.0f);
                }
                return null;
            }
            default: {
                return null;
            }
        }
    }
    
    public static final KeyEvent createKeyEvent(final Event event, final NativeSurfaceHolder nativeSurfaceHolder) {
        switch (event.type) {
            case 1:
            case 2: {
                final short eventTypeSWT2NEWT = eventTypeSWT2NEWT(event.type);
                if (eventTypeSWT2NEWT != 0) {
                    final short swtKeyCode2NewtKeyCode = swtKeyCode2NewtKeyCode(event.keyCode);
                    return KeyEvent.create(eventTypeSWT2NEWT, nativeSurfaceHolder, 0xFFFFFFFFL & event.time, swtModifiers2Newt(event.stateMask, false), swtKeyCode2NewtKeyCode, swtKeyCode2NewtKeyCode, event.character);
                }
                return null;
            }
            default: {
                return null;
            }
        }
    }
    
    public SWTNewtEventFactory() {
        this.dragButtonDown = 0;
        this.resetButtonsDown();
    }
    
    final void resetButtonsDown() {
        this.dragButtonDown = 0;
    }
    
    public final boolean dispatchMouseEvent(final Event event, final NativeSurfaceHolder nativeSurfaceHolder, final MouseListener mouseListener) {
        final MouseEvent mouseEvent = createMouseEvent(event, nativeSurfaceHolder);
        if (null != mouseEvent) {
            if (null != mouseListener) {
                switch (event.type) {
                    case 3: {
                        this.dragButtonDown = (short)event.button;
                        mouseListener.mousePressed(mouseEvent);
                        break;
                    }
                    case 4: {
                        this.dragButtonDown = 0;
                        mouseListener.mouseReleased(mouseEvent);
                        mouseListener.mouseClicked(new MouseEvent((short)200, mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), mouseEvent.getButton(), mouseEvent.getRotation(), mouseEvent.getRotationScale()));
                        break;
                    }
                    case 5: {
                        if (0 < this.dragButtonDown) {
                            mouseListener.mouseDragged(new MouseEvent((short)206, mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), this.dragButtonDown, mouseEvent.getRotation(), mouseEvent.getRotationScale()));
                            break;
                        }
                        mouseListener.mouseMoved(mouseEvent);
                        break;
                    }
                    case 6: {
                        mouseListener.mouseEntered(mouseEvent);
                        break;
                    }
                    case 7: {
                        this.resetButtonsDown();
                        mouseListener.mouseExited(mouseEvent);
                        break;
                    }
                    case 37: {
                        mouseListener.mouseWheelMoved(mouseEvent);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public final boolean dispatchKeyEvent(final Event event, final NativeSurfaceHolder nativeSurfaceHolder, final KeyListener keyListener) {
        final KeyEvent keyEvent = createKeyEvent(event, nativeSurfaceHolder);
        if (null != keyEvent) {
            if (null != keyListener) {
                switch (event.type) {
                    case 1: {
                        keyListener.keyPressed(keyEvent);
                        break;
                    }
                    case 2: {
                        keyListener.keyReleased(keyEvent);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public final void attachDispatchListener(final Control control, final NativeSurfaceHolder nativeSurfaceHolder, final MouseListener mouseListener, final KeyListener keyListener) {
        if (null == control) {
            throw new IllegalArgumentException("Argument ctrl is null");
        }
        if (null == nativeSurfaceHolder) {
            throw new IllegalArgumentException("Argument source is null");
        }
        if (null != mouseListener) {
            final Listener listener = (Listener)new Listener() {
                public void handleEvent(final Event event) {
                    SWTNewtEventFactory.this.dispatchMouseEvent(event, nativeSurfaceHolder, mouseListener);
                }
            };
            control.addListener(3, (Listener)listener);
            control.addListener(4, (Listener)listener);
            control.addListener(5, (Listener)listener);
            control.addListener(6, (Listener)listener);
            control.addListener(7, (Listener)listener);
            control.addListener(37, (Listener)listener);
        }
        if (null != keyListener) {
            final Listener listener2 = (Listener)new Listener() {
                public void handleEvent(final Event event) {
                    SWTNewtEventFactory.this.dispatchKeyEvent(event, nativeSurfaceHolder, keyListener);
                }
            };
            control.addListener(1, (Listener)listener2);
            control.addListener(2, (Listener)listener2);
        }
    }
}
