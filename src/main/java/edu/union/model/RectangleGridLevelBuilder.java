package edu.union.model;

import java.awt.*;

public class RectangleGridLevelBuilder extends LevelBuilder<RectangleGridCell>{

    private int numRows;
    private int numCols;

    public final String TYPE = "Rectangle";
    /**
     * basic initialization calling Observable constructor
     *
     * @param graph A
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

}
