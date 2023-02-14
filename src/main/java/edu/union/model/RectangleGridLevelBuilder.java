package edu.union.model;

import java.awt.*;

public class RectangleGridLevelBuilder extends LevelBuilder<RectangleGridCell> implements Cloneable{

    private int numRows;
    private int numCols;

    /**
     * basic initialization calling Observable constructor
     *
     * @param rows: The number of rows in the builder graph.
     * @param cols: The number of columns in the builder graph.
     */
    public RectangleGridLevelBuilder(int rows, int cols) {
        super(new ColoredGraph<RectangleGridCell>());
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                graph.addVertex(new RectangleGridCell(i,j),0);
            }
        }
        graph.buildGraphWithAdjacency();
        this.numRows = rows;
        this.numCols = cols;
    }

    public void changeGridSize(int rows, int cols){

    }

    @Override
    public String getLevelType() {
        return LevelType.RECTANGLE_GRID_LEVEL;
    }

    public int getRows(){
        return this.numRows;
    }

    public int getCols(){
        return this.numCols;
    }

    public Object clone(){
        return super.clone();
    }
}
