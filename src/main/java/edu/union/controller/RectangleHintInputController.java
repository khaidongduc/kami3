package edu.union.controller;

import edu.union.model.*;
import edu.union.utils.Command;
import edu.union.utils.CommandInvoker;
import edu.union.utils.PlayMoveCommand;
import edu.union.view.ViewEnum;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class RectangleHintInputController {

    private RectangleHintInputLevel level;
    private RectangleHintInputLevel startLevel;

    public RectangleHintInputController(){}

    public RectangleHintInputController(RectangleHintInputLevel level){setLevel(level);}

    public void setLevel(RectangleHintInputLevel level){
        this.level = level;
        this.startLevel = new RectangleHintInputLevel(new ColoredGraph(level.getGraph()), level.getRows(), level.getCols());
    }

    public void handleColorGridBtn(ActionEvent e){
        try{
            Button targetButton = (Button) e.getSource();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            Command move = new PlayMoveCommand<>(this.level,new Move<>(level.getCurrentColor(), new RectangleGridCell(row,col)));
            CommandInvoker.getInstance().invoke(move);
        }catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    public void handleChooseColorBtn(ActionEvent e){
        try{
            Button targetButton = (Button) e.getTarget();
            Color color = (Color) targetButton.getUserData();
            level.switchColor(color);
        }catch (Exception error){
            Alert a = new Alert(Alert.AlertType.WARNING, e.toString());
            a.show();
        }
    }

    public void handleRestartBtn(){
        CommandInvoker.getInstance().reset();
        ViewSwitcher.getInstance().switchView(ViewEnum.HINT,startLevel);
    }

    public void handleExitButton(){
        ViewSwitcher.getInstance().switchView(ViewEnum.MENU);
    }
}
