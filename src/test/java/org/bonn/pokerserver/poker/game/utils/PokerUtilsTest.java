package org.bonn.pokerserver.poker.game.utils;

import org.bonn.pokerserver.poker.game.entities.Card;
import org.bonn.pokerserver.poker.game.entities.HandValue;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Suit;
import org.bonn.pokerserver.poker.game.entities.enums.Value;
import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PokerUtilsTest {


    private Set<Card> testCards = new HashSet<>();

    private PokerUtils pokerUtils = PokerUtils.getPokerUtils();

    @After
    public void tearDown() {
        testCards.clear();
    }

    @Test
    public void testHandValueHighCard() {
        setHighCardScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.HIGHCARD, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }

    @Test
    public void testHandValuePair() {
        PokerUtils pokerUtils = PokerUtils.getPokerUtils();
        setPairedHandScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.PAIR, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }

    @Test
    public void testTwoPair() {
        setTwoPairHandScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.TWO_PAIR, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertEquals(Value.KING, resultHandValue.getSecondCardValue());
    }

    @Test
    public void testThreeOfAKind() {
        setThreeOfAKindScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.THREE_OF_AKIND, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }

    @Test
    public void testQuads() {
        setQuadsScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.FOUR_OF_A_KIND, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertNull(resultHandValue.getSecondCardValue());
    }

    @Test
    public void testFullHouse() {
        setFullHouseScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.FULL_HOUSE, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertEquals(Value.KING, resultHandValue.getSecondCardValue());
    }

    @Test
    public void testStraight() {
        setStraightScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.STRAIGHT, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertNull(resultHandValue.getSecondCardValue());
    }

    @Test
    public void testFlush() {
        setFlushScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.FLUSH, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertEquals(Value.KING, resultHandValue.getSecondCardValue());
    }

    @Test
    public void testStraightFlush() {
        setStraightFlushScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.STRAIGHT_FLUSH, resultHandValue.getHandType());
        assertEquals(Value.KING, resultHandValue.getFirstCardValue());
        assertEquals(Value.KING, resultHandValue.getSecondCardValue());
    }

    @Test
    public void testRoyalFlush() {
        setRoyalFlushScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.ROYAL_FLUSH, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertEquals(Value.ACE, resultHandValue.getSecondCardValue());
    }

    @Test
    public void testPreFlopCalculation() {
        HandValue resultHandValue = pokerUtils.calculateOmahaPreFlop(getBroadWayHand());

        assertEquals(HandValueType.HIGHCARD, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertEquals(Value.KING, resultHandValue.getSecondCardValue());

        resultHandValue = pokerUtils.calculateOmahaPreFlop(getPocketPairHand());

        assertEquals(HandValueType.PAIR, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
        assertNull(resultHandValue.getSecondCardValue());
    }

    // Utility methods

    private Set<Card> getPocketPairHand() {
        Set<Card> pairHandList = new HashSet<>();
        pairHandList.add(Card.newCard(Value.ACE, Suit.SPADE));
        pairHandList.add(Card.newCard(Value.ACE, Suit.DIAMOND));
        pairHandList.add(Card.newCard(Value.KING, Suit.CLUB));
        pairHandList.add(Card.newCard(Value.KING, Suit.HEART));

        return pairHandList;
    }


    private Set<Card> getBroadWayHand() {
        Set<Card> boardWayHand = new HashSet<>();
        boardWayHand.add(Card.newCard(Value.ACE, Suit.SPADE));
        boardWayHand.add(Card.newCard(Value.KING, Suit.DIAMOND));
        boardWayHand.add(Card.newCard(Value.JACK, Suit.CLUB));
        boardWayHand.add(Card.newCard(Value.QUEEN, Suit.HEART));

        return boardWayHand;
    }

    private void setRoyalFlushScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.CLUB));
        testCards.add(Card.newCard(Value.KING, Suit.CLUB));
        testCards.add(Card.newCard(Value.QUEEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.JACK, Suit.CLUB));
        testCards.add(Card.newCard(Value.TEN, Suit.CLUB));
    }

    private void setStraightFlushScenario() {
        testCards.add(Card.newCard(Value.KING, Suit.CLUB));
        testCards.add(Card.newCard(Value.QUEEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.JACK, Suit.CLUB));
        testCards.add(Card.newCard(Value.TEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.NINE, Suit.CLUB));
    }

    private void setQuadsScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.CLUB));
        testCards.add(Card.newCard(Value.ACE, Suit.HEART));
        testCards.add(Card.newCard(Value.ACE, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.KING, Suit.HEART));
    }

    private void setFullHouseScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.CLUB));
        testCards.add(Card.newCard(Value.ACE, Suit.HEART));
        testCards.add(Card.newCard(Value.ACE, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.KING, Suit.CLUB));
        testCards.add(Card.newCard(Value.KING, Suit.HEART));
    }


    private void setFlushScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.CLUB));
        testCards.add(Card.newCard(Value.KING, Suit.CLUB));
        testCards.add(Card.newCard(Value.QUEEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.JACK, Suit.CLUB));
        testCards.add(Card.newCard(Value.NINE, Suit.CLUB));
    }

    private void setStraightScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.KING, Suit.HEART));
        testCards.add(Card.newCard(Value.QUEEN, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.JACK, Suit.CLUB));
        testCards.add(Card.newCard(Value.TEN, Suit.CLUB));
    }

    private void setThreeOfAKindScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.ACE, Suit.HEART));
        testCards.add(Card.newCard(Value.ACE, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.THREE, Suit.CLUB));
        testCards.add(Card.newCard(Value.FOUR, Suit.CLUB));
    }


    private void setHighCardScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.QUEEN, Suit.SPADE));
        testCards.add(Card.newCard(Value.TEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.EIGHT, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.TWO, Suit.HEART));
    }

    private void setTwoPairHandScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.ACE, Suit.HEART));
        testCards.add(Card.newCard(Value.KING, Suit.SPADE));
        testCards.add(Card.newCard(Value.KING, Suit.HEART));
        testCards.add(Card.newCard(Value.TWO, Suit.HEART));
    }

    private void setPairedHandScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.QUEEN, Suit.SPADE));
        testCards.add(Card.newCard(Value.ACE, Suit.CLUB));
        testCards.add(Card.newCard(Value.EIGHT, Suit.DIAMOND));
        testCards.add(Card.newCard(Value.TWO, Suit.HEART));
    }

}