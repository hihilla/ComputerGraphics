// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class CArrayOfArcs
{
    private Arc[] array;
    private int pointer;
    private final boolean noCheck = true;
    
    public CArrayOfArcs(final Arc[] array, final int pointer) {
        this.array = array;
        this.setPointer(pointer);
    }
    
    public CArrayOfArcs(final CArrayOfArcs cArrayOfArcs) {
        this.array = cArrayOfArcs.array;
        this.setPointer(cArrayOfArcs.pointer);
    }
    
    public CArrayOfArcs(final Arc[] array) {
        this.array = array;
        this.pointer = 0;
    }
    
    public Arc get() {
        return this.array[this.pointer];
    }
    
    public void pp() {
        this.setPointer(this.pointer + 1);
    }
    
    public void set(final Arc arc) {
        this.array[this.pointer] = arc;
    }
    
    public Arc get(final int n) {
        return this.array[n];
    }
    
    public Arc getRelative(final int n) {
        return this.array[this.pointer + n];
    }
    
    public void setRelative(final int n, final Arc arc) {
        this.array[this.pointer + n] = arc;
    }
    
    public void lessenPointerBy(final int n) {
        this.setPointer(this.pointer - n);
    }
    
    public int getPointer() {
        return this.pointer;
    }
    
    public void setPointer(final int pointer) {
        this.pointer = pointer;
    }
    
    public void raisePointerBy(final int n) {
        this.setPointer(this.pointer + n);
    }
    
    public void mm() {
        this.setPointer(this.pointer - 1);
    }
    
    public Arc[] getArray() {
        return this.array;
    }
    
    public void setArray(final Arc[] array) {
        this.array = array;
    }
}
