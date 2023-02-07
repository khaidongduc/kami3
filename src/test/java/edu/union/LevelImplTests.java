package edu.union;

import edu.union.model.*;
import edu.union.service.LevelRepository;
import edu.union.service.RawTextLevelRepositoryStrategy;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelImplTests {
    private Level level;
    private RawTextLevelRepositoryStrategy repoStrat;

    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);


    @Before
    public void setUp(){
        repoStrat = new RawTextLevelRepositoryStrategy();
        repoStrat.setFolderPath("build/resources/test/edu.union/level");
        LevelRepository.getInstance().setLevelRepositoryStrategy(repoStrat);
        level = LevelRepository.getInstance().loadLevel(new LevelInfo(1));
    }

    @After
    public void tearDown(){level = null;}

    @Test
    public void testConstruct(){
        assertEquals(5, level.getNumRows());
        assertEquals(5, level.getNumCols());
        assertEquals(3, level.numMoveRemaining());
    }

    @Test
    public void testGetColorAt(){
        for(int i = 0; i < 5; i++) {
            assertEquals(red, level.getColorAt(0, i));
        }

        for (int i = 1; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(green, level.getColorAt(i, j));
            }
        }

        for (int i = 1; i < 3; i++){
            for (int j = 3; j < 5; j++){
                assertEquals(blue, level.getColorAt(i, j));
            }
        }

        for (int i = 3; i < 5; i++){
            for (int j = 0; j < 5; j++){
                assertEquals(light_blue, level.getColorAt(i, j));
            }
        }
    }

    @Test
    public void testSwitchColor(){
        level.switchColor(green);
        assertEquals(green, level.getCurrentColor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwitchColor_Invalid(){
        Color random = new Color(5, 5, 5);
        level.switchColor(random);
    }

    @Test
    public void testGetCurrentColor(){
        assertEquals(red, level.getCurrentColor());

        level.switchColor(green);
        assertEquals(green, level.getCurrentColor());
    }

    @Test
    public void testPlay(){
        Move move = new Move(blue, 0, 0);
        assertEquals(3, level.numMoveRemaining());
        level.play(move);
        for(int i = 0; i < 5; i++) {
            assertEquals(blue, level.getColorAt(0, i));
        }

        assertEquals(2, level.numMoveRemaining());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testPlay_noMovesLeft(){
        Move move1 = new Move(green, 0, 0);
        level.play(move1);
        Move move2 = new Move(red, 0, 1);
        level.play(move2);
        Move move3 = new Move(green, 0, 2);
        level.play(move3);
        Move move4 = new Move(red, 0, 3);
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
        ArrayList<Move> hints = new ArrayList<>();
        Move move1 = new Move(green, 0, 0);
        Move move2 = new Move(green, 1, 3);
        Move move3 = new Move(green, 4, 0);
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

        List<Move> winningPlay = level.getHints();
        int movesLeft = 3;
        while(winningPlay.iterator().hasNext()){
            Move move = winningPlay.get(0);
            assertEquals(movesLeft, level.numMoveRemaining());
            movesLeft--;
            assertEquals(ongoing, level.getLevelState());
            level.play(move);
        }

        assertEquals(win, level.getLevelState());
    }


    private boolean listsEqual(List<Move> hints, ArrayList<Move> otherHints){
        for(int i =0; i < otherHints.size(); i++){
            System.out.println(hints.get(i));
            if(!hints.get(i).toString().equals((otherHints.get(0)).toString())){
                return false;
            }
            hints.remove(hints.get(0));
            if(hints.iterator().hasNext()){
                return false;
            }
        }
        return true;
    }
}
