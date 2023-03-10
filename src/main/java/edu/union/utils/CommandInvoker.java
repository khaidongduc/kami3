package edu.union.utils;

import java.util.ArrayList;
import java.util.*;


public class CommandInvoker {
    private static CommandInvoker instance;
    private List<PlayMoveCommand> commandMoveQueue;

    private CommandInvoker(){
            this.commandMoveQueue = new LinkedList<>();
    }

    public static CommandInvoker getInstance(){
        if(instance == null){
            instance = new CommandInvoker();
        }
        return instance;
    }

    public void invoke(Command c){
        c.execute();
    }
    public void reset(){
        this.commandMoveQueue = new LinkedList<>();
    }
    public void invoke(PlayMoveCommand c){
        c.execute();
        commandMoveQueue.add(c);
    }

    public List<PlayMoveCommand> getCommandMoveQueue(){
        return this.commandMoveQueue;
    }


}
