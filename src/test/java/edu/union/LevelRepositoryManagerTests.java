package edu.union;

import edu.union.model.*;
import edu.union.service.LevelRepositoryManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelRepositoryManagerTests {
    private LevelRepositoryManager levelRepositoryManager;


    @Before
    public void setUp(){
        levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.setFolderPath("build/resources/test/edu.union/level");
    }

    @After
    public void tearDown(){levelRepositoryManager = null;}

    @Test
    public void testLoad(){
        RectangleGridLevel level = (RectangleGridLevel) levelRepositoryManager.loadLevel(new LevelInfo(1, "RECTANGLE_GRID_LEVEL", "build/resources/test/edu.union/level"));
        assertEquals(25, level.getGraph().getNumVertices());
        assertEquals(3, level.numMoveRemaining());
        assertEquals(new Color(255, 0, 0), level.getCurrentColor());
        List<Move<RectangleGridCell>> hints = level.getHints();
        for (Move m : hints){
            level.play(m);
        }
        assertEquals(LevelState.WIN, level.getLevelState());
    }

    @Test (expected = RuntimeException.class)
    public void testLoad_UnknownFile(){
        levelRepositoryManager.loadLevel(new LevelInfo(Integer.MAX_VALUE, "RECTANGLE_GRID_LEVEL", "build/resources/test/edu.union/level"));
    }


    @Test
    public void testSave(){
        RectangleGridLevelBuilder builder = new RectangleGridLevelBuilder(5, 5);
        builder.setColor(new Color(0, 255, 0), new RectangleGridCell(0, 0));
        levelRepositoryManager.saveLevel(builder);

        List<LevelInfo> levels = levelRepositoryManager.listLevelInfos();
        List<String> levelInfo = new ArrayList<>();
        for (LevelInfo l : levels){
            levelInfo.add(l.toString());
        }

        boolean is = false;
        for (String l : levelInfo){
            if(l.equals("4")){
                is = true;
            }
        }
        assertTrue(is);
    }
}
