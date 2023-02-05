package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.*;
import view.ViewEnum;

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
        Level level = LevelRepository.getInstance().loadLevel(this.level.getLevelInfo());
        ViewSwitcher.switchView(ViewEnum.LEVEL, level);
    }

    public void handleMoveToMenuBtn() {
        ViewSwitcher.switchView(ViewEnum.MENU);
    }
}
