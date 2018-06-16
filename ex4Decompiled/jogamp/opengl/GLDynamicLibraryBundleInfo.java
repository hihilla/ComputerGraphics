// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.os.DynamicLibraryBundle;
import com.jogamp.common.util.RunnableExecutor;
import com.jogamp.common.os.DynamicLibraryBundleInfo;

public abstract class GLDynamicLibraryBundleInfo implements DynamicLibraryBundleInfo
{
    @Override
    public final boolean shallLinkGlobal() {
        return true;
    }
    
    @Override
    public boolean shallLookupGlobal() {
        return false;
    }
    
    @Override
    public final RunnableExecutor getLibLoaderExecutor() {
        return DynamicLibraryBundle.getDefaultRunnableExecutor();
    }
}
