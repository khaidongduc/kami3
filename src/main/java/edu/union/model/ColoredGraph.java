package edu.union.model;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * a helper class for LevelSolver
 * @param <V> the type of the vertex
 */
public class ColoredGraph <V extends ColoredGraph.ColoredVertex> {

    private final String uuid;
    private final Map<V, Set<V>> adjVertices;
    private final Map<V, Integer> colorMap;

    /**
     * return the graph UUID
     * each graph is created with a unique uuid
     * @return the graph uuid
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
     * basic constructor
     */
    public ColoredGraph(){
        this.adjVertices = new HashMap<>();
        this.colorMap = new HashMap<>();
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    /**
     * copy constructor
     * @param graph a colored graph
     */
    public ColoredGraph(ColoredGraph<V> graph){
        this();
        for(V vertex : graph.adjVertices.keySet()){
            addVertex(vertex, graph.getVertexColor(vertex));
        }
        for(V vertex : graph.adjVertices.keySet()){
            for(V adj : graph.getNeighbors(vertex)){
                addEdge(vertex, adj);
            }
        }
    }


    /**
     * getter for the vertex set of the graph
     * @return the vertex set of the graph
     */
    public Set<V> getVertexSet(){
        return adjVertices.keySet();
    }

    /**
     * return the neighbors of a vertex
     * @param vertex the vertex
     * @return the neighbors of a vertex
     */
    public Set<V> getNeighbors(V vertex){
        return adjVertices.get(vertex);
    }

    /**
     * add a vertex
     * @param vertex the new vertex
     * @param color the color
     * @throws IllegalArgumentException if the vertex already existed or the color is invalid
     */
    public void addVertex(V vertex, int color) {
        if(adjVertices.containsKey(vertex))
            throw new IllegalArgumentException("graph already has this vertex");
        if(color < 0)
            throw new IllegalArgumentException("invalid color");
        adjVertices.put(vertex, new HashSet<>());
        colorMap.put(vertex, color);
    }

    /**
     * add a bidirectional edge, ignore if edge already exist
     * ignore loop edges
     * @param start the start the edge
     * @param end the end of the edge
     */
    public void addEdge(V start, V end){
        if(start.equals(end))
            return;
        adjVertices.get(start).add(end);
        adjVertices.get(end).add(start);
    }

    /**
     * remove an edge from the graph
     * ignore if edge does not exist
     * @param start the start of the edge
     * @param end the end of the edge
     */
    public void removeEdge(V start, V end){
        adjVertices.get(start).remove(end);
        adjVertices.get(end).remove(start);
    }

    /**
     * get the color of a vertex
     * @param vertex a vertex
     * @return the color of that vertex
     * @throws IllegalArgumentException if the vertex is not in the graph
     */
    public int getVertexColor(V vertex){
        if(!adjVertices.containsKey(vertex))
            throw new IllegalArgumentException("the vertex is not in the graph");
        return colorMap.get(vertex);
    }

    /**
     * set the color of a vertex
     * @param vertex a vertex
     * @param color the new color
     * @throws IllegalArgumentException if the vertex is not in the graph
     */
    public void setVertexColor(V vertex, int color){
        if(!adjVertices.containsKey(vertex))
            throw new IllegalArgumentException("the vertex is not in the graph");
        colorMap.put(vertex, color);
    }

    /**
     * get the number of vertices
     * @return the number of vertices
     */
    public int getNumVertices(){
        return adjVertices.size();
    }

    /**
     * add edges based on the adjacencyTo() implemented in V
     * will remove all existing edges
     * <p>
     * should call after adding all vertices to add the needed edges
     */
    public void buildGraphWithAdjacency(){
        for(V vertex : getVertexSet()){
            for(V anotherVertex : getVertexSet()) removeEdge(vertex, anotherVertex);
        }
        for(V vertex : getVertexSet()){
            for(V anotherVertex : getVertexSet()) if(vertex.adjacentTo(anotherVertex)){
                addEdge(vertex, anotherVertex);
            }
        }
    }

    /**
     * return a pruned graph of this graph
     * the pruning is that all connected vertices of the same color will be merged
     * @return the pruned graph
     */
    public ColoredGraph<V> pruneGraph(){
        ColoredGraph<V> cloned = new ColoredGraph<>(this);
        ColoredGraph<V> res = new ColoredGraph<>();
        int componentCount = 0;
        List<V> components = new ArrayList<>();
        for(V vertex : cloned.adjVertices.keySet()){
            if(cloned.getVertexColor(vertex) >= 0) {
                cloned.colorFloodFill(vertex, --componentCount);
                res.addVertex(vertex, this.getVertexColor(vertex));
                components.add(vertex); // this vertex color in cloned will be -componentCount-1
            }
        }
        for(V vertex : this.adjVertices.keySet()){
            for(V adjVertex : this.adjVertices.get(vertex)){
                V newStartVert = components.get(-cloned.getVertexColor(vertex)-1);
                V newEndVert = components.get(-cloned.getVertexColor(adjVertex)-1);
                res.addEdge(newStartVert, newEndVert);
            }
        }
        return res;
    }

    /**
     * flood fill the graph from a vertex with a color
     *
     * @param vertex a vertex
     * @param color color
     */
    public void colorFloodFill(V vertex, int color) {
        Queue<V> queue = new LinkedList<>();
        queue.add(vertex);
        int orgColor = this.getVertexColor(vertex);
        this.setVertexColor(vertex, color);
        while (!queue.isEmpty()){
            V source = queue.poll();
            for(V adj : adjVertices.get(source)){
                if(this.getVertexColor(adj) == orgColor && this.getVertexColor(adj) != color){
                    queue.add(adj);
                    this.setVertexColor(adj, color);
                }
            }
        }
    }

    /**
     * return all color id existed in this graph
     * @return List of color Ids
     */
    public List<Integer> getColorIds() {
        return colorMap.values().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredGraph<?> that = (ColoredGraph<?>) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    /**
     * the abstract class for all coloredVertex
     */
    public abstract static class ColoredVertex {

        /**
         * define how 2 vertex can be adjacent through their properties
         * @param vertex the other vertex
         * @return true iff they are adjacent
         */
        public boolean adjacentTo(ColoredVertex vertex){
            return true;
        }

    }
}
