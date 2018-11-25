package dev.triangulation.graphics;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class PointsGenerator {

    public static SortedSet<DrawablePoint> generate(int numberOfPoints, int maxX, int maxY) {
        SortedSet<DrawablePoint> randomizedPoints = new TreeSet<DrawablePoint>(new Comparator<DrawablePoint>() {
            @Override
            public int compare(DrawablePoint point1, DrawablePoint point2) {
                if (point1.getX() > point2.getX()) return 1;
                else if (point1.getX() < point2.getX()) return -1;
                else if (point1.getY() > point2.getY()) return 1;
                else if (point1.getY() < point2.getY()) return -1;
                else return 0;
            }
        });

        for (int index = 0; index < numberOfPoints; index++) {
            randomizedPoints.add(new DrawablePoint((int)(Math.random() * maxX), (int)(Math.random() * maxY)));
        }

        return randomizedPoints;
    }
}
