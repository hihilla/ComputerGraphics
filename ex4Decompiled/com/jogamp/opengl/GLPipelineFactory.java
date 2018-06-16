// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.util.StringTokenizer;
import java.lang.reflect.Constructor;
import com.jogamp.common.util.ReflectionUtil;

public class GLPipelineFactory
{
    public static final boolean DEBUG;
    
    public static final GL create(final String s, final Class<?> clazz, final GL gl, final Object[] array) {
        Class<?> clazz2 = gl.getClass();
        Class<?> upstreamClazz = null;
        Class<?> clazz3 = null;
        if (GLPipelineFactory.DEBUG) {
            System.out.println("GLPipelineFactory: Start " + clazz2.getName() + ", req. Interface: " + clazz + " -> " + s);
        }
        do {
            final Class<?>[] interfaces = clazz2.getInterfaces();
            for (int n = interfaces.length - 1; null == upstreamClazz && n >= 0; --n) {
                if (GLPipelineFactory.DEBUG) {
                    System.out.println("GLPipelineFactory: Try " + clazz2.getName() + " Interface[" + n + "]: " + interfaces[n].getName());
                }
                if (clazz != null && !clazz.getName().equals(interfaces[n].getName())) {
                    if (GLPipelineFactory.DEBUG) {
                        System.out.println("GLPipelineFactory: requested Interface " + clazz + " is _not_ " + interfaces[n].getName());
                    }
                }
                else if (!interfaces[n].isInstance(gl)) {
                    if (GLPipelineFactory.DEBUG) {
                        System.out.println("GLPipelineFactory: " + gl.getClass().getName() + " is _not_ instance of " + interfaces[n].getName());
                    }
                }
                else {
                    if (GLPipelineFactory.DEBUG) {
                        System.out.println("GLPipelineFactory: " + gl.getClass().getName() + " _is_ instance of " + interfaces[n].getName());
                    }
                    upstreamClazz = getUpstreamClazz(interfaces[n], s);
                    if (null != upstreamClazz) {
                        clazz3 = interfaces[n];
                    }
                }
            }
            if (null == upstreamClazz) {
                clazz2 = clazz2.getSuperclass();
            }
        } while (null != clazz2 && null == upstreamClazz);
        if (null == upstreamClazz) {
            throw new GLException("No pipeline (" + s + "*) available for :" + gl.getClass().getName());
        }
        if (GLPipelineFactory.DEBUG) {
            System.out.println("GLPipelineFactory: Got : " + upstreamClazz.getName() + ", base interface: " + clazz3.getName());
        }
        final Class[] array2 = new Class[1 + ((null == array) ? 0 : array.length)];
        int n2 = 0;
        array2[n2++] = clazz3;
        for (int n3 = 0; null != array && n3 < array.length; ++n3) {
            array2[n2++] = array[n3].getClass();
        }
        final Constructor<?> constructor = ReflectionUtil.getConstructor(upstreamClazz, (Class<?>[])array2);
        Object instance = null;
        try {
            final Object[] array3 = new Object[1 + ((null == array) ? 0 : array.length)];
            int n4 = 0;
            array3[n4++] = gl;
            for (int n5 = 0; null != array && n5 < array.length; ++n5) {
                array3[n4++] = array[n5];
            }
            instance = constructor.newInstance(array3);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        if (null == instance) {
            throw new GLException("Error: Couldn't create instance of pipeline: " + upstreamClazz.getName() + " ( " + getArgsClassNameList(clazz2, array) + " )");
        }
        if (!(instance instanceof GL)) {
            throw new GLException("Error: " + upstreamClazz.getName() + " not an instance of GL");
        }
        return (GL)instance;
    }
    
    private static final String getArgsClassNameList(final Class<?> clazz, final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append(clazz.getName());
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                sb.append(", ");
                sb.append(array[i].getClass().getName());
            }
        }
        return sb.toString();
    }
    
    private static final Class<?> getUpstreamClazz(final Class<?> clazz, final String s) {
        final String name = clazz.getName();
        final StringTokenizer stringTokenizer = new StringTokenizer(name, ".");
        String nextToken = name;
        while (stringTokenizer.hasMoreTokens()) {
            nextToken = stringTokenizer.nextToken();
        }
        final String string = s + nextToken;
        Class<?> forName = null;
        try {
            forName = Class.forName(string, true, GLPipelineFactory.class.getClassLoader());
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return forName;
    }
    
    static {
        DEBUG = Debug.debug("GLPipelineFactory");
    }
}
