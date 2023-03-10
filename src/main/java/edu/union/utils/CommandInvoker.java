package edu.union.utils;

import java.util.Stack;

public class CommandInvoker {
    private static CommandInvoker instance;
    private Stack<Command> commandStack;

    private CommandInvoker(){
        this.commandStack = new Stack<>();
    }

    private static CommandInvoker getInstance(){
        if(instance == null){
            instance = new CommandInvoker();
        }
        return instance;
    }

    public void invoke(Command c){
        c.execute();
        commandStack.add(c);
    }

    public Stack<Command> getCommandStack(){
        return this.commandStack;
    }


}
