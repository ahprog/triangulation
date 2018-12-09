package dev.triangulation;

import dev.triangulation.compute.PointsComparator;
import dev.triangulation.graphics.AppWindow;
import dev.triangulation.graphics.DrawablePoint;

import java.util.SortedSet;
import java.util.TreeSet;

public class App
{
    public static void main( String[] args )
    {
        SortedSet<DrawablePoint> points = new TreeSet<DrawablePoint>(new PointsComparator());

        AppWindow appWindow = new AppWindow(1000, 600, points);
    }
}
