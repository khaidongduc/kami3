package edu.union.controller;

import edu.union.model.*;
import edu.union.service.ColorRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class RectangleHintInputController {

    private RectangleGridLevelBuilder level;

    public RectangleHintInputController(){}

    public RectangleHintInputController(RectangleGridLevelBuilder level){setLevel(level);}

    public void setLevel(RectangleGridLevelBuilder level){this.level = level;}

    public void handleColorGridBtn(ActionEvent e){
        try{
            Button targetButton = (Button) e.getSource();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            level.setColorFlood(new Move<>(level.getCurrentColor(), new RectangleGridCell(row,col)));
        }catch (Exception error){
            System.out.println("problems");
        }
        boolean isMono = level.getGraph().pruneGraph().getNumVertices() == 1;
        if(isMono){
            System.out.println("win");
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
            System.out.println("This error");
        }
    }

    public void handleRestartBtn(){
        //TODO
    }

    public void handlExitButton(){
        //TODO
    }
}
