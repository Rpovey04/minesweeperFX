package mainmodule.minesweeperfx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Pair;

// wrapper class to hold a button and its position in the grid together in memory
public class ButtonWrapper {
    public Button b;
    public int x, y;

    public ButtonWrapper(){}

    public ButtonWrapper(String txt, int x, int y, Controller c){       // initialises event
        b = new Button(txt);
        this.x = x; this.y = y;

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {   // allows for interaction with corresponding "cell" from controller
                c.processTilePress(x,y);
            }
        });
    }

}
