package ru.vsu.cs.baklanova.besiercurvefxapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class BesierCurves {
    public static void drawBesierCurve(final GraphicsContext graphicsContext, ArrayList<Point2D> points) {
        if (points == null) {
            return;
        }
        int size  = points.size();
        if (size <= 1) {
            return;
        }
        int n = size - 1;//Степень кривой

        ArrayList<Point2D> cK = new ArrayList<>();
        double step = 0.1;

        final int MAX_POINTS_FOR_STEP = 200;
        if (size > MAX_POINTS_FOR_STEP) {
            step /= MAX_POINTS_FOR_STEP;
        } else {
            step = 0.1 / size;
        }
        double t = 0;

        cK.add(points.get(0));

        while (Math.abs(t - (1 + step)) >= 10e-10) {
            double[] coef1 = new double[n + 1];

            double tt0 = Math.pow(t, n);
            double tt1 = Math.pow(1 - t, n);

            double a1 = tt0;
            double a2 = tt1;

            if (t > 10e-300 && 1 - t > 10e-300) {
                for (int i = 1; i <= (n + 1) / 2; i++) {
                    tt1 = tt1 / (1 - t) * t;
                    tt0 = tt0 / t * (1 - t);

                    coef1[i] = tt1;
                    coef1[n - i] = tt0;
                }
            }
            coef1[0] = a2;
            coef1[n] = a1;

            double cX = 0;
            double cY = 0;
            for (int k = 0; k <= n; k++) {
                double b = basicFunction(n, k, t) * coef1[k];
                cX += points.get(k).getX() * b;
                cY += points.get(k).getY() * b;
            }
            cK.add(new Point2D(cX, cY));
            t += step;
        }

        for (int i = 1; i < cK.size(); i++) {
            Point2D lastPoint = cK.get(i - 1);
            Point2D clickPoint = cK.get(i);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }
    }

    public static double basicFunction(int n, int k, double t) {
        double c;
        if (k < n - k) {
            c = simultaneousDivision(n, n - k, k, 1);
        } else {
            c = simultaneousDivision(n, k, n - k, 1);
        }
        return c;

    }

    private static double simultaneousDivision(int n1, int n2, int m1, int m2) {
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


}


