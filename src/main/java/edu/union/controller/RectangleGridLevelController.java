package edu.union.controller;

import edu.union.service.LevelRepositoryManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import edu.union.model.*;
import edu.union.view.ViewEnum;

public class RectangleGridLevelController {

    private RectangleGridLevel level;

    /**
     * Constructor for the RectangleGridLevelController.
     */
    public RectangleGridLevelController() {

    }

    /**
     * Constructor for the RectangleGridLevelController.
     * @param level: The level to be played.
     */
    public RectangleGridLevelController(RectangleGridLevel level){
        setLevel(level);
    }

    /**
     * Setter for the level data field.
     * @param level: A RectangleGridLevel that we should control.
     */
    public void setLevel(RectangleGridLevel level){
        this.level = level;
    }

    /**
     * Handler for changing the color on the Kami board.
     * @param actionEvent: ActionEvent from the button on the board that was clicked.
     */
    public void handleColorGridBtn(ActionEvent actionEvent) {
        try {
            Button targetButton = (Button) actionEvent.getTarget();
            int row = GridPane.getRowIndex(targetButton);
            int col = GridPane.getColumnIndex(targetButton);
            level.getCareTaker().recordOriginator();
            level.play(new Move<>(level.getCurrentColor(), new RectangleGridCell(row, col)));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    /**
     * Handler for changing the color (not on the Kami board).
     * @param actionEvent: ActonEvent from the button on the color palette that was clicked.
     */
    public void handleChooseColorBtn(ActionEvent actionEvent) {
        try {
            Button targetButton = (Button) actionEvent.getTarget();
            Color color = (Color) targetButton.getUserData();
            level.switchColor(color);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.toString());
            alert.show();
        }
    }

    /**
     * Handler for the restart button. Resets the Kami board to its starting state.
     */
    public void handleRestartBtn() {
        Level level = LevelRepositoryManager.getInstance().loadLevel(this.level.getLevelInfo());
        ViewSwitcher.getInstance().switchView(ViewEnum.LEVEL, level);
    }

    /**
     * Handler for the exit button. Changes the view to the menu.
     */
    public void handleMoveToMenuBtn() {
        ViewSwitcher.getInstance().switchView(ViewEnum.MENU);
    }

    /**
     * Handler for the undo button. Changes the view to the level state before last move.
     */
    public void handleUndoButton(){
        if(level.getCareTaker().undoable()){
            level.getCareTaker().undo();
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "No previous moves!");
            alert.show();
        }
    }
}
