package service;

import com.sun.tools.jdeps.Graph;
import model.Color;
import model.ColorGrid;

import java.util.*;
import java.util.UUID;

public class ColoredGraph <V> {

    private String uuid;
    private Map<V, Set<V>> adjVertices;
    private Map<V, Integer> colorMap;

    public ColoredGraph(){
        this.adjVertices = new HashMap<>();
        this.colorMap = new HashMap<>();
        this.uuid = String.valueOf(UUID.randomUUID());
    }

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

    public String getUUID(){
        return this.uuid;
    }

    public Set<V> getVertexSet(){
        return adjVertices.keySet();
    }

    public Set<V> getNeighbors(V vertex){
        return adjVertices.get(vertex);
    }

    public void addVertex(V vertex, int color)
    {
        if(adjVertices.containsKey(vertex))
            throw new IllegalArgumentException("graph already has this vertex");
        if(color < 0)
            throw new IllegalArgumentException("invalid color");
        adjVertices.put(vertex, new HashSet<>());
        colorMap.put(vertex, color);
    }

    public void addEdge(V start, V end){
        adjVertices.get(start).add(end);
        adjVertices.get(end).add(start);
    }

    public int getVertexColor(V vertex){
        return colorMap.get(vertex);
    }

    public void setVertexColor(V vertex, int color){
        colorMap.put(vertex, color);
    }

    public int getNumVertices(){
        return adjVertices.size();
    }

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

    private void colorFloodFill(V vertex, int i) {
        Queue<V> queue = new LinkedList<>();
        queue.add(vertex);
        int orgColor = this.getVertexColor(vertex);
        this.setVertexColor(vertex, i);
        while (!queue.isEmpty()){
            V source = queue.poll();
            for(V adj : adjVertices.get(source)){
                if(this.getVertexColor(adj) == orgColor && this.getVertexColor(adj) != i){
                    queue.add(adj);
                    this.setVertexColor(adj, i);
                }
            }
        }
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

}
