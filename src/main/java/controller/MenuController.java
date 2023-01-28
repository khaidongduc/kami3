package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.LevelImpl;
import model.LevelInfo;

public class MenuController {

    public void handleMoveToLevelBtn(ActionEvent actionEvent) {
        Button target = (Button) actionEvent.getTarget();
        LevelInfo info = (LevelInfo) target.getUserData();
        ViewSwitcher.switchView("LevelView", new LevelImpl(info.getLevelId()));
    }
}
