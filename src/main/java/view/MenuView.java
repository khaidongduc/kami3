package view;

import controller.MenuController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import model.LevelInfo;
import model.LevelRepository;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class MenuView implements View, Observer {

    public final int gridNumColumns = 2;
    private LevelRepository levelRepository;

    private MenuController menuController;
    private Scene scene;
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

        levelGridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        levelGridPane.setVgap(10); //vertical gap in pixels
        levelGridPane.setPadding(new Insets(10, 10, 10, 10));
        levelGridPane.setGridLinesVisible(false);
        levelGridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        List<LevelInfo> levelInfoList = this.levelRepository.listLevelInfo();

        int gridNumRows = (levelInfoList.size() + gridNumColumns - 1) / gridNumColumns;
        for(int i = 0 ; i < gridNumColumns ; ++ i) {
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
        buttonGrid = new Button[gridNumRows][gridNumColumns];
        for(int i = 0 ; i < gridNumRows ; ++ i){
            for(int j = 0 ; j < gridNumColumns ; ++ j) {
                int idx = i * gridNumColumns + j;
                if(idx >= levelInfoList.size())
                    continue;
                Button button = new Button();
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                button.setUserData(levelInfoList.get(idx));
                button.setText(String.valueOf(levelInfoList.get(idx).getLevelId()));
                button.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                button.setStyle(button.getStyle() + "-fx-background-color: rgb(255, 192, 203);");
                button.setOnAction(menuController::handleMoveToLevelBtn);
                button.setVisible(true);
                buttonGrid[i][j] = button;
                levelGridPane.add(button, j, i);
            }
        }

        this.parent.setCenter(levelGridPane);
    }


    @Override
    public void update() {
        renderView();
    }

}
