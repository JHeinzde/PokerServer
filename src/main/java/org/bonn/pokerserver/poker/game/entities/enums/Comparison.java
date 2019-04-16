package org.bonn.pokerserver.poker.game.entities.enums;

public enum Comparison {
    TIE(0),
    WIN(1),
    LOSS(2);

    private final int numericValue;

    private Comparison(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
