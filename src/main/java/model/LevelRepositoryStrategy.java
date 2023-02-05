package model;

import java.util.List;

public interface LevelRepositoryStrategy {
    Level loadLevel(LevelInfo levelInfo);
    void saveLevel(LevelBuilder levelBuilder);
    List<LevelInfo> listLevelInfo();
}
