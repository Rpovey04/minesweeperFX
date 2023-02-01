package mainmodule.minesweeperfx;

import javafx.util.Pair;

public class Grid {
    int rows, cols;
    private ButtonWrapper[] arr;

    public Grid(int r, int c){  // rows=r, columns=c
        arr = new ButtonWrapper[r*c];
        rows=r; cols=c;
    }
    public void insert(ButtonWrapper element, int r, int c){
        arr[r + c*rows] = element;
    }
    public ButtonWrapper get(int r, int c){
        return arr[r + c*rows];
    }
}
