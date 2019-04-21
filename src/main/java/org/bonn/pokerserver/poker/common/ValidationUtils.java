package org.bonn.pokerserver.poker.common;

import java.util.regex.Pattern;

/**
 * This class handles basic user input validation
 */
public class ValidationUtils {

    private static final ValidationUtils singletonReference = new ValidationUtils();

    private static final Pattern UUID_PATTERN = Pattern
            .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private ValidationUtils() {
        // This is just to hide the constructor from the outside
    }

    /**
     * Checks if the given table id is a uuid
     *
     * @param tableId The id as string to check
     * @return True if uuid else false
     */
    public boolean validateTableId(String tableId) {
        return validateUUID(tableId);
    }

    /**
     * Checks if the given player id is a uuid
     *
     * @param playerId The id as string to check
     * @return True if uuid else false
     */
    public boolean validatePlayerId(String playerId) {
        return validateUUID(playerId);
    }

    /**
     * Validates if the given string is a uuid
     * @param uuid The string with the potential uuid
     * @return True if uuid else false
     */
    public boolean validateUUID(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }


    public static ValidationUtils getValidationUtils() {
        return singletonReference;
    }
}
