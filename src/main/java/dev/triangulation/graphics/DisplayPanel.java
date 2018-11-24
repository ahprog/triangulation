package dev.triangulation.graphics;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.SortedSet;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

    private SortedSet<DrawablePoint> points;

    public DisplayPanel(SortedSet<DrawablePoint> points_) {
        points = points_;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                System.out.print(points.size());
                points.add(new DrawablePoint(event.getX(), event.getY()));
                repaint();
            }
        });
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
}
