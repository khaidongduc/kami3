package edu.union;

import edu.union.controller.BuildController;
import edu.union.controller.LevelController;
import edu.union.controller.MenuController;
import edu.union.controller.ViewSwitcher;
import edu.union.service.LevelRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import edu.union.view.BuildView;
import edu.union.view.LevelView;
import edu.union.view.MenuView;
import edu.union.view.ViewEnum;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        LevelRepository.getInstance().setLevelRepositoryStrategy(Config.levelRepositoryStrategy);

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