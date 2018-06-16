// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.common.util.SecurityUtil;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Field;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.ProcAddressTable;

public final class GLXExtProcAddressTable extends ProcAddressTable
{
    long _addressof_glXGetProcAddress;
    long _addressof_glXGetProcAddressARB;
    long _addressof_glXCreateContextAttribsARB;
    long _addressof_glXGetGPUIDsAMD;
    long _addressof_glXGetGPUInfoAMD;
    long _addressof_glXGetContextGPUIDAMD;
    long _addressof_glXCreateAssociatedContextAMD;
    long _addressof_glXCreateAssociatedContextAttribsAMD;
    long _addressof_glXDeleteAssociatedContextAMD;
    long _addressof_glXMakeAssociatedContextCurrentAMD;
    long _addressof_glXGetCurrentAssociatedContextAMD;
    long _addressof_glXBlitContextFramebufferAMD;
    long _addressof_glXGetCurrentDisplayEXT;
    long _addressof_glXQueryContextInfoEXT;
    long _addressof_glXGetContextIDEXT;
    long _addressof_glXImportContextEXT;
    long _addressof_glXFreeContextEXT;
    long _addressof_glXSwapIntervalEXT;
    long _addressof_glXBindTexImageEXT;
    long _addressof_glXReleaseTexImageEXT;
    long _addressof_glXGetAGPOffsetMESA;
    long _addressof_glXCopySubBufferMESA;
    long _addressof_glXQueryCurrentRendererIntegerMESA;
    long _addressof_glXQueryCurrentRendererStringMESA;
    long _addressof_glXQueryRendererIntegerMESA;
    long _addressof_glXQueryRendererStringMESA;
    long _addressof_glXReleaseBuffersMESA;
    long _addressof_glXSet3DfxModeMESA;
    long _addressof_glXCopyBufferSubDataNV;
    long _addressof_glXNamedCopyBufferSubDataNV;
    long _addressof_glXCopyImageSubDataNV;
    long _addressof_glXDelayBeforeSwapNV;
    long _addressof_glXEnumerateVideoDevicesNV;
    long _addressof_glXBindVideoDeviceNV;
    long _addressof_glXJoinSwapGroupNV;
    long _addressof_glXBindSwapBarrierNV;
    long _addressof_glXQuerySwapGroupNV;
    long _addressof_glXQueryMaxSwapGroupsNV;
    long _addressof_glXQueryFrameCountNV;
    long _addressof_glXResetFrameCountNV;
    long _addressof_glXBindVideoCaptureDeviceNV;
    long _addressof_glXEnumerateVideoCaptureDevicesNV;
    long _addressof_glXLockVideoCaptureDeviceNV;
    long _addressof_glXQueryVideoCaptureDeviceNV;
    long _addressof_glXReleaseVideoCaptureDeviceNV;
    long _addressof_glXGetVideoDeviceNV;
    long _addressof_glXReleaseVideoDeviceNV;
    long _addressof_glXBindVideoImageNV;
    long _addressof_glXReleaseVideoImageNV;
    long _addressof_glXSendPbufferToVideoNV;
    long _addressof_glXGetVideoInfoNV;
    long _addressof_glXGetSyncValuesOML;
    long _addressof_glXGetMscRateOML;
    long _addressof_glXSwapBuffersMscOML;
    long _addressof_glXWaitForMscOML;
    long _addressof_glXWaitForSbcOML;
    long _addressof_glXBindSwapBarrierSGIX;
    long _addressof_glXQueryMaxSwapBarriersSGIX;
    long _addressof_glXJoinSwapGroupSGIX;
    long _addressof_glXBindChannelToWindowSGIX;
    long _addressof_glXChannelRectSGIX;
    long _addressof_glXQueryChannelRectSGIX;
    long _addressof_glXQueryChannelDeltasSGIX;
    long _addressof_glXChannelRectSyncSGIX;
    long _addressof_glXCushionSGI;
    long _addressof_glXMakeCurrentReadSGI;
    long _addressof_glXGetCurrentReadDrawableSGI;
    long _addressof_glXSwapIntervalSGI;
    long _addressof_glXGetVideoSyncSGI;
    long _addressof_glXWaitVideoSyncSGI;
    long _addressof_glXGetTransparentIndexSUN;
    
    public GLXExtProcAddressTable() {
    }
    
    public GLXExtProcAddressTable(final FunctionAddressResolver functionAddressResolver) {
        super(functionAddressResolver);
    }
    
    @Override
    protected boolean isFunctionAvailableImpl(final String s) throws IllegalArgumentException {
        final String normalizeVEN = GLNameResolver.normalizeVEN(GLNameResolver.normalizeARB(s, true), true);
        final Field field = AccessController.doPrivileged((PrivilegedAction<Field>)new PrivilegedAction<Field>() {
            final /* synthetic */ int val$funcNamePermNum = GLNameResolver.getFuncNamePermutationNumber(normalizeVEN);
            final /* synthetic */ String val$addressFieldNameBase = "_addressof_" + normalizeVEN;
            
            @Override
            public final Field run() {
                int i = 0;
                while (i < this.val$funcNamePermNum) {
                    final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(this.val$addressFieldNameBase, i);
                    try {
                        final Field declaredField = GLXExtProcAddressTable.class.getDeclaredField(funcNamePermutation);
                        declaredField.setAccessible(true);
                        return declaredField;
                    }
                    catch (NoSuchFieldException ex) {
                        ++i;
                        continue;
                    }
                    break;
                }
                return null;
            }
        });
        if (null == field) {
            throw new RuntimeException("WARNING: Address field query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or address field is not a known " + "function");
        }
        try {
            return 0L != field.getLong(this);
        }
        catch (Exception ex) {
            throw new RuntimeException("WARNING: Address query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or is not a known " + "function", ex);
        }
    }
    
    @Override
    public long getAddressFor(final String s) throws SecurityException, IllegalArgumentException {
        SecurityUtil.checkAllLinkPermission();
        final String normalizeVEN = GLNameResolver.normalizeVEN(GLNameResolver.normalizeARB(s, true), true);
        final Field field = AccessController.doPrivileged((PrivilegedAction<Field>)new PrivilegedAction<Field>() {
            final /* synthetic */ int val$funcNamePermNum = GLNameResolver.getFuncNamePermutationNumber(normalizeVEN);
            final /* synthetic */ String val$addressFieldNameBase = "_addressof_" + normalizeVEN;
            
            @Override
            public final Field run() {
                int i = 0;
                while (i < this.val$funcNamePermNum) {
                    final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(this.val$addressFieldNameBase, i);
                    try {
                        final Field declaredField = GLXExtProcAddressTable.class.getDeclaredField(funcNamePermutation);
                        declaredField.setAccessible(true);
                        return declaredField;
                    }
                    catch (NoSuchFieldException ex) {
                        ++i;
                        continue;
                    }
                    break;
                }
                return null;
            }
        });
        if (null == field) {
            throw new RuntimeException("WARNING: Address field query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or address field is not a known " + "function");
        }
        try {
            return field.getLong(this);
        }
        catch (Exception ex) {
            throw new RuntimeException("WARNING: Address query failed for \"" + normalizeVEN + "\"/\"" + s + "\"; it's either statically linked or is not a known " + "function", ex);
        }
    }
}
