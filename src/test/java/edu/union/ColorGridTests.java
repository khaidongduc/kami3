package edu.union;

import edu.union.model.ColorGrid;
import edu.union.service.ColorRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColorGridTests {
    private ColorGrid c;

    @Before
    public void setUp(){c = new ColorGrid(2, 3);}

    @After
    public void tearDown(){c = null;}

    @Test
    public void testConstructDefault(){
        ColorGrid grid = new ColorGrid();
        assertEquals(0, grid.getNumCols());
        assertEquals(0, grid.getNumRows());
    }

    @Test
    public void testConstruct(){
        assertEquals(3, c.getNumCols());
        assertEquals(2, c.getNumRows());
    }

    @Test
    public void testSet_GetColor(){
        ColorRepository repo = ColorRepository.getInstance();
        c.setColor(2, 0, 0);
        assertEquals(2, c.getColorOfEntry(0, 0));

        c.setColor(1, 1, 1);
        assertEquals(1, c.getColorOfEntry(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColor_IndexOutOfBounds(){
        c.setColor(2, 5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColor_SameColor(){
        c.setColor(0, 0, 0);
    }


    @Test
    public void testSetColorFlood(){
        c.setColor(2, 0, 0);
        c.setColorFlood(1, 0, 1);

        assertEquals(2, c.getColorOfEntry(0, 0));
        assertEquals(1, c.getColorOfEntry(0, 1));
        assertEquals(1, c.getColorOfEntry(0, 2));
        assertEquals(1, c.getColorOfEntry(1, 0));
        assertEquals(1, c.getColorOfEntry(1, 1));
        assertEquals(1, c.getColorOfEntry(1, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColorFlood_IndexOutOfBounds(){
        c.setColorFlood(2, 5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetColorFlood_SameColor(){
        c.setColorFlood(0, 0, 0);
    }

    @Test
    public void testGetAvailableColorIds(){
        c.setColor(1, 0, 1);
        c.setColor(2, 0, 2);
        c.setColor(3, 1, 0);

        ArrayList<Integer> expectedIds = new ArrayList<>();
        expectedIds.add(0);
        expectedIds.add(1);
        expectedIds.add(2);
        expectedIds.add(3);

        Set<Integer> ids = c.getAvailableColorIds();
        assertTrue(setContains(expectedIds, ids));
        assertFalse(ids.contains(4));

    }

    private boolean setContains(ArrayList<Integer> a, Set<Integer> s){
        for (int i : s){
            if (!a.contains(i)){
                return false;
            }
        }
        return true;
    }
}
