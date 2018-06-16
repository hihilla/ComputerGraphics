// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

class FilterWriteStrategy
{
    private static final int COMPUTE_STATS_EVERY_N_LINES = 8;
    final ImageInfo imgInfo;
    public final FilterType configuredType;
    private FilterType currentType;
    private int lastRowTested;
    private final double[] lastSums;
    private final double[] lastEntropies;
    private double[] preference;
    private int discoverEachLines;
    private final double[] histogram1;
    
    FilterWriteStrategy(final ImageInfo imgInfo, final FilterType filterType) {
        this.lastRowTested = -1000000;
        this.lastSums = new double[5];
        this.lastEntropies = new double[5];
        this.preference = new double[] { 1.1, 1.1, 1.1, 1.1, 1.2 };
        this.discoverEachLines = -1;
        this.histogram1 = new double[256];
        this.imgInfo = imgInfo;
        this.configuredType = filterType;
        if (filterType.val < 0) {
            if ((imgInfo.rows < 8 && imgInfo.cols < 8) || imgInfo.indexed || imgInfo.bitDepth < 8) {
                this.currentType = FilterType.FILTER_NONE;
            }
            else {
                this.currentType = FilterType.FILTER_PAETH;
            }
        }
        else {
            this.currentType = filterType;
        }
        if (filterType == FilterType.FILTER_AGGRESSIVE) {
            this.discoverEachLines = 8;
        }
        if (filterType == FilterType.FILTER_VERYAGGRESSIVE) {
            this.discoverEachLines = 1;
        }
    }
    
    boolean shouldTestAll(final int n) {
        if (this.discoverEachLines > 0 && this.lastRowTested + this.discoverEachLines <= n) {
            this.currentType = null;
            return true;
        }
        return false;
    }
    
    public void setPreference(final double n, final double n2, final double n3, final double n4, final double n5) {
        this.preference = new double[] { n, n2, n3, n4, n5 };
    }
    
    public boolean computesStatistics() {
        return this.discoverEachLines > 0;
    }
    
    void fillResultsForFilter(final int lastRowTested, final FilterType filterType, final double n, final int[] array, final boolean b) {
        this.lastRowTested = lastRowTested;
        this.lastSums[filterType.val] = n;
        if (array != null) {
            final double n2 = (lastRowTested == 0) ? 0.0 : 0.3;
            final double n3 = 1.0 - n2;
            double n4 = 0.0;
            for (int i = 0; i < 256; ++i) {
                final double n5 = this.histogram1[i] * n2 + array[i] / this.imgInfo.cols * n3;
                if (b) {
                    n4 += ((n5 > 1.0E-8) ? (n5 * Math.log(n5)) : 0.0);
                }
                else {
                    this.histogram1[i] = n5;
                }
            }
            this.lastEntropies[filterType.val] = -n4;
        }
    }
    
    FilterType gimmeFilterType(final int n, final boolean b) {
        if (this.currentType == null) {
            if (n == 0) {
                this.currentType = FilterType.FILTER_SUB;
            }
            else {
                double n2 = Double.MAX_VALUE;
                for (int i = 0; i < 5; ++i) {
                    final double n3 = (b ? this.lastEntropies[i] : this.lastSums[i]) / this.preference[i];
                    if (n3 <= n2) {
                        n2 = n3;
                        this.currentType = FilterType.getByVal(i);
                    }
                }
            }
        }
        if (this.configuredType == FilterType.FILTER_CYCLIC) {
            this.currentType = FilterType.getByVal((this.currentType.val + 1) % 5);
        }
        return this.currentType;
    }
}
