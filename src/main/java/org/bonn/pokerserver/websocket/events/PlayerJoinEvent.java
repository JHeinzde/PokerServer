package org.bonn.pokerserver.websocket.events;

public class PlayerJoinEvent implements Event {

    private static final EventType EVENT_TYPE = EventType.PLAYER_JOIN;
    private final String playerName;
    private final String playerId;


    private PlayerJoinEvent(String playerName, String playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
    }


    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public static PlayerJoinEvent newPlayerJoinEvent(String playerName, String playerId) {
        return new PlayerJoinEvent(playerName, playerId);
    }
}
