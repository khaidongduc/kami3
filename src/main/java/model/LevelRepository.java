package model;

import utils.Observable;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * a level repository that manages all Levels (list and create)
 * Singleton Class
 *
 * @author Khai Dong
 */
public class LevelRepository extends Observable {
    private static LevelRepository instance;

    /**
     * basic initialization
     */
    private LevelRepository(){

    }

    /**
     * get an instance or create an instance if it does not exist
     * @return the instance
     */
    public static LevelRepository getInstance(){
        if(instance == null)
            instance = new LevelRepository();
        return instance;
    }


    public LevelImpl loadLevel(int levelId){
        try {
            String relPath = String.format("levels/%s", levelId);
            File file = new File(getClass().getResource(relPath).getPath());
            Scanner scanner = new Scanner(file);
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            ColorGrid grid = new ColorGrid(numRows, numCols);
            for (int i = 0; i < numRows; ++i){
                for (int j = 0; j < numCols; ++j) {
                    int colorId = scanner.nextInt();
                    if(grid.getColorOfEntry(i, j) != colorId) grid.setColor(colorId, i, j);
                }
            }
            int maxNumTurn = scanner.nextInt();
            Color curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
            return new LevelImpl(grid, curColor, maxNumTurn, levelId);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * assign an id and save the level into hard drive
     * @param level the level
     */
    public void saveLevel(Level level){
        // TODO: LevelBuilder
        notifyObservers();
    }

    /**
     * convert the levelBuilder to a level,
     * assign an id and save the level into hard drive
     * @param levelBuilder the levelBuilder
     */
    public void saveLevel(LevelBuilder levelBuilder){
        // TODO: LevelBuilder
        notifyObservers();
    }

    /**
     * list all level info
     * @return the List of all LevelInfo
     */
    public List<LevelInfo> listLevelInfo(){
        File folder = new File(getClass().getResource("levels").getPath());
        LinkedList<LevelInfo> levelInfos = new LinkedList<>();
        for(File file : folder.listFiles()){
            levelInfos.add(new LevelInfo(Integer.parseInt(file.getName())));
        }
        return levelInfos;
    }

}
