// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

import com.jogamp.common.os.AndroidVersion;
import java.io.File;
import com.jogamp.common.util.ReflectionUtil;
import java.lang.reflect.Method;

public class AndroidUtils
{
    private static final Method androidGetPackageInfoVersionNameMethod;
    private static final Method androidGetPackageInfoVersionCodeMethod;
    private static final Method androidGetTempRootMethod;
    
    public static final int getPackageInfoVersionCode(final String s) {
        if (null != AndroidUtils.androidGetPackageInfoVersionCodeMethod) {
            return (int)ReflectionUtil.callMethod(null, AndroidUtils.androidGetPackageInfoVersionCodeMethod, s);
        }
        return -1;
    }
    
    public static final String getPackageInfoVersionName(final String s) {
        if (null != AndroidUtils.androidGetPackageInfoVersionNameMethod) {
            return (String)ReflectionUtil.callMethod(null, AndroidUtils.androidGetPackageInfoVersionNameMethod, s);
        }
        return null;
    }
    
    public static File getTempRoot() throws RuntimeException {
        if (null != AndroidUtils.androidGetTempRootMethod) {
            return (File)ReflectionUtil.callMethod(null, AndroidUtils.androidGetTempRootMethod, new Object[0]);
        }
        return null;
    }
    
    static {
        if (AndroidVersion.isAvailable) {
            final Class<?> class1 = ReflectionUtil.getClass("jogamp.common.os.android.AndroidUtilsImpl", true, AndroidUtils.class.getClassLoader());
            androidGetPackageInfoVersionCodeMethod = ReflectionUtil.getMethod(class1, "getPackageInfoVersionCode", String.class);
            androidGetPackageInfoVersionNameMethod = ReflectionUtil.getMethod(class1, "getPackageInfoVersionName", String.class);
            androidGetTempRootMethod = ReflectionUtil.getMethod(class1, "getTempRoot", (Class<?>[])new Class[0]);
        }
        else {
            androidGetPackageInfoVersionCodeMethod = null;
            androidGetPackageInfoVersionNameMethod = null;
            androidGetTempRootMethod = null;
        }
    }
}
