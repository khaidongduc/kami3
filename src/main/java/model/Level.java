package model;

import utils.Observable;

import java.util.List;

public interface Level extends Observable {
    int getLevelId();
    int getNumRows();
    int getNumCols();
    Color getColorAt(int row, int col);

    int numMoveRemaining();
    void switchColor(Color color);
    Color getCurrentColor();
    void play(Move move);
    void restart();
    List<Color> getColors();
    List<Move> getHints();
    LevelState getLevelState();

}
