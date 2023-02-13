package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;

import java.util.List;

public class LevelRepositoryManager {

    private static LevelRepositoryManager instance;

    private LevelRepositoryManager(){

    }

    public static LevelRepositoryManager getInstance(){
        if(instance == null){
            instance = new LevelRepositoryManager();
        }
        return instance;
    }

    public void register(String levelType, LevelRepository levelRepository){

    }

    public Level loadLevel(LevelInfo levelInfo){
        return null;
    }

    public void saveLevel(LevelBuilder levelBuilder){

    }

    public List<LevelInfo> listLevelInfos(){
        return null;
    }

}
