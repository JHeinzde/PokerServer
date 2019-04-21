package org.bonn.pokerserver.poker.interalapi.dto;

import org.bonn.pokerserver.poker.game.StakeLevel;

public class NewTableDTO {

    private final BuyInDTO buyIn;
    private final PlayerDTO player;
    private final StakeLevel stakeLevel;


    public NewTableDTO(BuyInDTO buyIn, PlayerDTO player, StakeLevel stakeLevel) {
        this.buyIn = buyIn;
        this.player = player;
        this.stakeLevel = stakeLevel;
    }


    public PlayerDTO getPlayer() {
        return player;
    }

    public BuyInDTO getBuyIn() {
        return buyIn;
    }

    public StakeLevel getStakeLevel() {
        return stakeLevel;
    }
}
