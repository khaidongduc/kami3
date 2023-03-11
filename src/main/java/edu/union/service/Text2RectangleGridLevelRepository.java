package edu.union.service;

import edu.union.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * a strategy where level is saved in raw text format with "," being the delimiter
 */
public class Text2RectangleGridLevelRepository extends LevelRepository {

    private static final int MAXBUILDTIME = 5;
    private static Text2RectangleGridLevelRepository instance;

    private Text2RectangleGridLevelRepository() {

    }

    /**
     * singleton's getInstance()
     * @return the instance
     */
    public static Text2RectangleGridLevelRepository getInstance(){
        if(instance == null){
            instance = new Text2RectangleGridLevelRepository();
        }
        return instance;
    }

    /**
     * load a RectangleGridLevel using a levelInfo
     * if levelInfo is wrong, will throw a runtime exception
     * @param levelInfo the levelInfo
     * @return the associated level
     */
    @Override
    public Level _loadLevel(LevelInfo levelInfo) {
        try {
            File file = new File(levelInfo.getFilePath());
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[;,]\\s*");
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            ColoredGraph<RectangleGridCell> graph = new ColoredGraph<>();
            for (int i = 0; i < numRows; ++i){
                for (int j = 0; j < numCols; ++j) {
                    int colorId = scanner.nextInt();
                    graph.addVertex(new RectangleGridCell(i, j), colorId);
                }
            }
            graph.buildGraphWithAdjacency();
            int maxNumTurn = scanner.nextInt();
            ArrayList<Move<RectangleGridCell>> hints = new ArrayList<>();
            for(int i = 0 ; i < maxNumTurn ; ++ i){
                int colorId = scanner.nextInt(), row = scanner.nextInt(), col = scanner.nextInt();
                Color color = ColorRepository.getInstance().getColor(colorId);
                Move<RectangleGridCell> move = new Move<RectangleGridCell>(color, new RectangleGridCell(row, col));
                hints.add(move);
            }
            return new RectangleGridLevel(graph, hints, levelInfo);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * save a RectangleGridLevelBuilder
     * if levelBuilder is of wrong type, will throw a runtime exception
     * @param lb the LevelBuilder
     * @param folderPath the path of the folder where the file is saved
     */
    @Override
    public void _saveLevel(LevelBuilder lb, List<Move> hints, String folderPath) {
        RectangleGridLevelBuilder levelBuilder = (RectangleGridLevelBuilder) lb;

        File folder = new File(folderPath);
        try {
            String fileName = "/" + (folder.listFiles().length + 1) + '.' + levelBuilder.getLevelType();
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(levelBuilder.getRows() + "," + levelBuilder.getCols() + ",\n");
            for(int i = 0; i < levelBuilder.getRows(); i++){
                String line = Integer.toString(levelBuilder.getColorAt(new RectangleGridCell(i,0)).getColorId());
                for(int j = 1; j < levelBuilder.getCols(); j++){
                    line = line + "," + levelBuilder.getColorAt(new RectangleGridCell(i,j)).getColorId();
                }
                line += ",\n";
                fw.write(line);
            }

            try {
                fw.write(hints.size() + ",\n");
                for(Move<RectangleGridCell> move : hints){
                    fw.write(move.getColor().getColorId() + ","
                            + move.getVertex().row + "," + move.getVertex().col + ",\n");
                }
            } catch (Exception e) {
                fw.close();
                File f = new File(folder+fileName);
                f.delete();
                throw new RuntimeException("Level save took too long");
            }
            fw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void _saveLevel(LevelHint l, String folderPath) {
        RectangleHintInputLevel level = (RectangleHintInputLevel) l;

        File folder = new File(folderPath);
        try {
            String fileName = "/"+ (folder.listFiles().length + 1) + '.' + level.getLevelType();
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(level.getRows() + "," + level.getCols() + ",\n");
            for(int i = 0; i < level.getRows(); i++){
                String line = Integer.toString(level.getColorAt(new RectangleGridCell(i,0)).getColorId());
                for(int j = 1; j < level.getCols(); j++){
                    line = line + "," + level.getColorAt(new RectangleGridCell(i,j)).getColorId();
                }
                line += ",\n";
                fw.write(line);
            }
            List<Move<RectangleGridCell>> hints =  level.getHints();
            fw.write(Integer.toString(hints.size()) + ",\n");
            for(Move<RectangleGridCell> move: hints){
                fw.write(move.getColor().getColorId() + ","
                        + move.getVertex().row + "," + move.getVertex().col + ",\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
