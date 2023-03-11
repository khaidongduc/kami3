package edu.union;

import edu.union.model.*;
import edu.union.utils.Command;
import edu.union.utils.CommandInvoker;
import edu.union.utils.PlayMoveCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.css.Rect;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CommandTest {
    private CommandInvoker commandInvoker;
    private RectangleHintInputLevelStub level;
    private final Color red = new Color(255, 0, 0);

    @Before
    public void setUp(){
        commandInvoker = CommandInvoker.getInstance();
        level = new RectangleHintInputLevelStub();
    }

    @After
    public void tearDown(){
        commandInvoker = null;
        level = null;
    }

    @Test
    public void testReset(){
        Command c0 = new PlayMoveCommand<>(level, new Move<>(red,new RectangleGridCell(0,0)));
        Command c1 = new PlayMoveCommand<>(level, new Move<>(red,new RectangleGridCell(0,1)));
        Command c2 = new PlayMoveCommand<>(level, new Move<>(red,new RectangleGridCell(1,0)));
        commandInvoker.invoke(c0);
        commandInvoker.invoke(c1);
        commandInvoker.invoke(c2);
        commandInvoker.reset();
        assertEquals(commandInvoker.getCommandQueue(),new LinkedList<>());
    }

    @Test
    public void testGetCommandQueue(){
        for(int i = 0; i < 4; i++){
            commandInvoker.invoke(new PlayMoveCommand<>(level,new Move<>(red,new RectangleGridCell(0,i))));
        }
        List<Command> moveList = commandInvoker.getCommandQueue();
        for(int i = 0; i < moveList.size(); i++){
            assertEquals(moveList.get(0),new PlayMoveCommand<>(level,new Move<>(red,new RectangleGridCell(0,i))));
        }
        commandInvoker.reset();
    }

    @Test
    public void testInvoke(){
        for(int i = 0; i < 4; i++){
            commandInvoker.invoke(new PlayMoveCommand<>(level,new Move<>(red,new RectangleGridCell(0,i))));
        }
        List<Command> commandList = commandInvoker.getCommandQueue();
        List<Move<RectangleGridCell>> moveList = level.getMoves();
        for(int i = 0; i < moveList.size(); i++){
            PlayMoveCommand c = (PlayMoveCommand) commandList.get(i);
            assertEquals(c.getMove(),moveList.get(i));
            assertEquals(moveList.get(i),new Move<>(red,new RectangleGridCell(0,i)));
        }
        commandInvoker.reset();
    }

}
