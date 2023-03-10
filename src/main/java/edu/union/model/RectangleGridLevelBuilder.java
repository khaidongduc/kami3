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

    /**
     * change the grid size of the RectangleGridLevelBuilder
     * @param rows new # of rows
     * @param cols new # of cols
     */
    public void changeGridSize(int rows, int cols){
        this.graph = new ColoredGraph<RectangleGridCell>();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                graph.addVertex(new RectangleGridCell(i,j),0);
            }
        }
        graph.buildGraphWithAdjacency();
        this.numRows = rows;
        this.numCols = cols;
        notifyObservers();
    }

    /**
     * the level type of this levelBuilder
     * @return the level type
     */
    @Override
    public String getLevelType() {
        return LevelType.RECTANGLE_GRID_LEVEL;
    }

    /**
     * return the number of rows
     * @return the # of rows
     */
    public int getRows(){
        return this.numRows;
    }

    /**
     * return the number of rows
     * @return the # of rows
     */
    public int getCols(){
        return this.numCols;
    }

    public Object clone(){
        RectangleGridLevelBuilder cloned = new RectangleGridLevelBuilder(this.numRows, this.numCols);
        cloned.graph = new ColoredGraph<>(this.graph);
        cloned.curColor = this.curColor;
        return cloned;
    }
}
