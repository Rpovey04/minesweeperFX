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

public class Source extends Application {
    private Ticker t = new Ticker();;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Source.class.getResource("View.fxml"));
        GridPane layout = fxmlLoader.load();


        // adding extra text field
        final int gridLength = 8;
        final int tileWidth = (20/gridLength)*20;
        Controller myController = fxmlLoader.getController();
        myController.init(layout, gridLength, tileWidth);

        Scene scene = new Scene(layout, gridLength*tileWidth, gridLength*tileWidth + 100);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.show();

        t.run(myController);
    }

    private class Ticker extends Thread {
        public void run(Controller c){
            try { Thread.sleep(5000);
            } catch (InterruptedException e){}
        }
        public Ticker()
        {}
    }

    public void mainStart(){
        launch();
    }

    public static void main(String[] args) {
        Source s = new Source();
        s.mainStart();
    }
}