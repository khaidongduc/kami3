package edu.union;

import edu.union.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RectangleGridCellTests {
    private RectangleGridCell cell;
    @Before
    public void setUp(){
        cell = new RectangleGridCell(1,1);
    }

    @After
    public void tearDown(){cell = null;}

    @Test
    public void testAdjacentTo(){
        RectangleGridCell neighbor0 = new RectangleGridCell(0, 0);
        RectangleGridCell neighbor1 = new RectangleGridCell(1, 0);
        RectangleGridCell neighbor2 = new RectangleGridCell(0, 1);
        RectangleGridCell neighbor3 = new RectangleGridCell(1, 2);
        RectangleGridCell neighbor4 = new RectangleGridCell(2, 1);

        assertTrue(cell.adjacentTo(neighbor1));
        assertTrue(cell.adjacentTo(neighbor2));
        assertTrue(cell.adjacentTo(neighbor3));
        assertTrue(cell.adjacentTo(neighbor4));
        assertFalse(cell.adjacentTo(neighbor0));

    }
}
