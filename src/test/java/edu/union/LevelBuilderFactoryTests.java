package edu.union;

import edu.union.model.Color;
import edu.union.model.ColoredGraph;
import edu.union.model.Move;
import edu.union.model.RectangleGridCell;
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
}
