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

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LevelRepositoryManagerTests {
    private LevelRepositoryManager repositoryManager;


    @Before
    public void setUp(){
        ColorRepository colorRepository = ColorRepository.getInstance();
        for(Color color: Config.DEFAULT_COLORS)
            colorRepository.addColor(color);

        LevelRepositoryManager levelRepositoryManager = LevelRepositoryManager.getInstance();
        levelRepositoryManager.register(LevelType.RECTANGLE_GRID_LEVEL, TextRectangleGridLevelRepository.getInstance());
        levelRepositoryManager.setFolderPath("build/resources/test/edu.union/level");
        repositoryManager = levelRepositoryManager;
    }

    @After
    public void tearDown(){
        repositoryManager = null;
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.clear();
    }

    @Test
    public void testLoad(){
        RectangleGridLevel level = (RectangleGridLevel) repositoryManager.loadLevel(new LevelInfo(1, LevelType.RECTANGLE_GRID_LEVEL,
                "build/resources/test/edu.union/level/1.rectgrl"));
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
        repositoryManager.loadLevel(new LevelInfo(Integer.MAX_VALUE, LevelType.RECTANGLE_GRID_LEVEL,
                "build/resources/test/edu.union/level/9999.rectgrl"));
    }


    @Test
    public void testSaveBuild(){
        RectangleGridLevelBuilder builder = new RectangleGridLevelBuilder(5, 5);
        builder.setColor(new Color(0, 255, 0), new RectangleGridCell(0, 0));
        repositoryManager.saveLevel(builder);

        List<LevelInfo> levels = repositoryManager.listLevelInfos();
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

    @Test
    public void testSaveHint(){
        RectangleGridLevelBuilder builder = new RectangleGridLevelBuilder(5, 5);
        builder.setColor(new Color(0, 255, 0), new RectangleGridCell(0, 0));
        RectangleHintInputLevel level = new RectangleHintInputLevel(builder.getGraph(),5,5);
        repositoryManager.saveLevel(level);

        List<LevelInfo> levels = repositoryManager.listLevelInfos();
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
