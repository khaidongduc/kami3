package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.*;
import view.ViewEnum;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MenuController {

    public void handleMoveToLevelBtn(ActionEvent actionEvent) {
        Button target = (Button) actionEvent.getTarget();
        LevelInfo info = (LevelInfo) target.getUserData();
        Level level = LevelRepository.getInstance().getLevel(info.getLevelId());
        ViewSwitcher.switchView(ViewEnum.LEVEL, level);
    }

    public void handleMoveToBuilderBtn(ActionEvent actionEvent) {
        ViewSwitcher.switchView(ViewEnum.BUILDER);
    }
}
