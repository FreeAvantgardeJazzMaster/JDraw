package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.model.CanvasHistory;
import main.model.Paint;
import main.model.PaintLine;
import main.model.ResizableCanvas;


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

    public boolean toolSelected = false;

    public GraphicsContext graphicsContext;

    public double startX, startY;

    public CanvasHistory canvasHistory;

    @FXML
    public void initialize(){
        initControls();
        initCanvas();
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

    private void initCanvas(){
        canvasHistory = new CanvasHistory();
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            if (lineButton.isSelected()){
                startX = e.getX();
                startY = e.getY();
            }

        });
        canvas.setOnMouseReleased(e -> {
            if (lineButton.isSelected()){
              //  graphicsContext.setStroke(colorPicker.getValue());
               // graphicsContext.setLineWidth(Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()));
               // graphicsContext.strokeLine(startX, startY, e.getX(), e.getY());
                PaintLine paintLine = new PaintLine(startX, startY, e.getX(), e.getY(),
                        Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()),colorPicker.getValue(), graphicsContext);
                paintLine.execute();
                canvasHistory.addPaint(paintLine);
            }
            graphicsContext.beginPath();
        });
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString());
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            if (brushButton.isSelected()){
                graphicsContext.setFill(colorPicker.getValue());
                graphicsContext.fillRoundRect(x ,y ,size, size, size, size);
            }
            else if (pencilButton.isSelected()){
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.setLineWidth(Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString()));
                graphicsContext.lineTo(e.getX(), e.getY());
                graphicsContext.stroke();
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
}
