package edu.union.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Observable abstract class for observer pattern
 * define methods that are used in observer pattern by observable
 */
public abstract class Observable {

    private final Set<Observer> observers;

    /**
     * basic initialization
     */
    protected Observable() {
        this.observers = new HashSet<>();
    }

    /**
     * attach an observer to this observable
     * @param observer the observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * detach an observer from this observable
     * @param observer the observer
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * notify all observers that there are changes to the observable
     */
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }

}
