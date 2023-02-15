package edu.union.service;

import edu.union.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * a level repository that manages all Levels (list and create)
 * Singleton Class
 *
 * @author Khai Dong
 */
public class TextRectangleGridLevelRepository implements LevelRepository {
    private static TextRectangleGridLevelRepository instance;

    /**
     * get an instance or create an instance if it does not exist
     * @return the instance
     */
    public static TextRectangleGridLevelRepository getInstance(){
        if(instance == null)
            instance = new TextRectangleGridLevelRepository();
        return instance;
    }

    /**
     * basic initialization
     */
    private TextRectangleGridLevelRepository(){
    }

    /**
     * load a level using a LevelInfo
     * @param levelInfo the levelInfo
     * @return the corresponding Level object
     */
    public Level loadLevel(LevelInfo levelInfo){
        try {
            File file = new File(levelInfo.getFilePath());
            Scanner scanner = new Scanner(file);
            String levelType = scanner.next();
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
     * convert the levelBuilder to a level,
     * assign an id and save the level into hard drive
     * @param lb the levelBuilder
     */
    public void saveLevel(LevelBuilder lb, String folderPath){
        RectangleGridLevelBuilder levelBuilder = (RectangleGridLevelBuilder) lb;

        File folder = new File(folderPath);
        try {
            String fileName = "/"+ (folder.listFiles().length + 1);
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(lb.getLevelType() + '\n');
            fw.write(levelBuilder.getRows() + " " + levelBuilder.getCols() + "\n");
            for(int i = 0; i < levelBuilder.getRows(); i++){
                String line = Integer.toString(levelBuilder.getColorAt(new RectangleGridCell(i,0)).getColorId());
                for(int j = 1; j < levelBuilder.getCols(); j++){
                    line = line + " " + levelBuilder.getColorAt(new RectangleGridCell(i,j)).getColorId();
                }
                line += "\n";
                fw.write(line);
            }
            List<Move<RectangleGridCell>> hints = ColoredGraphSolver.getInstance().solveColoredGraph(levelBuilder.getGraph());
            fw.write(Integer.toString(hints.size()) + "\n");
            for(Move<RectangleGridCell> move : hints){
                fw.write(move.getColor().getColorId() + " "
                        + move.getVertex().row + " " + move.getVertex().col + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
