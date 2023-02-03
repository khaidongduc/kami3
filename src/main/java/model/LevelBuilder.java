package model;

import utils.Observable;

import java.util.List;

/**
 * an abstract class for levelBuilder
 * containing the required function to building a level
 *
 * @author Khai Dong
 */
public abstract class LevelBuilder extends Observable {

    /**
     * basic initialization calling Observable constructor
     */
    public LevelBuilder(){
        super();
    }

    /**
     * get the number of rows in the color grid associated with a levelBuilder
     * @return the number of rows in the color grid associated with a levelBuilder
     */
    public abstract int getNumRows();

    /**
     * get the number of columns in the color grid associated with a levelBuilder
     * @return the number of columns in the color grid associated with a levelBuilder
     */
    public abstract int getNumCols();

    /**
     * get the color at a specified cell
     * @param row the row
     * @param col the column
     * @return the current color of that cell
     */
    public abstract Color getColorAt(int row, int col);

    /**
     * get the color palette for creating level
     * listing all possible colors in color repository
     * @return List of colors
     */
    public abstract List<Color> getColors();
    /**
     * switch to a different color within the color palette of the grid
     * get the color palette by calling getColors()
     * @param color the new color
     * @throws IllegalArgumentException if the color does not exist within the palette, or it is the same color as the previous one
     */
    public abstract void switchColor(Color color);

    /**
     * get the current color that the level builder is using
     * @return the current color
     */
    public abstract Color getCurrentColor();

    /**
     * set a grid cell color to a new color
     * @param color the new color
     * @param row the row
     * @param col the column
     * @throws IllegalArgumentException if color does not exist in color palette
     * @throws IllegalArgumentException if index is our of bound
     */
    public abstract void setColor(Color color, int row, int col);

    /**
     * set a grid cell color to a new color, and set the surrounding cells
     * with the same color to that new color
     * @param color the new color
     * @param row the row
     * @param col the column
     * @throws IllegalArgumentException if color does not exist in color palette
     * @throws IllegalArgumentException if index is our of bound
     */
    public abstract void setColorFlood(Color color, int row, int col);

    /**
     * change the grid size of the level
     * will restart the whole grid
     * @param numRows the new number of rows
     * @param numCols the new number of columns
     */
    public abstract void changeGridSize(int numRows, int numCols);

    /**
     * restart the level creation from scratch
     */
    public abstract void restart();

    /**
     * Get the minimum number of moves needed to solve a built board.
     * @return: The integer value of the minimum number of moves needed to solve the Kami Board.
     */
    public abstract int getMinNumMoves();
}
