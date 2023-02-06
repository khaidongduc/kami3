package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

import java.util.List;

/**
 * a strategy where level is saved in raw text format
 */
public class RawTextLevelRepositoryStrategy implements LevelRepositoryStrategy {

    public RawTextLevelRepositoryStrategy(){

    }


    /**
     * load level from a levelInfo
     *
     * @param levelInfo the levelInfo
     * @return the corresponding level
     */
    @Override
    public Level loadLevel(LevelInfo levelInfo) {
        return null;
    }

    /**
     * save a level into hard drive using information from an existing levelBuilder
     *
     * @param levelBuilder the levelBuilder
     */
    @Override
    public void saveLevel(LevelBuilder levelBuilder) {

    }

    /**
     * list all available levelInfos
     *
     * @return levelInfo
     */
    @Override
    public List<LevelInfo> listLevelInfo() {
        return null;
    }
}
