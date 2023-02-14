package edu.union.service;

import edu.union.model.LevelBuilder;
import edu.union.model.RectangleGridLevelBuilder;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LevelBuilderFactory {

    private static LevelBuilderFactory instance;
    private AbstractMap<String,LevelBuilder> map;
    public LevelBuilderFactory(){
        map = new HashMap<>();
    }

    public static LevelBuilderFactory getInstance(){
        if(instance == null){
            instance = new LevelBuilderFactory();
        }
        return instance;
    }

    public LevelBuilder createLevelBuilder(String levelType) throws Exception {
        if(map.containsKey(levelType)){
            return (LevelBuilder) map.get(levelType).clone();
        }
        throw new Exception("We cannot find a LevelBuilder for the given key");
    }

    public void register(String levelType, LevelBuilder levelBuilder){
        map.put(levelType,levelBuilder);
    }

}
