package edu.union.controller;

import edu.union.service.LevelBuilderFactory;
import edu.union.service.LevelRepository;
import edu.union.service.LevelRepositoryManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import edu.union.model.*;
import edu.union.view.ViewEnum;

public class MenuController {

    /**
     * Switches the view on the display to a playable level.
     * @param actionEvent: An ActionEvent.
     */
    public void handleMoveToLevelBtn(ActionEvent actionEvent) {
        Button target = (Button) actionEvent.getTarget();
        LevelInfo info = (LevelInfo) target.getUserData();
        Level level = LevelRepositoryManager.getInstance().loadLevel(info);
        ViewSwitcher.getInstance().switchView(ViewEnum.LEVEL, level);
    }

    /**
     * Switches the view on the display to a buildable level.
     * @param actionEvent: An ActionEvent.
     */
    public void handleMoveToBuilderBtn(ActionEvent actionEvent) {
        ViewSwitcher.getInstance().switchView(ViewEnum.BUILDER, LevelBuilderFactory.getInstance()
                .createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL));
    }
}
