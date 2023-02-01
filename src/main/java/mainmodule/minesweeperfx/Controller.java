package mainmodule.minesweeperfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Controller {
    Grid<ButtonWrapper> buttonGrid;        // grid containing buttons
    Grid<Character> gameGrid;               // grid containing game logic / symbols

    public Controller(){
        System.out.println("Controller instantiated");
    }

    @FXML
    public void initialize(){   // this method is called after fxml attributes are uploaded and given to the class

    }

    public void initGrid(GridPane layout, int gridLength, int tileWidth){ // initialises all tiles as well as adding buttons
        buttonGrid = new Grid<ButtonWrapper>(gridLength, gridLength);
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

    public void processTilePress(int x, int y){ // refercing position in grid since "touching" values will need to be accessed
        System.out.println("Tile in position ("+ ((Integer)x).toString()+","+((Integer)y).toString()+") pressed");

        // These two commands will be used to
        // buttonGrid.get(x,y).b.setText("B");
        // buttonGrid.get(x,y).b.setStyle("-fx-background-color: rgb(255, 0, 0)");
    }
}