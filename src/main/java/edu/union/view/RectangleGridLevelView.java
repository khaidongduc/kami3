package edu.union.view;

import edu.union.controller.RectangleGridLevelController;
import edu.union.model.*;
import edu.union.utils.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import edu.union.utils.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangleGridLevelView implements View, Observer {

    private final RectangleGridLevelController levelController;
    private RectangleGridLevel level;

    private final Scene scene;
    private BorderPane parent;
    private GridPane colorGridPane;

    private Button[][] buttonGrid;
    private Map<Color, Button> colorToChooseButton;
    private Label numMoveRemainingLabel;

    private Alert resultAlert;

    /**
     * Makes a rectangle grid level view object
     * @param levelController the controller for the view
     */
    public RectangleGridLevelView(RectangleGridLevelController levelController){
        this.levelController = levelController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);
    }

    public RectangleGridLevelView(RectangleGridLevelController levelController, RectangleGridLevel level){
        this(levelController);
        level.attach(this);
        bindModel(level);
    }

    /**
     *  Gets the scene for the rectangle grid level view
     * @return the scene for the view
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Binds the model
     * @param obj the object being bound
     */
    @Override
    public void bindModel(Observable obj){
        RectangleGridLevel level = (RectangleGridLevel) obj;
        if(this.level != null)
            this.level.detach(this);
        level.attach(this);
        this.levelController.setLevel(level);
        this.level = level;
        renderView();
    }

    /**
     * Renders the view for the rectangle grid level view
     */
    private void renderView(){
        this.parent = new BorderPane();
        scene.setRoot(this.parent);

        GridPane colorGridPane = new GridPane();

        colorGridPane.setGridLinesVisible(true);
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
                button.setStyle("-fx-border-color: black;");
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

        int count = 2;

        numMoveRemainingLabel = new Label();
        numMoveRemainingLabel.setAlignment(Pos.CENTER);
        colorChoiceGrid.add(numMoveRemainingLabel, count++, 0);
        numMoveRemainingLabel.setPrefSize(10, 10);

        GridPane controlMenu = new GridPane();
        controlMenu.setPrefWidth(150);
        controlMenu.setPrefHeight(100);
        controlMenu.setGridLinesVisible(false);

        List<Color> colors = level.getColors();
        for(Color color: colors){
            Button button = new Button();
            button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d); -fx-border-width: 2 2 2 2;",
                    color.getRValue(), color.getGValue(), color.getBValue()));
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setOnAction(levelController::handleChooseColorBtn);
            button.setUserData(color);

            colorChoiceGrid.add(button, count++, 0);
            colorToChooseButton.put(color, button);
        }

        GridPane optionsGrid = new GridPane();
        optionsGrid.setPrefHeight(100);
        optionsGrid.setPrefWidth(150);
        optionsGrid.setGridLinesVisible(false);

        Button restartBtn = new Button("Restart");
        restartBtn.setMinHeight(optionsGrid.getPrefHeight() / 2);
        restartBtn.setMinWidth(optionsGrid.getPrefWidth() / 2);
        restartBtn.setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2;");

        Button exitBtn = new Button("Exit");
        exitBtn.setMinHeight(optionsGrid.getPrefHeight() / 2);
        exitBtn.setMinWidth(optionsGrid.getPrefWidth() / 2);
        exitBtn.setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2;");

        Button getHintsBtn = new Button("Hints");
        getHintsBtn.setMinHeight(optionsGrid.getPrefHeight() / 2);
        getHintsBtn.setMinWidth(optionsGrid.getPrefWidth() / 2);
        getHintsBtn.setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2;");

        Button undoBtn = new Button("Undo");
        undoBtn.setMinHeight(optionsGrid.getPrefHeight() / 2);
        undoBtn.setMinWidth(optionsGrid.getPrefWidth() / 2);
        undoBtn.setStyle("-fx-border-color: black; -fx-border-width: 2 2 2 2;");

        undoBtn.setOnAction(event -> levelController.handleUndoButton());
        restartBtn.setOnAction(event -> levelController.handleRestartBtn());
        exitBtn.setOnAction(event -> levelController.handleMoveToMenuBtn());
        getHintsBtn.setOnAction(event -> {

            levelController.handleRestartBtn();
            List<Move<RectangleGridCell>> hints = level.getHints();
            StringBuilder readableHints = new StringBuilder();
            int hintNumber = 1;
            for (Move<RectangleGridCell> move : hints) {
                readableHints.append(String.valueOf(hintNumber)).append(". ");
                hintNumber += 1;

                Color color = move.getColor();
                readableHints.append(color.getReadableColor(String.format("RGB(%d,%d,%d)", color.getRValue(),
                        color.getGValue(), color.getBValue())));
                readableHints.append(String.format(" row: %s | col %s", move.getVertex().row, move.getVertex().col)).append("\n");
            }

            Alert hintAlert = new Alert(Alert.AlertType.INFORMATION, readableHints.toString());
            hintAlert.setHeaderText("Hints");
            hintAlert.show();

        });
        optionsGrid.add(exitBtn,0,0);
        optionsGrid.add(restartBtn,0,1);
        optionsGrid.add(getHintsBtn,1,1);
        optionsGrid.add(undoBtn, 1, 0);

        //parent.setBottom(optionsGrid);
        controlMenu.add(optionsGrid, 0, 0);
        controlMenu.add(colorChoiceGrid, 1, 0);
        parent.setBottom(controlMenu);

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

    /**
     * Updates the observers
     */
    @Override
    public void update() {
        // color grid
        for(int i = 0 ; i < level.getNumRows() ; ++ i) {
            for (int j = 0; j < level.getNumCols(); ++j) {
                Color color = level.getColorAt(new RectangleGridCell(i, j));
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
