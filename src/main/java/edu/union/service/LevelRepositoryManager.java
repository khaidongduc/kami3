package edu.union.service;

import edu.union.Config;
import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;
import edu.union.utils.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LevelRepositoryManager extends Observable {

    private static LevelRepositoryManager instance;
    private static final String DEFAULT_FOLDER_PATH = Level.class.getResource("levels").getPath();

    private final Map<String, LevelRepository> levelRepositoryMap;
    private String folderPath;

    private LevelRepositoryManager(){
        folderPath = DEFAULT_FOLDER_PATH;
        levelRepositoryMap = new HashMap<>();
    }

    public static LevelRepositoryManager getInstance(){
        if(instance == null){
            instance = new LevelRepositoryManager();
        }
        return instance;
    }

    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    public void register(String levelType, LevelRepository levelRepository){
        levelRepositoryMap.put(levelType, levelRepository);
    }

    public Level loadLevel(LevelInfo levelInfo){
        return levelRepositoryMap.get(levelInfo.getLevelType()).loadLevel(levelInfo);
    }

    public void saveLevel(LevelBuilder levelBuilder){
        levelRepositoryMap.get(levelBuilder.getLevelType()).saveLevel(levelBuilder, folderPath);
        notifyObservers();
    }

    public List<LevelInfo> listLevelInfos(){
        try {
            File folder = new File(folderPath);
            List<LevelInfo> res = new LinkedList<>();
            for (File file : folder.listFiles()) {
                Scanner scanner = new Scanner(file);
                res.add(
                    new LevelInfo(Integer.parseInt(file.getName()),
                        scanner.next(),
                        file.getPath())
                );
            }
            res.sort(Comparator.comparingInt(LevelInfo::getLevelId));
            return res;
        }catch (FileNotFoundException ignored){

        }
        return null;
    }

}
