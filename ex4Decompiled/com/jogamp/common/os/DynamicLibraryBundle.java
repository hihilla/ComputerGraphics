// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import com.jogamp.common.jvm.JNILibLoaderBase;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import com.jogamp.common.util.RunnableExecutor;
import java.util.HashSet;
import java.util.List;

public class DynamicLibraryBundle implements DynamicLookupHelper
{
    private final DynamicLibraryBundleInfo info;
    protected final List<NativeLibrary> nativeLibraries;
    private final DynamicLinker dynLinkGlobal;
    private final List<List<String>> toolLibNames;
    private final List<String> glueLibNames;
    private final boolean[] toolLibLoaded;
    private int toolLibLoadedNumber;
    private final boolean[] glueLibLoaded;
    private int glueLibLoadedNumber;
    private long toolGetProcAddressHandle;
    private boolean toolGetProcAddressComplete;
    private HashSet<String> toolGetProcAddressFuncNameSet;
    private final List<String> toolGetProcAddressFuncNameList;
    
    public static RunnableExecutor getDefaultRunnableExecutor() {
        return RunnableExecutor.currentThreadExecutor;
    }
    
    public DynamicLibraryBundle(final DynamicLibraryBundleInfo info) {
        if (null == info) {
            throw new RuntimeException("Null DynamicLibraryBundleInfo");
        }
        this.info = info;
        if (DynamicLibraryBundle.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - DynamicLibraryBundle.init start with: " + info.getClass().getName());
        }
        this.nativeLibraries = new ArrayList<NativeLibrary>();
        this.toolLibNames = info.getToolLibNames();
        this.glueLibNames = info.getGlueLibNames();
        this.toolLibLoaded = new boolean[this.toolLibNames.size()];
        if (DynamicLibraryBundle.DEBUG) {
            if (this.toolLibNames.size() == 0) {
                System.err.println("No Tool native library names given");
            }
            if (this.glueLibNames.size() == 0) {
                System.err.println("No Glue native library names given");
            }
        }
        for (int i = this.toolLibNames.size() - 1; i >= 0; --i) {
            this.toolLibLoaded[i] = false;
        }
        this.glueLibLoaded = new boolean[this.glueLibNames.size()];
        for (int j = this.glueLibNames.size() - 1; j >= 0; --j) {
            this.glueLibLoaded[j] = false;
        }
        final DynamicLinker[] array = { null };
        info.getLibLoaderExecutor().invoke(true, new Runnable() {
            @Override
            public void run() {
                array[0] = DynamicLibraryBundle.this.loadLibraries();
            }
        });
        this.dynLinkGlobal = array[0];
        this.toolGetProcAddressFuncNameList = info.getToolGetProcAddressFuncNameList();
        if (null != this.toolGetProcAddressFuncNameList) {
            this.toolGetProcAddressFuncNameSet = new HashSet<String>(this.toolGetProcAddressFuncNameList);
            this.toolGetProcAddressHandle = this.getToolGetProcAddressHandle();
            this.toolGetProcAddressComplete = (0L != this.toolGetProcAddressHandle);
        }
        else {
            this.toolGetProcAddressFuncNameSet = new HashSet<String>();
            this.toolGetProcAddressHandle = 0L;
            this.toolGetProcAddressComplete = true;
        }
        if (DynamicLibraryBundle.DEBUG) {
            System.err.println("DynamicLibraryBundle.init Summary: " + info.getClass().getName());
            System.err.println("     toolGetProcAddressFuncNameList: " + this.toolGetProcAddressFuncNameList + ", complete: " + this.toolGetProcAddressComplete + ", 0x" + Long.toHexString(this.toolGetProcAddressHandle));
            System.err.println("     Tool Lib Names : " + this.toolLibNames);
            System.err.println("     Tool Lib Loaded: " + this.getToolLibLoadedNumber() + "/" + this.getToolLibNumber() + " " + Arrays.toString(this.toolLibLoaded) + ", complete " + this.isToolLibComplete());
            System.err.println("     Glue Lib Names : " + this.glueLibNames);
            System.err.println("     Glue Lib Loaded: " + this.getGlueLibLoadedNumber() + "/" + this.getGlueLibNumber() + " " + Arrays.toString(this.glueLibLoaded) + ", complete " + this.isGlueLibComplete());
            System.err.println("     All Complete: " + this.isLibComplete());
            System.err.println("     LibLoaderExecutor: " + info.getLibLoaderExecutor().getClass().getName());
        }
    }
    
    public final void destroy() {
        if (DynamicLibraryBundle.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - DynamicLibraryBundle.destroy() START: " + this.info.getClass().getName());
        }
        this.toolGetProcAddressFuncNameSet = null;
        this.toolGetProcAddressHandle = 0L;
        this.toolGetProcAddressComplete = false;
        for (int i = 0; i < this.nativeLibraries.size(); ++i) {
            this.nativeLibraries.get(i).close();
        }
        this.nativeLibraries.clear();
        this.toolLibNames.clear();
        this.glueLibNames.clear();
        if (DynamicLibraryBundle.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - DynamicLibraryBundle.destroy() END: " + this.info.getClass().getName());
        }
    }
    
    public final boolean isLibComplete() {
        return this.isToolLibComplete() && this.isGlueLibComplete();
    }
    
    public final int getToolLibNumber() {
        return this.toolLibNames.size();
    }
    
    public final int getToolLibLoadedNumber() {
        return this.toolLibLoadedNumber;
    }
    
    public final boolean isToolLibComplete() {
        final int toolLibNumber = this.getToolLibNumber();
        return this.toolGetProcAddressComplete && (toolLibNumber == 0 || null != this.dynLinkGlobal) && toolLibNumber == this.getToolLibLoadedNumber();
    }
    
    public final boolean isToolLibLoaded() {
        return 0 < this.toolLibLoadedNumber;
    }
    
    public final boolean isToolLibLoaded(final int n) {
        return 0 <= n && n < this.toolLibLoaded.length && this.toolLibLoaded[n];
    }
    
    public final int getGlueLibNumber() {
        return this.glueLibNames.size();
    }
    
    public final int getGlueLibLoadedNumber() {
        return this.glueLibLoadedNumber;
    }
    
    public final boolean isGlueLibComplete() {
        return 0 == this.getGlueLibNumber() || this.isGlueLibLoaded(this.getGlueLibNumber() - 1);
    }
    
    public final boolean isGlueLibLoaded(final int n) {
        return 0 <= n && n < this.glueLibLoaded.length && this.glueLibLoaded[n];
    }
    
    public final DynamicLibraryBundleInfo getBundleInfo() {
        return this.info;
    }
    
    protected final long getToolGetProcAddressHandle() throws SecurityException {
        if (!this.isToolLibLoaded()) {
            return 0L;
        }
        long dynamicLookupFunctionOnLibs = 0L;
        for (int i = 0; i < this.toolGetProcAddressFuncNameList.size(); ++i) {
            final String s = this.toolGetProcAddressFuncNameList.get(i);
            dynamicLookupFunctionOnLibs = this.dynamicLookupFunctionOnLibs(s);
            if (DynamicLibraryBundle.DEBUG) {
                System.err.println("getToolGetProcAddressHandle: " + s + " -> 0x" + Long.toHexString(dynamicLookupFunctionOnLibs));
            }
        }
        return dynamicLookupFunctionOnLibs;
    }
    
    protected static final NativeLibrary loadFirstAvailable(final List<String> list, final ClassLoader classLoader, final boolean b) throws SecurityException {
        for (int i = 0; i < list.size(); ++i) {
            final NativeLibrary open = NativeLibrary.open(list.get(i), classLoader, b);
            if (open != null) {
                return open;
            }
        }
        return null;
    }
    
    final DynamicLinker loadLibraries() throws SecurityException {
        this.toolLibLoadedNumber = 0;
        final ClassLoader classLoader = this.info.getClass().getClassLoader();
        DynamicLinker dynamicLinker = null;
        for (int i = 0; i < this.toolLibNames.size(); ++i) {
            final List<String> list = this.toolLibNames.get(i);
            if (null != list && list.size() > 0) {
                final NativeLibrary loadFirstAvailable = loadFirstAvailable(list, classLoader, this.info.shallLinkGlobal());
                if (null == loadFirstAvailable) {
                    if (DynamicLibraryBundle.DEBUG) {
                        System.err.println("Unable to load any Tool library of: " + list);
                    }
                }
                else {
                    if (null == dynamicLinker) {
                        dynamicLinker = loadFirstAvailable.getDynamicLinker();
                    }
                    this.nativeLibraries.add(loadFirstAvailable);
                    this.toolLibLoaded[i] = true;
                    ++this.toolLibLoadedNumber;
                    if (DynamicLibraryBundle.DEBUG) {
                        System.err.println("Loaded Tool library: " + loadFirstAvailable);
                    }
                }
            }
        }
        if (this.toolLibNames.size() > 0 && !this.isToolLibLoaded()) {
            if (DynamicLibraryBundle.DEBUG) {
                System.err.println("No Tool libraries loaded");
            }
            return dynamicLinker;
        }
        this.glueLibLoadedNumber = 0;
        for (int j = 0; j < this.glueLibNames.size(); ++j) {
            final String s = this.glueLibNames.get(j);
            boolean loadLibrary;
            try {
                loadLibrary = GlueJNILibLoader.loadLibrary(s, true, classLoader);
                if (DynamicLibraryBundle.DEBUG && !loadLibrary) {
                    System.err.println("Info: Could not load JNI/Glue library: " + s);
                }
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                loadLibrary = false;
                if (DynamicLibraryBundle.DEBUG) {
                    System.err.println("Unable to load JNI/Glue library: " + s);
                    unsatisfiedLinkError.printStackTrace();
                }
            }
            this.glueLibLoaded[j] = loadLibrary;
            if (loadLibrary) {
                ++this.glueLibLoadedNumber;
            }
        }
        return dynamicLinker;
    }
    
    private final long dynamicLookupFunctionOnLibs(final String s) throws SecurityException {
        if (!this.isToolLibLoaded() || null == s) {
            if (DynamicLibraryBundle.DEBUG_LOOKUP && !this.isToolLibLoaded()) {
                System.err.println("Lookup-Native: <" + s + "> ** FAILED ** Tool native library not loaded");
            }
            return 0L;
        }
        long n = 0L;
        NativeLibrary nativeLibrary = null;
        if (this.info.shallLookupGlobal()) {
            n = this.dynLinkGlobal.lookupSymbolGlobal(s);
        }
        for (int n2 = 0; 0L == n && n2 < this.nativeLibraries.size(); n = nativeLibrary.dynamicLookupFunction(s), ++n2) {
            nativeLibrary = this.nativeLibraries.get(n2);
        }
        if (DynamicLibraryBundle.DEBUG_LOOKUP) {
            final String s2 = (null == nativeLibrary) ? "GLOBAL" : nativeLibrary.toString();
            if (0L != n) {
                System.err.println("Lookup-Native: <" + s + "> 0x" + Long.toHexString(n) + " in lib " + s2);
            }
            else {
                System.err.println("Lookup-Native: <" + s + "> ** FAILED ** in libs " + this.nativeLibraries);
            }
        }
        return n;
    }
    
    private final long toolDynamicLookupFunction(final String s) {
        if (0L != this.toolGetProcAddressHandle) {
            final long toolGetProcAddress = this.info.toolGetProcAddress(this.toolGetProcAddressHandle, s);
            if (DynamicLibraryBundle.DEBUG_LOOKUP && 0L != toolGetProcAddress) {
                System.err.println("Lookup-Tool: <" + s + "> 0x" + Long.toHexString(toolGetProcAddress) + ", via tool 0x" + Long.toHexString(this.toolGetProcAddressHandle));
            }
            return toolGetProcAddress;
        }
        return 0L;
    }
    
    @Override
    public final void claimAllLinkPermission() throws SecurityException {
        for (int i = 0; i < this.nativeLibraries.size(); ++i) {
            this.nativeLibraries.get(i).claimAllLinkPermission();
        }
    }
    
    @Override
    public final void releaseAllLinkPermission() throws SecurityException {
        for (int i = 0; i < this.nativeLibraries.size(); ++i) {
            this.nativeLibraries.get(i).releaseAllLinkPermission();
        }
    }
    
    @Override
    public final long dynamicLookupFunction(final String s) throws SecurityException {
        if (!this.isToolLibLoaded() || null == s) {
            if (DynamicLibraryBundle.DEBUG_LOOKUP && !this.isToolLibLoaded()) {
                System.err.println("Lookup: <" + s + "> ** FAILED ** Tool native library not loaded");
            }
            return 0L;
        }
        if (this.toolGetProcAddressFuncNameSet.contains(s)) {
            return this.toolGetProcAddressHandle;
        }
        long n = 0L;
        final boolean useToolGetProcAdressFirst = this.info.useToolGetProcAdressFirst(s);
        if (useToolGetProcAdressFirst) {
            n = this.toolDynamicLookupFunction(s);
        }
        if (0L == n) {
            n = this.dynamicLookupFunctionOnLibs(s);
        }
        if (0L == n && !useToolGetProcAdressFirst) {
            n = this.toolDynamicLookupFunction(s);
        }
        return n;
    }
    
    @Override
    public final boolean isFunctionAvailable(final String s) throws SecurityException {
        return 0L != this.dynamicLookupFunction(s);
    }
    
    static final class GlueJNILibLoader extends JNILibLoaderBase
    {
        protected static synchronized boolean loadLibrary(final String s, final boolean b, final ClassLoader classLoader) {
            return JNILibLoaderBase.loadLibrary(s, b, classLoader);
        }
    }
}
