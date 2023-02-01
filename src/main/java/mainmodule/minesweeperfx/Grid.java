package mainmodule.minesweeperfx;

import javafx.util.Pair;

public class Grid<T> {
    int rows, cols;
    private T[] arr;

    public Grid(int r, int c){  // rows=r, columns=c
        arr = (T[]) new Object[r*c];
        rows=r; cols=c;
    }
    public void insert(T element, int r, int c){
        arr[r + c*rows] = element;
    }
    public T get(int r, int c){
        return arr[r + c*rows];
    }
}
