package edu.union.utils;

import java.util.Stack;

public class MementoCaretaker {

    private final Stack<Memento> mementoStack;
    private final MementoOriginator originator;

    public MementoCaretaker(MementoOriginator originator){
        this.originator = originator;
        mementoStack = new Stack<>();
    }

    public void recordOriginator(){
        mementoStack.push(originator.createMemento());
    }

    public void undo(){
        originator.setMemento(mementoStack.pop());
    }

    public boolean undoable(){
        return !mementoStack.isEmpty();
    }

}
