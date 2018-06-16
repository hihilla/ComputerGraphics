// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public interface RunnableExecutor
{
    public static final RunnableExecutor currentThreadExecutor = new CurrentThreadExecutor();
    
    void invoke(final boolean p0, final Runnable p1);
    
    public static class CurrentThreadExecutor implements RunnableExecutor
    {
        @Override
        public void invoke(final boolean b, final Runnable runnable) {
            runnable.run();
        }
    }
}
