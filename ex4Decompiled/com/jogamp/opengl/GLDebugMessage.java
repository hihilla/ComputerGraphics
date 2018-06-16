// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.os.Platform;

public class GLDebugMessage
{
    final GLContext source;
    final long when;
    final int dbgSource;
    final int dbgType;
    final int dbgId;
    final int dbgSeverity;
    final String dbgMsg;
    
    public GLDebugMessage(final GLContext source, final long when, final int dbgSource, final int dbgType, final int dbgId, final int dbgSeverity, final String dbgMsg) {
        this.source = source;
        this.when = when;
        this.dbgSource = dbgSource;
        this.dbgType = dbgType;
        this.dbgId = dbgId;
        this.dbgSeverity = dbgSeverity;
        this.dbgMsg = dbgMsg;
    }
    
    public static GLDebugMessage translateAMDEvent(final GLContext glContext, final long n, final int n2, final int n3, final int n4, final String s) {
        int n5 = 0;
        int n6 = 0;
        switch (n3) {
            case 37193: {
                n5 = 33350;
                n6 = 33356;
                break;
            }
            case 37194: {
                n5 = 33351;
                n6 = 33361;
                break;
            }
            case 37198: {
                n5 = 33352;
                n6 = 33361;
                break;
            }
            case 37199: {
                n5 = 33354;
                n6 = 33361;
                break;
            }
            case 37195: {
                n5 = 33355;
                n6 = 33357;
                break;
            }
            case 37196: {
                n5 = 33355;
                n6 = 33358;
                break;
            }
            case 37197: {
                n5 = 33355;
                n6 = 33360;
                break;
            }
            default: {
                n5 = 33355;
                n6 = 33361;
                break;
            }
        }
        return new GLDebugMessage(glContext, n, n5, n6, n2, n4, s);
    }
    
    public static int translateARB2AMDCategory(final int n, final int n2) {
        switch (n) {
            case 33351: {
                return 37194;
            }
            case 33352: {
                return 37198;
            }
            case 33354: {
                return 37199;
            }
            default: {
                switch (n2) {
                    case 33357: {
                        return 37195;
                    }
                    case 33358: {
                        return 37196;
                    }
                    case 33360: {
                        return 37197;
                    }
                    default: {
                        return 37200;
                    }
                }
                break;
            }
        }
    }
    
    public GLContext getSource() {
        return this.source;
    }
    
    public long getWhen() {
        return this.when;
    }
    
    public int getDbgSource() {
        return this.dbgSource;
    }
    
    public int getDbgType() {
        return this.dbgType;
    }
    
    public int getDbgId() {
        return this.dbgId;
    }
    
    public int getDbgSeverity() {
        return this.dbgSeverity;
    }
    
    public String getDbgMsg() {
        return this.dbgMsg;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        final String string = Platform.getNewline() + "\t";
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("GLDebugEvent[ id ");
        toHexString(sb, this.dbgId).append(string).append("type ").append(getDbgTypeString(this.dbgType)).append(string).append("severity ").append(getDbgSeverityString(this.dbgSeverity)).append(string).append("source ").append(getDbgSourceString(this.dbgSource)).append(string).append("msg ").append(this.dbgMsg).append(string).append("when ").append(this.when);
        if (null != this.source) {
            sb.append(string).append("source ").append(this.source.getGLVersion()).append(" - hash 0x").append(Integer.toHexString(this.source.hashCode()));
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    public static String getDbgSourceString(final int n) {
        switch (n) {
            case 33350: {
                return "GL API";
            }
            case 33352: {
                return "GLSL or extension compiler";
            }
            case 33351: {
                return "Native Windowing binding";
            }
            case 33353: {
                return "Third party";
            }
            case 33354: {
                return "Application";
            }
            case 33355: {
                return "generic";
            }
            default: {
                return "Unknown (" + toHexString(n) + ")";
            }
        }
    }
    
    public static String getDbgTypeString(final int n) {
        switch (n) {
            case 33356: {
                return "Error";
            }
            case 33357: {
                return "Warning: marked for deprecation";
            }
            case 33358: {
                return "Warning: undefined behavior";
            }
            case 33360: {
                return "Warning: implementation dependent performance";
            }
            case 33359: {
                return "Warning: vendor-specific extension use";
            }
            case 33361: {
                return "Warning: generic";
            }
            default: {
                return "Unknown (" + toHexString(n) + ")";
            }
        }
    }
    
    public static String getDbgSeverityString(final int n) {
        switch (n) {
            case 37190: {
                return "High: dangerous undefined behavior";
            }
            case 37191: {
                return "Medium: Severe performance/deprecation/other warnings";
            }
            case 37192: {
                return "Low: Performance warnings (redundancy/undefined)";
            }
            default: {
                return "Unknown (" + toHexString(n) + ")";
            }
        }
    }
    
    public static StringBuilder toHexString(StringBuilder sb, final int n) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        return sb.append("0x").append(Integer.toHexString(n));
    }
    
    public static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
}
