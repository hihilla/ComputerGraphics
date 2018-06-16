// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import java.util.ArrayList;
import java.util.List;
import jogamp.opengl.GLDynamicLibraryBundleInfo;

public final class DesktopES2DynamicLibraryBundleInfo extends GLDynamicLibraryBundleInfo
{
    static final List<String> glueLibNames;
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("eglGetProcAddress");
        return list;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return EGLContext.eglGetProcAddress(n, s);
    }
    
    @Override
    public final boolean useToolGetProcAdressFirst(final String s) {
        return true;
    }
    
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("libGL.so.1");
        list2.add("libGL.so");
        list2.add("OpenGL32");
        list2.add("/System/Library/Frameworks/OpenGL.framework/Libraries/libGL.dylib");
        list2.add("GL");
        list.add(list2);
        return (List<List<String>>)list;
    }
    
    @Override
    public final List<String> getGlueLibNames() {
        return DesktopES2DynamicLibraryBundleInfo.glueLibNames;
    }
    
    static {
        (glueLibNames = new ArrayList<String>()).add("jogl_mobile");
    }
}
