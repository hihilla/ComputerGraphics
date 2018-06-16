// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.awt;

import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.util.EDTUtil;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import jogamp.newt.DisplayImpl;

public class DisplayDriver extends DisplayImpl
{
    @Override
    protected void createNativeImpl() {
        this.aDevice = AWTGraphicsDevice.createDefault();
    }
    
    protected void setAWTGraphicsDevice(final AWTGraphicsDevice aDevice) {
        this.aDevice = aDevice;
    }
    
    @Override
    protected EDTUtil createEDTUtil() {
        AWTEDTUtil awtedtUtil;
        if (NewtFactory.useEDT()) {
            awtedtUtil = new AWTEDTUtil(Thread.currentThread().getThreadGroup(), "AWTDisplay-" + this.getFQName(), this.dispatchMessagesRunnable);
            if (DisplayDriver.DEBUG) {
                System.err.println("Display.createNative(" + this.getFQName() + ") Create EDTUtil: " + awtedtUtil.getClass().getName());
            }
        }
        else {
            awtedtUtil = null;
        }
        return awtedtUtil;
    }
    
    @Override
    protected void closeNativeImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        abstractGraphicsDevice.close();
    }
    
    @Override
    protected void dispatchMessagesNative() {
    }
}
