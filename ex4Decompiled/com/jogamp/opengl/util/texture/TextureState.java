// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;

public class TextureState
{
    private final int target;
    private final int[] state;
    
    public static final int getTextureTargetQueryName(final int n) {
        int n2 = 0;
        switch (n) {
            case 3553: {
                n2 = 32873;
                break;
            }
            case 34067: {
                n2 = 34068;
                break;
            }
            case 32879: {
                n2 = 32874;
                break;
            }
            case 3552: {
                n2 = 32872;
                break;
            }
            case 35864: {
                n2 = 35868;
                break;
            }
            case 35866: {
                n2 = 35869;
                break;
            }
            case 34037: {
                n2 = 34038;
                break;
            }
            case 35882: {
                n2 = 35884;
                break;
            }
            case 37120: {
                n2 = 37124;
                break;
            }
            case 37122: {
                n2 = 37125;
                break;
            }
            default: {
                n2 = 0;
                break;
            }
        }
        return n2;
    }
    
    private static final String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    private static final int activeTexture(final GL gl) {
        final int[] array = { 0 };
        gl.glGetIntegerv(34016, array, 0);
        return array[0];
    }
    
    public TextureState(final GL gl, final int n) throws GLException {
        this(gl, activeTexture(gl), n);
    }
    
    public TextureState(final GL gl, final int n, final int target) throws GLException {
        this.state = new int[] { 0, 0, 0, 0, 0, 0 };
        this.target = target;
        this.state[0] = n;
        final int textureTargetQueryName = getTextureTargetQueryName(target);
        if (textureTargetQueryName == 0) {
            throw new GLException("Unsupported textureTarget " + toHexString(target));
        }
        gl.glGetIntegerv(textureTargetQueryName, this.state, 1);
        gl.glGetTexParameteriv(this.target, 10240, this.state, 2);
        gl.glGetTexParameteriv(this.target, 10241, this.state, 3);
        gl.glGetTexParameteriv(this.target, 10242, this.state, 4);
        gl.glGetTexParameteriv(this.target, 10243, this.state, 5);
    }
    
    public final void restore(final GL gl) {
        gl.glActiveTexture(this.state[0]);
        gl.glBindTexture(this.target, this.state[1]);
        gl.glTexParameteri(this.target, 10240, this.state[2]);
        gl.glTexParameteri(this.target, 10241, this.state[3]);
        gl.glTexParameteri(this.target, 10242, this.state[4]);
        gl.glTexParameteri(this.target, 10243, this.state[5]);
    }
    
    public final int getUnit() {
        return this.state[0];
    }
    
    public final int getTarget() {
        return this.target;
    }
    
    public final int getObject() {
        return this.state[1];
    }
    
    public final int getMagFilter() {
        return this.state[2];
    }
    
    public final int getMinFilter() {
        return this.state[3];
    }
    
    public final int getWrapS() {
        return this.state[4];
    }
    
    public final int getWrapT() {
        return this.state[5];
    }
    
    @Override
    public final String toString() {
        return "TextureState[unit " + (this.state[0] - 33984) + ", target " + toHexString(this.target) + ": obj " + toHexString(this.state[1]) + ", filter[mag " + toHexString(this.state[2]) + ", min " + toHexString(this.state[3]) + "], " + ": wrap[s " + toHexString(this.state[4]) + ", t " + toHexString(this.state[5]) + "]]";
    }
}
