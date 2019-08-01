package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.player.Player;

/**
 * This class represents a pot limit omaha pot
 */
public class Pot {

    private Integer currentPotAmount;

    /**
     * Pays out the pot to the given player
     * @return The amount paid to the player
     */
    public Integer payOut(Player player) {
        player.
    }

    private void reset() {
        currentPotAmount = 0;
    }
}
