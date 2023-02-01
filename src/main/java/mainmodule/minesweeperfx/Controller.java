package mainmodule.minesweeperfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controller {
    Grid buttonGrid;


    public Controller(){
        System.out.println("Controller instantiated");
    }

    @FXML
    public void initialize(){   // this method is called after fxml attributes are uploaded and given to the class

    }

    public void addButtons(GridPane layout, int gridLength, int tileWidth){
        buttonGrid = new Grid(gridLength, gridLength);
        ButtonWrapper tempButton;
        EventHandler<ActionEvent> tempEvent;

        for(int w = 0; w < gridLength; w++){
            for (int h = 0; h < gridLength; h++){
                tempButton = new ButtonWrapper(((Integer)w).toString(), w, h, this);        // so proud of this line and implementation
                tempButton.b.setPrefWidth(tileWidth);    tempButton.b.setPrefHeight(tileWidth);
                buttonGrid.insert(tempButton, w, h);        // event is initialised in here
                layout.add(tempButton.b, w, h, 1, 1);
            }
        }
    }

    public void processTilePress(int x, int y){
        System.out.println("Tile in position ("+ ((Integer)x).toString()+","+((Integer)y).toString()+") pressed");
        buttonGrid.get(x,y).b.setText("B");
    }
}