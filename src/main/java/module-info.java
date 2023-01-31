module mainmodule.minesweeperfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens mainmodule.minesweeperfx to javafx.fxml;
    exports mainmodule.minesweeperfx;
}