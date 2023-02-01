package mainmodule.minesweeperfx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Pair;

public class ButtonWrapper {
    public Button b;
    public int x, y;

    public ButtonWrapper(){}

    public ButtonWrapper(String txt, int x, int y, Controller c){
        b = new Button(txt);
        this.x = x; this.y = y;

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                c.processTilePress(x,y);
            }
        });
    }

}
