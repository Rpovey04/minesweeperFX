package mainmodule.minesweeperfx;
import javafx.util.Pair;
import java.util.Random;

public class mineSolver {
    Controller game;
    int currentX, currentY;     // will be the last touched tile when makeMove is called

    // class being used to access from main thread. Actions on javaFX objects are not allowed in multithreading
    public class Move {
        public int x;
        public int y;
        public boolean leftClick;   // true for explore, false for flag
        public Move(int x, int y, boolean leftClick){
            this.x = x; this.y = y; this.leftClick = leftClick;
        }
    }

    // orientation (agent / intelligence)
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

    // decision-making (intelligence)
    private boolean processNextMove(){     // true for left (explore) click and false for right (flag) click
        // check for reset
        if (game.resetFlag()){
            System.out.println("reset detected");
            findEdgeTile();
        }

        // randomness removed, AI will be here tomorrow when I can be bothered to formulate it
        // The algorithm will also be nice and neat

        return true;
    }

    // constructor (system)
    public mineSolver(Controller game) {
        this.game = game;
    }
    public void init(){
        findEdgeTile();
        System.out.println("AI initialised successfully");
    }

    // interface with game (agent)
    public Move getMove(){
        boolean status = processNextMove();
        System.out.println("Currently at coordinate: (" + ((Integer)currentX).toString() + "," + ((Integer)currentY).toString() + ")");
        return new Move(currentX, currentY, status);
    }
}
