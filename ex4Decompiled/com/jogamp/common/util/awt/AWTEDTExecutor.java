// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.awt;

import java.lang.reflect.InvocationTargetException;
import java.awt.EventQueue;
import com.jogamp.common.util.RunnableExecutor;

public class AWTEDTExecutor implements RunnableExecutor
{
    public static final AWTEDTExecutor singleton;
    
    @Override
    public void invoke(final boolean b, final Runnable runnable) {
        if (EventQueue.isDispatchThread()) {
            runnable.run();
        }
        else {
            try {
                if (b) {
                    EventQueue.invokeAndWait(runnable);
                }
                else {
                    EventQueue.invokeLater(runnable);
                }
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getTargetException());
            }
            catch (InterruptedException ex2) {
                throw new RuntimeException(ex2);
            }
        }
    }
    
    public boolean invoke(final Object o, final boolean b, final boolean b2, final Runnable runnable) {
        if (EventQueue.isDispatchThread()) {
            runnable.run();
            return true;
        }
        if (!Thread.holdsLock(o)) {
            try {
                if (b2) {
                    EventQueue.invokeAndWait(runnable);
                }
                else {
                    EventQueue.invokeLater(runnable);
                }
            }
            catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getTargetException());
            }
            catch (InterruptedException ex2) {
                throw new RuntimeException(ex2);
            }
            return true;
        }
        if (b) {
            runnable.run();
            return true;
        }
        return false;
    }
    
    static {
        singleton = new AWTEDTExecutor();
    }
}
