package edu.cg;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class LocationOnMesila {
    public Point position;
    public Vec tangent, normal;

    public LocationOnMesila(Point position, Vec tangent, Vec normal) {
        this.position = position;
        this.tangent = tangent;
        this.normal = normal;
    }

    public Vec tangentCronnSromal() {
        return tangent.cross(normal);
    }
}
