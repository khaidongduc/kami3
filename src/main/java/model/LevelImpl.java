package model;

import com.sun.tools.jdeprscan.scan.Scan;
import utils.Observer;

import java.io.File;
import java.util.*;

public class LevelImpl implements Level {

    private int levelId;
    private ColorGrid grid;
    private int curNumTurn;
    private int maxNumTurn;
    private Color curColor;

    private final Set<Observer> observers;

    public LevelImpl(int levelId){
        this.observers = new HashSet<Observer>();

        importLevel(levelId);
    }

    @Override
    public void importLevel(int levelId){
        try {
            String relPath = String.format("levels/%s", String.valueOf(levelId));
            File file = new File(getClass().getResource(relPath).getPath());
            Scanner scanner = new Scanner(file);
            this.levelId = Integer.parseInt(file.getName());
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            grid = new ColorGrid(numRows, numCols);
            for (int i = 0; i < numRows; ++i){
                for (int j = 0; j < numCols; ++j) {
                    int colorId = scanner.nextInt();
                    if(grid.getColorOfEntry(i, j) != colorId) grid.setColor(colorId, i, j);
                }
            }
            this.maxNumTurn = scanner.nextInt();
            this.curNumTurn = 0;
            this.curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
            notifyObservers();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
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
        importLevel(getLevelId());
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
