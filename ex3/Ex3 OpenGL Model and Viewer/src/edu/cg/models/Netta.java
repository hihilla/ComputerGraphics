package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Thank you Europe, Capara Aleichem!
 */
public class Netta implements IRenderable {
    private boolean isLightSpheres;

    public void render(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

        drawGulguls(gl, glu, q);
        drawHeadHair(gl, glu, q);
        drawHeadFace(gl, glu, q);
        drawEyes(gl, glu, q);
        drawMic(gl, glu, q);
    }

    private void drawMic(GL2 gl, GLU glu, GLUquadric q) {
        gl.glColor3f(.15f,.15f,.15f); //dark color
        gl.glPushMatrix();

        gl.glTranslated(0, -.3, .6);
        glu.gluSphere(q, .08, 30, 30);

        gl.glPopMatrix();


        gl.glPushMatrix();
        gl.glTranslated(0, -.3, .6);
        gl.glRotated(45, 1, 0, 0);
        glu.gluCylinder(q, .06, .035, .4, 30, 1);
        gl.glTranslated(0, 0, .4);
        glu.gluDisk(q, 0, .035, 10, 1);

        gl.glPopMatrix();
    }

    private void drawGulguls(GL2 gl, GLU glu, GLUquadric q) {
        gl.glColor3f(.25f,.155f,.15f); //brownish color
        gl.glPushMatrix();

        gl.glTranslated(.4, .4, .01);
        glu.gluSphere(q, .24, 30, 30);

        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glTranslated(-.4, .4, .01);
        glu.gluSphere(q, .24, 30, 30);

        gl.glPopMatrix();
    }

    private void drawHeadHair(GL2 gl, GLU glu, GLUquadric q) {
        gl.glColor3f(.25f,.155f,.15f); //brownish color
        gl.glPushMatrix();

        gl.glScaled(.7, .9, .9);
        glu.gluSphere(q, .45, 30, 30);

        gl.glPopMatrix();
    }

    private void drawHeadFace(GL2 gl, GLU glu, GLUquadric q) {
        gl.glColor3f( 1f,.85f,.74f ); // skin color
        // face
        gl.glPushMatrix();

        gl.glTranslated(0, -.07, .067);
        gl.glScaled(.7, .97, .9);
        glu.gluSphere(q, .4226, 30, 30);

        gl.glPopMatrix();

        // ears
        gl.glPushMatrix();

        gl.glTranslated(.29, 0, .17);
        gl.glScaled(.37, .9, .87);
        glu.gluSphere(q, .07, 30, 30);

        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glTranslated(-.29, 0, .17);
        gl.glScaled(.37, .9, .87);
        glu.gluSphere(q, .07, 30, 30);

        gl.glPopMatrix();
    }

    private void drawEyes(GL2 gl, GLU glu, GLUquadric q) {
        // eyeballs
        gl.glPushMatrix();
        gl.glColor3f( 0f,.08f,.09f ); // dark color
        gl.glTranslated(.09, -.056, .43);
        glu.gluSphere(q, .025, 30, 30);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-.09, -.056, .43);
        glu.gluSphere(q, .025, 30, 30);
        gl.glPopMatrix();

        // eyes

        gl.glColor3f( 1f,.98f,.99f ); // bright color
        gl.glPushMatrix();

        gl.glTranslated(.09, -.03, .4);
        gl.glRotated(45, 1, 0, 0);
        gl.glScaled(1, .44, 1);
        glu.gluSphere(q, .068, 30, 30);

        // eyeshadow
        gl.glColor3f( .9f,.2f,.7f ); // pink eyeshadow color
        gl.glTranslated(0, .012, -.02);
        glu.gluSphere(q, .069, 30, 30);

        gl.glPopMatrix();

        gl.glColor3f( 1f,.98f,.99f ); // bright color
        gl.glPushMatrix();

        gl.glTranslated(-.09, -.03, .4);
        gl.glRotated(45, 1, 0, 0);
        gl.glScaled(1, .44, 1);
        glu.gluSphere(q, .068, 30, 30);

        // eyeshadow
        gl.glColor3f( .9f,.2f,.7f ); // pink eyeshadow color
        gl.glTranslated(0, .012, -.02);
        glu.gluSphere(q, .069, 30, 30);

        gl.glPopMatrix();
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
    public void init(GL2 gl) {

    }

    @Override
    public void setCamera(GL2 gl) {

    }
}
