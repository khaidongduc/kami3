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
        LevelBuilder newBuild = new LevelBuilder() {
            @Override
            public int getNumRows() {
                return 0;
            }

            @Override
            public int getNumCols() {
                return 0;
            }

            @Override
            public Color getColorAt(int row, int col) {
                return null;
            }

            @Override
            public List<Color> getColors() {
                return null;
            }

            @Override
            public void switchColor(Color color) {

            }

            @Override
            public Color getCurrentColor() {
                return null;
            }

            @Override
            public void setColor(Color color, int row, int col) {

            }

            @Override
            public void setColorFlood(Color color, int row, int col) {

            }

            @Override
            public void changeGridSize(int numRows, int numCols) {

            }

            @Override
            public void restart() {

            }
        };
        Button target = (Button) actionEvent.getTarget();
        ViewSwitcher.switchView(ViewEnum.BUILDER, newBuild);
    }
}
