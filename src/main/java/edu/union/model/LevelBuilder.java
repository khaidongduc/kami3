package edu.union.model;

import edu.union.service.ColorRepository;
import edu.union.utils.Observable;
import edu.union.model.ColoredGraph.ColoredVertex;

import java.util.List;
import java.util.Set;

/**
 * an abstract class for levelBuilder
 * containing the required function to building a level
 *
 * @author Khai Dong
 */
public abstract class LevelBuilder <V extends ColoredVertex> extends Observable implements Cloneable {

    protected ColoredGraph<V> graph;
    protected Color curColor;

    public abstract String getLevelType();

    /**
     * basic initialization calling Observable constructor
     */
    public LevelBuilder(ColoredGraph<V> graph){
        super();
        this.graph = graph;
        this.curColor = getColors().get(0);
    }

    /**
     * get the grid associated with this levelBuilder
     * @return the grid associated with this levelBuilde
     */
    public ColoredGraph<V> getGraph(){
        return this.graph;
    }

    public Set<V> getVertexSet(){
        return graph.getVertexSet();
    }


    /**
     * get the color at a specified cell
     * @param vertex the vertex
     * @return the current color of that cell
     */
    public Color getColorAt(V vertex){
        int colorId = graph.getVertexColor(vertex);
        return ColorRepository.getInstance().getColor(colorId);
    }

    /**
     * get the color palette for creating level
     * listing all possible colors in color repository
     * @return List of colors
     */
    public List<Color> getColors(){
        return ColorRepository.getInstance().listColors();
    }
    /**
     * switch to a different color within the color palette of the grid
     * get the color palette by calling getColors()
     * @param color the new color
     * @throws IllegalArgumentException if the color does not exist within the palette, or it is the same color as the previous one
     */
    public void switchColor(Color color){
        this.curColor = color;
        notifyObservers();
    }

    /**
     * get the current color that the level builder is using
     * @return the current color
     */
    public Color getCurrentColor(){
        return this.curColor;
    }

    /**
     * set a vertex's color to a new color
     * @param color the new color
     * @param vertex the vertex
     * @throws IllegalArgumentException if color does not exist in color palette
     * @throws IllegalArgumentException if vertex is not in the graph
     */
    public void setColor(Color color, V vertex){
        graph.setVertexColor(vertex, color.getColorId());
        notifyObservers();
    }

    /**
     * set a grid cell color to a new color, and set the surrounding cells
     * with the same color to that new color
     * @param color the new color
     * @param vertex the vertex
     * @throws IllegalArgumentException if color does not exist in color palette
     * @throws IllegalArgumentException if index is our of bound
     */
    public void setColorFlood(Move<V> move){
        graph.colorFloodFill(move.getVertex(), move.getColor().getColorId());
        notifyObservers();
    }

    /**
     * restart the level creation from scratch
     */
    public void restart(){
        for(V vertex : graph.getVertexSet()){
            graph.setVertexColor(vertex, 0);
        }
        notifyObservers();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }
}
