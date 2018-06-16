// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.ArrayList;
import java.util.List;

public abstract class DesktopGLDynamicLibraryBundleInfo extends GLDynamicLibraryBundleInfo
{
    private static final List<String> glueLibNames;
    
    @Override
    public final List<String> getGlueLibNames() {
        return DesktopGLDynamicLibraryBundleInfo.glueLibNames;
    }
    
    @Override
    public final boolean useToolGetProcAdressFirst(final String s) {
        return true;
    }
    
    static {
        (glueLibNames = new ArrayList<String>()).add("jogl_desktop");
    }
}
