// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import com.jogamp.opengl.GL2;

public class Cube implements IRenderable
{
    private boolean isLightSpheres;
    
    @Override
    public void render(final GL2 gl) {
        this.drawRGBCube(gl);
    }
    
    private void drawRGBCube(final GL2 gl) {
        final double r = 0.5;
        final boolean lightingFlag = gl.glIsEnabled(2896);
        gl.glDisable(2896);
        gl.glBegin(7);
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(-r, -r, r);
        gl.glColor3d(1.0, 0.0, 1.0);
        gl.glVertex3d(r, -r, r);
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glVertex3d(r, r, r);
        gl.glColor3d(0.0, 1.0, 1.0);
        gl.glVertex3d(-r, r, r);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-r, -r, -r);
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(-r, -r, r);
        gl.glColor3d(0.0, 1.0, 1.0);
        gl.glVertex3d(-r, r, r);
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glVertex3d(-r, r, -r);
        gl.glColor3d(1.0, 0.0, 1.0);
        gl.glVertex3d(r, -r, r);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(r, -r, -r);
        gl.glColor3d(1.0, 1.0, 0.0);
        gl.glVertex3d(r, r, -r);
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glVertex3d(r, r, r);
        gl.glColor3d(1.0, 1.0, 0.0);
        gl.glVertex3d(r, r, -r);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(r, -r, -r);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-r, -r, -r);
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glVertex3d(-r, r, -r);
        gl.glColor3d(0.0, 1.0, 1.0);
        gl.glVertex3d(-r, r, r);
        gl.glColor3d(1.0, 1.0, 1.0);
        gl.glVertex3d(r, r, r);
        gl.glColor3d(1.0, 1.0, 0.0);
        gl.glVertex3d(r, r, -r);
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glVertex3d(-r, r, -r);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-r, -r, -r);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(r, -r, -r);
        gl.glColor3d(1.0, 0.0, 1.0);
        gl.glVertex3d(r, -r, r);
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(-r, -r, r);
        gl.glEnd();
        if (lightingFlag) {
            gl.glEnable(2896);
        }
    }
    
    @Override
    public String toString() {
        try {
            return new String(Base64.getDecoder().decode("Q3ViZQ=="), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    @Override
    public void control(final int type, final Object params) {
        switch (type) {
            case 0: {
                this.isLightSpheres = !this.isLightSpheres;
                break;
            }
            default: {
                System.out.println("Control type not supported: " + this.toString() + ", " + type);
                break;
            }
        }
    }
    
    @Override
    public boolean isAnimated() {
        return false;
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void setCamera(final GL2 gl) {
    }
}
