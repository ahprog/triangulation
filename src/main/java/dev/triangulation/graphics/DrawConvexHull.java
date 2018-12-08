package dev.triangulation.graphics;

import dev.triangulation.compute.ConvexHull;
import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;

public class DrawConvexHull {

    public ConvexHull convexHull;

    public DrawConvexHull(ArrayList<DrawablePoint> points){
        convexHull = new ConvexHull(points);
    }

    public void draw(Graphics graphics) {
        if (convexHull.pointsConvHull.size() == 0) return;
        graphics.setColor(Color.BLUE);
        for(int index=0 ; index<convexHull.pointsConvHull.size()-1 ; index++) {
            graphics.drawLine((int)convexHull.pointsConvHull.get(index).getX(), (int)convexHull.pointsConvHull.get(index).getY(), (int)convexHull.pointsConvHull.get(index+1).getX(), (int)convexHull.pointsConvHull.get(index+1).getY());
        }
        graphics.drawLine((int)convexHull.pointsConvHull.get(convexHull.pointsConvHull.size()-1).getX(), (int)convexHull.pointsConvHull.get(convexHull.pointsConvHull.size()-1).getY(), (int)convexHull.pointsConvHull.get(0).getX(), (int)convexHull.pointsConvHull.get(0).getY());
        graphics.setColor(Color.BLACK);
    }
}
