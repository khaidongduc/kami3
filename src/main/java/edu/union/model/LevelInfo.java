package edu.union.model;

/**
 * edu.union.model containing minimal info on a Level
 * to be displayed in bulk
 *
 * @author Khai Dong
 */
public class LevelInfo {
    private final int levelId;
    private final String levelType;
    private final String filePath;


    /**
     * initialized with levelId
     * @param levelId the levelId
     */
    public LevelInfo(int levelId, String levelType, String filePath){
        this.levelId = levelId;
        this.levelType = levelType;
        this.filePath = filePath;
    }

    /**
     * get the levelId
     * @return the leveId
     */
    public int getLevelId(){
        return levelId;
    }

    /**
     * get the level type
     * @return the leve type
     */
    public String getLevelType(){
        return levelType;
    }

    /**
     * get the file path
     * @return the file path
     */
    public String getFilePath(){
        return filePath;
    }


    public String toString(){
        return String.valueOf(levelId);
    }
}
