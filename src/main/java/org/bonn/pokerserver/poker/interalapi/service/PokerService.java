package org.bonn.pokerserver.poker.interalapi.service;

import org.bonn.pokerserver.poker.internalapi.dto.UpdateDTO;
import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.interalapi.dto.NewTableDTO;
import org.bonn.pokerserver.poker.interalapi.dto.PlayerDTO;
import org.bonn.pokerserver.poker.interalapi.mapper.PokerDTOMapper;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PokerService {

    private final TableList tableList;
    private static final PokerDTOMapper mapper = Mappers.getMapper(PokerDTOMapper.class);

    @Inject
    public PokerService(TableList globalTableList) {
        this.tableList = globalTableList;
    }

    public String createNewTable(NewTableDTO newTableDTO) {
        String tableId = tableList.createNewTable(newTableDTO.getStakeLevel());
        PotLimitOmahaTable newTable = tableList.getPotLimitOmahaTableById(tableId);
        newTable.addPlayer(mapper.playerDtoToPlayer(newTableDTO.getPlayer()), mapper.buyInDtoToBuyIn(newTableDTO.getBuyIn()));

        return tableId;
    }

    public boolean deleteTable(String tableId) {
        return tableList.removeTable(tableId);
    }

    //TODO: Implement. This should update a table with a new player and depending on different factors rebuy/topOff the player
    public List<PlayerDTO> updateTable(UpdateDTO update) {
        return null;
    }
}
