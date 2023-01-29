package model;

import utils.Observable;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * get a Level by levelId
     * @param levelId the levelId
     * @return the Level generated
     */
    public Level getLevel(int levelId){
        return new LevelImpl(levelId);
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
