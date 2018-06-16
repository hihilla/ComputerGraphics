// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

public class Mnemonic
{
    public static final short SVTCA = 0;
    public static final short SPVTCA = 2;
    public static final short SFVTCA = 4;
    public static final short SPVTL = 6;
    public static final short SFVTL = 8;
    public static final short SPVFS = 10;
    public static final short SFVFS = 11;
    public static final short GPV = 12;
    public static final short GFV = 13;
    public static final short SFVTPV = 14;
    public static final short ISECT = 15;
    public static final short SRP0 = 16;
    public static final short SRP1 = 17;
    public static final short SRP2 = 18;
    public static final short SZP0 = 19;
    public static final short SZP1 = 20;
    public static final short SZP2 = 21;
    public static final short SZPS = 22;
    public static final short SLOOP = 23;
    public static final short RTG = 24;
    public static final short RTHG = 25;
    public static final short SMD = 26;
    public static final short ELSE = 27;
    public static final short JMPR = 28;
    public static final short SCVTCI = 29;
    public static final short SSWCI = 30;
    public static final short SSW = 31;
    public static final short DUP = 32;
    public static final short POP = 33;
    public static final short CLEAR = 34;
    public static final short SWAP = 35;
    public static final short DEPTH = 36;
    public static final short CINDEX = 37;
    public static final short MINDEX = 38;
    public static final short ALIGNPTS = 39;
    public static final short UTP = 41;
    public static final short LOOPCALL = 42;
    public static final short CALL = 43;
    public static final short FDEF = 44;
    public static final short ENDF = 45;
    public static final short MDAP = 46;
    public static final short IUP = 48;
    public static final short SHP = 50;
    public static final short SHC = 52;
    public static final short SHZ = 54;
    public static final short SHPIX = 56;
    public static final short IP = 57;
    public static final short MSIRP = 58;
    public static final short ALIGNRP = 60;
    public static final short RTDG = 61;
    public static final short MIAP = 62;
    public static final short NPUSHB = 64;
    public static final short NPUSHW = 65;
    public static final short WS = 66;
    public static final short RS = 67;
    public static final short WCVTP = 68;
    public static final short RCVT = 69;
    public static final short GC = 70;
    public static final short SCFS = 72;
    public static final short MD = 73;
    public static final short MPPEM = 75;
    public static final short MPS = 76;
    public static final short FLIPON = 77;
    public static final short FLIPOFF = 78;
    public static final short DEBUG = 79;
    public static final short LT = 80;
    public static final short LTEQ = 81;
    public static final short GT = 82;
    public static final short GTEQ = 83;
    public static final short EQ = 84;
    public static final short NEQ = 85;
    public static final short ODD = 86;
    public static final short EVEN = 87;
    public static final short IF = 88;
    public static final short EIF = 89;
    public static final short AND = 90;
    public static final short OR = 91;
    public static final short NOT = 92;
    public static final short DELTAP1 = 93;
    public static final short SDB = 94;
    public static final short SDS = 95;
    public static final short ADD = 96;
    public static final short SUB = 97;
    public static final short DIV = 98;
    public static final short MUL = 99;
    public static final short ABS = 100;
    public static final short NEG = 101;
    public static final short FLOOR = 102;
    public static final short CEILING = 103;
    public static final short ROUND = 104;
    public static final short NROUND = 108;
    public static final short WCVTF = 112;
    public static final short DELTAP2 = 113;
    public static final short DELTAP3 = 114;
    public static final short DELTAC1 = 115;
    public static final short DELTAC2 = 116;
    public static final short DELTAC3 = 117;
    public static final short SROUND = 118;
    public static final short S45ROUND = 119;
    public static final short JROT = 120;
    public static final short JROF = 121;
    public static final short ROFF = 122;
    public static final short RUTG = 124;
    public static final short RDTG = 125;
    public static final short SANGW = 126;
    public static final short AA = 127;
    public static final short FLIPPT = 128;
    public static final short FLIPRGON = 129;
    public static final short FLIPRGOFF = 130;
    public static final short SCANCTRL = 133;
    public static final short SDPVTL = 134;
    public static final short GETINFO = 136;
    public static final short IDEF = 137;
    public static final short ROLL = 138;
    public static final short MAX = 139;
    public static final short MIN = 140;
    public static final short SCANTYPE = 141;
    public static final short INSTCTRL = 142;
    public static final short PUSHB = 176;
    public static final short PUSHW = 184;
    public static final short MDRP = 192;
    public static final short MIRP = 224;
    
    public static String getMnemonic(final short n) {
        if (n >= 224) {
            return "MIRP[" + (((n & 0x10) == 0x0) ? "nrp0," : "srp0,") + (((n & 0x8) == 0x0) ? "nmd," : "md,") + (((n & 0x4) == 0x0) ? "nrd," : "rd,") + (n & 0x3) + "]";
        }
        if (n >= 192) {
            return "MDRP[" + (((n & 0x10) == 0x0) ? "nrp0," : "srp0,") + (((n & 0x8) == 0x0) ? "nmd," : "md,") + (((n & 0x4) == 0x0) ? "nrd," : "rd,") + (n & 0x3) + "]";
        }
        if (n >= 184) {
            return "PUSHW[" + ((n & 0x7) + 1) + "]";
        }
        if (n >= 176) {
            return "PUSHB[" + ((n & 0x7) + 1) + "]";
        }
        if (n >= 142) {
            return "INSTCTRL";
        }
        if (n >= 141) {
            return "SCANTYPE";
        }
        if (n >= 140) {
            return "MIN";
        }
        if (n >= 139) {
            return "MAX";
        }
        if (n >= 138) {
            return "ROLL";
        }
        if (n >= 137) {
            return "IDEF";
        }
        if (n >= 136) {
            return "GETINFO";
        }
        if (n >= 134) {
            return "SDPVTL[" + (n & 0x1) + "]";
        }
        if (n >= 133) {
            return "SCANCTRL";
        }
        if (n >= 130) {
            return "FLIPRGOFF";
        }
        if (n >= 129) {
            return "FLIPRGON";
        }
        if (n >= 128) {
            return "FLIPPT";
        }
        if (n >= 127) {
            return "AA";
        }
        if (n >= 126) {
            return "SANGW";
        }
        if (n >= 125) {
            return "RDTG";
        }
        if (n >= 124) {
            return "RUTG";
        }
        if (n >= 122) {
            return "ROFF";
        }
        if (n >= 121) {
            return "JROF";
        }
        if (n >= 120) {
            return "JROT";
        }
        if (n >= 119) {
            return "S45ROUND";
        }
        if (n >= 118) {
            return "SROUND";
        }
        if (n >= 117) {
            return "DELTAC3";
        }
        if (n >= 116) {
            return "DELTAC2";
        }
        if (n >= 115) {
            return "DELTAC1";
        }
        if (n >= 114) {
            return "DELTAP3";
        }
        if (n >= 113) {
            return "DELTAP2";
        }
        if (n >= 112) {
            return "WCVTF";
        }
        if (n >= 108) {
            return "NROUND[" + (n & 0x3) + "]";
        }
        if (n >= 104) {
            return "ROUND[" + (n & 0x3) + "]";
        }
        if (n >= 103) {
            return "CEILING";
        }
        if (n >= 102) {
            return "FLOOR";
        }
        if (n >= 101) {
            return "NEG";
        }
        if (n >= 100) {
            return "ABS";
        }
        if (n >= 99) {
            return "MUL";
        }
        if (n >= 98) {
            return "DIV";
        }
        if (n >= 97) {
            return "SUB";
        }
        if (n >= 96) {
            return "ADD";
        }
        if (n >= 95) {
            return "SDS";
        }
        if (n >= 94) {
            return "SDB";
        }
        if (n >= 93) {
            return "DELTAP1";
        }
        if (n >= 92) {
            return "NOT";
        }
        if (n >= 91) {
            return "OR";
        }
        if (n >= 90) {
            return "AND";
        }
        if (n >= 89) {
            return "EIF";
        }
        if (n >= 88) {
            return "IF";
        }
        if (n >= 87) {
            return "EVEN";
        }
        if (n >= 86) {
            return "ODD";
        }
        if (n >= 85) {
            return "NEQ";
        }
        if (n >= 84) {
            return "EQ";
        }
        if (n >= 83) {
            return "GTEQ";
        }
        if (n >= 82) {
            return "GT";
        }
        if (n >= 81) {
            return "LTEQ";
        }
        if (n >= 80) {
            return "LT";
        }
        if (n >= 79) {
            return "DEBUG";
        }
        if (n >= 78) {
            return "FLIPOFF";
        }
        if (n >= 77) {
            return "FLIPON";
        }
        if (n >= 76) {
            return "MPS";
        }
        if (n >= 75) {
            return "MPPEM";
        }
        if (n >= 73) {
            return "MD[" + (n & 0x1) + "]";
        }
        if (n >= 72) {
            return "SCFS";
        }
        if (n >= 70) {
            return "GC[" + (n & 0x1) + "]";
        }
        if (n >= 69) {
            return "RCVT";
        }
        if (n >= 68) {
            return "WCVTP";
        }
        if (n >= 67) {
            return "RS";
        }
        if (n >= 66) {
            return "WS";
        }
        if (n >= 65) {
            return "NPUSHW";
        }
        if (n >= 64) {
            return "NPUSHB";
        }
        if (n >= 62) {
            return "MIAP[" + (((n & 0x1) == 0x0) ? "nrd+nci" : "rd+ci") + "]";
        }
        if (n >= 61) {
            return "RTDG";
        }
        if (n >= 60) {
            return "ALIGNRP";
        }
        if (n >= 58) {
            return "MSIRP[" + (n & 0x1) + "]";
        }
        if (n >= 57) {
            return "IP";
        }
        if (n >= 56) {
            return "SHPIX";
        }
        if (n >= 54) {
            return "SHZ[" + (n & 0x1) + "]";
        }
        if (n >= 52) {
            return "SHC[" + (n & 0x1) + "]";
        }
        if (n >= 50) {
            return "SHP";
        }
        if (n >= 48) {
            return "IUP[" + (((n & 0x1) == 0x0) ? "y" : "x") + "]";
        }
        if (n >= 46) {
            return "MDAP[" + (((n & 0x1) == 0x0) ? "nrd" : "rd") + "]";
        }
        if (n >= 45) {
            return "ENDF";
        }
        if (n >= 44) {
            return "FDEF";
        }
        if (n >= 43) {
            return "CALL";
        }
        if (n >= 42) {
            return "LOOPCALL";
        }
        if (n >= 41) {
            return "UTP";
        }
        if (n >= 39) {
            return "ALIGNPTS";
        }
        if (n >= 38) {
            return "MINDEX";
        }
        if (n >= 37) {
            return "CINDEX";
        }
        if (n >= 36) {
            return "DEPTH";
        }
        if (n >= 35) {
            return "SWAP";
        }
        if (n >= 34) {
            return "CLEAR";
        }
        if (n >= 33) {
            return "POP";
        }
        if (n >= 32) {
            return "DUP";
        }
        if (n >= 31) {
            return "SSW";
        }
        if (n >= 30) {
            return "SSWCI";
        }
        if (n >= 29) {
            return "SCVTCI";
        }
        if (n >= 28) {
            return "JMPR";
        }
        if (n >= 27) {
            return "ELSE";
        }
        if (n >= 26) {
            return "SMD";
        }
        if (n >= 25) {
            return "RTHG";
        }
        if (n >= 24) {
            return "RTG";
        }
        if (n >= 23) {
            return "SLOOP";
        }
        if (n >= 22) {
            return "SZPS";
        }
        if (n >= 21) {
            return "SZP2";
        }
        if (n >= 20) {
            return "SZP1";
        }
        if (n >= 19) {
            return "SZP0";
        }
        if (n >= 18) {
            return "SRP2";
        }
        if (n >= 17) {
            return "SRP1";
        }
        if (n >= 16) {
            return "SRP0";
        }
        if (n >= 15) {
            return "ISECT";
        }
        if (n >= 14) {
            return "SFVTPV";
        }
        if (n >= 13) {
            return "GFV";
        }
        if (n >= 12) {
            return "GPV";
        }
        if (n >= 11) {
            return "SFVFS";
        }
        if (n >= 10) {
            return "SPVFS";
        }
        if (n >= 8) {
            return "SFVTL[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]";
        }
        if (n >= 6) {
            return "SPVTL[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]";
        }
        if (n >= 4) {
            return "SFVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]";
        }
        if (n >= 2) {
            return "SPVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]";
        }
        if (n >= 0) {
            return "SVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]";
        }
        return "????";
    }
    
    public static String getComment(final short n) {
        if (n >= 224) {
            return "MIRP[" + (((n & 0x10) == 0x0) ? "nrp0," : "srp0,") + (((n & 0x8) == 0x0) ? "nmd," : "md,") + (((n & 0x4) == 0x0) ? "nrd," : "rd,") + (n & 0x3) + "]\t\tMove Indirect Relative Point";
        }
        if (n >= 192) {
            return "MDRP[" + (((n & 0x10) == 0x0) ? "nrp0," : "srp0,") + (((n & 0x8) == 0x0) ? "nmd," : "md,") + (((n & 0x4) == 0x0) ? "nrd," : "rd,") + (n & 0x3) + "]\t\tMove Direct Relative Point";
        }
        if (n >= 184) {
            return "PUSHW[" + ((n & 0x7) + 1) + "]";
        }
        if (n >= 176) {
            return "PUSHB[" + ((n & 0x7) + 1) + "]";
        }
        if (n >= 142) {
            return "INSTCTRL\tINSTruction Execution ConTRol";
        }
        if (n >= 141) {
            return "SCANTYPE\tSCANTYPE";
        }
        if (n >= 140) {
            return "MIN\t\tMINimum of top two stack elements";
        }
        if (n >= 139) {
            return "MAX\t\tMAXimum of top two stack elements";
        }
        if (n >= 138) {
            return "ROLL\t\tROLL the top three stack elements";
        }
        if (n >= 137) {
            return "IDEF\t\tInstruction DEFinition";
        }
        if (n >= 136) {
            return "GETINFO\tGET INFOrmation";
        }
        if (n >= 134) {
            return "SDPVTL[" + (n & 0x1) + "]\tSet Dual Projection_Vector To Line";
        }
        if (n >= 133) {
            return "SCANCTRL\tSCAN conversion ConTRoL";
        }
        if (n >= 130) {
            return "FLIPRGOFF\tFLIP RanGe OFF";
        }
        if (n >= 129) {
            return "FLIPRGON\tFLIP RanGe ON";
        }
        if (n >= 128) {
            return "FLIPPT\tFLIP PoinT";
        }
        if (n >= 127) {
            return "AA";
        }
        if (n >= 126) {
            return "SANGW\t\tSet Angle _Weight";
        }
        if (n >= 125) {
            return "RDTG\t\tRound Down To Grid";
        }
        if (n >= 124) {
            return "RUTG\t\tRound Up To Grid";
        }
        if (n >= 122) {
            return "ROFF\t\tRound OFF";
        }
        if (n >= 121) {
            return "JROF\t\tJump Relative On False";
        }
        if (n >= 120) {
            return "JROT\t\tJump Relative On True";
        }
        if (n >= 119) {
            return "S45ROUND\tSuper ROUND 45 degrees";
        }
        if (n >= 118) {
            return "SROUND\tSuper ROUND";
        }
        if (n >= 117) {
            return "DELTAC3\tDELTA exception C3";
        }
        if (n >= 116) {
            return "DELTAC2\tDELTA exception C2";
        }
        if (n >= 115) {
            return "DELTAC1\tDELTA exception C1";
        }
        if (n >= 114) {
            return "DELTAP3\tDELTA exception P3";
        }
        if (n >= 113) {
            return "DELTAP2\tDELTA exception P2";
        }
        if (n >= 112) {
            return "WCVTF\t\tWrite Control Value Table in FUnits";
        }
        if (n >= 108) {
            return "NROUND[" + (n & 0x3) + "]";
        }
        if (n >= 104) {
            return "ROUND[" + (n & 0x3) + "]";
        }
        if (n >= 103) {
            return "CEILING\tCEILING";
        }
        if (n >= 102) {
            return "FLOOR\t\tFLOOR";
        }
        if (n >= 101) {
            return "NEG\t\tNEGate";
        }
        if (n >= 100) {
            return "ABS\t\tABSolute value";
        }
        if (n >= 99) {
            return "MUL\t\tMULtiply";
        }
        if (n >= 98) {
            return "DIV\t\tDIVide";
        }
        if (n >= 97) {
            return "SUB\t\tSUBtract";
        }
        if (n >= 96) {
            return "ADD\t\tADD";
        }
        if (n >= 95) {
            return "SDS\t\tSet Delta_Shift in the graphics state";
        }
        if (n >= 94) {
            return "SDB\t\tSet Delta_Base in the graphics state";
        }
        if (n >= 93) {
            return "DELTAP1\tDELTA exception P1";
        }
        if (n >= 92) {
            return "NOT\t\tlogical NOT";
        }
        if (n >= 91) {
            return "OR\t\t\tlogical OR";
        }
        if (n >= 90) {
            return "AND\t\tlogical AND";
        }
        if (n >= 89) {
            return "EIF\t\tEnd IF";
        }
        if (n >= 88) {
            return "IF\t\t\tIF test";
        }
        if (n >= 87) {
            return "EVEN";
        }
        if (n >= 86) {
            return "ODD";
        }
        if (n >= 85) {
            return "NEQ\t\tNot EQual";
        }
        if (n >= 84) {
            return "EQ\t\t\tEQual";
        }
        if (n >= 83) {
            return "GTEQ\t\tGreater Than or Equal";
        }
        if (n >= 82) {
            return "GT\t\t\tGreater Than";
        }
        if (n >= 81) {
            return "LTEQ\t\tLess Than or Equal";
        }
        if (n >= 80) {
            return "LT\t\t\tLess Than";
        }
        if (n >= 79) {
            return "DEBUG";
        }
        if (n >= 78) {
            return "FLIPOFF\tSet the auto_flip Boolean to OFF";
        }
        if (n >= 77) {
            return "FLIPON\tSet the auto_flip Boolean to ON";
        }
        if (n >= 76) {
            return "MPS\t\tMeasure Point Size";
        }
        if (n >= 75) {
            return "MPPEM\t\tMeasure Pixels Per EM";
        }
        if (n >= 73) {
            return "MD[" + (n & 0x1) + "]\t\t\tMeasure Distance";
        }
        if (n >= 72) {
            return "SCFS\t\tSets Coordinate From the Stack using projection_vector and freedom_vector";
        }
        if (n >= 70) {
            return "GC[" + (n & 0x1) + "]\t\t\tGet Coordinate projected onto the projection_vector";
        }
        if (n >= 69) {
            return "RCVT\t\tRead Control Value Table";
        }
        if (n >= 68) {
            return "WCVTP\t\tWrite Control Value Table in Pixel units";
        }
        if (n >= 67) {
            return "RS\t\t\tRead Store";
        }
        if (n >= 66) {
            return "WS\t\t\tWrite Store";
        }
        if (n >= 65) {
            return "NPUSHW";
        }
        if (n >= 64) {
            return "NPUSHB";
        }
        if (n >= 62) {
            return "MIAP[" + (((n & 0x1) == 0x0) ? "nrd+nci" : "rd+ci") + "]\t\tMove Indirect Absolute Point";
        }
        if (n >= 61) {
            return "RTDG\t\tRound To Double Grid";
        }
        if (n >= 60) {
            return "ALIGNRP\tALIGN Relative Point";
        }
        if (n >= 58) {
            return "MSIRP[" + (n & 0x1) + "]\t\tMove Stack Indirect Relative Point";
        }
        if (n >= 57) {
            return "IP\t\t\tInterpolate Point by the last relative stretch";
        }
        if (n >= 56) {
            return "SHPIX\t\tSHift point by a PIXel amount";
        }
        if (n >= 54) {
            return "SHZ[" + (n & 0x1) + "]\t\tSHift Zone by the last pt";
        }
        if (n >= 52) {
            return "SHC[" + (n & 0x1) + "]\t\tSHift Contour by the last point";
        }
        if (n >= 50) {
            return "SHP\t\tSHift Point by the last point";
        }
        if (n >= 48) {
            return "IUP[" + (((n & 0x1) == 0x0) ? "y" : "x") + "]\t\tInterpolate Untouched Points through the outline";
        }
        if (n >= 46) {
            return "MDAP[" + (((n & 0x1) == 0x0) ? "nrd" : "rd") + "]\t\tMove Direct Absolute Point";
        }
        if (n >= 45) {
            return "ENDF\t\tEND Function definition";
        }
        if (n >= 44) {
            return "FDEF\t\tFunction DEFinition ";
        }
        if (n >= 43) {
            return "CALL\t\tCALL function";
        }
        if (n >= 42) {
            return "LOOPCALL\tLOOP and CALL function";
        }
        if (n >= 41) {
            return "UTP\t\tUnTouch Point";
        }
        if (n >= 39) {
            return "ALIGNPTS\tALIGN Points";
        }
        if (n >= 38) {
            return "MINDEX\tMove the INDEXed element to the top of the stack";
        }
        if (n >= 37) {
            return "CINDEX\tCopy the INDEXed element to the top of the stack";
        }
        if (n >= 36) {
            return "DEPTH\t\tReturns the DEPTH of the stack";
        }
        if (n >= 35) {
            return "SWAP\t\tSWAP the top two elements on the stack";
        }
        if (n >= 34) {
            return "CLEAR\t\tClear the entire stack";
        }
        if (n >= 33) {
            return "POP\t\tPOP top stack element";
        }
        if (n >= 32) {
            return "DUP\t\tDuplicate top stack element";
        }
        if (n >= 31) {
            return "SSW\t\tSet Single-width";
        }
        if (n >= 30) {
            return "SSWCI\t\tSet Single_Width_Cut_In";
        }
        if (n >= 29) {
            return "SCVTCI\tSet Control Value Table Cut In";
        }
        if (n >= 28) {
            return "JMPR\t\tJuMP";
        }
        if (n >= 27) {
            return "ELSE";
        }
        if (n >= 26) {
            return "SMD\t\tSet Minimum_ Distance";
        }
        if (n >= 25) {
            return "RTHG\t\tRound To Half Grid";
        }
        if (n >= 24) {
            return "RTG\t\tRound To Grid";
        }
        if (n >= 23) {
            return "SLOOP\t\tSet LOOP variable";
        }
        if (n >= 22) {
            return "SZPS\t\tSet Zone PointerS";
        }
        if (n >= 21) {
            return "SZP2\t\tSet Zone Pointer 2";
        }
        if (n >= 20) {
            return "SZP1\t\tSet Zone Pointer 1";
        }
        if (n >= 19) {
            return "SZP0\t\tSet Zone Pointer 0";
        }
        if (n >= 18) {
            return "SRP2\t\tSet Reference Point 2";
        }
        if (n >= 17) {
            return "SRP1\t\tSet Reference Point 1";
        }
        if (n >= 16) {
            return "SRP0\t\tSet Reference Point 0";
        }
        if (n >= 15) {
            return "ISECT\t\tmoves point p to the InterSECTion of two lines";
        }
        if (n >= 14) {
            return "SFVTPV\tSet Freedom_Vector To Projection Vector";
        }
        if (n >= 13) {
            return "GFV\t\tGet Freedom_Vector";
        }
        if (n >= 12) {
            return "GPV\t\tGet Projection_Vector";
        }
        if (n >= 11) {
            return "SFVFS\t\tSet Freedom_Vector From Stack";
        }
        if (n >= 10) {
            return "SPVFS\t\tSet Projection_Vector From Stack";
        }
        if (n >= 8) {
            return "SFVTL[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]\t\tSet Freedom_Vector To Line";
        }
        if (n >= 6) {
            return "SPVTL[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]\t\tSet Projection_Vector To Line";
        }
        if (n >= 4) {
            return "SFVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]\tSet Freedom_Vector to Coordinate Axis";
        }
        if (n >= 2) {
            return "SPVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]\tSet Projection_Vector To Coordinate Axis";
        }
        if (n >= 0) {
            return "SVTCA[" + (((n & 0x1) == 0x0) ? "y-axis" : "x-axis") + "]\t\tSet freedom and projection Vectors To Coordinate Axis";
        }
        return "????";
    }
}
