// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Arc
{
    public PwlArc pwlArc;
    private long type;
    public Arc link;
    Arc prev;
    Arc next;
    private final BezierArc bezierArc;
    public static final int ARC_NONE = 0;
    public static final int ARC_RIGHT = 1;
    public static final int ARC_TOP = 2;
    public static final int ARC_LEFT = 3;
    public static final int ARC_BOTTOM = 4;
    private static final long BEZIER_TAG = 8192L;
    private static final long ARC_TAG = 8L;
    private static final long TAIL_TAG = 64L;
    
    public Arc(final int n) {
        this.bezierArc = null;
        this.pwlArc = null;
        this.type = 0L;
        this.setside(n);
    }
    
    private void setside(final int n) {
        this.clearside();
        this.type |= n << 8;
    }
    
    private void clearside() {
        this.type &= 0xFFFFFFFFFFFFF8FFL;
    }
    
    public Arc append(final Arc prev) {
        if (prev != null) {
            this.next = prev.next;
            this.prev = prev;
            this.next.prev = this;
            this.prev.next = this;
        }
        else {
            this.next = this;
            this.prev = this;
        }
        return this;
    }
    
    public boolean check() {
        return true;
    }
    
    public void setbezier() {
        this.type |= 0x2000L;
    }
    
    public float[] tail() {
        return this.pwlArc.pts[0].param;
    }
    
    public float[] head() {
        return this.next.pwlArc.pts[0].param;
    }
    
    public boolean ismarked() {
        return (this.type & 0x8L) > 0L;
    }
    
    public void clearmark() {
        this.type &= 0xFFFFFFFFFFFFFFF7L;
    }
    
    public void setmark() {
        this.type |= 0x8L;
    }
    
    public void setitail() {
        this.type |= 0x40L;
    }
    
    public boolean getitail() {
        return false;
    }
    
    public void clearitail() {
        this.type &= 0xFFFFFFFFFFFFFFBFL;
    }
}
