package edu.union;

import edu.union.model.Color;
import edu.union.model.LevelInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

    public static final int RECT_LEVEL_BUILDER_ROWS = 5;
    public static final int RECT_LEVEL_BUILDER_COLS = 5;

    public static final Color[] DEFAULT_COLORS = new Color[]{
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(0, 255, 255)
    };

}
