package edu.union.model;

import edu.union.utils.Observable;
import edu.union.model.ColoredGraph.ColoredVertex;
import edu.union.service.ColorRepository;

import java.util.List;
import java.util.Stack;

public abstract class LevelHint<V extends ColoredVertex> extends Observable{

    protected ColoredGraph<V> graph;

    protected Color curColor;

    public LevelHint(ColoredGraph<V> graph){
        super();
        this.graph = graph;
        this.curColor = getColors().get(0);
    }

    public Color getColorAt(V vertex){
        int colorId = graph.getVertexColor(vertex);
        return ColorRepository.getInstance().getColor(colorId);
    }

    public List<Color> getColors(){
        return ColorRepository.getInstance().listColors();
    }

    public void switchColor(Color color){
        this.curColor = color;
        notifyObservers();
    }

    public Color getCurrentColor(){
        return this.curColor;
    }

    public void play(Move<V> move){
        graph.colorFloodFill(move.getVertex(), move.getColor().getColorId());
        notifyObservers();
    }
    public void restart(){
        notifyObservers();
    }

    public ColoredGraph<V> getGraph(){
        return this.graph;
    }
    public LevelState getLevelState(){
        boolean isMono = this.graph.pruneGraph().getNumVertices() == 1;
        if(!isMono){
            return LevelState.ONGOING;
        }
        return LevelState.WIN;
    }

}
