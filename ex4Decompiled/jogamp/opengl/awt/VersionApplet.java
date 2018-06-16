// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.awt;

import com.jogamp.opengl.GLAutoDrawable;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.List;
import com.jogamp.opengl.GLEventListener;
import java.awt.GridLayout;
import java.awt.Container;
import com.jogamp.common.os.Platform;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import java.awt.Component;
import java.awt.event.WindowListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Frame;
import com.jogamp.opengl.awt.GLCanvas;
import java.awt.TextArea;
import java.applet.Applet;

public class VersionApplet extends Applet
{
    TextArea tareaVersion;
    TextArea tareaCaps;
    GLCanvas canvas;
    
    public static void main(final String[] array) {
        final Frame frame = new Frame("JOGL Version Applet");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        final VersionApplet versionApplet = new VersionApplet();
        frame.addWindowListener(new ClosingWindowAdapter(frame, versionApplet));
        versionApplet.init();
        frame.add(versionApplet, "Center");
        frame.validate();
        frame.setVisible(true);
        versionApplet.start();
    }
    
    private synchronized void my_init() {
        if (null != this.canvas) {
            return;
        }
        this.setEnabled(true);
        final GLProfile default1 = GLProfile.getDefault();
        final GLCapabilities glCapabilities = new GLCapabilities(default1);
        this.setLayout(new BorderLayout());
        this.tareaVersion = new TextArea(120, 60);
        final String string = VersionUtil.getPlatformInfo().toString();
        System.err.println(string);
        this.tareaVersion.append(string);
        final String string2 = GlueGenVersion.getInstance().toString();
        System.err.println(string2);
        this.tareaVersion.append(string2);
        final String string3 = JoglVersion.getInstance().toString();
        System.err.println(string3);
        this.tareaVersion.append(string3);
        this.tareaCaps = new TextArea(120, 20);
        final List<GLCapabilitiesImmutable> availableCapabilities = GLDrawableFactory.getFactory(default1).getAvailableCapabilities(null);
        for (int i = 0; i < availableCapabilities.size(); ++i) {
            final String string4 = availableCapabilities.get(i).toString();
            System.err.println(string4);
            this.tareaCaps.append(string4);
            this.tareaCaps.append(Platform.getNewline());
        }
        final Container container = new Container();
        container.setLayout(new GridLayout(2, 1));
        container.add(this.tareaVersion);
        container.add(this.tareaCaps);
        this.add(container, "Center");
        (this.canvas = new GLCanvas(glCapabilities)).addGLEventListener(new GLInfo());
        this.canvas.setSize(10, 10);
        this.add(this.canvas, "South");
        this.validate();
    }
    
    private synchronized void my_release() {
        if (null != this.canvas) {
            this.remove(this.canvas);
            this.canvas.destroy();
            this.canvas = null;
            this.remove(this.tareaVersion.getParent());
            this.tareaVersion = null;
            this.tareaCaps = null;
            this.setEnabled(false);
        }
    }
    
    @Override
    public void init() {
        System.err.println("VersionApplet: init() - begin");
        this.my_init();
        System.err.println("VersionApplet: init() - end");
    }
    
    @Override
    public void start() {
        System.err.println("VersionApplet: start() - begin");
        this.canvas.setVisible(true);
        System.err.println("VersionApplet: start() - end");
    }
    
    @Override
    public void stop() {
        System.err.println("VersionApplet: stop() - begin");
        this.canvas.setVisible(false);
        System.err.println("VersionApplet: stop() - end");
    }
    
    @Override
    public void destroy() {
        System.err.println("VersionApplet: destroy() - start");
        this.my_release();
        System.err.println("VersionApplet: destroy() - end");
    }
    
    static class ClosingWindowAdapter extends WindowAdapter
    {
        Frame f;
        VersionApplet va;
        
        public ClosingWindowAdapter(final Frame f, final VersionApplet va) {
            this.f = f;
            this.va = va;
        }
        
        @Override
        public void windowClosing(final WindowEvent windowEvent) {
            this.f.setVisible(false);
            this.va.stop();
            this.va.destroy();
            this.f.remove(this.va);
            this.f.dispose();
            System.exit(0);
        }
    }
    
    class GLInfo implements GLEventListener
    {
        @Override
        public void init(final GLAutoDrawable glAutoDrawable) {
            final String string = JoglVersion.getGLInfo(glAutoDrawable.getGL(), null).toString();
            System.err.println(string);
            VersionApplet.this.tareaVersion.append(string);
        }
        
        @Override
        public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        }
        
        @Override
        public void display(final GLAutoDrawable glAutoDrawable) {
        }
        
        @Override
        public void dispose(final GLAutoDrawable glAutoDrawable) {
        }
    }
}
