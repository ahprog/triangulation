package dev.triangulation.compute;

import dev.triangulation.graphics.DrawTriangle;
import dev.triangulation.graphics.DrawablePoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Un triangulation simple d'un ensemble de points.
 */
public class TriangulateShape {


    private ArrayList<DrawTriangle> triangleArrayList = new ArrayList<DrawTriangle>();

    public TriangulateShape(ArrayList<DrawablePoint> points){
        getTriangulationShape(points);
    }


    public ArrayList<DrawablePoint> getTriangulationShape(ArrayList<DrawablePoint> points) {
        if (points.size() == 0) return new ArrayList<DrawablePoint>();
        if(points.size() <= 3) {
            if (points.size() == 3) {
                if(isAbove(points.get(0), points.get(1), points.get(2))<0){
                    DrawablePoint ptTemp = points.get(1);
                    points.remove( 1);
                    points.add(ptTemp);
                }
                this.triangleArrayList.add(new DrawTriangle(points.get(0), points.get(1), points.get(2)));
            }
            return points;
        }

        ArrayList<DrawablePoint> ptList1 = new ArrayList<DrawablePoint>();
        ArrayList<DrawablePoint> ptList2 = new ArrayList<DrawablePoint>();


        // On rempli deux sous-listes
        for(int index=0 ; index < (points.size()/2) ; index ++){
            ptList1.add(points.get(index));
        }
        for(int index=(points.size()/2) ; index < points.size() ; index ++){
            ptList2.add(points.get(index));
        }

        ptList1 = getTriangulationShape(ptList1);
        ptList2 = getTriangulationShape(ptList2);

        points = fusion(ptList1, ptList2);

        return points;
    }

    public  ArrayList<DrawablePoint> fusion(List<DrawablePoint> pointsList1, List<DrawablePoint> pointsList2){

        //Le point le plus à droite de la liste de point 1
        int ptMaxAbs = 0;
        for(int index=0 ; index<pointsList1.size() ; index++ )
            if(pointsList1.get(index).getX() > pointsList1.get(ptMaxAbs).getX())
                ptMaxAbs = index;

        //Le point le plus à gauche de la liste de point2
        int ptMinAbs = 0;


        //Pour trouver le segment du haut
        int ptMaxHeight1 = ptMaxAbs;
        int ptMaxHeight2 = ptMinAbs;

        boolean continu = true;
        while(continu){
            continu = false;

            if(isAbove(pointsList2.get(ptMaxHeight2), pointsList1.get(ptMaxHeight1), pointsList1.get((ptMaxHeight1+1)%pointsList1.size())) < 0){
                this.triangleArrayList.add(new DrawTriangle(pointsList2.get(ptMaxHeight2), pointsList1.get(ptMaxHeight1), pointsList1.get((ptMaxHeight1+1)%pointsList1.size())));
                ptMaxHeight1 = (ptMaxHeight1+1)%pointsList1.size();
                continu = true;
            }
            if(isAbove(pointsList1.get(ptMaxHeight1), pointsList2.get(ptMaxHeight2), pointsList2.get((ptMaxHeight2-1+pointsList2.size())%pointsList2.size())) > 0){
                this.triangleArrayList.add(new DrawTriangle(pointsList1.get(ptMaxHeight1), pointsList2.get(ptMaxHeight2), pointsList2.get((ptMaxHeight2-1+pointsList2.size())%pointsList2.size())));
                ptMaxHeight2 = (ptMaxHeight2-1+pointsList2.size())%pointsList2.size();
                continu = true;
            }
        }



        //Pour trouver le segment du bas
        int ptMinHeight1 = ptMaxAbs;
        int ptMinHeight2 = ptMinAbs;

        continu = true;
        while(continu){
            continu = false;

            if(isAbove(pointsList2.get(ptMinHeight2), pointsList1.get(ptMinHeight1), pointsList1.get((ptMinHeight1-1+pointsList1.size())%pointsList1.size())) > 0){
                this.triangleArrayList.add(new DrawTriangle(pointsList2.get(ptMinHeight2), pointsList1.get(ptMinHeight1), pointsList1.get((ptMinHeight1-1+pointsList1.size())%pointsList1.size())));
                ptMinHeight1 = (ptMinHeight1-1+pointsList1.size())%pointsList1.size();
                continu = true;
            }
            if(isAbove(pointsList1.get(ptMinHeight1), pointsList2.get(ptMinHeight2), pointsList2.get((ptMinHeight2+1)%pointsList2.size())) < 0){
                this.triangleArrayList.add(new DrawTriangle(pointsList1.get(ptMinHeight1), pointsList2.get(ptMinHeight2), pointsList2.get((ptMinHeight2+1)%pointsList2.size())));
                ptMinHeight2 = (ptMinHeight2+1)%pointsList2.size();
                continu = true;
            }
        }


        //Création de l'enveloppe convexe
        ArrayList<DrawablePoint> convexHull = new ArrayList();


        for(int index=0 ; index<=ptMinHeight1 ; index++){
            convexHull.add(pointsList1.get(index));
        }

        if(ptMaxHeight2==0){
            for(int index=ptMinHeight2 ; index<pointsList2.size() ; index++){
                convexHull.add(pointsList2.get(index));
            }
            convexHull.add(pointsList2.get(0));
        }
        else{
            for(int index=ptMinHeight2 ; index<=ptMaxHeight2 ; index++){
                convexHull.add(pointsList2.get(index));
            }
        }
        if(ptMaxHeight1!=0){
            for(int index=ptMaxHeight1 ; index<pointsList1.size() ; index++){
                convexHull.add(pointsList1.get(index));
            }
        }

/*
        for(int index=ptMinHeight2 ; index<ptMaxHeight2 ; index++){
            this.triangleArrayList.add(new DrawTriangle(pointsList1.get(ptMinHeight1), pointsList2.get(index), pointsList2.get((index-1)%pointsList2.size())));
        }
        for(int index=ptMinHeight1 ; index<ptMaxHeight1 ; index++){
            this.triangleArrayList.add(new DrawTriangle(pointsList1.get(index), pointsList1.get(index+1), pointsList2.get(ptMaxHeight2)));
        }
*/
        return convexHull;
    }

    public ArrayList<DrawTriangle> getTriangles() { return triangleArrayList; }

    public double isAbove(DrawablePoint pt1, DrawablePoint pt2, DrawablePoint pt3){

            double vec1X = pt1.getX() - pt2.getX();
            double vec1Y = pt1.getY() - pt2.getY();

            double vec2X = pt3.getX() - pt2.getX();
            double vec2Y = pt3.getY() - pt2.getY();

            //double angle = (vec1X * vec2X + vec1Y * vec2Y)/(Math.sqrt(vec1X*vec1X + vec1Y*vec1Y) * Math.sqrt((vec2X*vec2X + vec2Y*vec2Y)));

            double angle = (vec1X * vec2Y) - (vec1Y * vec2X);
            return angle;
        }
}
