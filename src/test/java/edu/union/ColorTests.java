package edu.union;

import edu.union.model.Color;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColorTests {
    private Color c;

    @Before
    public void setUp(){c = new Color(5, 7, 22);}

    @After
    public void tearDown(){c = null;}

    @Test(expected = RuntimeException.class)
    public void constructTest(){
        Color c = new Color(-5, 5, 5);
    }

    @Test
    public void testGetRValue(){
        int redValue = c.getRValue();
        assertEquals(5, redValue);
    }

    @Test
    public void testGetBValue(){
        int blueValue = c.getBValue();
        assertEquals(22, blueValue);
    }

    @Test
    public void testGetGValue(){
        int greenValue = c.getGValue();
        assertEquals(7, c.getGValue());
    }

    @Test
    public void testEquals(){
        Color c2 = new Color(5, 7, 22);
        assertEquals("Expect the colors to be equal", c, c2);

        Color c3 = new Color(2, 3, 4);
        assertNotEquals("The colors should not be equal to one another",
                c, c3);
    }
}
