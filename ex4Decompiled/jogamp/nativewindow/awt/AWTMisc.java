// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.awt;

import java.awt.image.BufferedImage;
import jogamp.nativewindow.jawt.JAWTUtil;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.WindowClosingProtocol;
import java.awt.Image;
import java.awt.Toolkit;
import com.jogamp.nativewindow.util.PixelFormatUtil;
import com.jogamp.nativewindow.util.PixelFormat;
import java.util.Hashtable;
import com.jogamp.nativewindow.awt.DirectDataBufferInt;
import com.jogamp.nativewindow.util.PixelRectangle;
import javax.swing.MenuSelectionManager;
import java.awt.FocusTraversalPolicy;
import com.jogamp.nativewindow.util.Point;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Window;
import java.awt.Frame;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Cursor;
import java.util.HashMap;

public class AWTMisc
{
    static final HashMap<Integer, Cursor> cursorMap;
    static final Cursor nulCursor;
    
    public static JFrame getJFrame(Component parent) {
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        return (JFrame)parent;
    }
    
    public static Frame getFrame(Component parent) {
        while (parent != null && !(parent instanceof Frame)) {
            parent = parent.getParent();
        }
        return (Frame)parent;
    }
    
    public static Window getWindow(Component parent) {
        while (parent != null && !(parent instanceof Window)) {
            parent = parent.getParent();
        }
        return (Window)parent;
    }
    
    public static Container getContainer(Component parent) {
        while (parent != null && !(parent instanceof Container)) {
            parent = parent.getParent();
        }
        return (Container)parent;
    }
    
    public static Insets getInsets(final Component component, final boolean b) {
        if (component instanceof Window) {
            return ((Window)component).getInsets();
        }
        if (component instanceof JRootPane) {
            final Window window = getWindow(component);
            if (null != window) {
                return window.getInsets();
            }
            return ((JRootPane)component).getInsets();
        }
        else {
            if (!b && component instanceof JComponent) {
                return ((JComponent)component).getInsets();
            }
            return null;
        }
    }
    
    public static Point getLocationOnScreenSafe(Point point, final Component component, final boolean b) {
        if (!Thread.holdsLock(component.getTreeLock())) {
            if (null == point) {
                point = new Point();
            }
            getLocationOnScreenNonBlocking(point, component, b);
            return point;
        }
        final java.awt.Point locationOnScreen = component.getLocationOnScreen();
        Point translate;
        if (null != point) {
            translate = point.translate(locationOnScreen.x, locationOnScreen.y);
        }
        else {
            translate = new Point(locationOnScreen.x, locationOnScreen.y);
        }
        return translate;
    }
    
    public static Component getLocationOnScreenNonBlocking(final Point point, Component parent, final boolean b) {
        final Insets insets = new Insets(0, 0, 0, 0);
        Component component = null;
        while (null != parent) {
            final int x = parent.getX();
            final int y = parent.getY();
            if (b) {
                final Insets insets2 = getInsets(parent, false);
                if (null != insets2) {
                    final Insets insets3 = insets;
                    insets3.bottom += insets2.bottom;
                    final Insets insets4 = insets;
                    insets4.top += insets2.top;
                    final Insets insets5 = insets;
                    insets5.left += insets2.left;
                    final Insets insets6 = insets;
                    insets6.right += insets2.right;
                }
                System.err.print("LOS: " + point + " + " + parent.getClass().getName() + "[" + x + "/" + y + ", vis " + parent.isVisible() + ", ins " + insets2 + " -> " + insets + "] -> ");
            }
            point.translate(x, y);
            if (b) {
                System.err.println(point);
            }
            component = parent;
            if (parent instanceof Window) {
                break;
            }
            parent = parent.getParent();
        }
        return component;
    }
    
    public static int performAction(final Container container, final Class<?> clazz, final ComponentAction componentAction) {
        int n = 0;
        for (int componentCount = container.getComponentCount(), i = 0; i < componentCount; ++i) {
            final Component component = container.getComponent(i);
            if (component instanceof Container) {
                n += performAction((Container)component, clazz, componentAction);
            }
            else if (clazz.isInstance(component)) {
                componentAction.run(component);
                ++n;
            }
        }
        if (clazz.isInstance(container)) {
            componentAction.run(container);
            ++n;
        }
        return n;
    }
    
    public static Component getNextFocus(Component component, final boolean b) {
        Container container;
        for (container = component.getFocusCycleRootAncestor(); container != null && (!container.isShowing() || !container.isFocusable() || !container.isEnabled()); container = component.getFocusCycleRootAncestor()) {
            component = container;
        }
        Component defaultComponent = null;
        if (container != null) {
            final FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
            defaultComponent = (b ? focusTraversalPolicy.getComponentAfter(container, component) : focusTraversalPolicy.getComponentBefore(container, component));
            if (defaultComponent == null) {
                defaultComponent = focusTraversalPolicy.getDefaultComponent(container);
            }
        }
        return defaultComponent;
    }
    
    public static void clearAWTMenus() {
        MenuSelectionManager.defaultManager().clearSelectedPath();
    }
    
    public static synchronized Cursor getNullCursor() {
        return AWTMisc.nulCursor;
    }
    
    public static synchronized Cursor getCursor(final PixelRectangle pixelRectangle, final java.awt.Point point) {
        final int n = 31 + pixelRectangle.hashCode();
        final Integer value = (n << 5) - n + point.hashCode();
        Cursor cursor = AWTMisc.cursorMap.get(value);
        if (null == cursor) {
            cursor = createCursor(pixelRectangle, point);
            AWTMisc.cursorMap.put(value, cursor);
        }
        return cursor;
    }
    
    private static synchronized Cursor createCursor(final PixelRectangle pixelRectangle, final java.awt.Point point) {
        final int width = pixelRectangle.getSize().getWidth();
        final DirectDataBufferInt.BufferedImageInt bufferedImage = DirectDataBufferInt.createBufferedImage(width, pixelRectangle.getSize().getHeight(), 2, null, null);
        PixelFormatUtil.convert(pixelRectangle, bufferedImage.getDataBuffer().getDataBytes(), PixelFormat.BGRA8888, false, width * 4);
        return Toolkit.getDefaultToolkit().createCustomCursor(bufferedImage, point, pixelRectangle.toString());
    }
    
    public static WindowClosingProtocol.WindowClosingMode AWT2NWClosingOperation(final int n) {
        switch (n) {
            case 2:
            case 3: {
                return WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE;
            }
            case 0:
            case 1: {
                return WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE;
            }
            default: {
                throw new NativeWindowException("Unhandled AWT Closing Operation: " + n);
            }
        }
    }
    
    public static WindowClosingProtocol.WindowClosingMode getNWClosingOperation(final Component component) {
        final JFrame jFrame = getJFrame(component);
        return AWT2NWClosingOperation((null != jFrame) ? jFrame.getDefaultCloseOperation() : 0);
    }
    
    static {
        cursorMap = new HashMap<Integer, Cursor>();
        Cursor customCursor = null;
        if (!JAWTUtil.isHeadlessMode()) {
            try {
                customCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, 6), new java.awt.Point(0, 0), "nullCursor");
            }
            catch (Exception ex) {
                if (JAWTUtil.DEBUG) {
                    System.err.println("Caught exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        nulCursor = customCursor;
    }
    
    public interface ComponentAction
    {
        void run(final Component p0);
    }
}
