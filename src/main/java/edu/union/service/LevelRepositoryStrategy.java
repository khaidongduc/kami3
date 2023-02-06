package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

import java.util.List;

/**
 * an interface for a strategy in saving and loading level
 */
public interface LevelRepositoryStrategy {
    /**
     * load level from a levelInfo
     * @param levelInfo the levelInfo
     * @return the corresponding level
     */
    Level loadLevel(LevelInfo levelInfo);

    /**
     * save a level into hard drive using information from an existing levelBuilder
     * @param levelBuilder the levelBuilder
     */
    void saveLevel(LevelBuilder levelBuilder);

    /**
     * list all available levelInfos
     * @return levelInfo
     */
    List<LevelInfo> listLevelInfo();
}
