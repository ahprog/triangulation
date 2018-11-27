package dev.triangulation.graphics;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

    private SortedSet<DrawablePoint> points;

    public DisplayPanel(SortedSet<DrawablePoint> points_) {
        points = points_;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                points.add(new DrawablePoint(event.getX(), event.getY()));
                repaint();
            }
        });
        this.setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        for (DrawablePoint point : points) {
            point.draw(g);
        }
        g.setColor(oldColor);
    }

    public ArrayList<DrawablePoint> getPoints() {
        ArrayList<DrawablePoint> pointsList = new ArrayList<DrawablePoint>(points);
        return pointsList;
    }

    public void newPoints(SortedSet<DrawablePoint> points) {
        this.points = points;
        repaint();
    }

    public void clear() {
        points.clear();
        repaint();
    }
}
