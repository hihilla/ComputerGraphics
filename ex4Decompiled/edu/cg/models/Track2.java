// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.glu.GLU;
import java.lang.reflect.Method;
import java.util.Iterator;
import edu.cg.algebra.Vec;
import java.io.IOException;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import com.jogamp.opengl.GL2;
import edu.cg.TrackPoints;
import edu.cg.TrackSegment;
import com.jogamp.opengl.util.texture.Texture;
import edu.cg.algebra.Point;
import edu.cg.CyclicList;

public class Track2 implements IRenderable
{
    private IRenderable vehicle;
    private CyclicList<Point> trackPoints;
    private Texture texGrass;
    private Texture texTrack;
    private CyclicList<TrackSegment> segments;
    private static final double pieceLength = 0.05;
    private static final float trackWidth = 0.05f;
    private double t;
    private int segNum;
    double velocity;
    
    public Track2(final IRenderable vehicle, final CyclicList<Point> trackPoints) {
        this.texGrass = null;
        this.texTrack = null;
        this.segments = null;
        this.t = 0.0;
        this.segNum = 0;
        this.velocity = 0.005;
        this.vehicle = vehicle;
        this.trackPoints = trackPoints;
    }
    
    public Track2(final IRenderable vehicle) {
        this(vehicle, TrackPoints.track1());
    }
    
    public Track2() {
        this(new Locomotive1());
    }
    
    @Override
    public void init(final GL2 gl) {
        this.segments = TrackSegment.calcTrackSegmants(this.trackPoints);
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
        final TrackSegment segment = this.segments.get(this.segNum);
        final TrackSegment.LocationOnSegment location = segment.getLocationOnSegment(this.t);
        final Point pos = location.pos;
        final Vec tangent = location.tangent.neg();
        final Vec normal = location.normal;
        final Vec right = location.right().neg();
        gl.glTranslatef(pos.x, pos.y, pos.z);
        final double[] mat = { tangent.x, tangent.y, tangent.z, 0.0, normal.x, normal.y, normal.z, 0.0, right.x, right.y, right.z, 0.0, 0.0, 0.0, 0.0, 1.0 };
        gl.glMultMatrixd(mat, 0);
        gl.glScaled(0.15, 0.15, 0.15);
        gl.glTranslated(0.0, 0.35, 0.0);
        this.vehicle.render(gl);
        gl.glPopMatrix();
        final double length = segment.length();
        final double dt = this.velocity / length;
        this.t += dt;
        if (this.t > 1.0) {
            --this.t;
            this.segNum = (this.segNum + 1) % this.segments.size();
        }
        else if (this.t < 0.0) {
            ++this.t;
            this.segNum = (this.segNum - 1) % this.segments.size();
        }
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
        gl.glBegin(4);
        for (final TrackSegment segment : this.segments) {
            final double length = segment.length();
            final int numOfSubSegments = (int)(length / 0.05) + 1;
            final double dt = 1.0 / numOfSubSegments;
            double t = 0.0;
            for (int i = 0; i < numOfSubSegments; ++i, t += dt) {
                final TrackSegment.LocationOnSegment l0 = segment.getLocationOnSegment(t);
                final TrackSegment.LocationOnSegment l2 = segment.getLocationOnSegment(t + dt);
                this.renderTrackPiece(gl, l0, l2);
            }
        }
        gl.glEnd();
        if (lightningEnabled) {
            gl.glEnable(2896);
        }
        gl.glDisable(3042);
        gl.glDisable(3553);
    }
    
    private void renderTrackPiece(final GL2 gl, final TrackSegment.LocationOnSegment l0, final TrackSegment.LocationOnSegment l1) {
        final Vec r0 = l0.right();
        final Vec r2 = l1.right();
        final Point p0 = l0.pos.add(r0.mult(0.05f));
        final Point p2 = l1.pos.add(r2.mult(0.05f));
        final Point p3 = l1.pos.add(r2.mult(-0.05f));
        final Point p4 = l0.pos.add(r0.mult(-0.05f));
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3fv(p0.toGLVertex());
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3fv(p2.toGLVertex());
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3fv(p3.toGLVertex());
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3fv(p0.toGLVertex());
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3fv(p3.toGLVertex());
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3fv(p2.toGLVertex());
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3fv(p0.toGLVertex());
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3fv(p3.toGLVertex());
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3fv(p4.toGLVertex());
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3fv(p0.toGLVertex());
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3fv(p4.toGLVertex());
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3fv(p3.toGLVertex());
    }
    
    @Override
    public void control(final int type, final Object params) {
        switch (type) {
            case 38: {
                this.velocity += 0.005;
                break;
            }
            case 40: {
                this.velocity -= 0.005;
                break;
            }
            case 10: {
                try {
                    final Method m = TrackPoints.class.getMethod("track" + params, (Class<?>[])new Class[0]);
                    this.trackPoints = (CyclicList<Point>)m.invoke(null, new Object[0]);
                    this.segments = TrackSegment.calcTrackSegmants(this.trackPoints);
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
        final TrackSegment segment = this.segments.get(this.segNum);
        final TrackSegment.LocationOnSegment location = segment.getLocationOnSegment(this.t);
        Point pos = location.pos;
        final Vec tangent = location.tangent;
        final Vec normal = location.normal;
        final Vec right = location.right();
        final Point trans = pos.add(tangent);
        pos = pos.add(tangent.mult(-0.25f)).add(normal.mult(0.25f)).add(right.mult(-0.2f));
        new GLU().gluLookAt(pos.x, pos.y, pos.z, trans.x, trans.y, trans.z, normal.x, normal.y, normal.z);
    }
    
    @Override
    public void destroy(final GL2 gl) {
        this.texGrass.destroy(gl);
        this.texTrack.destroy(gl);
        this.vehicle.destroy(gl);
    }
}
