package edu.union.model;

/**
 * edu.union.model containing minimal info on a Level
 * to be displayed in bulk
 *
 * @author Khai Dong
 */
public class LevelInfo {
    private final int levelId;

    /**
     * initialized with levelId
     * @param levelId the levelId
     */
    public LevelInfo(int levelId){
        this.levelId = levelId;
    }

    /**
     * get the levelId
     * @return the leveId
     */
    public int getLevelId(){
        return levelId;
    }

}
