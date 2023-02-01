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
import java.util.Random;

public class Controller {
    private Grid<ButtonWrapper> buttonGrid;        // grid containing buttons
    private Grid<Integer> gameGrid;               // grid containing game logic / symbols
    private Grid<Boolean> discoveredGrid;           // used for game logic
    private int gridLength;
    private Random rand;

    private void initGame(){
        gameGrid = new Grid<>(gridLength, gridLength);
        for (int i = 0; i < gridLength; i++){for (int j = 0; j < gridLength; j++) {gameGrid.insert(0,i,j);}}
        discoveredGrid = new Grid<>(gridLength, gridLength);
        for (int i = 0; i < gridLength; i++){for (int j = 0; j < gridLength; j++) {discoveredGrid.insert(false,i,j);}}


        // placing bombs
        int count = 0;
        int randX,randY;
        while (count < gridLength*2){
            randX = rand.nextInt(gridLength);
            randY = rand.nextInt(gridLength);
            if (gameGrid.get(randX, randY) != -1) {
                gameGrid.insert(-1, randX, randY);
                count+=1;
            }
        }

        // updating rest of the grid
        int currentCount;
        for(int x = 0; x < gridLength; x++){
            for (int y = 0; y < gridLength; y++){
                if (gameGrid.get(x,y) != -1){
                    currentCount = 0;
                    //left
                    if (x > 0){if (gameGrid.get(x-1,y) == -1) {currentCount+=1;}}
                    //bottomleft
                    if (x>0 && y < gridLength-1) {if (gameGrid.get(x-1,y+1) == -1){currentCount+=1;}}
                    //bottom
                    if (y < gridLength-1) {if (gameGrid.get(x,y+1) == -1){currentCount+=1;}}
                    //bottomright
                    if (x < gridLength-1 && y < gridLength-1) {if (gameGrid.get(x+1,y+1) == -1){currentCount+=1;}}
                    //right
                    if (x < gridLength-1) {if (gameGrid.get(x+1,y) == -1){currentCount+=1;}}
                    //topright
                    if (x < gridLength-1 && y > 0) {if (gameGrid.get(x+1, y-1)==-1){currentCount+=1;}}
                    //top
                    if (y > 0) {if(gameGrid.get(x,y-1)==-1){currentCount+=1;}}
                    //topleft
                    if (x > 0 && y > 0) {if(gameGrid.get(x-1,y-1)==-1){currentCount+=1;}}
                    gameGrid.insert(currentCount,x,y);
                }
            }
        }
    }
    public Controller(){
        rand = new Random();
    }

    @FXML
    public void initialize(){   // this method is called after fxml attributes are uploaded and given to the class
    }

    public void initButtons(GridPane layout, int gridLength, int tileWidth){ // initialises all tiles as well as adding buttons
        this.gridLength = gridLength;
        initGame();

        buttonGrid = new Grid<>(gridLength, gridLength);
        ButtonWrapper tempButton;

        for(int w = 0; w < gridLength; w++){
            for (int h = 0; h < gridLength; h++){
                tempButton = new ButtonWrapper("", w, h, this);        // so proud of this line and implementation
                tempButton.b.setPrefWidth(tileWidth);    tempButton.b.setPrefHeight(tileWidth);
                buttonGrid.insert(tempButton, w, h);        // event is initialised in here
                layout.add(tempButton.b, w, h, 1, 1);
            }
        }

        // selecting a blank tile to start the game
        boolean found = false;
        int randX, randY;
        while (!found){
            randX = rand.nextInt(gridLength);
            randY = rand.nextInt(gridLength);
            if (gameGrid.get(randX, randY) == 0){
                found = true;
                processTilePress(randX, randY);
            }
        }
    }

    // Game updates on click
    private String getTileColor(int val){
        switch(val){
            case(-1): // bomb
                return "-fx-background-color: rgb(255, 0, 0)";
            case(0):    // blank space
                return "-fx-background-color: rgb(255, 255, 255)";
            case(1):
                return "-fx-background-color: rgb(0, 0, 128)";
            case(2):
                return "-fx-background-color: rgb(0, 128, 0)";
            case(3):
                return "-fx-background-color: rgb(128, 0, 0)";
            case(4):
                return "-fx-background-color: rgb(128, 128, 0)";
            default:
                return "-fx-background-color: rgb(0, 128, 128)";
        }
    }

    private void showConnectedSafeSpaces(int x, int y){
        if (x > 0){if (!discoveredGrid.get(x-1,y)) {processTilePress(x-1,y);}}
        //bottom
        if (y < gridLength-1) {if (!discoveredGrid.get(x,y+1)){processTilePress(x,y+1);}}
        //right
        if (x < gridLength-1) {if (!discoveredGrid.get(x+1,y)){processTilePress(x+1,y);}}
        //top
        if (y > 0) {if (!discoveredGrid.get(x,y-1)){processTilePress(x,y-1);}}
    }

    public void processTilePress(int x, int y){ // refercing position in grid since "touching" values will need to be accessed
        // Set the style of the tile
        discoveredGrid.insert(true, x, y);
        buttonGrid.get(x,y).b.setStyle(getTileColor(gameGrid.get(x,y)));
        if (gameGrid.get(x,y) == -1) { buttonGrid.get(x,y).b.setText("B");}
        else {buttonGrid.get(x,y).b.setText(gameGrid.get(x,y).toString());}

        // safe tile, reveal all connected tiles
        if (gameGrid.get(x,y) == 0){
            showConnectedSafeSpaces(x, y);
        }
    }

    public void processFlagPlace(int x, int y){
        if (!discoveredGrid.get(x,y)){
            buttonGrid.get(x,y).b.setStyle("-fx-background-color: rgb(255, 255, 0)");   // yellow
            buttonGrid.get(x,y).b.setText("F");
        }
    }
}