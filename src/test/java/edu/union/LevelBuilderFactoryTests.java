package edu.union;

import edu.union.model.*;
import edu.union.service.ColorRepository;
import edu.union.service.LevelBuilderFactory;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class LevelBuilderFactoryTests {
    private LevelBuilderFactory builderFactory;

    @Before
    public void setUp(){
        ColorRepository colorRepository = ColorRepository.getInstance();
        for(Color color: Config.DEFAULT_COLORS)
            colorRepository.addColor(color);

        builderFactory = LevelBuilderFactory.getInstance();
    }

    @After
    public void tearDown(){
        builderFactory = null;
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.clear();
    }

    @Test
    public void testRegister() throws Exception {
        LevelBuilderFactory.getInstance().register(LevelType.RECTANGLE_GRID_LEVEL, new RectangleGridLevelBuilderStub(5, 5));
        Object builder = LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
        assertSame(RectangleGridLevelBuilderStub.levelBuilder, builder);
    }

    @Test(expected = Exception.class)
    public void testCreateLevelBuilder_NoKey() throws Exception {
        RectangleGridLevelBuilder builder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
    }
}
