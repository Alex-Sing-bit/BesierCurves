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

    private final int POINT_RADIUS = 6;

    private boolean isDragged = false;

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

        canvas.setOnMouseDragged(event -> {
            movePoint(graphicsContext, event);
        });

        canvas.setOnMouseClicked(event -> {
            isDragged = false;
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(graphicsContext, event);
                case MIDDLE -> handleMiddleClick(graphicsContext, event);
                case SECONDARY -> deleteLastPoint(graphicsContext);
            }
        });
    }

    private void movePoint(GraphicsContext graphicsContext, MouseEvent event) {

        for (int i = 0; i < points.size(); i++) {
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            //graphicsContext.fillOval(160, 160, 60, 60);
            //System.out.println(x + " " + event.getX());
            //System.out.println(y + " " + event.getY());
            //System.out.println();
            if (Math.abs(event.getX() - x) <= POINT_RADIUS) {
                if (Math.abs(event.getY() - y) <= POINT_RADIUS) {
                    if (!isDragged) {
                        points.remove(i);
                        isDragged = true;
                    }
                    //graphicsContext.fillOval(60, 60, 60, 60);
                    return;
                }
            }
        }
        //graphicsContext.fillOval(160, 60, 60, 60);
    }
    private void deleteLastPoint(GraphicsContext graphicsContext) {
        if (points.size() <= 0) {
            return;
        }
        points.remove(points.size() - 1);
        drawBesierCurves(graphicsContext);
    }
    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        points.add(clickPoint);

        //System.out.println(BesierCurves.partFactorial(13, 12));

        if (points.size() < 30) {
            drawBesierCurves(graphicsContext);
        }
        if (points.size() == 29) {
            graphicsContext.fillText("Больше точек не поддерживается. Нажмите на колесико мыши для очистки холста.", 20, 590);
            System.out.println("Больше точек не поддерживается. Нажмите на колесико мыши для очистки холста.");
        }
    }

    private void drawBesierCurves(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(1, 1, 798.5, 598);
        for (int i = 0; i < points.size(); i++) {
            graphicsContext.fillOval(
                    points.get(i).getX() - POINT_RADIUS, points.get(i).getY() - POINT_RADIUS,
                    2 * POINT_RADIUS, 2 * POINT_RADIUS);
        }
        BesierCurves.drawBesierCurve(graphicsContext, points);
    }

    private void handleMiddleClick(GraphicsContext graphicsContext, MouseEvent event) {
        points.clear();
        graphicsContext.clearRect(1, 1, 798.5, 598);
    }
}