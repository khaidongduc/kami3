package edu.union.view;

import edu.union.controller.RectangleHintInputController;
import edu.union.model.*;
import edu.union.service.ColorRepository;
import edu.union.utils.Observable;
import edu.union.utils.Observer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangleHintInputView implements View, Observer {

    private final RectangleHintInputController controller;
    private RectangleHintInputLevel level;
    private final Scene scene;
    private BorderPane parent;
    private Button[][] buttonGrid;
    private Map<Color,Button> colorToChooseButton;
    private Alert savingAlert;
    public RectangleHintInputView(RectangleHintInputController controller) {
        this.controller = controller;
        this.parent = new BorderPane();
        this.scene = new Scene(this.parent, 400,400);
    }

    public RectangleHintInputView(RectangleHintInputController controller, RectangleHintInputLevel level){
        this(controller);
        level.attach(this);
        bindModel(level);
    }

    public void renderView(){
        this.parent = new BorderPane();
        scene.setRoot(this.parent);

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
            button.setOnAction(controller::handleChooseColorBtn);
            button.setUserData(color);
            colorChoiceGrid.add(button, 0,count++);
            colorToChooseButton.put(color, button);
        }

        parent.setRight(colorChoiceGrid);

        //The Kami board to give hints for
        GridPane colorGridPane = new GridPane();
        colorGridPane.setHgap(10);
        colorGridPane.setVgap(10);
        colorGridPane.setPadding(new Insets(10, 10, 10, 10));
        colorGridPane.setGridLinesVisible(false);
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
                button.setStyle(button.getStyle() + String.format("-fx-background-color: rgb(%d, %d, %d);",
                        color.getRValue(), color.getGValue(), color.getBValue()));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                buttonGrid[i][j] = button;
                colorGridPane.add(button, j, i);
                button.setOnAction(controller::handleColorGridBtn);
            }
        }
        parent.setCenter(colorGridPane);

        //Reset and Exit buttons
        GridPane optionsGrid = new GridPane();
        optionsGrid.setPrefHeight(100);
        optionsGrid.setHgap(10);
        optionsGrid.setVgap(10);
        optionsGrid.setPadding(new Insets(10, 10, 10, 10));
        optionsGrid.setGridLinesVisible(false);
        Button restartBtn = new Button("Restart");
        Button exitBtn = new Button("Exit");
        restartBtn.setOnAction(event -> controller.handleRestartBtn());
        exitBtn.setOnAction(event -> controller.handleExitButton());
        optionsGrid.add(exitBtn,0,0);
        optionsGrid.add(restartBtn,1,0);
        parent.setBottom(optionsGrid);

        ButtonType MOVE_TO_MENU = new ButtonType("Move to Menu");
        savingAlert = new Alert(Alert.AlertType.CONFIRMATION, "Thank you for solving the board!", MOVE_TO_MENU);
        savingAlert.setOnCloseRequest(event -> {controller.handleSave();});
    }

    @Override
    public Scene getScene() {return this.scene;}

    @Override
    public void bindModel(Observable obj) {
        RectangleHintInputLevel level = ( RectangleHintInputLevel) obj;
        if(this.level != null){
            this.level.detach(this);
        }
        level.attach(this);
        this.controller.setLevel(level);
        this.level = level;
        renderView();
    }
    @Override
    public void update() {
        // color grid
        for(int i = 0 ; i < level.getRows() ; ++ i) {
            for (int j = 0; j < level.getRows(); ++j) {
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

        LevelState levelState = level.getLevelState();
        if(levelState.equals(LevelState.WIN)){
            savingAlert.show();
        }
    }
}
