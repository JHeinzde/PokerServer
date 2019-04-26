package org.bonn.pokerserver.poker.game.entities.player;

import java.math.BigDecimal;

/**
 * This class represents a buy in
 */
public class BuyIn {

    private final Integer buyInAmount;

    /**
     * Public constructor for map struct
     */
    public BuyIn() {
        this.buyInAmount = null;
    }

    private BuyIn(Integer buyInAmount) {
        this.buyInAmount = buyInAmount;
    }

    /**
     * Returns the buy in amount
     *
     * @return BuyIn as BigDecimal
     */
    public Integer getBuyInAmount() {
        return buyInAmount;
    }

    /**
     * Creates new BuyIn objects
     *
     * @param buyIn The BigDecimal to be included in the object
     * @return A new BuyIn Object
     */
    public static BuyIn newBuyIn(Integer buyIn) {
        return new BuyIn(buyIn);
    }
}
