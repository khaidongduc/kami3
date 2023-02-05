package edu.union.view;

import edu.union.controller.BuildController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import edu.union.model.Color;
import edu.union.model.ColorRepository;
import edu.union.model.LevelBuilder;
import edu.union.utils.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildView  implements View, Observer {
    private final BuildController buildController;
    private final Scene scene;
    private BorderPane parent;
    private LevelBuilder level;

    private Button[][] buttonGrid;
    private Map<Color,Button> colorToChooseButton;
    private Alert resultAlert;
    public BuildView(BuildController buildController) {
        this.buildController = buildController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);

        this.level = buildController.getLevel();
        level.attach(this);
        bindModel(level);
    }

    private void renderView() {
        this.parent = new BorderPane();
        scene.setRoot(this.parent);

        //Kami board buttons
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
                button.setOnAction(buildController::handleColorGridBtn);
                buttonGrid[i][j] = button;
                colorGridPane.add(button, j, i);
            }
        }

        parent.setCenter(colorGridPane);

        //Color choice buttons
        colorToChooseButton = new HashMap<>();
        GridPane colorChoiceGrid = new GridPane();
        colorChoiceGrid.setPrefHeight(100);
        colorChoiceGrid.setHgap(10);
        colorChoiceGrid.setVgap(10);
        colorChoiceGrid.setPadding(new Insets(10, 10, 10, 10));
        colorChoiceGrid.setGridLinesVisible(false);

        int count = 0;

        List<Color> colors = ColorRepository.getInstance().listColors();
        for(Color color: colors){
            Button button = new Button();
            button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d);",
                    color.getRValue(), color.getGValue(), color.getBValue()));
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setOnAction(buildController::handleChooseColorBtn);
            button.setUserData(color);
            colorChoiceGrid.add(button, 0,count++);
            colorToChooseButton.put(color, button);
        }
        parent.setRight(colorChoiceGrid);

        // Exit, restart, and save buttons
        GridPane optionsGrid = new GridPane();
        optionsGrid.setPrefHeight(100);
        optionsGrid.setHgap(10);
        optionsGrid.setVgap(10);
        optionsGrid.setPadding(new Insets(10, 10, 10, 10));
        optionsGrid.setGridLinesVisible(false);
        Button restartBtn = new Button("Restart");
        Button saveBtn = new Button("Save");
        Button exitBtn = new Button("Exit");
        Label rowLabel = new Label("Number of rows:");
        Label colLabel = new Label("Number of cols:");
        TextField rowInput = new TextField();
        TextField colInput = new TextField();
        Button resizeBtn = new Button("Resize");
        resizeBtn.setOnAction(event -> {
            buildController.handleResizeBtn(rowInput.getText(),colInput.getText());
        });
        restartBtn.setOnAction(buildController::handleRestartBtn);
        saveBtn.setOnAction(buildController::handleSaveBtn);
        exitBtn.setOnAction(buildController::handleExitBtn);
        optionsGrid.add(exitBtn,0,0);
        optionsGrid.add(restartBtn,0,1);
        optionsGrid.add(saveBtn,0,2);
        optionsGrid.add(rowLabel,1,0);
        optionsGrid.add(rowInput,2,0);
        optionsGrid.add(colLabel,1,1);
        optionsGrid.add(colInput,2,1);
        optionsGrid.add(resizeBtn,2,2);
        parent.setBottom(optionsGrid);

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
    }

    @Override
    public Scene getScene() {return scene;}

    @Override
    public void bindModel(Object obj){
        LevelBuilder level = (LevelBuilder) obj;
        if(this.level != null){
            this.level.detach(this);
        }
        level.attach(this);
        this.buildController.setLevel(level);
        this.level = level;
        renderView();
    }
}
