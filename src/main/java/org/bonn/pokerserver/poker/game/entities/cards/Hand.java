package org.bonn.pokerserver.poker.game.entities.cards;

import org.bonn.pokerserver.poker.game.entities.enums.Comparison;

import java.util.Collections;
import java.util.Set;

public abstract class Hand {

    /**
     * This wont change, since the hand handCards are fixed after dealing them
     */
    protected final Set<Card> handCards;
    protected final Board communityCards;

    /**
     * This HandValue will change depending on pre flop, flop, turn and river
     */
    protected HandValue handValue;

    Hand(Set<Card> handCards, Board communityCards) {
        this.handCards = handCards;
        this.communityCards = communityCards;
    }

    public Set<Card> getHandCards() {
        return Collections.unmodifiableSet(handCards);
    }

    public HandValue getHandValue() {
        return handValue;
    }

    public abstract Comparison winsAgainst(Hand otherHand);

    protected abstract void calculateCurrentHandValue();

}
