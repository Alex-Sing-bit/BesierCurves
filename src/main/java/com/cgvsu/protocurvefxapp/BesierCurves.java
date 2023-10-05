package com.cgvsu.protocurvefxapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;

public class BesierCurves {
    public static void drawBesierCurve(final GraphicsContext graphicsContext, ArrayList<Point2D> points) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int n = points.size() - 1;//Степень кривой

        ArrayList<Point2D> cK = new ArrayList<>();
        double shag = 0.1;
        double t = 0;

        cK.add(points.get(0));

        while (Math.abs(t - (1 + shag)) >= 10e-10) {
            double cX = 0;
            double cY = 0;
            for (int k = 0; k <= n; k++) {
                double b = basicFunction(n, k, t);
                cX += points.get(k).getX() * b;
                cY += points.get(k).getY() * b;
            }
            cK.add(new Point2D(cX, cY));
            t += shag;
        }

        for (int i = 1; i < cK.size(); i++) {
            Point2D lastPoint = cK.get(i - 1);
            Point2D clickPoint = cK.get(i);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }
    }

    public static double basicFunction(int n, int k, double t) {
        int c = factorial(n)/factorial(k)/factorial(n - k);
        return c * Math.pow(t, k) * Math.pow(1 - t, n - k);
    }
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }
}


