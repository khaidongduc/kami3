package edu.union.service;

import edu.union.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * a strategy where level is saved in raw text format
 */
public class RawTextLevelRepositoryStrategy implements LevelRepositoryStrategy {

    public static String DEFAULT_FOLDER_PATH = Level.class.getResource("levels/text/").getPath();


    public String folderPath;

    public RawTextLevelRepositoryStrategy(){
        folderPath = DEFAULT_FOLDER_PATH;
    }

    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    /**
     * load level from a levelInfo
     *
     * @param levelInfo the levelInfo
     * @return the corresponding level
     */
    @Override
    public Level loadLevel(LevelInfo levelInfo) {
        try {
            String relPath = String.format("%s", levelInfo.getLevelId());
            File file = new File(String.format("%s/%s", folderPath, relPath));
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
            ArrayList<Move> hints = new ArrayList<>();
            for(int i = 0 ; i < maxNumTurn ; ++ i){
                int colorId = scanner.nextInt(), row = scanner.nextInt(), col = scanner.nextInt();
                Color color = ColorRepository.getInstance().getColor(colorId);
                Move move = new Move(color, row, col);
                hints.add(move);
            }
            Color curColor = ColorRepository.getInstance().getColor(grid.getAvailableColorIds().stream().findFirst().get());
            return new LevelImpl(grid, curColor, hints, levelInfo);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * save a level into hard drive using information from an existing levelBuilder
     *
     * @param levelBuilder the levelBuilder
     */
    @Override
    public void saveLevel(LevelBuilder levelBuilder) {
        File folder = new File(folderPath);
        try {
            String fileName = "/"+ (folder.listFiles().length + 1);
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(levelBuilder.getNumRows() + " " + levelBuilder.getNumCols() + "\n");
            for(int i = 0; i < levelBuilder.getNumRows(); i++){
                String line = Integer.toString(levelBuilder.getColorAt(i,0).getColorId());
                for(int j = 1; j < levelBuilder.getNumCols(); j++){
                    line = line + " " + levelBuilder.getColorAt(i,j).getColorId();
                }
                line += "\n";
                fw.write(line);
            }
            List<Move> hints = LevelSolver.getInstance().solveColorGrid(levelBuilder.getGrid());
            fw.write(Integer.toString(hints.size()) + "\n");
            for(Move move : hints){
                fw.write(move.getColor().getColorId() + " " + move.getRow() + " " + move.getCol() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * list all available levelInfos
     *
     * @return levelInfo
     */
    @Override
    public List<LevelInfo> listLevelInfo() {
        File folder = new File(folderPath);
        LinkedList<LevelInfo> levelInfos = new LinkedList<>();
        for(File file : folder.listFiles()){
            levelInfos.add(new LevelInfo(Integer.parseInt(file.getName())));
        }
        levelInfos.sort(Comparator.comparingInt(LevelInfo::getLevelId));
        return levelInfos;
    }
}
