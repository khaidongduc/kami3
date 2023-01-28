package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Color;
import model.Level;
import model.Move;

public class LevelController {

    private Level level;

    public LevelController() {

    }

    public LevelController(Level level){
        setLevel(level);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void handleColorGridBtn(ActionEvent actionEvent) {
        try {
            Button targetButton = (Button) actionEvent.getTarget();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            level.play(new Move(level.getCurrentColor(), row, col));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    public void handleChooseColorBtn(ActionEvent actionEvent) {
        try {
            Button targetButton = (Button) actionEvent.getTarget();
            Color color = (Color) targetButton.getUserData();
            level.switchColor(color);
            System.out.println(color);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    public void handleRestartBtn() {
        level.restart();
    }

    public void handleMoveToMenuBtn() {
        ViewSwitcher.switchView("Level2");
    }
}
