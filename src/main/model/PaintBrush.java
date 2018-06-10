package main.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PaintBrush implements Paint {
    private double size;
    private Color color;
    private GraphicsContext gc;
    List<Coordinates> coordinates = new ArrayList<>();

    public void addCoorinates(Coordinates coordinate){
        this.coordinates.add(coordinate);
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

    public PaintBrush(double size, Color color, GraphicsContext gc, Coordinates coordinates) {
        this.size = size;
        this.color = color;
        this.gc = gc;
        this.coordinates.add(coordinates);
    }

    @Override
    public void execute() {
        gc.setFill(color);
        gc.beginPath();
        for (Coordinates coordinates : coordinates){
            gc.fillRoundRect(coordinates.getX() ,coordinates.getY() ,size, size, size, size);
        }
    }
}
