// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageType
{
    public static final int MAGIC_MAX_SIZE = 25;
    public static final String T_JPG = "jpg";
    public static final String T_PNG = "png";
    public static final String T_ICNS = "icns";
    public static final String T_ICO = "ico";
    public static final String T_CUR = "cur";
    public static final String T_GIF = "gif";
    public static final String T_BMP = "bmp";
    public static final String T_DIB = "dib";
    public static final String T_DCX = "dcx";
    public static final String T_PCX = "pcx";
    public static final String T_PPM = "ppm";
    public static final String T_PSD = "psd";
    public static final String T_TIFF = "tiff";
    public static final String T_LDF = "ldf";
    public static final String T_SGI_RGB = "rgb";
    public static final String T_DDS = "dds";
    public static final String T_PAM = "pam";
    public static final String T_PGM = "pgm";
    public static final String T_PBM = "pbm";
    public static final String T_3D2 = "3d2";
    public static final String T_3DMF = "3dmf";
    public static final String T_92I = "92i";
    public static final String T_AMFF = "amff";
    public static final String T_ART = "art";
    public static final String T_CALS = "cals";
    public static final String T_CAM = "cam";
    public static final String T_CBD = "cbd";
    public static final String T_CE2 = "ce2";
    public static final String T_CIN = "cin";
    public static final String T_COB = "cob";
    public static final String T_CPT = "cpt";
    public static final String T_CVG = "cvg";
    public static final String T_DEM = "dem";
    public static final String T_DPX = "dpx";
    public static final String T_DRW = "drw";
    public static final String T_DWG = "dwg";
    public static final String T_ECW = "ecw";
    public static final String T_EMF = "emf";
    public static final String T_FPX = "fpx";
    public static final String T_FTS = "fts";
    public static final String T_GRO = "gro";
    public static final String T_HDR = "hdr";
    public static final String T_HRU = "hru";
    public static final String T_IMG = "img";
    public static final String T_INFINI_D = "infini-d";
    public static final String T_IWC = "iwc";
    public static final String T_J6I = "j6i";
    public static final String T_JIF = "jif";
    public static final String T_JP2 = "jp2";
    public static final String T_KDC = "kdc";
    public static final String T_L64 = "l64";
    public static final String T_LBM = "lbm";
    public static final String T_RAD = "rad";
    public static final String T_LWF = "lwf";
    public static final String T_MBM = "mbm";
    public static final String T_MGL = "mgl";
    public static final String T_MIF = "mif";
    public static final String T_MNG = "mng";
    public static final String T_MPW = "mpw";
    public static final String T_MSP = "msp";
    public static final String T_N64 = "n64";
    public static final String T_NCR = "ncr";
    public static final String T_NFF = "nff";
    public static final String T_NGG = "ngg";
    public static final String T_NLM = "nlm";
    public static final String T_NOL = "nol";
    public static final String T_PAL = "pal";
    public static final String T_PAX = "pax";
    public static final String T_PCD = "pcd";
    public static final String T_PCL = "pcl";
    public static final String T_PIC = "pic";
    public static final String T_PIX = "pix";
    public static final String T_POL = "pol";
    public static final String T_PSP = "psp";
    public static final String T_QFX = "qfx";
    public static final String T_QTM = "qtm";
    public static final String T_RAS = "ras";
    public static final String T_RIX = "rix";
    public static final String T_SID = "sid";
    public static final String T_SLD = "sld";
    public static final String T_SOD = "sod";
    public static final String T_WIC = "wic";
    public static final String T_WLM = "wlm";
    public static final String T_WMF = "wmf";
    public static final String T_WPG = "wpg";
    public static final String T_WRL = "wrl";
    public static final String T_XBM = "xbm";
    public static final String T_XPM = "xpm";
    public static final String T_TGA = "tga";
    public final String type;
    public final byte[] header;
    private final int hash;
    
    public ImageType(final InputStream inputStream) throws IOException {
        final byte[] header = new byte[25];
        this.type = Util.getFileSuffix(inputStream, header);
        this.header = header;
        this.hash = ((null != this.type) ? this.type.hashCode() : 0);
    }
    
    public ImageType(final String type) {
        this.header = null;
        this.type = type;
        this.hash = this.type.hashCode();
    }
    
    public final boolean isDefined() {
        return null != this.type;
    }
    
    @Override
    public final int hashCode() {
        return this.hash;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof ImageType && this.type.equals(((ImageType)o).type));
    }
    
    @Override
    public String toString() {
        return "ImageType[" + this.type + "]";
    }
    
    public static class Util
    {
        public static String getFileSuffix(final InputStream inputStream) throws IOException {
            return getFileSuffix(inputStream, new byte[25]);
        }
        
        public static String getFileSuffix(InputStream inputStream, final byte[] array) throws IOException {
            if (inputStream == null) {
                throw new IOException("Stream was null");
            }
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            if (!inputStream.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (inputStream.available() < 25) {
                throw new IOException("Requires 25 bytes, has " + inputStream.available() + " bytes");
            }
            try {
                inputStream.mark(25);
                final int read = inputStream.read(array);
                if (25 > read) {
                    throw new IOException("Could not read 25 bytes, read " + read + " bytes");
                }
                return getFileSuffix(array);
            }
            finally {
                inputStream.reset();
            }
        }
        
        public static String getFileSuffix(final byte[] array) {
            if (array.length < 25) {
                throw new IllegalArgumentException("byte array must be >= 25, has " + array.length);
            }
            final byte b = array[0];
            final byte b2 = array[1];
            final byte b3 = array[2];
            final byte b4 = array[3];
            final byte b5 = array[4];
            final byte b6 = array[5];
            if (b == 0) {
                if (b2 == 0 && b3 == 0 && b4 == 12 && b5 == 106 && b6 == 80 && array[6] == 32 && array[7] == 32 && array[8] == 13 && array[9] == 10 && array[10] == -121 && array[11] == 10) {
                    return "jp2";
                }
                if (b2 == 1) {
                    return "ico";
                }
                if (b2 == 2) {
                    return "cur";
                }
            }
            else if (b == 1) {
                if (b2 == -38) {
                    return "rgb";
                }
                if (b2 == -1 && b3 == 2 && b4 == 4 && b5 == 3 && b6 == 2) {
                    return "drw";
                }
                if (b2 == 0 && b3 == 0 && b4 == 0 && b5 == 88 && b6 == 0 && array[6] == 0 && array[7] == 0) {
                    return "emf";
                }
            }
            else {
                if (b == 7 && b2 == 32 && b3 == 77 && b4 == 77) {
                    return "cam";
                }
                if (b == 10 && b2 == 5 && b3 == 1 && b4 == 8) {
                    return "pcx";
                }
                if (b == 27 && b2 == 69 && b3 == 27 && b4 == 38 && b5 == 108 && b6 == 48 && array[6] == 79 && array[7] == 27 && array[8] == 38 && array[9] == 108 && array[10] == 48 && array[11] == 69 && array[12] == 27 && array[13] == 38) {
                    return "pcl";
                }
                if (b == 32 && b2 == 119 && b3 == 0 && b4 == 2) {
                    return "cbd";
                }
                if (b == 35) {
                    if (b2 == 32 && b3 == 36 && b4 == 73 && b5 == 100 && b6 == 58 && array[6] == 32) {
                        return "sid";
                    }
                    if (b2 == 86 && b3 == 82 && b4 == 77 && b5 == 76 && b6 == 32 && array[6] == 86 && array[7] == 50 && array[8] == 46 && array[9] == 48) {
                        return "wrl";
                    }
                    if (b2 == 100 && b3 == 101 && b4 == 102 && b5 == 105 && b6 == 110 && array[6] == 101) {
                        return "xbm";
                    }
                }
                else {
                    if (b == 42 && b2 == 42 && b3 == 84 && b4 == 73 && b5 == 57 && b6 == 50 && array[6] == 42 && array[7] == 42 && array[8] == 1 && array[9] == 0 && array[10] == 88 && array[11] == 110 && array[12] == 86 && array[13] == 105) {
                        return "92i";
                    }
                    if (b == 47 && b2 == 42 && b3 == 32 && b4 == 88 && b5 == 80 && b6 == 77 && array[6] == 32 && array[7] == 42 && array[8] == 47) {
                        return "xpm";
                    }
                    if (b == 51 && b2 == 68 && b3 == 77 && b4 == 70) {
                        return "3dmf";
                    }
                    if (b == 53 && b2 == 75 && b3 == 80 && b4 == 53 && b5 == 49 && b6 == 93 && array[6] == 42 && array[7] == 103 && array[8] == 114 && array[9] == 114 && array[10] == -128 && array[11] == -125 && array[12] == -123 && array[13] == 99) {
                        return "hru";
                    }
                    if (b == 54 && b2 == 52 && b3 == 76 && b4 == 65 && b5 == 78 && b6 == 32 && array[6] == 73 && array[7] == 68 && array[8] == 66 && array[9] == 76 && array[10] == 79 && array[11] == 67 && array[12] == 75) {
                        return "l64";
                    }
                    if (b == 55 && b2 == 0 && b3 == 0 && b4 == 16 && b5 == 66 && b6 == 0 && array[6] == 0 && array[7] == 16 && array[8] == 0 && array[9] == 0 && array[10] == 0 && array[11] == 0 && array[12] == 57 && array[13] == 100) {
                        return "mbm";
                    }
                    if (b == 56 && b2 == 66 && b3 == 80 && b4 == 83 && b5 == 0 && b6 == 1 && array[6] == 0 && array[7] == 0 && array[8] == 0 && array[9] == 0) {
                        return "psd";
                    }
                    if (b == 58 && b2 == -34 && b3 == 104 && b4 == -79) {
                        return "dcx";
                    }
                    if (b == 61 && b2 == 2) {
                        return "3d2";
                    }
                    if (b == 65) {
                        if (b2 == 67 && b3 == 49 && b4 == 48) {
                            return "dwg";
                        }
                        if (b2 == 72) {
                            return "pal";
                        }
                        if (b2 == 77 && b3 == 70 && b4 == 70) {
                            return "amff";
                        }
                        if (b2 == 117 && b3 == 116 && b4 == 111 && b5 == 67 && b6 == 65 && array[6] == 68 && array[7] == 32 && array[8] == 83 && array[9] == 108 && array[10] == 105 && array[11] == 100 && array[12] == 101) {
                            return "sld";
                        }
                    }
                    else if (b == 66 && b2 == 77) {
                        if (b3 == 54) {
                            return "dib";
                        }
                        return "bmp";
                    }
                    else if (b == 67) {
                        if (b2 == 54 && b3 == 52) {
                            return "n64";
                        }
                        if (b2 == 65 && b3 == 76 && b4 == 65 && b5 == 77 && b6 == 85 && array[6] == 83 && array[7] == 67 && array[8] == 86 && array[9] == 71) {
                            return "cvg";
                        }
                        if (b2 == 80 && b3 == 84 && b4 == 70 && b5 == 73 && b6 == 76 && array[6] == 69) {
                            return "cpt";
                        }
                        if (b2 == 97 && b3 == 108 && b4 == 105 && b5 == 103 && b6 == 97 && array[6] == 114 && array[7] == 105) {
                            return "cob";
                        }
                    }
                    else if (b == 68) {
                        if (b2 == 68 && b3 == 83 && b4 == 32) {
                            return "dds";
                        }
                        if (b2 == 97 && b3 == 110 && b4 == 77) {
                            return "msp";
                        }
                    }
                    else if (b == 69) {
                        if (b2 == 89 && b3 == 69 && b4 == 83) {
                            return "ce2";
                        }
                        if (b2 == 120 && b3 == 105 && b4 == 102) {
                            return "jpg";
                        }
                    }
                    else if (b == 70 && b2 == 79 && b3 == 82 && b4 == 77) {
                        if (b5 == 65 && b6 == 84 && array[6] == 61) {
                            return "rad";
                        }
                        return "lbm";
                    }
                    else {
                        if (b == 71 && b2 == 73 && b3 == 70 && b4 == 56 && (b5 == 55 || b5 == 57) && b6 == 97) {
                            return "gif";
                        }
                        if (b == 72 && b2 == 80 && b3 == 72 && b4 == 80 && b5 == 52 && b6 == 56 && array[6] == 45 && array[7] == 69 && array[8] == 30 && array[9] == 43) {
                            return "gro";
                        }
                        if (b == 73) {
                            if (b2 == 73 && b3 == 42 && b4 == 0) {
                                if (b5 == 8 && b6 == 0 && array[6] == 0 && array[7] == 0 && array[8] == 14 && array[9] == 0 && array[10] == 0 && array[11] == 1 && array[12] == 4 && array[13] == 0) {
                                    return "ldf";
                                }
                                return "tiff";
                            }
                            else if (b2 == 87 && b3 == 67 && b4 == 1) {
                                return "iwc";
                            }
                        }
                        else if (b == 74) {
                            if (b2 == 70 && b3 == 73 && b4 == 70) {
                                return "jpg";
                            }
                            if (b2 == 71 && (b3 == 3 || b3 == 4) && b4 == 14 && b5 == 0 && b6 == 0 && array[6] == 0) {
                                return "art";
                            }
                            if (b2 == 73 && b3 == 70 && b4 == 57 && b5 == 57 && b6 == 97) {
                                return "jif";
                            }
                        }
                        else if (b == 77) {
                            if (b2 == 71 && b3 == 76) {
                                return "mgl";
                            }
                            if (b2 == 77 && b3 == 0 && b4 == 42) {
                                return "kdc";
                            }
                            if (b2 == 80 && b3 == 70) {
                                return "mpw";
                            }
                        }
                        else if (b == 78) {
                            if (b2 == 71 && b3 == 71 && b4 == 0 && b5 == 1 && b6 == 0) {
                                return "ngg";
                            }
                            if (b2 == 76 && b3 == 77 && b4 == 32 && b5 == 1 && b6 == 2 && array[6] == 0) {
                                return "nlm";
                            }
                            if (b2 == 79 && b3 == 76 && b4 == 0 && b5 == 1 && b6 == 0 && array[6] == 6 && array[7] == 1 && array[8] == 3 && array[9] == 0) {
                                return "nol";
                            }
                        }
                        else if (b == 80) {
                            if (b2 == 49 || b2 == 52) {
                                return "pbm";
                            }
                            if (b2 == 50 || b2 == 53) {
                                return "pgm";
                            }
                            if (b2 == 51 || b2 == 54) {
                                return "ppm";
                            }
                            if (b2 == 55) {
                                return "pam";
                            }
                            if (b2 == 65 && b3 == 88) {
                                return "pax";
                            }
                            if (b2 == 73 && b3 == 88 && b4 == 32) {
                                return "pix";
                            }
                            if (b2 == 79 && b3 == 76 && b4 == 32 && b5 == 70 && b6 == 111 && array[6] == 114 && array[7] == 109 && array[8] == 97 && array[9] == 116) {
                                return "pol";
                            }
                            if (b2 == 97 && b3 == 105 && b4 == 110 && b5 == 116 && b6 == 32 && array[6] == 83 && array[7] == 104 && array[8] == 111 && array[9] == 112 && array[10] == 32 && array[11] == 80 && array[12] == 114 && array[13] == 111 && array[14] == 32 && array[15] == 73 && array[16] == 109 && array[17] == 97 && array[18] == 103 && array[19] == 101 && array[20] == 32 && array[21] == 70 && array[22] == 105 && array[23] == 108 && array[24] == 101) {
                                return "psp";
                            }
                        }
                        else {
                            if (b == 81 && b2 == 76 && b3 == 73 && b4 == 73 && b5 == 70 && b6 == 65 && array[6] == 88) {
                                return "qfx";
                            }
                            if (b == 82 && b2 == 73 && b3 == 88 && b4 == 51) {
                                return "rix";
                            }
                            if (b == 83) {
                                if (b2 == 68 && b3 == 80 && b4 == 88) {
                                    return "dpx";
                                }
                                if (b2 == 73 && b3 == 77 && b4 == 80 && b5 == 76 && b6 == 69 && array[6] == 32 && array[7] == 32 && array[8] == 61) {
                                    return "fts";
                                }
                                if (b2 == 116 && b3 == 111 && b4 == 114 && b5 == 109 && b6 == 51 && array[6] == 68) {
                                    return "sod";
                                }
                                if (b2 == -128 && b3 == -10 && b4 == 52) {
                                    return "pic";
                                }
                            }
                            else {
                                if (b == 86 && b2 == 105 && b3 == 115 && b4 == 116 && b5 == 97 && b6 == 32 && array[6] == 68 && array[7] == 69 && array[8] == 77 && array[9] == 32 && array[10] == 70 && array[11] == 105 && array[12] == 108 && array[13] == 101) {
                                    return "dem";
                                }
                                if (b == 87 && b2 == 86 && b3 == 2 && b4 == 0 && b5 == 71 && b6 == 69 && array[6] == 0 && array[7] == 14) {
                                    return "lwf";
                                }
                                if (b == 89 && b2 == -90 && b3 == 106 && b4 == -107) {
                                    return "ras";
                                }
                                if (b == 99 && b2 == 82 && b3 == 1 && b4 == 1 && b5 == 56 && b6 == 9 && array[6] == 61 && array[7] == 0) {
                                    return "pcd";
                                }
                                if (b == 101) {
                                    if (b2 == 2 && b3 == 1 && b4 == 2) {
                                        return "ecw";
                                    }
                                    if (b2 == 108 && b3 == 109 && b4 == 111) {
                                        return "infini-d";
                                    }
                                }
                                else {
                                    if (b == 105 && b2 == 99 && b3 == 110 && b4 == 115) {
                                        return "icns";
                                    }
                                    if (b == 109 && b2 == 111 && b3 == 111 && b4 == 118) {
                                        return "qtm";
                                    }
                                    if (b == 110) {
                                        if (b2 == 99 && b3 == 111 && b4 == 108 && b5 == 115) {
                                            return "hdr";
                                        }
                                        if (b2 == 102 && b3 == 102) {
                                            return "nff";
                                        }
                                        if (b2 == 110 && b3 == 10 && b4 == 0 && b5 == 94 && b6 == 0) {
                                            return "ncr";
                                        }
                                    }
                                    else {
                                        if (b == 115 && b2 == 114 && b3 == 99 && b4 == 100 && b5 == 111 && b6 == 99 && array[6] == 105 && array[7] == 100 && array[8] == 58) {
                                            return "cals";
                                        }
                                        if (b == 123 && b2 == 10 && b3 == 32 && b4 == 32 && b5 == 67 && b6 == 114 && array[6] == 101 && array[7] == 97 && array[8] == 116 && array[9] == 101 && array[10] == 100) {
                                            return "mif";
                                        }
                                        if (b == 126 && b2 == 66 && b3 == 75 && b4 == 0) {
                                            return "psp";
                                        }
                                        if (b == -128) {
                                            if (b2 == 42 && b3 == 95 && b4 == -41 && b5 == 0 && b6 == 0 && array[6] == 8 && array[7] == 0 && array[8] == 0 && array[9] == 0 && array[10] == 4 && array[11] == 0 && array[12] == 0 && array[13] == 0) {
                                                return "cin";
                                            }
                                            if (b2 == 62 && b3 == 68 && b4 == 83 && b5 == 67 && b6 == 73 && array[6] == 77) {
                                                return "j6i";
                                            }
                                        }
                                        else {
                                            if (b == -119 && b2 == 80 && b3 == 78 && b4 == 71 && b5 == 13 && b6 == 10 && array[6] == 26 && array[7] == 10) {
                                                return "png";
                                            }
                                            if (b == -118 && b2 == 77 && b3 == 78 && b4 == 71 && b5 == 13 && b6 == 10 && array[6] == 26 && array[7] == 10) {
                                                return "mng";
                                            }
                                            if (b == -48 && b2 == -49 && b3 == 17 && b4 == -32 && b5 == -95 && b6 == -79 && array[6] == 26 && array[7] == -31 && array[8] == 0) {
                                                return "fpx";
                                            }
                                            if (b == -45 && b2 == 35 && b3 == 0 && b4 == 0 && b5 == 3 && b6 == 0 && array[6] == 0 && array[7] == 0) {
                                                return "wlm";
                                            }
                                            if (b == -41 && b2 == -51 && b3 == -58 && b4 == -102) {
                                                return "wmf";
                                            }
                                            if (b == -21 && b2 == 60 && b3 == -112 && b4 == 42) {
                                                return "img";
                                            }
                                            if (b == -6 && b2 == -34 && b3 == -70 && b4 == -66 && b5 == 1 && b6 == 1) {
                                                return "wic";
                                            }
                                            if (b == -1) {
                                                if (b2 == -40) {
                                                    return "jpg";
                                                }
                                                if (b2 == 87 && b3 == 80 && b4 == 67 && b5 == 16) {
                                                    return "wpg";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }
}
