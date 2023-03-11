package edu.union.utils;

import java.util.*;


public class CommandInvoker {
    private static CommandInvoker instance;
    private List<Command> commandQueue;

    private CommandInvoker(){
            this.commandQueue = new LinkedList<>();
    }

    public static CommandInvoker getInstance(){
        if(instance == null){
            instance = new CommandInvoker();
        }
        return instance;
    }

    public void invoke(Command c){
        c.execute();
        commandQueue.add(c);
    }
    public void reset(){
        this.commandQueue = new LinkedList<>();
    }

    public List<Command> getCommandQueue(){
        return this.commandQueue;
    }


}
