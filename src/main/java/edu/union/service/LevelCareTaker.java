package edu.union.service;

import edu.union.model.Level;

import java.util.Stack;

public class CareTaker {
    private Stack<Object> stack;
    private Level level;

    public CareTaker(Level targetLevel){
        this.level = targetLevel;
        stack = new Stack<>();
    }

    public void add(Object toAdd){
        stack.push(toAdd);
    }

    public Object undo(){
        return stack.pop();
    }

    public boolean undoable(){
        return !stack.isEmpty();
    }
}
