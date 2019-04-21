package org.bonn.pokerserver.poker.game.entities.player;

import java.math.BigDecimal;

public class BuyIn {

    private final BigDecimal buyIn;

    private BuyIn(BigDecimal buyIn) {
        this.buyIn = buyIn;
    }

    public BigDecimal getBuyIn() {
        return buyIn;
    }

    public static BuyIn newBuyIn(BigDecimal buyIn) {
        return new BuyIn(buyIn);
    }
}
