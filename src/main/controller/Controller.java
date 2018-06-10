package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.model.*;
import main.plugin.Plugin;
import main.plugin.PluginLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    @FXML
    private ToggleButton brushButton;

    @FXML
    private ToggleButton lineButton;

    @FXML
    private ToggleButton pencilButton;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox sizeComboBox;

    @FXML
    private MenuItem fileNew;

    @FXML
    private MenuItem fileOpen;

    @FXML
    private MenuItem fileSave;

    @FXML
    private MenuItem editRedo;

    @FXML
    private MenuItem editUndo;

    @FXML
    private MenuItem helpAbout;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox pluginsComboBox;

    @FXML
    private HBox pluginsToolBox;

    public Plugin[] plugins;

    public boolean toolSelected = false;

    public GraphicsContext graphicsContext;

    public double startX, startY;

    public CanvasHistory canvasHistory;

    PaintPencil paintPencil = null;

    PaintBrush paintBrush = null;

    @FXML
    public void initialize(){
        initControls();
        initCanvas();
        loadPlugins();
    }

    public void selectTool(ActionEvent event){
        toolSelected = true;

    }

    public void newCanvas(ActionEvent event){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        canvasHistory.clear();
    }

    private void initControls(){
        ObservableList<String> values = FXCollections.observableArrayList("1", "2", "5", "10", "15", "20", "25", "30");
        sizeComboBox.setItems(values);
        sizeComboBox.getSelectionModel().selectFirst();
    }

    private void loadPlugins(){
        plugins = null;

        try {
            plugins = PluginLoader.initAsPlugin(PluginLoader.loadPlugins("src\\plugins", "config.cfg"));
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }

        initPlugins();
    }

    private void initPlugins(){
        if (plugins != null) {
            for (Plugin plugin : plugins) {
                Button button = new Button(plugin.getName());
                pluginsToolBox.getChildren().add(button);
                button.setOnAction(e -> {
                    canvasHistory.addPaint(plugin.run(canvas, colorPicker.getValue()));
                });
            }
        }
    }

    private void initCanvas(){
        canvasHistory = new CanvasHistory();
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(e -> {
            if (lineButton.isSelected()){
                startX = e.getX();
                startY = e.getY();
            }
            else if (pencilButton.isSelected()){
                paintPencil = new PaintPencil(Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()),
                        colorPicker.getValue(), graphicsContext, new Coordinates(e.getX(), e.getY()));
            }
            else if (brushButton.isSelected()){
                paintBrush = new PaintBrush(Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()),
                        colorPicker.getValue(), graphicsContext, new Coordinates(e.getX(), e.getY()));
            }

        });

        canvas.setOnMouseReleased(e -> {
            if (lineButton.isSelected()){
                PaintLine paintLine = new PaintLine(startX, startY, e.getX(), e.getY(),
                        Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()),colorPicker.getValue(), graphicsContext);
                paintLine.execute();
                canvasHistory.addPaint(paintLine);
            }
            else if (pencilButton.isSelected()){
                canvasHistory.addPaint(paintPencil);
            }
            else if (brushButton.isSelected()){
                canvasHistory.addPaint(paintBrush);
            }
            graphicsContext.beginPath();
        });

        canvas.setOnMouseDragged(e -> {
            if (brushButton.isSelected()){
                double size = Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString());
                double x = e.getX() - size/2;
                double y = e.getY() - size/2;
                graphicsContext.setFill(colorPicker.getValue());
                graphicsContext.fillRoundRect(x ,y ,size, size, size, size);

                paintBrush.addCoorinates(new Coordinates(x, y));
            }
            else if (pencilButton.isSelected()){
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.setLineWidth(Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()));
                graphicsContext.lineTo(e.getX(), e.getY());
                graphicsContext.stroke();

                paintPencil.addCoorinates(new Coordinates(e.getX(), e.getY()));
            }
        });
    }

    public void undo(ActionEvent event){
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        canvasHistory.undo();
    }

    public void redo(ActionEvent event){
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        canvasHistory.redo();
    }

    public void save(ActionEvent event) throws IOException{
        File file = new File("picture.png");
        WritableImage image = canvas.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", file);
    }

    public void drawSquare(ActionEvent event){

    }
}
