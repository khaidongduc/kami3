package edu.union;
import edu.union.model.*;
import edu.union.service.LevelRepository;
import edu.union.service.RawTextLevelRepositoryStrategy;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelBuilderImplTests {
    private LevelBuilder builder;
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
        builder = new LevelBuilderImpl(4, 6);
    }

    @After
    public void tearDown(){builder = null;}

    @Test
    public void testLevelBuilder_Default(){
        LevelBuilder levelBuilder = new LevelBuilderImpl();
        assertEquals(5, levelBuilder.getNumCols());
        assertEquals(5, levelBuilder.getNumRows());
        List<Color> colors = levelBuilder.getColors();
        assertTrue(colors.contains(red));
        assertFalse(colors.contains(green));
        assertFalse(colors.contains(blue));
        assertFalse(colors.contains(light_blue));
    }

    @Test
    public void testLevelBuilder(){
        assertEquals(6, builder.getNumCols());
        assertEquals(4, builder.getNumRows());
        List<Color> colors = builder.getColors();
        assertTrue(colors.contains(red));
        assertFalse(colors.contains(green));
        assertFalse(colors.contains(blue));
        assertFalse(colors.contains(light_blue));
    }

    @Test
    public void testGetCurrentColor(){
        assertEquals(red, builder.getCurrentColor());
        builder.switchColor(green);
        assertEquals(green, builder.getCurrentColor());
    }

    @Test
    public void testSetColor(){
        builder.setColor(green, 0, 0);
        assertEquals(green, builder.getColorAt(0, 0));

        builder.setColor(blue, 0, 0);
        assertEquals(blue, builder.getColorAt(0, 0));
    }

    @Test
    public void testChangeGridSize(){
        builder.changeGridSize(2, 3);
        ColorGrid grid = builder.getGrid();
        assertEquals(3, grid.getNumCols());
        assertEquals(2, grid.getNumRows());

        assertEquals(3, builder.getNumCols());
        assertEquals(2, builder.getNumRows());
    }

    @Test
    public void testRestart(){
        for (int i = 0; i <builder.getNumCols(); i++){
            builder.setColor(green, 0, i);
        }
        for (int i = 0; i <builder.getNumCols(); i++){
            builder.setColor(blue, 1, i);
        }
        for (int i = 0; i <builder.getNumCols(); i++){
            builder.setColor(light_blue, 2, i);
        }

        builder.restart();
        for (int i = 0; i < builder.getNumRows(); i++){
            for (int j = 0; j < builder.getNumCols(); j++){
                assertEquals(red, builder.getColorAt(i, j));
            }
        }
    }
}
