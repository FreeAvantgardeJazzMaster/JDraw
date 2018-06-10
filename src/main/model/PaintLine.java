package main.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintLine implements Paint{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double size;
    private Color color;
    private GraphicsContext gc;

    public String getName(){return null;}

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {

        this.endY = endY;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PaintLine(double startX, double startY, double endX, double endY, double size, Color color, GraphicsContext gc) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.size = size;
        this.color = color;
        this.gc = gc;
    }


    @Override
    public void execute() {
        gc.setStroke(color);
        gc.setLineWidth(size);
        gc.strokeLine(startX, startY, endX, endY);
    }
}
