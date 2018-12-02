package dev.triangulation.graphics;

import java.awt.Graphics;

public class DrawablePoint {

    private double x, y;

    public DrawablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics) {
        graphics.fillOval((int)x-5, (int)y-5, 10, 10);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
