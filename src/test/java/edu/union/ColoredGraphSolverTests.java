package edu.union;

import edu.union.model.*;
import edu.union.service.ColoredGraphSolver;
import edu.union.service.LevelRepositoryManager;
import edu.union.service.RectangleGridLevelRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColoredGraphSolverTests {
    private ColoredGraphSolver solver = ColoredGraphSolver.getInstance();
    private ColoredGraph<RectangleGridCell> graph;
    private RectangleGridLevel level;
    private LevelRepositoryManager repositoryManager;

    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);


    @Before
    public void setUp(){
        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, RectangleGridLevelRepository.getInstance());
        levelRepositoryManager.setFolderPath("build/resources/test/edu.union/level");
        repositoryManager = levelRepositoryManager;
        red.setColorId(0);
        green.setColorId(1);
        blue.setColorId(2);
        light_blue.setColorId(3);
        graph = new ColoredGraph<>();
    }

    @After
    public void tearDown(){graph = null;}

    @Test
    public void testSolver_MonochromaticGrid(){
        ColoredGraph<RectangleGridCell> graph = new ColoredGraph<>();
        List<Move<RectangleGridCell>> hints = solver.solveColoredGraph(graph);
        assertTrue(hints.isEmpty());
    }

    @Test
    public void testSolver_1(){

        level = (RectangleGridLevel) repositoryManager.loadLevel(new LevelInfo(1, LevelType.RECTANGLE_GRID_LEVEL, "build/resources/test/edu.union/level/1"));

        List<Move<RectangleGridCell>> hints = level.getHints();
        for (Move<RectangleGridCell> hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    @Test
    public void testSolver_2(){
        level = (RectangleGridLevel) repositoryManager.loadLevel(new LevelInfo(2, LevelType.RECTANGLE_GRID_LEVEL, "build/resources/test/edu.union/level/2"));

        List<Move<RectangleGridCell>> hints = level.getHints();
        for (Move<RectangleGridCell> hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    @Test
    public void testSolver_3(){
        level = (RectangleGridLevel) repositoryManager.loadLevel(new LevelInfo(30, LevelType.RECTANGLE_GRID_LEVEL, "build/resources/test/edu.union/level/30"));

        List<Move<RectangleGridCell>> hints = level.getHints();
        for (Move<RectangleGridCell> hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    @Test(expected = RuntimeException.class)
    public void testSolver_ManyChanges(){
        solver.setMaxNumSteps(5);
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
        graph.addVertex(new RectangleGridCell(2, 0), blue.getColorId());
        graph.addVertex(new RectangleGridCell(2, 1), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(2, 2), red.getColorId());
        graph.addVertex(new RectangleGridCell(2, 3), green.getColorId());
        graph.addVertex(new RectangleGridCell(2, 4), blue.getColorId());
        graph.addVertex(new RectangleGridCell(3, 0), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(3, 1), red.getColorId());
        graph.addVertex(new RectangleGridCell(3, 2), green.getColorId());
        graph.addVertex(new RectangleGridCell(3, 3), blue.getColorId());
        graph.addVertex(new RectangleGridCell(3, 4), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(4, 0), red.getColorId());
        graph.addVertex(new RectangleGridCell(4, 1), green.getColorId());
        graph.addVertex(new RectangleGridCell(4, 2), blue.getColorId());
        graph.addVertex(new RectangleGridCell(4, 3), light_blue.getColorId());
        graph.addVertex(new RectangleGridCell(4, 4), red.getColorId());
        graph.buildGraphWithAdjacency();
        List<Move<RectangleGridCell>> hints = solver.solveColoredGraph(graph);
    }
}
