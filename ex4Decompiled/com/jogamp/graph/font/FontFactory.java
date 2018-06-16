// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.font;

import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.net.Uri;
import com.jogamp.common.util.cache.TempJarCache;
import java.security.AccessController;
import java.io.Closeable;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import com.jogamp.common.util.IOUtil;
import java.security.PrivilegedAction;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import jogamp.graph.font.UbuntuFontLoader;
import jogamp.graph.font.JavaFontLoader;
import jogamp.graph.font.FontConstructor;

public class FontFactory
{
    private static final String FontConstructorPropKey = "jogamp.graph.font.ctor";
    private static final String DefaultFontConstructor = "jogamp.graph.font.typecast.TypecastFontConstructor";
    public static final int UBUNTU = 0;
    public static final int JAVA = 1;
    private static final FontConstructor fontConstr;
    
    public static final FontSet getDefault() {
        return get(0);
    }
    
    public static final FontSet get(final int n) {
        switch (n) {
            case 1: {
                return JavaFontLoader.get();
            }
            default: {
                return UbuntuFontLoader.get();
            }
        }
    }
    
    public static final Font get(final File file) throws IOException {
        return FontFactory.fontConstr.create(file);
    }
    
    public static final Font get(final InputStream inputStream, final int n, final boolean b) throws IOException {
        try {
            return FontFactory.fontConstr.create(inputStream, n);
        }
        finally {
            if (b) {
                inputStream.close();
            }
        }
    }
    
    public static final Font get(final InputStream inputStream, final boolean b) throws IOException {
        final IOException[] array = { null };
        final int[] array2 = { 0 };
        final File[] array3 = { null };
        final InputStream inputStream2 = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction<InputStream>() {
            @Override
            public InputStream run() {
                InputStream inputStream = null;
                try {
                    array3[0] = IOUtil.createTempFile("jogl.font", ".ttf", false);
                    array2[0] = IOUtil.copyStream2File(inputStream, array3[0], -1);
                    if (0 == array2[0]) {
                        throw new IOException("Font stream has zero bytes");
                    }
                    inputStream = new BufferedInputStream(new FileInputStream(array3[0]), array2[0]);
                }
                catch (IOException ex) {
                    array[0] = ex;
                    if (null != array3[0]) {
                        array3[0].delete();
                        array3[0] = null;
                    }
                    array2[0] = 0;
                }
                finally {
                    if (b) {
                        IOUtil.close(inputStream, array, System.err);
                    }
                }
                return inputStream;
            }
        });
        if (null != array[0]) {
            throw array[0];
        }
        if (null == inputStream2) {
            throw new IOException("Could not cache font stream");
        }
        try {
            return FontFactory.fontConstr.create(inputStream2, array2[0]);
        }
        finally {
            if (null != inputStream2) {
                inputStream2.close();
            }
            if (null != array3[0]) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        array3[0].delete();
                        return null;
                    }
                });
            }
        }
    }
    
    public static final Font get(final Class<?> clazz, final String s, final boolean b) throws IOException {
        InputStream inputStream = null;
        Label_0062: {
            if (b) {
                try {
                    final Uri resourceUri = TempJarCache.getResourceUri(s);
                    inputStream = ((null != resourceUri) ? resourceUri.toURL().openConnection().getInputStream() : null);
                    break Label_0062;
                }
                catch (Exception ex) {
                    throw new IOException(ex);
                }
            }
            inputStream = IOUtil.getResource(s, clazz.getClassLoader(), clazz).getInputStream();
        }
        if (null != inputStream) {
            return get(inputStream, true);
        }
        return null;
    }
    
    public static boolean isPrintableChar(final char c) {
        if (Character.isWhitespace(c)) {
            return true;
        }
        if ('\0' == c || Character.isISOControl(c)) {
            return false;
        }
        final Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        return of != null && of != Character.UnicodeBlock.SPECIALS;
    }
    
    static {
        String property = PropertyAccess.getProperty("jogamp.graph.font.ctor", true);
        if (null == property) {
            property = "jogamp.graph.font.typecast.TypecastFontConstructor";
        }
        fontConstr = (FontConstructor)ReflectionUtil.createInstance(property, FontFactory.class.getClassLoader());
    }
}
