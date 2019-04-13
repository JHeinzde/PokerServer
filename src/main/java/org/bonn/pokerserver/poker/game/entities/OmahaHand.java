package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.exceptions.TechnicalGameException;

import java.util.Collections;
import java.util.Set;

public class OmahaHand extends Hand {

    private static final int MAX_HAND_CARDS = 4;

    private OmahaHand(Set<Card> handCards, Board board) {
        super(handCards);
        if (handCards.size() != MAX_HAND_CARDS) {
            throw new TechnicalGameException(String.format("An omaha hand can only have 4 cards, but %d where given",
                    handCards.size()));
        }
        calculateCurrentHandValue(board);
    }

    @Override
    public Set<Card> getCards() {
        return Collections.unmodifiableSet(this.cards);
    }

    @Override
    public boolean winsAgainst(Hand otherHand) {

        if ((this.handValue.getHandType() == HandValueType.FLUSH && otherHand.getHandValue().getHandType() == HandValueType.FLUSH)
                || (this.handValue.getHandType() == HandValueType.HIGHCARD && otherHand.getHandValue().getHandType() == HandValueType.HIGHCARD)) {

        }


    }

    @Override
    protected void calculateCurrentHandValue(Board communityCards) {

    }


    private HandValue calculateHandValue(Set<Card> subSetOfHand) {
        return null;
    }


    /**
     * Creates a new Hand object
     *
     * @param cards The hand cards
     * @param board The current board. Used to calculate the current HandValue
     * @return A new Hand object created by using the input parameters
     */
    public static Hand newHand(Set<Card> cards, Board board) {
        return new OmahaHand(cards, board);
    }
}
