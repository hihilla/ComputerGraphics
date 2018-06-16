// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.common.util.SecurityUtil;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Field;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.ProcAddressTable;

public final class GLXProcAddressTable extends ProcAddressTable
{
    long _addressof_glXGetVisualFromFBConfig;
    long _addressof_glXChooseFBConfig;
    long _addressof_glXGetFBConfigs;
    long _addressof_glXChooseVisual;
    long _addressof_glXCreateContext;
    long _addressof_glXDestroyContext;
    long _addressof_glXMakeCurrent;
    long _addressof_glXCopyContext;
    long _addressof_glXSwapBuffers;
    long _addressof_glXCreateGLXPixmap;
    long _addressof_glXDestroyGLXPixmap;
    long _addressof_glXQueryExtension;
    long _addressof_glXQueryVersion;
    long _addressof_glXIsDirect;
    long _addressof_glXGetConfig;
    long _addressof_glXGetCurrentContext;
    long _addressof_glXGetCurrentDrawable;
    long _addressof_glXWaitGL;
    long _addressof_glXWaitX;
    long _addressof_glXUseXFont;
    long _addressof_glXQueryExtensionsString;
    long _addressof_glXQueryServerString;
    long _addressof_glXGetClientString;
    long _addressof_glXGetCurrentDisplay;
    long _addressof_glXGetProcAddress;
    long _addressof_glXGetProcAddressARB;
    long _addressof_glXGetFBConfigAttrib;
    long _addressof_glXCreateWindow;
    long _addressof_glXDestroyWindow;
    long _addressof_glXCreatePixmap;
    long _addressof_glXDestroyPixmap;
    long _addressof_glXCreatePbuffer;
    long _addressof_glXDestroyPbuffer;
    long _addressof_glXQueryDrawable;
    long _addressof_glXCreateNewContext;
    long _addressof_glXMakeContextCurrent;
    long _addressof_glXGetCurrentReadDrawable;
    long _addressof_glXQueryContext;
    long _addressof_glXSelectEvent;
    long _addressof_glXGetSelectedEvent;
    long _addressof_glXSwapIntervalEXT;
    
    public GLXProcAddressTable() {
    }
    
    public GLXProcAddressTable(final FunctionAddressResolver functionAddressResolver) {
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
                        final Field declaredField = GLXProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
                        final Field declaredField = GLXProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
