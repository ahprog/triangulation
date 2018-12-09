package dev.triangulation.graphics;

import dev.triangulation.compute.Triangle;
import java.awt.Graphics;

public class DrawTriangle {

    private Triangle triangle;

    public DrawTriangle(DrawablePoint pt1, DrawablePoint pt2, DrawablePoint pt3){
        triangle = new Triangle(pt1, pt2, pt3);
    }

    public void draw(Graphics graphics){
        graphics.drawLine((int)triangle.getPt1().getX(), (int)triangle.getPt1().getY(), (int)triangle.getPt2().getX(), (int)triangle.getPt2().getY());
        graphics.drawLine((int)triangle.getPt2().getX(), (int)triangle.getPt2().getY(), (int)triangle.getPt3().getX(), (int)triangle.getPt3().getY());
        graphics.drawLine((int)triangle.getPt3().getX(), (int)triangle.getPt3().getY(), (int)triangle.getPt1().getX(), (int)triangle.getPt1().getY());
    }

    public Triangle getTriangle(){
        return triangle;
    }
}
