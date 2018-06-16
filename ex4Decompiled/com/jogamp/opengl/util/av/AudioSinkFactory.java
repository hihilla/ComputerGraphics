// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.av;

import com.jogamp.common.util.ReflectionUtil;
import jogamp.opengl.util.av.NullAudioSink;

public class AudioSinkFactory
{
    private static final String ALAudioSinkClazzName = "jogamp.opengl.openal.av.ALAudioSink";
    private static final String JavaAudioSinkClazzName = "jogamp.opengl.util.av.JavaSoundAudioSink";
    
    public static AudioSink createDefault() {
        final ClassLoader classLoader = GLMediaPlayerFactory.class.getClassLoader();
        AudioSink audioSink = create(classLoader, "jogamp.opengl.openal.av.ALAudioSink");
        if (null == audioSink) {
            audioSink = create(classLoader, "jogamp.opengl.util.av.JavaSoundAudioSink");
        }
        if (null == audioSink) {
            audioSink = createNull();
        }
        return audioSink;
    }
    
    public static AudioSink createNull() {
        return new NullAudioSink();
    }
    
    public static AudioSink create(final ClassLoader classLoader, final String s) {
        if (ReflectionUtil.isClassAvailable(s, classLoader)) {
            try {
                final AudioSink audioSink = (AudioSink)ReflectionUtil.createInstance(s, classLoader);
                if (audioSink.isInitialized()) {
                    return audioSink;
                }
            }
            catch (Throwable t) {
                if (AudioSink.DEBUG) {
                    System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                    t.printStackTrace();
                }
            }
        }
        return null;
    }
}
