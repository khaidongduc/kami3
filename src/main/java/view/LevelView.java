package view;

import com.sun.rowset.internal.Row;
import controller.LevelController;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import model.Color;
import model.Level;
import utils.Observer;

import java.util.List;

public class LevelView implements Observer {

    private LevelController levelController;
    private Level level;
    private BorderPane parent;
    private GridPane colorGridPane;
    private Button[][] buttonGrid;

    public Parent asParent() {
        return parent;
    }

    public LevelView(LevelController levelController, Level level){
        this.levelController = levelController;
        this.level = level;
        level.attach(this);

        this.parent = new BorderPane();

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
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                button.setOnAction(levelController::handleGridClickEvent);
                buttonGrid[i][j] = button;
                colorGridPane.add(button, i, j);
            }
        }

        parent.setCenter(colorGridPane);

        StackPane colorPane = new StackPane();
        List<Color> colors = level.getColors();
        for(Color color: colors){
            Button button = new Button();
            button.setStyle(String.format("-fx-background-color: rgb(%d, %d, %d);",
                    color.getRValue(), color.getGValue(), color.getBValue()));
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            colorPane.getChildren().add(button);
            button.setOnAction(levelController::handleGridClickEvent);
        }

        parent.setBottom(colorPane);
        update();
    }

    @Override
    public void update() {
        for(int i = 0 ; i < level.getNumRows() ; ++ i) {
            for (int j = 0; j < level.getNumCols(); ++j) {
                Color color = level.getColorAt(i, j);
                Button button = buttonGrid[i][j];
                button.setStyle(String.format("-fx-background-color: rgb(%d, %d, %d);",
                        color.getRValue(), color.getGValue(), color.getBValue()));

            }
        }
    }
}
