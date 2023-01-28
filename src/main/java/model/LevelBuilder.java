package model;

import java.util.List;

public interface LevelBuilder {
    int getNumRows();
    int getNumCols();
    Color getColorAt(int row, int col);

    List<Color> getColors();
    void switchColor(Color color);
    Color getCurrentColor();
    void setColor(Color color, int row, int col);
    void setColorFlood(Color color, int row, int col);
    void changeGridSize(int numRows, int numCols);


    void restart();

}
