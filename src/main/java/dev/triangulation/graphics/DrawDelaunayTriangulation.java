package dev.triangulation.graphics;

import dev.triangulation.compute.DelaunayTriangulation;
import java.awt.Graphics;
import java.util.ArrayList;

public class DrawDelaunayTriangulation {
    private DelaunayTriangulation triangles;

    public DrawDelaunayTriangulation(ArrayList<DrawablePoint> points){
        triangles = new DelaunayTriangulation(points);
    }

    public void draw(Graphics graphics, boolean emptyCircle){
        for(DrawTriangle triangle : triangles.getTriangle() ){
            triangle.draw(graphics);
        }
        if(emptyCircle) {
            for (DelaunayTriangulation.Circle cercle : triangles.cirCircle) {
                graphics.drawOval((int) (cercle.centerX - (cercle.radius)), (int) (cercle.centerY - (cercle.radius)), (int) cercle.radius * 2, (int) cercle.radius * 2);
            }
        }
    }
}
