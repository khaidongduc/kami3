package model;

import utils.Observer;

import java.util.*;

public class LevelImpl implements Level {

    private int levelId;
    private ColorGrid grid;
    private int curNumTurn;
    private int maxNumTurn;
    private Color curColor;

    private final Set<Observer> observers;

    public LevelImpl(String filename){
        grid = new ColorGrid(3, 3);
        //grid.setColor(0, 0, 0);
        grid.setColor(1, 1, 1);
        grid.setColorFlood(2, 1, 1);
        this.curNumTurn = 0;
        this.maxNumTurn = 2;
        this.curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
        this.observers = new HashSet<Observer>();
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
        notifyObservers();
    }

    @Override
    public Color getCurrentColor() {
        return this.curColor;
    }

    @Override
    public void play(Move move) {
        if (numMoveRemaining() == 0){
            throw new IllegalArgumentException("No move left");
        }
        grid.setColorFlood(move.getColor().getColorId(), move.getRow(), move.getCol());
        ++curNumTurn;
        notifyObservers();
    }

    @Override
    public void restart() {
        this.curNumTurn = 0;
        notifyObservers();
    }

    @Override
    public List<Color> getColors() {
        return ColorRepository.getInstance().listColors(grid.getAvailableColorIds());
    }

    @Override
    public List<Move> getHints() {
        return null;
    }

    @Override
    public LevelState getLevelState() {
        int colorId = grid.getColorOfEntry(0, 0);
        boolean isMono = true;
        for(int i = 0 ; i < grid.getNumRows() && isMono ; ++ i)
            for(int j = 0 ; j < grid.getNumCols() && isMono; ++ j){
                if(grid.getColorOfEntry(i, j) != colorId) isMono = false;
            }
        if(!isMono){
            if(numMoveRemaining() > 0) {
                return LevelState.ONGOING;
            }
            return LevelState.LOSE;
        }
        return LevelState.WIN;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }

}
