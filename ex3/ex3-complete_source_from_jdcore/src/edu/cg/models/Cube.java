package edu.cg.models;

import com.jogamp.opengl.GL2;
import java.io.PrintStream;

public class Cube implements IRenderable
{
  private boolean isLightSpheres;
  
  public Cube() {}
  
  public void render(GL2 gl)
  {
    drawRGBCube(gl);
  }
  



  private void drawRGBCube(GL2 gl)
  {
    double r = 0.5D;
    
    boolean lightingFlag = gl.glIsEnabled(2896);
    gl.glDisable(2896);
    
    gl.glBegin(7);
    gl.glColor3d(0.0D, 0.0D, 1.0D);
    gl.glVertex3d(-r, -r, r);
    gl.glColor3d(1.0D, 0.0D, 1.0D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(1.0D, 1.0D, 1.0D);
    gl.glVertex3d(r, r, r);
    gl.glColor3d(0.0D, 1.0D, 1.0D);
    gl.glVertex3d(-r, r, r);
    
    gl.glColor3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(0.0D, 0.0D, 1.0D);
    gl.glVertex3d(-r, -r, r);
    gl.glColor3d(0.0D, 1.0D, 1.0D);
    gl.glVertex3d(-r, r, r);
    gl.glColor3d(0.0D, 1.0D, 0.0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(1.0D, 0.0D, 1.0D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(1.0D, 0.0D, 0.0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(1.0D, 1.0D, 0.0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(1.0D, 1.0D, 1.0D);
    gl.glVertex3d(r, r, r);
    
    gl.glColor3d(1.0D, 1.0D, 0.0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(1.0D, 0.0D, 0.0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(0.0D, 1.0D, 0.0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(0.0D, 1.0D, 1.0D);
    gl.glVertex3d(-r, r, r);
    gl.glColor3d(1.0D, 1.0D, 1.0D);
    gl.glVertex3d(r, r, r);
    gl.glColor3d(1.0D, 1.0D, 0.0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(0.0D, 1.0D, 0.0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(1.0D, 0.0D, 0.0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(1.0D, 0.0D, 1.0D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(0.0D, 0.0D, 1.0D);
    gl.glVertex3d(-r, -r, r);
    
    gl.glEnd();
    
    if (lightingFlag) {
      gl.glEnable(2896);
    }
  }
  
  public String toString() {
    return "Cube";
  }
  


  public void control(int type, Object params)
  {
    switch (type)
    {
    case 0: 
      isLightSpheres = (!isLightSpheres);
      break;
    
    default: 
      System.out.println("Control type not supported: " + toString() + ", " + type);
    }
  }
  
  public boolean isAnimated()
  {
    return false;
  }
  
  public void init(GL2 gl) {}
  
  public void setCamera(GL2 gl) {}
}
