// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.glu.gl2;

import com.jogamp.common.util.SecurityUtil;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Field;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.ProcAddressTable;

final class GLUgl2ProcAddressTable extends ProcAddressTable
{
    long _addressof_gluBuild1DMipmapLevels;
    long _addressof_gluBuild1DMipmaps;
    long _addressof_gluBuild2DMipmapLevels;
    long _addressof_gluBuild2DMipmaps;
    long _addressof_gluBuild3DMipmapLevels;
    long _addressof_gluBuild3DMipmaps;
    long _addressof_gluScaleImage;
    
    public GLUgl2ProcAddressTable() {
    }
    
    public GLUgl2ProcAddressTable(final FunctionAddressResolver functionAddressResolver) {
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
                        final Field declaredField = GLUgl2ProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
                        final Field declaredField = GLUgl2ProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
