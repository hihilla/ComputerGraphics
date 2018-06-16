// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Type_Widget
{
    ByteBuffer buffer;
    
    public Type_Widget() {
        this.buffer = ByteBuffer.allocate(4);
    }
    
    public void setUB0(final byte b) {
        this.buffer.position(0);
        this.buffer.put(b);
    }
    
    public byte getUB0() {
        this.buffer.position(0);
        return this.buffer.get();
    }
    
    public void setUB1(final byte b) {
        this.buffer.position(1);
        this.buffer.put(b);
    }
    
    public byte getUB1() {
        this.buffer.position(1);
        return this.buffer.get();
    }
    
    public void setUB2(final byte b) {
        this.buffer.position(2);
        this.buffer.put(b);
    }
    
    public byte getUB2() {
        this.buffer.position(2);
        return this.buffer.get();
    }
    
    public void setUB3(final byte b) {
        this.buffer.position(3);
        this.buffer.put(b);
    }
    
    public byte getUB3() {
        this.buffer.position(3);
        return this.buffer.get();
    }
    
    public void setUS0(final short n) {
        this.buffer.position(0);
        this.buffer.putShort(n);
    }
    
    public short getUS0() {
        this.buffer.position(0);
        return this.buffer.getShort();
    }
    
    public void setUS1(final short n) {
        this.buffer.position(2);
        this.buffer.putShort(n);
    }
    
    public short getUS1() {
        this.buffer.position(2);
        return this.buffer.getShort();
    }
    
    public void setUI(final int n) {
        this.buffer.position(0);
        this.buffer.putInt(n);
    }
    
    public int getUI() {
        this.buffer.position(0);
        return this.buffer.getInt();
    }
    
    public void setB0(final byte b) {
        this.buffer.position(0);
        this.buffer.put(b);
    }
    
    public byte getB0() {
        this.buffer.position(0);
        return this.buffer.get();
    }
    
    public void setB1(final byte b) {
        this.buffer.position(1);
        this.buffer.put(b);
    }
    
    public byte getB1() {
        this.buffer.position(1);
        return this.buffer.get();
    }
    
    public void setB2(final byte b) {
        this.buffer.position(2);
        this.buffer.put(b);
    }
    
    public byte getB2() {
        this.buffer.position(2);
        return this.buffer.get();
    }
    
    public void setB3(final byte b) {
        this.buffer.position(3);
        this.buffer.put(b);
    }
    
    public byte getB3() {
        this.buffer.position(3);
        return this.buffer.get();
    }
    
    public void setS0(final short n) {
        this.buffer.position(0);
        this.buffer.putShort(n);
    }
    
    public short getS0() {
        this.buffer.position(0);
        return this.buffer.getShort();
    }
    
    public void setS1(final short n) {
        this.buffer.position(2);
        this.buffer.putShort(n);
    }
    
    public short getS1() {
        this.buffer.position(2);
        return this.buffer.getShort();
    }
    
    public void setI(final int n) {
        this.buffer.position(0);
        this.buffer.putInt(n);
    }
    
    public int getI() {
        this.buffer.position(0);
        return this.buffer.getInt();
    }
    
    public void setF(final float n) {
        this.buffer.position(0);
        this.buffer.putFloat(n);
    }
    
    public float getF() {
        this.buffer.position(0);
        return this.buffer.getFloat();
    }
    
    public ByteBuffer getBuffer() {
        this.buffer.rewind();
        return this.buffer;
    }
    
    public static void main(final String[] array) {
        final Type_Widget type_Widget = new Type_Widget();
        type_Widget.setI(1000000);
        System.out.println("int: " + Integer.toHexString(type_Widget.getI()));
    }
}
