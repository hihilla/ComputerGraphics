package edu.cg.models;

import com.jogamp.opengl.GL2;

public class Locomotive implements IRenderable {
    private boolean isLightSpheres;

    public void render(GL2 gl) {
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
    private void drawChassis() {
        drawDoor();
        drawWindows();

    }

    private void drawDoor() {

    }

    private void drawWindows() {

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

    private void draeLocomotive() {
        drawChassis();
        drawWeels();
        drawLights();
        drawRoof();
        drawChimney();
    }
}
