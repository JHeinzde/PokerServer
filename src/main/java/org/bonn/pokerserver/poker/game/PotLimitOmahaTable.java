package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.common.CustomCollectors;
import org.bonn.pokerserver.poker.game.entities.Pot;
import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.MoneyStack;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.game.entities.player.TopOff;
import org.bonn.pokerserver.poker.websocket.events.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private final Pot pot;

    private PotLimitOmahaTable(StakeLevel stakeLevel) {
        this.roundHistory = new Stack<>();
        this.currentPlayers = new LinkedList<>();
        this.stakeLevel = stakeLevel;
        this.pot = Pot.newPot();
    }

    //TODO: Remove this and put it into a single class
    public Event processEvent(Event event) {
        return null;
    }

    /**
     * This method adds a player to the pot limit omaha table
     *
     * @param player The player to be added
     * @param buyIn  The amount of money the player bought in for
     */
    public void addPlayer(Player player, BuyIn buyIn) {
        currentPlayers.add(Player.newPlayer(player.getName(),
                player.getId(),
                MoneyStack.newMoneyStack(stakeLevel.getNumericLevel(), buyIn.getBuyInAmount())));
    }

    /**
     * Returns the player associated with the id
     *
     * @param playerId The player id of the player to be returned
     * @return The Player object associated with the input id
     */
    public Player getPlayerById(String playerId) {
        return currentPlayers.stream()
                .filter(player -> player.getId().equals(playerId))
                .collect(CustomCollectors.toSingleton());
    }

    /**
     * This method tops off a player for the specified amount
     *
     * @param player The player to be topped off
     * @param topOff The amount to top off the player
     */
    public void topOffPlayer(Player player, TopOff topOff) {
        manipulatePlayer(currentPlayer -> currentPlayer.getId().equals(player.getId()),
                playerToUpdate -> playerToUpdate.topOfStack(topOff.getAmount()));
    }

    /**
     * This method re-buys the specified player with the specified amount
     *
     * @param player The player that should be re-bought
     * @param buyIn  The re-buy amount that should be used for the new MoneyStack object
     */
    public void rebuyPlayer(Player player, BuyIn buyIn) {
        manipulatePlayer(playerToFilter -> playerToFilter.getId().equals(player.getId()),
                playerToUpdate -> playerToUpdate.reBuy(buyIn.getBuyInAmount()));
    }

    public void executeBet(Player player, Integer betAmount) {
        manipulatePlayer(playerToFilter -> playerToFilter.getId().equals(player.getId()),
                playerToUpdate -> playerToUpdate.makeBet(betAmount));
        pot.addBet(betAmount);
    }

    /**
     * Removes a player from the table
     *
     * @param player The player to be removed from the table
     * @return true if the player could be removed false if the player did not exist in the player list
     */
    public boolean removePlayer(Player player) {
        return currentPlayers.remove(player);
    }

    /**
     * Returns the current size of the player list of the table
     *
     * @return The current amount of player sitting at the table
     */
    public Integer size() {
        return this.currentPlayers.size();
    }

    /**
     * Manipulates a single player matching the supplied filter closure and applies the given closure action
     *
     * @param playerFilter The filter closure that defines the condition for the found player
     * @param action       The action to take on the player that was found by the filter
     */
    private void manipulatePlayer(Predicate<Player> playerFilter, Consumer<Player> action) {
        currentPlayers.stream()
                .filter(playerFilter)
                .findFirst()
                .ifPresent(action);
    }

    /**
     * This method returns a new PotLimitOmahaTable object for the specified StakeLevel.
     * The StakeLevel defines the size of the max stack. The max stack of a table is 100 big blinds
     * And the big blind is 100th of the StakeLevel.
     *
     * @param stakeLevel The StakeLevel to be used in Table creation
     * @return A new PotLimitOmahaTable instance with the StakeLevel specified
     */
    public static PotLimitOmahaTable newPotLimitOmahaTable(StakeLevel stakeLevel) {
        return new PotLimitOmahaTable(stakeLevel);
    }
}
