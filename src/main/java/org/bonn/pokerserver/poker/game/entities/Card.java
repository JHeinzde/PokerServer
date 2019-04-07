package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.enums.Suit;
import org.bonn.pokerserver.poker.game.entities.enums.Value;

import java.util.Objects;

/**
 * A class representing a playing card
 */
public class Card {

    private Value value;
    private Suit suit;

    private Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Returns the suit of the card
     * @return The suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the card value e.g. ACE, KING, QUEEN ...
     * @return The value of the card
     */
    public Value getValue() {
        return value;
    }

    /**
     *
     * Creates a new card object from the given values
     * @param value The value of the card.
     * @param suit The suit of the card
     * @return The card created from the given input parameters
     */
    public static Card newCard(Value value, Suit suit)  {
        return new Card(value, suit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
}
