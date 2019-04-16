package org.bonn.pokerserver.poker.game.entities;

import com.google.common.collect.Sets;
import org.bonn.pokerserver.poker.common.interfaces.Observer;
import org.bonn.pokerserver.poker.game.entities.enums.Comparison;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Stage;
import org.bonn.pokerserver.poker.game.exceptions.TechnicalGameException;
import org.bonn.pokerserver.poker.game.utils.PokerUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a pot limit omaha poker hand
 */
public class OmahaHand extends Hand implements Observer {

    private static final int AMOUNT_OF_HAND_CARDS = 4;
    private static final int AMOUNT_OF_CARDS_FOR_VALUE_CALCULATION = 2;
    private static final int AMOUNT_OF_BOARD_CARDS_FOR_VALUE_CALCULATION = 3;

    private static final PokerUtils pokerUtils = PokerUtils.getPokerUtils();


    private OmahaHand(Set<Card> handCards, Board communityCards) {
        super(handCards, communityCards);
        if (handCards.size() != AMOUNT_OF_HAND_CARDS) {
            throw new TechnicalGameException(String.format("An omaha hand can only have 4 handCards, but %d where given",
                    handCards.size()));
        }
        calculateCurrentHandValue();
    }

    /**
     * Returns an unmodifiable view on the handCards contained in this omaha hand
     *
     * @return An unmodifiable card set
     */
    @Override
    public Set<Card> getHandCards() {
        return Collections.unmodifiableSet(this.handCards);
    }

    /**
     * This method checks if this omaha hand wins against another omaha hand
     *
     * @param otherHand The other hand that is used for the check
     * @return Comparison.WIN when winning Comparison.TIE when tie and Comparison.LOSS if loss
     */
    @Override
    public Comparison winsAgainst(Hand otherHand) {
        if (tie(otherHand)) {
            return Comparison.TIE;
        } else if (loss(otherHand)) {
            return Comparison.LOSS;
        } else {
            return Comparison.WIN;
        }
    }

    private boolean loss(Hand otherHand) {
        return this.handValue.getHandType().getNumericHandValue()
                < otherHand.getHandValue().getHandType().getNumericHandValue();
    }

    // TODO: Refactor this method to assure the quality contract is met
    private boolean tie(Hand otherHand) {
        if (this.communityCards.getCommunityCards() == null) {
            boolean handValueTypeComparison = this.handValue.getHandType().getNumericHandValue()
                    == otherHand.getHandValue().getHandType().getNumericHandValue();
            if (handValueTypeComparison) {
                return this.handValue.getFirstCardValue().getNumericValue() == otherHand.getHandValue().getFirstCardValue().getNumericValue()
                        && handValue.getSecondCardValue().getNumericValue() == otherHand.getHandValue().getFirstCardValue().getNumericValue();
            }
        }

        if (this.getHandValue().getHandType().getNumericHandValue() == otherHand.getHandValue().getHandType().getNumericHandValue()) {
            if (this.handValue.getHandType() == HandValueType.FLUSH || this.handValue.getHandType() == HandValueType.HIGHCARD) {
                List<Card> cardsOfThisHand = new LinkedList<>(this.handValue.getBoard());
                List<Card> cardsOfTheOtherHand = new LinkedList<>(otherHand.getHandValue().getBoard());

                cardsOfThisHand.sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));
                cardsOfTheOtherHand.sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));

                for (int i = 0; i < cardsOfThisHand.size(); i++) {
                    if (cardsOfThisHand.get(i).getValue().getNumericValue()
                            != cardsOfTheOtherHand.get(i).getValue().getNumericValue()) {
                        return false;
                    }
                }

            } else {

                boolean firstCardCompare = this.handValue.getFirstCardValue().getNumericValue()
                        == otherHand.getHandValue().getHandType().getNumericHandValue();
                return firstCardCompare || (this.handValue.getSecondCardValue().getNumericValue()
                        == otherHand.getHandValue().getSecondCardValue().getNumericValue());
            }
        }
        return true;
    }


    @Override
    protected void calculateCurrentHandValue() {
        if (communityCards.getStageOfBoard() == Stage.PRE_FLOP) {
            this.handValue = pokerUtils.calculateOmahaPreFlop(handCards);
        } else {
            this.handValue = Sets.powerSet(this.handCards)
                    .stream()
                    .filter(subSetOfHandCards -> subSetOfHandCards.size() == AMOUNT_OF_CARDS_FOR_VALUE_CALCULATION)
                    .map(this::calculateHandValue)
                    .sorted(Comparator
                            .comparingInt(handValue -> handValue
                                    .getHandType()
                                    .getNumericHandValue()))
                    .max(Comparator.comparingInt(handValue -> handValue.getHandType().getNumericHandValue()))
                    .orElseThrow();
        }
    }


    private HandValue calculateHandValue(Set<Card> subSetOfHand) {
        Set<Set<Card>> subSetOfBoardCards = Sets.powerSet(this.communityCards.getCommunityCards())
                .stream()
                .filter(subSet -> subSet.size() == AMOUNT_OF_BOARD_CARDS_FOR_VALUE_CALCULATION)
                .collect(Collectors.toSet());

        List<HandValue> handValues = new LinkedList<>();

        for (Set<Card> subSet : subSetOfBoardCards) {
            Set<Card> possibleBoard = new HashSet<>();
            possibleBoard.addAll(subSet);
            possibleBoard.addAll(subSetOfHand);
            handValues.add(pokerUtils.calculateHandValue(possibleBoard));
        }

        return handValues
                .stream()
                .max(Comparator
                        .comparingInt(handValue ->
                                handValue.getHandType().getNumericHandValue()))
                .orElseThrow();
    }


    /**
     * Creates a new Hand object
     *
     * @param cards The hand handCards
     * @param board The current communityCards. Used to calculate the current HandValue
     * @return A new Hand object created by using the input parameters
     */
    public static Hand newHand(Set<Card> cards, Board board) {
        return new OmahaHand(cards, board);
    }


    @Override
    public void notifyObserver() {
        calculateCurrentHandValue();
    }
}
