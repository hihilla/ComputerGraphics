// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.algebra.Vec;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.awt.Component;
import com.jogamp.opengl.util.FPSAnimator;
import edu.cg.models.IRenderable;
import java.awt.Point;
import com.jogamp.opengl.GLEventListener;

public class Viewer implements GLEventListener
{
    private double zoom;
    private Point mouseFrom;
    private Point mouseTo;
    private int canvasWidth;
    private int canvasHeight;
    private boolean isWireframe;
    private boolean isAxes;
    private IRenderable model;
    private FPSAnimator ani;
    private double[] rotationMatrix;
    private Component glPanel;
    private boolean isModelCamera;
    private boolean isModelInitialized;
    
    public Viewer(final Component glPanel) {
        this.isWireframe = false;
        this.isAxes = true;
        this.model = null;
        this.rotationMatrix = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
        this.isModelCamera = false;
        this.isModelInitialized = false;
        this.glPanel = glPanel;
        this.zoom = 0.0;
    }
    
    @Override
    public void display(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        if (!this.isModelInitialized) {
            this.initModel(gl);
            this.isModelInitialized = true;
        }
        gl.glClearColor(0.0f, 0.5f, 1.0f, 1.0f);
        gl.glClear(16640);
        gl.glMatrixMode(5888);
        this.setupCamera(gl);
        if (this.isAxes) {
            this.renderAxes(gl);
        }
        if (this.isWireframe) {
            gl.glPolygonMode(1032, 6913);
        }
        else {
            gl.glPolygonMode(1032, 6914);
        }
        this.model.render(gl);
        gl.glPolygonMode(1032, 6914);
    }
    
    private Vec mousePointToVec(final Point pt) {
        final double x = 2 * pt.x / this.canvasWidth - 1.0;
        final double y = 1.0 - 2 * pt.y / this.canvasHeight;
        double z2 = 2.0 - x * x - y * y;
        if (z2 < 0.0) {
            z2 = 0.0;
        }
        final double z3 = Math.sqrt(z2);
        return new Vec(x, y, z3).normalize();
    }
    
    private void setupCamera(final GL2 gl) {
        if (!this.isModelCamera) {
            gl.glLoadIdentity();
            if (this.mouseFrom != null && this.mouseTo != null) {
                final Vec from = this.mousePointToVec(this.mouseFrom);
                final Vec to = this.mousePointToVec(this.mouseTo);
                final Vec axis = from.cross(to).normalize();
                if (axis.isFinite()) {
                    axis.normalize();
                    final double angle = 57.29577951308232 * Math.acos(from.dot(to));
                    gl.glRotated(angle, axis.x, axis.y, axis.z);
                }
            }
            gl.glMultMatrixd(this.rotationMatrix, 0);
            gl.glGetDoublev(2982, this.rotationMatrix, 0);
            gl.glLoadIdentity();
            gl.glTranslated(0.0, 0.0, -1.2);
            gl.glTranslated(0.0, 0.0, -this.zoom);
            gl.glMultMatrixd(this.rotationMatrix, 0);
            this.mouseFrom = null;
            this.mouseTo = null;
        }
        else {
            gl.glLoadIdentity();
            this.model.setCamera(gl);
        }
    }
    
    @Override
    public void dispose(final GLAutoDrawable drawable) {
    }
    
    @Override
    public void init(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        (this.ani = new FPSAnimator(30, true)).add(drawable);
        this.glPanel.repaint();
        this.initModel(gl);
    }
    
    public void initModel(final GL2 gl) {
        gl.glCullFace(1029);
        gl.glEnable(2884);
        gl.glEnable(2977);
        gl.glEnable(2929);
        gl.glLightModelf(2898, 1.0f);
        gl.glEnable(2896);
        this.model.init(gl);
        if (this.model.isAnimated()) {
            this.startAnimation();
        }
        else {
            this.stopAnimation();
        }
    }
    
    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
        final GL2 gl = drawable.getGL().getGL2();
        this.canvasWidth = width;
        this.canvasHeight = height;
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        gl.glFrustum(-0.1, 0.1, -0.1 * height / width, 0.1 * height / width, 0.1, 1000.0);
    }
    
    public void trackball(final Point from, final Point to) {
        if (!this.isModelCamera) {
            if (this.mouseFrom == null) {
                this.mouseFrom = from;
            }
            this.mouseTo = to;
            this.glPanel.repaint();
        }
    }
    
    public void zoom(final double s) {
        if (!this.isModelCamera) {
            this.zoom += s * 0.1;
            this.glPanel.repaint();
        }
    }
    
    public void toggleRenderMode() {
        this.isWireframe = !this.isWireframe;
        this.glPanel.repaint();
    }
    
    public void toggleLightSpheres() {
        this.model.control(0, null);
        this.glPanel.repaint();
    }
    
    public void toggleAxes() {
        this.isAxes = !this.isAxes;
        this.glPanel.repaint();
    }
    
    public void toggleModelCamera() {
        this.isModelCamera = !this.isModelCamera;
        this.glPanel.repaint();
    }
    
    public void startAnimation() {
        if (!this.ani.isAnimating()) {
            this.ani.start();
        }
    }
    
    public void stopAnimation() {
        if (this.ani.isAnimating()) {
            this.ani.stop();
        }
    }
    
    private void renderAxes(final GL2 gl) {
        gl.glLineWidth(2.0f);
        final boolean flag = gl.glIsEnabled(2896);
        gl.glDisable(2896);
        gl.glBegin(1);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(1.0, 0.0, 0.0);
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 1.0, 0.0);
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 1.0);
        gl.glEnd();
        if (flag) {
            gl.glEnable(2896);
        }
    }
    
    public void setModel(final IRenderable model) {
        if (this.model != null) {
            final GL2 gl = ((GLAutoDrawable)this.glPanel).getGL().getGL2();
            this.model.destroy(gl);
        }
        this.model = model;
        this.isModelInitialized = false;
    }
    
    public IRenderable getModel() {
        return this.model;
    }
}
