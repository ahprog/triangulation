package dev.triangulation.compute;

import dev.triangulation.graphics.DrawablePoint;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Genere un ensemble de points en positions aleatoires dans un rectangle
 * dont les dimensions sont donnees en argument du constructeur.
 */
public class PointsGenerator {

    public static SortedSet<DrawablePoint> generate(int numberOfPoints, int maxX, int maxY) {
        int offset = 0;

        if (maxX > 30 && maxY > 30) {
            offset = 10;
        }

        SortedSet<DrawablePoint> randomizedPoints = new TreeSet<DrawablePoint>(new PointsComparator());

        for (int index = 0; index < numberOfPoints; index++) {
            randomizedPoints.add(new DrawablePoint((int)(offset + (Math.random() * (maxX - (offset * 2)))), (int)(offset + (Math.random() * (maxY - (offset * 2))))));
        }

        return randomizedPoints;
    }
}
