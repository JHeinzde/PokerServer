package org.bonn.pokerserver.poker.websocket;

import org.bonn.pokerserver.poker.websocket.events.Event;

/**
 * This interface defines the api that the event handler must offer
 */
public interface EventHandler {
    /**
     * Processes the event given as input
     * @param event The input event that should be processed by the event handler
     * @return
     */
    Event handleEvent(Event event, String tableId);
}
