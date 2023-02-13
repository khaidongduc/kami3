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
    }

    public int getNumRows(){
        return this.numRows;
    }

    public int getNumCols(){
        return this.numCols;
    }

}
