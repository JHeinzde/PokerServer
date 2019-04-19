package org.bonn.pokerserver.poker.common.comparators;

import org.bonn.pokerserver.poker.game.entities.Card;
import org.bonn.pokerserver.poker.game.entities.HandValue;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Value;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HandValueComparator implements Comparator<HandValue> {

    @Override
    public int compare(HandValue handValue, HandValue handValue2) {
        // In this case its a pre flop situation
        if (handValue.getBoard() == null || handValue.getBoard().isEmpty()) {
            return preFlopCalculation(handValue, handValue2);
        }

        return afterFlopIsDealtCalculation(handValue, handValue2);
    }

    private int afterFlopIsDealtCalculation(HandValue handValue, HandValue handValue2) {
        int numericHandValue = handValue.getHandType().getNumericHandValue();
        int numericHandValue2 = handValue2.getHandType().getNumericHandValue();

        // Hand one loses against hand two
        if (numericHandValue < numericHandValue2) {
            return -1;

            // Hand one wins against hand two
        } else if (numericHandValue > numericHandValue2) {
            return 1;

            // Its a tie in handValue. Now the check against the actual cards comes in to determine the winner
        } else {

            // In case of a tie and handType equals Flush or Highcard the whole board must be compared to reach a
            // conclusive decision
            if (handValue.getHandType() == HandValueType.FLUSH || handValue.getHandType() == HandValueType.HIGHCARD) {
                return fullBoardCheck(handValue, handValue2);
            } else {
                // Else a fast check can occur.
                return fastCheck(handValue, handValue2);
            }
        }
    }


    // This check is performed in case of handType != Flush and Highcard
    private int fastCheck(HandValue handValue, HandValue handValue2) {
        if (handValue.getFirstCardValue().getNumericValue() < handValue2.getFirstCardValue().getNumericValue()) {
            return -1;
        } else if (handValue.getFirstCardValue().getNumericValue() > handValue2.getFirstCardValue().getNumericValue()) {
            return 1;
        } else if (handValue.getFirstCardValue().getNumericValue() == handValue2.getFirstCardValue().getNumericValue() &&
                handValue.getSecondCardValue() == null && handValue2.getSecondCardValue() == null) {
            return 0;
        } else {
            return Integer.compare(handValue.getSecondCardValue().getNumericValue(),
                    handValue2.getSecondCardValue().getNumericValue());
        }
    }

    private int fullBoardCheck(HandValue handValue, HandValue handValue2) {
        List<Card> boardSortedByCardValue = new LinkedList<>(handValue.getBoard());
        List<Card> boardSortedByCardValue2 = new LinkedList<>(handValue2.getBoard());

        boardSortedByCardValue.sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));
        boardSortedByCardValue2.sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));

        for (int i = 0; i < boardSortedByCardValue.size(); i++) {
            Value cardValue = boardSortedByCardValue.get(i).getValue();
            Value cardValueTwo = boardSortedByCardValue2.get(i).getValue();

            if (cardValue.getNumericValue() < cardValueTwo.getNumericValue()) {
                return -1;
            }
            if (cardValue.getNumericValue() > cardValueTwo.getNumericValue()) {
                return 1;
            }
        }

        return 0;
    }

    private int preFlopCalculation(HandValue handValue, HandValue handValue2) {
        int numericHandValue = handValue.getHandType().getNumericHandValue();
        int numericHandValue2 = handValue2.getHandType().getNumericHandValue();


        // Hand one loses against hand two
        if (numericHandValue < numericHandValue2) {
            return -1;

            // Hand one wins against hand two
        } else if (numericHandValue > numericHandValue2) {
            return 1;

            // Its a tie return the result of fast check
        } else {
            return fastCheck(handValue, handValue2);
        }
    }
}
