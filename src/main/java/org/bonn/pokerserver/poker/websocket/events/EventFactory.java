package org.bonn.pokerserver.poker.websocket.events;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;

/**
 * A class producing events
 */
public class EventFactory {

    private static final EventFactory SINGLETON_INSTANCE = new EventFactory();

    private EventFactory() {
        // Hide constructor from the outside
    }

    /**
     * Creates a PlayerJoinEvent from the given playerName and playerId
     * @param player The player that joined the table
     * @return The newly created event
     */
    public Event newPlayerJoinEvent(Player player) {
        return PlayerJoinEvent.newPlayerJoinEvent(player);
    }

    /**
     * Creates a PlayerLeaveEvent from the given playerName and playerId
     * @param player The player that left the game
     * @return The newly created event
     */
    public Event newPlayerLeaveEvent(Player player) {
        return PlayerLeaveEvent.newPlayerLeaveEvent(player);
    }

    /**
     * Returns a new InvalidInputEvent
     * @param message The message to be passed into the event
     * @return The new InvalidInputEvent
     */
    public Event newInvalidInputEvent(String message){
        return InvalidInputEvent.newInvalidInputEvent(message);
    }

    /**
     * Returns a static reference to the singleton event factory
     * @return A singleton event factory
     */
    public static EventFactory getEventFactory() {
        return SINGLETON_INSTANCE;
    }

}
