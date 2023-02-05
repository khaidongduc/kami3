package edu.union.utils;

/**
 * Observer interface for observer pattern
 * define methods that are used in observer pattern by observer
 */
public interface Observer {
    /**
     * update the observer by getting states from the attached Observable
     */
    void update();
}
