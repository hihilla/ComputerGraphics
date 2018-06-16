// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.common.util.SecurityUtil;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Field;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.ProcAddressTable;

public final class WGLExtProcAddressTable extends ProcAddressTable
{
    long _addressof_wglCreateBufferRegionARB;
    long _addressof_wglDeleteBufferRegionARB;
    long _addressof_wglSaveBufferRegionARB;
    long _addressof_wglRestoreBufferRegionARB;
    long _addressof_wglCreateContextAttribsARB;
    long _addressof_wglGetExtensionsStringARB;
    long _addressof_wglMakeContextCurrent;
    long _addressof_wglGetCurrentReadDC;
    long _addressof_wglCreatePbufferARB;
    long _addressof_wglGetPbufferDCARB;
    long _addressof_wglReleasePbufferDCARB;
    long _addressof_wglDestroyPbufferARB;
    long _addressof_wglQueryPbufferARB;
    long _addressof_wglGetPixelFormatAttribivARB;
    long _addressof_wglGetPixelFormatAttribfvARB;
    long _addressof_wglChoosePixelFormatARB;
    long _addressof_wglBindTexImageARB;
    long _addressof_wglReleaseTexImageARB;
    long _addressof_wglSetPbufferAttribARB;
    long _addressof_wglSetStereoEmitterState3DL;
    long _addressof_wglGetGPUIDsAMD;
    long _addressof_wglGetGPUInfoAMD;
    long _addressof_wglGetContextGPUIDAMD;
    long _addressof_wglCreateAssociatedContextAMD;
    long _addressof_wglCreateAssociatedContextAttribsAMD;
    long _addressof_wglDeleteAssociatedContextAMD;
    long _addressof_wglMakeAssociatedContextCurrentAMD;
    long _addressof_wglGetCurrentAssociatedContextAMD;
    long _addressof_wglBlitContextFramebufferAMD;
    long _addressof_wglCreateDisplayColorTableEXT;
    long _addressof_wglLoadDisplayColorTableEXT;
    long _addressof_wglBindDisplayColorTableEXT;
    long _addressof_wglDestroyDisplayColorTableEXT;
    long _addressof_wglGetExtensionsStringEXT;
    long _addressof_wglCreatePbufferEXT;
    long _addressof_wglGetPbufferDCEXT;
    long _addressof_wglReleasePbufferDCEXT;
    long _addressof_wglDestroyPbufferEXT;
    long _addressof_wglQueryPbufferEXT;
    long _addressof_wglGetPixelFormatAttribivEXT;
    long _addressof_wglGetPixelFormatAttribfvEXT;
    long _addressof_wglChoosePixelFormatEXT;
    long _addressof_wglSwapIntervalEXT;
    long _addressof_wglGetSwapIntervalEXT;
    long _addressof_wglEnableFrameLockI3D;
    long _addressof_wglDisableFrameLockI3D;
    long _addressof_wglIsEnabledFrameLockI3D;
    long _addressof_wglQueryFrameLockMasterI3D;
    long _addressof_wglGetFrameUsageI3D;
    long _addressof_wglBeginFrameTrackingI3D;
    long _addressof_wglEndFrameTrackingI3D;
    long _addressof_wglQueryFrameTrackingI3D;
    long _addressof_wglDXSetResourceShareHandleNV;
    long _addressof_wglDXOpenDeviceNV;
    long _addressof_wglDXCloseDeviceNV;
    long _addressof_wglDXRegisterObjectNV;
    long _addressof_wglDXUnregisterObjectNV;
    long _addressof_wglDXObjectAccessNV;
    long _addressof_wglDXLockObjectsNV;
    long _addressof_wglDXUnlockObjectsNV;
    long _addressof_wglCopyImageSubDataNV;
    long _addressof_wglDelayBeforeSwapNV;
    long _addressof_wglEnumerateVideoDevicesNV;
    long _addressof_wglBindVideoDeviceNV;
    long _addressof_wglQueryCurrentContextNV;
    long _addressof_wglJoinSwapGroupNV;
    long _addressof_wglBindSwapBarrierNV;
    long _addressof_wglQuerySwapGroupNV;
    long _addressof_wglQueryMaxSwapGroupsNV;
    long _addressof_wglQueryFrameCountNV;
    long _addressof_wglResetFrameCountNV;
    long _addressof_wglAllocateMemoryNV;
    long _addressof_wglFreeMemoryNV;
    long _addressof_wglBindVideoCaptureDeviceNV;
    long _addressof_wglEnumerateVideoCaptureDevicesNV;
    long _addressof_wglLockVideoCaptureDeviceNV;
    long _addressof_wglQueryVideoCaptureDeviceNV;
    long _addressof_wglReleaseVideoCaptureDeviceNV;
    long _addressof_wglGetVideoDeviceNV;
    long _addressof_wglReleaseVideoDeviceNV;
    long _addressof_wglBindVideoImageNV;
    long _addressof_wglReleaseVideoImageNV;
    long _addressof_wglSendPbufferToVideoNV;
    long _addressof_wglGetVideoInfoNV;
    
    public WGLExtProcAddressTable() {
    }
    
    public WGLExtProcAddressTable(final FunctionAddressResolver functionAddressResolver) {
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
                        final Field declaredField = WGLExtProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
                        final Field declaredField = WGLExtProcAddressTable.class.getDeclaredField(funcNamePermutation);
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
