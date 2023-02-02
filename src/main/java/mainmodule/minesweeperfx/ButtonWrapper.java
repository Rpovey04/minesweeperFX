package mainmodule.minesweeperfx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

// wrapper class to hold a button and its position in the grid together in memory
public class ButtonWrapper {
    public Button b;
    public int x, y;
    String defaultColStyle;

    public void resetStyle(){
        // b.setText("");
        b.setStyle(defaultColStyle);
        b.setOnMouseEntered(e->b.setStyle("-fx-background-color: rgb(255,255,255)"));
        b.setOnMouseExited(e->b.setStyle(defaultColStyle));
    }

    public ButtonWrapper(){}

    public ButtonWrapper(String txt, int x, int y, Controller c){       // initialises event
        b = new Button(txt);
        this.x = x; this.y = y;
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY){c.processTilePress(x,y);}
                else {c.processFlagPlace(x,y);}
            }
        });
    }

    public void setColStyle(String s){
        defaultColStyle = s;
        b.setStyle(s);
    }

}
