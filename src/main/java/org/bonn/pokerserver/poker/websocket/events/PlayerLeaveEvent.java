package org.bonn.pokerserver.poker.websocket.events;

import org.bonn.pokerserver.poker.game.entities.player.Player;

public class PlayerLeaveEvent implements Event {

    private static final EventType EVENT_TYPE = EventType.PLAYER_LEAVE;
    private final Player player;

    private PlayerLeaveEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }

    public static PlayerLeaveEvent newPlayerLeaveEvent(Player player) {
        return new PlayerLeaveEvent(player);
    }
}
