// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.algebra.Point;

public class TrackPoints
{
    public static CyclicList<Point> track1() {
        final CyclicList<Point> ans = new CyclicList<Point>();
        ans.add(new Point(0.7, -0.2, 0.1));
        ans.add(new Point(-0.2, -0.6, 0.5));
        ans.add(new Point(-0.7, -0.4, 0.6));
        ans.add(new Point(-0.78, 0.0, 0.6));
        ans.add(new Point(-0.7, 0.4, 0.6));
        ans.add(new Point(-0.2, 0.6, 0.5));
        ans.add(new Point(0.7, 0.2, 0.1));
        return ans;
    }
    
    public static CyclicList<Point> track2() {
        final CyclicList<Point> ans = new CyclicList<Point>();
        ans.add(new Point(0.0f, 0.0f, 0.0f));
        ans.add(new Point(-0.5, 0.0, 0.5));
        ans.add(new Point(0.0f, 0.0f, 1.0f));
        ans.add(new Point(0.5, 0.0, 0.5));
        return ans;
    }
    
    public static CyclicList<Point> track3() {
        final CyclicList<Point> ans = new CyclicList<Point>();
        ans.add(new Point(-0.5, -0.5, 0.0));
        ans.add(new Point(-0.5, 0.5, 0.5));
        ans.add(new Point(0.5, 0.5, 0.0));
        ans.add(new Point(0.5, -0.5, 0.5));
        return ans;
    }
}
