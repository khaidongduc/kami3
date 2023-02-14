package edu.union;

import edu.union.model.*;
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
        builderFactory = LevelBuilderFactory.getInstance();
    }

    @After
    public void tearDown(){builderFactory = null;}

    @Test
    public void testRegister() throws Exception {
        LevelBuilderFactory.getInstance().register(LevelType.RECTANGLE_GRID_LEVEL, new RectangleGridLevelBuilder(5, 5));
        RectangleGridLevelBuilder builder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
    }

    @Test(expected = Exception.class)
    public void testCreateLevelBuilder_NoKey() throws Exception {
        RectangleGridLevelBuilder builder = (RectangleGridLevelBuilder) LevelBuilderFactory.getInstance().createLevelBuilder(LevelType.RECTANGLE_GRID_LEVEL);
    }
}
