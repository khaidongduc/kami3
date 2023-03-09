package edu.union;

import edu.union.model.ColoredGraph;
import edu.union.model.LevelBuilder;
import edu.union.model.LevelType;
import edu.union.model.RectangleGridLevelBuilder;

public class RectangleGridLevelBuilderStub extends RectangleGridLevelBuilder {
    /**
     * basic initialization calling Observable constructor
     *
     * @param rows : The number of rows in the builder graph.
     * @param cols : The number of columns in the builder graph.
     */

    public  static final LevelBuilder levelBuilder = new LevelBuilder(new ColoredGraph()) {
        @Override
        public String getLevelType() {
            return null;
        }
    };

    public RectangleGridLevelBuilderStub(int rows, int cols) {
        super(rows, cols);
    }

    public String getLevelType() {
        return LevelType.RECTANGLE_GRID_LEVEL;
    }

    @Override
    public Object clone(){
        return levelBuilder;
    }
}
