package edu.union.model;
import edu.union.model.ColoredGraph.ColoredVertex;

/**
 * Basic class containing data on a move that can be made on a level
 */
public class Move <V extends ColoredVertex>{

    private final Color color;
    private final V vertex;
    /**
     * initialization
     * note that create with index out of bound is accepted
     * but should be rejected when passed into Level
     * @param color the color of the move
     * @oaram vertex the vertex we are moving
     */
    public Move(Color color, V vertex){
        this.color = color;
        this.vertex = vertex;
    }

    /**
     * Vertex getter
     * @return the vertex
     */
    public V getVertex() {
        return this.vertex;
    }

    /**
     * color getter
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    public String toString(){
        return vertex.toString() + color.toString();
    }
}
