package edu.union.utils;

import edu.union.model.ColoredGraph.ColoredVertex;
import edu.union.model.LevelHint;
import edu.union.model.Move;

public class PlayMoveCommand <V extends ColoredVertex> implements Command {
    private LevelHint level;
    private Move<V> move;

    public PlayMoveCommand(LevelHint level, Move<V> move){
        this.move = move;
        this.level = level;
    }
    @Override
    public void execute() {
        level.play(move);
    }

    public Move<V> getMove(){
        return this.move;
    }
}
