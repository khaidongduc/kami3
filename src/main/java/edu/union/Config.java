package edu.union;

import edu.union.model.Color;
import edu.union.model.LevelInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

    public static final Color[] DEFAULT_COLORS = new Color[]{
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(0, 255, 255)
    };

    public static final ArrayList<LevelInfo> levelInfos = new ArrayList<LevelInfo>(
        Arrays.asList(
            new LevelInfo(1, "RectangleGridLevel",
            "C:\\Users\\khaid\\projs\\csc-260\\kami3\\src\\main\\resources\\edu\\union\\model\\levels\\text\\1")
        )
    );

}
