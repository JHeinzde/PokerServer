package org.bonn.pokerserver.websocket.events;

public class PlayerLeaveEvent implements Event {

    private static final EventType EVENT_TYPE = EventType.PLAYER_LEAVE;

    private final String playerName;
    private final String playerId;

    private PlayerLeaveEvent(String playerName, String playerId) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }

    public static PlayerLeaveEvent newPlayerLeaveEvent(String playerName, String playerId) {
        return new PlayerLeaveEvent(playerName, playerId);
    }
}
