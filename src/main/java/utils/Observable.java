package utils;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {

    private final Set<Observer> observers;

    protected Observable() {
        this.observers = new HashSet<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }


    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }

}
