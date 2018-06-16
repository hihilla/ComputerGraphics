// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.linux;

import com.jogamp.newt.Screen;
import com.jogamp.newt.Window;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.event.WindowEvent;
import jogamp.newt.WindowImpl;
import jogamp.newt.driver.MouseTracker;
import com.jogamp.newt.event.WindowListener;

public class LinuxMouseTracker implements WindowListener, MouseTracker
{
    private static final LinuxMouseTracker lmt;
    private volatile boolean stop;
    private int x;
    private int y;
    private short buttonDown;
    private int old_x;
    private int old_y;
    private volatile int lastFocusedX;
    private volatile int lastFocusedY;
    private short old_buttonDown;
    private WindowImpl focusedWindow;
    private final MouseDevicePoller mouseDevicePoller;
    
    public LinuxMouseTracker() {
        this.stop = false;
        this.x = 0;
        this.y = 0;
        this.buttonDown = 0;
        this.old_x = 0;
        this.old_y = 0;
        this.lastFocusedX = 0;
        this.lastFocusedY = 0;
        this.old_buttonDown = 0;
        this.focusedWindow = null;
        this.mouseDevicePoller = new MouseDevicePoller();
    }
    
    public static LinuxMouseTracker getSingleton() {
        return LinuxMouseTracker.lmt;
    }
    
    @Override
    public final int getLastX() {
        return this.lastFocusedX;
    }
    
    @Override
    public final int getLastY() {
        return this.lastFocusedY;
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
    
    @Override
    public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
    }
    
    static {
        lmt = new LinuxMouseTracker();
        final InterruptSource.Thread thread = new InterruptSource.Thread(null, LinuxMouseTracker.lmt.mouseDevicePoller, "NEWT-LinuxMouseTracker");
        thread.setDaemon(true);
        thread.start();
    }
    
    class MouseDevicePoller implements Runnable
    {
        @Override
        public void run() {
            final byte[] array = new byte[3];
            final File file = new File("/dev/input/mice");
            file.setReadOnly();
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            while (!LinuxMouseTracker.this.stop) {
                int i = 3;
                while (i > 0) {
                    int read = 0;
                    try {
                        read = fileInputStream.read(array, 0, i);
                    }
                    catch (IOException ex2) {
                        ex2.printStackTrace();
                    }
                    if (read < 0) {
                        LinuxMouseTracker.this.stop = true;
                    }
                    else {
                        i -= read;
                    }
                }
                final boolean b = (array[0] & 0x1) > 0;
                final boolean b2 = (array[0] & 0x2) > 0;
                final boolean b3 = (array[0] & 0x4) > 0;
                final boolean b4 = (array[0] & 0x10) > 0;
                final boolean b5 = (array[0] & 0x20) > 0;
                final boolean b6 = (array[0] & 0x40) > 0;
                final boolean b7 = (array[0] & 0x80) > 0;
                final byte b8 = array[1];
                final byte b9 = array[2];
                LinuxMouseTracker.this.x += b8;
                LinuxMouseTracker.this.y -= b9;
                if (LinuxMouseTracker.this.x < 0) {
                    LinuxMouseTracker.this.x = 0;
                }
                if (LinuxMouseTracker.this.y < 0) {
                    LinuxMouseTracker.this.y = 0;
                }
                LinuxMouseTracker.this.buttonDown = 0;
                if (b) {
                    LinuxMouseTracker.this.buttonDown = 1;
                }
                if (b3) {
                    LinuxMouseTracker.this.buttonDown = 2;
                }
                if (b2) {
                    LinuxMouseTracker.this.buttonDown = 3;
                }
                if (null != LinuxMouseTracker.this.focusedWindow) {
                    final Screen screen = LinuxMouseTracker.this.focusedWindow.getScreen();
                    final int width = screen.getWidth();
                    final int height = screen.getHeight();
                    if (LinuxMouseTracker.this.x >= width) {
                        LinuxMouseTracker.this.x = width - 1;
                    }
                    if (LinuxMouseTracker.this.y >= height) {
                        LinuxMouseTracker.this.y = height - 1;
                    }
                    final int[] convertToPixelUnits = LinuxMouseTracker.this.focusedWindow.convertToPixelUnits(new int[] { LinuxMouseTracker.this.focusedWindow.getX(), LinuxMouseTracker.this.focusedWindow.getY() });
                    final int n = LinuxMouseTracker.this.x - convertToPixelUnits[0];
                    final int n2 = LinuxMouseTracker.this.y - convertToPixelUnits[1];
                    if (LinuxMouseTracker.this.old_x != LinuxMouseTracker.this.x || LinuxMouseTracker.this.old_y != LinuxMouseTracker.this.y) {
                        LinuxMouseTracker.this.lastFocusedX = n;
                        LinuxMouseTracker.this.lastFocusedY = n2;
                        LinuxMouseTracker.this.focusedWindow.sendMouseEvent((short)205, 0, n, n2, (short)0, 0.0f);
                    }
                    if (LinuxMouseTracker.this.old_buttonDown != LinuxMouseTracker.this.buttonDown) {
                        if (0 != LinuxMouseTracker.this.buttonDown) {
                            LinuxMouseTracker.this.focusedWindow.sendMouseEvent((short)203, 0, n, n2, LinuxMouseTracker.this.buttonDown, 0.0f);
                        }
                        else {
                            LinuxMouseTracker.this.focusedWindow.sendMouseEvent((short)204, 0, n, n2, LinuxMouseTracker.this.old_buttonDown, 0.0f);
                        }
                    }
                }
                else if (Window.DEBUG_MOUSE_EVENT) {
                    System.out.println(LinuxMouseTracker.this.x + "/" + LinuxMouseTracker.this.y + ", hs=" + b4 + ",vs=" + b5 + ",lb=" + b + ",rb=" + b2 + ",mb=" + b3 + ",xo=" + b6 + ",yo=" + b7 + "xd=" + b8 + ",yd=" + b9);
                }
                LinuxMouseTracker.this.old_x = LinuxMouseTracker.this.x;
                LinuxMouseTracker.this.old_y = LinuxMouseTracker.this.y;
                LinuxMouseTracker.this.old_buttonDown = LinuxMouseTracker.this.buttonDown;
            }
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                }
                catch (IOException ex3) {
                    ex3.printStackTrace();
                }
            }
        }
    }
}
