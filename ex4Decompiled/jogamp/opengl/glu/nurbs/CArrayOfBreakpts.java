// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class CArrayOfBreakpts
{
    private final Breakpt[] pole;
    private int pointer;
    
    public CArrayOfBreakpts(final Breakpt[] pole, final int pointer) {
        this.pole = pole;
        this.pointer = pointer;
    }
    
    public CArrayOfBreakpts(final CArrayOfBreakpts cArrayOfBreakpts) {
        this.pole = cArrayOfBreakpts.pole;
        this.pointer = cArrayOfBreakpts.pointer;
    }
    
    public Breakpt get() {
        return this.pole[this.pointer];
    }
    
    public void pp() {
        ++this.pointer;
    }
    
    public void set(final Breakpt breakpt) {
        this.pole[this.pointer] = breakpt;
    }
    
    public Breakpt get(final int n) {
        return this.pole[n];
    }
    
    public void lessenPointerBy(final int n) {
        this.pointer -= n;
    }
    
    public int getPointer() {
        return this.pointer;
    }
    
    public void setPointer(final int pointer) {
        this.pointer = pointer;
    }
    
    public void raisePointerBy(final int n) {
        this.pointer += n;
    }
    
    public void mm() {
        --this.pointer;
    }
}
