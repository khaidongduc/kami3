package edu.union.utils;

public interface MementoOriginator {
    MementoCaretaker getCareTaker();
    Memento createMemento();
    void setMemento(Memento memento);
}
