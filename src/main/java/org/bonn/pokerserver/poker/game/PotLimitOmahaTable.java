package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.websocket.events.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents a pot limit omaha table
 */
public class PotLimitOmahaTable {

    private Round currentRound;
    private Player toAct;
    private List<Player> remainingPlayers;

    private final Stack<Round> roundHistory;
    private final List<Player> currrentPlayers;

    private PotLimitOmahaTable() {
        this.roundHistory = new Stack<>();
        this.currrentPlayers = new LinkedList<>();
    }


    public static PotLimitOmahaTable newPotLimitOmahaTable() {
        return new PotLimitOmahaTable();
    }

    public Event processEvent(Event event) {
        return null;
    }
}
