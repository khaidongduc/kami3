package edu.union.view;

import edu.union.controller.LevelController;
import edu.union.model.Move;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import edu.union.model.Color;
import edu.union.model.Level;
import edu.union.model.LevelState;
import edu.union.utils.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelView implements View, Observer {

    private final LevelController levelController;
    private Level level;

    private final Scene scene;
    private BorderPane parent;
    private GridPane colorGridPane;

    private Button[][] buttonGrid;
    private Map<Color, Button> colorToChooseButton;
    private Label numMoveRemainingLabel;

    private Alert resultAlert;

    public Scene getScene() {
        return scene;
    }

    public LevelView(LevelController levelController){
        this.levelController = levelController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);
    }

    public LevelView(LevelController levelController, Level level){
        this(levelController);
        level.attach(this);
        bindModel(level);
    }

    @Override
    public void bindModel(Object obj){
        Level level = (Level) obj;
        if(this.level != null)
            this.level.detach(this);
        level.attach(this);
        this.levelController.setLevel(level);
        this.level = level;
        renderView();
    }

    private void renderView(){
        this.parent = new BorderPane();
        scene.setRoot(this.parent);

        GridPane colorGridPane = new GridPane();

        colorGridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        colorGridPane.setVgap(10); //vertical gap in pixels
        colorGridPane.setPadding(new Insets(10, 10, 10, 10));
        colorGridPane.setGridLinesVisible(false);
        colorGridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        for(int i = 0 ; i < level.getNumCols() ; ++ i) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            colorGridPane.getColumnConstraints().add(columnConstraints);
        }
        for(int i = 0 ; i < level.getNumCols() ; ++ i) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            colorGridPane.getRowConstraints().add(rowConstraints);
        }
        buttonGrid = new Button[level.getNumRows()][level.getNumCols()];
        for(int i = 0 ; i < level.getNumRows() ; ++ i) {
            for (int j = 0; j < level.getNumCols(); ++j) {
                Button button = new Button();
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                button.setOnAction(levelController::handleColorGridBtn);
                buttonGrid[i][j] = button;
                colorGridPane.add(button, j, i);
            }
        }

        parent.setCenter(colorGridPane);

        colorToChooseButton = new HashMap<>();
        GridPane colorChoiceGrid = new GridPane();
        colorChoiceGrid.setPrefHeight(100);
        colorChoiceGrid.setHgap(10);
        colorChoiceGrid.setVgap(10);
        colorChoiceGrid.setPadding(new Insets(10, 10, 10, 10));
        colorChoiceGrid.setGridLinesVisible(false);

        int count = 0;

        numMoveRemainingLabel = new Label();
        colorChoiceGrid.add(numMoveRemainingLabel, count++, 0);
        numMoveRemainingLabel.setPrefSize(10, 10);

        List<Color> colors = level.getColors();
        for(Color color: colors){
            Button button = new Button();
            button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d);",
                    color.getRValue(), color.getGValue(), color.getBValue()));
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setOnAction(levelController::handleChooseColorBtn);
            button.setUserData(color);
            colorChoiceGrid.add(button, 0, count++);
            colorToChooseButton.put(color, button);
        }
        parent.setRight(colorChoiceGrid);


        GridPane optionsGrid = new GridPane();
        optionsGrid.setPrefHeight(100);
        optionsGrid.setHgap(10);
        optionsGrid.setVgap(10);
        optionsGrid.setPadding(new Insets(10, 10, 10, 10));
        optionsGrid.setGridLinesVisible(false);
        Button restartBtn = new Button("Restart");
        Button exitBtn = new Button("Exit");
        Button getHintsBtn = new Button("Get Hints");

        restartBtn.setOnAction(event -> levelController.handleRestartBtn());
        exitBtn.setOnAction(event -> levelController.handleMoveToMenuBtn());
        getHintsBtn.setOnAction(event -> {

            List<Move> hints = level.getHints();
            for(Move move : hints){
                Color color = move.getColor();
                System.out.println(String.format("RGB(%d,%d,%d) row:%d col:%d",
                        color.getRValue(), color.getGValue(), color.getBValue(),
                        move.getRow(), move.getCol()));
            }

        });
        optionsGrid.add(exitBtn,0,0);
        optionsGrid.add(restartBtn,0,1);
        optionsGrid.add(getHintsBtn,1,1);


        parent.setBottom(optionsGrid);

        ButtonType RESTART = new ButtonType("Restart");
        ButtonType MOVE_TO_MENU = new ButtonType("Move to Menu");

        resultAlert = new Alert(Alert.AlertType.CONFIRMATION, "",
                RESTART, MOVE_TO_MENU);
        resultAlert.setOnCloseRequest(event -> {
            ButtonType result = resultAlert.getResult();
            if(result.equals(RESTART)){
                levelController.handleRestartBtn();
            } else {
                levelController.handleMoveToMenuBtn();
            }
        });
        update();
    }

    @Override
    public void update() {
        // color grid
        for(int i = 0 ; i < level.getNumRows() ; ++ i) {
            for (int j = 0; j < level.getNumCols(); ++j) {
                Color color = level.getColorAt(i, j);
                Button button = buttonGrid[i][j];
                button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d);",
                        color.getRValue(), color.getGValue(), color.getBValue()));

            }
        }
        // current color
        for(Button button : colorToChooseButton.values()){
            button.setBorder(new Border(new BorderStroke[]{}));
        }
        Button curColorChooseButton = colorToChooseButton.get(level.getCurrentColor());
        curColorChooseButton.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // numMove remaining
        numMoveRemainingLabel.textProperty().set(String.valueOf(level.numMoveRemaining()));

        // check won
        LevelState levelState = level.getLevelState();
        if (!levelState.equals(LevelState.ONGOING)) {
            if (levelState.equals(LevelState.WIN)) {
                resultAlert.setContentText("You win");
            } else {
                resultAlert.setContentText("You lose");
            }
            resultAlert.show();
        }
    }
}
