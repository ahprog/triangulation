package dev.triangulation;

import dev.triangulation.graphics.AppWindow;
import dev.triangulation.graphics.DrawablePoint;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SortedSet<DrawablePoint> points = new TreeSet<DrawablePoint>(new Comparator<DrawablePoint>() {
            @Override
            public int compare(DrawablePoint point1, DrawablePoint point2) {
                if (point1.getX() > point2.getX()) return 1;
                else if (point1.getX() < point2.getX()) return -1;
                else return 0;
            }
        });

        AppWindow appWindow = new AppWindow(600, 500, points);
    }
}
