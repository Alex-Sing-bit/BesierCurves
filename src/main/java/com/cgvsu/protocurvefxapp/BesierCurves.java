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
        double t = 1;

        for (int k = 0; k < n; k++) {
            int cX = 50;
            int cY = 50;
            for (int k1 = 0; k1 < n; k1++) {
                double b = basicFunction(n, k, t);
                cX += points.get(k).getX() * b;
                cY += points.get(k).getY() * b;
            }
            cK.add(new Point2D(cX, cY));
        }

        for (int i = 1; i < cK.size(); i++) {
            Point2D lastPoint = cK.get(i - 1);
            Point2D clickPoint = cK.get(i);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }
    }

    public static double basicFunction(int n, int k, double t) {
        int c = factorial(n)/factorial(k)/factorial(n - k);
        return c * Math.pow(t, n) * Math.pow(1 - t, n - k);
    }
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }
}


