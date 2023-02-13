package edu.union.service;

import edu.union.model.*;
import edu.union.utils.Observable;

import java.util.*;

/**
 * a level repository that manages all Levels (list and create)
 * Singleton Class
 *
 * @author Khai Dong
 */
public class _LevelRepository extends Observable {
    private static _LevelRepository instance;

    private _LevelRepositoryStrategy levelRepositoryStrategy;

    /**
     * basic initialization
     */
    private _LevelRepository(){

    }

    public void setLevelRepositoryStrategy(_LevelRepositoryStrategy levelRepositoryStrategy) {
        this.levelRepositoryStrategy = levelRepositoryStrategy;
    }

    /**
     * get an instance or create an instance if it does not exist
     * @return the instance
     */
    public static _LevelRepository getInstance(){
        if(instance == null)
            instance = new _LevelRepository();
        return instance;
    }


    /**
     * load a level using a LevelInfo
     * @param levelInfo the levelInfo
     * @return the corresponding Level object
     */
    public Level loadLevel(LevelInfo levelInfo){
        return levelRepositoryStrategy.loadLevel(levelInfo);
    }

    /**
     * convert the levelBuilder to a level,
     * assign an id and save the level into hard drive
     * @param levelBuilder the levelBuilder
     */
    public void saveLevel(LevelBuilder levelBuilder){
        levelRepositoryStrategy.saveLevel(levelBuilder);
        notifyObservers();
    }

    /**
     * list all level info
     * @return the List of all LevelInfo
     */
    public List<LevelInfo> listLevelInfo(){
        List<LevelInfo> levelInfos = levelRepositoryStrategy.listLevelInfo();
        return levelInfos;
    }

}
