import controller.LevelController;
import controller.MenuController;
import controller.ViewSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import view.LevelView;
import view.MenuView;
import view.ViewEnum;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        ViewSwitcher.setStage(stage);

        MenuController menuController = new MenuController();
        MenuView menuView = new MenuView(menuController);
        ViewSwitcher.addView(ViewEnum.MENU, menuView);

        LevelController levelController = new LevelController();
        LevelView levelView = new LevelView(levelController);
        ViewSwitcher.addView(ViewEnum.LEVEL, levelView);

        stage.setScene(menuView.getScene());
        stage.show();
    }
    public static void main(String[] args){launch(args);}
}