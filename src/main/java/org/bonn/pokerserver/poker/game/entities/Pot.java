package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.player.Player;

/**
 * This class represents a pot limit omaha pot
 */
public class Pot {

    private Integer currentPotAmount;

    // Hide the default constructor
    private Pot() {
    }

    /**
     * Pays out the pot to the given player and resets the pot to 0
     *
     * @return The amount paid to the player
     */
    public Integer payOut(Player player) {
        player.payOutPlayer(currentPotAmount);
        Integer returnAmount = currentPotAmount;
        reset();
        return returnAmount;
    }

    public void addBet(Integer betAmount) {
        validateBet(betAmount);
        currentPotAmount += betAmount;
    }

    private void validateBet(Integer betAmount) {
    }

    private void reset() {
        currentPotAmount = 0;
    }

    public static Pot newPot() {
        return new Pot();
    }
}
