package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.Level;
import model.LevelImpl;
import model.LevelInfo;
import model.LevelRepository;

public class MenuController {

    public void handleMoveToLevelBtn(ActionEvent actionEvent) {
        Button target = (Button) actionEvent.getTarget();
        LevelInfo info = (LevelInfo) target.getUserData();
        Level level = LevelRepository.getInstance().getLevel(info.getLevelId());
        ViewSwitcher.switchView("LevelView", level);
    }
}
