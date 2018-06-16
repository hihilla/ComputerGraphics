// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import com.jogamp.opengl.GLEventListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import javax.swing.JFrame;
import edu.cg.models.Empty;
import edu.cg.models.Locomotive1;
import edu.cg.models.Track2;
import java.awt.Frame;
import java.awt.Point;
import edu.cg.models.IRenderable;

public class Main
{
    static IRenderable[] models;
    static Point prevMouse;
    static int currentModel;
    static Frame frame;
    
    static {
        Main.models = new IRenderable[] { new Track2(), new Locomotive1(), new Empty() };
    }
    
    public static void main(final String[] args) {
        Main.frame = new JFrame();
        GLProfile.initSingleton();
        final GLProfile glp = GLProfile.get("GL2");
        final GLCapabilities caps = new GLCapabilities(glp);
        final GLJPanel canvas = new GLJPanel(caps);
        Main.frame.setSize(500, 500);
        Main.frame.setLayout(new BorderLayout());
        Main.frame.add(canvas, "Center");
        final Viewer viewer = new Viewer(canvas);
        viewer.setModel(nextModel());
        canvas.addGLEventListener(viewer);
        Main.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            int num = 0;
            
            @Override
            public void keyPressed(final KeyEvent e) {
                final int code = e.getKeyCode();
                switch (code) {
                    case 80: {
                        viewer.toggleRenderMode();
                        break;
                    }
                    case 65: {
                        viewer.toggleAxes();
                        break;
                    }
                    case 76: {
                        viewer.toggleLightSpheres();
                        break;
                    }
                    case 32:
                    case 77: {
                        viewer.setModel(nextModel());
                        break;
                    }
                    case 67: {
                        viewer.toggleModelCamera();
                        break;
                    }
                    case 38: {
                        viewer.getModel().control(38, null);
                        break;
                    }
                    case 40: {
                        viewer.getModel().control(40, null);
                        break;
                    }
                    case 10: {
                        final int num = this.num;
                        this.num = 0;
                        viewer.getModel().control(10, num);
                        break;
                    }
                    case 27: {
                        System.exit(0);
                        break;
                    }
                }
                if (code >= 48 & code <= 57) {
                    final int n = code - 48;
                    this.num = this.num * 10 + n;
                }
                else if (code >= 96 & code <= 105) {
                    final int n = code - 96;
                    this.num = this.num * 10 + n;
                }
                canvas.repaint();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                viewer.trackball(Main.prevMouse, e.getPoint());
                Main.prevMouse = e.getPoint();
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                Main.prevMouse = e.getPoint();
                super.mousePressed(e);
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
                viewer.trackball(Main.prevMouse, e.getPoint());
                super.mouseReleased(e);
            }
        });
        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                final double rot = e.getWheelRotation();
                viewer.zoom(rot);
                canvas.repaint();
            }
        });
        canvas.setFocusable(true);
        canvas.requestFocus();
        Main.frame.setVisible(true);
        canvas.repaint();
    }
    
    private static IRenderable nextModel() {
        final IRenderable model = Main.models[Main.currentModel++];
        Main.frame.setTitle("Exercise 4 - " + model.toString());
        Main.currentModel %= Main.models.length;
        return model;
    }
}
