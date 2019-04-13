package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Value;

/**
 * A class representing the value of a poker hand
 */
public class HandValue {

    private final HandValueType handType;
    private final Value firstCardValue;
    private final Value secondCardValue;

    private HandValue(HandValueType handType, Value firstCardValue, Value secondCardValue) {
        this.handType = handType;
        this.firstCardValue = firstCardValue;
        this.secondCardValue = secondCardValue;
    }

    /**
     * If the HandValueType is either a high card, pair or three of a kind, this method
     * returns the value of the cards involved
     *
     * @return The value of the cards involved or null
     */
    public Value getFirstCardValue() {
        return firstCardValue;
    }

    /**
     * If the hand value is a two pair this contains the value of the second pair.
     * Otherwise this methods returns nil
     * @return null or value of the second pair
     */
    public Value getSecondCardValue() {
        return secondCardValue;
    }


    /**
     * Returns the HandTypeEnum of this HandValue
     *
     * @return The HandValueTypeEnum of this HandValue
     */
    public HandValueType getHandType() {
        return handType;
    }

    /**
     * This method creates a new hand value object
     * @param handType The type of the hand value (HIGHCARD, PAIR, TWO_PAIR ...)
     * @param firstCardValue In case of a high card, pair, two pair or three of a kind this is the value of the first card
     * @param secondCardValue Value of the second pair in case of TWO_PAIR handType
     * @return A new HandValue object created from the input parameters
     */
    public static HandValue newHandValue(HandValueType handType, Value firstCardValue, Value secondCardValue) {
        return new HandValue(handType, firstCardValue, secondCardValue);
    }
}
