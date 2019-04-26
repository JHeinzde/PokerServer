package org.bonn.pokerserver.poker.game.entities.player;

/**
 * This class wraps an integer that should be used to top off a player
 */
public class TopOff {

    private final Integer topOffAmount;

    public TopOff(Integer topOffAmount) {
        this.topOffAmount = topOffAmount;
    }

    public Integer getAmount() {
        return this.topOffAmount;
    }
}
