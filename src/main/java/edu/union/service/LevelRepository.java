package edu.union.service;

import edu.union.model.*;

/**
 * abstract class for levelRepository
 */
public abstract class LevelRepository {

    protected LevelRepositoryStrategy strategy;

    /**
     * load level from a levelInfo
     * will fail if the levelInfo of a wrong type is passed
     * @param levelInfo the levelInfo
     * @return the Level associated with levelInfo
     */
    public abstract Level loadLevel(LevelInfo levelInfo);

    /**
     * save a level created by a levelBuilder to a specific folder
     * will fail if the levelBuilder of a wrong type is passed
     * @param levelBuilder the levelBuilder
     * @param folderPath the folder path
     */
    public abstract void saveLevel(LevelBuilder levelBuilder, String folderPath);

    /**
     * set the strategy for this LevelRepository
     * @param strategy the strategy
     */
    public void setLevelRepositoryStrategy(LevelRepositoryStrategy strategy){
        this.strategy = strategy;
    }

}
