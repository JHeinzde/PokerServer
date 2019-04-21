package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.MoneyStack;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.websocket.events.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents a pot limit omaha table
 */
public class PotLimitOmahaTable {

    private final StakeLevel stakeLevel;

    private Round currentRound;
    private Player toAct;
    private List<Player> remainingPlayers;

    private final Stack<Round> roundHistory;
    private final List<Player> currentPlayers;

    private PotLimitOmahaTable(StakeLevel stakeLevel) {
        this.roundHistory = new Stack<>();
        this.currentPlayers = new LinkedList<>();
        this.stakeLevel = stakeLevel;
    }


    public static PotLimitOmahaTable newPotLimitOmahaTable(StakeLevel stakeLevel) {
        return new PotLimitOmahaTable(stakeLevel);
    }

    public Event processEvent(Event event) {
        return null;
    }

    public void addPlayer(Player player, BuyIn buyIn) {
        currentPlayers.add(Player.newPlayer(player.getName(),
                player.getId(),
                MoneyStack
                        .newMoneyStack(stakeLevel.getNumericLevel(), buyIn.getBuyIn())));
    }
}
