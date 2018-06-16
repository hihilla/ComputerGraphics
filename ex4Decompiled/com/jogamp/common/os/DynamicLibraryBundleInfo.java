// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import com.jogamp.common.util.RunnableExecutor;
import java.util.List;

public interface DynamicLibraryBundleInfo
{
    public static final boolean DEBUG = DynamicLibraryBundle.DEBUG;
    
    List<List<String>> getToolLibNames();
    
    List<String> getGlueLibNames();
    
    List<String> getToolGetProcAddressFuncNameList();
    
    long toolGetProcAddress(final long p0, final String p1);
    
    boolean useToolGetProcAdressFirst(final String p0);
    
    boolean shallLinkGlobal();
    
    boolean shallLookupGlobal();
    
    RunnableExecutor getLibLoaderExecutor();
}
