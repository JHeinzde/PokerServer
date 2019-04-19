package org.bonn.pokerserver.websocket.events;

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
     * @param playerName The playerName to be set in the new event
     * @param playerId The playerId to be set in the new event
     * @return The newly created event
     */
    public Event newPlayerJoinEvent(String playerName, String playerId) {
        return PlayerJoinEvent.newPlayerJoinEvent(playerName, playerId);
    }

    /**
     * Creates a PlayerLeaveEvent from the given playerName and playerId
     * @param playerName The playerName to be set in the new event
     * @param playerId The playerId to be set in the new event
     * @return The newly created event
     *
     */
    public Event newPlayerLeaveEvent(String playerName, String playerId) {
        return PlayerLeaveEvent.newPlayerLeaveEvent(playerName, playerId);
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
