package org.bonn.pokerserver.poker.game.entities.enums;

public enum HandValueType {
    HIGHCARD(1), PAIR(2), TWO_PAIR(3), THREE_OF_AKIND(3), STRAIGHT(5), FLUSH(6), FULL_HOUSE(7), FOUR_OF_A_KIND(8), STRAIGHT_FLUSH(9), ROYAL_FLUSH(10);

    private int numericHandValue;

    private HandValueType(int numericHandValue) {
        this.numericHandValue = numericHandValue;
    }

    public int getNumericHandValue() {
        return numericHandValue;
    }
}
