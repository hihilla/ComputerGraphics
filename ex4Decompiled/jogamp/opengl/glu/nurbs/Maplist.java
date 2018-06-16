// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Maplist
{
    private Mapdesc maps;
    
    public void initialize() {
        this.maps = null;
    }
    
    public void define(final int n, final int n2, final int n3) {
        final Mapdesc locate = this.locate(n);
        assert locate.isrational == n2 && locate.ncoords == n3;
        this.add(n, n2, n3);
    }
    
    private void add(final int n, final int n2, final int n3) {
        final Mapdesc mapdesc = new Mapdesc(n, n2, n3);
        if (this.maps == null) {
            this.maps = mapdesc;
        }
        else {
            mapdesc.next = this.maps;
            this.maps = mapdesc;
        }
    }
    
    public Mapdesc locate(final int n) {
        Mapdesc mapdesc;
        for (mapdesc = this.maps; mapdesc != null && mapdesc.getType() != n; mapdesc = mapdesc.next) {}
        return mapdesc;
    }
    
    public Mapdesc find(final int n) {
        return this.locate(n);
    }
}
