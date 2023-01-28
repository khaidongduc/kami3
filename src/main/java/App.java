import controller.LevelController;
import controller.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Level;
import model.LevelImpl;
import view.LevelView;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        ViewSwitcher.setStage(stage);

        Level level = new LevelImpl(1);
        LevelController controller = new LevelController(level);
        LevelView view = new LevelView(controller, level);
        ViewSwitcher.addView("Level1", view);

        Level level2 = new LevelImpl(3);
        LevelController controller2 = new LevelController(level2);
        LevelView view2 = new LevelView(controller2, level2);
        ViewSwitcher.addView("Level2", view2);

        stage.setScene(view.getScene());
        stage.show();
    }
    public static void main(String[] args){launch(args);}
}