// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import java.util.ArrayList;
import java.util.List;

public final class EGLES1DynamicLibraryBundleInfo extends EGLDynamicLibraryBundleInfo
{
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("libGLESv1_CM.so.2");
        list2.add("libGLESv1_CM.so");
        list2.add("GLESv1_CM");
        list2.add("GLES_CM");
        list2.add("GLES_CL");
        list2.add("libGLESv1_CM");
        list2.add("libGLES_CM");
        list2.add("libGLES_CL");
        list.add(list2);
        list.add(this.getEGLLibNamesList());
        return (List<List<String>>)list;
    }
}
