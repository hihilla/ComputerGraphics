// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;

class DefaultAnimatorImpl implements AnimatorBase.AnimatorImpl
{
    @Override
    public void display(final ArrayList<GLAutoDrawable> list, final boolean b, final boolean b2) throws AnimatorBase.UncaughtAnimatorException {
        for (int n = 0, n2 = 0; n == 0 && n2 < list.size(); ++n2) {
            boolean b3 = true;
            GLAutoDrawable glAutoDrawable = null;
            try {
                glAutoDrawable = list.get(n2);
                b3 = false;
                glAutoDrawable.display();
            }
            catch (Throwable t) {
                if (b3 && t instanceof IndexOutOfBoundsException) {
                    n = 1;
                }
                else {
                    if (!b) {
                        throw new AnimatorBase.UncaughtAnimatorException(glAutoDrawable, t);
                    }
                    if (b2) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
    
    @Override
    public boolean blockUntilDone(final Thread thread) {
        return Thread.currentThread() != thread;
    }
}
