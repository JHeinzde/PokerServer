package org.bonn.pokerserver.poker.interalapi.mapper;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.interalapi.dto.BuyInDTO;
import org.bonn.pokerserver.poker.interalapi.dto.PlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper to map dtos to internal objects and the other way around
 */
@Mapper(uses = {Player.class, BuyIn.class})
public interface PokerDTOMapper {

    /**
     * Maps a PlayerDTO to a Player Object
     *
     * @param player The player to map
     * @return The mapped Object
     */
    @Mapping(target = "moneyStack", ignore = true)
    Player playerDtoToPlayer(PlayerDTO player);

    /**
     * Maps a BuyInDTO to a BuyIn object
     * @param buyIn The BuyInDTO to map
     * @return The mapped BuyIn
     */
    BuyIn buyInDtoToBuyIn(BuyInDTO buyIn);

}
