package model;

import java.util.Set;
import java.util.List;

public interface Game {
    int getNumRows();
    int getNumCols();
    Color getColorAt(int row, int col);

    int numMoveRemaining();
    void switchColor(Color color);
    Color getCurrentColor();
    void play(Move move);
    void restart();
    Set<Color> getColorSet();
    List<Move> getHints();

}
