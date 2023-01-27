package model;

import java.util.List;

public class LevelRepository {
    private static LevelRepository instance;


    private LevelRepository(){

    }

    public LevelRepository getInstance(){
        if(instance == null)
            instance = new LevelRepository();
        return instance;
    }

    public Level getGame(int gameId){
        return null;
    }

    public List<Level> listGames(){
        return null;
    }


}
