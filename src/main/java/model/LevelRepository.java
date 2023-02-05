package model;

import utils.Observable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * a level repository that manages all Levels (list and create)
 * Singleton Class
 *
 * @author Khai Dong
 */
public class LevelRepository extends Observable {
    private static LevelRepository instance;

    private LevelRepositoryStrategy levelRepositoryStrategy;

    /**
     * basic initialization
     */
    private LevelRepository(){

    }

    public void setLevelRepositoryStrategy(LevelRepositoryStrategy levelRepositoryStrategy) {
        this.levelRepositoryStrategy = levelRepositoryStrategy;
    }

    /**
     * get an instance or create an instance if it does not exist
     * @return the instance
     */
    public static LevelRepository getInstance(){
        if(instance == null)
            instance = new LevelRepository();
        return instance;
    }


    public LevelImpl loadLevel(LevelInfo levelInfo){
        try {
            String relPath = String.format("levels/%s", levelInfo.getLevelId());
            File file = new File(getClass().getResource(relPath).getPath());
            Scanner scanner = new Scanner(file);
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            ColorGrid grid = new ColorGrid(numRows, numCols);
            for (int i = 0; i < numRows; ++i){
                for (int j = 0; j < numCols; ++j) {
                    int colorId = scanner.nextInt();
                    if(grid.getColorOfEntry(i, j) != colorId) grid.setColor(colorId, i, j);
                }
            }
            int maxNumTurn = scanner.nextInt();
            Color curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
            return new LevelImpl(grid, curColor, maxNumTurn, levelInfo);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * convert the levelBuilder to a level,
     * assign an id and save the level into hard drive
     * @param levelBuilder the levelBuilder
     */
    public void saveLevel(LevelBuilder levelBuilder){
        File folder = new File(getClass().getResource("levels").getPath());
        try {
            String fileName = "/"+(folder.listFiles().length+1);
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(levelBuilder.getNumRows() +" "+ levelBuilder.getNumCols() + "\n");
            for(int i = 0; i < levelBuilder.getNumRows(); i++){
                String line = Integer.toString(levelBuilder.getColorAt(i,0).getColorId());
                for(int j = 1; j < levelBuilder.getNumCols(); j++){
                    line = line + " " + levelBuilder.getColorAt(i,j).getColorId();
                }
                line += "\n";
                fw.write(line);
            }
            fw.write(Integer.toString(levelBuilder.getMinNumMoves()));
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifyObservers();
    }

    /**
     * list all level info
     * @return the List of all LevelInfo
     */
    public List<LevelInfo> listLevelInfo(){
        File folder = new File(getClass().getResource("levels").getPath());
        LinkedList<LevelInfo> levelInfos = new LinkedList<>();
        for(File file : folder.listFiles()){
            levelInfos.add(new LevelInfo(Integer.parseInt(file.getName())));
        }
        return levelInfos;
    }

}
