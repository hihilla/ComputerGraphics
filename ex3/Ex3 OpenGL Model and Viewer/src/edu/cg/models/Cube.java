package edu.cg.models;

import com.jogamp.opengl.GL2;

public class Cube implements IRenderable {
    private boolean isLightSpheres;

    public void render(GL2 gl) {
        drawCube(gl);

        gl.glFlush();
    }

    private void drawCube(GL2 gl) {
        float d = 0.5f;
        //giving different colors to different sides
        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
        gl.glColor3f(1f,0f,0f); //red color
        gl.glVertex3f(d, d, -d); // Top Right Of The Quad (Top)
        gl.glVertex3f( -d, d, -d); // Top Left Of The Quad (Top)
        gl.glVertex3f( -d, d, d ); // Bottom Left Of The Quad (Top)
        gl.glVertex3f( d, d, d ); // Bottom Right Of The Quad (Top)

        gl.glColor3f( 0f,1f,0f ); //green color
        gl.glVertex3f( d, -d, d ); // Top Right Of The Quad
        gl.glVertex3f( -d, -d, d ); // Top Left Of The Quad
        gl.glVertex3f( -d, -d, -d ); // Bottom Left Of The Quad
        gl.glVertex3f( d, -d, -d ); // Bottom Right Of The Quad

        gl.glColor3f( 0f,0f,1f ); //blue color
        gl.glVertex3f( d, d, d ); // Top Right Of The Quad (Front)
        gl.glVertex3f( -d, d, d ); // Top Left Of The Quad (Front)
        gl.glVertex3f( -d, -d, d ); // Bottom Left Of The Quad
        gl.glVertex3f( d, -d, d ); // Bottom Right Of The Quad

        gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
        gl.glVertex3f( d, -d, -d ); // Bottom Left Of The Quad
        gl.glVertex3f( -d, -d, -d ); // Bottom Right Of The Quad
        gl.glVertex3f( -d, d, -d ); // Top Right Of The Quad (Back)
        gl.glVertex3f( d, d, -d ); // Top Left Of The Quad (Back)

        gl.glColor3f( 1f,0f,1f ); //purple (red + green)
        gl.glVertex3f( -d, d, d ); // Top Right Of The Quad (Left)
        gl.glVertex3f( -d, d, -d ); // Top Left Of The Quad (Left)
        gl.glVertex3f( -d, -d, -d ); // Bottom Left Of The Quad
        gl.glVertex3f( -d, -d, d ); // Bottom Right Of The Quad

        gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
        gl.glVertex3f( d, d, -d ); // Top Right Of The Quad (Right)
        gl.glVertex3f( d, d, d ); // Top Left Of The Quad
        gl.glVertex3f( d, -d, d ); // Bottom Left Of The Quad
        gl.glVertex3f( d, -d, -d ); // Bottom Right Of The Quad
        gl.glEnd(); // Done Drawing The Quad

    }


    @Override
    public String toString() {
        return "Cube";
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
    public void init(GL2 gl) {}

    @Override
    public void setCamera(GL2 gl) {}
}
