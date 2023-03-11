package edu.union;

import edu.union.model.*;
import edu.union.service.ColorRepository;
import edu.union.service.LevelBuilderFactory;
import edu.union.utils.PlayMoveCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
    public class RectangleHintInputLevelTests {

    private RectangleHintInputLevel level;
    private RectangleGridLevelBuilder builder;
    private final Color red = new Color(255, 0, 0);
    private final Color green = new Color(0, 255, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color light_blue = new Color(0, 255, 255);

    private final int NUM_ROWS = 5;
    private final int NUM_COLS = 5;

    @Before
    public void setUp() throws Exception{
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.addColor(red);
        colorRepository.addColor(green);
        colorRepository.addColor(blue);
        colorRepository.addColor(light_blue);

        builder = new RectangleGridLevelBuilder(this.NUM_ROWS,this.NUM_COLS);
        level = new RectangleHintInputLevel(builder.getGraph(),builder.getRows(),builder.getCols());
    }

    @After
    public void tearDown(){
        level = null;
        builder = null;
        ColorRepository colorRepository = ColorRepository.getInstance();
        colorRepository.clear();
    }

    @Test
    public void testConstructor(){
        assertEquals(NUM_COLS,level.getCols());
        assertEquals(NUM_ROWS,level.getRows());
        List<Color> colors = level.getColors();
        assertTrue(colors.contains(red));
        assertTrue(colors.contains(green));
        assertTrue(colors.contains(blue));
        assertTrue(colors.contains(light_blue));
        assertEquals(builder.getGraph(),level.getGraph());
    }
    @Test
    public void testSwitchColor(){
        level.switchColor(green);
        assertEquals(green,level.getCurrentColor());
        level.switchColor(blue);
        assertEquals(blue,level.getCurrentColor());
    }
    @Test
    public void testPlay(){
        for(int colorId = 0; colorId < level.getColors().size(); colorId++) {
            for (int i = 0; i < NUM_COLS; i++) {
                //First row is all one color
                this.builder.setColor(ColorRepository.getInstance().getColor(colorId), new RectangleGridCell(0, i));
            }
            for (int i = 0; i < NUM_ROWS; i++) {
                //First col is all one color
                this.builder.setColor(ColorRepository.getInstance().getColor(colorId), new RectangleGridCell(i, 0));
            }
            //Update the level to reflect changes to the builder
            this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);

            //Create a color that is not the same as colorId and play it at (0,0)
            Color c = ColorRepository.getInstance().getColor((colorId+1)%this.level.getColors().size());
            this.level.play(new Move(c,new RectangleGridCell(0,0)));

            for(int i = 0; i < NUM_COLS;i++){
                //First row must be color c
                assertEquals(this.level.getColorAt(new RectangleGridCell(0,i)),c);
            }
            for(int i = 0; i < NUM_ROWS; i++){
                //First col must be color c
                assertEquals(this.level.getColorAt(new RectangleGridCell(i,0)),c);
            }
            //Restart the builder and restart the level
            this.builder.restart();
            this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);
        }
    }

    @Test
    public void testResetGraph(){
        //Scramble the colors and to set the level
        int numColors = level.getColors().size();
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){
                this.builder.setColor(ColorRepository.getInstance().getColor((i+j)%numColors),new RectangleGridCell(i,j));
            }
        }
        this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);
        //Play a bunch of moves
        for(int i = 0; i < NUM_ROWS;i++){
            for(int j = 0; j < NUM_COLS; j++){
                level.play(new Move(green,new RectangleGridCell(i,j)));
            }
        }
        //Reset the graph and see if everything is equal
        this.level.resetGraph();
        for(int i = 0; i < NUM_ROWS;i++){
            for(int j = 0; j < NUM_COLS; j++){
                assertEquals(this.level.getColorAt(new RectangleGridCell(i,j)),this.builder.getColorAt(new RectangleGridCell(i,j)));
            }
        }
        //Restart the builder and the level
        this.builder.restart();
        this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);
    }

    @Test
    public void testGetLevelState(){
        //Trivially non-winning game
        this.builder.setColor(ColorRepository.getInstance().getColor(0),new RectangleGridCell(0,0));
        this.builder.setColor(ColorRepository.getInstance().getColor(1),new RectangleGridCell(0,1));
        this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);
        assertEquals(this.level.getLevelState(),LevelState.ONGOING);
        //Trivially won game
        this.builder.restart();
        this.level = new RectangleHintInputLevel(new ColoredGraph(this.builder.getGraph()),NUM_ROWS,NUM_COLS);
        assertEquals(this.level.getLevelState(),LevelState.WIN);
    }
}
