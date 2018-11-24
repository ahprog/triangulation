package dev.triangulation.graphics;

import java.awt.Graphics;

public class DrawablePoint {

    private int x, y;

    public DrawablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics) {
        graphics.fillOval(x-5, y-5, 10, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
