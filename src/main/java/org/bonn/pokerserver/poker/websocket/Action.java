package org.bonn.pokerserver.poker.websocket;

import org.bonn.pokerserver.poker.websocket.events.Event;

/**
 * This interface represents a functional interface that encapsulates an action that should be taken when
 * event x was recived
 */
public interface Action {
    Event processEvent(Event event, String tableId, String playerId);
}
