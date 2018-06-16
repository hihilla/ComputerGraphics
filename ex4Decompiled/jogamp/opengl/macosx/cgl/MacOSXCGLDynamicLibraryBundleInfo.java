// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import java.util.ArrayList;
import java.util.List;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;

public final class MacOSXCGLDynamicLibraryBundleInfo extends DesktopGLDynamicLibraryBundleInfo
{
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("/System/Library/Frameworks/OpenGL.framework/Libraries/libGL.dylib");
        list2.add("GL");
        list.add(list2);
        return (List<List<String>>)list;
    }
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        return null;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return 0L;
    }
}
