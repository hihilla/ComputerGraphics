// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.FBObject;
import jogamp.opengl.GLDrawableHelper;
import com.jogamp.opengl.GLEventListener;

public class StereoClientRenderer implements GLEventListener
{
    private final GLDrawableHelper helper;
    private final StereoDeviceRenderer deviceRenderer;
    private final boolean ownsDevice;
    private final FBObject[] fbos;
    private final int magFilter;
    private final int minFilter;
    private int numSamples;
    private final FBObject.TextureAttachment[] fboTexs;
    
    public StereoClientRenderer(final StereoDeviceRenderer deviceRenderer, final boolean ownsDevice, final int magFilter, final int minFilter, final int numSamples) {
        final int textureCount = deviceRenderer.getTextureCount();
        if (0 > textureCount || 2 < textureCount) {
            throw new IllegalArgumentException("fboCount must be within [0..2], has " + textureCount + ", due to " + deviceRenderer);
        }
        this.helper = new GLDrawableHelper();
        this.deviceRenderer = deviceRenderer;
        this.ownsDevice = ownsDevice;
        this.magFilter = magFilter;
        this.minFilter = minFilter;
        this.numSamples = numSamples;
        this.fbos = new FBObject[textureCount];
        for (int i = 0; i < textureCount; ++i) {
            this.fbos[i] = new FBObject();
        }
        this.fboTexs = new FBObject.TextureAttachment[textureCount];
    }
    
    private void initFBOs(final GL gl, final DimensionImmutable[] array) {
        for (int i = 0; i < this.fbos.length; ++i) {
            this.fbos[i].init(gl, array[i].getWidth(), array[i].getHeight(), this.numSamples);
            if (i > 0 && this.fbos[i - 1].getNumSamples() != this.fbos[i].getNumSamples()) {
                throw new InternalError("sample size mismatch: \n\t0: " + this.fbos[i - 1] + "\n\t1: " + this.fbos[i]);
            }
            this.numSamples = this.fbos[i].getNumSamples();
            if (this.numSamples > 0) {
                this.fbos[i].attachColorbuffer(gl, 0, true);
                this.fbos[i].attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH, 0);
                final FBObject samplingSink = new FBObject();
                samplingSink.init(gl, array[i].getWidth(), array[i].getHeight(), 0);
                samplingSink.attachTexture2D(gl, 0, false, this.magFilter, this.minFilter, 33071, 33071);
                samplingSink.attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH, 0);
                this.fbos[i].setSamplingSink(samplingSink);
                this.fbos[i].resetSamplingSink(gl);
                this.fboTexs[i] = this.fbos[i].getSamplingSink().getTextureAttachment();
            }
            else {
                this.fboTexs[i] = this.fbos[i].attachTexture2D(gl, 0, false, this.magFilter, this.minFilter, 33071, 33071);
                this.fbos[i].attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH, 0);
            }
            this.fbos[i].unbind(gl);
            System.err.println("FBO[" + i + "]: " + this.fbos[i]);
        }
    }
    
    private void resetFBOs(final GL gl, final DimensionImmutable dimensionImmutable) {
        for (int i = 0; i < this.fbos.length; ++i) {
            this.fbos[i].reset(gl, dimensionImmutable.getWidth(), dimensionImmutable.getHeight(), this.numSamples);
            if (i > 0 && this.fbos[i - 1].getNumSamples() != this.fbos[i].getNumSamples()) {
                throw new InternalError("sample size mismatch: \n\t0: " + this.fbos[i - 1] + "\n\t1: " + this.fbos[i]);
            }
            this.numSamples = this.fbos[i].getNumSamples();
            if (this.numSamples > 0) {
                this.fboTexs[i] = this.fbos[i].getSamplingSink().getTextureAttachment();
            }
            else {
                this.fboTexs[i] = this.fbos[i].getColorbuffer(0).getTextureAttachment();
            }
        }
    }
    
    public final StereoDeviceRenderer getStereoDeviceRenderer() {
        return this.deviceRenderer;
    }
    
    public final void addGLEventListener(final StereoGLEventListener stereoGLEventListener) {
        this.helper.addGLEventListener(stereoGLEventListener);
    }
    
    public final void removeGLEventListener(final StereoGLEventListener stereoGLEventListener) {
        this.helper.removeGLEventListener(stereoGLEventListener);
    }
    
    @Override
    public void init(final GLAutoDrawable glAutoDrawable) {
        final GL2ES2 gl2ES2 = glAutoDrawable.getGL().getGL2ES2();
        this.deviceRenderer.init(gl2ES2);
        final DimensionImmutable[] array;
        if (this.deviceRenderer.getTextureCount() > 1) {
            this.deviceRenderer.getEyeSurfaceSize();
        }
        else {
            array = new DimensionImmutable[] { this.deviceRenderer.getTotalSurfaceSize() };
        }
        this.initFBOs(gl2ES2, array);
        this.helper.init(glAutoDrawable, false);
        gl2ES2.setSwapInterval(1);
    }
    
    @Override
    public void dispose(final GLAutoDrawable glAutoDrawable) {
        final GL2ES2 gl2ES2 = glAutoDrawable.getGL().getGL2ES2();
        this.helper.disposeAllGLEventListener(glAutoDrawable, false);
        for (int i = 0; i < this.fbos.length; ++i) {
            this.fbos[i].destroy(gl2ES2);
            this.fboTexs[i] = null;
        }
        if (this.ownsDevice) {
            this.deviceRenderer.dispose(gl2ES2);
        }
    }
    
    @Override
    public void display(final GLAutoDrawable glAutoDrawable) {
        final GL2ES2 gl2ES2 = glAutoDrawable.getGL().getGL2ES2();
        this.deviceRenderer.beginFrame(gl2ES2);
        if (0 < this.numSamples) {
            gl2ES2.glEnable(32925);
        }
        final int length = this.fbos.length;
        int n;
        if (1 >= length) {
            n = 2;
        }
        else {
            n = 0;
        }
        final int[] eyeRenderOrder = this.deviceRenderer.getDevice().getEyeRenderOrder();
        final int length2 = eyeRenderOrder.length;
        final ViewerPose updateViewerPose = this.deviceRenderer.updateViewerPose();
        if (length != 0) {
            this.fbos[0].bind(gl2ES2);
        }
        for (int i = 0; i < length2; ++i) {
            final int n2 = eyeRenderOrder[i];
            if (1 < length) {
                this.fbos[n2].bind(gl2ES2);
            }
            final StereoDeviceRenderer.Eye eye = this.deviceRenderer.getEye(n2);
            final RectangleImmutable viewport = eye.getViewport();
            gl2ES2.glViewport(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
            this.helper.runForAllGLEventListener(glAutoDrawable, new GLDrawableHelper.GLEventListenerAction() {
                final /* synthetic */ int val$displayFlags = (i > 0) ? (0x1 | n) : false;
                
                @Override
                public void run(final GLAutoDrawable glAutoDrawable, final GLEventListener glEventListener) {
                    final StereoGLEventListener stereoGLEventListener = (StereoGLEventListener)glEventListener;
                    stereoGLEventListener.reshapeForEye(glAutoDrawable, viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight(), eye.getEyeParameter(), updateViewerPose);
                    stereoGLEventListener.display(glAutoDrawable, this.val$displayFlags);
                }
            });
            if (1 < length) {
                this.fbos[n2].unbind(gl2ES2);
            }
        }
        if (length != 0) {
            this.fbos[0].unbind(gl2ES2);
        }
        gl2ES2.glViewport(0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
        if (this.deviceRenderer.ppAvailable()) {
            this.deviceRenderer.ppBegin(gl2ES2);
            if (length != 0) {
                this.fbos[0].use(gl2ES2, this.fboTexs[0]);
                for (int j = 0; j < length2; ++j) {
                    this.deviceRenderer.ppOneEye(gl2ES2, eyeRenderOrder[j]);
                }
                this.fbos[0].unuse(gl2ES2);
            }
            else {
                for (final int n3 : eyeRenderOrder) {
                    this.fbos[n3].use(gl2ES2, this.fboTexs[n3]);
                    this.deviceRenderer.ppOneEye(gl2ES2, n3);
                    this.fbos[n3].unuse(gl2ES2);
                }
            }
            this.deviceRenderer.ppEnd(gl2ES2);
        }
        if (!glAutoDrawable.getAutoSwapBufferMode()) {
            glAutoDrawable.swapBuffers();
        }
        this.deviceRenderer.endFrame(gl2ES2);
    }
    
    @Override
    public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        if (!glAutoDrawable.getAutoSwapBufferMode()) {
            glAutoDrawable.getGL().getGL2ES2().glViewport(0, 0, n3, n4);
        }
    }
}
