package dev.triangulation.compute;

import dev.triangulation.graphics.DrawablePoint;

/**
 * Une liaison entre trois points.
 */
public class Triangle {

    private DrawablePoint pt1, pt2, pt3;

    public Triangle(DrawablePoint pt1, DrawablePoint pt2, DrawablePoint pt3){
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.pt3 = pt3;
    }

    public DrawablePoint getPt1() { return pt1; }

    public DrawablePoint getPt2() { return pt2; }

    public DrawablePoint getPt3() { return pt3; }
}
