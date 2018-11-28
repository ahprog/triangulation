package dev.triangulation.graphics;

import dev.triangulation.compute.Triangle;
import dev.triangulation.compute.TriangulateShape;

import java.awt.*;
import java.util.ArrayList;

public class DrawTriangulateShape {

    private TriangulateShape triangles;

    public DrawTriangulateShape(ArrayList<DrawablePoint> points){
        triangles = new TriangulateShape(points);
    }

    public void draw(Graphics graphics){
        /*
        for(Triangle triangle : triangles.getTriangles()){
            graphics.drawLine(triangle.getPt1().getX(), triangle.getPt1().getY(), triangle.getPt2().getX(), triangle.getPt2().getY());
            graphics.drawLine(triangle.getPt2().getX(), triangle.getPt2().getY(), triangle.getPt3().getX(), triangle.getPt3().getY());
            graphics.drawLine(triangle.getPt3().getX(), triangle.getPt3().getY(), triangle.getPt1().getX(), triangle.getPt1().getY());
        }*/

        for(DrawTriangle triangle : triangles.getTriangles()){
            triangle.draw(graphics);
        }
    }
}
