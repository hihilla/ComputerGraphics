// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font;

import com.jogamp.graph.font.FontFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import com.jogamp.opengl.GLException;
import java.io.IOException;
import com.jogamp.graph.font.Font;
import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.common.util.IntObjectHashMap;
import com.jogamp.graph.font.FontSet;

public class JavaFontLoader implements FontSet
{
    private static final IntObjectHashMap fontMap;
    private static final FontSet fontLoader;
    static final String[] availableFontFileNames;
    final String javaFontPath;
    
    public static FontSet get() {
        return JavaFontLoader.fontLoader;
    }
    
    private JavaFontLoader() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("java.home");
            }
        });
        if (null != s) {
            this.javaFontPath = s + "/lib/fonts/";
        }
        else {
            this.javaFontPath = null;
        }
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
        if (null == this.javaFontPath) {
            throw new GLException("java font path undefined");
        }
        Font font = (Font)JavaFontLoader.fontMap.get(n << 8 | n2);
        if (font != null) {
            return font;
        }
        if (is(n2, 2)) {
            Font font2;
            if (is(n2, 4)) {
                font2 = this.abspath(JavaFontLoader.availableFontFileNames[5], n, n2);
            }
            else {
                font2 = this.abspath(JavaFontLoader.availableFontFileNames[4], n, n2);
            }
            if (null != font2) {
                JavaFontLoader.fontMap.put(n << 8 | n2, font2);
            }
            return font2;
        }
        switch (n) {
            case 0:
            case 1:
            case 2:
            case 3: {
                if (is(n2, 4)) {
                    if (is(n2, 8)) {
                        font = this.abspath(JavaFontLoader.availableFontFileNames[3], n, n2);
                        break;
                    }
                    font = this.abspath(JavaFontLoader.availableFontFileNames[2], n, n2);
                    break;
                }
                else {
                    if (is(n2, 8)) {
                        font = this.abspath(JavaFontLoader.availableFontFileNames[1], n, n2);
                        break;
                    }
                    font = this.abspath(JavaFontLoader.availableFontFileNames[0], n, n2);
                    break;
                }
                break;
            }
            case 4: {
                if (is(n2, 4)) {
                    font = this.abspath(JavaFontLoader.availableFontFileNames[7], n, n2);
                    break;
                }
                font = this.abspath(JavaFontLoader.availableFontFileNames[6], n, n2);
                break;
            }
        }
        return font;
    }
    
    Font abspath(final String s, final int n, final int n2) throws IOException {
        try {
            final Font abspathImpl = this.abspathImpl(this.javaFontPath + s, n, n2);
            if (null != abspathImpl) {
                return abspathImpl;
            }
            throw new IOException(String.format("Problem loading font %s, file %s%s", s, this.javaFontPath, s));
        }
        catch (IOException ex) {
            throw new IOException(String.format("Problem loading font %s, file %s%s", s, this.javaFontPath, s), ex);
        }
    }
    
    private Font abspathImpl(final String s, final int n, final int n2) throws IOException {
        final Exception[] array = { null };
        final int[] array2 = { 0 };
        final InputStream inputStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
            @Override
            public InputStream run() {
                try {
                    final File file = new File(s);
                    array2[0] = (int)file.length();
                    return new BufferedInputStream(new FileInputStream(file), array2[0]);
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
        if (null != inputStream) {
            final Font value = FontFactory.get(inputStream, array2[0], true);
            if (null != value) {
                JavaFontLoader.fontMap.put(n << 8 | n2, value);
                return value;
            }
        }
        return null;
    }
    
    static {
        fontMap = new IntObjectHashMap();
        fontLoader = new JavaFontLoader();
        availableFontFileNames = new String[] { "LucidaBrightRegular.ttf", "LucidaBrightItalic.ttf", "LucidaBrightDemiBold.ttf", "LucidaBrightDemiItalic.ttf", "LucidaSansRegular.ttf", "LucidaSansDemiBold.ttf", "LucidaTypewriterRegular.ttf", "LucidaTypewriterBold.ttf" };
    }
}
