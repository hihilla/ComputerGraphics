// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.gluegen.runtime;

import com.jogamp.common.os.DynamicLookupHelper;

public interface FunctionAddressResolver
{
    long resolve(final String p0, final DynamicLookupHelper p1) throws SecurityException;
}
