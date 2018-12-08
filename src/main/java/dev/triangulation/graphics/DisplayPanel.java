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

    private boolean dynamicCompute = true;
    private SortedSet<DrawablePoint> points;
    private DrawConvexHull convexHull;
    private DrawTriangulateShape triangulation;
    private DrawDelaunayTriangulation triangulationDelaunay;

    private boolean convexHullVisible = false, triangulationVisible = false, triangulationDelaunayVisible = false;
    private boolean drawDebugCircle = false;

    public DisplayPanel(SortedSet<DrawablePoint> points_) {
        points = points_;
        computeShapes();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                points.add(new DrawablePoint(event.getX(), event.getY()));
                computeShapesDynamic(false);
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

        triangulation.draw(g);
        triangulationDelaunay.draw(g, drawDebugCircle);
        convexHull.draw(g);

        g.setColor(oldColor);
    }

    public void newConvHull(ArrayList<DrawablePoint> points){
        this.convexHull = new DrawConvexHull(points);
        repaint();
    }

    public void resetConvHull() {
        this.convexHull = new DrawConvexHull(new ArrayList<DrawablePoint>());
        repaint();
    }

    public void newTriangulation(ArrayList<DrawablePoint> points){
        this.triangulation = new DrawTriangulateShape(points);
        repaint();
    }

    public void resetTriangulation() {
        this.triangulation = new DrawTriangulateShape(new ArrayList<DrawablePoint>());
        repaint();
    }

    public void newTriangulationDelaunay(ArrayList<DrawablePoint> points){
        this.triangulationDelaunay = new DrawDelaunayTriangulation(points);
        repaint();
    }

    public void resetDelaunayTriangulation() {
        this.triangulationDelaunay = new DrawDelaunayTriangulation(new ArrayList<DrawablePoint>());
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
        computeShapes();
        repaint();
    }

    public void setConvexHullVisible(boolean newValue){
        this.convexHullVisible = newValue;
    }

    public void setTriangulationVisible(boolean newValue){
        this.triangulationVisible = newValue;
    }

    public void setTriangulationDelaunayVisible(boolean newValue) {
        this.triangulationDelaunayVisible = newValue;
    }

    public void switchDynamicCompute() {
        dynamicCompute = !dynamicCompute;
        computeShapesDynamic(false);
    }

    public void computeShapes() {
        if (convexHullVisible) {
            newConvHull(getPoints());
        }
        else {
            resetConvHull();
        }

        if (triangulationDelaunayVisible) {
            newTriangulationDelaunay(getPoints());
        }
        else {
            resetDelaunayTriangulation();
        }

        if (triangulationVisible) {
            newTriangulation(getPoints());
        }
        else {
            resetTriangulation();
        }
    }

    public void computeShapesDynamic(boolean resetShapes) {
        if (!dynamicCompute) {
            if (resetShapes) {
                resetTriangulation();
                resetDelaunayTriangulation();
                resetConvHull();
            }
            return;
        }
        computeShapes();
    }
}
