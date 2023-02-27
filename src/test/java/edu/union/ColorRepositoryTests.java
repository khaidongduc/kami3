package edu.union;

import edu.union.model.Color;
import edu.union.service.ColorRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ColorRepositoryTests {
    private ColorRepository c;

    @Before
    public void setUp(){
        c = ColorRepository.getInstance();
        for(Color color: Config.DEFAULT_COLORS)
            c.addColor(color);
    }

    @After
    public void tearDown(){
        c.clear();
        c = null;
    }

    @Test
    public void testConstruct(){
        List<Color> repo = c.listColors();
        Color color1 = new Color(255, 0, 0);
        Color color2 = new Color(0, 255, 0);
        Color color3 = new Color(0, 0, 255);
        Color color4 = new Color(0, 255, 255);

        assertTrue(repo.contains(color1) && repo.contains(color2)
                && repo.contains(color3) && repo.contains(color4));
    }

    @Test
    public void testListColors_Default(){
        Color color0 = new Color(5, 5, 5);
        c.addColor(color0);
        List<Color> repo = c.listColors();

        assertTrue(repo.contains(color0));
        assertFalse(repo.contains(new Color(20, 20, 20)));
    }

    @Test
    public void testListColors_Ids(){
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<Color> repo = c.listColors(ids);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        colors.add(new Color(0, 255, 0));
        colors.add(new Color(0, 0, 255));
        colors.add(new Color(0, 255, 255));

        assertTrue(repo.containsAll(colors));
    }

    @Test
    public void testAddColor(){
        Color black = new Color(255, 255, 255);
        c.addColor(black);
        List<Color> repo = c.listColors();
        assertTrue(repo.contains(black));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddColor_AlreadyExists(){
        Color red = new Color(255, 0, 0);
        c.addColor(red);
    }

    @Test
    public void testGetColor(){
        Color color0 = c.getColor(0);
        Color color1 = c.getColor(1);
        Color color2 = c.getColor(2);
        Color color3 = c.getColor(3);

        assertEquals(color0, new Color(255, 0, 0));
        assertEquals(color1, new Color(0, 255, 0));
        assertEquals(color2, new Color(0, 0, 255));
        assertEquals(color3, new Color(0, 255, 255));
    }
}
