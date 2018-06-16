package edu.cg.models;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import edu.cg.CyclicList;
import edu.cg.LocationOnMesila;
import edu.cg.Mesila;
import edu.cg.TrackPoints;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class Track implements IRenderable {
	private IRenderable vehicle;
	private CyclicList<Point> trackPoints;
	private Texture texGrass = null;
	private Texture texTrack = null;

	private CyclicList<Mesila> mesilot;
	private int mesila = 0;
	private double t = 0;
	private double velocity = 0.01;
	
	public Track(IRenderable vehicle, CyclicList<Point> trackPoints) {
		this.vehicle = vehicle;
		this.trackPoints = trackPoints;
		this.mesilot = null;
	}
	
	public Track(IRenderable vehicle) {
		this(vehicle, TrackPoints.track1());
	}
	
	public Track() {
		//TODO: uncomment this and change it if for your needs.
//		this(new Locomotive());
	}

	@Override
	public void init(GL2 gl) {
		//TODO: Build your track splines here.
		//Compute the length of each spline.
		//Do not repeat those calculations over and over in the render method.
		//It will make the application to run not smooth.  
		loadTextures(gl);
		vehicle.init(gl);
	}
	
	private void loadTextures(GL2 gl) {
		File fileGrass = new File("grass.jpg");
		File fileRoad = new File("track.png");
		try {
			texTrack = TextureIO.newTexture(fileRoad, true);
			texGrass = TextureIO.newTexture(fileGrass, false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GL2 gl) {
		renderVehicle(gl);
		renderField(gl);
		renderTrack(gl);
	}

	private void renderVehicle(GL2 gl) {
		gl.glPushMatrix();

		//TODO: implement vehicle translations and rotations here...
		Mesila mesila = mesilot.get(this.mesila);
		LocationOnMesila location = mesila.locationOnMesila(this.t);
		Point p = location.position;
		Vec tangent = location.tangent.neg();
		Vec normalToTangent = location.normal;
		Vec tangCrossNormal = location.tangentCrossNromal().neg();
		gl.glTranslated(p.x, p.y, p.z);

		double[] coeffMatrix = {tangent.x, tangent.y, tangent.z, 0,
								normalToTangent.x, normalToTangent.y, normalToTangent.z, 0,
								tangCrossNormal.x, tangCrossNormal.y, tangCrossNormal.z, 0,
								0, 0, 0, 1};

		gl.glMultMatrixd(coeffMatrix, 0);
		gl.glScaled(0.15, 0.15, 0.15);
		gl.glTranslated(0, 0.35, 0);

		this.vehicle.render(gl);
		gl.glPopMatrix();

		double length = mesila.getLength();
		double dt = this.velocity / length;
		this.t += dt;
		if (this.t > 1) {
			this.t = this.t-1;
			mesilot.get(this.mesila) = this.get(this.mesila + 1) % this.mesilot.size();
		}
		else if (this.t < 0) {
			this.t++;
			mesilot.get(this.mesila) = this.get(this.mesila - 1) % this.mesilot.size();
	}

	private void renderField(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texGrass.getTextureObject());
		
		boolean lightningEnabled;
		if((lightningEnabled = gl.glIsEnabled(GL2.GL_LIGHTING)))
			gl.glDisable(GL2.GL_LIGHTING);

		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, 1);
		
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-1.2, -1.2, -.02);
		gl.glTexCoord2d(4, 0);
		gl.glVertex3d(1.2, -1.2, -.02);
		gl.glTexCoord2d(4, 4);
		gl.glVertex3d(1.2, 1.2, -.02);
		gl.glTexCoord2d(0, 4);
		gl.glVertex3d(-1.2, 1.2, -.02);
		
		gl.glEnd();
		
		if(lightningEnabled)
			gl.glEnable(GL2.GL_LIGHTING);
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}

	private void renderTrack(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texTrack.getTextureObject());
		
		boolean lightningEnabled;
		if((lightningEnabled = gl.glIsEnabled(GL2.GL_LIGHTING)))
			gl.glDisable(GL2.GL_LIGHTING);
		
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAX_LOD, 2);
		
		//: implement track rendering here...
		gl.glBegin(GL2.GL_TRIANGLES);

		for (Mesila mesila: mesilot) {
			int numOfMesilot = (int) Math.ceil(mesila.getLength() / 0.05);
			double t = 0;
			double dt = 1.0 / numOfMesilot;

			for (int i = 0; i < numOfMesilot; i++) {
				LocationOnMesila l0 = mesila.locationOnMesila(t);
				t += dt;
				LocationOnMesila l1 = mesila.locationOnMesila(t);
				drawTrackTriangle(gl, l0, l1);
			}
		}

		gl.glEnd();
		
		if(lightningEnabled)
			gl.glEnable(GL2.GL_LIGHTING);
		
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}

	private void drawTrackTriangle(GL2 gl, LocationOnMesila l0, LocationOnMesila l1) {
		Vec tcn0 = l0.tangentCrossNromal();
		Vec tcn1 = l1.tangentCrossNromal();
		Point p1 = l0.position.add(tcn0.mult(0.05));
		Point p2 = l0.position.add(tcn1.mult(0.05));
		Point p3 = l0.position.add(tcn1.mult(-0.05));
		Point p4 = l0.position.add(tcn0.mult(-0.05));

		// 1, 2, 3 - clockwise
		FloatBuffer fb;
		gl.glTexCoord2d(0, 0);
		fb = p1.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(0, 1);
		fb = p2.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 1);
		fb = p3.floatBuffer();
		gl.glVertex3fv(fb);

		// 1, 3, 2 - counterclockwise
		gl.glTexCoord2d(0, 0);
		fb = p1.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 1);
		fb = p3.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(0, 1);
		fb = p2.floatBuffer();
		gl.glVertex3fv(fb);

		// 1, 3, 4 - clockwise
		gl.glTexCoord2d(0, 0);
		fb = p1.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 1);
		fb = p3.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 0);
		fb = p4.floatBuffer();
		gl.glVertex3fv(fb);

		// 1, 4, 3 - counterclockwise
		gl.glTexCoord2d(0, 0);
		fb = p1.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 0);
		fb = p4.floatBuffer();
		gl.glVertex3fv(fb);

		gl.glTexCoord2d(1, 1);
		fb = p3.floatBuffer();
		gl.glVertex3fv(fb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void control(int type, Object params) {
		switch(type) {
		case KeyEvent.VK_UP:
			//: increase the locomotive velocity
			velocity += 0.01;
			break;
			
		case KeyEvent.VK_DOWN:
			//: decrease the locomotive velocity
			velocity -= 0.01;
			break;
			
		case KeyEvent.VK_ENTER:
			try {
				Method m = TrackPoints.class.getMethod("track" + params);
				trackPoints = (CyclicList<Point>)m.invoke(null);
				//TODO: replace the track with the new one...
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			
		case IRenderable.TOGGLE_LIGHT_SPHERES:
			vehicle.control(type, params);
			break;
			
		default:
			System.out.println("Unsupported operation for Track control");
		}
	}

	@Override
	public boolean isAnimated() {
		return true;
	}

	@Override
	public void setCamera(GL2 gl) {
		//You should use:
//		GLU glu = new GLU();
//		glu.gluLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
		//TODO: set the camera here to follow the locomotive...
	}
	
	@Override
	public void destroy(GL2 gl) {	
		texGrass.destroy(gl);
		texTrack.destroy(gl);
		vehicle.destroy(gl);
	}

}
