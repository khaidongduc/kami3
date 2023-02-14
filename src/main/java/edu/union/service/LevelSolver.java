package edu.union.service;

import edu.union.model.*;
import edu.union.model.ColoredGraph.ColoredVertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * edu.union.service class to solve a level
 * only method solveColorGrid which return a list of Move
 */
public class LevelSolver {

    private static LevelSolver instance;

    /**
     * basic constructor
     */
    private LevelSolver(){

    }

    /**
     * get the instance of a singleton class
     * @return the instance
     */
    public static LevelSolver getInstance(){
        if(instance == null)
            instance = new LevelSolver();
        return instance;
    }

    /**
     * solve the ColorGrid grid
     * return a list of move on a grid and that to make it mono-color in the least number of moves
     *
     * @param graph the color grid
     * @return the list of moves as solutions
     */
    public <V extends ColoredVertex> List<Move<V>> solveColorGrid(ColoredGraph<V> graph)
    {
        Queue<ColoredGraph<V>> queue = new LinkedList<>();
        Map<ColoredGraph<V>, ColoredGraph<V>> prevGraph = new HashMap<>();
        Map<ColoredGraph<V>, Move<V>> moves = new HashMap<>();

        ColoredGraph<V> startGraph = graph.pruneGraph();
        queue.add(startGraph);
        prevGraph.put(startGraph, null);
        moves.put(startGraph, null);

        ColoredGraph<V> foundResult = null;
        while(!queue.isEmpty() && foundResult == null){
            ColoredGraph<V> sourceGraph = queue.poll();
            for(V vertex : sourceGraph.getVertexSet()){
                int orgColor = sourceGraph.getVertexColor(vertex);
                Set<Integer> possibleColors = sourceGraph.getNeighbors(vertex).stream()
                        .map(sourceGraph::getVertexColor).collect(Collectors.toSet());
                possibleColors.remove(orgColor);

                for(Integer color : possibleColors){
                    sourceGraph.setVertexColor(vertex, color);
                    ColoredGraph<V> nextGraph = sourceGraph.pruneGraph();
                    queue.add(nextGraph);
                    prevGraph.put(nextGraph, sourceGraph);
                    moves.put(nextGraph, new Move<V>(ColorRepository.getInstance().getColor(color), vertex));
                    if(nextGraph.getNumVertices() == 1){
                        foundResult = nextGraph;
                    }
                }
                sourceGraph.setVertexColor(vertex, orgColor);
            }
        }
        LinkedList<Move<V>> solution = new LinkedList<>();
        while(prevGraph.get(foundResult) != null){
            solution.addFirst(moves.get(foundResult));
            foundResult = prevGraph.get(foundResult);
        }
        return solution;
    }



}
