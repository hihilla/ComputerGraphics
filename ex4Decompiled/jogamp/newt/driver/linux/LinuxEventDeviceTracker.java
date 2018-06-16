// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.linux;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.Window;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import com.jogamp.common.nio.StructAccessor;
import java.nio.ByteBuffer;
import java.io.File;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.event.WindowEvent;
import jogamp.newt.WindowImpl;
import jogamp.newt.driver.KeyTracker;
import com.jogamp.newt.event.WindowListener;

public class LinuxEventDeviceTracker implements WindowListener, KeyTracker
{
    private static final LinuxEventDeviceTracker ledt;
    private WindowImpl focusedWindow;
    private final EventDeviceManager eventDeviceManager;
    private final EventDevicePoller[] eventDevicePollers;
    
    public LinuxEventDeviceTracker() {
        this.focusedWindow = null;
        this.eventDeviceManager = new EventDeviceManager();
        this.eventDevicePollers = new EventDevicePoller[32];
    }
    
    public static LinuxEventDeviceTracker getSingleton() {
        return LinuxEventDeviceTracker.ledt;
    }
    
    @Override
    public void windowResized(final WindowEvent windowEvent) {
    }
    
    @Override
    public void windowMoved(final WindowEvent windowEvent) {
    }
    
    @Override
    public void windowDestroyNotify(final WindowEvent windowEvent) {
        if (this.focusedWindow == windowEvent.getSource()) {
            this.focusedWindow = null;
        }
    }
    
    @Override
    public void windowDestroyed(final WindowEvent windowEvent) {
    }
    
    @Override
    public void windowGainedFocus(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (source instanceof WindowImpl) {
            this.focusedWindow = (WindowImpl)source;
        }
    }
    
    @Override
    public void windowLostFocus(final WindowEvent windowEvent) {
        if (this.focusedWindow == windowEvent.getSource()) {
            this.focusedWindow = null;
        }
    }
    
    public static void main(final String[] array) {
        System.setProperty("newt.debug.Window.KeyEvent", "true");
        getSingleton();
        try {
            while (true) {
                Thread.sleep(1000L);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
    }
    
    static {
        ledt = new LinuxEventDeviceTracker();
        final InterruptSource.Thread thread = new InterruptSource.Thread(null, LinuxEventDeviceTracker.ledt.eventDeviceManager, "NEWT-LinuxEventDeviceManager");
        thread.setDaemon(true);
        thread.start();
    }
    
    class EventDeviceManager implements Runnable
    {
        private volatile boolean stop;
        
        EventDeviceManager() {
            this.stop = false;
        }
        
        @Override
        public void run() {
            final File file = new File("/dev/input/");
            while (!this.stop) {
                for (final String s : file.list()) {
                    if (s.startsWith("event")) {
                        final int int1 = Integer.parseInt(s.substring(5));
                        if (int1 < 32 && int1 >= 0) {
                            if (LinuxEventDeviceTracker.this.eventDevicePollers[int1] == null) {
                                LinuxEventDeviceTracker.this.eventDevicePollers[int1] = new EventDevicePoller(int1);
                                final InterruptSource.Thread thread = new InterruptSource.Thread(null, LinuxEventDeviceTracker.this.eventDevicePollers[int1], "NEWT-LinuxEventDeviceTracker-event" + int1);
                                thread.setDaemon(true);
                                thread.start();
                            }
                            else if (LinuxEventDeviceTracker.this.eventDevicePollers[int1].stop) {
                                LinuxEventDeviceTracker.this.eventDevicePollers[int1] = null;
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    class EventDevicePoller implements Runnable
    {
        private volatile boolean stop;
        private final String eventDeviceName;
        
        public EventDevicePoller(final int n) {
            this.stop = false;
            this.eventDeviceName = "/dev/input/event" + n;
        }
        
        @Override
        public void run() {
            final byte[] array = new byte[16];
            final StructAccessor structAccessor = new StructAccessor(ByteBuffer.wrap(array));
            final File file = new File(this.eventDeviceName);
            file.setReadOnly();
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
            }
            catch (FileNotFoundException ex) {
                this.stop = true;
                return;
            }
            int n = 0;
        Label_1007:
            while (!this.stop) {
                int read;
                for (int i = 16; i > 0; i -= read) {
                    try {
                        read = fileInputStream.read(array, 0, i);
                    }
                    catch (IOException ex2) {
                        this.stop = true;
                        break Label_1007;
                    }
                    if (read < 0) {
                        this.stop = true;
                        break Label_1007;
                    }
                }
                final int int1 = structAccessor.getIntAt(0);
                final short short1 = structAccessor.getShortAt(4);
                final short short2 = structAccessor.getShortAt(8);
                final short short3 = structAccessor.getShortAt(10);
                final int int2 = structAccessor.getIntAt(12);
                switch (short2) {
                    case 0: {
                        if (Window.DEBUG_KEY_EVENT) {
                            System.out.println("[SYN_REPORT----]");
                            continue;
                        }
                        continue;
                    }
                    case 1: {
                        final short linuxEVKey2NewtVKey = this.LinuxEVKey2NewtVKey(short3);
                        final char newtVKey2Unicode = this.NewtVKey2Unicode(linuxEVKey2NewtVKey, n);
                        if (Window.DEBUG_KEY_EVENT) {
                            System.out.println("[EV_KEY: [time " + int1 + ":" + short1 + "] type " + short2 + " / code " + short3 + " = value " + int2);
                        }
                        switch (int2) {
                            case 0: {
                                final short n2 = 301;
                                switch (linuxEVKey2NewtVKey) {
                                    case 15: {
                                        n &= 0xFFFFFFFE;
                                        break;
                                    }
                                    case 18: {
                                        n &= 0xFFFFFFF7;
                                        break;
                                    }
                                    case 19: {
                                        n &= 0xFFFFFFEF;
                                        break;
                                    }
                                    case 17: {
                                        n &= 0xFFFFFFFD;
                                        break;
                                    }
                                }
                                if (null != LinuxEventDeviceTracker.this.focusedWindow) {
                                    LinuxEventDeviceTracker.this.focusedWindow.sendKeyEvent(n2, n, linuxEVKey2NewtVKey, linuxEVKey2NewtVKey, newtVKey2Unicode);
                                }
                                if (Window.DEBUG_KEY_EVENT) {
                                    System.out.println("[event released] keyCode: " + linuxEVKey2NewtVKey + " keyChar: " + newtVKey2Unicode + " modifiers: " + n);
                                    continue;
                                }
                                continue;
                            }
                            case 1: {
                                final short n3 = 300;
                                switch (linuxEVKey2NewtVKey) {
                                    case 15: {
                                        n |= 0x1;
                                        break;
                                    }
                                    case 18: {
                                        n |= 0x8;
                                        break;
                                    }
                                    case 19: {
                                        n |= 0x10;
                                        break;
                                    }
                                    case 17: {
                                        n |= 0x2;
                                        break;
                                    }
                                }
                                if (null != LinuxEventDeviceTracker.this.focusedWindow) {
                                    LinuxEventDeviceTracker.this.focusedWindow.sendKeyEvent(n3, n, linuxEVKey2NewtVKey, linuxEVKey2NewtVKey, newtVKey2Unicode);
                                }
                                if (Window.DEBUG_KEY_EVENT) {
                                    System.out.println("[event pressed] keyCode: " + linuxEVKey2NewtVKey + " keyChar: " + newtVKey2Unicode + " modifiers: " + n);
                                    continue;
                                }
                                continue;
                            }
                            case 2: {
                                final short n4 = 300;
                                int n5 = n | 0x20000000;
                                switch (linuxEVKey2NewtVKey) {
                                    case 15: {
                                        n5 |= 0x1;
                                        break;
                                    }
                                    case 18: {
                                        n5 |= 0x8;
                                        break;
                                    }
                                    case 19: {
                                        n5 |= 0x10;
                                        break;
                                    }
                                    case 17: {
                                        n5 |= 0x2;
                                        break;
                                    }
                                }
                                if (null != LinuxEventDeviceTracker.this.focusedWindow) {
                                    LinuxEventDeviceTracker.this.focusedWindow.sendKeyEvent((short)301, n5, linuxEVKey2NewtVKey, linuxEVKey2NewtVKey, newtVKey2Unicode);
                                    LinuxEventDeviceTracker.this.focusedWindow.sendKeyEvent(n4, n5, linuxEVKey2NewtVKey, linuxEVKey2NewtVKey, newtVKey2Unicode);
                                }
                                if (Window.DEBUG_KEY_EVENT) {
                                    System.out.println("[event released auto] keyCode: " + linuxEVKey2NewtVKey + " keyChar: " + newtVKey2Unicode + " modifiers: " + n5);
                                    System.out.println("[event pressed auto] keyCode: " + linuxEVKey2NewtVKey + " keyChar: " + newtVKey2Unicode + " modifiers: " + n5);
                                }
                                n = (n5 & 0xDFFFFFFF);
                                continue;
                            }
                        }
                        continue;
                    }
                    case 4: {
                        if (short3 == 4) {
                            continue;
                        }
                        continue;
                    }
                    default: {
                        if (Window.DEBUG_KEY_EVENT) {
                            System.out.println("TODO EventDevicePoller: [time " + int1 + ":" + short1 + "] type " + short2 + " / code " + short3 + " = value " + int2);
                            continue;
                        }
                        continue;
                    }
                }
            }
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                }
                catch (IOException ex3) {}
            }
            this.stop = true;
        }
        
        private char NewtVKey2Unicode(final short n, final int n2) {
            if (!KeyEvent.isPrintableKey(n, true)) {
                return '\0';
            }
            if ((n2 & 0x1) == 0x1) {
                return (char)n;
            }
            return String.valueOf((char)n).toLowerCase().charAt(0);
        }
        
        private char LinuxEVKey2Unicode(final short n) {
            switch (n) {
                case 17: {
                    return 'w';
                }
                case 31: {
                    return 's';
                }
                case 30: {
                    return 'a';
                }
                case 32: {
                    return 'd';
                }
                case 1: {
                    return '\u001b';
                }
                case 28:
                case 96: {
                    return '\n';
                }
                case 57: {
                    return ' ';
                }
                case 11:
                case 82: {
                    return '0';
                }
                case 2:
                case 79: {
                    return '1';
                }
                case 3:
                case 80: {
                    return '2';
                }
                case 4:
                case 81: {
                    return '3';
                }
                case 5:
                case 75: {
                    return '4';
                }
                case 6:
                case 76: {
                    return '5';
                }
                case 7:
                case 77: {
                    return '6';
                }
                case 8:
                case 71: {
                    return '7';
                }
                case 9:
                case 72: {
                    return '8';
                }
                case 10:
                case 73: {
                    return '9';
                }
                default: {
                    return '\0';
                }
            }
        }
        
        private short LinuxEVKey2NewtVKey(final short n) {
            switch (n) {
                case 1: {
                    return 27;
                }
                case 2: {
                    return 49;
                }
                case 79: {
                    return 129;
                }
                case 3: {
                    return 50;
                }
                case 80: {
                    return 130;
                }
                case 4: {
                    return 51;
                }
                case 81: {
                    return 131;
                }
                case 5: {
                    return 52;
                }
                case 75: {
                    return 132;
                }
                case 6: {
                    return 53;
                }
                case 76: {
                    return 133;
                }
                case 7: {
                    return 54;
                }
                case 77: {
                    return 134;
                }
                case 8: {
                    return 55;
                }
                case 71: {
                    return 135;
                }
                case 9: {
                    return 56;
                }
                case 72: {
                    return 136;
                }
                case 10: {
                    return 57;
                }
                case 73: {
                    return 137;
                }
                case 11: {
                    return 48;
                }
                case 82: {
                    return 128;
                }
                case 12: {
                    return 45;
                }
                case 13: {
                    return 61;
                }
                case 14: {
                    return 8;
                }
                case 15: {
                    return 9;
                }
                case 16: {
                    return 81;
                }
                case 17: {
                    return 87;
                }
                case 18: {
                    return 69;
                }
                case 19: {
                    return 82;
                }
                case 20: {
                    return 84;
                }
                case 21: {
                    return 89;
                }
                case 22: {
                    return 85;
                }
                case 23: {
                    return 73;
                }
                case 24: {
                    return 79;
                }
                case 25: {
                    return 80;
                }
                case 26: {
                    return 40;
                }
                case 27: {
                    return 41;
                }
                case 28:
                case 96: {
                    return 13;
                }
                case 29: {
                    return 17;
                }
                case 30: {
                    return 65;
                }
                case 31: {
                    return 83;
                }
                case 32: {
                    return 68;
                }
                case 33: {
                    return 70;
                }
                case 34: {
                    return 71;
                }
                case 35: {
                    return 72;
                }
                case 36: {
                    return 74;
                }
                case 37: {
                    return 75;
                }
                case 38: {
                    return 76;
                }
                case 39: {
                    return 59;
                }
                case 40: {
                    return 39;
                }
                case 41: {
                    return 96;
                }
                case 42: {
                    return 15;
                }
                case 43: {
                    return 92;
                }
                case 44: {
                    return 90;
                }
                case 45: {
                    return 88;
                }
                case 46: {
                    return 67;
                }
                case 47: {
                    return 86;
                }
                case 48: {
                    return 66;
                }
                case 49: {
                    return 78;
                }
                case 50: {
                    return 77;
                }
                case 51: {
                    return 44;
                }
                case 52: {
                    return 46;
                }
                case 53: {
                    return 47;
                }
                case 54: {
                    return 15;
                }
                case 55: {
                    return 42;
                }
                case 56: {
                    return 18;
                }
                case 57: {
                    return 32;
                }
                case 58: {
                    return 20;
                }
                case 59: {
                    return 97;
                }
                case 60: {
                    return 98;
                }
                case 61: {
                    return 99;
                }
                case 62: {
                    return 100;
                }
                case 63: {
                    return 101;
                }
                case 64: {
                    return 102;
                }
                case 65: {
                    return 103;
                }
                case 66: {
                    return 104;
                }
                case 67: {
                    return 105;
                }
                case 68: {
                    return 106;
                }
                case 69: {
                    return 148;
                }
                case 70: {
                    return 23;
                }
                case 74: {
                    return 45;
                }
                case 78: {
                    return 43;
                }
                case 83: {
                    return 46;
                }
                case 87: {
                    return 107;
                }
                case 88: {
                    return 108;
                }
                case 89: {
                    return -1902;
                }
                case 90: {
                    return -1898;
                }
                case 91: {
                    return -1897;
                }
                case 92: {}
                case 93: {}
                case 94: {}
                case 97: {
                    return 17;
                }
                case 98: {
                    return 47;
                }
                case 100: {
                    return 18;
                }
                case 102: {
                    return 2;
                }
                case 103: {
                    return 150;
                }
                case 104: {
                    return 16;
                }
                case 105: {
                    return 149;
                }
                case 106: {
                    return 151;
                }
                case 107: {
                    return 3;
                }
                case 108: {
                    return 152;
                }
                case 109: {
                    return 11;
                }
                case 110: {
                    return 26;
                }
                case 111: {
                    return 147;
                }
                case 112: {}
                case 113: {}
                case 114: {}
                case 115: {}
                case 117: {
                    return 61;
                }
                case 119: {
                    return 22;
                }
                case 121: {
                    return 44;
                }
                case 122: {}
                case 123: {}
                case 125:
                case 126: {
                    return 155;
                }
                case 127: {
                    return 157;
                }
                case 128: {
                    return 159;
                }
                case 129: {
                    return -1923;
                }
                case 130: {
                    return -1921;
                }
                case 131: {
                    return -1924;
                }
                case 133: {
                    return -1926;
                }
                case 135: {
                    return -1925;
                }
                case 136: {
                    return -1922;
                }
                case 137: {
                    return -1927;
                }
                case 138: {
                    return 156;
                }
                case 139: {}
                case 140: {}
                case 141: {}
                case 142: {}
                case 143: {}
                case 144: {}
                case 145: {}
                case 146: {}
                case 147: {}
                case 148: {}
                case 149: {}
                case 150: {}
                case 151: {}
                case 152: {}
                case 153: {}
                case 154: {}
                case 155: {}
                case 156: {}
                case 157: {}
                case 158: {}
                case 159: {}
                case 160: {}
                case 161: {}
                case 162: {}
                case 163: {}
                case 164: {}
                case 165: {}
                case 166: {}
                case 167: {}
                case 168: {}
                case 169: {}
                case 170: {}
                case 171: {}
                case 172: {}
                case 173: {}
                case 174: {}
                case 175: {}
                case 176: {}
                case 177: {}
                case 179: {
                    return 40;
                }
                case 180: {
                    return 41;
                }
                case 181: {}
                case 183: {
                    return 109;
                }
                case 184: {
                    return 110;
                }
                case 185: {
                    return 111;
                }
                case 186: {
                    return 112;
                }
                case 187: {
                    return 113;
                }
                case 188: {
                    return 114;
                }
                case 189: {
                    return 115;
                }
                case 190: {
                    return 116;
                }
                case 191: {
                    return 117;
                }
                case 192: {
                    return 118;
                }
                case 193: {
                    return 119;
                }
                case 194: {
                    return 120;
                }
                case 200: {}
                case 201: {}
                case 202: {}
                case 203: {}
                case 204: {}
                case 205: {}
                case 206: {}
                case 207: {}
                case 210: {
                    return 5;
                }
                case 211: {}
                case 212: {}
                case 213: {}
                case 214: {}
                case 215: {}
                case 216: {}
                case 217: {}
                case 218: {}
                case 219: {}
                case 220: {}
                case 221: {}
                case 222: {}
                case 223: {}
                case 224: {}
                case 225: {}
                case 226: {}
                case 227: {}
                case 228: {}
                case 229: {}
                case 230: {}
                case 231: {}
                case 232: {}
                case 233: {}
                case 234: {}
                case 235: {}
                case 236: {}
                case 237: {}
                case 238: {}
                case 240: {
                    return 0;
                }
                case 241: {}
                case 242: {}
                case 243: {}
                case 244: {}
                case 245: {}
                case 246: {}
                case 247: {}
            }
            if (Window.DEBUG_KEY_EVENT) {
                System.out.println("TODO LinuxEVKey2NewtVKey: Unmapped EVKey " + n);
            }
            return 0;
        }
    }
}
