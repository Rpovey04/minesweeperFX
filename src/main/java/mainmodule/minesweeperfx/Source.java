package mainmodule.minesweeperfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;

public class Source extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Source.class.getResource("View.fxml"));
        GridPane layout = fxmlLoader.load();

        // adding extra text field
        TextField myTxtField = new TextField("txt from src");
        myTxtField.setPrefHeight(100);
        myTxtField.setPrefWidth(100);
        myTxtField.setAlignment(Pos.CENTER);
        layout.add(myTxtField, 3, 3, 1, 1);



        Scene scene = new Scene(layout, 400, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}