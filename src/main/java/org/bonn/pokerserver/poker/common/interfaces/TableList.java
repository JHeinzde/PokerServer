package org.bonn.pokerserver.poker.common.interfaces;

import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.StakeLevel;

/**
 * An interface describing a PokerTableList
 */
public interface TableList {

    /**
     * Returns a PotLimitOmahaTable by its ID
     * @param tableId The table-id of the table
     * @return A PotLimitOmahaObject or null if the table does not exist on the server
     */
    PotLimitOmahaTable getPotLimitOmahaTableById(String tableId);

    /**
     * Creates a new table and returns its uuid
     * @param stakeLevel The stakeLevel for which the table should be created
     * @return The uuid of the new table
     */
    String createNewTable(StakeLevel stakeLevel);

    /**
     * Removes a table from the table list
     * @param tableId The id of the table
     * @return True if the table was contained and removed false if the table was not contained in the table list
     */
    boolean removeTable(String tableId);
}
