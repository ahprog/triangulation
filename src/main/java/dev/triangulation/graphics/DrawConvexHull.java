package dev.triangulation.graphics;

import dev.triangulation.compute.ConvexHull;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class DrawConvexHull {

    private ConvexHull convexHull;

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
