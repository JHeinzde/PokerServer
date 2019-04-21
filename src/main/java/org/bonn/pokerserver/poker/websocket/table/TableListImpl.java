package org.bonn.pokerserver.poker.websocket.table;

import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.StakeLevel;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A class encapsulating the list of currently running tables
 */
@Singleton
public class TableListImpl implements TableList {
    private static final Map<String, PotLimitOmahaTable> POT_LIMIT_OMAHA_TABLES = new HashMap<>();

    private TableListImpl() {
        // Hide the construct
    }

    /**
     * Returns the PotLimitOmahaTable associated to the tableId
     * @param tableId The uuid that should be used to return a PotLimitOmahaTable
     * @return PotLimitOmahaTable
     */
    @Override
    public PotLimitOmahaTable getPotLimitOmahaTableById(String tableId) {
        return POT_LIMIT_OMAHA_TABLES.get(tableId);
    }

    /**
     * Creates a new PotLimitOmahaTable and returns the table id
     * @return The table id
     */
    @Override
    public String createNewTable(StakeLevel stakeLevel) {
        String tableId = UUID.randomUUID().toString();
        PotLimitOmahaTable newTable = PotLimitOmahaTable.newPotLimitOmahaTable(stakeLevel);

        POT_LIMIT_OMAHA_TABLES.put(tableId, newTable);
        return tableId;
    }

    /**
     * Removes a table from the table list
     * @param tableId The id of the table to be removed from the list
     */
    @Override
    public boolean removeTable(String tableId) {
        PotLimitOmahaTable removedTable = POT_LIMIT_OMAHA_TABLES.remove(tableId);
        return removedTable != null;
    }
}
