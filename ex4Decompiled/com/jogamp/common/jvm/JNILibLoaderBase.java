// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.jvm;

import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.common.util.PropertyAccess;
import jogamp.common.Debug;
import java.util.Iterator;
import com.jogamp.common.os.NativeLibrary;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.net.URISyntaxException;
import java.io.IOException;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.util.JarUtil;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.common.net.Uri;
import java.lang.reflect.Method;
import java.util.HashSet;

public class JNILibLoaderBase
{
    public static final boolean DEBUG;
    protected static final boolean PERF;
    private static final Object perfSync;
    private static long perfTotal;
    private static long perfCount;
    private static final HashSet<String> loaded;
    private static LoaderAction loaderAction;
    private static final String nativeJarTagPackage = "jogamp.nativetag";
    private static final Method customLoadLibraryMethod;
    
    public static boolean isLoaded(final String s) {
        return JNILibLoaderBase.loaded.contains(s);
    }
    
    public static void addLoaded(final String s) {
        JNILibLoaderBase.loaded.add(s);
        if (JNILibLoaderBase.DEBUG) {
            System.err.println("JNILibLoaderBase: Loaded Native Library: " + s);
        }
    }
    
    public static void disableLoading() {
        setLoadingAction(null);
    }
    
    public static void enableLoading() {
        setLoadingAction(new DefaultAction());
    }
    
    public static synchronized void setLoadingAction(final LoaderAction loaderAction) {
        JNILibLoaderBase.loaderAction = loaderAction;
    }
    
    private static final boolean addNativeJarLibsImpl(final Class<?> clazz, final Uri uri, final Uri.Encoded encoded, final Uri.Encoded encoded2) throws IOException, SecurityException, URISyntaxException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //     3: ifeq            140
        //     6: new             Ljava/lang/StringBuilder;
        //     9: dup            
        //    10: invokespecial   java/lang/StringBuilder.<init>:()V
        //    13: astore          4
        //    15: aload           4
        //    17: ldc             "JNILibLoaderBase: addNativeJarLibsImpl("
        //    19: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    22: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //    25: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    28: pop            
        //    29: aload           4
        //    31: ldc             "  classFromJavaJar  = "
        //    33: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    36: aload_0        
        //    37: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    40: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //    43: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    46: pop            
        //    47: aload           4
        //    49: ldc             "  classJarURI       = "
        //    51: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    54: aload_1        
        //    55: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    58: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //    61: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    64: pop            
        //    65: aload           4
        //    67: ldc             "  jarBasename       = "
        //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    72: aload_2        
        //    73: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;
        //    76: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    82: pop            
        //    83: aload           4
        //    85: ldc             "  os.and.arch       = "
        //    87: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    90: getstatic       jogamp/common/os/PlatformPropsImpl.os_and_arch:Ljava/lang/String;
        //    93: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    96: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   102: pop            
        //   103: aload           4
        //   105: ldc             "  nativeJarBasename = "
        //   107: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   110: aload_3        
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;
        //   114: getstatic       jogamp/common/os/PlatformPropsImpl.NEWLINE:Ljava/lang/String;
        //   117: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   120: pop            
        //   121: aload           4
        //   123: ldc             ")"
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: pop            
        //   129: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   132: aload           4
        //   134: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   137: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //   140: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.PERF:Z
        //   143: ifeq            152
        //   146: invokestatic    java/lang/System.currentTimeMillis:()J
        //   149: goto            153
        //   152: lconst_0       
        //   153: lstore          4
        //   155: iconst_0       
        //   156: istore          6
        //   158: aload_1        
        //   159: invokevirtual   com/jogamp/common/net/Uri.getContainedUri:()Lcom/jogamp/common/net/Uri;
        //   162: astore          7
        //   164: aconst_null    
        //   165: aload           7
        //   167: if_acmpne       197
        //   170: new             Ljava/lang/IllegalArgumentException;
        //   173: dup            
        //   174: new             Ljava/lang/StringBuilder;
        //   177: dup            
        //   178: invokespecial   java/lang/StringBuilder.<init>:()V
        //   181: ldc             "JarSubURI is null of: "
        //   183: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   186: aload_1        
        //   187: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   190: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   193: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //   196: athrow         
        //   197: aload           7
        //   199: invokevirtual   com/jogamp/common/net/Uri.getDirectory:()Lcom/jogamp/common/net/Uri;
        //   202: astore          8
        //   204: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   207: ifeq            233
        //   210: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   213: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: initial: %s -> %s%n"
        //   215: iconst_2       
        //   216: anewarray       Ljava/lang/Object;
        //   219: dup            
        //   220: iconst_0       
        //   221: aload           7
        //   223: aastore        
        //   224: dup            
        //   225: iconst_1       
        //   226: aload           8
        //   228: aastore        
        //   229: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   232: pop            
        //   233: ldc             "natives/%s/"
        //   235: iconst_1       
        //   236: anewarray       Ljava/lang/Object;
        //   239: dup            
        //   240: iconst_0       
        //   241: getstatic       jogamp/common/os/PlatformPropsImpl.os_and_arch:Ljava/lang/String;
        //   244: aastore        
        //   245: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   248: astore          9
        //   250: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   253: ifeq            274
        //   256: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   259: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: nativeLibraryPath: %s%n"
        //   261: iconst_1       
        //   262: anewarray       Ljava/lang/Object;
        //   265: dup            
        //   266: iconst_0       
        //   267: aload           9
        //   269: aastore        
        //   270: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   273: pop            
        //   274: aload           8
        //   276: invokevirtual   com/jogamp/common/net/Uri.getEncoded:()Lcom/jogamp/common/net/Uri$Encoded;
        //   279: aload_3        
        //   280: invokevirtual   com/jogamp/common/net/Uri$Encoded.concat:(Lcom/jogamp/common/net/Uri$Encoded;)Lcom/jogamp/common/net/Uri$Encoded;
        //   283: invokestatic    com/jogamp/common/util/JarUtil.getJarFileUri:(Lcom/jogamp/common/net/Uri$Encoded;)Lcom/jogamp/common/net/Uri;
        //   286: astore          10
        //   288: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   291: ifeq            316
        //   294: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   297: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: module: %s -> %s%n"
        //   299: iconst_2       
        //   300: anewarray       Ljava/lang/Object;
        //   303: dup            
        //   304: iconst_0       
        //   305: aload_3        
        //   306: aastore        
        //   307: dup            
        //   308: iconst_1       
        //   309: aload           10
        //   311: aastore        
        //   312: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   315: pop            
        //   316: aload_0        
        //   317: aload           10
        //   319: aload           9
        //   321: invokestatic    com/jogamp/common/util/cache/TempJarCache.addNativeLibs:(Ljava/lang/Class;Lcom/jogamp/common/net/Uri;Ljava/lang/String;)Z
        //   324: istore          6
        //   326: goto            363
        //   329: astore          11
        //   331: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   334: ifeq            363
        //   337: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   340: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n"
        //   342: iconst_1       
        //   343: anewarray       Ljava/lang/Object;
        //   346: dup            
        //   347: iconst_0       
        //   348: aload           11
        //   350: invokevirtual   java/lang/Exception.getMessage:()Ljava/lang/String;
        //   353: aastore        
        //   354: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   357: pop            
        //   358: aload           11
        //   360: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   363: iload           6
        //   365: ifne            697
        //   368: aload_0        
        //   369: invokevirtual   java/lang/Class.getClassLoader:()Ljava/lang/ClassLoader;
        //   372: astore          10
        //   374: aload           10
        //   376: aload           9
        //   378: invokevirtual   java/lang/ClassLoader.getResource:(Ljava/lang/String;)Ljava/net/URL;
        //   381: astore          11
        //   383: aconst_null    
        //   384: aload           11
        //   386: if_acmpeq       482
        //   389: aload           8
        //   391: invokevirtual   com/jogamp/common/net/Uri.getEncoded:()Lcom/jogamp/common/net/Uri$Encoded;
        //   394: aload_2        
        //   395: invokevirtual   com/jogamp/common/net/Uri$Encoded.concat:(Lcom/jogamp/common/net/Uri$Encoded;)Lcom/jogamp/common/net/Uri$Encoded;
        //   398: invokestatic    com/jogamp/common/util/JarUtil.getJarFileUri:(Lcom/jogamp/common/net/Uri$Encoded;)Lcom/jogamp/common/net/Uri;
        //   401: astore          12
        //   403: aload_0        
        //   404: aload           12
        //   406: aload           9
        //   408: invokestatic    com/jogamp/common/util/cache/TempJarCache.addNativeLibs:(Ljava/lang/Class;Lcom/jogamp/common/net/Uri;Ljava/lang/String;)Z
        //   411: ifeq            445
        //   414: iconst_1       
        //   415: istore          6
        //   417: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   420: ifeq            445
        //   423: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   426: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: fat: %s -> %s%n"
        //   428: iconst_2       
        //   429: anewarray       Ljava/lang/Object;
        //   432: dup            
        //   433: iconst_0       
        //   434: aload_2        
        //   435: aastore        
        //   436: dup            
        //   437: iconst_1       
        //   438: aload           12
        //   440: aastore        
        //   441: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   444: pop            
        //   445: goto            482
        //   448: astore          13
        //   450: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   453: ifeq            482
        //   456: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   459: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n"
        //   461: iconst_1       
        //   462: anewarray       Ljava/lang/Object;
        //   465: dup            
        //   466: iconst_0       
        //   467: aload           13
        //   469: invokevirtual   java/lang/Exception.getMessage:()Ljava/lang/String;
        //   472: aastore        
        //   473: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   476: pop            
        //   477: aload           13
        //   479: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   482: iload           6
        //   484: ifne            697
        //   487: aload_0        
        //   488: invokevirtual   java/lang/Class.getPackage:()Ljava/lang/Package;
        //   491: invokevirtual   java/lang/Package.getName:()Ljava/lang/String;
        //   494: astore          12
        //   496: aload           12
        //   498: bipush          46
        //   500: invokevirtual   java/lang/String.lastIndexOf:(I)I
        //   503: istore          13
        //   505: iconst_0       
        //   506: iload           13
        //   508: if_icmpgt       525
        //   511: aload           12
        //   513: iload           13
        //   515: iconst_1       
        //   516: iadd           
        //   517: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   520: astore          11
        //   522: goto            529
        //   525: aload           12
        //   527: astore          11
        //   529: getstatic       jogamp/common/os/PlatformPropsImpl.os_and_arch:Ljava/lang/String;
        //   532: bipush          45
        //   534: bipush          46
        //   536: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //   539: astore          12
        //   541: new             Ljava/lang/StringBuilder;
        //   544: dup            
        //   545: invokespecial   java/lang/StringBuilder.<init>:()V
        //   548: ldc             "jogamp.nativetag."
        //   550: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   553: aload           11
        //   555: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   558: ldc             "."
        //   560: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   563: aload           12
        //   565: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   568: ldc             ".TAG"
        //   570: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   573: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   576: astore          13
        //   578: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   581: ifeq            612
        //   584: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   587: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: ClassLoader/TAG: Locating module %s, os.and.arch %s: %s%n"
        //   589: iconst_3       
        //   590: anewarray       Ljava/lang/Object;
        //   593: dup            
        //   594: iconst_0       
        //   595: aload           11
        //   597: aastore        
        //   598: dup            
        //   599: iconst_1       
        //   600: aload           12
        //   602: aastore        
        //   603: dup            
        //   604: iconst_2       
        //   605: aload           13
        //   607: aastore        
        //   608: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   611: pop            
        //   612: aload           13
        //   614: aload           10
        //   616: invokestatic    com/jogamp/common/util/JarUtil.getJarUri:(Ljava/lang/String;Ljava/lang/ClassLoader;)Lcom/jogamp/common/net/Uri;
        //   619: astore          14
        //   621: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   624: ifeq            650
        //   627: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   630: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: ClassLoader/TAG: %s -> %s%n"
        //   632: iconst_2       
        //   633: anewarray       Ljava/lang/Object;
        //   636: dup            
        //   637: iconst_0       
        //   638: aload           13
        //   640: aastore        
        //   641: dup            
        //   642: iconst_1       
        //   643: aload           14
        //   645: aastore        
        //   646: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   649: pop            
        //   650: aload_0        
        //   651: aload           14
        //   653: aload           9
        //   655: invokestatic    com/jogamp/common/util/cache/TempJarCache.addNativeLibs:(Ljava/lang/Class;Lcom/jogamp/common/net/Uri;Ljava/lang/String;)Z
        //   658: istore          6
        //   660: goto            697
        //   663: astore          14
        //   665: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   668: ifeq            697
        //   671: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   674: ldc             "JNILibLoaderBase: addNativeJarLibsImpl: Caught %s%n"
        //   676: iconst_1       
        //   677: anewarray       Ljava/lang/Object;
        //   680: dup            
        //   681: iconst_0       
        //   682: aload           14
        //   684: invokevirtual   java/lang/Exception.getMessage:()Ljava/lang/String;
        //   687: aastore        
        //   688: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   691: pop            
        //   692: aload           14
        //   694: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   697: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.DEBUG:Z
        //   700: ifne            709
        //   703: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.PERF:Z
        //   706: ifeq            835
        //   709: invokestatic    java/lang/System.currentTimeMillis:()J
        //   712: lload           4
        //   714: lsub           
        //   715: lstore          10
        //   717: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.perfSync:Ljava/lang/Object;
        //   720: dup            
        //   721: astore          16
        //   723: monitorenter   
        //   724: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.perfCount:J
        //   727: lconst_1       
        //   728: ladd           
        //   729: lstore          14
        //   731: getstatic       com/jogamp/common/jvm/JNILibLoaderBase.perfTotal:J
        //   734: lload           10
        //   736: ladd           
        //   737: lstore          12
        //   739: lload           12
        //   741: putstatic       com/jogamp/common/jvm/JNILibLoaderBase.perfTotal:J
        //   744: lload           14
        //   746: putstatic       com/jogamp/common/jvm/JNILibLoaderBase.perfCount:J
        //   749: aload           16
        //   751: monitorexit    
        //   752: goto            763
        //   755: astore          17
        //   757: aload           16
        //   759: monitorexit    
        //   760: aload           17
        //   762: athrow         
        //   763: lload           12
        //   765: l2d            
        //   766: lload           14
        //   768: l2d            
        //   769: ddiv           
        //   770: dstore          16
        //   772: getstatic       java/lang/System.err:Ljava/io/PrintStream;
        //   775: ldc             "JNILibLoaderBase: addNativeJarLibsImpl.X: %s / %s -> ok: %b; duration: now %d ms, total %d ms (count %d, avrg %.3f ms)%n"
        //   777: bipush          7
        //   779: anewarray       Ljava/lang/Object;
        //   782: dup            
        //   783: iconst_0       
        //   784: aload_2        
        //   785: aastore        
        //   786: dup            
        //   787: iconst_1       
        //   788: aload_3        
        //   789: aastore        
        //   790: dup            
        //   791: iconst_2       
        //   792: iload           6
        //   794: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   797: aastore        
        //   798: dup            
        //   799: iconst_3       
        //   800: lload           10
        //   802: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   805: aastore        
        //   806: dup            
        //   807: iconst_4       
        //   808: lload           12
        //   810: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   813: aastore        
        //   814: dup            
        //   815: iconst_5       
        //   816: lload           14
        //   818: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   821: aastore        
        //   822: dup            
        //   823: bipush          6
        //   825: dload           16
        //   827: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   830: aastore        
        //   831: invokevirtual   java/io/PrintStream.printf:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
        //   834: pop            
        //   835: iload           6
        //   837: ireturn        
        //    Exceptions:
        //  throws java.io.IOException
        //  throws java.lang.SecurityException
        //  throws java.net.URISyntaxException
        //    Signature:
        //  (Ljava/lang/Class<*>;Lcom/jogamp/common/net/Uri;Lcom/jogamp/common/net/Uri$Encoded;Lcom/jogamp/common/net/Uri$Encoded;)Z
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  316    326    329    363    Ljava/lang/Exception;
        //  403    445    448    482    Ljava/lang/Exception;
        //  578    660    663    697    Ljava/lang/Exception;
        //  724    752    755    763    Any
        //  755    760    755    763    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 1, Size: 1
        //     at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        //     at java.util.ArrayList.get(ArrayList.java:429)
        //     at com.strobel.assembler.Collection.get(Collection.java:43)
        //     at java.util.Collections$UnmodifiableList.get(Collections.java:1309)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.adjustArgumentsForMethodCallCore(AstMethodBodyBuilder.java:1310)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.adjustArgumentsForMethodCall(AstMethodBodyBuilder.java:1283)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1195)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:717)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:437)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static final boolean addNativeJarLibsJoglCfg(final Class<?>[] array) {
        return addNativeJarLibs(array, "-all");
    }
    
    public static boolean addNativeJarLibs(final Class<?>[] array, final String s) {
        if (JNILibLoaderBase.DEBUG) {
            final StringBuilder sb = new StringBuilder();
            sb.append("JNILibLoaderBase: addNativeJarLibs(").append(PlatformPropsImpl.NEWLINE);
            sb.append("  classesFromJavaJars   = ").append(Arrays.asList(array)).append(PlatformPropsImpl.NEWLINE);
            sb.append("  singleJarMarker       = ").append(s).append(PlatformPropsImpl.NEWLINE);
            sb.append(")");
            System.err.println(sb.toString());
        }
        boolean addNativeJarLibsWithTempJarCache = false;
        if (TempJarCache.isInitialized()) {
            addNativeJarLibsWithTempJarCache = addNativeJarLibsWithTempJarCache(array, s);
        }
        else if (JNILibLoaderBase.DEBUG) {
            System.err.println("JNILibLoaderBase: addNativeJarLibs0: disabled due to uninitialized TempJarCache");
        }
        return addNativeJarLibsWithTempJarCache;
    }
    
    private static boolean addNativeJarLibsWithTempJarCache(final Class<?>[] array, final String s) {
        int n = 0;
        boolean addNativeJarLibsImpl;
        try {
            boolean b = false;
            addNativeJarLibsImpl = true;
            for (int i = 0; i < array.length; ++i) {
                final Class<?> clazz = array[i];
                if (clazz != null) {
                    final Uri jarUri = JarUtil.getJarUri(clazz.getName(), clazz.getClassLoader());
                    final Uri.Encoded jarBasename = JarUtil.getJarBasename(jarUri);
                    if (jarBasename != null) {
                        final Uri.Encoded substring = jarBasename.substring(0, jarBasename.indexOf(".jar"));
                        if (JNILibLoaderBase.DEBUG) {
                            System.err.printf("JNILibLoaderBase: jarBasename: %s%n", substring);
                        }
                        if (s != null && substring.indexOf(s) >= 0) {
                            b = true;
                        }
                        addNativeJarLibsImpl = addNativeJarLibsImpl(clazz, jarUri, jarBasename, Uri.Encoded.cast(String.format("%s-natives-%s.jar", substring.get(), PlatformPropsImpl.os_and_arch)));
                        if (addNativeJarLibsImpl) {
                            ++n;
                        }
                        if (JNILibLoaderBase.DEBUG && b) {
                            System.err.printf("JNILibLoaderBase: addNativeJarLibs0: done: %s%n", substring);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            System.err.printf("JNILibLoaderBase: Caught %s: %s%n", ex.getClass().getSimpleName(), ex.getMessage());
            if (JNILibLoaderBase.DEBUG) {
                ex.printStackTrace();
            }
            addNativeJarLibsImpl = false;
        }
        if (JNILibLoaderBase.DEBUG) {
            System.err.printf("JNILibLoaderBase: addNativeJarLibsWhenInitialized: count %d, ok %b%n", n, addNativeJarLibsImpl);
        }
        return addNativeJarLibsImpl;
    }
    
    protected static synchronized boolean loadLibrary(final String s, final boolean b, final ClassLoader classLoader) {
        return JNILibLoaderBase.loaderAction != null && JNILibLoaderBase.loaderAction.loadLibrary(s, b, classLoader);
    }
    
    protected static synchronized void loadLibrary(final String s, final String[] array, final boolean b, final ClassLoader classLoader) {
        if (JNILibLoaderBase.loaderAction != null) {
            JNILibLoaderBase.loaderAction.loadLibrary(s, array, b, classLoader);
        }
    }
    
    private static void loadLibraryInternal(final String s, final ClassLoader classLoader) {
        int n = 0;
        Label_0482: {
            if (null != JNILibLoaderBase.customLoadLibraryMethod && !s.equals("jawt")) {
                if (JNILibLoaderBase.DEBUG) {
                    System.err.println("JNILibLoaderBase: customLoad(" + s + ") - mode 1");
                }
                try {
                    JNILibLoaderBase.customLoadLibraryMethod.invoke(null, s);
                    n = 1;
                    break Label_0482;
                }
                catch (Exception ex) {
                    Throwable targetException = ex;
                    if (targetException instanceof InvocationTargetException) {
                        targetException = ((InvocationTargetException)targetException).getTargetException();
                    }
                    if (targetException instanceof Error) {
                        throw (Error)targetException;
                    }
                    if (targetException instanceof RuntimeException) {
                        throw (RuntimeException)targetException;
                    }
                    throw (UnsatisfiedLinkError)new UnsatisfiedLinkError("can not load library " + s).initCause(ex);
                }
            }
            final String library = NativeLibrary.findLibrary(s, classLoader);
            if (JNILibLoaderBase.DEBUG) {
                System.err.println("JNILibLoaderBase: loadLibraryInternal(" + s + "), TempJarCache: " + library);
            }
            if (null != library) {
                if (JNILibLoaderBase.DEBUG) {
                    System.err.println("JNILibLoaderBase: System.load(" + library + ") - mode 2");
                }
                System.load(library);
                n = 2;
            }
            else {
                if (JNILibLoaderBase.DEBUG) {
                    System.err.println("JNILibLoaderBase: System.loadLibrary(" + s + ") - mode 3");
                }
                try {
                    System.loadLibrary(s);
                    n = 3;
                }
                catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    if (JNILibLoaderBase.DEBUG) {
                        System.err.println("ERROR (retry w/ enumLibPath) - " + unsatisfiedLinkError.getMessage());
                    }
                    final Iterator<String> iterator = NativeLibrary.enumerateLibraryPaths(s, s, s, classLoader).iterator();
                    while (n == 0 && iterator.hasNext()) {
                        final String s2 = iterator.next();
                        if (JNILibLoaderBase.DEBUG) {
                            System.err.println("JNILibLoaderBase: System.load(" + s2 + ") - mode 4");
                        }
                        try {
                            System.load(s2);
                            n = 4;
                        }
                        catch (UnsatisfiedLinkError unsatisfiedLinkError2) {
                            if (JNILibLoaderBase.DEBUG) {
                                System.err.println("n/a - " + unsatisfiedLinkError2.getMessage());
                            }
                            if (!iterator.hasNext()) {
                                throw unsatisfiedLinkError2;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        if (JNILibLoaderBase.DEBUG) {
            System.err.println("JNILibLoaderBase: loadLibraryInternal(" + s + "): OK - mode " + n);
        }
    }
    
    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("JNILibLoader");
        PERF = (JNILibLoaderBase.DEBUG || PropertyAccess.isPropertyDefined("jogamp.debug.JNILibLoader.Perf", true));
        perfSync = new Object();
        JNILibLoaderBase.perfTotal = 0L;
        JNILibLoaderBase.perfCount = 0L;
        loaded = new HashSet<String>();
        JNILibLoaderBase.loaderAction = new DefaultAction();
        customLoadLibraryMethod = AccessController.doPrivileged((PrivilegedAction<Method>)new PrivilegedAction<Method>() {
            @Override
            public Method run() {
                final boolean booleanProperty = PropertyAccess.getBooleanProperty("sun.jnlp.applet.launcher", true);
                Class<?> forName = null;
                Method method = null;
                if (booleanProperty) {
                    try {
                        forName = Class.forName("org.jdesktop.applet.util.JNLPAppletLauncher");
                    }
                    catch (ClassNotFoundException ex4) {
                        System.err.println("JNILibLoaderBase: <org.jdesktop.applet.util.JNLPAppletLauncher> not found, despite enabled property <sun.jnlp.applet.launcher>, JNLPAppletLauncher was probably used before");
                        System.setProperty("sun.jnlp.applet.launcher", Boolean.FALSE.toString());
                    }
                    catch (LinkageError linkageError) {
                        throw linkageError;
                    }
                    if (null != forName) {
                        try {
                            method = forName.getDeclaredMethod("loadLibrary", String.class);
                        }
                        catch (NoSuchMethodException ex) {
                            if (JNILibLoaderBase.DEBUG) {
                                ex.printStackTrace();
                            }
                            forName = null;
                        }
                    }
                }
                if (null == forName) {
                    final String property = PropertyAccess.getProperty("jnlp.launcher.class", false);
                    if (null != property) {
                        try {
                            method = Class.forName(property).getDeclaredMethod("loadLibrary", String.class);
                        }
                        catch (ClassNotFoundException ex2) {
                            if (JNILibLoaderBase.DEBUG) {
                                ex2.printStackTrace();
                            }
                        }
                        catch (NoSuchMethodException ex3) {
                            if (JNILibLoaderBase.DEBUG) {
                                ex3.printStackTrace();
                            }
                        }
                    }
                }
                return method;
            }
        });
    }
    
    private static class DefaultAction implements LoaderAction
    {
        @Override
        public boolean loadLibrary(final String s, final boolean b, final ClassLoader classLoader) {
            boolean b2 = true;
            if (!JNILibLoaderBase.isLoaded(s)) {
                try {
                    loadLibraryInternal(s, classLoader);
                    JNILibLoaderBase.addLoaded(s);
                    if (JNILibLoaderBase.DEBUG) {
                        System.err.println("JNILibLoaderBase: loaded " + s);
                    }
                }
                catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    b2 = false;
                    if (JNILibLoaderBase.DEBUG) {
                        unsatisfiedLinkError.printStackTrace();
                    }
                    if (!b && unsatisfiedLinkError.getMessage().indexOf("already loaded") < 0) {
                        throw unsatisfiedLinkError;
                    }
                }
            }
            return b2;
        }
        
        @Override
        public void loadLibrary(final String s, final String[] array, final boolean b, final ClassLoader classLoader) {
            if (!JNILibLoaderBase.isLoaded(s)) {
                if (null != array) {
                    for (int i = 0; i < array.length; ++i) {
                        this.loadLibrary(array[i], b, classLoader);
                    }
                }
                this.loadLibrary(s, false, classLoader);
            }
        }
    }
    
    public interface LoaderAction
    {
        boolean loadLibrary(final String p0, final boolean p1, final ClassLoader p2);
        
        void loadLibrary(final String p0, final String[] p1, final boolean p2, final ClassLoader p3);
    }
}
