// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.util.ArrayList;
import java.util.List;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;

public final class X11GLXDynamicLibraryBundleInfo extends DesktopGLDynamicLibraryBundleInfo
{
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("libGL.so.1");
        list2.add("libGL.so");
        list2.add("GL");
        list.add(list2);
        return (List<List<String>>)list;
    }
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("glXGetProcAddressARB");
        list.add("glXGetProcAddress");
        return list;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return GLX.glXGetProcAddress(n, s);
    }
}
