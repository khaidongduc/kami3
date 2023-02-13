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

    /**
     * initialized with levelId
     * @param levelId the levelId
     */
    public LevelInfo(int levelId, String levelType){
        this.levelId = levelId;
        this.levelType = levelType;
    }

    /**
     * get the levelId
     * @return the leveId
     */
    public int getLevelId(){
        return levelId;
    }

    /**
     * get the levelId
     * @return the leveId
     */
    public String getLevelType(){
        return levelType;
    }

    public String toString(){
        return String.valueOf(levelId);
    }
}
