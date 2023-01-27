package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Level;
import model.Move;

public class LevelController {

    private final Level level;

    public LevelController(Level level){
        this.level = level;
    }


    public void handleGridClickEvent(ActionEvent actionEvent) {
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
}
