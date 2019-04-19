package org.bonn.pokerserver.poker.game.entities;

import org.bonn.pokerserver.poker.game.entities.enums.*;
import org.bonn.pokerserver.poker.game.exceptions.TechnicalGameException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OmahaHandTest {

    private Set<Card> cards;

    @Before
    public void setUp() {
        cards = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            cards.add(Card.newCard(Value.values()[i], Suit.values()[i]));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testImmutabilityOfGetCards() {
        Hand testHand = OmahaHand.newHand(cards, Board.newBoard(cards, Stage.PRE_FLOP));

        Set<Card> handCards = testHand.getHandCards();
        handCards.remove(Card.newCard(Value.THREE, Suit.SPADE));
        // Test if the returned list is immutable
        assertEquals(4, handCards.size());
    }

    @Test(expected = TechnicalGameException.class)
    public void testForExceptionWhenMoreThanFourCards() {
        cards.add(Card.newCard(Value.ACE, Suit.CLUB));

        OmahaHand.newHand(cards, null);
    }

    @Test(expected = TechnicalGameException.class)
    public void testForExceptionHandlingWhenLessThanFourCards() {
        cards.remove(Card.newCard(Value.THREE, Suit.SPADE));

        OmahaHand.newHand(cards, Board.newBoard(new HashSet<>(), Stage.PRE_FLOP));
    }

    @Test
    public void testFlopCalculation() {
        Set<Card> boardCards = new HashSet<>();
        boardCards.add(Card.newCard(Value.ACE, Suit.HEART));
        boardCards.add(Card.newCard(Value.KING, Suit.SPADE));
        boardCards.add(Card.newCard(Value.QUEEN, Suit.SPADE));

        Board board = Board.newBoard(boardCards, Stage.FLOP);

        // In this context the highCardHand is also
        Set<Card> handCards = getBroadWayHand();
        Hand acePairHand = OmahaHand.newHand(handCards, board);

        assertEquals(HandValueType.TWO_PAIR, acePairHand.getHandValue().getHandType());
        assertEquals(Value.ACE, acePairHand.getHandValue().getFirstCardValue());
    }

    @Test
    public void winsAgainstTest() {
        Board board = Board.newBoard(new HashSet<>(), Stage.PRE_FLOP);
        Hand highCardHand = OmahaHand.newHand(getBroadWayHand(), board);
        Hand pairHand = OmahaHand.newHand(getPocketPairHand(), board);

        assertEquals(Comparison.WIN, pairHand.winsAgainst(highCardHand));
        assertEquals(Comparison.LOSS, highCardHand.winsAgainst(pairHand));

        Hand tiePairHand = OmahaHand.newHand(getTieHandForPocketPair(), board);

        assertEquals(Comparison.TIE, pairHand.winsAgainst(tiePairHand));
    }

    private static Set<Card> getTieHandForPocketPair(){
        Set<Card> pairHandList = new HashSet<>();
        pairHandList.add(Card.newCard(Value.ACE, Suit.HEART));
        pairHandList.add(Card.newCard(Value.ACE, Suit.CLUB));
        pairHandList.add(Card.newCard(Value.KING, Suit.DIAMOND));
        pairHandList.add(Card.newCard(Value.KING, Suit.SPADE));

        return pairHandList;
    }


    // Utility methods

    private static Set<Card> getPocketPairHand() {
        Set<Card> pairHandList = new HashSet<>();
        pairHandList.add(Card.newCard(Value.ACE, Suit.SPADE));
        pairHandList.add(Card.newCard(Value.ACE, Suit.DIAMOND));
        pairHandList.add(Card.newCard(Value.KING, Suit.CLUB));
        pairHandList.add(Card.newCard(Value.KING, Suit.HEART));

        return pairHandList;
    }


    private static Set<Card> getBroadWayHand() {
        Set<Card> boardWayHand = new HashSet<>();
        boardWayHand.add(Card.newCard(Value.ACE, Suit.SPADE));
        boardWayHand.add(Card.newCard(Value.KING, Suit.DIAMOND));
        boardWayHand.add(Card.newCard(Value.JACK, Suit.CLUB));
        boardWayHand.add(Card.newCard(Value.QUEEN, Suit.HEART));

        return boardWayHand;
    }
}