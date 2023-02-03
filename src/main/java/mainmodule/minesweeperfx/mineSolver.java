package mainmodule.minesweeperfx;
import javafx.util.Pair;


public class mineSolver implements Runnable  {
    Controller game;
    int currentX, currentY;     // will be the last touched tile when makeMove is called

    // orientation
    private void findClearTile(){       // used for intial values since safe
        for (int i = 0; i < game.getGridLength(); i++){
            for (int j = 0; j < game.getGridLength(); j++){
                if (game.getKnownState(i, j) == 0) {
                    currentX = i;   currentY = j;
                    return;
                }
            }
        }
    }
    private Pair<Integer, Integer> findEdgeTile(int xDir, int yDir, int x, int y){        // finding a safe marked tile on the edge of a clear field
        if (x+xDir >= 0 && x+xDir <= game.getGridLength()-1 && y+yDir >= 0 && y+yDir <= game.getGridLength()-1){
            if (game.getKnownState(x+xDir, y+yDir) > 0){    // is an edge tile
                return new Pair<>(x+xDir, y+yDir);
            }
            else {  // is still a blank space
                return findEdgeTile(xDir, yDir, x+xDir, y+yDir);
            }
        }
        else {
            return new Pair<>(-1, -1);
        }
    }
    private void findEdgeTile(){
        findClearTile();
        Pair<Integer, Integer> newCoords = new Pair<>(-1, -1);
        for (int i = -1; i <= 1 && newCoords.getKey() == -1; i++){      // searches in all directions
            for (int j = -1; j <= 1 && newCoords.getKey() == -1; j++){
                if (i != 0 || j != 0){
                    newCoords = findEdgeTile(i,j, currentX, currentY);
                }
            }
        }
        currentX = newCoords.getKey();
        currentY = newCoords.getValue();
    }

    // decision-making
    private boolean processNextMove(){     // true for left (explore) click and false for right (flag) click
        // check for reset
        if (game.resetFlag()){
            System.out.println("reset detected");
            findEdgeTile();
        }

        return true;
    }

    // constructor
    public mineSolver(Controller game) {
        this.game = game;
        findEdgeTile();
        System.out.println("AI initialised successfully");
    }

    // interface with game
    public void makeMove(){
        boolean status = processNextMove();
        if (status){        // left click
            game.processTilePress(currentX, currentY);
        }
        else {              // right click
            game.processFlagPlace(currentX, currentY);
        }

        System.out.println("Currently at coordinate: (" + ((Integer)currentX).toString() + "," + ((Integer)currentY).toString() + ")");
    }

    // threading and main loop
    public void run() {
        System.out.println("AI starting");
        while (true) {
            try { Thread.sleep(1000);
            } catch (InterruptedException e){}
            makeMove();
        }
    }
}
