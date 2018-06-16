// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL;
import java.lang.reflect.Method;
import java.io.IOException;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import com.jogamp.opengl.GL2;
import edu.cg.TrackPoints;
import com.jogamp.opengl.util.texture.Texture;
import edu.cg.algebra.Point;
import edu.cg.CyclicList;

public class Track  implements IRenderable
{
    private IRenderable vehicle;
    private CyclicList<Point> trackPoints;
    private Texture texGrass;
    private Texture texTrack;
    
    public Track(final IRenderable vehicle, final CyclicList<Point> trackPoints) {
        this.texGrass = null;
        this.texTrack = null;
        this.vehicle = vehicle;
        this.trackPoints = trackPoints;
    }
    
    public Track(final IRenderable vehicle) {
        this(vehicle, TrackPoints.track1());
    }
    
    public Track() {
        this.texGrass = null;
        this.texTrack = null;
    }
    
    @Override
    public void init(final GL2 gl) {
        this.loadTextures(gl);
        this.vehicle.init(gl);
    }
    
    private void loadTextures(final GL2 gl) {
        final File fileGrass = new File("grass.jpg");
        final File fileRoad = new File("track.png");
        try {
            this.texTrack = TextureIO.newTexture(fileRoad, true);
            this.texGrass = TextureIO.newTexture(fileGrass, false);
        }
        catch (GLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void render(final GL2 gl) {
        this.renderVehicle(gl);
        this.renderField(gl);
        this.renderTrack(gl);
    }
    
    private void renderVehicle(final GL2 gl) {
        gl.glPushMatrix();
        gl.glScaled(0.15, 0.15, 0.15);
        gl.glTranslated(0.0, 0.35, 0.0);
        this.vehicle.render(gl);
        gl.glPopMatrix();
    }
    
    private void renderField(final GL2 gl) {
        gl.glEnable(3553);
        gl.glBindTexture(3553, this.texGrass.getTextureObject());
        final boolean lightningEnabled;
        if (lightningEnabled = gl.glIsEnabled(2896)) {
            gl.glDisable(2896);
        }
        gl.glTexEnvi(8960, 8704, 7681);
        gl.glTexParameteri(3553, 10243, 10497);
        gl.glTexParameteri(3553, 10242, 10497);
        gl.glTexParameteri(3553, 10241, 9729);
        gl.glTexParameteri(3553, 33083, 1);
        gl.glBegin(7);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-1.2, -1.2, -0.02);
        gl.glTexCoord2d(4.0, 0.0);
        gl.glVertex3d(1.2, -1.2, -0.02);
        gl.glTexCoord2d(4.0, 4.0);
        gl.glVertex3d(1.2, 1.2, -0.02);
        gl.glTexCoord2d(0.0, 4.0);
        gl.glVertex3d(-1.2, 1.2, -0.02);
        gl.glEnd();
        if (lightningEnabled) {
            gl.glEnable(2896);
        }
        gl.glDisable(3553);
    }
    
    private void renderTrack(final GL2 gl) {
        gl.glEnable(3553);
        gl.glEnable(3042);
        gl.glBlendFunc(770, 771);
        gl.glBindTexture(3553, this.texTrack.getTextureObject());
        final boolean lightningEnabled;
        if (lightningEnabled = gl.glIsEnabled(2896)) {
            gl.glDisable(2896);
        }
        gl.glTexEnvi(8960, 8704, 7681);
        gl.glTexParameteri(3553, 10243, 10497);
        gl.glTexParameteri(3553, 10242, 10497);
        gl.glTexParameteri(3553, 10241, 9987);
        gl.glTexParameteri(3553, 33083, 2);
        if (lightningEnabled) {
            gl.glEnable(2896);
        }
        gl.glDisable(3042);
        gl.glDisable(3553);
    }
    
    @Override
    public void control(final int type, final Object params) {
        switch (type) {
            case 38: {
                break;
            }
            case 40: {
                break;
            }
            case 10: {
                try {
                    final Method m = TrackPoints.class.getMethod("track" + params, (Class<?>[])new Class[0]);
                    this.trackPoints = (CyclicList<Point>)m.invoke(null, new Object[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 0: {
                this.vehicle.control(type, params);
                break;
            }
            default: {
                System.out.println("Unsupported operation for Track control");
                break;
            }
        }
    }
    
    @Override
    public boolean isAnimated() {
        return true;
    }
    
    @Override
    public void setCamera(final GL2 gl) {
    }
    
    @Override
    public void destroy(final GL2 gl) {
        this.texGrass.destroy(gl);
        this.texTrack.destroy(gl);
        this.vehicle.destroy(gl);
    }
}
