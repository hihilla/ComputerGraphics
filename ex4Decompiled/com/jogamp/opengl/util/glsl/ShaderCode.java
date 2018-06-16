// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.opengl.GLContext;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.nio.ByteBuffer;
import java.io.Closeable;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;
import com.jogamp.common.util.IOUtil;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import com.jogamp.common.net.Uri;
import java.io.IOException;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import java.nio.Buffer;

public class ShaderCode
{
    public static final boolean DEBUG_CODE;
    public static final String SUFFIX_VERTEX_SOURCE = "vp";
    public static final String SUFFIX_VERTEX_BINARY = "bvp";
    public static final String SUFFIX_GEOMETRY_SOURCE = "gp";
    public static final String SUFFIX_GEOMETRY_BINARY = "bgp";
    public static final String SUFFIX_COMPUTE_SOURCE = "cp";
    public static final String SUFFIX_COMPUTE_BINARY = "bcp";
    public static final String SUFFIX_TESS_CONTROL_SOURCE = "tcp";
    public static final String SUFFIX_TESS_CONTROL_BINARY = "btcp";
    public static final String SUFFIX_TESS_EVALUATION_SOURCE = "tep";
    public static final String SUFFIX_TESS_EVALUATION_BINARY = "btep";
    public static final String SUFFIX_FRAGMENT_SOURCE = "fp";
    public static final String SUFFIX_FRAGMENT_BINARY = "bfp";
    public static final String SUB_PATH_NVIDIA = "nvidia";
    public static final String es2_default_precision_vp = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
    public static final String es2_default_precision_fp = "\nprecision mediump float;\nprecision mediump int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
    public static final String es3_default_precision_vp = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
    public static final String es3_default_precision_fp = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
    public static final String gl3_default_precision_vp_gp = "\nprecision highp float;\nprecision highp int;\n";
    public static final String gl3_default_precision_fp = "\nprecision highp float;\nprecision mediump int;\n/*precision mediump sampler2D;*/\n";
    public static final String REQUIRE = "require";
    public static final String ENABLE = "enable";
    public static final String DISABLE = "disable";
    public static final String WARN = "warn";
    protected CharSequence[][] shaderSource;
    protected Buffer shaderBinary;
    protected int shaderBinaryFormat;
    protected IntBuffer shader;
    protected int shaderType;
    protected int id;
    protected boolean valid;
    protected static int nextID;
    
    public ShaderCode(final int shaderType, final int n, final CharSequence[][] shaderSource) {
        this.shaderSource = null;
        this.shaderBinary = null;
        this.shaderBinaryFormat = -1;
        this.shader = null;
        this.shaderType = -1;
        this.id = -1;
        this.valid = false;
        if (shaderSource.length != n) {
            throw new IllegalArgumentException("shader number (" + n + ") and sourceFiles array (" + shaderSource.length + ") of different lenght.");
        }
        switch (shaderType) {
            case 35632:
            case 35633:
            case 36313:
            case 36487:
            case 36488:
            case 37305: {
                this.shaderSource = shaderSource;
                this.shaderBinaryFormat = -1;
                this.shaderBinary = null;
                this.shaderType = shaderType;
                this.shader = Buffers.newDirectIntBuffer(n);
                this.id = getNextID();
                if (ShaderCode.DEBUG_CODE) {
                    System.out.println("Created: " + this.toString());
                }
            }
            default: {
                throw new GLException("Unknown shader type: " + shaderType);
            }
        }
    }
    
    public ShaderCode(final int shaderType, final int n, final int shaderBinaryFormat, final Buffer shaderBinary) {
        this.shaderSource = null;
        this.shaderBinary = null;
        this.shaderBinaryFormat = -1;
        this.shader = null;
        this.shaderType = -1;
        this.id = -1;
        this.valid = false;
        switch (shaderType) {
            case 35632:
            case 35633:
            case 36313:
            case 36487:
            case 36488:
            case 37305: {
                this.shaderSource = null;
                this.shaderBinaryFormat = shaderBinaryFormat;
                this.shaderBinary = shaderBinary;
                this.shaderType = shaderType;
                this.shader = Buffers.newDirectIntBuffer(n);
                this.id = getNextID();
            }
            default: {
                throw new GLException("Unknown shader type: " + shaderType);
            }
        }
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final int n2, final Class<?> clazz, final String[] array, final boolean b) {
        if (null != gl2ES2 && !ShaderUtil.isShaderCompilerAvailable(gl2ES2)) {
            return null;
        }
        CharSequence[][] array2 = null;
        if (null != array) {
            array2 = new CharSequence[array.length][1];
            for (int i = 0; i < array.length; ++i) {
                try {
                    array2[i][0] = readShaderSource(clazz, array[i], b);
                }
                catch (IOException ex) {
                    throw new RuntimeException("readShaderSource(" + array[i] + ") error: ", ex);
                }
                if (null == array2[i][0]) {
                    array2 = null;
                }
            }
        }
        if (null == array2) {
            return null;
        }
        return new ShaderCode(n, n2, array2);
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final int n2, final Uri[] array, final boolean b) {
        if (null != gl2ES2 && !ShaderUtil.isShaderCompilerAvailable(gl2ES2)) {
            return null;
        }
        CharSequence[][] array2 = null;
        if (null != array) {
            array2 = new CharSequence[array.length][1];
            for (int i = 0; i < array.length; ++i) {
                try {
                    array2[i][0] = readShaderSource(array[i], b);
                }
                catch (IOException ex) {
                    throw new RuntimeException("readShaderSource(" + array[i] + ") error: ", ex);
                }
                if (null == array2[i][0]) {
                    array2 = null;
                }
            }
        }
        if (null == array2) {
            return null;
        }
        return new ShaderCode(n, n2, array2);
    }
    
    public static ShaderCode create(final int n, final int n2, final Class<?> clazz, int n3, final String s) {
        Buffer shaderBinary = null;
        if (null != s && 0 <= n3) {
            try {
                shaderBinary = readShaderBinary(clazz, s);
            }
            catch (IOException ex) {
                throw new RuntimeException("readShaderBinary(" + s + ") error: ", ex);
            }
            if (null == shaderBinary) {
                n3 = -1;
            }
        }
        if (null == shaderBinary) {
            return null;
        }
        return new ShaderCode(n, n2, n3, shaderBinary);
    }
    
    public static ShaderCode create(final int n, final int n2, int n3, final Uri uri) {
        Buffer shaderBinary = null;
        if (null != uri && 0 <= n3) {
            try {
                shaderBinary = readShaderBinary(uri);
            }
            catch (IOException ex) {
                throw new RuntimeException("readShaderBinary(" + uri + ") error: ", ex);
            }
            if (null == shaderBinary) {
                n3 = -1;
            }
        }
        if (null == shaderBinary) {
            return null;
        }
        return new ShaderCode(n, n2, n3, shaderBinary);
    }
    
    public static String getFileSuffix(final boolean b, final int n) {
        switch (n) {
            case 35633: {
                return b ? "bvp" : "vp";
            }
            case 35632: {
                return b ? "bfp" : "fp";
            }
            case 36313: {
                return b ? "bgp" : "gp";
            }
            case 36488: {
                return b ? "btcp" : "tcp";
            }
            case 36487: {
                return b ? "btep" : "tep";
            }
            case 37305: {
                return b ? "bcp" : "cp";
            }
            default: {
                throw new GLException("illegal shader type: " + n);
            }
        }
    }
    
    public static String getBinarySubPath(final int n) {
        switch (n) {
            case 35083: {
                return "nvidia";
            }
            default: {
                throw new GLException("unsupported binary format: " + n);
            }
        }
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final int n2, final Class<?> clazz, final String s, final String[] array, final String s2, final String s3, final String s4, final String s5, final boolean b) {
        String string = null;
        String string2 = null;
        if (null != array && ShaderUtil.isShaderCompilerAvailable(gl2ES2)) {
            final String[] array2 = new String[array.length];
            final String s6 = (null != s2) ? s2 : getFileSuffix(false, n);
            if (null != s && s.length() > 0) {
                for (int i = 0; i < array2.length; ++i) {
                    array2[i] = s + '/' + array[i] + "." + s6;
                }
            }
            else {
                for (int j = 0; j < array2.length; ++j) {
                    array2[j] = array[j] + "." + s6;
                }
            }
            final ShaderCode create = create(gl2ES2, n, n2, clazz, array2, b);
            if (null != create) {
                return create;
            }
            string = Arrays.toString(array2);
        }
        if (null != s4) {
            final Set<Integer> shaderBinaryFormats = ShaderUtil.getShaderBinaryFormats(gl2ES2);
            final String s7 = (null != s5) ? s5 : getFileSuffix(true, n);
            for (final int intValue : shaderBinaryFormats) {
                final String binarySubPath = getBinarySubPath(intValue);
                if (null == binarySubPath) {
                    continue;
                }
                string2 = s3 + '/' + binarySubPath + '/' + s4 + "." + s7;
                final ShaderCode create2 = create(n, n2, clazz, intValue, string2);
                if (null != create2) {
                    return create2;
                }
            }
        }
        throw new GLException("No shader code found (source nor binary) for src: " + string + ", bin: " + string2);
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final int n2, final Class<?> clazz, final String s, final String[] array, final String s2, final String s3, final boolean b) {
        return create(gl2ES2, n, n2, clazz, s, array, null, s2, s3, null, b);
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final Class<?> clazz, final String s, final String s2, final String s3, final String s4, final String s5, final boolean b) {
        return create(gl2ES2, n, 1, clazz, s, new String[] { s3 }, s4, s2, s3, s5, b);
    }
    
    public static ShaderCode create(final GL2ES2 gl2ES2, final int n, final Class<?> clazz, final String s, final String s2, final String s3, final boolean b) {
        return create(gl2ES2, n, clazz, s, s2, s3, null, null, b);
    }
    
    public int id() {
        return this.id;
    }
    
    public int shaderType() {
        return this.shaderType;
    }
    
    public String shaderTypeStr() {
        return shaderTypeStr(this.shaderType);
    }
    
    public static String shaderTypeStr(final int n) {
        switch (n) {
            case 35633: {
                return "VERTEX_SHADER";
            }
            case 35632: {
                return "FRAGMENT_SHADER";
            }
            case 36313: {
                return "GEOMETRY_SHADER";
            }
            case 36488: {
                return "TESS_CONTROL_SHADER";
            }
            case 36487: {
                return "TESS_EVALUATION_SHADER";
            }
            case 37305: {
                return "COMPUTE_SHADER";
            }
            default: {
                return "UNKNOWN_SHADER";
            }
        }
    }
    
    public int shaderBinaryFormat() {
        return this.shaderBinaryFormat;
    }
    
    public Buffer shaderBinary() {
        return this.shaderBinary;
    }
    
    public CharSequence[][] shaderSource() {
        return this.shaderSource;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public IntBuffer shader() {
        return this.shader;
    }
    
    public boolean compile(final GL2ES2 gl2ES2) {
        return this.compile(gl2ES2, null);
    }
    
    public boolean compile(final GL2ES2 gl2ES2, final PrintStream printStream) {
        if (this.isValid()) {
            return true;
        }
        if (null != this.shaderSource) {
            if (ShaderCode.DEBUG_CODE) {
                System.err.println("ShaderCode.compile:");
                this.dumpShaderSource(System.err);
            }
            this.valid = ShaderUtil.createAndCompileShader(gl2ES2, this.shader, this.shaderType, this.shaderSource, printStream);
        }
        else {
            if (null == this.shaderBinary) {
                throw new GLException("no code (source or binary)");
            }
            this.valid = ShaderUtil.createAndLoadShader(gl2ES2, this.shader, this.shaderType, this.shaderBinaryFormat, this.shaderBinary, printStream);
        }
        return this.valid;
    }
    
    public void destroy(final GL2ES2 gl2ES2) {
        if (this.isValid()) {
            if (null != gl2ES2) {
                ShaderUtil.deleteShader(gl2ES2, this.shader());
            }
            this.valid = false;
        }
        if (null != this.shaderBinary) {
            this.shaderBinary.clear();
            this.shaderBinary = null;
        }
        this.shaderSource = null;
        this.shaderBinaryFormat = -1;
        this.shaderType = -1;
        this.id = -1;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ShaderCode && this.id() == ((ShaderCode)o).id());
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShaderCode[id=" + this.id + ", type=" + this.shaderTypeStr() + ", valid=" + this.valid + ", shader: ");
        for (int i = 0; i < this.shader.remaining(); ++i) {
            sb.append(" " + this.shader.get(i));
        }
        if (null != this.shaderSource) {
            sb.append(", source]");
        }
        else if (null != this.shaderBinary) {
            sb.append(", binary " + this.shaderBinary + "]");
        }
        return sb.toString();
    }
    
    public void dumpShaderSource(final PrintStream printStream) {
        if (null == this.shaderSource) {
            printStream.println("<no shader source>");
            return;
        }
        final int length = this.shaderSource.length;
        for (int n = (null != this.shader) ? this.shader.capacity() : 0, i = 0; i < n; ++i) {
            printStream.println("");
            printStream.println("Shader #" + i + "/" + n + " name " + this.shader.get(i));
            printStream.println("--------------------------------------------------------------");
            if (i >= length) {
                printStream.println("<no shader source>");
            }
            else {
                final CharSequence[] array = this.shaderSource[i];
                int n2 = 0;
                for (int j = 0; j < array.length; ++j) {
                    printStream.printf("%4d: // Segment %d/%d: \n", n2, j, array.length);
                    final BufferedReader bufferedReader = new BufferedReader(new StringReader(array[j].toString()));
                    try {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            ++n2;
                            printStream.printf("%4d: %s\n", n2, line);
                        }
                    }
                    catch (IOException ex) {}
                }
            }
            printStream.println("--------------------------------------------------------------");
        }
    }
    
    public int insertShaderSource(final int n, final String s, final int n2, final CharSequence charSequence) {
        if (null == this.shaderSource) {
            throw new IllegalStateException("no shader source");
        }
        final int n3 = (null != this.shader) ? this.shader.capacity() : 0;
        if (0 > n || n >= n3) {
            throw new IndexOutOfBoundsException("shaderIdx not within shader bounds [0.." + (n3 - 1) + "]: " + n);
        }
        final int length = this.shaderSource.length;
        if (n >= length) {
            throw new IndexOutOfBoundsException("shaderIdx not within source bounds [0.." + (length - 1) + "]: " + n);
        }
        final CharSequence[] array = this.shaderSource[n];
        int n4 = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!(array[i] instanceof StringBuilder)) {
                throw new IllegalStateException("shader source not a mutable StringBuilder, but CharSequence of type: " + array[i].getClass().getName());
            }
            final StringBuilder sb = (StringBuilder)array[i];
            n4 += sb.length();
            if (n2 < n4) {
                final int index = sb.indexOf(s, n2);
                if (0 <= index) {
                    int n5 = index + s.length();
                    int n6 = sb.indexOf("\n", n5);
                    if (0 > n6) {
                        n6 = sb.indexOf("\r", n5);
                    }
                    if (0 < n6) {
                        n5 = n6 + 1;
                    }
                    else {
                        sb.insert(n5, "\n");
                        ++n5;
                    }
                    sb.insert(n5, charSequence);
                    return n5 + charSequence.length();
                }
            }
        }
        return -1;
    }
    
    public int replaceInShaderSource(final String s, final String s2) {
        if (null == this.shaderSource) {
            throw new IllegalStateException("no shader source");
        }
        if (s == s2 || s.equals(s2)) {
            return 0;
        }
        final int length = s.length();
        final int length2 = s2.length();
        int n = 0;
        for (int length3 = this.shaderSource.length, i = 0; i < length3; ++i) {
            final CharSequence[] array = this.shaderSource[i];
            for (int j = 0; j < array.length; ++j) {
                if (!(array[j] instanceof StringBuilder)) {
                    throw new IllegalStateException("shader source not a mutable StringBuilder, but CharSequence of type: " + array[j].getClass().getName());
                }
                final StringBuilder sb = (StringBuilder)array[j];
                int k = 0;
                while (k < sb.length() - length + 1) {
                    final int index = sb.indexOf(s, k);
                    if (0 <= index) {
                        sb.replace(index, index + length, s2);
                        k = index + length2;
                        ++n;
                    }
                    else {
                        k = sb.length();
                    }
                }
            }
        }
        return n;
    }
    
    public int insertShaderSource(final int n, int n2, final CharSequence charSequence) {
        if (null == this.shaderSource) {
            throw new IllegalStateException("no shader source");
        }
        final int n3 = (null != this.shader) ? this.shader.capacity() : 0;
        if (0 > n || n >= n3) {
            throw new IndexOutOfBoundsException("shaderIdx not within shader bounds [0.." + (n3 - 1) + "]: " + n);
        }
        final int length = this.shaderSource.length;
        if (n >= length) {
            throw new IndexOutOfBoundsException("shaderIdx not within source bounds [0.." + (length - 1) + "]: " + n);
        }
        final CharSequence[] array = this.shaderSource[n];
        int n4 = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!(array[i] instanceof StringBuilder)) {
                throw new IllegalStateException("shader source not a mutable StringBuilder, but CharSequence of type: " + array[i].getClass().getName());
            }
            final StringBuilder sb = (StringBuilder)array[i];
            n4 += sb.length();
            if (0 > n2 && i == array.length - 1) {
                n2 = n4;
            }
            if (0 <= n2 && n2 <= n4) {
                sb.insert(n2, charSequence);
                return n2 + charSequence.length();
            }
        }
        return n2;
    }
    
    public int insertShaderSource(final int n, final int n2, final Class<?> clazz, final String s) throws IOException {
        final CharSequence shaderSource = readShaderSource(clazz, s, true);
        if (null != shaderSource) {
            return this.insertShaderSource(n, n2, shaderSource);
        }
        return n2;
    }
    
    private static int readShaderSource(final Class<?> clazz, final URLConnection urlConnection, final StringBuilder sb, int shaderSource) throws IOException {
        if (ShaderCode.DEBUG_CODE) {
            if (0 == shaderSource) {
                sb.append("// " + urlConnection.getURL().toExternalForm() + "\n");
            }
            else {
                sb.append("// included @ line " + shaderSource + ": " + urlConnection.getURL().toExternalForm() + "\n");
            }
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ++shaderSource;
                if (line.startsWith("#include ")) {
                    final String trim = line.substring(9).trim();
                    URLConnection urlConnection2 = IOUtil.openURL(Uri.valueOf(urlConnection.getURL()).getRelativeOf(new Uri.Encoded(trim, "/!_-.~")).toURL(), "ShaderCode.relativeOf ");
                    if (urlConnection2 == null) {
                        urlConnection2 = IOUtil.getResource(trim, clazz.getClassLoader(), clazz);
                    }
                    if (urlConnection2 == null) {
                        throw new FileNotFoundException("Can't find include file " + trim);
                    }
                    shaderSource = readShaderSource(clazz, urlConnection2, sb, shaderSource);
                }
                else {
                    sb.append(line + "\n");
                }
            }
        }
        catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
        finally {
            IOUtil.close(bufferedReader, false);
        }
        return shaderSource;
    }
    
    public static void readShaderSource(final Class<?> clazz, final URLConnection urlConnection, final StringBuilder sb) throws IOException {
        readShaderSource(clazz, urlConnection, sb, 0);
    }
    
    public static CharSequence readShaderSource(final Class<?> clazz, final String s, final boolean b) throws IOException {
        final URLConnection resource = IOUtil.getResource(s, clazz.getClassLoader(), clazz);
        if (resource == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        readShaderSource(clazz, resource, sb);
        return (String)(b ? sb : sb.toString());
    }
    
    public static CharSequence readShaderSource(final Uri uri, final boolean b) throws IOException {
        final URLConnection openURL = IOUtil.openURL(uri.toURL(), "ShaderCode ");
        if (openURL == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        readShaderSource(null, openURL, sb);
        return (String)(b ? sb : sb.toString());
    }
    
    public static ByteBuffer readShaderBinary(final Class<?> clazz, final String s) throws IOException {
        final URLConnection resource = IOUtil.getResource(s, clazz.getClassLoader(), clazz);
        if (resource == null) {
            return null;
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(resource.getInputStream());
        try {
            return IOUtil.copyStream2ByteBuffer(bufferedInputStream);
        }
        finally {
            IOUtil.close(bufferedInputStream, false);
        }
    }
    
    public static ByteBuffer readShaderBinary(final Uri uri) throws IOException {
        final URLConnection openURL = IOUtil.openURL(uri.toURL(), "ShaderCode ");
        if (openURL == null) {
            return null;
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(openURL.getInputStream());
        try {
            return IOUtil.copyStream2ByteBuffer(bufferedInputStream);
        }
        finally {
            IOUtil.close(bufferedInputStream, false);
        }
    }
    
    public static String createExtensionDirective(final String s, final String s2) {
        return "#extension " + s + " : " + s2 + "\n";
    }
    
    public final int addGLSLVersion(final GL2ES2 gl2ES2) {
        return this.insertShaderSource(0, 0, gl2ES2.getContext().getGLSLVersionString());
    }
    
    public final int addDefaultShaderPrecision(final GL2ES2 gl2ES2, int insertShaderSource) {
        String s = null;
        if (gl2ES2.isGLES3()) {
            switch (this.shaderType) {
                case 35633: {
                    s = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
                    break;
                }
                case 35632: {
                    s = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
                    break;
                }
                case 37305: {
                    s = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
                    break;
                }
                default: {
                    s = null;
                    break;
                }
            }
        }
        else if (gl2ES2.isGLES2()) {
            switch (this.shaderType) {
                case 35633: {
                    s = "\nprecision highp float;\nprecision highp int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
                    break;
                }
                case 35632: {
                    s = "\nprecision mediump float;\nprecision mediump int;\n/*precision lowp sampler2D;*/\n/*precision lowp samplerCube;*/\n";
                    break;
                }
                default: {
                    s = null;
                    break;
                }
            }
        }
        else if (requiresGL3DefaultPrecision(gl2ES2)) {
            switch (this.shaderType) {
                case 35633:
                case 36313:
                case 36487:
                case 36488: {
                    s = "\nprecision highp float;\nprecision highp int;\n";
                    break;
                }
                case 35632: {
                    s = "\nprecision highp float;\nprecision mediump int;\n/*precision mediump sampler2D;*/\n";
                    break;
                }
                case 37305: {
                    s = "\nprecision highp float;\nprecision mediump int;\n/*precision mediump sampler2D;*/\n";
                    break;
                }
                default: {
                    s = null;
                    break;
                }
            }
        }
        else {
            s = null;
        }
        if (null != s) {
            insertShaderSource = this.insertShaderSource(0, insertShaderSource, s);
        }
        return insertShaderSource;
    }
    
    public static final boolean requiresDefaultPrecision(final GL2ES2 gl2ES2) {
        return gl2ES2.isGLES() || requiresGL3DefaultPrecision(gl2ES2);
    }
    
    public static final boolean requiresGL3DefaultPrecision(final GL2ES2 gl2ES2) {
        if (gl2ES2.isGL3()) {
            final VersionNumber glslVersionNumber = gl2ES2.getContext().getGLSLVersionNumber();
            return glslVersionNumber.compareTo(GLContext.Version1_30) >= 0 && glslVersionNumber.compareTo(GLContext.Version1_50) < 0;
        }
        return false;
    }
    
    public final int defaultShaderCustomization(final GL2ES2 gl2ES2, final boolean b, final boolean b2) {
        int n;
        if (b) {
            n = this.addGLSLVersion(gl2ES2);
        }
        else {
            n = 0;
        }
        if (b2) {
            n = this.addDefaultShaderPrecision(gl2ES2, n);
        }
        return n;
    }
    
    public final int defaultShaderCustomization(final GL2ES2 gl2ES2, final boolean b, final String s) {
        int addGLSLVersion;
        if (b) {
            addGLSLVersion = this.addGLSLVersion(gl2ES2);
        }
        else {
            addGLSLVersion = 0;
        }
        int n;
        if (gl2ES2.isGLES() && null != s) {
            n = this.insertShaderSource(0, addGLSLVersion, s);
        }
        else {
            n = this.addDefaultShaderPrecision(gl2ES2, addGLSLVersion);
        }
        return n;
    }
    
    private static synchronized int getNextID() {
        return ShaderCode.nextID++;
    }
    
    static {
        DEBUG_CODE = PropertyAccess.isPropertyDefined("jogl.debug.GLSLCode", true);
        ShaderCode.nextID = 1;
    }
}
