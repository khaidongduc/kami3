package edu.union;

import edu.union.model.*;
import edu.union.service.LevelRepositoryManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColoredGraphTests {

    private ColoredGraph<RectangleGridCell> graph;
    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);


    @Before
    public void setUp(){
        red.setColorId(0);
        green.setColorId(1);
        blue.setColorId(2);
        light_blue.setColorId(3);
        graph.addVertex(new RectangleGridCell(0, 0), red.getColorId());
        graph.addVertex(new RectangleGridCell(0, 1), green.getColorId());
        graph.addVertex(new RectangleGridCell(0, 2), blue.getColorId());
        graph.addVertex(new RectangleGridCell(0, 3), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(0, 4), red.getColorId());
        graph.addVertex(new RectangleGridCell(1, 0), green.getColorId());
        graph.addVertex(new RectangleGridCell(1, 1), blue.getColorId());
        graph.addVertex(new RectangleGridCell(1, 2), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(1, 3), red.getColorId());
        graph.addVertex(new RectangleGridCell(1, 4), green.getColorId());
    }

    @After
    public void tearDown(){graph = null;}

    @Test
    public void ColoredGraphTest_Default(){
        ColoredGraph<RectangleGridCell> defaultGraph = new ColoredGraph<>();
        assertTrue(defaultGraph.getVertexSet().isEmpty());
        assertTrue(defaultGraph.getColorIds().isEmpty());
    }
}
