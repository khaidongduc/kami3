package edu.union.service;

import com.sun.tools.jdeps.Graph;
import edu.union.model.*;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * a strategy where level is saved in raw text format
 */
public class TextRectangleGridLevelRepositoryStrategy implements LevelRepositoryStrategy {

    private static TextRectangleGridLevelRepositoryStrategy instance;

    private TextRectangleGridLevelRepositoryStrategy() {

    }

    public static TextRectangleGridLevelRepositoryStrategy getInstance(){
        if(instance == null){
            instance = new TextRectangleGridLevelRepositoryStrategy();
        }
        return instance;
    }

    @Override
    public Level loadFromFile(LevelInfo levelInfo) {
        try {
            File file = new File(levelInfo.getFilePath());
            Scanner scanner = new Scanner(file);
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

    @Override
    public void saveToFile(LevelBuilder lb) {
        RectangleGridLevelBuilder levelBuilder = (RectangleGridLevelBuilder) lb;

        File folder = new File("abc");
        try {
            String fileName = "/"+ (folder.listFiles().length + 1);
            FileWriter fw = new FileWriter(folder+fileName);
            fw.write(levelBuilder.getRows() + " " + levelBuilder.getCols() + "\n");
            for(int i = 0; i < levelBuilder.getRows(); i++){
                String line = Integer.toString(levelBuilder.getColorAt(new RectangleGridCell(i,0)).getColorId());
                for(int j = 1; j < levelBuilder.getCols(); j++){
                    line = line + " " + levelBuilder.getColorAt(new RectangleGridCell(i,j)).getColorId();
                }
                line += "\n";
                fw.write(line);
            }
            List<Move<RectangleGridCell>> hints = LevelSolver.getInstance().solveColorGrid(levelBuilder.getGraph());
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
