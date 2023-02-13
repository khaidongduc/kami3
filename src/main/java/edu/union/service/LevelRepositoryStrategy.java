package edu.union.service;

import java.io.File;
import java.util.logging.Level;

public interface LevelRepositoryStrategy {
    Level loadFromFile(File file);
    void saveToFile(Level level);
}
