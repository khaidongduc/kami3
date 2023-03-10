package edu.union;

import edu.union.utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class MementoCaretakerTests {


    public MementoOriginatorTestObject originator;
    public MementoCaretaker mementoCaretaker;


    private final String STATE0 = "0";
    private final String STATE1 = "1";
    private final String STATE2 = "2";



    @Before
    public void setUp(){
        originator = new MementoOriginatorTestObject(STATE0);
        mementoCaretaker = new MementoCaretaker(originator);
        originator.setMementoCaretaker(mementoCaretaker);
    }

    @Test
    public void testUndoable(){
        assertFalse(mementoCaretaker.undoable());
        mementoCaretaker.recordOriginator();
        assertTrue(mementoCaretaker.undoable());
        mementoCaretaker.undo();
        assertFalse(mementoCaretaker.undoable());
    }

    @Test
    public void testUndo(){
        mementoCaretaker.recordOriginator();
        originator.changeInnerState(STATE1);

        mementoCaretaker.recordOriginator();
        originator.changeInnerState(STATE2);

        assertEquals(STATE2, originator.getInnerState());
        mementoCaretaker.undo();
        assertEquals(STATE1, originator.getInnerState());

        assertEquals(STATE1, originator.getInnerState());
        mementoCaretaker.undo();
        assertEquals(STATE0, originator.getInnerState());
    }

    @After
    public void tearDown(){
        mementoCaretaker = null;
        originator = null;
    }

}


/**
 * both a stub and a spy because the caretaker has
 * some indirect input from the originator (caretaker.recordOriginator() calls result of createMemento())
 * and
 * some indirect output to the originator (setMemento())
 */
class MementoOriginatorTestObject implements MementoOriginator {

    private String innerState;
    private MementoCaretaker mementoCaretaker;


    public MementoOriginatorTestObject(String initialState){
        this.innerState = initialState;
    }

    public void changeInnerState(String state){
        this.innerState = state;
    }

    public String getInnerState(){
        return this.innerState;
    }

    public void setMementoCaretaker(MementoCaretaker mementoCaretaker) {
        this.mementoCaretaker = mementoCaretaker;
    }

    @Override
    public MementoCaretaker getCareTaker() {
        return mementoCaretaker;
    }

    @Override
    public Memento createMemento() {
        return new TestMemento(this);
    }

    @Override
    public void setMemento(Memento memento) {
        TestMemento stub = (TestMemento) memento;
        this.innerState = stub.state;
    }


    public static class TestMemento extends Memento {
        protected final String state;
        private TestMemento(MementoOriginatorTestObject originator){
            this.state = originator.innerState;
        }
    }

}

