// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

import jogamp.common.util.locks.RecursiveLockImplJava5;
import jogamp.common.util.locks.RecursiveLockImpl01CompleteFair;
import jogamp.common.util.locks.RecursiveThreadGroupLockImpl01Unfairish;
import jogamp.common.util.locks.RecursiveLockImpl01Unfairish;

public class LockFactory
{
    public static RecursiveLock createRecursiveLock() {
        return new RecursiveLockImpl01Unfairish();
    }
    
    public static RecursiveThreadGroupLock createRecursiveThreadGroupLock() {
        return new RecursiveThreadGroupLockImpl01Unfairish();
    }
    
    public static RecursiveLock createRecursiveLock(final ImplType implType, final boolean b) {
        switch (implType) {
            case Int01: {
                return b ? new RecursiveLockImpl01CompleteFair() : new RecursiveLockImpl01Unfairish();
            }
            case Java5: {
                return new RecursiveLockImplJava5(b);
            }
            case Int02ThreadGroup: {
                return new RecursiveThreadGroupLockImpl01Unfairish();
            }
            default: {
                throw new InternalError("XXX");
            }
        }
    }
    
    public enum ImplType
    {
        Int01(0), 
        Java5(1), 
        Int02ThreadGroup(2);
        
        public final int id;
        
        private ImplType(final int id) {
            this.id = id;
        }
    }
}
