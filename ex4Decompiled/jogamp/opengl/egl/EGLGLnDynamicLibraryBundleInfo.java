// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.common.os.Platform;
import java.util.ArrayList;
import java.util.List;

public final class EGLGLnDynamicLibraryBundleInfo extends EGLDynamicLibraryBundleInfo
{
    private static final List<String> glueLibNames;
    
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        if (Platform.OSType.MACOS == Platform.getOSType()) {
            list2.add("/System/Library/Frameworks/OpenGL.framework/Libraries/libGL.dylib");
            list2.add("GL");
        }
        else if (Platform.OSType.WINDOWS == Platform.getOSType()) {
            list2.add("OpenGL32");
        }
        else {
            list2.add("libGL.so.1");
            list2.add("libGL.so");
            list2.add("GL");
        }
        list.add(list2);
        list.add(this.getEGLLibNamesList());
        return (List<List<String>>)list;
    }
    
    static {
        (glueLibNames = new ArrayList<String>()).add("jogl_desktop");
    }
}
