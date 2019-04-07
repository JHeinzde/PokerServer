package org.bonn.pokerserver.poker.game.entities;

import com.google.common.collect.Sets;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Value;
import org.bonn.pokerserver.poker.game.exceptions.TechnicalGameException;

import java.util.*;

public class OmahaHand extends Hand {

    public static final int MAX_HAND_CARDS = 4;

    private OmahaHand(Set<Card> handCards, Board board) {
        super(handCards);
        if (handCards.size() != MAX_HAND_CARDS) {
            throw new TechnicalGameException(String.format("An omaha hand can only have 4 cards, but %d where given", handCards.size()));
        }
        calculateCurrentHandValue(board);
    }

    @Override
    public Set<Card> getCards() {
        return Collections.unmodifiableSet(this.cards);
    }

    @Override
    public boolean winsAgainst(Hand otherHand) {
        boolean returnValue = otherHand.handValue.getHandType().getNumericHandValue() < this.handValue.getHandType().getNumericHandValue();

        if (this.handValue.getFirstCardValue() == null || otherHand.getHandValue().getFirstCardValue() == null
                || this.handValue.getHandType().getNumericHandValue() != otherHand.getHandValue().getHandType().getNumericHandValue()) {
            return returnValue;
        } else {
            return returnValue && otherHand.handValue.getFirstCardValue().getNumericValue() < otherHand.getHandValue().getFirstCardValue().getNumericValue();
        }
    }

    @Override
    protected void calculateCurrentHandValue(Board communityCards) {
        switch (communityCards.getStageOfBoard()) {
            case PRE_FLOP:
                calculatePreFlop();
                break;
            case FLOP:
                calculateFlop(communityCards);
                break;
            case TURN:
                calculateTurn(communityCards);
                break;
            case RIVER:
                calculateRiver(communityCards);
                break;
        }
    }

    private void calculateFlop(Board communityCards) {
        Set<Set<Card>> powerSetOfHandCards = Sets.powerSet(communityCards.getCommunityCards());
        Set<HandValue> handValues = new HashSet<>();

        powerSetOfHandCards.forEach( subSetOfHand -> {
            Set<Card> handPlusBoard = new HashSet<>();
            handPlusBoard.addAll(communityCards.getCommunityCards());
            handPlusBoard.addAll(subSetOfHand);


            handValues.add(calculateHandValue(handPlusBoard));
        });
    }

    private void calculateTurn(Board communityCards){

    }

    private void calculateRiver(Board communityCards){

    }

    private HandValue calculateHandValue(Set<Card> subSetOfHand) {
        return null;
    }

    /**
     * Calculates the hand value for a pre flop
     */
    private void calculatePreFlop() {
        Map<Value, List<Card>> cardsMappedByValue = new EnumMap<>(Value.class);

        for (Value value : Value.values()) {
            cardsMappedByValue.put(value, new LinkedList<>());
        }
        this.cards.forEach(card -> cardsMappedByValue.get(card.getValue()).add(card));

        Value maxPair = null;
        Value highCard = Value.TWO;
        // There will always be a high card so this can be initialized

        for (Map.Entry<Value, List<Card>> entry : cardsMappedByValue.entrySet()) {
            List<Card> listOfCards = entry.getValue();
            Value cardValue = entry.getKey();

            if (listOfCards.size() >= 2 && maxPair == null) {
                maxPair = cardValue;
            }

            if (listOfCards.size() >= 2 && maxPair.getNumericValue() < cardValue.getNumericValue()) {
                maxPair = listOfCards.get(0).getValue();
            }

            if (listOfCards.size() == 1 && highCard.getNumericValue() < cardValue.getNumericValue()) {
                highCard = cardValue;
            }
        }

        if (maxPair == null) {
            this.handValue = HandValue.newHandValue(HandValueType.HIGHCARD, highCard, null);
        } else {
            this.handValue = HandValue.newHandValue(HandValueType.PAIR, maxPair, null);
        }
    }

    /**
     * Creates a new Hand object
     * @param cards The hand cards
     * @param board The current board. Used to calculate the current HandValue
     * @return A new Hand object created by using the input parameters
     */
    public static Hand newHand(Set<Card> cards, Board board) {
        return new OmahaHand(cards, board);
    }
}
