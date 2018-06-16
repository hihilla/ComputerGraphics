// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font;

import java.net.URLConnection;
import com.jogamp.graph.font.FontFactory;
import com.jogamp.common.util.IOUtil;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.common.util.JarUtil;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.os.Platform;
import java.io.IOException;
import com.jogamp.graph.font.Font;
import com.jogamp.common.net.Uri;
import com.jogamp.common.util.IntObjectHashMap;
import com.jogamp.graph.font.FontSet;

public class UbuntuFontLoader implements FontSet
{
    private static final IntObjectHashMap fontMap;
    private static final Uri.Encoded jarSubDir;
    private static final Uri.Encoded jarName;
    private static final String absFontPath = "jogamp/graph/font/fonts/ubuntu/";
    private static final FontSet fontLoader;
    static final String[] availableFontFileNames;
    private static boolean attemptedJARLoading;
    private static boolean useTempJARCache;
    
    public static final FontSet get() {
        return UbuntuFontLoader.fontLoader;
    }
    
    static boolean is(final int n, final int n2) {
        return 0x0 != (n & n2);
    }
    
    @Override
    public Font getDefault() throws IOException {
        return this.get(0, 0);
    }
    
    @Override
    public Font get(final int n, final int n2) throws IOException {
        Font font = (Font)UbuntuFontLoader.fontMap.get(n << 8 | n2);
        if (font != null) {
            return font;
        }
        switch (n) {
            case 0:
            case 3:
            case 4: {
                if (is(n2, 4)) {
                    if (is(n2, 8)) {
                        font = this.abspath(UbuntuFontLoader.availableFontFileNames[3], n, n2);
                        break;
                    }
                    font = this.abspath(UbuntuFontLoader.availableFontFileNames[2], n, n2);
                    break;
                }
                else {
                    if (is(n2, 8)) {
                        font = this.abspath(UbuntuFontLoader.availableFontFileNames[1], n, n2);
                        break;
                    }
                    font = this.abspath(UbuntuFontLoader.availableFontFileNames[0], n, n2);
                    break;
                }
                break;
            }
            case 1: {
                if (is(n2, 8)) {
                    font = this.abspath(UbuntuFontLoader.availableFontFileNames[5], n, n2);
                    break;
                }
                font = this.abspath(UbuntuFontLoader.availableFontFileNames[4], n, n2);
                break;
            }
            case 2: {
                if (is(n2, 8)) {
                    font = this.abspath(UbuntuFontLoader.availableFontFileNames[6], n, n2);
                    break;
                }
                font = this.abspath(UbuntuFontLoader.availableFontFileNames[7], n, n2);
                break;
            }
        }
        return font;
    }
    
    private synchronized Font abspath(final String s, final int n, final int n2) throws IOException {
        if (!UbuntuFontLoader.attemptedJARLoading) {
            UbuntuFontLoader.attemptedJARLoading = true;
            Platform.initSingleton();
            if (TempJarCache.isInitialized()) {
                try {
                    final Exception ex = AccessController.doPrivileged((PrivilegedAction<Exception>)new PrivilegedAction<Exception>() {
                        final /* synthetic */ Uri val$uri = JarUtil.getRelativeOf(UbuntuFontLoader.class, UbuntuFontLoader.jarSubDir, UbuntuFontLoader.jarName);
                        
                        @Override
                        public Exception run() {
                            try {
                                TempJarCache.addResources(UbuntuFontLoader.class, this.val$uri);
                                UbuntuFontLoader.useTempJARCache = true;
                                return null;
                            }
                            catch (Exception ex) {
                                return ex;
                            }
                        }
                    });
                    if (null != ex) {
                        throw ex;
                    }
                }
                catch (Exception ex2) {
                    System.err.println("Caught " + ex2.getMessage());
                    ex2.printStackTrace();
                }
            }
        }
        try {
            final Font abspathImpl = this.abspathImpl("jogamp/graph/font/fonts/ubuntu/" + s, n, n2);
            if (null != abspathImpl) {
                return abspathImpl;
            }
            throw new IOException(String.format("Problem loading font %s, stream %s%s", s, "jogamp/graph/font/fonts/ubuntu/", s));
        }
        catch (Exception ex3) {
            throw new IOException(String.format("Problem loading font %s, stream %s%s", s, "jogamp/graph/font/fonts/ubuntu/", s), ex3);
        }
    }
    
    private Font abspathImpl(final String s, final int n, final int n2) throws IOException {
        InputStream inputStream;
        if (UbuntuFontLoader.useTempJARCache) {
            final Exception[] array = { null };
            inputStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
                @Override
                public InputStream run() {
                    try {
                        final Uri resourceUri = TempJarCache.getResourceUri(s);
                        return (null != resourceUri) ? resourceUri.toURL().openConnection().getInputStream() : null;
                    }
                    catch (Exception ex) {
                        array[0] = ex;
                        return null;
                    }
                }
            });
            if (null != array[0]) {
                throw new IOException(array[0]);
            }
        }
        else {
            final URLConnection resource = IOUtil.getResource(s, this.getClass().getClassLoader(), null);
            inputStream = ((null != resource) ? resource.getInputStream() : null);
        }
        if (null != inputStream) {
            final Font value = FontFactory.get(inputStream, true);
            if (null != value) {
                UbuntuFontLoader.fontMap.put(n << 8 | n2, value);
                return value;
            }
        }
        return null;
    }
    
    static {
        fontMap = new IntObjectHashMap();
        jarSubDir = Uri.Encoded.cast("atomic/");
        jarName = Uri.Encoded.cast("jogl-fonts-p0.jar");
        fontLoader = new UbuntuFontLoader();
        availableFontFileNames = new String[] { "Ubuntu-R.ttf", "Ubuntu-RI.ttf", "Ubuntu-B.ttf", "Ubuntu-BI.ttf", "Ubuntu-L.ttf", "Ubuntu-LI.ttf", "Ubuntu-M.ttf", "Ubuntu-MI.ttf" };
        UbuntuFontLoader.attemptedJARLoading = false;
        UbuntuFontLoader.useTempJARCache = false;
    }
}
