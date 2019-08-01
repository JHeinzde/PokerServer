package org.bonn.pokerserver.poker.websocket.events;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;

public class PlayerJoinEvent implements Event {

    private static final EventType EVENT_TYPE = EventType.PLAYER_JOIN;
    private final Player player;;


    private PlayerJoinEvent(Player player ) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }


    public static PlayerJoinEvent newPlayerJoinEvent(Player player) {
        return new PlayerJoinEvent(player);
    }
}
