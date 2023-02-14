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

        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, RectangleGridLevelRepository.getInstance());

        LevelBuilderFactory levelBuilderFactory = LevelBuilderFactory.getInstance();
        levelBuilderFactory.register(LevelType.RECTANGLE_GRID_LEVEL, new RectangleGridLevelBuilder(5,5));


        ViewSwitcher viewSwitcher = ViewSwitcher.getInstance();

        viewSwitcher.setStage(stage);

        MenuController menuController = new MenuController();
        MenuView menuView = new MenuView(menuController);
        viewSwitcher.addView(ViewEnum.MENU, menuView);

        LevelController levelController = new LevelController();
        LevelView levelView = new LevelView(levelController);
        viewSwitcher.addView(ViewEnum.LEVEL, levelView);

        BuildController buildController = new BuildController();
        BuildView buildView = new BuildView(buildController);
        viewSwitcher.addView(ViewEnum.BUILDER, buildView);

        stage.setScene(menuView.getScene());
        stage.show();
    }
    public static void main(String[] args){launch(args);}
}