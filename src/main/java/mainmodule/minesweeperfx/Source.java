package mainmodule.minesweeperfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.List;

public class Source extends Application {
    // tracking
    Controller myController;
    // thread to track time passed
    Ticker t;
    Thread clockThread;
    Thread[] threadList = new Thread[2];
    // AI
    mineSolver AI;
    Thread AIThread;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Source.class.getResource("View.fxml"));
        GridPane layout = fxmlLoader.load();


        // adding extra text field
        final int gridLength = 15;
        final int tileWidth = (20/gridLength)*20;
        myController = fxmlLoader.getController();
        myController.init(layout, gridLength, tileWidth);

        Scene scene = new Scene(layout, gridLength*tileWidth, gridLength*tileWidth + 100);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setOnHidden(e->myController.windowClose());
        stage.show();

        launchClock();
        launchAI();
        myController.setThreadsToClose(threadList);
    }

    // Clock implement
    private class Ticker implements Runnable {
        public Controller c;
        public void run() {
            while (true){
                try { Thread.sleep(1000);
                } catch (InterruptedException e){}
                c.tick();
            }
        }
        public Ticker(Controller c){this.c = c;}
    }
    private void launchClock(){
        t = new Ticker(myController);
        clockThread =  new Thread(t);
        threadList[0] = clockThread;
        clockThread.start();
    }
    // AI implement
    private void launchAI(){
        AI = new mineSolver(myController);
        AIThread = new Thread(AI);
        threadList[1] = AIThread;
        AIThread.start();
    }

    public void mainStart(){
        launch();
    }

    public static void main(String[] args) {
        Source s = new Source();
        s.mainStart();
    }
}