package edu.union.service;

import edu.union.model.LevelBuilder;
import edu.union.model.RectangleGridLevelBuilder;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * general LevelBuilderFactory
 * abstract the creation of LevelBuilder objects
 * allow registering LevelBuilder prototype and clone that prototype everytime.
 * is a singleton
 */
public class LevelBuilderFactory {

    private static LevelBuilderFactory instance;
    private AbstractMap<String, LevelBuilder> map;
    public LevelBuilderFactory(){
        map = new HashMap<>();
    }

    /**
     * get an instance of a levelBuilderFactory
     * @return the instance
     */
    public static LevelBuilderFactory getInstance(){
        if(instance == null){
            instance = new LevelBuilderFactory();
        }
        return instance;
    }

    /**
     * create a level builder from the levelType
     * @param levelType the levelType
     * @return a levelBuilder of the right type
     * @throws Exception if no prototype has been registered under this type
     */
    public LevelBuilder createLevelBuilder(String levelType) {
        if(map.containsKey(levelType)){
            return (LevelBuilder) map.get(levelType).clone();
        }
        throw new RuntimeException("We cannot find a LevelBuilder for the given key");
    }

    /**
     * register a prototype of a levelBuilder under a levelType
     * @param levelType the levelType
     * @param levelBuilder the levelBuilder prototype
     */
    public void register(String levelType, LevelBuilder levelBuilder){
        map.put(levelType,levelBuilder);
    }

}
