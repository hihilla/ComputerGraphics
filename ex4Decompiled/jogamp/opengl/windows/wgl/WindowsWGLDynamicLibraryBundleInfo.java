// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import java.util.ArrayList;
import java.util.List;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;

public final class WindowsWGLDynamicLibraryBundleInfo extends DesktopGLDynamicLibraryBundleInfo
{
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("OpenGL32");
        list.add(list2);
        return (List<List<String>>)list;
    }
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("wglGetProcAddress");
        return list;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return WGL.wglGetProcAddress(n, s);
    }
}
