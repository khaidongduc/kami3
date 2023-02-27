package edu.union;
import edu.union.model.*;
import edu.union.service.ColorRepository;
import edu.union.service.LevelBuilderFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RectangleGridLevelBuilderTests {
    private RectangleGridLevelBuilder builder;
    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);

    @Before
    public void setUp() throws Exception {
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.addColor(red);
        colorRepository.addColor(green);
        colorRepository.addColor(blue);
        colorRepository.addColor(light_blue);

        LevelBuilderFactory.getInstance().register(LevelType.RECTANGLE_GRID_LEVEL,
                new RectangleGridLevelBuilder(5, 5));
        builder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
    }

    @After
    public void tearDown(){
        builder = null;
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.clear();
    }

    @Test
    public void testLevelBuilder(){
        assertEquals(5, builder.getCols());
        assertEquals(5, builder.getRows());
        List<Color> colors = builder.getColors();
        assertTrue(colors.contains(red));
        assertTrue(colors.contains(green));
        assertTrue(colors.contains(blue));
        assertTrue(colors.contains(light_blue));
    }

    @Test
    public void testGetCurrentColor(){
        assertEquals(red, builder.getCurrentColor());
        builder.switchColor(green);
        assertEquals(green, builder.getCurrentColor());
    }

    @Test
    public void testSetColor(){
        builder.setColor(green, new RectangleGridCell(0, 0));
        assertEquals(green, builder.getColorAt(new RectangleGridCell(0, 0)));

        builder.setColor(blue, new RectangleGridCell(0, 0));
        assertEquals(blue, builder.getColorAt(new RectangleGridCell(0, 0)));
    }

    @Test
    public void testChangeGridSize(){
        builder.changeGridSize(2, 3);
        ColoredGraph<RectangleGridCell> graph = builder.getGraph();
        assertEquals(3, builder.getCols());
        assertEquals(2, builder.getRows());
    }

    @Test
    public void testRestart(){
        for (int i = 0; i <builder.getCols(); i++){
            builder.setColor(green, new RectangleGridCell(0, i));
        }
        for (int i = 0; i <builder.getCols(); i++){
            builder.setColor(blue, new RectangleGridCell(1, i));
        }
        for (int i = 0; i <builder.getCols(); i++){
            builder.setColor(light_blue, new RectangleGridCell(2, i));
        }

        builder.restart();
        for (int i = 0; i < builder.getRows(); i++){
            for (int j = 0; j < builder.getCols(); j++){
                assertEquals(red, builder.getColorAt(new RectangleGridCell(i, j)));
            }
        }
    }
}
