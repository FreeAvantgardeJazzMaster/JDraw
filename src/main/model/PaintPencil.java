package main.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PaintPencil implements Paint {
    private double endX;
    private double endY;
    private double size;
    private Color color;
    private GraphicsContext gc;
    List<Coordinates> coordinates = new ArrayList<>();

    public void addCoorinates(Coordinates coordinate){
        this.coordinates.add(coordinate);
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

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public PaintPencil(double endX, double endY, double size, Color color, GraphicsContext gc) {
        coordinates.add(new Coordinates(endX, endY));
        this.size = size;
        this.color = color;
        this.gc = gc;
    }

    @Override
    public void execute() {
        gc.setStroke(color);
        gc.setLineWidth(size);
        gc.beginPath();
        for (Coordinates coordinates : coordinates) {
            gc.lineTo(coordinates.getX(), coordinates.getY());
            gc.stroke();
        }
    }
}
