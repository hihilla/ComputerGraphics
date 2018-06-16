// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import java.security.AccessController;
import jogamp.nativewindow.Debug;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.os.Platform;
import java.security.PrivilegedAction;
import com.jogamp.common.jvm.JNILibLoaderBase;

public class NEWTJNILibLoader extends JNILibLoaderBase
{
    public static boolean loadNEWT() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Platform.initSingleton();
                if (TempJarCache.isInitialized() && null == TempJarCache.findLibrary("newt")) {
                    JNILibLoaderBase.addNativeJarLibsJoglCfg(new Class[] { Debug.class, jogamp.newt.Debug.class });
                }
                return JNILibLoaderBase.loadLibrary("newt", false, NEWTJNILibLoader.class.getClassLoader());
            }
        });
    }
}
