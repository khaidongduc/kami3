package edu.union;

import edu.union.model.*;
import edu.union.service.ColorRepository;
import edu.union.service.LevelRepositoryManager;
import edu.union.service.TextRectangleGridLevelRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RectangleGridLevelTests {
    private RectangleGridLevel level;
    private LevelRepositoryManager repositoryManager;

    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);


    @Before
    public void setUp(){
        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, TextRectangleGridLevelRepository.getInstance());

        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.addColor(red);
        colorRepository.addColor(green);
        colorRepository.addColor(blue);
        colorRepository.addColor(light_blue);

        repositoryManager = LevelRepositoryManager.getInstance();
        repositoryManager.setFolderPath("build/resources/test/edu.union/level");
        level = (RectangleGridLevel) repositoryManager.loadLevel(new LevelInfo(1, LevelType.RECTANGLE_GRID_LEVEL, "build/resources/test/edu.union/level/1.rectgrl"));
    }

    @After
    public void tearDown(){
        level = null;
        ColorRepository.getInstance().clear();
    }

    @Test
    public void testConstruct(){
        assertEquals(5, level.getNumRows());
        assertEquals(5, level.getNumCols());
        assertEquals(3, level.numMoveRemaining());
    }

    @Test
    public void testGetColorAt(){
        for(int i = 0; i < 5; i++) {
            assertEquals(red, level.getColorAt(new RectangleGridCell(0, i)));
        }

        for (int i = 1; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(green, level.getColorAt(new RectangleGridCell(i, j)));
            }
        }

        for (int i = 1; i < 3; i++){
            for (int j = 3; j < 5; j++){
                assertEquals(blue, level.getColorAt(new RectangleGridCell(i, j)));
            }
        }

        for (int i = 3; i < 5; i++){
            for (int j = 0; j < 5; j++){
                assertEquals(light_blue, level.getColorAt(new RectangleGridCell(i, j)));
            }
        }
    }

    @Test
    public void testSwitchColor(){
        level.switchColor(green);
        assertEquals(green, level.getCurrentColor());
    }

    @Test
    public void testSwitchColor_Invalid(){
        Color random = new Color(5, 5, 5);
        level.switchColor(random);
        assertEquals(random,level.getCurrentColor());
    }

    @Test
    public void testGetCurrentColor(){
        assertEquals(red, level.getCurrentColor());

        level.switchColor(green);
        assertEquals(green, level.getCurrentColor());
    }

    @Test
    public void testPlay(){
        Move<RectangleGridCell> move = new Move(blue, new RectangleGridCell(0, 0));
        assertEquals(3, level.numMoveRemaining());
        level.play(move);
        for(int i = 0; i < 5; i++) {
            assertEquals(blue, level.getColorAt(new RectangleGridCell(0, i)));
        }

        assertEquals(2, level.numMoveRemaining());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testPlay_noMovesLeft(){
        Move<RectangleGridCell> move1 = new Move(green, new RectangleGridCell(0, 0));
        level.play(move1);
        Move<RectangleGridCell> move2 = new Move(red, new RectangleGridCell(0, 1));
        level.play(move2);
        Move<RectangleGridCell> move3 = new Move(green, new RectangleGridCell(0, 2));
        level.play(move3);
        Move<RectangleGridCell> move4 = new Move(red, new RectangleGridCell(0, 3));
        level.play(move4);
    }

    @Test
    public void testGetColors(){
        List<Color> colors = level.getColors();
        assertTrue(colors.contains(red) && colors.contains(green)
                && colors.contains(blue) && colors.contains(light_blue));
    }

    @Test
    public void testGetHints(){
        ArrayList<Move<RectangleGridCell>> hints = new ArrayList<>();
        Move<RectangleGridCell> move1 = new Move(green, new RectangleGridCell(0, 0));
        Move<RectangleGridCell> move2 = new Move(green, new RectangleGridCell(1, 3));
        Move<RectangleGridCell> move3 = new Move(green, new RectangleGridCell(4, 0));
        hints.add(move1);
        hints.add(move2);
        hints.add(move3);

        assertTrue(listsEqual(level.getHints(), hints));
    }

    @Test
    public void testLevelState_Win(){
        LevelState ongoing = LevelState.ONGOING;
        LevelState win = LevelState.WIN;
        assertEquals(ongoing, level.getLevelState());

        List<Move<RectangleGridCell>> winningPlay = level.getHints();

        for (Move<RectangleGridCell> move : winningPlay) {
            assertEquals(ongoing, level.getLevelState());
            level.play(move);
        }

        assertEquals(win, level.getLevelState());
    }

    @Test
    public void testCreateAndSetMemento(){
        Level.LevelMemento levelMemento = (Level.LevelMemento) level.createMemento();
        assertEquals(red, level.getColorAt(new RectangleGridCell(0, 0)));
        level.play(new Move<>(green, new RectangleGridCell(0, 0)));
        assertNotEquals(red, level.getColorAt(new RectangleGridCell(0, 0)));
        level.setMemento(levelMemento);
        assertEquals(red, level.getColorAt(new RectangleGridCell(0, 0)));
    }

    private boolean listsEqual(List<Move<RectangleGridCell>> hints, ArrayList<Move<RectangleGridCell>> otherHints) {
        ListIterator<Move<RectangleGridCell>> iter1 = hints.listIterator();
        ListIterator<Move<RectangleGridCell>> iter2 = hints.listIterator();

        while (iter1.hasNext()) {
            if (!iter1.next().equals(iter2.next())) {
                return false;
            } else if (!iter1.hasNext() && iter2.hasNext()) {
                return false;
            }
        }
        return true;
    }
}
