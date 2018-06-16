// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.util.RunnableExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.security.AccessController;
import com.jogamp.common.os.DynamicLibraryBundle;
import java.security.PrivilegedAction;
import com.jogamp.common.util.VersionNumber;
import java.util.List;
import com.jogamp.common.os.DynamicLibraryBundleInfo;

class FFMPEGDynamicLibraryBundleInfo implements DynamicLibraryBundleInfo
{
    private static final boolean DEBUG;
    private static final List<String> glueLibNames;
    private static final int symbolCount = 65;
    private static final String[] symbolNames;
    private static final String[] optionalSymbolNames;
    private static final boolean[] libLoaded;
    private static final long[] symbolAddr;
    private static final boolean ready;
    private static final boolean libsUFCLoaded;
    static final VersionNumber avCodecVersion;
    static final VersionNumber avFormatVersion;
    static final VersionNumber avUtilVersion;
    static final VersionNumber avResampleVersion;
    static final VersionNumber swResampleVersion;
    private static final FFMPEGNatives natives;
    private static final int LIB_IDX_UTI = 0;
    private static final int LIB_IDX_FMT = 1;
    private static final int LIB_IDX_COD = 2;
    private static final int LIB_IDX_DEV = 3;
    private static final int LIB_IDX_AVR = 4;
    private static final int LIB_IDX_SWR = 5;
    private static final PrivilegedAction<DynamicLibraryBundle> privInitSymbolsAction;
    
    private static final boolean initSymbols(final VersionNumber[] array) {
        for (int i = 0; i < 6; ++i) {
            FFMPEGDynamicLibraryBundleInfo.libLoaded[i] = false;
        }
        if (FFMPEGDynamicLibraryBundleInfo.symbolNames.length != 65) {
            throw new InternalError("XXX0 " + FFMPEGDynamicLibraryBundleInfo.symbolNames.length + " != " + 65);
        }
        if (null == AccessController.doPrivileged(FFMPEGDynamicLibraryBundleInfo.privInitSymbolsAction)) {
            return false;
        }
        final HashSet set = new HashSet();
        set.addAll(Arrays.asList(FFMPEGDynamicLibraryBundleInfo.optionalSymbolNames));
        boolean b = true;
        for (int j = 0; j < 65; ++j) {
            if (0L == FFMPEGDynamicLibraryBundleInfo.symbolAddr[j]) {
                if (!set.contains(FFMPEGDynamicLibraryBundleInfo.symbolNames[j])) {
                    System.err.println("Fail: Could not resolve symbol <" + FFMPEGDynamicLibraryBundleInfo.symbolNames[j] + ">: not optional, no alternatives.");
                    b = false;
                }
                else if (FFMPEGDynamicLibraryBundleInfo.DEBUG) {
                    System.err.println("OK: Unresolved optional symbol <" + FFMPEGDynamicLibraryBundleInfo.symbolNames[j] + ">");
                }
            }
        }
        array[0] = FFMPEGStaticNatives.getAVVersion(FFMPEGStaticNatives.getAvVersion0(FFMPEGDynamicLibraryBundleInfo.symbolAddr[0]));
        array[1] = FFMPEGStaticNatives.getAVVersion(FFMPEGStaticNatives.getAvVersion0(FFMPEGDynamicLibraryBundleInfo.symbolAddr[1]));
        array[2] = FFMPEGStaticNatives.getAVVersion(FFMPEGStaticNatives.getAvVersion0(FFMPEGDynamicLibraryBundleInfo.symbolAddr[2]));
        array[3] = FFMPEGStaticNatives.getAVVersion(FFMPEGStaticNatives.getAvVersion0(FFMPEGDynamicLibraryBundleInfo.symbolAddr[3]));
        array[4] = FFMPEGStaticNatives.getAVVersion(FFMPEGStaticNatives.getAvVersion0(FFMPEGDynamicLibraryBundleInfo.symbolAddr[4]));
        return b;
    }
    
    static boolean libsLoaded() {
        return FFMPEGDynamicLibraryBundleInfo.libsUFCLoaded;
    }
    
    static boolean avDeviceLoaded() {
        return FFMPEGDynamicLibraryBundleInfo.libLoaded[3];
    }
    
    static boolean avResampleLoaded() {
        return FFMPEGDynamicLibraryBundleInfo.libLoaded[4];
    }
    
    static boolean swResampleLoaded() {
        return FFMPEGDynamicLibraryBundleInfo.libLoaded[5];
    }
    
    static FFMPEGNatives getNatives() {
        return FFMPEGDynamicLibraryBundleInfo.natives;
    }
    
    static boolean initSingleton() {
        return FFMPEGDynamicLibraryBundleInfo.ready;
    }
    
    @Override
    public final boolean shallLinkGlobal() {
        return true;
    }
    
    @Override
    public final boolean shallLookupGlobal() {
        return true;
    }
    
    @Override
    public final List<String> getGlueLibNames() {
        return FFMPEGDynamicLibraryBundleInfo.glueLibNames;
    }
    
    @Override
    public final List<List<String>> getToolLibNames() {
        final ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)new ArrayList<List<String>>();
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("avutil");
        list2.add("libavutil.so.55");
        list2.add("libavutil.so.54");
        list2.add("libavutil.so.53");
        list2.add("libavutil.so.52");
        list2.add("libavutil.so.51");
        list2.add("libavutil.so.50");
        list2.add("avutil-55");
        list2.add("avutil-54");
        list2.add("avutil-53");
        list2.add("avutil-52");
        list2.add("avutil-51");
        list2.add("avutil-50");
        list.add(list2);
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("avformat");
        list3.add("libavformat.so.57");
        list3.add("libavformat.so.56");
        list3.add("libavformat.so.55");
        list3.add("libavformat.so.54");
        list3.add("libavformat.so.53");
        list3.add("libavformat.so.52");
        list3.add("avformat-57");
        list3.add("avformat-56");
        list3.add("avformat-55");
        list3.add("avformat-54");
        list3.add("avformat-53");
        list3.add("avformat-52");
        list.add(list3);
        final ArrayList<String> list4 = new ArrayList<String>();
        list4.add("avcodec");
        list4.add("libavcodec.so.57");
        list4.add("libavcodec.so.56");
        list4.add("libavcodec.so.55");
        list4.add("libavcodec.so.54");
        list4.add("libavcodec.so.53");
        list4.add("libavcodec.so.52");
        list4.add("avcodec-57");
        list4.add("avcodec-56");
        list4.add("avcodec-55");
        list4.add("avcodec-54");
        list4.add("avcodec-53");
        list4.add("avcodec-52");
        list.add(list4);
        final ArrayList<String> list5 = new ArrayList<String>();
        list5.add("avdevice");
        list5.add("libavdevice.so.57");
        list5.add("libavdevice.so.56");
        list5.add("libavdevice.so.55");
        list5.add("libavdevice.so.54");
        list5.add("libavdevice.so.53");
        list5.add("avdevice-57");
        list5.add("avdevice-56");
        list5.add("avdevice-55");
        list5.add("avdevice-54");
        list5.add("avdevice-53");
        list.add(list5);
        final ArrayList<String> list6 = new ArrayList<String>();
        list6.add("avresample");
        list6.add("libavresample.so.3");
        list6.add("libavresample.so.2");
        list6.add("libavresample.so.1");
        list6.add("avresample-3");
        list6.add("avresample-2");
        list6.add("avresample-1");
        list.add(list6);
        final ArrayList<String> list7 = new ArrayList<String>();
        list7.add("swresample");
        list7.add("libswresample.so.2");
        list7.add("libswresample.so.1");
        list7.add("libswresample.so.0");
        list7.add("swresample-2");
        list7.add("swresample-1");
        list7.add("swresample-0");
        list.add(list7);
        return (List<List<String>>)list;
    }
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        return null;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return 0L;
    }
    
    @Override
    public final boolean useToolGetProcAdressFirst(final String s) {
        return false;
    }
    
    @Override
    public final RunnableExecutor getLibLoaderExecutor() {
        return DynamicLibraryBundle.getDefaultRunnableExecutor();
    }
    
    static {
        DEBUG = (FFMPEGMediaPlayer.DEBUG || DynamicLibraryBundleInfo.DEBUG);
        glueLibNames = new ArrayList<String>();
        symbolNames = new String[] { "avutil_version", "avformat_version", "avcodec_version", "avresample_version", "swresample_version", "avcodec_register_all", "avcodec_close", "avcodec_string", "avcodec_find_decoder", "avcodec_open2", "avcodec_alloc_frame", "avcodec_get_frame_defaults", "avcodec_free_frame", "avcodec_default_get_buffer", "avcodec_default_release_buffer", "avcodec_default_get_buffer2", "avcodec_get_edge_width", "av_image_fill_linesizes", "avcodec_align_dimensions", "avcodec_align_dimensions2", "avcodec_flush_buffers", "av_init_packet", "av_new_packet", "av_destruct_packet", "av_free_packet", "avcodec_decode_audio4", "avcodec_decode_video2", "av_pix_fmt_descriptors", "av_frame_unref", "av_realloc", "av_free", "av_get_bits_per_pixel", "av_samples_get_buffer_size", "av_get_bytes_per_sample", "av_opt_set_int", "av_dict_get", "av_dict_count", "av_dict_set", "av_dict_free", "avformat_alloc_context", "avformat_free_context", "avformat_close_input", "av_register_all", "av_find_input_format", "avformat_open_input", "av_dump_format", "av_read_frame", "av_seek_frame", "avformat_seek_file", "av_read_play", "av_read_pause", "avformat_network_init", "avformat_network_deinit", "avformat_find_stream_info", "avdevice_register_all", "avresample_alloc_context", "avresample_open", "avresample_close", "avresample_free", "avresample_convert", "av_opt_set_sample_fmt", "swr_alloc", "swr_init", "swr_free", "swr_convert" };
        optionalSymbolNames = new String[] { "avformat_seek_file", "avcodec_free_frame", "av_frame_unref", "av_dict_count", "avcodec_default_get_buffer", "avcodec_default_release_buffer", "avcodec_default_get_buffer2", "avdevice_register_all", "avresample_version", "avresample_alloc_context", "avresample_open", "avresample_close", "avresample_free", "avresample_convert", "av_opt_set_sample_fmt", "swresample_version", "swr_alloc", "swr_init", "swr_free", "swr_convert" };
        libLoaded = new boolean[6];
        symbolAddr = new long[65];
        privInitSymbolsAction = new PrivilegedAction<DynamicLibraryBundle>() {
            @Override
            public DynamicLibraryBundle run() {
                final DynamicLibraryBundle dynamicLibraryBundle = new DynamicLibraryBundle(new FFMPEGDynamicLibraryBundleInfo());
                for (int i = 0; i < 6; ++i) {
                    FFMPEGDynamicLibraryBundleInfo.libLoaded[i] = dynamicLibraryBundle.isToolLibLoaded(i);
                }
                if (!FFMPEGDynamicLibraryBundleInfo.libLoaded[0] || !FFMPEGDynamicLibraryBundleInfo.libLoaded[1] || !FFMPEGDynamicLibraryBundleInfo.libLoaded[2]) {
                    System.err.println("FFMPEG Tool library incomplete: [ avutil " + FFMPEGDynamicLibraryBundleInfo.libLoaded[0] + ", avformat " + FFMPEGDynamicLibraryBundleInfo.libLoaded[1] + ", avcodec " + FFMPEGDynamicLibraryBundleInfo.libLoaded[2] + "]");
                    return null;
                }
                dynamicLibraryBundle.claimAllLinkPermission();
                try {
                    for (int j = 0; j < 65; ++j) {
                        FFMPEGDynamicLibraryBundleInfo.symbolAddr[j] = dynamicLibraryBundle.dynamicLookupFunction(FFMPEGDynamicLibraryBundleInfo.symbolNames[j]);
                    }
                }
                finally {
                    dynamicLibraryBundle.releaseAllLinkPermission();
                }
                return dynamicLibraryBundle;
            }
        };
        GLProfile.initSingleton();
        boolean initSymbols = false;
        final VersionNumber[] array = new VersionNumber[5];
        try {
            initSymbols = initSymbols(array);
        }
        catch (Throwable t) {
            ExceptionUtils.dumpThrowable("", t);
        }
        libsUFCLoaded = (FFMPEGDynamicLibraryBundleInfo.libLoaded[0] && FFMPEGDynamicLibraryBundleInfo.libLoaded[1] && FFMPEGDynamicLibraryBundleInfo.libLoaded[2]);
        avUtilVersion = array[0];
        avFormatVersion = array[1];
        avCodecVersion = array[2];
        avResampleVersion = array[3];
        swResampleVersion = array[4];
        if (!FFMPEGDynamicLibraryBundleInfo.libsUFCLoaded) {
            System.err.println("LIB_AV Not Available: lavu, lavc, lavu");
            natives = null;
            ready = false;
        }
        else if (!initSymbols) {
            System.err.println("LIB_AV Not Matching");
            natives = null;
            ready = false;
        }
        else {
            final int major = FFMPEGDynamicLibraryBundleInfo.avCodecVersion.getMajor();
            final int major2 = FFMPEGDynamicLibraryBundleInfo.avFormatVersion.getMajor();
            final int major3 = FFMPEGDynamicLibraryBundleInfo.avUtilVersion.getMajor();
            if (major == 53 && major2 == 53 && major3 == 51) {
                natives = new FFMPEGv08Natives();
            }
            else if (major == 54 && major2 == 54 && major3 == 52) {
                natives = new FFMPEGv09Natives();
            }
            else if (major == 55 && major2 == 55 && (major3 == 52 || major3 == 53)) {
                natives = new FFMPEGv10Natives();
            }
            else if (major == 56 && major2 == 56 && major3 == 54) {
                natives = new FFMPEGv11Natives();
            }
            else {
                System.err.println("LIB_AV No Version/Native-Impl Match");
                natives = null;
            }
            if (null != FFMPEGDynamicLibraryBundleInfo.natives && FFMPEGStaticNatives.initIDs0()) {
                ready = FFMPEGDynamicLibraryBundleInfo.natives.initSymbols0(FFMPEGDynamicLibraryBundleInfo.symbolAddr, 65);
            }
            else {
                ready = false;
            }
        }
    }
}
