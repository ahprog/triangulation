package dev.triangulation.compute;

import dev.triangulation.graphics.DrawableLine;
import dev.triangulation.graphics.DrawablePoint;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.SortedSet;

public class ConvexHull {

    public ArrayList<DrawablePoint> pointsConvHull;

    public ConvexHull(ArrayList<DrawablePoint> points){
        pointsConvHull = getConvexHull(points);
    }

    public ArrayList<DrawablePoint> getConvexHull(ArrayList<DrawablePoint> points) {
        ArrayList<DrawablePoint> convexHull = new ArrayList<DrawablePoint>();

        ArrayList<DrawablePoint> pointsList = new ArrayList<DrawablePoint>(points);

        int test = pointsList.size();
        if( test <= 3){


            if( test == 3 ){
                if(isAbove(pointsList.get(0), pointsList.get(1), pointsList.get(2))<0) {
                    DrawablePoint ptTemp = pointsList.get(1);
                    pointsList.remove( 1);
                    pointsList.add(ptTemp);
                }
            }
            convexHull.addAll(pointsList);

            return convexHull;
        }

        ArrayList<DrawablePoint> pointsList1 = new ArrayList<DrawablePoint>();
        ArrayList<DrawablePoint> pointsList2 = new ArrayList<DrawablePoint>();

        // On rempli deux sous-listes
        for(int index=0 ; index < (pointsList.size()/2) ; index ++){
            pointsList1.add(pointsList.get(index));
        }
        for(int index=(pointsList.size()/2) ; index < points.size() ; index ++){
            pointsList2.add(pointsList.get(index));
        }

        pointsList1 = getConvexHull(pointsList1);
        pointsList2 = getConvexHull(pointsList2);

        convexHull = fusion(pointsList1, pointsList2);

        return convexHull;
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
                ptMaxHeight1 = (ptMaxHeight1+1)%pointsList1.size();
                continu = true;
            }
            if(isAbove(pointsList1.get(ptMaxHeight1), pointsList2.get(ptMaxHeight2), pointsList2.get((ptMaxHeight2-1+pointsList2.size())%pointsList2.size())) > 0){
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
                ptMinHeight1 = (ptMinHeight1-1+pointsList1.size())%pointsList1.size();
                continu = true;
            }
            if(isAbove(pointsList1.get(ptMinHeight1), pointsList2.get(ptMinHeight2), pointsList2.get((ptMinHeight2+1)%pointsList2.size())) < 0){
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



        return convexHull;
    }

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
