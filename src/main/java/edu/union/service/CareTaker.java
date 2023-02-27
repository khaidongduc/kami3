package edu.union.service;

import java.util.Stack;

public class CareTaker {
    private Stack<Object> stack;

    public CareTaker(){
        stack = new Stack<>();
    }

    public void add(Object toAdd){
        stack.push(toAdd);
    }

    public Object undo(){
        return stack.pop();
    }
}
