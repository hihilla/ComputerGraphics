package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import java.io.PrintStream;

public class Car implements IRenderable
{
  public Car() {}
  
  private boolean isLightSpheres = true;
  private static final double EPS = 0.001D;
  private static final float[] black = { 0.0F, 0.0F, 0.0F };
  
  public void render(GL2 gl) {
    gl.glTranslated(0.2D, 0.0D, 0.0D);
    gl.glLineWidth(4.0F);
    
    gl.glMaterialf(1028, 5633, 10.0F);
    
    float[] light0Pos = { 0.4F, 0.4F, -0.6F, 1.0F };
    float[] light0Color = { 1.0F, 1.0F, 0.5F, 1.0F };
    gl.glLightfv(16384, 4609, light0Color, 0);
    gl.glLightfv(16384, 4610, light0Color, 0);
    gl.glLightfv(16384, 4611, light0Pos, 0);
    gl.glEnable(16384);
    float[] light1Pos = { -1.0F, 0.3F, 0.6F, 1.0F };
    float[] light1Color = { 0.5F, 1.0F, 1.0F, 1.0F };
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
  
  private void setMaterialChassis(GL2 gl)
  {
    float[] specular = { 1.0F, 1.0F, 1.0F };
    gl.glColor3fv(specular, 0);
    gl.glMaterialfv(1028, 4609, black, 0);
    gl.glMaterialfv(1028, 4610, specular, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialDoor(GL2 gl) {
    float[] col = { 0.01F, 0.05F, 0.01F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
    gl.glMaterialfv(1028, 5632, black, 0);
  }
  
  private void setMaterialWindow(GL2 gl) {
    float[] col = { 0.4296875F, 0.6640625F, 0.95703125F };
    gl.glColor3fv(col, 0);
    gl.glMaterialfv(1028, 4609, col, 0);
    gl.glMaterialfv(1028, 4610, black, 0);
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
    
    drawQuadZ(gl, 0.6D, -0.9D, -0.2D, -0.2D, 0.3D);
    drawQuadZ(gl, -0.8D, -0.4D, 0.0D, 0.0D, 0.3D);
    drawQuadZ(gl, 0.4D, 0.5D, 0.0D, 0.0D, 0.3D);
    drawQuadZ(gl, -0.2D, 0.3D, 0.2D, 0.2D, 0.3D);
    drawQuadZ(gl, -0.4D, -0.2D, 0.0D, 0.2D, 0.3D);
    drawQuadZ(gl, 0.3D, 0.4D, 0.2D, 0.0D, 0.3D);
    drawQuadZ(gl, -0.9D, -0.8D, -0.2D, 0.0D, 0.3D);
    drawQuadZ(gl, 0.5D, 0.6D, 0.0D, -0.2D, 0.3D);
    
    drawLeftSide(gl);
    drawRightSide(gl);
    drawFrontWindow(gl);
    drawBackWindow(gl);
  }
  
  private void drawLeftSide(GL2 gl)
  {
    setMaterialChassis(gl);
    gl.glNormal3d(0.0D, 0.0D, 1.0D);
    gl.glBegin(7);
    gl.glVertex3d(-0.9D, -0.2D, 0.3D);
    gl.glVertex3d(0.6D, -0.2D, 0.3D);
    gl.glVertex3d(0.5D, 0.0D, 0.3D);
    gl.glVertex3d(-0.8D, 0.0D, 0.3D);
    
    gl.glVertex3d(0.4D, 0.0D, 0.3D);
    gl.glVertex3d(0.3D, 0.2D, 0.3D);
    gl.glVertex3d(-0.2D, 0.2D, 0.3D);
    gl.glVertex3d(-0.4D, 0.0D, 0.3D);
    gl.glEnd();
    
    drawFrontLeftDoor(gl);
    drawBackLeftDoor(gl);
  }
  
  private void drawFrontLeftDoor(GL2 gl)
  {
    setMaterialDoor(gl);
    int[] polygonMode = new int[1];
    gl.glGetIntegerv(2880, polygonMode, 0);
    gl.glPolygonMode(1032, 6913);
    gl.glBegin(9);
    gl.glVertex3d(-0.3D, -0.15D, 0.301D);
    gl.glVertex3d(-0.02D, -0.15D, 0.301D);
    gl.glVertex3d(-0.02D, 0.15D, 0.301D);
    gl.glVertex3d(-0.15D, 0.15D, 0.301D);
    gl.glVertex3d(-0.3D, 0.0D, 0.301D);
    gl.glEnd();
    gl.glPolygonMode(1032, polygonMode[0]);
    drawFrontLeftWindow(gl);
  }
  
  private void drawBackLeftDoor(GL2 gl) {
    setMaterialDoor(gl);
    int[] polygonMode = new int[1];
    gl.glGetIntegerv(2880, polygonMode, 0);
    gl.glPolygonMode(1032, 6913);
    gl.glLineWidth(4.0F);
    gl.glBegin(9);
    gl.glVertex3d(0.02D, -0.15D, 0.301D);
    gl.glVertex3d(0.15D, -0.15D, 0.301D);
    gl.glVertex3d(0.33D, 0.0D, 0.301D);
    gl.glVertex3d(0.25D, 0.15D, 0.301D);
    gl.glVertex3d(0.02D, 0.15D, 0.301D);
    gl.glEnd();
    gl.glPolygonMode(1032, polygonMode[0]);
    drawBackLeftWindow(gl);
  }
  
  private void drawFrontLeftWindow(GL2 gl) {
    setMaterialWindow(gl);
    gl.glBegin(7);
    gl.glVertex3d(-0.25D, 0.0D, 0.302D);
    gl.glVertex3d(-0.05D, 0.0D, 0.302D);
    gl.glVertex3d(-0.05D, 0.1D, 0.302D);
    gl.glVertex3d(-0.15D, 0.1D, 0.302D);
    gl.glEnd();
  }
  
  private void drawBackLeftWindow(GL2 gl) {
    setMaterialWindow(gl);
    gl.glBegin(7);
    gl.glVertex3d(0.05D, 0.0D, 0.302D);
    gl.glVertex3d(0.28D, 0.0D, 0.302D);
    gl.glVertex3d(0.23D, 0.1D, 0.302D);
    gl.glVertex3d(0.05D, 0.1D, 0.302D);
    gl.glEnd();
  }
  
  private void drawFrontWindow(GL2 gl) {
    setMaterialWindow(gl);
    drawQuadZ(gl, -0.401D, -0.251D, 0.0D, 0.15D, 0.25D);
  }
  
  private void drawBackWindow(GL2 gl) {
    setMaterialWindow(gl);
    drawQuadZ(gl, 0.326D, 0.401D, 0.15D, 0.0D, 0.25D);
  }
  
  private void drawRightSide(GL2 gl) { gl.glFrontFace(2304);
    gl.glScaled(1.0D, 1.0D, -1.0D);
    drawLeftSide(gl);
    gl.glScaled(1.0D, 1.0D, -1.0D);
    gl.glFrontFace(2305);
  }
  
  private void drawWheels(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glPushMatrix();
    gl.glTranslated(-0.575D, -0.2D, 0.0D);
    drawWheelPair(gl, glu, quad);
    gl.glTranslated(0.8999999999999999D, 0.0D, 0.0D);
    drawWheelPair(gl, glu, quad);
    gl.glPopMatrix();
  }
  
  private void drawWheelPair(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glPushMatrix();
    gl.glTranslated(0.0D, 0.0D, -0.3D);
    drawWheel(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, 0.6D);
    drawWheel(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, -0.3D);
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
  
  private void drawFrontLights(GL2 gl, GLU glu, GLUquadric quad)
  {
    gl.glPushMatrix();
    gl.glTranslated(-0.9D, -0.1D, 0.0D);
    gl.glTranslated(0.0D, 0.0D, -0.17D);
    drawFrontLight(gl, glu, quad);
    gl.glTranslated(0.0D, 0.0D, 0.34D);
    drawFrontLight(gl, glu, quad);
    gl.glPopMatrix();
  }
  
  private void drawFrontLight(GL2 gl, GLU glu, GLUquadric quad) {
    gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
    setMaterialLightCase(gl);
    glu.gluCylinder(quad, 0.07D, 0.07D, 0.1D, 20, 1);
    setMaterialFrontLight(gl);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    glu.gluDisk(quad, 0.0D, 0.07D, 20, 1);
    gl.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
    gl.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
  }
  


  public String toString()
  {
    return "Car";
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
