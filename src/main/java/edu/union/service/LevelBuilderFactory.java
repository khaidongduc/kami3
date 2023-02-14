package edu.union.service;

import edu.union.model.LevelBuilder;
import edu.union.model.RectangleGridLevelBuilder;

import java.util.AbstractMap;

public class LevelBuilderFactory {

    private static LevelBuilderFactory instance;
    private AbstractMap<String,LevelBuilder> map;
    public LevelBuilderFactory(){

    }

    public static LevelBuilderFactory getInstance(){
        if(instance == null){
            instance = new LevelBuilderFactory();
        }
        return instance;
    }

    public LevelBuilder createLevelBuilder(String levelType) throws Exception {
        if(map.containsKey(levelType)){
            return map.get(levelType);
        }
        throw new Exception("We cannot find a LevelBuilder for the given key");
    }

    public void register(String levelType, LevelBuilder levelBuilder){
        map.put(levelType,levelBuilder);
    }


}
