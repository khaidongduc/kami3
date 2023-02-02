import model.Color;
import model.ColorGrid;
import model.ColorRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

public class ColorGridTests {
    private ColorGrid c;

    @Before
    public void setUp(){c = new ColorGrid(3, 4);}

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
        assertEquals(4, c.getNumCols());
        assertEquals(3, c.getNumRows());
    }

    @Test
    public void testSetGetColor(){
        ColorRepository repo = ColorRepository.getInstance();
        c.setColor(0, 0, 0);
        assertEquals(0, c.getColorOfEntry(0, 0));

        c.setColor(2, 1, 1);
        assertEquals(2, c.getColorOfEntry(1, 1));
    }

    @Test
    public void testSetColorFlood(){
        c.setColor(0, 0, 0);
        c.setColorFlood(1, 0, 1);

        assertEquals(1, c.getColorOfEntry(4, 4));
    }
}
