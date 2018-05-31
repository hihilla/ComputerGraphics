package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;

public class Bus implements IRenderable
{
  public Bus() {}
  
  private boolean isLightSpheres = true;
  private static final double EPS = 0.002D;
  private static final float[] black = { 0.0F, 0.0F, 0.0F };
  private static final float[] white = { 1.0F, 1.0F, 1.0F };
  
  public void render(GL2 gl) {
    gl.glTranslated(0.2D, 0.0D, 0.0D);
    gl.glLineWidth(4.0F);
    
    gl.glMaterialfv(1028, 5633, new float[] { 10.0F, 10.0F, 10.0F, 1.0F }, 0);
    
    float[] light0Pos = { 0.6F, 0.4F, -0.6F, 1.0F };
    float[] light0Color = { 1.0F, 1.0F, 0.5F, 1.0F };
    gl.glLightfv(16384, 4608, black, 0);
    gl.glLightfv(16384, 4609, light0Color, 0);
    gl.glLightfv(16384, 4610, light0Color, 0);
    gl.glLightfv(16384, 4611, light0Pos, 0);
    gl.glEnable(16384);
    float[] light1Pos = { -1.0F, 0.3F, 0.6F, 1.0F };
    float[] light1Color = { 1.0F, 1.0F, 1.0F, 1.0F };
    gl.glLightfv(16385, 4609, light1Color, 0);
    gl.glLightfv(16385, 4610, light1Color, 0);
    gl.glLightfv(16385, 4611, light1Pos, 0);
    gl.glEnable(16385);
    
    GLU glu = new GLU();
    GLUquadric quad = glu.gluNewQuadric();
    
    if (isLightSpheres) {
      boolean lightFlag = gl.glIsEnabled(2896);
      gl.glDisable(2896);
      gl.glPushMatrix();
      gl.glTranslated(light0Pos[0], light0Pos[1], light0Pos[2]);
      gl.glColor4fv(light0Color, 0);
      glu.gluSphere(quad, 0.02D, 6, 3);
      gl.glPopMatrix();
      gl.glPushMatrix();
      gl.glTranslated(light1Pos[0], light1Pos[1], light1Pos[2]);
      gl.glColor4fv(light1Color, 0);
      glu.gluSphere(quad, 0.02D, 6, 3);
      gl.glPopMatrix();
      if (lightFlag) {
        gl.glEnable(2896);
      }
    }
    drawChassis(gl);
    drawWheels(gl, glu, quad);
    drawLights(gl, glu, quad);
    drawRoof(gl, glu, quad);
    
    glu.gluDeleteQuadric(quad);
  }
  
  private void drawQuadZ(GL2 gl, double x1, double x2, double y1, double y2, double z) {
    double a = x2 - x1;
    double b = y2 - y1;
    double l = 2.0D * z * Math.sqrt(a * a + b * b);
    gl.glNormal3d(-2.0D * b * z / l, 2.0D * a * z / l, 0.0D);
    gl.glBegin(7);
    gl.glVertex3d(x1, y1, -z);
    gl.glVertex3d(x1, y1, z);
    gl.glVertex3d(x2, y2, z);
    gl.glVertex3d(x2, y2, -z);
    gl.glEnd();
  }
  
  private void setMaterialRoof(GL2 gl) {
    gl.glColor3fv(black, 0);
    gl.glMaterialfv(1028, 4609, black, 0);
    gl.glMaterialfv(1028, 4610, white, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialChassis(GL2 gl) {
    float[] col = { 1.0F, 0.77F, 0.0F };
    gl.glColor3fv(white, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, white, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialWindowOrDoor(GL2 gl) {
    float[] col = { 0.1F, 0.1F, 0.2F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, col, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialWheelOuter(GL2 gl) {
    float[] col = { 0.05F, 0.05F, 0.05F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialWheelInner(GL2 gl) { float[] col = { 0.8F, 0.8F, 0.8F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialLightCase(GL2 gl) {
    float[] col = { 0.2F, 0.2F, 0.2F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialFrontLight(GL2 gl) {
    float[] col = { 0.9F, 0.9F, 0.8F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
    gl.glMaterialfv(1028, 5632, col, 0);
  }
  


  private void drawChassis(GL2 gl)
  {
    setMaterialChassis(gl);
    
    drawQuadZ(gl, 0.5D, -0.8D, -0.2D, -0.2D, 0.2D);
    drawQuadZ(gl, -0.8D, -0.6D, 0.0D, 0.0D, 0.2D);
    drawQuadZ(gl, -0.6D, 0.5D, 0.2D, 0.2D, 0.2D);
    drawQuadZ(gl, -0.6D, -0.6D, 0.0D, 0.2D, 0.2D);
    drawQuadZ(gl, 0.5D, 0.5D, 0.2D, -0.2D, 0.2D);
    drawQuadZ(gl, -0.8D, -0.8D, -0.2D, 0.0D, 0.2D);
    
    drawLeftSide(gl);
    drawRightSide(gl);
    drawFrontWindow(gl);
    drawBackWindow(gl);
  }
  
  private void drawRoof(GL2 gl, GLU glu, GLUquadric quad) {
    double l = 1.096D;
    setMaterialRoof(gl);
    gl.glPushMatrix();
    gl.glTranslated(-0.598D, 0.2D, 0.0D);
    gl.glScaled(1.0D, 0.25D, 1.0D);
    gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
    glu.gluCylinder(quad, 0.2D, 0.2D, 1.096D, 20, 1);
    gl.glTranslated(0.0D, 0.0D, 1.096D);
    glu.gluDisk(quad, 0.0D, 0.2D, 20, 1);
    gl.glTranslated(0.0D, 0.0D, -1.096D);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    glu.gluDisk(quad, 0.0D, 0.2D, 20, 1);
    gl.glPopMatrix();
  }
  
  private void drawWindowOrDoor(GL2 gl) {
    setMaterialWindowOrDoor(gl);
    gl.glNormal3d(0.0D, 0.0D, 1.0D);
    gl.glBegin(7);
    gl.glVertex3d(0.0D, 0.0D, 0.0D);
    gl.glVertex3d(0.1D, 0.0D, 0.0D);
    gl.glVertex3d(0.1D, 0.1D, 0.0D);
    gl.glVertex3d(0.0D, 0.1D, 0.0D);
    gl.glEnd();
  }
  
  private void drawLeftSide(GL2 gl) {
    drawLeftSideWithoutFrontWindow(gl);
    gl.glPushMatrix();
    gl.glTranslated(-0.45D, -0.05D, 0.202D);
    gl.glScaled(1.5D, 2.0D, 1.0D);
    drawWindowOrDoor(gl);
    gl.glPopMatrix();
  }
  
  private void drawLeftSideWithoutFrontWindow(GL2 gl) {
    setMaterialChassis(gl);
    gl.glNormal3d(0.0D, 0.0D, 1.0D);
    gl.glBegin(7);
    gl.glVertex3d(-0.8D, -0.2D, 0.2D);
    gl.glVertex3d(-0.6D, -0.2D, 0.2D);
    gl.glVertex3d(-0.6D, 0.0D, 0.2D);
    gl.glVertex3d(-0.8D, 0.0D, 0.2D);
    
    gl.glVertex3d(-0.6D, -0.2D, 0.2D);
    gl.glVertex3d(0.5D, -0.2D, 0.2D);
    gl.glVertex3d(0.5D, 0.2D, 0.2D);
    gl.glVertex3d(-0.6D, 0.2D, 0.2D);
    gl.glEnd();
    
    gl.glPushMatrix();
    gl.glScaled(1.5D, 2.0D, 1.0D);
    gl.glTranslated(-0.13333333333333333D, -0.025D, 0.202D);
    
    drawWindowOrDoor(gl);
    gl.glTranslated(0.16666666666666666D, 0.0D, 0.0D);
    drawWindowOrDoor(gl);
    gl.glTranslated(0.16666666666666666D, 0.0D, 0.0D);
    drawWindowOrDoor(gl);
    
    gl.glPopMatrix();
  }
  
  private void drawFrontWindow(GL2 gl) {
    gl.glPushMatrix();
    gl.glScaled(1.0D, 1.5D, 3.0D);
    gl.glTranslated(-0.602D, 0.0D, -0.05D);
    gl.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
    drawWindowOrDoor(gl);
    gl.glPopMatrix();
  }
  
  private void drawBackWindow(GL2 gl) {
    gl.glPushMatrix();
    gl.glTranslated(0.0D, -0.05D, 0.0D);
    gl.glScaled(1.0D, 2.0D, 3.0D);
    gl.glTranslated(0.502D, 0.0D, 0.05D);
    gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
    drawWindowOrDoor(gl);
    gl.glPopMatrix();
  }
  
  private void drawRightSide(GL2 gl) {
    gl.glFrontFace(2304);
    gl.glScaled(1.0D, 1.0D, -1.0D);
    drawLeftSideWithoutFrontWindow(gl);
    
    gl.glPushMatrix();
    gl.glTranslated(-0.45D, -0.2D, 0.202D);
    gl.glScaled(1.5D, 3.5D, 1.0D);
    drawWindowOrDoor(gl);
    gl.glPopMatrix();
    
    gl.glScaled(1.0D, 1.0D, -1.0D);
    gl.glFrontFace(2305);
  }
  
  private void drawWheels(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glPushMatrix();
    gl.glTranslated(-0.6D, -0.2D, 0.0D);
    drawWheelPair(gl, glu, quad);
    gl.glTranslated(0.8D, 0.0D, 0.0D);
    drawWheelPair(gl, glu, quad);
    gl.glPopMatrix();
  }
  
  private void drawWheelPair(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glPushMatrix();
    gl.glTranslated(0.0D, 0.0D, -0.2D);
    drawWheel(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, 0.4D);
    drawWheel(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, -0.2D);
    gl.glPopMatrix();
  }
  
  private void drawWheel(GL2 gl, GLU glu, GLUquadric quad) {
    setMaterialWheelOuter(gl);
    gl.glTranslated(0.0D, 0.0D, -0.05D);
    glu.gluCylinder(quad, 0.125D, 0.125D, 0.1D, 20, 1);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    setMaterialWheelOuter(gl);
    glu.gluDisk(quad, 0.08D, 0.125D, 20, 1);
    setMaterialWheelInner(gl);
    glu.gluDisk(quad, 0.0D, 0.08D, 20, 1);
    gl.glRotated(-180.0D, 1.0D, 0.0D, 0.0D);
    gl.glTranslated(0.0D, 0.0D, 0.1D);
    setMaterialWheelOuter(gl);
    glu.gluDisk(quad, 0.08D, 0.125D, 20, 1);
    setMaterialWheelInner(gl);
    glu.gluDisk(quad, 0.0D, 0.08D, 20, 1);
    gl.glTranslated(0.0D, 0.0D, -0.05D);
  }
  
  private void drawLights(GL2 gl, GLU glu, GLUquadric quad) {
    drawFrontLights(gl, glu, quad);
  }
  
  private void drawFrontLights(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glPushMatrix();
    gl.glTranslated(-0.825D, -0.1D, 0.0D);
    gl.glTranslated(0.0D, 0.0D, -0.1D);
    drawFrontLight(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, 0.2D);
    drawFrontLight(gl, glu, quad);
    gl.glPopMatrix();
  }
  
  private void drawFrontLight(GL2 gl, GLU glu, GLUquadric quad) {
    double r = 0.05D;
    gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
    setMaterialLightCase(gl);
    glu.gluCylinder(quad, 0.05D, 0.05D, 0.05D, 20, 1);
    setMaterialFrontLight(gl);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    glu.gluDisk(quad, 0.0D, 0.05D, 20, 1);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    gl.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
  }
  

  public String toString()
  {
    try
    {
      return new String(Base64.getDecoder().decode("QnVz"), "UTF-8");
    } catch (UnsupportedEncodingException e) {}
    return "";
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
