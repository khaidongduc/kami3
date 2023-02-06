package edu.union;

import edu.union.model.Color;
import edu.union.model.ColorGrid;
import edu.union.model.LevelImpl;
import edu.union.model.LevelInfo;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelImplTests {
    private LevelImpl level;
    private ColorGrid grid;
    private Color color;

    @Before
    public void setUp(){
        grid = new ColorGrid(5, 5);
        level = new LevelImpl(grid, color, 3, new LevelInfo(1));
    }

    @After
    public void tearDown(){level = null;}

    @Test
    public void testConstruct(){
        System.out.println(level);
        assertEquals(5, level.getNumRows());
        assertEquals(5, level.getNumCols());
        assertEquals(3, level.numMoveRemaining());
    }

    //@Test(expected = RuntimeException.class)
    //public void testConstruct_UnknownLevel(){
    //    LevelImpl level = new LevelImpl(Integer.MAX_VALUE);
    //}
}
