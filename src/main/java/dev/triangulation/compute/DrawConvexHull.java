package dev.triangulation.compute;

import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;

public class DrawConvexHull {
    public static void drawConvexHull(ArrayList<DrawablePoint> points, Graphics graphics) {
        ArrayList<DrawablePoint> lines = ConvexHull.getConvexHull(points);

        for(int index=0 ; index<lines.size()-1 ; index++) {
            DrawableLine.drawLine(lines.get(index), lines.get(index+1), graphics);
        }
        DrawableLine.drawLine(lines.get(lines.size()-1), lines.get(0), graphics);
    }
}
