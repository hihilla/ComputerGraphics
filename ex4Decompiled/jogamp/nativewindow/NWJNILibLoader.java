// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import java.security.AccessController;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.os.Platform;
import java.security.PrivilegedAction;
import com.jogamp.common.jvm.JNILibLoaderBase;

public class NWJNILibLoader extends JNILibLoaderBase
{
    public static boolean loadNativeWindow(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Platform.initSingleton();
                final String string = "nativewindow_" + s;
                if (TempJarCache.isInitialized() && null == TempJarCache.findLibrary(string)) {
                    JNILibLoaderBase.addNativeJarLibsJoglCfg(new Class[] { Debug.class });
                }
                return JNILibLoaderBase.loadLibrary(string, false, NWJNILibLoader.class.getClassLoader());
            }
        });
    }
}
