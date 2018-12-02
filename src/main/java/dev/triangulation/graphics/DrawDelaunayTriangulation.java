package dev.triangulation.graphics;

import dev.triangulation.compute.DelaunayTriangulation;

import java.awt.*;
import java.util.ArrayList;

public class DrawDelaunayTriangulation {
    private DelaunayTriangulation triangles;

    public DrawDelaunayTriangulation(ArrayList<DrawablePoint> points){
        triangles = new DelaunayTriangulation(points);
    }

    public void draw(Graphics graphics){
        for(DrawTriangle triangle : triangles.getTriangle() ){
            triangle.draw(graphics);
        }
    }
}
