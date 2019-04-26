package org.bonn.pokerserver.poker.game;

/**
 * The stake level wraps an integer. The integer is representing the stake level in cents for USD and Euro.
 * This implementaiton is only to be used in dollar and euros.
 */
public enum StakeLevel {

    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRET(100),
    ONE_HUNDRET_FIFTY(150),
    THREE_HUNDRED(300);

    private final Integer numericLevel;

    StakeLevel(Integer numericLevel) {
        this.numericLevel = numericLevel;
    }

    public Integer getNumericLevel() {
        return numericLevel;
    }
}
