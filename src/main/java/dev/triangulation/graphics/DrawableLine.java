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
        graphics.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }
}
