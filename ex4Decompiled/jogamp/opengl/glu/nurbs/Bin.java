// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Bin
{
    private Arc head;
    private Arc current;
    
    public boolean isnonempty() {
        return this.head != null;
    }
    
    public void addarc(final Arc head) {
        head.link = this.head;
        this.head = head;
    }
    
    public int numarcs() {
        int n = 0;
        for (Arc arc = this.firstarc(); arc != null; arc = this.nextarc()) {
            ++n;
        }
        return n;
    }
    
    public Arc removearc() {
        final Arc head = this.head;
        if (head != null) {
            this.head = head.link;
        }
        return head;
    }
    
    public void adopt() {
        this.markall();
        Arc removearc;
        while ((removearc = this.removearc()) != null) {
            for (Arc arc = removearc.next; !arc.equals(removearc); arc = arc.next) {
                if (!arc.ismarked()) {
                    removearc.link = arc.link;
                    (arc.link = removearc).clearmark();
                    break;
                }
            }
        }
    }
    
    private void markall() {
        for (Arc arc = this.firstarc(); arc != null; arc = this.nextarc()) {
            arc.setmark();
        }
    }
    
    private Arc firstarc() {
        this.current = this.head;
        return this.nextarc();
    }
    
    private Arc nextarc() {
        final Arc current = this.current;
        if (current != null) {
            this.current = current.link;
        }
        return current;
    }
}
