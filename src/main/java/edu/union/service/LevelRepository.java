package edu.union.service;

import edu.union.model.*;

public abstract class LevelRepository {

    protected LevelRepositoryStrategy strategy;

    public abstract Level loadLevel(LevelInfo levelInfo);
    public abstract void saveLevel(LevelBuilder levelBuilder, String folderPath);

    public void setLevelRepositoryStrategy(LevelRepositoryStrategy strategy){
        this.strategy = strategy;
    }

}
