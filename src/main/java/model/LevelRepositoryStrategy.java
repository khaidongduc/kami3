package model;

import java.util.List;

public interface LevelRepositoryStrategy {
    Level loadLevel(int levelId);
    void saveLevel(LevelBuilder levelBuilder);
    List<LevelInfo> listLevelInfo();
}
