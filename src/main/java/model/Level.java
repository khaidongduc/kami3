package model;

import utils.Observable;

import java.util.List;

public abstract class Level extends Observable {

    public Level() {
        super();
    }

    public abstract void importLevel(int levelId);
    public abstract int getLevelId();
    public abstract int getNumRows();
    public abstract int getNumCols();
    public abstract Color getColorAt(int row, int col);

    public abstract int numMoveRemaining();
    public abstract void switchColor(Color color);
    public abstract Color getCurrentColor();
    public abstract void play(Move move);
    public abstract void restart();
    public abstract List<Color> getColors();
    public abstract List<Move> getHints();
    public abstract LevelState getLevelState();

}
