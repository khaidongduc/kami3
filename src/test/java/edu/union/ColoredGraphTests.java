package edu.union;

import edu.union.model.*;
import edu.union.service.ColorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
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

        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.addColor(red);
        colorRepository.addColor(green);
        colorRepository.addColor(blue);
        colorRepository.addColor(light_blue);

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
        graph = null;
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.clear();
    }

    @Test
    public void testColoredGraph_Default(){
        ColoredGraph<RectangleGridCell> defaultGraph = new ColoredGraph<>();
        assertTrue(defaultGraph.getVertexSet().isEmpty());
        assertTrue(defaultGraph.getColorIds().isEmpty());
    }

    @Test
    public void testColoredGraph(){
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
    public void testPruneGraph(){
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

    @Test
    public void testRemoveEdge(){
        graph.removeEdge(new RectangleGridCell(0, 0), new RectangleGridCell(0, 1));
        Set<RectangleGridCell> neighbors = graph.getNeighbors(new RectangleGridCell(0, 0));
        assertFalse(neighbors.contains(new RectangleGridCell(0, 1)));
    }

    @Test
    public void testGetVertexColor(){
        int colorId = graph.getVertexColor(new RectangleGridCell(0, 0));
        assertEquals(red.getColorId(), colorId);
    }

    @Test
    public void testSetVertexColor(){
        graph.setVertexColor(new RectangleGridCell(0, 0), green.getColorId());
        int colorId = graph.getVertexColor(new RectangleGridCell(0, 0));
        assertEquals(green.getColorId(), colorId);
    }

    @Test
    public void testGetNumOfVertices(){
        assertEquals(10, graph.getNumVertices());
    }

    @Test
    public void testBuildGraphWithAdjacency(){
        ColoredGraph<RectangleGridCell> smallGraph = new ColoredGraph<>();
        smallGraph.addVertex(new RectangleGridCell(0, 0), red.getColorId());
        smallGraph.addVertex(new RectangleGridCell(0, 1), red.getColorId());
        smallGraph.addVertex(new RectangleGridCell(0, 2), green.getColorId());
        smallGraph.buildGraphWithAdjacency();

        Set<RectangleGridCell> neighbors00 = graph.getNeighbors(new RectangleGridCell(0, 0));
        assertTrue(neighbors00.contains(new RectangleGridCell(0, 1)));

        Set<RectangleGridCell> neighbors01 = graph.getNeighbors(new RectangleGridCell(0, 1));
        assertTrue(neighbors01.contains(new RectangleGridCell(0, 0)));
        assertTrue(neighbors01.contains(new RectangleGridCell(0, 2)));

        Set<RectangleGridCell> neighbors02 = graph.getNeighbors(new RectangleGridCell(0, 2));
        assertTrue(neighbors02.contains(new RectangleGridCell(0, 1)));
    }

    @Test
    public void testColorFloodFill(){
        graph.colorFloodFill(new RectangleGridCell(0, 0), green.getColorId());

        for(int i = 0; i < 5; i++){
            assertEquals(graph.getVertexColor(new RectangleGridCell(0, i)), green.getColorId());
        }
    }

    @Test
    public void testGetColorIds(){
        List<Integer> colorIds = graph.getColorIds();
        assertTrue(colorIds.contains(red.getColorId()));
        assertTrue(colorIds.contains(green.getColorId()));
        assertTrue(colorIds.contains(blue.getColorId()));
        assertFalse(colorIds.contains(light_blue.getColorId()));
    }
}
