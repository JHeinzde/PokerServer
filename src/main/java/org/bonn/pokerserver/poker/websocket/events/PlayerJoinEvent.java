package org.bonn.pokerserver.poker.websocket.events;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;

public class PlayerJoinEvent implements Event {

    private static final EventType EVENT_TYPE = EventType.PLAYER_JOIN;
    private final Player player;
    private final BuyIn buyIn;


    private PlayerJoinEvent(Player player, BuyIn buyIn) {
        this.player = player;
        this.buyIn = buyIn;
    }

    public Player getPlayer() {
        return player;
    }

    public BuyIn getBuyIn() {
        return buyIn;
    }

    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }


    public static PlayerJoinEvent newPlayerJoinEvent(Player player, BuyIn buyIn) {
        return new PlayerJoinEvent(player, buyIn);
    }
}
