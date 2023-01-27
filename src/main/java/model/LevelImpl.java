package model;

import java.util.List;
import java.util.Set;

public class LevelImpl implements Level {

    private int levelId;
    private ColorGrid grid;
    private int curNumTurn;
    private int maxNumTurn;
    private Color curColor;


    public LevelImpl(String filename){
        grid = new ColorGrid(3, 3);
        //grid.setColor(0, 0, 0);
        grid.setColor(1, 1, 1);
        grid.setColorFlood(2, 1, 1);
        this.curNumTurn = 0;
    }

    @Override
    public int getLevelId(){
        return levelId;
    }

    @Override
    public int getNumRows() {
        return grid.getNumRows();
    }

    @Override
    public int getNumCols() {
        return grid.getNumCols();
    }

    @Override
    public Color getColorAt(int row, int col) {
        int colorId = grid.getColorOfEntry(row, col);
        return ColorRepository.getInstance().getColor(colorId);
    }

    @Override
    public int numMoveRemaining() {
        return maxNumTurn - curNumTurn;
    }

    @Override
    public void switchColor(Color color) {
        this.curColor = color;
    }

    @Override
    public Color getCurrentColor() {
        return this.curColor;
    }

    @Override
    public void play(Move move) {
        grid.setColorFlood(move.getColor().getColorId(), move.getRow(), move.getCol());
        ++ curNumTurn;
    }

    @Override
    public void restart() {
        this.curNumTurn = 0;
    }

    @Override
    public Set<Color> getColorSet() {
        return ColorRepository.getInstance().listColors(grid.getAvailableColorIds());
    }

    @Override
    public List<Move> getHints() {
        return null;
    }
}
