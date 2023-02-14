package edu.union;

import edu.union.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColoredGraphTests {

    private ColoredGraph<RectangleGridCell> graphTemplate = new ColoredGraph<>();
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
        graphTemplate.addVertex(new RectangleGridCell(0, 0), red.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(0, 1), red.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(0, 2), red.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(0, 3), red.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(0, 4), red.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(1, 0), green.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(1, 1), green.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(1, 2), green.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(1, 3), blue.getColorId());
        graphTemplate.addVertex(new RectangleGridCell(1, 4), blue.getColorId());
        graphTemplate.buildGraphWithAdjacency();
        graph = new ColoredGraph<>(graphTemplate);
    }

    @After
    public void tearDown(){
        graph = null;}

    @Test
    public void testColoredGraph_Default(){
        ColoredGraph<RectangleGridCell> defaultGraph = new ColoredGraph<>();
        assertTrue(defaultGraph.getVertexSet().isEmpty());
        assertTrue(defaultGraph.getColorIds().isEmpty());
    }

    @Test
    public void testColoredGraph(){
        assertEquals(10, graph.getNumVertices());
        Set<RectangleGridCell> verts = graph.getVertexSet();
        assertTrue(verts.contains(new RectangleGridCell(0, 0)));
        assertTrue(verts.contains(new RectangleGridCell(0, 1)));
        assertTrue(verts.contains(new RectangleGridCell(0, 2)));
        assertTrue(verts.contains(new RectangleGridCell(0, 3)));
        assertTrue(verts.contains(new RectangleGridCell(0, 4)));
        assertTrue(verts.contains(new RectangleGridCell(1, 0)));
        assertTrue(verts.contains(new RectangleGridCell(1, 1)));
        assertTrue(verts.contains(new RectangleGridCell(1, 2)));
        assertTrue(verts.contains(new RectangleGridCell(1, 3)));
        assertTrue(verts.contains(new RectangleGridCell(1, 4)));
    }

    @Test
    public void testGetNeighbors(){
        Set<RectangleGridCell> neighbors00 = graph.getNeighbors(new RectangleGridCell(0, 0));
        assertTrue(neighbors00.contains(new RectangleGridCell(1, 0)));
        assertTrue(neighbors00.contains(new RectangleGridCell(0, 1)));

        Set<RectangleGridCell> neighbors02 = graph.getNeighbors(new RectangleGridCell(0, 2));
        assertTrue(neighbors02.contains(new RectangleGridCell(1, 2)));
        assertTrue(neighbors02.contains(new RectangleGridCell(0, 1)));
        assertTrue(neighbors02.contains(new RectangleGridCell(0, 3)));
    }

    @Test
    public void testAddVertex(){
        graph.addVertex(new RectangleGridCell(2, 0), red.getColorId());
        Set<RectangleGridCell> verts = graph.getVertexSet();
        assertTrue(verts.contains(new RectangleGridCell(2, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVertex_AlreadyExists(){
        graph.addVertex(new RectangleGridCell(0, 0), red.getColorId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVertex_InvalidColor(){
        graph.addVertex(new RectangleGridCell(2, 0), -1);
    }

    @Test
    public void testPrune(){
        ColoredGraph<RectangleGridCell> prunedGraph = graph.pruneGraph();
        assertEquals(3, prunedGraph.getNumVertices());
        assertEquals(3, prunedGraph.getColorIds().size());
    }

    @Test
    public void testAddEdge(){
        graph.addVertex(new RectangleGridCell(2, 0), red.getColorId());
        graph.addVertex(new RectangleGridCell(2, 1), red.getColorId());
        graph.addEdge(new RectangleGridCell(2, 0), new RectangleGridCell(2, 1));

        Set<RectangleGridCell> neighbors = graph.getNeighbors(new RectangleGridCell(2, 0));
        assertTrue(neighbors.contains(new RectangleGridCell(2, 1)));
    }
}
