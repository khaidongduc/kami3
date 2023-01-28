package model;

import utils.Observable;

import java.util.List;

public abstract class LevelBuilder extends Observable {

    public LevelBuilder(){
        super();
    }

    public abstract int getNumRows();
    public abstract int getNumCols();
    public abstract Color getColorAt(int row, int col);

    public abstract List<Color> getColors();
    public abstract void switchColor(Color color);
    public abstract Color getCurrentColor();
    public abstract void setColor(Color color, int row, int col);
    public abstract void setColorFlood(Color color, int row, int col);
    public abstract void changeGridSize(int numRows, int numCols);

    public abstract void restart();

}
