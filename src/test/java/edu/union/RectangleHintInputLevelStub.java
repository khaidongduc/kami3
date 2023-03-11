package edu.union;

import edu.union.model.ColoredGraph;
import edu.union.model.Move;
import edu.union.model.RectangleGridCell;
import edu.union.model.RectangleHintInputLevel;

import java.util.LinkedList;
import java.util.List;

public class RectangleHintInputLevelStub extends RectangleHintInputLevel {

    private List<Move<RectangleGridCell>> moveList;

    public RectangleHintInputLevelStub(ColoredGraph<RectangleGridCell> graph, int numRows, int numCols) {
        super(graph, numRows, numCols);
        this.moveList = new LinkedList<>();
    }
    public RectangleHintInputLevelStub(){
        super(new ColoredGraph<RectangleGridCell>(),0,0);
        this.moveList = new LinkedList<>();
    }

    @Override
    public void play(Move<RectangleGridCell> move) {
        this.moveList.add(move);
    }

    public List<Move<RectangleGridCell>> getMoves(){
        return this.moveList;
    }
}