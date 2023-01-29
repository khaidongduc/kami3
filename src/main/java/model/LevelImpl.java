package model;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * a concrete implementation of level where the cell on the grid is connected by adjacency on a rectangle grid
 * example: * is connected to the surroundings 1
 * |0|1|0|
 * |1|*|1|
 * |0|1|0|
 *
 * @author Khai Dong
 */
public class LevelImpl extends Level {

    private int levelId;
    private ColorGrid grid;
    private int curNumTurn;
    private int maxNumTurn;
    private Color curColor;

    /**
     * initialization by levelId
     * @param levelId the levelId
     */
    public LevelImpl(int levelId){
        super();
        importLevel(levelId);
    }

    /**
     * import a level by its id
     * @param levelId the levelId
     */
    @Override
    public void importLevel(int levelId){
        try {
            String relPath = String.format("levels/%s", levelId);
            File file = new File(getClass().getResource(relPath).getPath());
            Scanner scanner = new Scanner(file);
            this.levelId = Integer.parseInt(file.getName());
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            grid = new ColorGrid(numRows, numCols);
            for (int i = 0; i < numRows; ++i){
                for (int j = 0; j < numCols; ++j) {
                    int colorId = scanner.nextInt();
                    if(grid.getColorOfEntry(i, j) != colorId) grid.setColor(colorId, i, j);
                }
            }
            this.maxNumTurn = scanner.nextInt();
            this.curNumTurn = 0;
            this.curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
            notifyObservers();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * get the levelId
     * @return the levelId
     */
    @Override
    public int getLevelId(){
        return levelId;
    }

    /**
     * get the number of rows in the color grid associated with a level
     * @return the number of rows in the color grid associated with a level
     */
    @Override
    public int getNumRows() {
        return grid.getNumRows();
    }

    /**
     * get the number of columns in the color grid associated with a level
     * @return the number of columns in the color grid associated with a level
     */
    @Override
    public int getNumCols() {
        return grid.getNumCols();
    }

    /**
     * get the color at a specified cell
     * @param row the row
     * @param col the column
     * @return the current color of that cell
     * @throws IllegalArgumentException if the index is out of bound
     */
    @Override
    public Color getColorAt(int row, int col) {
        int colorId = grid.getColorOfEntry(row, col);
        return ColorRepository.getInstance().getColor(colorId);
    }

    /**
     * get the number of moves remaining before losing the game
     * @return the number of moves remaining before losing the game
     */
    @Override
    public int numMoveRemaining() {
        return maxNumTurn - curNumTurn;
    }

    /**
     * switch to a different color within the color palette of the grid
     * get the color palette by calling getColors()
     * @param color the new color
     * @throws IllegalArgumentException if the color does not exist within palette, or it is the same color as the previous one
     */
    @Override
    public void switchColor(Color color) {
        this.curColor = color;
        notifyObservers();
    }

    /**
     * get the current color that the level is using
     * @return the current color
     */
    @Override
    public Color getCurrentColor() {
        return this.curColor;
    }

    /**
     * play a move on the level
     * if the current color is different from the color in the move
     * switch the color to that color given that the move's color is in the grid
     * @param move the move on the level
     * @throws IllegalArgumentException if the color of the move is not in the grid
     * @throws IllegalArgumentException index out of bound
     */
    @Override
    public void play(Move move) {
        if (numMoveRemaining() == 0){
            throw new IllegalArgumentException("No move left");
        }
        grid.setColorFlood(move.getColor().getColorId(), move.getRow(), move.getCol());
        ++curNumTurn;
        notifyObservers();
    }

    /**
     * restart the level;
     */
    @Override
    public void restart() {
        importLevel(getLevelId());
    }

    /**
     * get the color palette of this level
     * @return List of colors
     */
    @Override
    public List<Color> getColors() {
        return ColorRepository.getInstance().listColors(grid.getAvailableColorIds());
    }

    /**
     * return hints to solve this level
     * @return the hints
     */
    @Override
    public List<Move> getHints() {
        //TODO: implement
        return null;
    }

    /**
     * get the current level's state
     * either ONGOING, WIN, LOSE
     * @return the level's current state
     */
    @Override
    public LevelState getLevelState() {
        int colorId = grid.getColorOfEntry(0, 0);
        boolean isMono = true;
        for(int i = 0 ; i < grid.getNumRows() && isMono ; ++ i)
            for(int j = 0 ; j < grid.getNumCols() && isMono; ++ j){
                if(grid.getColorOfEntry(i, j) != colorId) isMono = false;
            }
        if(!isMono){
            if(numMoveRemaining() > 0) {
                return LevelState.ONGOING;
            }
            return LevelState.LOSE;
        }
        return LevelState.WIN;
    }

}
