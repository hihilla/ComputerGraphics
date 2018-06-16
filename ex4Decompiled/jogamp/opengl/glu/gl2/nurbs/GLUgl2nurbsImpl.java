// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.gl2.nurbs;

import jogamp.opengl.glu.nurbs.Property;
import jogamp.opengl.glu.nurbs.Mapdesc;
import jogamp.opengl.glu.nurbs.CArrayOfFloats;
import jogamp.opengl.glu.nurbs.Quilt;
import jogamp.opengl.glu.nurbs.Knotvector;
import jogamp.opengl.glu.nurbs.O_nurbssurface;
import jogamp.opengl.glu.nurbs.Backend;
import jogamp.opengl.glu.nurbs.O_trim;
import jogamp.opengl.glu.nurbs.O_surface;
import jogamp.opengl.glu.nurbs.O_nurbscurve;
import jogamp.opengl.glu.nurbs.Subdivider;
import jogamp.opengl.glu.nurbs.Renderhints;
import jogamp.opengl.glu.nurbs.O_curve;
import jogamp.opengl.glu.nurbs.Maplist;
import com.jogamp.opengl.glu.GLUnurbs;

public class GLUgl2nurbsImpl implements GLUnurbs
{
    public static final int CT_NONE = 0;
    public static final int CT_NURBSCURVE = 1;
    public static final int CT_NPWLCURVE = 2;
    private final boolean autoloadmode;
    Maplist maplist;
    private int isDataValid;
    private int inCurve;
    private O_curve currentCurve;
    private boolean inTrim;
    private boolean playBack;
    private O_curve nextCurve;
    private final Renderhints renderhints;
    private final Subdivider subdivider;
    private O_nurbscurve nextNurbscurve;
    private int inSurface;
    private int numTrims;
    private O_surface currentSurface;
    private O_trim nextTrim;
    
    public GLUgl2nurbsImpl() {
        this.maplist = new Maplist();
        this.renderhints = new Renderhints();
        this.subdivider = new Subdivider();
        this.redefineMaps();
        this.defineMap(3506, 0, 3);
        this.defineMap(3474, 0, 3);
        this.defineMap(3507, 0, 1);
        this.defineMap(3475, 0, 1);
        this.defineMap(3508, 0, 2);
        this.defineMap(3476, 0, 2);
        this.defineMap(3509, 0, 3);
        this.defineMap(3477, 0, 3);
        this.defineMap(3510, 1, 4);
        this.defineMap(3478, 1, 4);
        this.defineMap(3512, 1, 4);
        this.defineMap(3480, 1, 4);
        this.defineMap(3511, 0, 3);
        this.defineMap(3479, 0, 3);
        this.defineMap(3504, 0, 4);
        this.defineMap(3472, 0, 4);
        this.defineMap(3505, 0, 1);
        this.defineMap(3473, 0, 1);
        this.setnurbsproperty(3479, 10, 6.0f);
        this.setnurbsproperty(3480, 10, 6.0f);
        this.setnurbsproperty(3511, 10, 6.0f);
        this.setnurbsproperty(3512, 10, 6.0f);
        this.setnurbsproperty(3479, 1, 50.0f);
        this.setnurbsproperty(3480, 1, 50.0f);
        this.setnurbsproperty(3511, 1, 50.0f);
        this.setnurbsproperty(3512, 1, 50.0f);
        this.setnurbsproperty(3479, 20, 0.5f);
        this.setnurbsproperty(3480, 20, 0.5f);
        this.setnurbsproperty(3511, 20, 0.5f);
        this.setnurbsproperty(3512, 20, 0.5f);
        this.setnurbsproperty(3479, 6, 100.0f);
        this.setnurbsproperty(3480, 6, 100.0f);
        this.setnurbsproperty(3511, 6, 100.0f);
        this.setnurbsproperty(3512, 6, 100.0f);
        this.setnurbsproperty(3479, 10, 6.0f);
        this.set_domain_distance_u_rate(100.0);
        this.set_domain_distance_v_rate(100.0);
        this.set_is_domain_distance_sampling(0);
        this.autoloadmode = true;
    }
    
    private void set_domain_distance_u_rate(final double n) {
        this.subdivider.set_domain_distance_u_rate(n);
    }
    
    private void set_domain_distance_v_rate(final double n) {
        this.subdivider.set_domain_distance_v_rate(n);
    }
    
    public void bgncurve() {
        this.thread("do_bgncurve", new O_curve());
    }
    
    private void thread(final String s, final Object o) {
        final Class[] array = { o.getClass() };
        try {
            this.getClass().getMethod(s, (Class<?>[])array).invoke(this, o);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void thread2(final String s) {
        try {
            this.getClass().getMethod(s, (Class<?>[])null).invoke(this, (Object[])null);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void do_bgncurve(final O_curve o_curve) {
        if (this.inCurve > 0) {
            this.do_nurbserror(6);
            this.endcurve();
        }
        this.inCurve = 1;
        this.currentCurve = o_curve;
        this.currentCurve.curvetype = 0;
        if (this.inTrim) {
            if (!this.nextCurve.equals(o_curve)) {
                this.nextCurve = o_curve;
            }
        }
        else {
            if (!this.playBack) {
                this.bgnrender();
            }
            this.isDataValid = 1;
        }
        this.nextCurve = o_curve.next;
        this.nextNurbscurve = o_curve.o_nurbscurve;
    }
    
    public void do_bgnsurface(final O_surface currentSurface) {
        if (this.inSurface > 0) {
            this.do_nurbserror(27);
            this.endsurface();
        }
        this.inSurface = 1;
        if (!this.playBack) {
            this.bgnrender();
        }
        this.isDataValid = 1;
        this.numTrims = 0;
        this.currentSurface = currentSurface;
        this.nextTrim = currentSurface.o_trim;
    }
    
    public void endcurve() {
        this.thread2("do_endcurve");
    }
    
    public void do_endsurface() {
        if (this.inTrim) {
            this.do_nurbserror(12);
            this.endtrim();
        }
        if (this.inSurface <= 0) {
            this.do_nurbserror(13);
            return;
        }
        this.inSurface = 0;
        if (this.isDataValid <= 0) {
            return;
        }
        if (this.nextTrim != null) {
            this.nextTrim = null;
        }
        if (this.numTrims > 0) {}
        this.subdivider.beginQuilts(new GL2Backend());
        for (O_nurbssurface o_nurbssurface = this.currentSurface.o_nurbssurface; o_nurbssurface != null; o_nurbssurface = o_nurbssurface.next) {
            this.subdivider.addQuilt(o_nurbssurface.bezier_patches);
        }
        this.subdivider.endQuilts();
        this.subdivider.drawSurfaces();
        if (!this.playBack) {
            this.endrender();
        }
    }
    
    public void do_endcurve() {
        if (this.inCurve <= 0) {
            this.do_nurbserror(7);
            return;
        }
        this.inCurve = 0;
        this.nextCurve = null;
        if (this.currentCurve.curvetype == 1) {}
        if (!this.inTrim) {
            if (this.isDataValid <= 0) {
                return;
            }
            if (this.currentCurve.curvetype == 1) {
                this.subdivider.beginQuilts(new GL2Backend());
                for (O_nurbscurve o_nurbscurve = this.currentCurve.o_nurbscurve; o_nurbscurve != null; o_nurbscurve = o_nurbscurve.next) {
                    this.subdivider.addQuilt(o_nurbscurve.bezier_curves);
                }
                this.subdivider.endQuilts();
                this.subdivider.drawCurves();
                if (!this.playBack) {
                    this.endrender();
                }
            }
            else {
                if (!this.playBack) {
                    this.endrender();
                }
                this.do_nurbserror(9);
            }
        }
    }
    
    private void do_nurbserror(final int n) {
    }
    
    private void bgnrender() {
        if (this.autoloadmode) {
            this.loadGLMatrices();
        }
    }
    
    private void loadGLMatrices() {
    }
    
    private void endrender() {
    }
    
    public void nurbscurve(final int n, final float[] array, final int n2, final float[] array2, final int n3, final int n4) {
        final Mapdesc locate = this.maplist.locate(n4);
        if (locate == null) {
            this.do_nurbserror(35);
            this.isDataValid = 0;
            return;
        }
        if (array2 == null) {
            this.do_nurbserror(36);
            this.isDataValid = 0;
            return;
        }
        if (n2 < 0) {
            this.do_nurbserror(34);
            this.isDataValid = 0;
            return;
        }
        final Knotvector knotvector = new Knotvector(n, n2, n3, array);
        if (!this.do_check_knots(knotvector, "curve")) {
            return;
        }
        final O_nurbscurve o_nurbscurve = new O_nurbscurve(n4);
        (o_nurbscurve.bezier_curves = new Quilt(locate)).toBezier(knotvector, new CArrayOfFloats(array2), locate.getNCoords());
        this.thread("do_nurbscurve", o_nurbscurve);
    }
    
    public boolean do_check_knots(final Knotvector knotvector, final String s) {
        final int validate = knotvector.validate();
        if (validate > 0) {
            this.do_nurbserror(validate);
            if (this.renderhints.errorchecking != 0) {
                knotvector.show(s);
            }
        }
        return validate <= 0;
    }
    
    public void do_nurbscurve(final O_nurbscurve o_nurbscurve) {
        if (this.inCurve <= 0) {
            this.bgncurve();
            this.inCurve = 2;
        }
        if (o_nurbscurve.used) {
            this.do_nurbserror(23);
            this.isDataValid = 0;
            return;
        }
        o_nurbscurve.used = true;
        if (this.currentCurve.curvetype == 0) {
            this.currentCurve.curvetype = 1;
        }
        else if (this.currentCurve.curvetype != 1) {
            this.do_nurbserror(24);
            this.isDataValid = 0;
            return;
        }
        if (!o_nurbscurve.equals(this.currentCurve.o_nurbscurve)) {
            this.currentCurve.o_nurbscurve = o_nurbscurve;
        }
        this.nextNurbscurve = o_nurbscurve.next;
        if (!this.currentCurve.equals(o_nurbscurve.owner)) {
            o_nurbscurve.owner = this.currentCurve;
        }
        if (this.inCurve == 2) {
            this.endcurve();
        }
    }
    
    public void do_nurbssurface(final O_nurbssurface o_nurbssurface) {
        if (this.inSurface <= 0) {
            this.bgnsurface();
            this.inSurface = 2;
        }
        if (o_nurbssurface.used) {
            this.do_nurbserror(25);
            this.isDataValid = 0;
            return;
        }
        o_nurbssurface.used = true;
        this.currentSurface.o_nurbssurface = o_nurbssurface;
        if (!this.currentSurface.equals(o_nurbssurface.owner)) {
            o_nurbssurface.owner = this.currentSurface;
        }
        if (this.inSurface == 2) {
            this.endsurface();
        }
    }
    
    public void redefineMaps() {
        this.maplist.initialize();
    }
    
    public void defineMap(final int n, final int n2, final int n3) {
        this.maplist.define(n, n2, n3);
    }
    
    public void setnurbsproperty(final int n, final int n2, final float n3) {
        final Mapdesc locate = this.maplist.locate(n);
        if (locate == null) {
            this.do_nurbserror(35);
            return;
        }
        if (!locate.isProperty(n2)) {
            this.do_nurbserror(26);
            return;
        }
        this.thread("do_setnurbsproperty2", new Property(n, n2, n3));
    }
    
    public void do_setnurbsproperty2(final Property property) {
        this.maplist.find(property.type).setProperty(property.tag, property.value);
    }
    
    public void do_setnurbsproperty(final Property property) {
        this.renderhints.setProperty(property);
    }
    
    public void set_is_domain_distance_sampling(final int n) {
        this.subdivider.set_is_domain_distance_sampling(n);
    }
    
    public void bgnsurface() {
        this.thread("do_bgnsurface", new O_surface());
    }
    
    public void endsurface() {
        this.thread2("do_endsurface");
    }
    
    private void endtrim() {
    }
    
    public void nurbssurface(final int n, final float[] array, final int n2, final float[] array2, final int n3, final int n4, final float[] array3, final int n5, final int n6, final int n7) {
        final Mapdesc locate = this.maplist.locate(n7);
        if (locate == null) {
            this.do_nurbserror(35);
            this.isDataValid = 0;
            return;
        }
        if (n3 < 0 || n4 < 0) {
            this.do_nurbserror(34);
            this.isDataValid = 0;
            return;
        }
        final Knotvector knotvector = new Knotvector(n, n3, n5, array);
        if (!this.do_check_knots(knotvector, "surface")) {
            return;
        }
        final Knotvector knotvector2 = new Knotvector(n2, n4, n6, array2);
        if (!this.do_check_knots(knotvector2, "surface")) {
            return;
        }
        final O_nurbssurface o_nurbssurface = new O_nurbssurface(n7);
        (o_nurbssurface.bezier_patches = new Quilt(locate)).toBezier(knotvector, knotvector2, new CArrayOfFloats(array3), locate.getNCoords());
        this.thread("do_nurbssurface", o_nurbssurface);
    }
}
