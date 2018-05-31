package edu.cg;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import edu.cg.algebra.Vec;
import edu.cg.models.IRenderable;
import java.awt.Component;
import java.awt.Point;

public class Viewer
  implements GLEventListener {
  private double zoom;
  private Point mouseFrom;
  private Point mouseTo;
  private int canvasWidth;
  private int canvasHeight;
  private boolean isWireframe = false;
  private boolean isAxes = true;
  private IRenderable model;
  private FPSAnimator ani;
  private double[] rotationMatrix = { 1.0D, 0.0D, 0.0D, 0.0D, 
    0.0D, 1.0D, 0.0D, 0.0D, 
    0.0D, 0.0D, 1.0D, 0.0D, 
    0.0D, 0.0D, 0.0D, 1.0D };
  
  private Component glPanel;
  private boolean isModelCamera = false;
  private boolean isModelInitialized = false;
  
  public Viewer(Component glPanel) {
    this.glPanel = glPanel;
    zoom = 0.0D;
  }
  
  public void display(GLAutoDrawable drawable)
  {
    GL2 gl = drawable.getGL().getGL2();
    if (!isModelInitialized) {
      initModel(gl);
      isModelInitialized = true;
    }
    

    gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
    gl.glClear(16640);
    gl.glMatrixMode(5888);
    
    setupCamera(gl);
    if (isAxes) {
      renderAxes(gl);
    }
    if (isWireframe) {
      gl.glPolygonMode(1032, 6913);
    } else {
      gl.glPolygonMode(1032, 6914);
    }
    model.render(gl);
    

    gl.glPolygonMode(1032, 6914);
  }

  private Vec mousePointToVec(Point pt) {
    double x = 2 * pt.x / canvasWidth - 1.0D;
    double y = 1.0D - 2 * pt.y / canvasHeight;
    double z2 = 2.0D - x * x - y * y;
    if (z2 < 0.0D)
      z2 = 0.0D;
    double z = Math.sqrt(z2);
    return new Vec(x, y, z).normalize();
  }
  
  private void setupCamera(GL2 gl) {
    if (!isModelCamera)
    {
      gl.glLoadIdentity();
      if ((mouseFrom != null) && (mouseTo != null)) {
        Vec from = mousePointToVec(mouseFrom);
        Vec to = mousePointToVec(mouseTo);
        Vec axis = from.cross(to).normalize();
        if (axis.isFinite()) {
          double angle = 57.29577951308232D * Math.acos(from.dot(to));
          angle = Double.isFinite(angle) ? angle : 0.0D;
          gl.glRotated(angle, from.x, from.y, from.z);
        }
      }
      gl.glMultMatrixd(rotationMatrix, 0);
      gl.glGetDoublev(2982, rotationMatrix, 0);
      
      gl.glLoadIdentity();
      gl.glTranslated(0.0D, 0.0D, -1.2D);
      gl.glTranslated(0.0D, 0.0D, -zoom);
      gl.glMultMatrixd(rotationMatrix, 0);

      mouseFrom = null;
      mouseTo = null;
    } else {
      gl.glLoadIdentity();
      model.setCamera(gl);
    }
  }
  


  public void dispose(GLAutoDrawable drawable) {}
  

  public void init(GLAutoDrawable drawable)
  {
    GL2 gl = drawable.getGL().getGL2();
    

    ani = new FPSAnimator(30, true);
    ani.add(drawable);
    
    glPanel.repaint();
    
    initModel(gl);
  }
  
  public void initModel(GL2 gl) {
    gl.glCullFace(1029);
    gl.glEnable(2884);
    
    gl.glEnable(2977);
    gl.glEnable(2929);
    gl.glLightModelf(2898, 1.0F);
    gl.glEnable(2896);
    
    model.init(gl);
    

    if (model.isAnimated()) {
      startAnimation();
    } else {
      stopAnimation();
    }
  }
  
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL2 gl = drawable.getGL().getGL2();
    
    canvasWidth = width;
    canvasHeight = height;
    
    gl.glMatrixMode(5889);
    gl.glLoadIdentity();
    

    gl.glFrustum(-0.1D, 0.1D, -0.1D * height / width, 0.1D * height / width, 0.1D, 1000.0D);
  }

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
  

  public void zoom(double s)
  {
    if (!isModelCamera) {
      zoom += s * 0.1D;
      
      glPanel.repaint();
    }
  }
  


  public void toggleRenderMode()
  {
    isWireframe = (!isWireframe);
    
    glPanel.repaint();
  }
  


  public void toggleLightSpheres()
  {
    model.control(0, null);
    
    glPanel.repaint();
  }
  


  public void toggleAxes()
  {
    isAxes = (!isAxes);
    
    glPanel.repaint();
  }
  
  public void toggleModelCamera() {
    isModelCamera = (!isModelCamera);
    
    glPanel.repaint();
  }
  


  public void startAnimation()
  {
    if (!ani.isAnimating()) {
      ani.start();
    }
  }
  

  public void stopAnimation()
  {
    if (ani.isAnimating()) {
      ani.stop();
    }
  }
  
  private void renderAxes(GL2 gl) {
    gl.glLineWidth(2.0F);
    boolean flag = gl.glIsEnabled(2896);
    gl.glDisable(2896);
    gl.glBegin(1);
    gl.glColor3d(1.0D, 0.0D, 0.0D);
    gl.glVertex3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(1.0D, 0.0D, 0.0D);
    
    gl.glColor3d(0.0D, 1.0D, 0.0D);
    gl.glVertex3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(0.0D, 1.0D, 0.0D);
    
    gl.glColor3d(0.0D, 0.0D, 1.0D);
    gl.glVertex3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(0.0D, 0.0D, 1.0D);
    
    gl.glEnd();
    if (flag)
      gl.glEnable(2896);
  }
  
  public void setModel(IRenderable model) {
    this.model = model;
    isModelInitialized = false;
  }
}
