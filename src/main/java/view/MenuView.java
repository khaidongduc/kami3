package view;

import controller.MenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.LevelInfo;
import model.LevelRepository;
import utils.Observer;

import java.util.List;

public class MenuView implements View, Observer {

    public final int GRID_NUM_COLUMNS = 2;
    private LevelRepository levelRepository;

    private final MenuController menuController;
    private final Scene scene;
    private BorderPane parent;
    private GridPane levelGridPane;

    private Button[][] buttonGrid;


    public MenuView(MenuController menuController){
        this.menuController = menuController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);
        bindModel(LevelRepository.getInstance());
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void bindModel(Object model) {
        LevelRepository levelRepository = (LevelRepository) model;
        if(this.levelRepository != null)
            this.levelRepository.detach(this);
        this.levelRepository = levelRepository;
        this.levelRepository.attach(this);
        renderView();
    }

    private void renderView() {
        this.parent = new BorderPane();
        scene.setRoot(this.parent);
        levelGridPane = new GridPane();

        Text title = new Text(0, 0, "KAMI 3");
        title.setStyle("-fx-stroke: black");
        title.setStyle("-fx-font: 40px Verdana");

        levelGridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        levelGridPane.setVgap(10); //vertical gap in pixels
        levelGridPane.setPadding(new Insets(10, 10, 10, 10));
        levelGridPane.setGridLinesVisible(false);
        levelGridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        List<LevelInfo> levelInfoList = this.levelRepository.listLevelInfo();

        int gridNumRows = (levelInfoList.size() + GRID_NUM_COLUMNS - 1) / GRID_NUM_COLUMNS;
        for(int i = 0; i < GRID_NUM_COLUMNS; ++ i) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            levelGridPane.getColumnConstraints().add(columnConstraints);
        }
        for(int i = 0 ; i < gridNumRows ; ++ i) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            levelGridPane.getRowConstraints().add(rowConstraints);
        }
        buttonGrid = new Button[gridNumRows][GRID_NUM_COLUMNS];
        for(int i = 0 ; i < gridNumRows ; ++ i){
            for(int j = 0; j < GRID_NUM_COLUMNS; ++ j) {
                int idx = i * GRID_NUM_COLUMNS + j;
                if(idx >= levelInfoList.size())
                    continue;
                Button button = new Button();
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);

                button.setUserData(levelInfoList.get(idx));
                styleButton(button, "Level " + String.valueOf(levelInfoList.get(idx).getLevelId()));
                button.setStyle("-fx-background-color: #d1edf2");
                button.setOnAction(menuController::handleMoveToLevelBtn);

                buttonGrid[i][j] = button;
                levelGridPane.add(button, j, i);
            }
        }

        Button builderButton = new Button();
        styleButton(builderButton, "Build your own!");
        builderButton.setStyle("-fx-background-color: #d1edf2");
        builderButton.setOnAction(menuController::handleMoveToBuilderBtn);

        this.parent.setBottom(builderButton);
        this.parent.setAlignment(builderButton, Pos.CENTER);

        this.parent.setTop(title);
        this.parent.setAlignment(title, Pos.CENTER);
        this.parent.setCenter(levelGridPane);
    }

    private void styleButton(Button button, String toWrite){
        button.setText(toWrite);
        button.setStyle("-fx-font: 20px Verdana");
        button.setStyle("-fx-padding: 10");
        button.setEffect(new DropShadow());
        button.setVisible(true);
    }


    @Override
    public void update() {
        renderView();
    }

}
