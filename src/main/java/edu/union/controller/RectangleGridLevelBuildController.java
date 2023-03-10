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

    /**
     * Constructor for a Controller for the Model & View for building Kami boards with rectangular cells.
     */
    public RectangleGridLevelBuildController(){
        try {
            this.levelBuilder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor for a Controller for the Model & View for building Kami boards with rectangular cells.
     * (Unused, may be deprecated)
     * @param level: A RectangleGridLevelBuilder object.
     */
    public RectangleGridLevelBuildController(RectangleGridLevelBuilder level){
        this.levelBuilder = level;
    }

    /**
     * Handler for changing the current color (not coloring the Kami board).
     * @param action: The ActionEvent from the button that was clicked.
     */
    public void handleChooseColorBtn(ActionEvent action){
        try{
            Button targetButton = (Button) action.getTarget();
            Color color = (Color) targetButton.getUserData();
            levelBuilder.switchColor(color);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    /**
     * Handler for changing the colors on the Kami board grid.
     * @param action: The ActionEvent from the button (on the Kami board) that was clicked.
     */
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

    /**
     * Handler for resetting the colors of the Kami Board.
     * @param e: An ActionEvent from the reset button.
     */
    public void handleRestartBtn(ActionEvent e){
        levelBuilder.restart();}

    /**
     * Handler for exiting the Kami board builder view. Changes the view to the menu.
     * @param e: An ActionEvent from the exit button.
     */
    public void handleExitBtn(ActionEvent e){ViewSwitcher.getInstance().switchView(ViewEnum.MENU);}


    /**
     * Handler for saving the Kami board. Changes the view to the menu.
     * @param e: An ActionEvent from the save button.
     */
    public void handleSaveBtn(ActionEvent e){
        try {
            LevelRepositoryManager.getInstance().saveLevel(this.levelBuilder);
            ViewSwitcher.getInstance().switchView(ViewEnum.MENU);
        }catch (RuntimeException error){
            RectangleHintInputLevel level = new RectangleHintInputLevel(new ColoredGraph<>(levelBuilder.getGraph()),levelBuilder.getRows(),levelBuilder.getCols());
            ViewSwitcher.getInstance().switchView(ViewEnum.HINT,level);
        }

    }

    /**
     * Handler for resizing the KamiBoard with the resize button. Board will only be resized if integers are passed.
     * @param rowInput: A String from the text fields passed in the call.
     * @param colInput: A String from the text fields passed in the call.
     */
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

    /**
     * Sets the data field for the level we are building.
     * @param levelBuilder: A RectangleGridLevelBuilder to store as the level we are controlling.
     */
    public void setLevelBuilder(RectangleGridLevelBuilder levelBuilder){
        this.levelBuilder = levelBuilder;
    }

    /**
     * Gets the level that was being built.
     * @return: The RectangleGridLevelBuilderObject.
     */
    public RectangleGridLevelBuilder getLevelBuilder(){
        return this.levelBuilder;
    }

}
