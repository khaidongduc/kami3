package edu.union;

import edu.union.model.*;
import edu.union.service.LevelRepository;
import edu.union.service.LevelSolver;
import edu.union.service.RawTextLevelRepositoryStrategy;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelSolverTests {
    private LevelSolver solver = LevelSolver.getInstance();
    private ColorGrid grid;
    private Level level;
    private RawTextLevelRepositoryStrategy repoStrat;

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
        grid = new ColorGrid(5, 5);
    }

    @After
    public void tearDown(){grid = null;}

    @Test
    public void testSolver_MonochromaticGrid(){
        ColorGrid grid1 = new ColorGrid();
        List<Move> hints = solver.solveColorGrid(grid1);
        assertTrue(hints.isEmpty());
    }

    @Test
    public void testSolver_1(){
        repoStrat = new RawTextLevelRepositoryStrategy();
        repoStrat.setFolderPath("build/resources/test/edu.union/level");
        LevelRepository.getInstance().setLevelRepositoryStrategy(repoStrat);
        level = LevelRepository.getInstance().loadLevel(new LevelInfo(1));

        List<Move> hints = level.getHints();
        for (Move hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    @Test
    public void testSolver_2(){
        repoStrat = new RawTextLevelRepositoryStrategy();
        repoStrat.setFolderPath("build/resources/test/edu.union/level");
        LevelRepository.getInstance().setLevelRepositoryStrategy(repoStrat);
        level = LevelRepository.getInstance().loadLevel(new LevelInfo(2));

        List<Move> hints = level.getHints();
        for (Move hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    @Test
    public void testSolver_3(){
        repoStrat = new RawTextLevelRepositoryStrategy();
        repoStrat.setFolderPath("build/resources/test/edu.union/level");
        LevelRepository.getInstance().setLevelRepositoryStrategy(repoStrat);
        level = LevelRepository.getInstance().loadLevel(new LevelInfo(30));

        List<Move> hints = level.getHints();
        for (Move hint : hints) {
            level.play(hint);
        }
        LevelState win = LevelState.WIN;
        assertEquals(win, level.getLevelState());
    }

    // this one is failing right now, but we'll need to add the exception to
    // this so laptops don't die when they try to run this
    @Test(expected = IllegalArgumentException.class)
    public void testSolver_ManyChanges(){
        grid.setColor(green.getColorId(), 0, 1);
        grid.setColor(blue.getColorId(), 0, 2);
        grid.setColor(light_blue.getColorId(), 0, 3);
        grid.setColor(green.getColorId(), 0, 4);
        grid.setColor(blue.getColorId(), 1, 1);
        grid.setColor(light_blue.getColorId(), 1, 2);
        grid.setColor(green.getColorId(), 1, 3);
        grid.setColor(blue.getColorId(), 1, 4);
        grid.setColor(light_blue.getColorId(), 2, 1);
        grid.setColor(green.getColorId(), 2, 2);
        grid.setColor(blue.getColorId(), 2, 3);
        grid.setColor(light_blue.getColorId(), 2, 4);
        grid.setColor(green.getColorId(), 3, 1);
        grid.setColor(blue.getColorId(), 3, 2);
        grid.setColor(light_blue.getColorId(), 3, 3);
        grid.setColor(green.getColorId(), 3, 4);
        grid.setColor(blue.getColorId(), 4, 1);
        grid.setColor(light_blue.getColorId(), 4, 2);
        grid.setColor(green.getColorId(), 4, 3);
        grid.setColor(blue.getColorId(), 4, 4);
        List<Move> hints = solver.solveColorGrid(grid);
    }
}
