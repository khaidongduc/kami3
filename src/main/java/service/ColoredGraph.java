package service;

import com.sun.tools.jdeps.Graph;
import jdk.javadoc.internal.doclets.toolkit.taglets.snippet.Style;
import model.Color;
import model.ColorGrid;

import java.util.*;

public class ColoredGraph <V> {

    Map<V, Set<V>> adjVertices;
    Map<V, Integer> colorMap;

    public ColoredGraph(){
        this.adjVertices = new HashMap<>();
        this.colorMap = new HashMap<>();
    }

    public ColoredGraph(ColoredGraph<V> graph){
        this();
        for(V vertex : graph.adjVertices.keySet()){
            addVertex(vertex, graph.getVertexColor(vertex));
        }
        for(V vertex : graph.adjVertices.keySet()){
            for(V adj : graph.adjVertices.get(vertex)){
                addEdge(vertex, adj);
            }
        }
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

}
