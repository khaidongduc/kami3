package edu.union.service;

import edu.union.model.Level;

import java.util.Stack;

public class LevelCareTaker {
    private Stack<Level.Memento> stack;
    private Level level;

    public LevelCareTaker(Level targetLevel){
        this.level = targetLevel;
        stack = new Stack<>();
    }

    public void recordOriginator(){
        stack.push(level.createMemento());
    }

    public void undo(){
        level.setMemento(stack.pop());
    }

    public boolean undoable(){
        return !stack.isEmpty();
    }
}
