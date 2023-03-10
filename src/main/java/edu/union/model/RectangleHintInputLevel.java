package edu.union.model;

import edu.union.controller.RectangleHintInputController;

public class RectangleHintInputLevel extends LevelHint<RectangleGridCell>{

    private int numRows;
    private int numCols;

    public RectangleHintInputLevel(ColoredGraph<RectangleGridCell> graph, int numRows, int numCols){
        super(graph);
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int getRows(){
        return this.numRows;
    }

    public int getCols(){
        return this.numCols;
    }
    public String getLevelType() {
        return LevelType.RECTANGLE_GRID_LEVEL;
    }
}
