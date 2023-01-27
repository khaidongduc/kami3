package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Level;

public class LevelController {

    private Level level;

    public LevelController(Level level){
        this.level = level;
    }


    public void handleGridClickEvent(ActionEvent actionEvent) {
        Button targetButton = (Button) actionEvent.getTarget();
        System.out.print(GridPane.getRowIndex(targetButton));
        System.out.print(" ");
        System.out.println(GridPane.getColumnIndex(targetButton));
    }
}
