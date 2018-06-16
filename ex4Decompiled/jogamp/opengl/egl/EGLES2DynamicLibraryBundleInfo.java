// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import java.util.ArrayList;
import java.util.List;

public final class EGLES2DynamicLibraryBundleInfo extends EGLDynamicLibraryBundleInfo
{
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("libGLESv3.so.3");
        list2.add("libGLESv3.so");
        list2.add("GLESv3");
        list2.add("GLES30");
        list2.add("libGLESv3");
        list2.add("libGLES30");
        list2.add("libGLESv2.so.2");
        list2.add("libGLESv2.so");
        list2.add("GLESv2");
        list2.add("GLES20");
        list2.add("GLESv2_CM");
        list2.add("libGLESv2");
        list2.add("libGLESv2_CM");
        list2.add("libGLES20");
        list.add(list2);
        list.add(this.getEGLLibNamesList());
        return (List<List<String>>)list;
    }
}
