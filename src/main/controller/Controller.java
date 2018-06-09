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
import main.model.ResizableCanvas;


public class Controller {

    @FXML
    private ToggleButton brushButton;

    @FXML
    private ToggleButton lineButton;

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

    @FXML
    public void initialize(){
        initControls();
        initCanvas();
    }

    public void selectTool(ActionEvent event){
        toolSelected = true;

    }

    private void initControls(){
        ObservableList<String> values = FXCollections.observableArrayList("10", "15", "20", "25", "30");
        sizeComboBox.setItems(values);
        sizeComboBox.getSelectionModel().selectFirst();
    }

    private void initCanvas(){
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(sizeComboBox.getSelectionModel().getSelectedItem().toString());
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            if(toolSelected){
                if (brushButton.isSelected()){
                    graphicsContext.setFill(colorPicker.getValue());
                    graphicsContext.fillRoundRect(x ,y ,size, size, size, size);
                }
                else if (lineButton.isSelected()){
                    graphicsContext.setStroke(colorPicker.getValue());
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(e.getX(), e.getY());
                    graphicsContext.stroke();
                }
            }
        });
    }
}
