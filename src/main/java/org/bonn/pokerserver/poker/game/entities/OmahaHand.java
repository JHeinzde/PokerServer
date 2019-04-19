package org.bonn.pokerserver.poker.game.entities;

import com.google.common.collect.Sets;
import org.bonn.pokerserver.poker.common.comparators.HandValueComparator;
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
        Comparator<HandValue> handValueComparator = new HandValueComparator();
        int resultOfComparison = handValueComparator.compare(this.handValue, otherHand.getHandValue());

        if (resultOfComparison < 0) {
            return Comparison.LOSS;
        } else if (resultOfComparison == 0){
            return Comparison.TIE;
        } else {
            return Comparison.WIN;
        }
    }



    @Override
    protected void calculateCurrentHandValue() {
        // If pre flop just call pokerUtils
        if (communityCards.getStageOfBoard() == Stage.PRE_FLOP) {
            this.handValue = pokerUtils.calculateOmahaPreFlop(handCards);
        } else {
            // If not pre flop build all subsets of size 2 and use them in calculation
            this.handValue = Sets.powerSet(this.handCards)
                    .stream()
                    .filter(subSetOfHandCards -> subSetOfHandCards.size() == AMOUNT_OF_CARDS_FOR_VALUE_CALCULATION)
                    .map(this::calculateHandValue)
                    .sorted(Comparator
                            .comparingInt(handValue -> handValue
                                    .getHandType()
                                    .getNumericHandValue()))
                    .max(new HandValueComparator())
                    .orElseThrow(IllegalAccessError::new);
        }
    }


    // Calculates a maximum HandValue from the given two card hand subset
    private HandValue calculateHandValue(Set<Card> subSetOfHand) {

        // Get all sub set of size 3 from the current community cards
        Set<Set<Card>> subSetOfBoardCards = Sets.powerSet(this.communityCards.getCommunityCards())
                .stream()
                .filter(subSet -> subSet.size() == AMOUNT_OF_BOARD_CARDS_FOR_VALUE_CALCULATION)
                .collect(Collectors.toSet());

        List<HandValue> handValues = new LinkedList<>();

        // For each possible three card sub set of the 3 or 4 or 5 community cards
        // Merge together calculate the hand value of the resulting board and add it to a list
        for (Set<Card> subSet : subSetOfBoardCards) {
            Set<Card> possibleBoard = new HashSet<>();
            possibleBoard.addAll(subSet);
            possibleBoard.addAll(subSetOfHand);
            handValues.add(pokerUtils.calculateHandValue(possibleBoard));
        }

        // Return the highest calculated hand value
        return handValues
                .stream()
                .max(new HandValueComparator())
                .orElseThrow(IllegalAccessError::new);
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
