package model;

import java.util.Set;

public interface LevelBuilder {
    int getNumRows();
    int getNumCols();
    Color getColorAt(int row, int col);

    Set<Color> getColorSet();
    void switchColor(Color color);
    Color getCurrentColor();
    void setColor(Color color, int row, int col);
    void setColorFlood(Color color, int row, int col);
    void changeGridSize(int numRows, int numCols);


    void restart();

}
