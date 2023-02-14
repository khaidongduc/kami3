package edu.union.service;

import edu.union.model.Level;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelInfo;
import edu.union.utils.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * managing the levelRepository of different type of Level
 * singleton
 */
public class LevelRepositoryManager extends Observable {

    private static LevelRepositoryManager instance;
    private static final String DEFAULT_FOLDER_PATH = Level.class.getResource("levels").getPath();

    private final Map<String, LevelRepository> levelRepositoryMap;
    private String folderPath;

    /**
     * basic constructor
     */
    private LevelRepositoryManager(){
        folderPath = DEFAULT_FOLDER_PATH;
        levelRepositoryMap = new HashMap<>();
    }

    /**
     * return a singleton instance of this type
     * @return the instance
     */
    public static LevelRepositoryManager getInstance(){
        if(instance == null){
            instance = new LevelRepositoryManager();
        }
        return instance;
    }

    /**
     * set the folderPath where the levels will be saved
     * @param folderPath the folderPath
     */
    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    /**
     * register a repository under a levelType
     * @param levelType the levelType
     * @param levelRepository the levelRepository
     */
    public void register(String levelType, LevelRepository levelRepository){
        levelRepositoryMap.put(levelType, levelRepository);
    }

    /**
     * load a level using the levelInfo
     * @param levelInfo the levelInfo
     * @return the level
     */
    public Level loadLevel(LevelInfo levelInfo){
        String levelType = levelInfo.getLevelType();
        if(!levelRepositoryMap.containsKey(levelType))
            throw new RuntimeException("no repo existed for this info");
        return levelRepositoryMap.get(levelType).loadLevel(levelInfo);
    }

    public void saveLevel(LevelBuilder levelBuilder){
        levelRepositoryMap.get(levelBuilder.getLevelType()).saveLevel(levelBuilder, folderPath);
        notifyObservers();
    }

    /**
     * list the available levelInfos
     * @return the List of levelInfo
     */
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
