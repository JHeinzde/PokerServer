package org.bonn.pokerserver.poker.common.interfaces;

/**
 * This class is an interface for an Observer
 */
public interface Observer {

    /**
     * This method is called by an Observable when it updates and the observer is registered
     */
     void notifyObserver();
}
