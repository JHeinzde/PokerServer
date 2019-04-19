package org.bonn.pokerserver.poker.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationUtilsTest {

    private static final String CORRECT_UUID = "33917209-3e19-4bf4-bdde-083776fd2f22";
    private static final String INCORRECT_CHARACTER_UUID = "33917209-3e19-4bf4-bdde-083776fd2fxx";
    private static final String INCORRECT_LENGTH_UUID = "33917209-3e19-4bf4-bdde-0837";
    private static final String MISSING_MINUS = "33917209-3e19-4bf4-bdde083776fd2f22";

    private static final ValidationUtils validationUtils = ValidationUtils.getValidationUtils();

    @Test
    public void checkCorrectUUID() {
        assertTrue(validationUtils.validateUUID(CORRECT_UUID));
    }

    @Test
    public void checkIncorrectCharacterUUID() {
        assertFalse(validationUtils.validateUUID(INCORRECT_CHARACTER_UUID));
    }

    @Test
    public void checkIncorrectLengthUUID() {
        assertFalse(validationUtils.validateUUID(INCORRECT_LENGTH_UUID));
    }

    @Test
    public void checkMisingMinusUUID() {
        assertFalse(validationUtils.validateUUID(MISSING_MINUS));
    }

}