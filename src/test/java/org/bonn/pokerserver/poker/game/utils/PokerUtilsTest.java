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

public class PokerUtilsTest {


    private Set<Card> testCards = new HashSet<>();

    @After
    public void tearDown() {
        testCards.clear();
    }

    @Test
    public void testHandValueHighCard() {
        PokerUtils pokerUtils = PokerUtils.newPokerUtils();
        setHighCardScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.HIGHCARD, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }

    @Test
    public void testHandValuePair() {
        PokerUtils pokerUtils = PokerUtils.newPokerUtils();
        setPairedHandScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.PAIR, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }

    @Test
    public void testTwoPair() {
        PokerUtils pokerUtils = PokerUtils.newPokerUtils();
        setPairedHandScenario();

        HandValue resultHandValue = pokerUtils.calculateHandValue(testCards);

        assertEquals(HandValueType.TWO_PAIR, resultHandValue.getHandType());
        assertEquals(Value.ACE, resultHandValue.getFirstCardValue());
    }


    private void setHighCardScenario() {
        testCards.add(Card.newCard(Value.ACE, Suit.SPADE));
        testCards.add(Card.newCard(Value.QUEEN, Suit.SPADE));
        testCards.add(Card.newCard(Value.TEN, Suit.CLUB));
        testCards.add(Card.newCard(Value.EIGHT, Suit.DIAMOND));
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