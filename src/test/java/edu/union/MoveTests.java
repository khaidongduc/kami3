package edu.union;
import edu.union.model.Color;
import edu.union.model.ColorGrid;
import edu.union.model.ColorRepository;
import edu.union.model.Move;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MoveTests {
    private Move move;

    @Before
    public void setUp(){move = new Move( new Color(0, 255, 0), 0, 0);}

    @After
    public void tearDown(){move = null;}

    @Test
    public void testGetColor(){
        assertEquals(new Color(0, 255, 0), move.getColor());
    }

    @Test
    public void testGetRow(){
        assertEquals(0, move.getRow());
    }

    @Test
    public void testGetCol(){
        assertEquals(0, move.getCol());
    }
}
