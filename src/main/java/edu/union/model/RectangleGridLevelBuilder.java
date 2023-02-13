package edu.union.model;

import java.awt.*;

public class RectangleGridLevelBuilder extends LevelBuilder<RectangleGridCell>{

    public final String TYPE = "Rectangle"
    /**
     * basic initialization calling Observable constructor
     *
     * @param graph A
     */
    public RectangleGridLevelBuilder(int rows, int cols) {
        super(new ColoredGraph<RectangleGridCell>());
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                graph.addVertex(new RectangleGridCell(i,j),0);
            }
        }
    }

    @Override
    public String getType(){
        return TYPE;
    }
}
