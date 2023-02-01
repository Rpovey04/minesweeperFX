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
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Source.class.getResource("View.fxml"));
        GridPane layout = fxmlLoader.load();

        // adding extra text field
        final int gridLength = 20;
        final int tileWidth = (20/gridLength)*20;
        Controller myController = fxmlLoader.getController();
        myController.initGrid(layout, gridLength, tileWidth);

        Scene scene = new Scene(layout, gridLength*tileWidth, gridLength*tileWidth + 100);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}