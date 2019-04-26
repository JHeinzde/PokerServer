package org.bonn.pokerserver.poker.game.entities.player;

import java.math.BigDecimal;

public class TopOff {

    private final BigDecimal topOffAmount;

    public TopOff(BigDecimal topOffAmount) {
        this.topOffAmount = topOffAmount;
    }

    public BigDecimal getAmount() {
        return this.topOffAmount;
    }
}
