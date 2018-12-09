package dev.triangulation.compute;

import dev.triangulation.graphics.DrawTriangle;
import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.util.ArrayList;

/**
 * Un ensemble de triangles issus d'une triangulation de Delaunay.
 * On utilise un algorithme d'Hermeline.
 */
public class DelaunayTriangulation {

    private ArrayList<DrawTriangle> triangleArrayList = new ArrayList<DrawTriangle>();
    //Pour le debug, les cercles circonscrits
    public ArrayList<Circle> cirCircle = new ArrayList<Circle>();

    /**
     * Permet de ne calculer qu'une fois les cercles circonscrit
     */
    public class Circle{
        public double centerX, centerY;
        public double radius;

        Circle(double centerX, double centerY, double radius){
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }

    public DelaunayTriangulation(ArrayList<DrawablePoint> points){
        getDelaunayTriangulation(points);
    }

    public void getDelaunayTriangulation(ArrayList<DrawablePoint> points){
        if (points.size() == 0) return;

        ArrayList<DrawTriangle> listTriangle = new ArrayList<DrawTriangle>();
        //ArrayList<Circle> cirCircle = new ArrayList<Circle>();

        /**
         * Definition des valeurs min et max de l'ensemble de points pour la création de la boite servant à la triangulation
         */
        double maxX = points.get(0).getX();
        double minX = points.get(0).getX();
        double maxY = points.get(0).getY();
        double minY = points.get(0).getY();

        for(int index=1 ; index<points.size(); index++){
            if(points.get(index).getX() > maxX)
                maxX = points.get(index).getX();
            if(points.get(index).getX() < minX)
                minX = points.get(index).getX();
            if(points.get(index).getY() > maxY)
                maxY = points.get(index).getY();
            if(points.get(index).getY() < minY)
                minY = points.get(index).getY();
        }

        /**
         * Création de la boite
         */
        ArrayList<DrawablePoint> pointBoite = new ArrayList<DrawablePoint>();
        pointBoite.add(new DrawablePoint(minX-maxX*9999, minY-maxY*9999));
        pointBoite.add(new DrawablePoint(minX-maxX*9999, maxY*10000));
        pointBoite.add(new DrawablePoint(maxX*10000, maxY*10000));
        pointBoite.add(new DrawablePoint(maxX*10000, minY-maxY*9999));

        listTriangle.add(new DrawTriangle(pointBoite.get(0), pointBoite.get(1), pointBoite.get(2)));
        listTriangle.add(new DrawTriangle(pointBoite.get(2), pointBoite.get(3), pointBoite.get(0)));

        cirCircle.add(getCirCircle(listTriangle.get(0)));
        cirCircle.add(getCirCircle(listTriangle.get(1)));


        /**
         * Triangulation suivant l'algorithme d'Hermeline
         * https://hal.inria.fr/inria-00075590/document page 16
         */
        for(DrawablePoint point : points){
            ArrayList<DrawableLine> linesExt = new ArrayList<DrawableLine>();

            for(int index=0 ; index<cirCircle.size(); index++){
                if(isInCirCircle(point, cirCircle.get(index))){
                  linesExt.add(new DrawableLine(listTriangle.get(index).getTriangle().getPt1(), listTriangle.get(index).getTriangle().getPt2()));
                    linesExt.add(new DrawableLine(listTriangle.get(index).getTriangle().getPt2(), listTriangle.get(index).getTriangle().getPt3()));
                    linesExt.add(new DrawableLine(listTriangle.get(index).getTriangle().getPt3(), listTriangle.get(index).getTriangle().getPt1()));

                    listTriangle.remove(index);
                    cirCircle.remove(index);
                    index--;
                }
            }

            linesExt = getLinesExt(linesExt);

            for(int index=0 ; index < linesExt.size() ; index++){
                listTriangle.add(new DrawTriangle(point, linesExt.get(index).getPoint1(), linesExt.get(index).getPoint2()));
                cirCircle.add(getCirCircle(listTriangle.get(listTriangle.size()-1)));
            }
        }

        //On supprime les triangles relié à la box
        deleteTriangleFromPoint(pointBoite.get(0), listTriangle);
        deleteTriangleFromPoint(pointBoite.get(1), listTriangle);
        deleteTriangleFromPoint(pointBoite.get(2), listTriangle);
        deleteTriangleFromPoint(pointBoite.get(3), listTriangle);

        triangleArrayList = listTriangle;
    }

    public boolean isInCirCircle(DrawablePoint newPoint, Circle circle){
        return (Math.sqrt((newPoint.getX()-circle.centerX)*(newPoint.getX()-circle.centerX) + (newPoint.getY()-circle.centerY)*(newPoint.getY()-circle.centerY))) < circle.radius;
    }

    /**
     * Retourne le cercle circonscrit au triangle
     *
     * @param drawTriangle
     */
    public Circle getCirCircle(DrawTriangle drawTriangle) {
        Triangle triangle = drawTriangle.getTriangle();
        DrawablePoint pt1 = triangle.getPt1();
        DrawablePoint pt2 = triangle.getPt2();
        DrawablePoint pt3 = triangle.getPt3();

        if (pt2.getY() - pt1.getY() == 0 || pt3.getY() - pt2.getY() == 0) {
            DrawablePoint ptTemp = pt1;
            pt1 = pt2;
            pt2 = pt3;
            pt3 = ptTemp;

            if (pt2.getY() - pt1.getY() == 0 || pt3.getY() - pt2.getY() == 0) {
                ptTemp = pt1;
                pt1 = pt2;
                pt2 = pt3;
                pt3 = ptTemp;
            }
        }

        // Premiere mediatrice
        DrawablePoint midPoint1 = new DrawablePoint((pt1.getX() + pt2.getX()) / 2, (pt1.getY() + pt2.getY()) / 2);
        double slope1 = -((pt2.getX() - pt1.getX()) / (pt2.getY() - pt1.getY()));
        double const1 = midPoint1.getY() - midPoint1.getX() * slope1;

        // Seconde mediatrice
        DrawablePoint midPoint2 = new DrawablePoint((pt2.getX() + pt3.getX()) / 2, (pt2.getY() + pt3.getY()) / 2);
        double slope2 = -((pt3.getX() - pt2.getX()) / (pt3.getY() - pt2.getY()));
        double const2 = midPoint2.getY() - midPoint2.getX() * slope2;

        // Cercle circonscrit
        double centerX = (const2 - const1) / (slope1 - slope2);
        double centerY = slope1 * centerX + const1;
        double radius = Math.sqrt((centerX - pt1.getX()) * (centerX - pt1.getX()) + (centerY - pt1.getY()) * (centerY - pt1.getY()));

        return new Circle(centerX, centerY, radius);
        /*
        Triangle triangle = drawTriangle.getTriangle();
        double centerX, centerY;
        double mediatrice1X, mediatrice1Y, mediatrice2X, mediatrice2Y;
        // y = slope*x + const
        double slope1, slope2, const1, const2;

        DrawablePoint pt1 = triangle.getPt1();
        DrawablePoint pt2 = triangle.getPt2();
        DrawablePoint pt3 = triangle.getPt3();


        if(pt2.getY()-pt1.getY()==0 || pt3.getY()-pt2.getY()==0){
            DrawablePoint ptTemp = pt1;
            pt1 = pt2;
            pt2 = pt3;
            pt3 = ptTemp;

            if(pt2.getY()-pt1.getY()==0 || pt3.getY()-pt2.getY()==0) {
                ptTemp = pt1;
                pt1 = pt2;
                pt2 = pt3;
                pt3 = ptTemp;
            }
        }

        //slope1 = (pt1.getY()-pt2.getY())/(pt1.getX()-pt2.getX());
        //slope2 = (pt2.getY()-pt3.getY())/(pt2.getX()-pt3.getX());
        slope1 = (double)(pt2.getX()-pt1.getX())/(double)(pt2.getY()-pt1.getY());
        slope2 = (double)(pt3.getX()-pt2.getX())/(double)(pt3.getY()-pt2.getY());

        mediatrice1X = (pt2.getX()-pt1.getX())/2;
        mediatrice1Y = (pt2.getY()-pt1.getY())/2;

        mediatrice2X = (pt3.getX()-pt2.getX())/2;
        mediatrice2Y = (pt3.getY()-pt2.getY())/2;

        const1 = mediatrice1Y-slope1*mediatrice1X;
        const2 = mediatrice2Y-slope2*mediatrice2X;

        centerX = (const2-const1)/(slope1-slope2);
        centerY = slope1*centerX+const1;

        double radius = Math.sqrt((centerX-pt1.getX())*(centerX-pt1.getX()) + (centerY-pt1.getY())*(centerY-pt1.getY()));


        return new Circle(centerX, centerY, radius);
        */
    }

    public ArrayList<DrawableLine> getLinesExt(ArrayList<DrawableLine> listLines){

        //ArrayList<DrawablePoint> points = new ArrayList<DrawablePoint>();
        ArrayList<DrawableLine> linesExt = new ArrayList<DrawableLine>();

        /*
        Triangle triangle;
        for(int index=0 ; index<listTriangle.size(); index++){
            triangle = listTriangle.get(index).getTriangle();

            points.add(triangle.getPt1());
            points.add(triangle.getPt2());
            points.add(triangle.getPt3());
        }
        */

        boolean ext;

        while(!listLines.isEmpty()){
            DrawableLine line = listLines.remove(0);
            ext = true;

            for(int index=0 ; index<listLines.size() ; index++){
                if(isLinesEqual(line, listLines.get(index))){
                    ext = false;
                    listLines.remove(listLines.get(index));
                    index--;
                }
            }
/*
            if(listLines.remove(line)){
                boolean isStillDoublon = false;
                do{
                    isStillDoublon = listLines.remove(line);
                }
                while(isStillDoublon);
                ext = false;
            }
*/
            if(ext){
                linesExt.add(line);
            }
        }

        return linesExt;
    }

    public boolean isLinesEqual(DrawableLine line1, DrawableLine line2){
        if(line1.getPoint1()==line2.getPoint1() || line1.getPoint1()==line2.getPoint2()){
            if(line1.getPoint2()==line2.getPoint1() || line1.getPoint2()==line2.getPoint2()){
                return true;
            }
        }
        return false;
    }

    public void deleteTriangleFromPoint(DrawablePoint pt, ArrayList<DrawTriangle> listTriangle){
        Triangle triangle;

        for(int index=0 ; index<listTriangle.size() ; index++){
            triangle = listTriangle.get(index).getTriangle();
            if(triangle.getPt1()==pt || triangle.getPt2()==pt || triangle.getPt3()==pt){
                listTriangle.remove(index);
                index--;
            }
        }
    }

    public ArrayList<DrawTriangle> getTriangle() {
        return triangleArrayList;
    }

}
