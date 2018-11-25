package dev.triangulation.compute;

import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class TriangulateShape {

    public static List<DrawableLine> getConvexHull(SortedSet<DrawablePoint> points) {
        List<DrawableLine> lines = new ArrayList<DrawableLine>();

        //On copie les points ordonnés du SortedSet dans une List pour pouvoir la diviser en deux groupes
        List<DrawablePoint> pointsList = new ArrayList<DrawablePoint>(points);

        /**
         * Ici c'est l'algorithme de triangulation vu en cours !
         * On ne retourne que les lignes de l'enveloppe convexe
         */


        return lines;
    }

    public static List<DrawableLine> getTriangulationShape(SortedSet<DrawablePoint> points) {
        List<DrawableLine> lines = new ArrayList<DrawableLine>();

        //On copie les points ordonnés du SortedSet dans une List pour pouvoir la diviser en deux groupes
        List<DrawablePoint> pointsList = new ArrayList<DrawablePoint>(points);

        /**
         * Ici c'est l'algorithme de triangulation vu en cours !
         * On retourne toutes les lignes de triangulation traitées
         */


        return lines;
    }

    public static void drawConvexHull(SortedSet<DrawablePoint> points, Graphics graphics) {
        List<DrawableLine> lines = TriangulateShape.getConvexHull(points);
        for (DrawableLine line : lines) {
            line.draw(graphics);
        }
    }

    public static void drawTriangulationShape(SortedSet<DrawablePoint> points, Graphics graphics) {
        List<DrawableLine> lines = TriangulateShape.getTriangulationShape(points);
        for (DrawableLine line : lines) {
            line.draw(graphics);
        }
    }
}
