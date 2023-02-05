package edu.union.service;

import edu.union.model.*;
import edu.union.model.ColorGrid.GridCellPosition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * edu.union.service class to solve a level
 * TODO: need implementation
 */
public class LevelSolver {

    private static LevelSolver instance;

    private LevelSolver(){

    }

    public static LevelSolver getInstance(){
        if(instance == null)
            instance = new LevelSolver();
        return instance;
    }

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
        Map<ColoredGraph<GridCellPosition>, ColoredGraph<GridCellPosition>> prevGraph = new HashMap<>();
        Map<ColoredGraph<GridCellPosition>, Move> moves = new HashMap<>();

        ColoredGraph<GridCellPosition> startGraph = graph.pruneGraph();
        queue.add(startGraph);
        prevGraph.put(startGraph, null);
        moves.put(startGraph, null);

        ColoredGraph<GridCellPosition> foundResult = null;
        while(!queue.isEmpty() && foundResult == null){
            ColoredGraph<GridCellPosition> sourceGraph = queue.poll();
            for(GridCellPosition vertex : sourceGraph.getVertexSet()){
                int orgColor = sourceGraph.getVertexColor(vertex);
                Set<Integer> possibleColors = sourceGraph.getNeighbors(vertex).stream()
                        .map(sourceGraph::getVertexColor).collect(Collectors.toSet());
                possibleColors.remove(orgColor);

                for(Integer color : possibleColors){
                    sourceGraph.setVertexColor(vertex, color);
                    ColoredGraph<GridCellPosition> nextGraph = sourceGraph.pruneGraph();
                    queue.add(nextGraph);
                    prevGraph.put(nextGraph, sourceGraph);
                    moves.put(nextGraph, new Move(ColorRepository.getInstance().getColor(color), vertex.row, vertex.col));
                    if(nextGraph.getNumVertices() == 1){
                        foundResult = nextGraph;
                    }
                }
                sourceGraph.setVertexColor(vertex, orgColor);
            }
        }
        List<Move> solution = new ArrayList<>();
        while(prevGraph.get(foundResult) != null){
            solution.add(moves.get(foundResult));
            foundResult = prevGraph.get(foundResult);
        }
        return solution;
    }



}