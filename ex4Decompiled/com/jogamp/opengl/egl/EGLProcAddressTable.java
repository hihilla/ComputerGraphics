// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.egl;

import com.jogamp.common.util.SecurityUtil;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Field;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.ProcAddressTable;

final class EGLProcAddressTable extends ProcAddressTable
{
    long _addressof_eglChooseConfig;
    long _addressof_eglCopyBuffers;
    long _addressof_eglCreateContext;
    long _addressof_eglCreatePbufferSurface;
    long _addressof_eglCreatePixmapSurface;
    long _addressof_eglCreateWindowSurface;
    long _addressof_eglDestroyContext;
    long _addressof_eglDestroySurface;
    long _addressof_eglGetConfigAttrib;
    long _addressof_eglGetConfigs;
    long _addressof_eglGetCurrentDisplay;
    long _addressof_eglGetCurrentSurface;
    long _addressof_eglGetDisplay;
    long _addressof_eglGetError;
    long _addressof_eglInitialize;
    long _addressof_eglMakeCurrent;
    long _addressof_eglQueryContext;
    long _addressof_eglQueryString;
    long _addressof_eglQuerySurface;
    long _addressof_eglSwapBuffers;
    long _addressof_eglTerminate;
    long _addressof_eglWaitGL;
    long _addressof_eglWaitNative;
    long _addressof_eglBindTexImage;
    long _addressof_eglReleaseTexImage;
    long _addressof_eglSurfaceAttrib;
    long _addressof_eglSwapInterval;
    long _addressof_eglBindAPI;
    long _addressof_eglQueryAPI;
    long _addressof_eglCreatePbufferFromClientBuffer;
    long _addressof_eglReleaseThread;
    long _addressof_eglWaitClient;
    long _addressof_eglGetCurrentContext;
    long _addressof_eglCreateSync;
    long _addressof_eglDestroySync;
    long _addressof_eglClientWaitSync;
    long _addressof_eglGetSyncAttrib;
    long _addressof_eglCreateImage;
    long _addressof_eglDestroyImage;
    long _addressof_eglGetPlatformDisplay;
    long _addressof_eglCreatePlatformWindowSurface;
    long _addressof_eglCreatePlatformPixmapSurface;
    long _addressof_eglWaitSync;
    
    public EGLProcAddressTable() {
    }
    
    public EGLProcAddressTable(final FunctionAddressResolver functionAddressResolver) {
        super(functionAddressResolver);
    }
    
    @Override
    protected boolean isFunctionAvailableImpl(final String s) throws IllegalArgumentException {
        final String normalizeVEN = GLNameResolver.normalizeVEN(GLNameResolver.normalizeARB(s, true), true);
        final Field field = AccessController.doPrivileged((PrivilegedAction<Field>)new PrivilegedAction<Field>() {
            final /* synthetic */ int val$funcNamePermNum = GLNameResolver.getFuncNamePermutationNumber(normalizeVEN);
            final /* synthetic */ String val$addressFieldNameBase = "_addressof_" + normalizeVEN;
            
            @Override
            public final Field run() {
                int i = 0;
                while (i < this.val$funcNamePermNum) {
                    final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(this.val$addressFieldNameBase, i);
                    try {
                        final Field declaredField = EGLProcAddressTable.class.getDeclaredField(funcNamePermutation);
                        declaredField.setAccessible(true);
                        return declaredField;
                    }
                    catch (NoSuchFieldException ex) {
                        ++i;
                        continue;
                    }
                    break;
                }
                return null;
            }
        });
        if (null == field) {
            throw new RuntimeException("WARNING: Address field query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or address field is not a known " + "function");
        }
        try {
            return 0L != field.getLong(this);
        }
        catch (Exception ex) {
            throw new RuntimeException("WARNING: Address query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or is not a known " + "function", ex);
        }
    }
    
    @Override
    public long getAddressFor(final String s) throws SecurityException, IllegalArgumentException {
        SecurityUtil.checkAllLinkPermission();
        final String normalizeVEN = GLNameResolver.normalizeVEN(GLNameResolver.normalizeARB(s, true), true);
        final Field field = AccessController.doPrivileged((PrivilegedAction<Field>)new PrivilegedAction<Field>() {
            final /* synthetic */ int val$funcNamePermNum = GLNameResolver.getFuncNamePermutationNumber(normalizeVEN);
            final /* synthetic */ String val$addressFieldNameBase = "_addressof_" + normalizeVEN;
            
            @Override
            public final Field run() {
                int i = 0;
                while (i < this.val$funcNamePermNum) {
                    final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(this.val$addressFieldNameBase, i);
                    try {
                        final Field declaredField = EGLProcAddressTable.class.getDeclaredField(funcNamePermutation);
                        declaredField.setAccessible(true);
                        return declaredField;
                    }
                    catch (NoSuchFieldException ex) {
                        ++i;
                        continue;
                    }
                    break;
                }
                return null;
            }
        });
        if (null == field) {
            throw new RuntimeException("WARNING: Address field query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or address field is not a known " + "function");
        }
        try {
            return field.getLong(this);
        }
        catch (Exception ex) {
            throw new RuntimeException("WARNING: Address query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or is not a known " + "function", ex);
        }
    }
}
