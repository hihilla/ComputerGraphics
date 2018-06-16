// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.av;

import com.jogamp.common.util.ReflectionUtil;
import jogamp.opengl.util.av.NullGLMediaPlayer;

public class GLMediaPlayerFactory
{
    private static final String AndroidGLMediaPlayerAPI14ClazzName = "jogamp.opengl.android.av.AndroidGLMediaPlayerAPI14";
    private static final String FFMPEGMediaPlayerClazzName = "jogamp.opengl.util.av.impl.FFMPEGMediaPlayer";
    private static final String OMXGLMediaPlayerClazzName = "jogamp.opengl.util.av.impl.OMXGLMediaPlayer";
    private static final String isAvailableMethodName = "isAvailable";
    
    public static GLMediaPlayer createDefault() {
        final ClassLoader classLoader = GLMediaPlayerFactory.class.getClassLoader();
        GLMediaPlayer glMediaPlayer = create(classLoader, "jogamp.opengl.util.av.impl.OMXGLMediaPlayer");
        if (null == glMediaPlayer) {
            glMediaPlayer = create(classLoader, "jogamp.opengl.android.av.AndroidGLMediaPlayerAPI14");
        }
        if (null == glMediaPlayer) {
            glMediaPlayer = create(classLoader, "jogamp.opengl.util.av.impl.FFMPEGMediaPlayer");
        }
        if (null == glMediaPlayer) {
            glMediaPlayer = createNull();
        }
        return glMediaPlayer;
    }
    
    public static GLMediaPlayer createNull() {
        return new NullGLMediaPlayer();
    }
    
    public static GLMediaPlayer create(final ClassLoader classLoader, final String s) {
        try {
            if (ReflectionUtil.callStaticMethod(s, "isAvailable", null, null, classLoader)) {
                return (GLMediaPlayer)ReflectionUtil.createInstance(s, classLoader);
            }
        }
        catch (Throwable t) {
            if (GLMediaPlayer.DEBUG) {
                System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        return null;
    }
}
