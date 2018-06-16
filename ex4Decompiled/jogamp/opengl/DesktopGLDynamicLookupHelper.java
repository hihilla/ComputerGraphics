// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.List;
import com.jogamp.common.os.DynamicLibraryBundle;
import java.util.ArrayList;
import com.jogamp.common.os.NativeLibrary;

public class DesktopGLDynamicLookupHelper extends GLDynamicLookupHelper
{
    NativeLibrary gluLib;
    
    public DesktopGLDynamicLookupHelper(final DesktopGLDynamicLibraryBundleInfo desktopGLDynamicLibraryBundleInfo) {
        super(desktopGLDynamicLibraryBundleInfo);
        this.gluLib = null;
    }
    
    public final DesktopGLDynamicLibraryBundleInfo getDesktopGLBundleInfo() {
        return (DesktopGLDynamicLibraryBundleInfo)this.getBundleInfo();
    }
    
    @Override
    public final synchronized boolean loadGLULibrary() {
        if (null == this.gluLib) {
            final ArrayList<String> list = new ArrayList<String>();
            list.add("/System/Library/Frameworks/OpenGL.framework/Libraries/libGLU.dylib");
            list.add("libGLU.so");
            list.add("GLU32");
            list.add("GLU");
            this.gluLib = DynamicLibraryBundle.loadFirstAvailable(list, null, true);
            if (null != this.gluLib) {
                this.nativeLibraries.add(this.gluLib);
            }
        }
        return null != this.gluLib;
    }
}
