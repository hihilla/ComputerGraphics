package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;

public class Locomotive implements IRenderable {
    private boolean isLightSpheres;

    public void render(GL2 gl) {
        drawLocomotive(gl);
    }


    @Override
    public String toString() {
        return "Empty";
    }


    //If your scene requires more control (like keyboard events), you can define it here.
    @Override
    public void control(int type, Object params) {
        switch (type) {
            case IRenderable.TOGGLE_LIGHT_SPHERES:
            {
                isLightSpheres = ! isLightSpheres;
                break;
            }
            default:
                System.out.println("Control type not supported: " + toString() + ", " + type);
        }
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public void init(GL2 gl) { }

    @Override
    public void setCamera(GL2 gl) { }

    /**
     * Chassis – The body of the locomotive. Filled polygons.
     ▪ Door – Filled polygon.
     ▪ Windows – Filled polygons
     */
    private void drawChassis(GL2 gl) {
        drawDoor(gl);
        drawWindowCloserToDoor(gl);
        drawWindowFurtherFormDoor(gl);

    }

    private void drawDoor(GL2 gl) {
        gl.glNormal3d(0, 0, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0, 0, 0); // black door
        gl.glVertex3f(0.2f, 0.15f, 0); // top right
        gl.glVertex3f(0.05f, 0.15f, 0); // top left
        gl.glVertex3f(0.05f, -0.2f, 0); // bottom left
        gl.glVertex3f(0.2f, -0.2f, 0); // bottom right

        gl.glEnd();
    }

    private void drawWindowCloserToDoor(GL2 gl) {
        gl.glNormal3d(0, 0, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0, 0, 0);
        gl.glVertex3f(-0.05f, 0.15f, 0); // top right
        gl.glVertex3f(-0.2f, 0.15f, 0); // top left
        gl.glVertex3f(-0.2f, -0.05f, 0); // bottom left
        gl.glVertex3f(-0.05f , -0.05f, 0); // bottom right
        gl.glEnd();
    }

    private void drawWindowFurtherFormDoor(GL2 gl){
        gl.glNormal3d(0, 0, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0, 0, 0);
        gl.glVertex3f(-0.3f, 0.15f, 0);
        gl.glVertex3f(-0.45f, 0.15f, 0);
        gl.glVertex3f(-0.45f, -0.05f, 0);
        gl.glEnd();
    }

    private void drawSideWithDoor(GL2 gl){
        // chassis somehow...

        gl.glNormal3d(0, 0, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glPushMatrix();
        // bigger rectangle
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0.3f, 0.2f, 0.2f);
        gl.glVertex3f(-0.5f, 0.2f, 0.2f);
        gl.glVertex3f(-0.5f, -0.2f, 0.2f);
        gl.glVertex3f(0.3f, -0.2f, 0.2f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        // smaller rectangle
        gl.glColor3f(1, 0 ,0);
        gl.glVertex3f(0.8f, 0, 0.2f);
        gl.glVertex3f(0.3f, 0, 0.2f);
        gl.glVertex3f(0.3f, -0.2f, 0.2f);
        gl.glVertex3f(0.8f, -0.2f, 0.2f);
        gl.glPopMatrix();
        gl.glEnd();

        // door and windows
        gl.glPushMatrix();
        gl.glTranslated(0, 0, 0.202f);
        drawDoor(gl);
        gl.glPushMatrix();
        drawWindowCloserToDoor(gl);
//        gl.glTranslated(0, 0, 0.202f);
        gl.glPushMatrix();
        drawWindowFurtherFormDoor(gl);




    }

    /**
     * Wheels
     ▪ Wheel – Cylinder bounded by disks.
     */
    private void drawWeels() {

    }

    /**
     * Lights
     ▪ Front light – Cylinder bounded by disk.
     */
    private void drawLights() {

    }

    /**
     * Roof – Non-uniformly scaled cylinder bounded by disks.
     */
    private void drawRoof() {

    }

    /**
     * Chimney – Two cylinders place on top of each other bounded by disks.
     */
    private void drawChimney() {

    }

    private void drawLocomotive(GL2 gl) {
        //drawChassis();
        //drawWeels();
        //drawLights();
        //drawRoof();
        //drawChimney();
        //drawWindows(gl);
        //drawDoor(gl);
        drawSideWithDoor(gl);
    }
}
