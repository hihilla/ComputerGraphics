// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.gluegen.runtime.opengl;

import com.jogamp.common.os.DynamicLookupHelper;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;

public class GLProcAddressResolver implements FunctionAddressResolver
{
    public static final boolean DEBUG = false;
    
    @Override
    public long resolve(final String s, final DynamicLookupHelper dynamicLookupHelper) throws SecurityException {
        long dynamicLookupFunction = 0L;
        for (int funcNamePermutationNumber = GLNameResolver.getFuncNamePermutationNumber(s), n = 0; 0L == dynamicLookupFunction && n < funcNamePermutationNumber; ++n) {
            final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(s, n);
            try {
                dynamicLookupFunction = dynamicLookupHelper.dynamicLookupFunction(funcNamePermutation);
            }
            catch (Exception ex) {}
        }
        return dynamicLookupFunction;
    }
}
