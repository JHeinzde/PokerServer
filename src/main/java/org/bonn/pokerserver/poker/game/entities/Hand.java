package org.bonn.pokerserver.poker.game.entities;

import java.util.Collections;
import java.util.Set;

public abstract class Hand {

    protected Set<Card> cards;
    protected HandValue handValue;

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
