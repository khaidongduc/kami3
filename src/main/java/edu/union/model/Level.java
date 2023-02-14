package edu.union.model;

import edu.union.service.ColorRepository;
import edu.union.utils.Observable;
import edu.union.model.ColoredGraph.ColoredVertex;

import java.util.List;
import java.util.Set;

/**
 * an abstract class defining all methods used in playing a level
 * it extend Observable to allows View to subscribe to its change
 */
public abstract class Level<V extends ColoredVertex> extends Observable {

    protected LevelInfo levelInfo;
    protected int curNumTurn;
    protected List<Move<V>> hints;
    protected Color curColor;
    protected ColoredGraph<V> graph;

    /**
     * constructor
     * ensure to initialize Observable
     */
    public Level(ColoredGraph<V> graph,
                 List<Move<V>> hints,
                 LevelInfo levelInfo) {
        super();
        this.graph = graph;
        this.hints = hints;
        this.levelInfo = levelInfo;
        this.curNumTurn = 0;
        this.curColor = getColors().iterator().next(); // first color in the graph
    }


    /**
     * get the levelInfo
     * @return the levelInfo
     */
    public LevelInfo getLevelInfo(){
        return levelInfo;
    };

    public Set<V> getVertexSet(){
        return graph.getVertexSet();
    }

    public ColoredGraph<V> getGraph(){
        return this.graph;
    }

    /**
     * get the color at a specified cell
     * @param vertex the vertex
     * @return the current color of that cell
     * @throws IllegalArgumentException if the index is out of bound
     */
    public Color getColorAt(V vertex){
        int colorId = graph.getVertexColor(vertex);
        return ColorRepository.getInstance().getColor(colorId);
    }

    /**
     * get the number of moves remaining before losing the game
     * @return the number of moves remaining before losing the game
     */
    public int numMoveRemaining() {
        return hints.size() - curNumTurn;
    }

    /**
     * switch to a different color within the color palette of the grid
     * get the color palette by calling getColors()
     * @param color the new color
     * @throws IllegalArgumentException if the color does not exist within palette, or it is the same color as the previous one
     */
    public void switchColor(Color color){
        this.curColor = color;
        notifyObservers();
    }

    /**
     * get the current color that the level is using
     * @return the current color
     */
    public Color getCurrentColor(){
        return this.curColor;
    }

    /**
     * play a move on the level
     * if the current color is different from the color in the move
     * switch the color to that color given that the move's color is in the grid
     * @param move the move on the level
     * @throws IllegalArgumentException if the color of the move is not in the grid
     * @throws IllegalArgumentException index out of bound
     */
    public void play(Move<V> move){
        if (numMoveRemaining() == 0){
            throw new IllegalArgumentException("No move left");
        }
        graph.colorFloodFill(move.getVertex(), move.getColor().getColorId());
        ++curNumTurn;
        notifyObservers();
    }

    /**
     * get the color palette of this level
     * @return List of colors
     */
    public List<Color> getColors(){
        return ColorRepository.getInstance().listColors(graph.getColorIds());
    }

    /**
     * return hints to solve this level
     * @return the hints
     */
    public List<Move<V>> getHints(){
        return hints;
    }

    /**
     * get the current level's state
     * either ONGOING, WIN, LOSE
     * @return the level's current state
     */
    public LevelState getLevelState(){
        boolean isMono = this.getGraph().pruneGraph().getNumVertices() == 1;
        if(!isMono){
            if(numMoveRemaining() > 0) {
                return LevelState.ONGOING;
            }
            return LevelState.LOSE;
        }
        return LevelState.WIN;


    }

}
