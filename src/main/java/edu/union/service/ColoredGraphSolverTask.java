package edu.union.service;

import edu.union.model.ColoredGraph;
import edu.union.model.Move;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ColoredGraphSolverTask<V extends ColoredGraph.ColoredVertex> implements Callable<List<Move<V>>> {

    private final ColoredGraph<V> graph;

    public ColoredGraphSolverTask(ColoredGraph<V> coloredGraph) {
        this.graph = coloredGraph;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public List<Move<V>> call() throws Exception {
        try {
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
            while (!queue.isEmpty() && foundResult == null && !Thread.currentThread().isInterrupted()) {
                ColoredGraph<V> sourceGraph = queue.poll();
                for (V vertex : sourceGraph.getVertexSet()) {
                    int orgColor = sourceGraph.getVertexColor(vertex);
                    Set<Integer> possibleColors = sourceGraph.getNeighbors(vertex).stream()
                            .map(sourceGraph::getVertexColor).collect(Collectors.toSet());
                    possibleColors.remove(orgColor);

                    for (Integer color : possibleColors) {
                        sourceGraph.setVertexColor(vertex, color);
                        ColoredGraph<V> nextGraph = sourceGraph.pruneGraph();
                        queue.add(nextGraph);
                        prevGraph.put(nextGraph, sourceGraph);
                        moves.put(nextGraph, new Move<V>(ColorRepository.getInstance().getColor(color), vertex));

                        int nextDistance = distances.get(sourceGraph) + 1;
                        distances.put(nextGraph, nextDistance);

                        if (nextGraph.getNumVertices() == 1) {
                            foundResult = nextGraph;
                        }
                    }
                    sourceGraph.setVertexColor(vertex, orgColor);
                }
            }
            LinkedList<Move<V>> solution = new LinkedList<>();
            while (prevGraph.get(foundResult) != null) {
                solution.addFirst(moves.get(foundResult));
                foundResult = prevGraph.get(foundResult);
            }
            return solution;
        } catch (OutOfMemoryError ignored) {
            throw new RuntimeException("unable to solve");
        }

    }
}
