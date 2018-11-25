package dev.triangulation.compute;

import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class TriangulateShape {

    public static List<DrawableLine> getTriangulateShape(SortedSet<DrawablePoint> points) {
        List<DrawableLine> lines = new ArrayList<DrawableLine>();

        return lines;
    }

    public static void drawLines(List<DrawableLine> lines, Graphics graphics) {
        for (DrawableLine line : lines) {
            line.draw(graphics);
        }
    }
}
