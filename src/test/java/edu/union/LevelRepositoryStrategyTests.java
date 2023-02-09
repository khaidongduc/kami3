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
public class LevelRepositoryStrategyTests {
    private RawTextLevelRepositoryStrategy repoStrategy;


    @Before
    public void setUp(){
        repoStrategy = new RawTextLevelRepositoryStrategy();
        repoStrategy.setFolderPath("build/resources/test/edu.union/level");
    }

    @After
    public void tearDown(){repoStrategy = null;}

    @Test
    public void testLoad(){
        LevelRepository.getInstance().setLevelRepositoryStrategy(repoStrategy);
        Level level = LevelRepository.getInstance().loadLevel(new LevelInfo(1));
        assertEquals(5, level.getNumRows());
        assertEquals(5, level.getNumCols());
        assertEquals(3, level.numMoveRemaining());
        assertEquals(new Color(255, 0, 0), level.getCurrentColor());
        List<Move> hints = level.getHints();
        for (Move m : hints){
            level.play(m);
        }
        assertEquals(LevelState.WIN, level.getLevelState());
    }

    @Test (expected = RuntimeException.class)
    public void testLoad_UnknownFile(){
        repoStrategy.loadLevel(new LevelInfo(Integer.MAX_VALUE));
    }


    @Test
    public void testSave(){
        LevelBuilder builder = new LevelBuilderImpl();
        builder.setColor(new Color(0, 255, 0), 0, 0);
        repoStrategy.saveLevel(builder);

        List<LevelInfo> levels = repoStrategy.listLevelInfo();
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
