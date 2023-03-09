package edu.union.view;

import edu.union.controller.MenuController;
import edu.union.service.LevelRepositoryManager;
import edu.union.utils.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import edu.union.model.LevelInfo;
import edu.union.service.LevelRepository;
import edu.union.utils.Observer;

import java.util.List;

public class MenuView implements View, Observer {

    public final int GRID_NUM_COLUMNS = 2;
    private LevelRepositoryManager levelRepositoryManager;

    private final MenuController menuController;
    private final Scene scene;
    private BorderPane parent;
    private GridPane levelGridPane;

    private Button[][] buttonGrid;


    /**
     * Makes a view object for the menu
     * @param menuController the controller for the menu
     */
    public MenuView(MenuController menuController){
        this.menuController = menuController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);
        bindModel(LevelRepositoryManager.getInstance());
    }

    /**
     * Gets the scene being added on the stage
     * @return the scene for the stage
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Binds the model to the view
     * @param model the model being bound to the view
     */
    @Override
    public void bindModel(Observable model) {
        LevelRepositoryManager LevelRepositoryManager = (LevelRepositoryManager) model;
        if(this.levelRepositoryManager != null)
            this.levelRepositoryManager.detach(this);
        this.levelRepositoryManager = LevelRepositoryManager;
        this.levelRepositoryManager.attach(this);
        renderView();
    }

    /**
     * Renders the view for the menu and makes buttons for the scene
     */
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

        List<LevelInfo> levelInfoList = this.levelRepositoryManager.listLevelInfos();

        int gridNumRows = (levelInfoList.size() + GRID_NUM_COLUMNS - 1) / GRID_NUM_COLUMNS;
        for(int i = 0; i < GRID_NUM_COLUMNS; ++ i) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            levelGridPane.getColumnConstraints().add(columnConstraints);
        }
        for(int i = 0 ; i < gridNumRows + 1; ++ i) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            levelGridPane.getRowConstraints().add(rowConstraints);
        }
        //buttonGrid = new Button[gridNumRows][GRID_NUM_COLUMNS];
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

                //buttonGrid[i][j] = button;
                levelGridPane.add(button, j, i);
            }
        }

        Button builderButton = new Button();
        styleButton(builderButton, "CREATE");
        builderButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(builderButton, true);
        GridPane.setFillHeight(builderButton, true);
        builderButton.setStyle("-fx-background-color: #d1edf2");
        builderButton.setOnAction(menuController::handleMoveToBuilderBtn);

        if(levelInfoList.size() % 2 == 0){
            levelGridPane.add(builderButton, 0, gridNumRows);
        } else{
            levelGridPane.add(builderButton, 1, gridNumRows - 1);
        }

        this.parent.setTop(title);
        this.parent.setAlignment(title, Pos.CENTER);
        this.parent.setCenter(levelGridPane);
    }

    /**
     * Styles the buttons so they are clean and readable
     * @param button the button being styled
     * @param toWrite the text to be displayed on the button
     */
    private void styleButton(Button button, String toWrite){
        button.setText(toWrite);
        button.setStyle("-fx-font: 20px Verdana");
        button.setStyle("-fx-padding: 10");
        button.setEffect(new DropShadow());
        button.setVisible(true);
    }

    /**
     * Updates the observers
     */
    @Override
    public void update() {
        renderView();
    }

}
