package org.bonn.pokerserver.poker.interalapi.dto;

import java.math.BigDecimal;

/**
 * Object used to transfer the buy in amount of a new player Or the rebuy of a player
 */
public class BuyInDTO {

    private final BigDecimal buyIn;

    private BuyInDTO(BigDecimal buyIn) {
        this.buyIn = buyIn;
    }

    public BigDecimal getBuyIn() {
        return buyIn;
    }

    public static BuyInDTO newBuyInDTO(BigDecimal buyIn) {
        return new BuyInDTO(buyIn);
    }
}
