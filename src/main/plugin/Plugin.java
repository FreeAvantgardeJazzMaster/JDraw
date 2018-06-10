package main.plugin;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import main.model.Paint;

public interface Plugin {

    public String getName();

    public Paint run(Canvas canvas, Color color);
}
