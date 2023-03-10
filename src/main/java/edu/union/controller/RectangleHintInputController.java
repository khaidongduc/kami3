package edu.union.controller;

import edu.union.model.*;
import edu.union.view.ViewEnum;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class RectangleHintInputController {

    private RectangleHintInputLevel level;

    public RectangleHintInputController(){}

    public RectangleHintInputController(RectangleHintInputLevel level){setLevel(level);}

    public void setLevel(RectangleHintInputLevel level){
        this.level = level;

    }

    public void handleColorGridBtn(ActionEvent e){
        try{
            Button targetButton = (Button) e.getSource();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            level.play(new Move<>(level.getCurrentColor(), new RectangleGridCell(row,col)));
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

    public void handleRestartBtn(){}

    public void handleExitButton(){
        ViewSwitcher.getInstance().switchView(ViewEnum.MENU);
    }
}
