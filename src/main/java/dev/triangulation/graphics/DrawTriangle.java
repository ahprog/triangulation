package dev.triangulation.graphics;

import dev.triangulation.compute.Triangle;

import java.awt.*;
import java.util.ArrayList;

public class DrawTriangle {

    private Triangle triangle;

    public DrawTriangle(DrawablePoint pt1, DrawablePoint pt2, DrawablePoint pt3){
        triangle = new Triangle(pt1, pt2, pt3);
    }

    public void draw(Graphics graphics){
        graphics.drawLine(triangle.getPt1().getX(), triangle.getPt1().getY(), triangle.getPt2().getX(), triangle.getPt2().getY());
        graphics.drawLine(triangle.getPt2().getX(), triangle.getPt2().getY(), triangle.getPt3().getX(), triangle.getPt3().getY());
        graphics.drawLine(triangle.getPt3().getX(), triangle.getPt3().getY(), triangle.getPt1().getX(), triangle.getPt1().getY());
    }
}
