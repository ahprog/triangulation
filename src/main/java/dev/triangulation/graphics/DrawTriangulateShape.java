package dev.triangulation.graphics;

import dev.triangulation.compute.TriangulateShape;
import java.awt.Graphics;
import java.util.ArrayList;

public class DrawTriangulateShape {

    private TriangulateShape triangles;

    public DrawTriangulateShape(ArrayList<DrawablePoint> points){
        triangles = new TriangulateShape(points);
    }

    public void draw(Graphics graphics){

        for(DrawTriangle triangle : triangles.getTriangles()){
            triangle.draw(graphics);
        }
    }
}
