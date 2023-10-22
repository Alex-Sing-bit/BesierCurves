package ru.vsu.cs.baklanova.besiercurvefxapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;
import java.util.Map;

public class BesierCurves {
    public static void drawBesierCurve(final GraphicsContext graphicsContext, ArrayList<Point2D> points) {
//        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        if (points == null) {
            return;
        }
        if (points.size() <= 1) {
            return;
        }
        int n = points.size() - 1;//Степень кривой

        ArrayList<Point2D> cK = new ArrayList<>();
        double shag = 0.005;
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
        //long c;
        double c;
        if (k < n - k) {
            //c = partFactorial(n, n - k) / partFactorial(k, 1);
            c = simultaneousDivision(n, n - k, k, 1);
        } else {
            //c = partFactorial(n, k) / partFactorial(n - k, 1);
            c = simultaneousDivision(n, k, n - k, 1);
        }
        return c * Math.pow(t, k) * Math.pow(1 - t, n - k);
    }
    public static long partFactorial(int n, int m) {
        if (n == 0) {
            return 1;
        } else if (n == m) {
            return 1;
        } else {
            return n * partFactorial(n-1, m);
        }
    }

    public static double simultaneousDivision(int n1, int n2, int m1, int m2) {

        double res = 1;

        int j = m1;
        for (int i = n1; i > n2; i--) {
            if (j < m2 || j == 0) {
                return res;
            }

            res = res * i / j;

            j--;
        }

        return res;
    }


}


