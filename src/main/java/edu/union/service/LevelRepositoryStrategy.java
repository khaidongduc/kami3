package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

import java.io.File;

public interface LevelRepositoryStrategy {
    Level loadFromFile(LevelInfo levelInfo);
    void saveToFile(LevelBuilder levelBuilder);
}
