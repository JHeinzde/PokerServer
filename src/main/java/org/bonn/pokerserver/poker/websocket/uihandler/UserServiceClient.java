package org.bonn.pokerserver.poker.websocket.uihandler;

import org.bonn.pokerserver.poker.game.entities.player.Player;

public class UserServiceClient {

    private static final UserServiceClient USER_SERVICE_CLIENT = new UserServiceClient();


    public Player getPlayerByPlayerId(String playerId) {
        return null;
    }

    public static UserServiceClient getUserServiceClient() {
        return USER_SERVICE_CLIENT;
    }
}
