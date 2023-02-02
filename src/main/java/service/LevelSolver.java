package service;

import model.ColorGrid;
import model.ColorGrid.GridCellPosition;
import model.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * service class to solve a level
 * TODO: need implementation
 */
public class LevelSolver {

    public List<Move> solveColorGrid(ColorGrid grid)
    {
        ColoredGraph<GridCellPosition> graph = new ColoredGraph<>();

        int numRows = grid.getNumRows();
        int numCols = grid.getNumCols();
        for(int i = 0 ; i < numRows ; ++ i){
            for(int j = 0 ; j < numCols ; ++ j){
                graph.addVertex(new GridCellPosition(i, j), grid.getColorOfEntry(i, j));
            }
        }
        for(int i = 0 ; i < numRows ; ++ i){
            for(int j = 0 ; j < numCols ; ++ j){
                GridCellPosition cell = new GridCellPosition(i, j);
                for(GridCellPosition adjCell : grid.getNeighborPositions(cell)){
                    graph.addEdge(cell, adjCell);
                }
            }
        }

        Queue<ColoredGraph<GridCellPosition>> queue = new LinkedList<>();
        queue.add(graph.pruneGraph());


        while(!queue.isEmpty()){
            ColoredGraph<GridCellPosition> sourceGraph = queue.poll();
            for(GridCellPosition vertex : sourceGraph.getVertexSet()){
                int orgColor = sourceGraph.getVertexColor(vertex);
                Set<Integer> possibleColors = sourceGraph.getNeighbors(vertex).stream()
                        .map(sourceGraph::getVertexColor).collect(Collectors.toSet());
                possibleColors.remove(orgColor);

                for(Integer color : possibleColors){
                    sourceGraph.setVertexColor(vertex, color);
                    ColoredGraph<GridCellPosition> nextGraph = sourceGraph.pruneGraph();

                    if(nextGraph.getNumVertices() == 1){
                        System.out.println("Found");
                        return null;
                    }

                    queue.add(nextGraph);
                }
                sourceGraph.setVertexColor(vertex, orgColor);
            }

        }

        return null;
    }

}
