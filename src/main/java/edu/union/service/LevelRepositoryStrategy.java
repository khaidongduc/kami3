package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

/**
 * the strategy to deal with fileIO for levelRepository
 */
public interface LevelRepositoryStrategy {
    /**
     * load the levelInfo from a file
     * will fail if the levelInfo is of the right type
     * @param levelInfo the levelInfo
     * @return a level
     */
    Level loadFromFile(LevelInfo levelInfo);

    /**
     * save a level from a levelBuilder to a file in a specific folder
     * @param levelBuilder the levelBuilder
     * @param folderPath the folder path
     */
    void saveToFile(LevelBuilder levelBuilder, String folderPath);
}
