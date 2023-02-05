package edu.union.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import edu.union.model.Color;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelBuilderImpl;
import edu.union.model.LevelRepository;
import edu.union.view.ViewEnum;

public class BuildController {

    private LevelBuilder level;
    public BuildController(){
        level = new LevelBuilderImpl();
    }

    public void handleChooseColorBtn(ActionEvent action){
        try{
            Button targetButton = (Button) action.getTarget();
            Color color = (Color) targetButton.getUserData();
            level.switchColor(color);
            System.out.println(color);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }
    public void handleColorGridBtn(ActionEvent action){
        try {
            Button targetButton = (Button) action.getTarget();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            level.setColor(level.getCurrentColor(),row,col);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }
    public void handleRestartBtn(ActionEvent e){level.restart();}
    public void handleExitBtn(ActionEvent e){ViewSwitcher.getInstance().switchView(ViewEnum.MENU);}

    //Save functionality is not implemented.
    public void handleSaveBtn(ActionEvent e){
        LevelRepository.getInstance().saveLevel(this.level);
        level.restart();
    }

    public void handleResizeBtn(String rowInput, String colInput){
        try{
            int rows = Integer.parseInt(rowInput);
            int cols = Integer.parseInt(colInput);
            if(0 < rows && 0 < cols){
                this.level = new LevelBuilderImpl(rows,cols);
                ViewSwitcher.getInstance().switchView(ViewEnum.BUILDER,this.level);
            }
        }catch(NumberFormatException e){}
    }

    public void setLevel(LevelBuilder level){this.level = level;}

    public LevelBuilder getLevel(){return this.level;}

}
