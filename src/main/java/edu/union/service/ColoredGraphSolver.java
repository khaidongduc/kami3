package edu.union.service;

import edu.union.model.*;
import edu.union.model.ColoredGraph.ColoredVertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * edu.union.service class to solve a level
 * only method solveColorGrid which return a list of Move
 */
public class ColoredGraphSolver {

    private static ColoredGraphSolver instance;

    private static final int DEFAULT_MAX_NUM_STEPS = 5;

    private int maxNumSteps;

    /**
     * basic constructor
     */
    private ColoredGraphSolver(){
        maxNumSteps = DEFAULT_MAX_NUM_STEPS;
    }

    /**
     * get the instance of a singleton class
     * @return the instance
     */
    public static ColoredGraphSolver getInstance(){
        if(instance == null)
            instance = new ColoredGraphSolver();
        return instance;
    }

    public void setMaxNumSteps(int maxNumSteps){
        this.maxNumSteps = maxNumSteps;
    }

    /**
     * solve the ColorGrid grid
     * return a list of move on a grid and that to make it mono-color in the least number of moves
     *
     * @param graph the color grid
     * @return the list of moves as solutions
     */
    public <V extends ColoredVertex> List<Move<V>> solveColoredGraph(ColoredGraph<V> graph)
    {
        Queue<ColoredGraph<V>> queue = new LinkedList<>();
        Map<ColoredGraph<V>, ColoredGraph<V>> prevGraph = new HashMap<>();
        Map<ColoredGraph<V>, Move<V>> moves = new HashMap<>();
        Map<ColoredGraph<V>, Integer> distances = new HashMap<>();

        ColoredGraph<V> startGraph = graph.pruneGraph();
        queue.add(startGraph);
        prevGraph.put(startGraph, null);
        moves.put(startGraph, null);
        distances.put(startGraph, 0);

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

                    int nextDistance = distances.get(sourceGraph) + 1;
                    if(nextDistance > this.maxNumSteps){
                        throw new RuntimeException("unable to solve");
                    }
                    distances.put(nextGraph, nextDistance);

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
