package mainmodule.minesweeperfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Grid<Boolean> flagGrid;
    private int gridLength;
    private Random rand;
    private int numBombs, numFlags, score, timePassed;


    private String getTileColor(int val){
        switch(val){
            case(-1): // bomb
                return "-fx-background-color: rgb(255, 0, 0)";
            case(0):    // blank space
                return "-fx-background-color: rgb(255, 255, 255)";
            case(1):
                return "-fx-background-color: rgb(0, 0, 200)";
            case(2):
                return "-fx-background-color: rgb(0, 200, 0)";
            case(3):
                return "-fx-background-color: rgb(200, 0, 0)";
            case(4):
                return "-fx-background-color: rgb(200, 200, 0)";
            default:
                return "-fx-background-color: rgb(0, 200, 200)";
        }
    }

    private void clearFirstTiles(){
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

    private int getNumBombs(){
        return (gridLength*gridLength)/10;      // approx 1/10 tiles are bombs
    }

    private void reset(){
        timePassed = 0;
        for (int i = 0; i < gridLength; i++){
            for (int j = 0; j < gridLength; j++){
                buttonGrid.get(i, j).resetStyle();
            }
        }
        initGame();
        clearFirstTiles();
        updateTextFields();
    }
    private void initGame(){
        // empty grids
        gameGrid = new Grid<>(gridLength, gridLength);
        for (int i = 0; i < gridLength; i++){for (int j = 0; j < gridLength; j++) {gameGrid.set(0,i,j);}}
        discoveredGrid = new Grid<>(gridLength, gridLength);
        for (int i = 0; i < gridLength; i++){for (int j = 0; j < gridLength; j++) {discoveredGrid.set(false,i,j);}}
        flagGrid = new Grid<>(gridLength, gridLength);
        for (int i = 0; i < gridLength; i++){for (int j = 0; j < gridLength; j++) {flagGrid.set(false,i,j);}}

        // game variables
        numBombs = getNumBombs();
        numFlags = numBombs;
        score = 0;

        // placing bombs
        int count = 0;
        int randX,randY;
        while (count < numBombs){
            randX = rand.nextInt(gridLength);
            randY = rand.nextInt(gridLength);
            if (gameGrid.get(randX, randY) != -1) {
                gameGrid.set(-1, randX, randY);
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
                    gameGrid.set(currentCount,x,y);
                }
            }
        }
    }

    private void initButtons(GridPane layout, int tileWidth){ // initialises all tiles as well as adding buttons
        buttonGrid = new Grid<>(gridLength, gridLength);
        ButtonWrapper tempButton;

        for(int w = 0; w < gridLength; w++){
            for (int h = 0; h < gridLength; h++){
                tempButton = new ButtonWrapper("", w, h, this);        // so proud of this line and implementation
                tempButton.b.setPrefWidth(tileWidth);    tempButton.b.setPrefHeight(tileWidth);
                buttonGrid.set(tempButton, w, h);        // event is initialised in here
                layout.add(tempButton.b, w, h, 1, 1);
            }
        }
    }

    /*
        CONSTRUCTORS
    */
    public Controller(){
        rand = new Random();
    }

    @FXML private Button reset_button;
    @FXML private TextField flag_num;
    @FXML private TextField time_passed;
    @FXML public void initialize(){   // this method is called after fxml attributes are uploaded and given to the class
        reset_button.setOnAction(e->reset());       // using the variable intialised from the FXML file
    }

    public void init(GridPane layout, int gridLength, int tileWidth){
        this.gridLength = gridLength;
        initGame();
        initButtons(layout, tileWidth);
        clearFirstTiles();
        updateTextFields();
    }

    /*
        GAME LOGIC
     */
    // Game logic
    private void showConnectedSafeSpaces(int x, int y){
        if (x > 0){if (!discoveredGrid.get(x-1,y)) {processTilePress(x-1,y);}}
        //bottom
        if (y < gridLength-1) {if (!discoveredGrid.get(x,y+1)){processTilePress(x,y+1);}}
        //right
        if (x < gridLength-1) {if (!discoveredGrid.get(x+1,y)){processTilePress(x+1,y);}}
        //top
        if (y > 0) {if (!discoveredGrid.get(x,y-1)){processTilePress(x,y-1);}}
    }

    private void checkWin(){
       if (score==numBombs){win();}
    }

    // endgames (will be updated to work with scoreboard ect)
    private void win(){
        System.out.println("Congrats!");
        try { Thread.sleep(3000);
        } catch (InterruptedException e){}
        reset();
    }
    private void lose(){
        System.out.println("BANG!");
        try { Thread.sleep(3000);
        } catch (InterruptedException e){}
        reset();
    }

    // FXML interface
    @FXML
    private void onButtonClicked(ActionEvent e)
    {}

    private void updateFlagNum(){
        flag_num.setText("flags: " + ((Integer)numFlags).toString());
    }
    private void updateTimePassed() {time_passed.setText("Time: " + ((Integer)timePassed).toString());}
    private void updateTextFields(){
        updateFlagNum();

    }    // Input
    public void tick(){     // called every second
        timePassed += 1;
        updateTimePassed();
    }
    public void processTilePress(int x, int y)  { // refercing position in grid since "touching" values will need to be accessed
        // Set the style of the tile
        discoveredGrid.set(true, x, y);
        buttonGrid.get(x,y).setColStyle(getTileColor(gameGrid.get(x,y)));
        if (gameGrid.get(x,y) == -1) { buttonGrid.get(x,y).b.setText("B");}
        else {buttonGrid.get(x,y).b.setText(gameGrid.get(x,y).toString());}

        // safe tile, reveal all connected tiles
        if (gameGrid.get(x,y) == 0){
            showConnectedSafeSpaces(x, y);
        }
        else if (gameGrid.get(x,y) == -1){   // touched bomb
            lose();
        }

        if (flagGrid.get(x, y)) {
            numFlags += 1;
        }
    }

    public void processFlagPlace(int x, int y){
        if (!discoveredGrid.get(x,y)){
            if (!flagGrid.get(x,y) && numFlags > 0) {   // placing flag
                buttonGrid.get(x, y).setColStyle("-fx-background-color: rgb(255, 255, 0)");   // yellow
                buttonGrid.get(x, y).b.setText("F");
                flagGrid.set(true, x,y);

                numFlags-=1;
                if (gameGrid.get(x,y) == -1){score+=1;}
                checkWin();
            }
            else {      // removing the flag
                buttonGrid.get(x, y).setColStyle("-fx-background-color: rgb(155,155,155)");
                buttonGrid.get(x, y).b.setText("");
                flagGrid.set(false, x,y);

                numFlags+=1;
                if (gameGrid.get(x,y) == -1){score-=1;}
            }
        }
        updateTextFields();
    }
}