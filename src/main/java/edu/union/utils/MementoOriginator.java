package edu.union.utils;

public abstract class MementoOriginator {

    protected MementoCaretaker mementoCaretaker;

    public MementoOriginator(){
        mementoCaretaker = new MementoCaretaker(this);
    }


    abstract Memento createMemento();
    abstract void setMemento(Memento memento);
}
