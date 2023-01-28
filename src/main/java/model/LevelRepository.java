package model;

import utils.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LevelRepository extends Observable {
    private static LevelRepository instance;


    private LevelRepository(){

    }

    public static LevelRepository getInstance(){
        if(instance == null)
            instance = new LevelRepository();
        return instance;
    }

    public Level getLevel(int gameId){
        return null;
    }

    public void saveLevel(Level level){
        // TODO: LevelBuilder
        notifyObservers();
    }

    public List<LevelInfo> listLevelInfo(){
        File folder = new File(getClass().getResource("levels").getPath());
        LinkedList<LevelInfo> levelInfos = new LinkedList<>();
        for(File file : folder.listFiles()){
            levelInfos.add(new LevelInfo(Integer.parseInt(file.getName())));
        }
        return levelInfos;
    }

}
