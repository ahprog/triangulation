package dev.triangulation.graphics;

import java.awt.Graphics;

public class DrawableLine {
    DrawablePoint point1;
    DrawablePoint point2;

    public DrawableLine(DrawablePoint point1, DrawablePoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public void draw(Graphics graphics) {
        graphics.drawLine((int)point1.getX(), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
    }

    public void drawLine(DrawablePoint point1, DrawablePoint point2, Graphics graphics){
        graphics.drawLine((int)point1.getX(), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
    }

    public DrawablePoint getPoint1() {
        return point1;
    }

    public DrawablePoint getPoint2() {
        return point2;
    }
}
