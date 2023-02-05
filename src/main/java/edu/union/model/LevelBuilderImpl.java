package edu.union.model;

import edu.union.service.LevelSolver;

import java.util.List;


public class LevelBuilderImpl extends LevelBuilder{
    private static final int DEFAULT_ROWS = 5;
    private static final int DEFAULT_COLS = 5;

    private int numRows, numCols;
    private ColorGrid grid;
    private Color curColor;

    private int maxNumTurn;

    public LevelBuilderImpl() {
        super();
        this.numRows = DEFAULT_ROWS;
        this.numCols = DEFAULT_COLS;
        restart();

    }

    public LevelBuilderImpl(int rows, int cols){
        super();
        this.numRows = rows;
        this.numCols = cols;
        restart();
    }

    @Override
    public int getNumRows() {return numRows;}

    @Override
    public int getNumCols() {return numCols;}

    @Override
    public Color getColorAt(int row, int col) {
        int colorId = grid.getColorOfEntry(row,col);
        return ColorRepository.getInstance().getColor(colorId);
    }

    @Override
    public List<Color> getColors() {
        return ColorRepository.getInstance().listColors(grid.getAvailableColorIds());
    }


    @Override
    public void switchColor(Color color) {
        this.curColor = color;
        notifyObservers();
    }

    @Override
    public Color getCurrentColor() {
        return this.curColor;
    }

    @Override
    public void setColor(Color color, int row, int col) {
        try {
            grid.setColor(color.getColorId(), row, col);
            notifyObservers();
        }catch(IllegalArgumentException e){}
    }

    //Unused in building levels, may be depricated.
    @Override
    public void setColorFlood(Color color, int row, int col) {}

    @Override
    public void changeGridSize(int numRows, int numCols) {
        grid = new ColorGrid(numRows, numCols);
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public void restart() {
        this.grid = new ColorGrid(this.numRows, this.numCols);
        this.curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
        notifyObservers();
    }

    @Override
    public int getMinNumMoves() {
        return LevelSolver.getInstance().solveColorGrid(this.grid).size();
    }
}