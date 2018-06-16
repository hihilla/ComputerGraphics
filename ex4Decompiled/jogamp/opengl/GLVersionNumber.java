// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.util.VersionNumber;
import java.util.regex.Pattern;
import com.jogamp.common.util.VersionNumberString;

public class GLVersionNumber extends VersionNumberString
{
    private final boolean valid;
    private static volatile Pattern _Pattern;
    
    private GLVersionNumber(final int[] array, final int n, final short n2, final String s, final boolean valid) {
        super(array[0], array[1], array[2], n, n2, s);
        this.valid = valid;
    }
    
    private static Pattern getUnderscorePattern() {
        if (null == GLVersionNumber._Pattern) {
            synchronized (VersionNumber.class) {
                if (null == GLVersionNumber._Pattern) {
                    GLVersionNumber._Pattern = VersionNumber.getVersionNumberPattern("_");
                }
            }
        }
        return GLVersionNumber._Pattern;
    }
    
    public static final GLVersionNumber create(final String s) {
        final int[] array = { 0, 0, 0 };
        int endOfStringMatch = 0;
        short n = 0;
        boolean b = false;
        if (s != null && s.length() > 0) {
            try {
                Pattern pattern;
                if (s.startsWith("GL_VERSION_")) {
                    pattern = getUnderscorePattern();
                }
                else {
                    pattern = VersionNumber.getDefaultVersionNumberPattern();
                }
                final VersionNumberString versionNumberString = new VersionNumberString(s, pattern);
                endOfStringMatch = versionNumberString.endOfStringMatch();
                array[0] = versionNumberString.getMajor();
                array[1] = versionNumberString.getMinor();
                n = (short)((versionNumberString.hasMajor() ? 1 : 0) | (versionNumberString.hasMinor() ? 2 : 0));
                b = (versionNumberString.hasMajor() && versionNumberString.hasMinor());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Info: ExtensionAvailabilityCache: FunctionAvailabilityCache.Version.<init>: " + ex);
                array[array[0] = 1] = 0;
            }
        }
        return new GLVersionNumber(array, endOfStringMatch, n, s, b);
    }
    
    public final boolean isValid() {
        return this.valid;
    }
    
    public static final VersionNumberString createVendorVersion(final String s) {
        if (s == null || s.length() <= 0) {
            return null;
        }
        int endOfStringMatch;
        for (String s2 = s.substring(create(s).endOfStringMatch()).trim(); s2.length() > 0; s2 = s2.substring(endOfStringMatch).trim()) {
            final VersionNumberString versionNumberString = new VersionNumberString(s2, VersionNumber.getDefaultVersionNumberPattern());
            endOfStringMatch = versionNumberString.endOfStringMatch();
            if (0 >= endOfStringMatch) {
                break;
            }
            if (versionNumberString.hasMajor() && versionNumberString.hasMinor()) {
                return versionNumberString;
            }
        }
        return VersionNumberString.zeroVersion;
    }
    
    static {
        GLVersionNumber._Pattern = null;
    }
}
