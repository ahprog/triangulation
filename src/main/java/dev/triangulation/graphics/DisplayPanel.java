package dev.triangulation.graphics;

import dev.triangulation.compute.DelaunayTriangulation;

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
    private DrawConvexHull convexHull;
    private DrawTriangulateShape triangulation;
    private DrawDelaunayTriangulation triangluationDelaunay;

    private boolean convexHullVisible, triangulationVisible, triangulationDelaunayVisible;
    private boolean drawDebugCircle = false;

    public DisplayPanel(SortedSet<DrawablePoint> points_) {
        points = points_;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                points.add(new DrawablePoint(event.getX(), event.getY()));
                newTriangulationDelaunay(getPoints());
                setTriangulationDelaunayVisible(true);
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
        if(convexHullVisible)
            convexHull.draw(g);
        if(triangulationVisible)
            triangulation.draw(g);
        if(triangulationDelaunayVisible)
            triangluationDelaunay.draw(g, drawDebugCircle);

        g.setColor(oldColor);
    }

    public void newConvHull(ArrayList<DrawablePoint> points){
        this.convexHull = new DrawConvexHull(points);
        repaint();
    }

    public void newTriangulation(ArrayList<DrawablePoint> points){
        this.triangulation = new DrawTriangulateShape(points);
        repaint();
    }

    public void newTriangulationDelaunay(ArrayList<DrawablePoint> points){
        this.triangluationDelaunay = new DrawDelaunayTriangulation(points);
        repaint();
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
        convexHullVisible = false;
        triangulationVisible = false;
        triangulationDelaunayVisible = false;
        repaint();
    }

    public void setConvexHullVisible(boolean newValue){
        this.convexHullVisible = newValue;
    }

    public void setTriangulationVisible(boolean newValue){
        this.triangulationVisible = newValue;
    }

    public void setTriangulationDelaunayVisible(boolean newValue) { this.triangulationDelaunayVisible = newValue; }
}
