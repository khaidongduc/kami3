package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

/**
 * a level repository that manages all Levels (list and create)
 * Singleton Class
 *
 * @author Khai Dong
 */
public class RectangleGridLevelRepository extends LevelRepository {
    private static RectangleGridLevelRepository instance;

    /**
     * get an instance or create an instance if it does not exist
     * @return the instance
     */
    public static RectangleGridLevelRepository getInstance(){
        if(instance == null)
            instance = new RectangleGridLevelRepository();
        return instance;
    }

    /**
     * basic initialization
     */
    private RectangleGridLevelRepository(){
        setLevelRepositoryStrategy(TextRectangleGridLevelRepositoryStrategy.getInstance());
    }

    /**
     * load a level using a LevelInfo
     * @param levelInfo the levelInfo
     * @return the corresponding Level object
     */
    public Level loadLevel(LevelInfo levelInfo){
        return strategy.loadFromFile(levelInfo);
    }

    /**
     * convert the levelBuilder to a level,
     * assign an id and save the level into hard drive
     * @param levelBuilder the levelBuilder
     */
    public void saveLevel(LevelBuilder levelBuilder, String folderPath){
        strategy.saveToFile(levelBuilder, folderPath);
    }

}
