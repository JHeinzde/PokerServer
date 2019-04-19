package org.bonn.pokerserver.poker.common.comparators;

import org.bonn.pokerserver.poker.game.entities.Board;
import org.bonn.pokerserver.poker.game.entities.Card;
import org.bonn.pokerserver.poker.game.entities.HandValue;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Stage;
import org.bonn.pokerserver.poker.game.entities.enums.Suit;
import org.bonn.pokerserver.poker.game.entities.enums.Value;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class HandValueComparatorTest {

    private Comparator<HandValue> handValueComparator = new HandValueComparator();

    @Test
    public void testFirstHandLosesPreFlop() {
        HandValue testHandValue = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, null);
        HandValue testHandValue2 = HandValue.newHandValue(HandValueType.PAIR, Value.ACE, null, null);

        int result = handValueComparator.compare(testHandValue, testHandValue2);

        assertEquals(-1, result);
    }

    @Test
    public void testFirstHandWinsPreFlop() {
        HandValue testHandValue = HandValue.newHandValue(HandValueType.PAIR, Value.ACE, null, null);
        HandValue testHandValue2 = HandValue.newHandValue(HandValueType.PAIR, Value.KING, null, null);

        int result = handValueComparator.compare(testHandValue, testHandValue2);

        assertEquals(1, result);
    }

    @Test
    public void testHandsAreTied() {
        HandValue testHandValue = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, null);
        HandValue testHandValue2 = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, null);

        int result = handValueComparator.compare(testHandValue, testHandValue2);

        assertEquals(0, result);
    }

    @Test
    public void testFirstHandLosesAfterFlop() {
        HandValue pairedBoard = HandValue.newHandValue(HandValueType.PAIR, Value.ACE, null, getPairedBoard());
        HandValue highCardBoard = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, getHighCardBoard());

        int result = handValueComparator.compare(highCardBoard, pairedBoard);

        assertEquals(-1, result);
    }


    @Test
    public void testFirstHandWinsAfterFlop() {
        HandValue highCardAceValue = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, getHighCardBoard());
        HandValue highCardAceValueTwo = HandValue.newHandValue(HandValueType.HIGHCARD, Value.ACE, Value.KING, getSecondHighCardBoard());

        int result = handValueComparator.compare(highCardAceValue, highCardAceValueTwo);

        assertEquals(1, result);
    }

    @Test
    public void testFlushAfterFlop() {
        HandValue twoHighFlush = HandValue.newHandValue(HandValueType.FLUSH, Value.ACE, Value.KING, getTwoHighFlush());
        HandValue threeHighFlush = HandValue.newHandValue(HandValueType.FLUSH, Value.ACE, Value.KING, getThreeHighFlus());

        int result = handValueComparator.compare(twoHighFlush, threeHighFlush);

        assertEquals(-1, result);
    }


    @Test
    public void testTiedHandAfterFlop() {
        HandValue pairedHand = HandValue.newHandValue(HandValueType.PAIR, Value.ACE, null, getPairedBoard());
        HandValue samePairedHand = HandValue.newHandValue(HandValueType.PAIR, Value.ACE, null, getPairedBoard());

        int result = handValueComparator.compare(pairedHand, samePairedHand);

        assertEquals(0, result);
    }

    private Set<Card> getThreeHighFlus() {
        return new HashSet<>(Arrays.asList(
                Card.newCard(Value.ACE, Suit.CLUB),
                Card.newCard(Value.JACK, Suit.CLUB),
                Card.newCard(Value.KING, Suit.CLUB),
                Card.newCard(Value.QUEEN, Suit.CLUB),
                Card.newCard(Value.THREE, Suit.CLUB)));

    }

    private Set<Card> getTwoHighFlush() {

        return new HashSet<>(Arrays.asList(
                Card.newCard(Value.ACE, Suit.CLUB),
                Card.newCard(Value.JACK, Suit.CLUB),
                Card.newCard(Value.KING, Suit.CLUB),
                Card.newCard(Value.QUEEN, Suit.CLUB),
                Card.newCard(Value.TWO, Suit.CLUB)));
    }


    private Set<Card> getSecondHighCardBoard() {

        return new HashSet<>(Arrays.asList(
                Card.newCard(Value.ACE, Suit.CLUB),
                Card.newCard(Value.JACK, Suit.SPADE),
                Card.newCard(Value.KING, Suit.HEART),
                Card.newCard(Value.QUEEN, Suit.DIAMOND),
                Card.newCard(Value.TWO, Suit.CLUB)));
    }

    private Set<Card> getHighCardBoard() {

        return new HashSet<>(Arrays.asList(
                Card.newCard(Value.ACE, Suit.CLUB),
                Card.newCard(Value.JACK, Suit.SPADE),
                Card.newCard(Value.KING, Suit.HEART),
                Card.newCard(Value.QUEEN, Suit.DIAMOND),
                Card.newCard(Value.THREE, Suit.CLUB)));
    }


    private Set<Card> getPairedBoard() {

        return new HashSet<>(Arrays.asList(
                Card.newCard(Value.ACE, Suit.CLUB),
                Card.newCard(Value.ACE, Suit.SPADE),
                Card.newCard(Value.KING, Suit.HEART),
                Card.newCard(Value.QUEEN, Suit.DIAMOND),
                Card.newCard(Value.TWO, Suit.CLUB)));
    }

}