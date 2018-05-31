package edu.cg;

import java.awt.Component;
import java.awt.Point;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.IRenderable;

/**
 * An OpenGL model viewer
 */
public class Viewer implements GLEventListener {

    private double zoom = 0.0; //How much to zoom in? >0 mean magnify, <0 means shrink
    private Point mouseFrom, mouseTo; //From where to where was the mouse dragged between the last redraws?
    private boolean isWireframe = false; //Should we display wireframe or not?
    private boolean isAxes = true; //Should we display axes or not?
    private IRenderable model; //Model to display
    private FPSAnimator ani; //This object is responsible to redraw the model with a constant FPS
    private Component glPanel; //We store the OpenGL panel component object to refresh the scene
    private boolean isModelCamera = false; //Whether the camera is relative to the model, rather than the world (ex6)
    private boolean isModelInitialized = false; //Whether model.init() was called.
    private double[] rotationMatrix = new double[]{1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0};

    // Remember the width and height of the canvas for the trackball.
    private int canWidth = 0;
    private int canHeight = 0;

    public Viewer(Component glPanel) {
        this.glPanel = glPanel;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (!isModelInitialized) {
            initModel(gl);
            isModelInitialized = true;
        }
        //TODO: uncomment the following line to clear the window before drawing
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        gl.glClear(GL.GL_CLEAR);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        setupCamera(gl);
        if (isAxes)
            renderAxes(gl);

        // set wireframe mode
        if (isWireframe) {
            // display wireframe
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        } else {
            // don't display wireframe
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        }

        model.render(gl);

        // Polygon mode needs to be set back to GL_FILL to work around a bug on some platforms.
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    private Vec projectPoint(Point p) {
        double x = (2.0 * p.x / canWidth) - 1;
        double y = 1 - (2.0 * p.y / canHeight);
        double z = 2 - Math.pow(x, 2) - Math.pow(y, 2);
        if (z < 0) {
            z = 0;
        }
        z = Math.sqrt(z);
        Vec vecOfMouse = new Vec(x, y, z).normalize();
        return vecOfMouse;
    }


    private void setupCamera(GL2 gl) {
        if (!isModelCamera) { //Camera is in an absolute location
            // place the camera. You should use mouseFrom, mouseTo, canvas width and
            //      height (reshape function), zoom etc. This should actually implement the trackball
            //		and zoom. You might want to store the rotation matrix in an array for next time.
            //		Relevant functions: glGetDoublev, glMultMatrixd
            //      Example: gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, rotationMatrix, 0);
            gl.glLoadIdentity();
            if (this.mouseFrom != null && this.mouseTo != null) {
                Vec projectPointFrom = projectPoint(this.mouseFrom);
                Vec projectPointTo = projectPoint(this.mouseTo);
                Vec axis = projectPointFrom.cross(projectPointTo).normalize();
                if (axis.isFinite()) {
                    double angle = Math.toDegrees(1.0) *
                            Math.acos((double) projectPointFrom.dot(projectPointTo));
                    angle = Double.isFinite(angle) ? angle : 0.0;
                    gl.glRotated(angle, (double) axis.x, (double) axis.y, (double) axis.z);
                }
            }

            gl.glMultMatrixd(rotationMatrix, 0);
            gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, rotationMatrix, 0);
            gl.glLoadIdentity();
            gl.glTranslated(0.0, 0.0, -1.2);
            gl.glTranslated(0.0, 0.0, -this.zoom);
            gl.glMultMatrixd(rotationMatrix, 0);

            //By this point, we should have already changed the point of view, now set these to null
            //so we don't change it again on the next redraw.
            mouseFrom = null;
            mouseTo = null;
        } else { //Camera is relative to the model
            gl.glLoadIdentity();
            model.setCamera(gl);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Initialize display callback timer
        //TODO Uncomment the following lines to create an animator object and attach it to the canvas.
        ani = new FPSAnimator(30, true);
        ani.add(drawable);

        glPanel.repaint();

        initModel(gl);
    }

    public void initModel(GL2 gl) {
        // light model, normal normalization, depth test, back face culling, ...
        gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1.0f); // light model
        gl.glEnable(GL2.GL_NORMALIZE); // normalize
        gl.glEnable(GL2.GL_DEPTH_TEST); // depth test


        gl.glCullFace(GL2.GL_BACK);    // Set Culling Face To Back Face
        gl.glEnable(GL2.GL_CULL_FACE); // Enable back face culling

        model.init(gl);

        if (model.isAnimated()) //Start animation (for ex6)
            startAnimation();
        else
            stopAnimation();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Remember the width and height of the canvas for the trackball.
        canWidth = width;
        canHeight = height;

        GL2 gl = drawable.getGL().getGL2();

        // prevent divide by zero
        if (width == 0) width = 1;
        float aspect = (float) height / width;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustum(-.1, .1, -.1 * aspect, .1 * aspect, .1, 1000);
    }

    /**
     * Stores the mouse coordinates for trackball rotation.
     *
     * @param from 2D canvas point of drag beginning
     * @param to   2D canvas point of drag ending
     */
    public void storeTrackball(Point from, Point to) {
        //The following lines store the rotation for use when next displaying the model.
        //After you redraw the model, you should set these variables back to null.
        if (!isModelCamera) {
            if (null == mouseFrom)
                mouseFrom = from;
            mouseTo = to;
            glPanel.repaint();
        }
    }

    /**
     * Zoom in or out of object. s<0 - zoom out. s>0 zoom in.
     *
     * @param s Scalar
     */
    public void zoom(double s) {
        if (!isModelCamera) {
            zoom += s * 0.1;
            glPanel.repaint();
        }
    }

    /**
     * Toggle rendering method. Either wireframes (lines) or fully shaded
     */
    public void toggleRenderMode() {
        isWireframe = !isWireframe;
        glPanel.repaint();
    }

    /**
     * Toggle whether little spheres are shown at the location of the light sources (ex6).
     */
    public void toggleLightSpheres() {
        model.control(IRenderable.TOGGLE_LIGHT_SPHERES, null);
        glPanel.repaint();
    }

    /**
     * Toggle whether axes are shown.
     */
    public void toggleAxes() {
        isAxes = !isAxes;
        glPanel.repaint();
    }

    public void toggleModelCamera() {
        isModelCamera = !isModelCamera;
        glPanel.repaint();
    }

    /**
     * Start redrawing the scene with 30 FPS
     */
    public void startAnimation() {
        //TODO Uncomment these lines to animate the model
        if (!ani.isAnimating())
            ani.start();
    }

    /**
     * Stop redrawing the scene with 30 FPS
     */
    public void stopAnimation() {
        //TODO Uncomment these lines to stop animating the model
        if (ani.isAnimating())
            ani.stop();
    }

    private void renderAxes(GL2 gl) {
        gl.glLineWidth(2);
        boolean flag = gl.glIsEnabled(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(10, 0, 0);

        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 10, 0);

        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 10);

        gl.glEnd();
        if (flag)
            gl.glEnable(GL2.GL_LIGHTING);
    }

    public void setModel(IRenderable model) {
        this.model = model;
        isModelInitialized = false;
    }
}