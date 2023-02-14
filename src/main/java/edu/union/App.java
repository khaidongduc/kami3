package edu.union;

import edu.union.model.*;
import edu.union.controller.*;
import edu.union.service.LevelBuilderFactory;
import edu.union.view.*;
import edu.union.service.LevelRepositoryManager;
import edu.union.service.RectangleGridLevelRepository;
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

        stage.setScene(menuView.getScene());
        stage.show();
    }

    /**
     * Configurates the level repository manager to use type Rectangle_Grid_Level
     */
    public void configService(){
        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, RectangleGridLevelRepository.getInstance());

        LevelBuilderFactory levelBuilderFactory = LevelBuilderFactory.getInstance();
        levelBuilderFactory.register(LevelType.RECTANGLE_GRID_LEVEL, new RectangleGridLevelBuilder(5,5));
    }
    public static void main(String[] args){launch(args);}
}