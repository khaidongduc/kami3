package edu.union;
import edu.union.model.Color;
import edu.union.model.Move;
import edu.union.model.RectangleGridCell;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MoveTests {
    private Move<RectangleGridCell> move;

    @Before
    public void setUp(){move = new Move<RectangleGridCell>( new Color(0, 255, 0), new RectangleGridCell(0, 0));}

    @After
    public void tearDown(){move = null;}

    @Test
    public void testGetColor(){
        assertEquals(new Color(0, 255, 0), move.getColor());
    }

    @Test
    public void testGetRow(){
        RectangleGridCell cell = (RectangleGridCell) move.getVertex();
        assertEquals(0, cell.row);
    }

    @Test
    public void testGetCol(){
        RectangleGridCell cell = (RectangleGridCell) move.getVertex();
        assertEquals(0, cell.col);
    }
}
