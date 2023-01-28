import controller.LevelController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Level;
import model.LevelImpl;
import view.LevelView;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Level model = new LevelImpl(1);
        LevelController controller = new LevelController(model);
        LevelView view = new LevelView(controller, model);

        Scene scene = new Scene(view.asParent(), 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){launch(args);}
}