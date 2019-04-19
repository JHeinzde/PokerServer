package org.bonn.pokerserver.websocket.TableManagement;

import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;

import java.util.*;

/**
 * A class encapsulating the list of currently running tables
 */
public class TableList {
    private static final TableList TABLE_LIST = new TableList();
    private static final Map<String, PotLimitOmahaTable> POT_LIMIT_OMAHA_TABLES = new HashMap<>();

    private TableList() {
        // Hide the construct
    }

    /**
     * Returns a singleton reference to the global table list
     *
     * @return The list of tables
     */
    public static TableList getTableList() {
        return TABLE_LIST;
    }

    /**
     * Returns the PotLimitOmahaTable associated to the tableId
     * @param tableId The uuid that should be used to return a PotLimitOmahaTable
     * @return PotLimitOmahaTable
     */
    public PotLimitOmahaTable getPotLimitOmahaTableById(String tableId) {
        return POT_LIMIT_OMAHA_TABLES.get(tableId);
    }

    /**
     * Creates a new PotLimitOmahaTable and returns the table id
     * @return The table id
     */
    public String createNewTable() {
        String tableId = UUID.randomUUID().toString();
        PotLimitOmahaTable newTable = PotLimitOmahaTable.newPotLimitOmahaTable();

        POT_LIMIT_OMAHA_TABLES.put(tableId, newTable);
        return tableId;
    }

    /**
     * Removes a table from the table list
     * @param tableId The id of the table to be removed from the list
     */
    private void removeTable(String tableId) {
        POT_LIMIT_OMAHA_TABLES.remove(tableId);
    }
}
