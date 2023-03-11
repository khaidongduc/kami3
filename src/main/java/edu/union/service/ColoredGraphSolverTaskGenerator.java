package edu.union.service;

import edu.union.model.*;
import edu.union.model.ColoredGraph.ColoredVertex;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * edu.union.service class to solve a level
 * only method solveColorGrid which return a list of Move
 */
public class ColoredGraphSolverTaskGenerator {

    private static ColoredGraphSolverTaskGenerator instance;


    /**
     * basic constructor
     */
    private ColoredGraphSolverTaskGenerator(){
    }

    /**
     * get the instance of a singleton class
     * @return the instance
     */
    public static ColoredGraphSolverTaskGenerator getInstance(){
        if(instance == null)
            instance = new ColoredGraphSolverTaskGenerator();
        return instance;
    }

    /**
     * solve the ColorGrid grid
     * return a list of move on a grid and that to make it mono-color in the least number of moves
     *
     * @param graph the color grid
     * @return the list of moves as solutions
     * @throws RuntimeException if the graph is unsolvable under the constraint
     */
    public <V extends ColoredVertex> Callable<List<Move<V>>> getSolverTask(ColoredGraph<V> graph)
    {
        return new ColoredGraphSolverTask<>(graph);
    }

}

