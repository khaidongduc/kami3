package edu.union.view;

import edu.union.controller.RectangleGridLevelBuildController;
import edu.union.model.RectangleGridCell;
import edu.union.model.RectangleGridLevelBuilder;
import edu.union.utils.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import edu.union.model.Color;
import edu.union.service.ColorRepository;
import edu.union.utils.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangleGridLevelBuildView implements View, Observer {
    private final RectangleGridLevelBuildController buildController;
    private final Scene scene;
    private BorderPane parent;
    private RectangleGridLevelBuilder level;

    private Button[][] buttonGrid;
    private Map<Color,Button> colorToChooseButton;
    private Alert resultAlert;

    /**
     * Makes a view object for a rectangle grid builder
     * @param buildController the rectangle level build controller
     */
    public RectangleGridLevelBuildView(RectangleGridLevelBuildController buildController) {
        this.buildController = buildController;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400, 400);

        this.level = buildController.getLevelBuilder();
        level.attach(this);
        bindModel(level);
    }

    /**
     * Renders the view for the rectangle level builder
     */
    private void renderView() {
        this.parent = new BorderPane();
        scene.setRoot(this.parent);

        //Kami board buttons
        GridPane colorGridPane = new GridPane();

        colorGridPane.setGridLinesVisible(true);
        colorGridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        for(int i = 0 ; i < level.getRows() ; ++ i) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setHgrow(Priority.ALWAYS);
            colorGridPane.getColumnConstraints().add(columnConstraints);
        }
        for(int i = 0 ; i < level.getCols() ; ++ i) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setVgrow(Priority.ALWAYS);
            colorGridPane.getRowConstraints().add(rowConstraints);
        }
        buttonGrid = new Button[level.getRows()][level.getCols()];
        for(int i = 0 ; i < level.getRows() ; ++ i) {
            for (int j = 0; j < level.getCols(); ++j) {
                Button button = new Button();
                Color color = level.getColorAt(new RectangleGridCell(i, j));
                button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d); -fx-border-color: black;",
                        color.getRValue(), color.getGValue(), color.getBValue()));
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
        Button curColorChooseButton = colorToChooseButton.get(level.getCurrentColor());
        curColorChooseButton.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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
        saveBtn.setOnAction(e -> {
            saveBtn.setDisable(true);
            Alert loading = new Alert(Alert.AlertType.NONE, "Loading... Please wait...");
            //loading.show();
            try {
                //loading.show();
                buildController.handleSaveBtn(e);
            } catch (RuntimeException error) {
                loading.close();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(error.getMessage());
                a.show();
            }
            loading.close();
            saveBtn.setDisable(false);
        });
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

    }

    /**
     * Updates the observers
     */
    @Override
    public void update() {
        this.renderView();
    }

    /**
     * Gets the scene of the rectangle level builder view
     * @return returns the scene of the rectangle level builder
     */
    @Override
    public Scene getScene() {return scene;}

    /**
     * Binds the model
     * @param obj the object being bound
     */
    @Override
    public void bindModel(Observable obj){
        RectangleGridLevelBuilder level = (RectangleGridLevelBuilder) obj;
        if(this.level != null){
            this.level.detach(this);
        }
        level.attach(this);
        this.buildController.setLevelBuilder(level);
        this.level = level;
        renderView();
    }
}
