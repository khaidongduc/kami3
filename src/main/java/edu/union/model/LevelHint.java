package edu.union.model;

import edu.union.utils.Observable;
import edu.union.model.ColoredGraph.ColoredVertex;
import edu.union.service.ColorRepository;
import edu.union.utils.PlayMoveCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class LevelHint<V extends ColoredVertex> extends Observable{

    protected ColoredGraph<V> graph;
    private ColoredGraph<V> startGraph;
    protected Color curColor;

    private List<Move<V>> hintList;

    public LevelHint(ColoredGraph<V> graph){
        super();
        this.graph = graph;
        this.startGraph = new ColoredGraph<V>(graph);
        this.curColor = getColors().get(0);
        this.hintList = new LinkedList<>();
    }
    public abstract String getLevelType();
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
        this.graph = new ColoredGraph(this.startGraph);
        notifyObservers();
    }

    public ColoredGraph<V> getGraph(){
        return this.graph;
    }
    public void resetGraph(){this.graph = new ColoredGraph(this.startGraph);}
    public LevelState getLevelState(){
        boolean isMono = this.graph.pruneGraph().getNumVertices() == 1;
        if(!isMono){
            return LevelState.ONGOING;
        }
        return LevelState.WIN;
    }

    public void setHints(List<PlayMoveCommand> hintList){
        for(PlayMoveCommand c: hintList){
            this.hintList.add(c.getMove());
        }
    }

    public List<Move<V>> getHints(){
        return this.hintList;
    }
}
