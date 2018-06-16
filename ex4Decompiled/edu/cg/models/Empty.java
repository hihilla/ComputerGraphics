// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import com.jogamp.opengl.GL2;

public class Empty implements IRenderable
{
    private boolean isLightSpheres;
    
    @Override
    public void render(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        try {
            return new String(Base64.getDecoder().decode("RW1wdHk="), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    @Override
    public void control(final int type, final Object params) {
        switch (type) {
            case 0: {
                this.isLightSpheres = !this.isLightSpheres;
                break;
            }
            default: {
                System.out.println("Control type not supported: " + this.toString() + ", " + type);
                break;
            }
        }
    }
    
    @Override
    public boolean isAnimated() {
        return false;
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void setCamera(final GL2 gl) {
    }
}
