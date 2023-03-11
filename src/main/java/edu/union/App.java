package edu.union;

import edu.union.model.*;
import edu.union.controller.*;
import edu.union.service.*;
import edu.union.view.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        configService();

        ViewSwitcher viewSwitcher = ViewSwitcher.getInstance();

        viewSwitcher.setStage(stage);

        MenuController menuController = new MenuController();
        MenuView menuView = new MenuView(menuController);
        viewSwitcher.addView(ViewEnum.MENU, menuView);

        RectangleGridLevelController levelController = new RectangleGridLevelController();
        RectangleGridLevelView levelView = new RectangleGridLevelView(levelController);
        viewSwitcher.addView(ViewEnum.LEVEL, levelView);

        RectangleGridLevelBuildController buildController = new RectangleGridLevelBuildController();
        RectangleGridLevelBuildView buildView = new RectangleGridLevelBuildView(buildController);
        viewSwitcher.addView(ViewEnum.BUILDER, buildView);

        RectangleHintInputController hintController = new RectangleHintInputController();
        RectangleHintInputView hintView = new RectangleHintInputView(hintController);
        viewSwitcher.addView(ViewEnum.HINT, hintView);

        stage.setScene(menuView.getScene());
        stage.show();

    }


    /**
     * Configurates the level repository manager to use type Rectangle_Grid_Level
     */
    public void configService(){
        ColorRepository colorRepository = ColorRepository.getInstance();
        for (Color color : Config.DEFAULT_COLORS){
            colorRepository.addColor(color);
        }
        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, TextRectangleGridLevelRepository.getInstance());
        TextRectangleGridLevelRepository.getInstance().setSuccessor(
                Text2RectangleGridLevelRepository.getInstance()
        );
        LevelBuilderFactory levelBuilderFactory = LevelBuilderFactory.getInstance();
        levelBuilderFactory.register(LevelType.RECTANGLE_GRID_LEVEL,
                new RectangleGridLevelBuilder(Config.RECT_LEVEL_BUILDER_ROWS,Config.RECT_LEVEL_BUILDER_COLS));
    }
    public static void main(String[] args){launch(args);}
}