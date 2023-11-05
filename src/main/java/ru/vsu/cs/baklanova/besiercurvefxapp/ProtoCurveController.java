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

    private final int POINT_RADIUS = 3;

    private boolean isDragged = false;

    private int isDraggedPoint = -1;

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
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(graphicsContext, event, isDraggedPoint);
                case MIDDLE -> handleMiddleClick(graphicsContext, event);
                case SECONDARY -> deleteLastPoint(graphicsContext);
            }
            isDragged = false;
            isDraggedPoint = -1;
        });
    }
    private void movePoint(GraphicsContext graphicsContext, MouseEvent event) {
        if (isDragged) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            if (Math.abs(event.getX() - x) <= POINT_RADIUS) {
                if (Math.abs(event.getY() - y) <= POINT_RADIUS) {
                    points.remove(i);
                    isDraggedPoint = i;
                    isDragged = true;
                    graphicsContext.fillOval(x - POINT_RADIUS * 2, y - POINT_RADIUS * 2, POINT_RADIUS * 4, POINT_RADIUS * 4);
                }
            }
        }
    }
    private void deleteLastPoint(GraphicsContext graphicsContext) {
        if (points.size() <= 0) {
            return;
        }
        points.remove(points.size() - 1);
        drawBesierCurves(graphicsContext);
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        handlePrimaryClick(graphicsContext, event, - 1);
    }
    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event, int pointPlace) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        if (pointPlace < 0) {
            points.add(clickPoint);
        } else {
            points.add(pointPlace, clickPoint);
        }

        drawBesierCurves(graphicsContext);
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