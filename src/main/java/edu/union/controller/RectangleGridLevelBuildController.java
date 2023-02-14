package edu.union.controller;

import edu.union.model.*;
import edu.union.service.LevelBuilderFactory;
import edu.union.service.LevelRepositoryManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import edu.union.view.ViewEnum;

public class RectangleGridLevelBuildController {

    private RectangleGridLevelBuilder levelBuilder;
    public RectangleGridLevelBuildController(){
        try {
            this.levelBuilder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RectangleGridLevelBuildController(RectangleGridLevelBuilder level){
        this.levelBuilder = level;
    }

    public void handleChooseColorBtn(ActionEvent action){
        try{
            Button targetButton = (Button) action.getTarget();
            Color color = (Color) targetButton.getUserData();
            levelBuilder.switchColor(color);
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
            levelBuilder.setColor(levelBuilder.getCurrentColor(), new RectangleGridCell(row,col));
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }
    public void handleRestartBtn(ActionEvent e){
        levelBuilder.restart();}
    public void handleExitBtn(ActionEvent e){ViewSwitcher.getInstance().switchView(ViewEnum.MENU);}

    //Save functionality is not implemented.
    public void handleSaveBtn(ActionEvent e){
        LevelRepositoryManager.getInstance().saveLevel(this.levelBuilder);
        levelBuilder.restart();
        ViewSwitcher.getInstance().switchView(ViewEnum.MENU);
    }

    public void handleResizeBtn(String rowInput, String colInput){
        try{
            int rows = Integer.parseInt(rowInput);
            int cols = Integer.parseInt(colInput);
            if(0 < rows && 0 < cols){
                this.levelBuilder.changeGridSize(rows,cols);
                ViewSwitcher.getInstance().switchView(ViewEnum.BUILDER,this.levelBuilder);
            }

        }
        catch(NumberFormatException ignored){

        }
    }

    public void setLevelBuilder(RectangleGridLevelBuilder levelBuilder){
        this.levelBuilder = levelBuilder;
    }

    public RectangleGridLevelBuilder getLevelBuilder(){
        return this.levelBuilder;
    }

}
