// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class CArrayOfFloats
{
    private float[] array;
    private int pointer;
    private final boolean noCheck = true;
    
    public CArrayOfFloats(final float[] array, final int pointer) {
        this.array = array;
        this.setPointer(pointer);
    }
    
    public CArrayOfFloats(final CArrayOfFloats cArrayOfFloats) {
        this.array = cArrayOfFloats.array;
        this.setPointer(cArrayOfFloats.pointer);
    }
    
    public CArrayOfFloats(final float[] array) {
        this.array = array;
        this.pointer = 0;
    }
    
    public float get() {
        return this.array[this.pointer];
    }
    
    public void pp() {
        this.setPointer(this.pointer + 1);
    }
    
    public void set(final float n) {
        this.array[this.pointer] = n;
    }
    
    public float get(final int n) {
        return this.array[n];
    }
    
    public float getRelative(final int n) {
        return this.array[this.pointer + n];
    }
    
    public void setRelative(final int n, final float n2) {
        this.array[this.pointer + n] = n2;
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
    
    public float[] getArray() {
        return this.array;
    }
    
    public void setArray(final float[] array) {
        this.array = array;
    }
}
