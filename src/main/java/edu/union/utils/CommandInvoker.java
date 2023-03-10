package edu.union.utils;

import java.util.ArrayList;
import java.util.*;


public class CommandInvoker {
    private static CommandInvoker instance;
    private Queue<Command> commandQueue;

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
        this.instance = null;
    }
    public Queue<Command> getCommandQueue(){
        return this.commandQueue;
    }


}
