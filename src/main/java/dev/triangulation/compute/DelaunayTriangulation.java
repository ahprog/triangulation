package dev.triangulation.compute;

import dev.triangulation.graphics.DrawTriangle;
import dev.triangulation.graphics.DrawablePoint;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class DelaunayTriangulation {

    private ArrayList<DrawTriangle> triangleArrayList = new ArrayList<DrawTriangle>();

    /**
     * Permet de ne calculer qu'une fois les cercles circonscrit
     */
    private class Circle{
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

        ArrayList<DrawTriangle> listTriangle = new ArrayList<DrawTriangle>();
        ArrayList<Circle> cirCircle = new ArrayList<Circle>();

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
        pointBoite.add(new DrawablePoint(minX-maxX, minY-maxY));
        pointBoite.add(new DrawablePoint(minX-maxX, maxY*2));
        pointBoite.add(new DrawablePoint(maxX*2, maxY*2));
        pointBoite.add(new DrawablePoint(maxX*2, minY-maxY));

        listTriangle.add(new DrawTriangle(pointBoite.get(0), pointBoite.get(1), pointBoite.get(2)));
        listTriangle.add(new DrawTriangle(pointBoite.get(2), pointBoite.get(3), pointBoite.get(0)));

        cirCircle.add(getCirCircle(listTriangle.get(0)));
        cirCircle.add(getCirCircle(listTriangle.get(1)));


        /**
         * Triangulation suivant l'algorithme d'Hermeline
         * https://hal.inria.fr/inria-00075590/document page 16
         */
        for(DrawablePoint point : points){
            ArrayList<DrawablePoint> ptEnvExt = new ArrayList<DrawablePoint>();

            for(int index=0 ; index<cirCircle.size(); index++){
                if(isInCirCircle(point, cirCircle.get(index))){
                    //if(!(ptEnvExt.contains(listTriangle.get(index).getTriangle().getPt1())))
                        ptEnvExt.add(listTriangle.get(index).getTriangle().getPt1());
                    //(!(ptEnvExt.contains(listTriangle.get(index).getTriangle().getPt2())))
                        ptEnvExt.add(listTriangle.get(index).getTriangle().getPt2());
                    //if(!(ptEnvExt.contains(listTriangle.get(index).getTriangle().getPt3())))
                        ptEnvExt.add(listTriangle.get(index).getTriangle().getPt3());

                    listTriangle.remove(index);
                    cirCircle.remove(index);
                    index--;
                }
            }

            /*
            SortedSet<DrawablePoint> ptEnvExtSorted = new TreeSet<DrawablePoint>(new PointsComparator());
            ptEnvExtSorted.addAll(ptEnvExt);
            ptEnvExt = new ArrayList<DrawablePoint>(ptEnvExtSorted);

            ConvexHull newVertex = new ConvexHull(ptEnvExt);
            */

            ptEnvExt = getPointExt(ptEnvExt);

            for(int index=0 ; index < ptEnvExt.size() ; index++){
                listTriangle.add(new DrawTriangle(point, ptEnvExt.get(index), ptEnvExt.get((index+1)%ptEnvExt.size())));
                cirCircle.add(getCirCircle(listTriangle.get(listTriangle.size()-1)));
            }
        }

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
        double slope1 = (pt2.getX() - pt1.getX()) / (pt2.getY() - pt1.getY());
        double const1 = midPoint1.getY() - midPoint1.getX() * slope1;

        // Seconde mediatrice
        DrawablePoint midPoint2 = new DrawablePoint((pt2.getX() + pt3.getX()) / 2, (pt2.getY() + pt3.getY()) / 2);
        double slope2 = (pt3.getX() - pt2.getX()) / (pt3.getY() - pt2.getY());
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

    public ArrayList<DrawablePoint> getPointExt(ArrayList<DrawablePoint> listPoints){

        //ArrayList<DrawablePoint> points = new ArrayList<DrawablePoint>();
        ArrayList<DrawablePoint> pointsExt = new ArrayList<DrawablePoint>();

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

        while(!listPoints.isEmpty()){
            DrawablePoint pt = listPoints.remove(0);
            ext = true;

            if(listPoints.remove(pt)){
                if(listPoints.remove(pt)){
                    ext = false;
                }
            }

            if(ext){
                pointsExt.add(pt);
            }
        }

        return pointsExt;
    }

    public ArrayList<DrawTriangle> getTriangle() {
        return triangleArrayList;
    }

}
