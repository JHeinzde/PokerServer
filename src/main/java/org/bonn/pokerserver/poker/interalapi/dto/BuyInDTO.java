package org.bonn.pokerserver.poker.interalapi.dto;

import java.math.BigDecimal;

/**
 * Object used to transfer the buy in amount of a new player Or the rebuy of a player
 */
public class BuyInDTO {

    private final Integer buyIn;

    private BuyInDTO(Integer buyIn) {
        this.buyIn = buyIn;
    }

    public Integer getBuyIn() {
        return buyIn;
    }

    public static BuyInDTO newBuyInDTO(Integer buyIn) {
        return new BuyInDTO(buyIn);
    }
}
