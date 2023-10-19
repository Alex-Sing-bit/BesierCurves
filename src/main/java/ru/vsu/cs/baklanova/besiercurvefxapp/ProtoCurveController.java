package ru.vsu.cs.baklanova.besiercurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ProtoCurveController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<Point2D>();

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        double width = canvas.getWidth() - 0.5;
        double height = canvas.getHeight() - 0.5;
        graphicsContext.strokeLine(0, 0, width, 0);
        graphicsContext.strokeLine(0, height, width, height);
        graphicsContext.strokeLine(width, 0, width, height);

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(graphicsContext, event);
                case MIDDLE -> handleMiddleClick(graphicsContext, event);
            }
        });
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        points.add(clickPoint);

        final int POINT_RADIUS = 2;

        //System.out.println(BesierCurves.partFactorial(13, 12));

        if (points.size() < 30) {
            graphicsContext.clearRect(1, 1, 798.5, 598);
            for (int i = 0; i < points.size(); i++) {
                graphicsContext.fillOval(
                        points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS,
                        2 * POINT_RADIUS, 2 * POINT_RADIUS);
            }
            BesierCurves.drawBesierCurve(graphicsContext, points);

        }
        if (points.size() == 29) {
            graphicsContext.fillText("Больше точек не поддерживается. Нажмите на колесико мыши для очистки холста.", 20, 590);
            System.out.println("Больше точек не поддерживается. Нажмите на колесико мыши для очистки холста.");
        }


        /*if (points.size() > 0) {
            final Point2D lastPoint = points.get(points.size() - 1);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }*/

    }

    private void handleMiddleClick(GraphicsContext graphicsContext, MouseEvent event) {
        points.clear();
        graphicsContext.clearRect(1, 1, 798.5, 598);
    }
}