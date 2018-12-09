package dev.triangulation.graphics;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.SortedSet;
import javax.swing.JPanel;

/**
 * DisplayPanel est un JPanel qui affiche les points et les formes generees.
 * On peut ajouter des points en utilisant le clic de la souris.
 */
public class DisplayPanel extends JPanel {

    /**
     * Le mode dynamique genere les formes a chaque ajout de point.
     */
    private boolean dynamicCompute = true;

    /**
     * Notre ensemble de points.
     * On utilise un SortedSet customisé (voir App) pour toujours parcourir les points dans le meme ordre.
     */
    private SortedSet<DrawablePoint> points;

    /**
     * On peut generer une enveloppe convexe et une triangulation simple ou de delaunay.
     */
    private DrawConvexHull convexHull;
    private DrawTriangulateShape triangulation;
    private DrawDelaunayTriangulation triangulationDelaunay;

    /**
     * Indique quelles sont les formes a generer.
     */
    private boolean convexHullVisible = false;
    private boolean triangulationVisible = false;
    private boolean triangulationDelaunayVisible = false;

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

    /**
     * Ces fonctions permettent de mettre a jour les formes stockees dans ce DisplayPanel.
     */
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

    /**
     * Permet de generer les formes qui doivent etre calculees
     */
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

    /**
     * Calcule les formes selectionnees si on est en mode dynamique (les formes sont generees a
     * chaque ajout de points).
     *
     * @param resetShapes : permet de supprimer toutes les formes pre-existantes.
     *                    On s'en sert quand on genere aleatoirement un ensemble de point et que le mode dynamique
     *                    est desactive : il faut afficher tout les nouveaux points, mais aussi vider le DisplayPanel
     *                    de ces anciennes formes.
     */
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
