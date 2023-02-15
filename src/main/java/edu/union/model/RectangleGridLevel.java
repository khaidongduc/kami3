package edu.union.model;

import java.util.List;

public class RectangleGridLevel extends Level<RectangleGridCell> {

    private int numRows;
    private int numCols;

    /**
     * constructor
     * ensure to initialize Observable
     *
     * @param graph the graph of the level
     * @param hints the hints to solve the level
     * @param levelInfo the levelInfo
     */
    public RectangleGridLevel(
            ColoredGraph<RectangleGridCell> graph,
            List<Move<RectangleGridCell>> hints,
            LevelInfo levelInfo) {
        super(graph, hints, levelInfo);

        numRows = numCols = 0;
        for(RectangleGridCell cell : graph.getVertexSet()){
            numRows = Math.max(numRows, cell.row);
            numCols = Math.max(numCols, cell.col);
        }
        numRows += 1;
        numCols += 1;
    }

    /**
     * return the levelType
     * @return the levelType
     */
    @Override
    public String getLevelType() {
        return LevelType.RECTANGLE_GRID_LEVEL;
    }

    /**
     * @return number of rows in the rectangleGridLevel
     */
    public int getNumRows(){
        return this.numRows;
    }

    /**
     *
     * @return number of cols in the rectangleGridLevel
     */
    public int getNumCols(){
        return this.numCols;
    }

}
