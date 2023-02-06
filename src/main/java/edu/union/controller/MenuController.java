package edu.union.controller;

import edu.union.service.LevelRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import edu.union.model.*;
import edu.union.view.ViewEnum;

public class MenuController {

    public void handleMoveToLevelBtn(ActionEvent actionEvent) {
        Button target = (Button) actionEvent.getTarget();
        LevelInfo info = (LevelInfo) target.getUserData();
        Level level = LevelRepository.getInstance().loadLevel(info);
        ViewSwitcher.getInstance().switchView(ViewEnum.LEVEL, level);
    }

    public void handleMoveToBuilderBtn(ActionEvent actionEvent) {
        ViewSwitcher.getInstance().switchView(ViewEnum.BUILDER);
    }
}
