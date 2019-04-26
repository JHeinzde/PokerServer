package org.bonn.pokerserver.poker.interalapi;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import org.bonn.pokerserver.poker.internalapi.dto.UpdateDTO;
import org.bonn.pokerserver.poker.interalapi.dto.NewTableDTO;
import org.bonn.pokerserver.poker.interalapi.dto.PlayerDTO;
import org.bonn.pokerserver.poker.interalapi.service.PokerService;

import javax.inject.Inject;
import java.util.List;

/**
 * A controller handling all actions related to table management
 */
@Controller("/tables")
public class PokerController {

    private final PokerService pokerService;

    /**
     * Returns a new PokerController object
     * @param pokerService A PokerService containing business logic
     */
    @Inject
    public PokerController(PokerService pokerService) {
        this.pokerService = pokerService;
    }

    /**
     * Creates a new pot limit omaha table
     * @param newTable A NewTableDTO containing a player a buyIn for the player and a stake level
     * @return The uuid of the new table
     */
    @Post(value = "/new", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
    public String createNewTable(NewTableDTO newTable) {
        return pokerService.createNewTable(newTable);
    }

    /**
     * Deletes a PotLimitOmahaTable from the local table list
     * @param tableId The table id for the table that should be deleted
     * @return True if the table could be deleted false if not
     */
    @Delete(value = "/{table-id}", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public boolean deleteTable(@PathVariable("table-id") String tableId) {
        return pokerService.deleteTable(tableId);
    }

    /**
     * Updates a table, either with a new player joining the table or a rebuy/top off
     * @param tableId The id of table for the update
     * @return The current list of players at the table
     */
    @Put(value = "/{table-id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public List<PlayerDTO> updateTable(@PathVariable("table-id") String tableId, UpdateDTO update) {
        return pokerService.updateTable(update);
    }
}
