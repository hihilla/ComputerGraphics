// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL;

public class GLPixelStorageModes
{
    private final int[] cachePack;
    private final int[] cacheUnpack;
    private boolean savedPack;
    private boolean savedUnpack;
    
    public GLPixelStorageModes() {
        this.cachePack = new int[8];
        this.cacheUnpack = new int[8];
        this.savedPack = false;
        this.savedUnpack = false;
    }
    
    public GLPixelStorageModes(final GL gl) {
        this.cachePack = new int[8];
        this.cacheUnpack = new int[8];
        this.savedPack = false;
        this.savedUnpack = false;
        this.saveAll(gl);
    }
    
    public final void setPackAlignment(final GL gl, final int n) {
        this.savePack(gl);
        gl.glPixelStorei(3333, n);
    }
    
    public final void setUnpackAlignment(final GL gl, final int n) {
        this.saveUnpack(gl);
        gl.glPixelStorei(3317, n);
    }
    
    public final void setAlignment(final GL gl, final int n, final int n2) {
        this.setPackAlignment(gl, n);
        this.setUnpackAlignment(gl, n2);
    }
    
    public final void setPackRowLength(final GL2ES3 gl2ES3, final int n) {
        this.savePack(gl2ES3);
        gl2ES3.glPixelStorei(3330, n);
    }
    
    public final void setUnpackRowLength(final GL2ES3 gl2ES3, final int n) {
        this.saveUnpack(gl2ES3);
        gl2ES3.glPixelStorei(3314, n);
    }
    
    public final void setRowLength(final GL2ES3 gl2ES3, final int n, final int n2) {
        this.setPackRowLength(gl2ES3, n);
        this.setUnpackRowLength(gl2ES3, n2);
    }
    
    public final void saveAll(final GL gl) {
        this.savePack(gl);
        this.saveUnpack(gl);
    }
    
    public final void resetAll(final GL gl) {
        this.resetPack(gl);
        this.resetUnpack(gl);
    }
    
    public final void restore(final GL gl) throws GLException {
        if (!this.savedPack && !this.savedUnpack) {
            throw new GLException("Neither PACK nor UNPACK pixel storage modes were saved");
        }
        if (this.savedPack) {
            this.restorePack(gl);
            this.savedPack = false;
        }
        if (this.savedUnpack) {
            this.restoreUnpack(gl);
            this.savedUnpack = false;
        }
    }
    
    public final void resetPack(final GL gl) {
        gl.glPixelStorei(3333, 4);
        if (gl.isGL2ES3()) {
            gl.glPixelStorei(3330, 0);
            gl.glPixelStorei(3331, 0);
            gl.glPixelStorei(3332, 0);
            if (gl.isGL2GL3()) {
                gl.glPixelStorei(3328, 0);
                gl.glPixelStorei(3329, 0);
                if (gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0) {
                    gl.glPixelStorei(32876, 0);
                    gl.glPixelStorei(32875, 0);
                }
            }
        }
    }
    
    public final void savePack(final GL gl) {
        if (this.savedPack) {
            return;
        }
        if (gl.isGL2()) {
            gl.getGL2().glPushClientAttrib(1);
        }
        else {
            gl.glGetIntegerv(3333, this.cachePack, 0);
            if (gl.isGL2ES3()) {
                gl.glGetIntegerv(3330, this.cachePack, 1);
                gl.glGetIntegerv(3331, this.cachePack, 2);
                gl.glGetIntegerv(3332, this.cachePack, 3);
                if (gl.isGL2GL3()) {
                    gl.glGetIntegerv(3328, this.cachePack, 4);
                    gl.glGetIntegerv(3329, this.cachePack, 5);
                    gl.glGetIntegerv(32876, this.cachePack, 6);
                    gl.glGetIntegerv(32875, this.cachePack, 7);
                }
            }
        }
        this.savedPack = true;
        this.resetPack(gl);
    }
    
    private final void restorePack(final GL gl) {
        if (gl.isGL2()) {
            gl.getGL2().glPopClientAttrib();
        }
        else {
            gl.glPixelStorei(3333, this.cachePack[0]);
            if (gl.isGL2ES3()) {
                gl.glPixelStorei(3330, this.cachePack[1]);
                gl.glPixelStorei(3331, this.cachePack[2]);
                gl.glPixelStorei(3332, this.cachePack[3]);
                if (gl.isGL2GL3()) {
                    gl.glPixelStorei(3328, this.cachePack[4]);
                    gl.glPixelStorei(3329, this.cachePack[5]);
                    gl.glPixelStorei(32876, this.cachePack[6]);
                    gl.glPixelStorei(32875, this.cachePack[7]);
                }
            }
        }
    }
    
    public final void resetUnpack(final GL gl) {
        gl.glPixelStorei(3317, 4);
        if (gl.isGL2ES3()) {
            gl.glPixelStorei(3314, 0);
            gl.glPixelStorei(3315, 0);
            gl.glPixelStorei(3316, 0);
            if (gl.isGL2GL3()) {
                if (gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0) {
                    gl.glPixelStorei(32878, 0);
                    gl.glPixelStorei(32877, 0);
                }
                gl.glPixelStorei(3312, 0);
                gl.glPixelStorei(3313, 0);
            }
            else {
                gl.glPixelStorei(32878, 0);
                gl.glPixelStorei(32877, 0);
            }
        }
    }
    
    public final void saveUnpack(final GL gl) {
        if (this.savedUnpack) {
            return;
        }
        if (gl.isGL2()) {
            gl.getGL2().glPushClientAttrib(1);
        }
        else {
            gl.glGetIntegerv(3317, this.cacheUnpack, 0);
            if (gl.isGL2ES3()) {
                gl.glGetIntegerv(3314, this.cacheUnpack, 1);
                gl.glGetIntegerv(3315, this.cacheUnpack, 2);
                gl.glGetIntegerv(3316, this.cacheUnpack, 3);
                gl.glGetIntegerv(32878, this.cacheUnpack, 4);
                gl.glGetIntegerv(32877, this.cacheUnpack, 5);
                if (gl.isGL2GL3()) {
                    gl.glGetIntegerv(3312, this.cacheUnpack, 6);
                    gl.glGetIntegerv(3313, this.cacheUnpack, 7);
                }
            }
        }
        this.savedUnpack = true;
        this.resetUnpack(gl);
    }
    
    private final void restoreUnpack(final GL gl) {
        if (gl.isGL2()) {
            gl.getGL2().glPopClientAttrib();
        }
        else {
            gl.glPixelStorei(3317, this.cacheUnpack[0]);
            if (gl.isGL2ES3()) {
                gl.glPixelStorei(3314, this.cacheUnpack[1]);
                gl.glPixelStorei(3315, this.cacheUnpack[2]);
                gl.glPixelStorei(3316, this.cacheUnpack[3]);
                gl.glPixelStorei(32878, this.cacheUnpack[4]);
                gl.glPixelStorei(32877, this.cacheUnpack[5]);
                if (gl.isGL2GL3()) {
                    gl.glPixelStorei(3312, this.cacheUnpack[6]);
                    gl.glPixelStorei(3313, this.cacheUnpack[7]);
                }
            }
        }
    }
}
