// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.os.DynamicLibraryBundleInfo;
import com.jogamp.common.os.DynamicLibraryBundle;

public class GLDynamicLookupHelper extends DynamicLibraryBundle
{
    public GLDynamicLookupHelper(final GLDynamicLibraryBundleInfo glDynamicLibraryBundleInfo) {
        super(glDynamicLibraryBundleInfo);
    }
    
    public final GLDynamicLibraryBundleInfo getGLBundleInfo() {
        return (GLDynamicLibraryBundleInfo)this.getBundleInfo();
    }
    
    public boolean loadGLULibrary() {
        return false;
    }
}
