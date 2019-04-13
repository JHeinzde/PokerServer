package org.bonn.pokerserver.poker.game.entities;

import java.util.Collections;
import java.util.Set;

public abstract class Hand {

    /**
     * This wont change, since the hand cards are fixed after dealing them
     */
    protected final Set<Card> cards;

    /**
     * This HandValue will change depending on pre flop, flop, turn and river
     */
    protected  HandValue handValue;

    Hand(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Card> getCards() {
        return Collections.unmodifiableSet(cards);
    }

    public HandValue getHandValue() {
        return handValue;
    }

    public abstract boolean winsAgainst(Hand otherHand);

    protected abstract void calculateCurrentHandValue(Board board);

}
