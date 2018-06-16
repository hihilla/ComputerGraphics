// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.util.HashMap;

public enum FilterType
{
    FILTER_NONE(0), 
    FILTER_SUB(1), 
    FILTER_UP(2), 
    FILTER_AVERAGE(3), 
    FILTER_PAETH(4), 
    FILTER_DEFAULT(-1), 
    FILTER_AGGRESSIVE(-2), 
    FILTER_VERYAGGRESSIVE(-3), 
    FILTER_CYCLIC(-50), 
    FILTER_UNKNOWN(-100);
    
    public final int val;
    private static HashMap<Integer, FilterType> byVal;
    
    private FilterType(final int val) {
        this.val = val;
    }
    
    public static FilterType getByVal(final int n) {
        return FilterType.byVal.get(n);
    }
    
    static {
        FilterType.byVal = new HashMap<Integer, FilterType>();
        for (final FilterType filterType : values()) {
            FilterType.byVal.put(filterType.val, filterType);
        }
    }
}
