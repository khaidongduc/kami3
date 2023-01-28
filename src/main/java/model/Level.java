package model;

import utils.Observable;

import java.util.List;

/**
 * an abstract class defining all methods used in playing a level
 * it extend Observable to allows View to subscribe to its change
 */
public abstract class Level extends Observable {

    /**
     * constructor
     * ensure to initialize Observable
     */
    public Level() {
        super();
    }

    /**
     * import a level by its id
     * @param levelId the levelId
     */
    public abstract void importLevel(int levelId);

    /**
     * get the levelId
     * @return the levelId
     */
    public abstract int getLevelId();

    /**
     * get the number of rows in the color grid associated with a level
     * @return the number of rows in the color grid associated with a level
     */
    public abstract int getNumRows();

    /**
     * get the number of columns in the color grid associated with a level
     * @return the number of columns in the color grid associated with a level
     */
    public abstract int getNumCols();

    /**
     * get the color at a specified cell
     * @param row the row
     * @param col the column
     * @return the current color of that cell
     * @throws IllegalArgumentException if the index is out of bound
     */
    public abstract Color getColorAt(int row, int col);

    /**
     * get the number of moves remaining before losing the game
     * @return the number of moves remaining before losing the game
     */
    public abstract int numMoveRemaining();

    /**
     * switch to a different color within the color palette of the grid
     * get the color palette by calling getColors()
     * @param color the new color
     * @throws IllegalArgumentException if the color does not exist within palette, or it is the same color as the previous one
     */
    public abstract void switchColor(Color color);

    /**
     * get the current color that the level is using
     * @return the current color
     */
    public abstract Color getCurrentColor();

    /**
     * play a move on the level
     * if the current color is different from the color in the move
     * switch the color to that color given that the move's color is in the grid
     * @param move the move on the level
     * @throws IllegalArgumentException if the color of the move is not in the grid
     * @throws IllegalArgumentException index out of bound
     */
    public abstract void play(Move move);

    /**
     * restart the level;
     */
    public abstract void restart();

    /**
     * get the color palette of this level
     * @return List of colors
     */
    public abstract List<Color> getColors();

    /**
     * return hints to solve this level
     * @return the hints
     */
    public abstract List<Move> getHints();

    /**
     * get the current level's state
     * either ONGOING, WIN, LOSE
     * @return the level's current state
     */
    public abstract LevelState getLevelState();

}
