package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;
import edu.union.utils.Observable;

import java.util.*;

public class LevelRepositoryManager extends Observable {

    private static LevelRepositoryManager instance;

    private final Map<String, LevelRepository> levelRepositoryMap;

    private LevelRepositoryManager(){
        levelRepositoryMap = new HashMap<>();
    }

    public static LevelRepositoryManager getInstance(){
        if(instance == null){
            instance = new LevelRepositoryManager();
        }
        return instance;
    }

    public void register(String levelType, LevelRepository levelRepository){
        levelRepositoryMap.put(levelType, levelRepository);
    }

    public Level loadLevel(LevelInfo levelInfo){
        return levelRepositoryMap.get(levelInfo.getLevelType()).loadLevel(levelInfo);
    }

    public void saveLevel(LevelBuilder levelBuilder){
        levelRepositoryMap.get(levelBuilder.getType()).saveLevel(levelBuilder);
        notifyObservers();
    }

    public List<LevelInfo> listLevelInfos(){
        List<LevelInfo> res = new ArrayList<>();
        res.add(new LevelInfo(1, "RectangleGridLevel",
                "C:\\Users\\khaid\\projs\\csc-260\\kami3\\src\\main\\resources\\edu\\union\\model\\levels\\text\\1"));
        return res;
    }

}
