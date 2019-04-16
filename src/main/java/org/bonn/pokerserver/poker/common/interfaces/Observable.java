package org.bonn.pokerserver.poker.common.interfaces;

import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class encapsulates the functionality of an observable object
 */
public abstract class Observable {

    private final List<Observer> observers;

    protected Observable() {
        this.observers = new LinkedList<>();
    }

    /**
     * This method is used to subscribe to the observable object its called on
     * @param observer The observer to be registered
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * This method is used to delete an observer from the list of observers
     * @param observer The observer to be removed from the observer list
     */
    public void deleteSubscription(Observer observer) {
        observers.remove(observer);
    }

    /**
     * This method is used to notify the observers of the observable
     */
    public void notifyObservers() {
        observers.forEach(Observer::notifyObserver);
    }
}
