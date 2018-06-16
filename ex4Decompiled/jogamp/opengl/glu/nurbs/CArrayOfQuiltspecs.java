// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class CArrayOfQuiltspecs
{
    private Quiltspec[] array;
    private int pointer;
    
    public CArrayOfQuiltspecs(final Quiltspec[] array, final int pointer) {
        this.array = array;
        this.pointer = pointer;
    }
    
    public CArrayOfQuiltspecs(final CArrayOfQuiltspecs cArrayOfQuiltspecs) {
        this.array = cArrayOfQuiltspecs.array;
        this.pointer = cArrayOfQuiltspecs.pointer;
    }
    
    public CArrayOfQuiltspecs(final Quiltspec[] array) {
        this.array = array;
        this.pointer = 0;
    }
    
    public Quiltspec get() {
        return this.array[this.pointer];
    }
    
    public void pp() {
        ++this.pointer;
    }
    
    public void set(final Quiltspec quiltspec) {
        this.array[this.pointer] = quiltspec;
    }
    
    public Quiltspec get(final int n) {
        return this.array[n];
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
    
    public Quiltspec[] getArray() {
        return this.array;
    }
    
    public void setArray(final Quiltspec[] array) {
        this.array = array;
    }
}
