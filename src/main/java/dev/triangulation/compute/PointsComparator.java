package dev.triangulation.compute;

import dev.triangulation.graphics.DrawablePoint;

import java.util.Comparator;


public class PointsComparator implements Comparator<DrawablePoint> {
    @Override
    public int compare(DrawablePoint point1, DrawablePoint point2) {
        if (point1.getX() > point2.getX()) return 1;
        else if (point1.getX() < point2.getX()) return -1;
        else if (point1.getY() > point2.getY()) return 1;
        else if (point1.getY() < point2.getY()) return -1;
        else return 0;
    }
}
