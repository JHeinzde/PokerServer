package org.bonn.pokerserver.poker.game.utils;

import org.bonn.pokerserver.poker.game.entities.Card;
import org.bonn.pokerserver.poker.game.entities.HandValue;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Suit;
import org.bonn.pokerserver.poker.game.entities.enums.Value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is a singleton implementation of the PokerUtils class
 * It calculates different thing for a poker game.
 * <p>
 * TODO: Think about abstracting this completely via function maps.
 */
public class PokerUtils {

    private static final PokerUtils pokerUtils = new PokerUtils();

    private static final int FIRST_LIST_ELEMENT = 0;
    private static final int LAST_LIST_ELEMENT = 4;
    private static final int SECOND_LAST_ELEMENT = 3;

    private Map<HandValueType, Function<Set<Card>, Optional<HandValue>>> handValueFunctionMap = new EnumMap<>(HandValueType.class);

    private PokerUtils() {
        handValueFunctionMap.put(HandValueType.HIGHCARD, this::highCard);
        handValueFunctionMap.put(HandValueType.PAIR, this::pair);
        handValueFunctionMap.put(HandValueType.TWO_PAIR, this::twoPair);
        handValueFunctionMap.put(HandValueType.THREE_OF_AKIND, this::threeOfAKind);
        handValueFunctionMap.put(HandValueType.STRAIGHT, this::straight);
        handValueFunctionMap.put(HandValueType.FLUSH, this::flush);
        handValueFunctionMap.put(HandValueType.FULL_HOUSE, this::fullHouse);
        handValueFunctionMap.put(HandValueType.FOUR_OF_A_KIND, this::quads);
        handValueFunctionMap.put(HandValueType.STRAIGHT_FLUSH, this::straightFlush);
        handValueFunctionMap.put(HandValueType.ROYAL_FLUSH, this::royalFlush);
    }

    /**
     * This method calculates a HandValue form the Set of 5 cards given to the method. It calculates all all possible HandValues,
     * and returns the highest of them. So when this method is called, it returns the highest hand value possible with
     * the given five cards.
     * <p>
     * Warning this method does not calculate pre flop. It only calculates after the first three cards are dealt.
     * </p>
     *
     * @param allCards The set of cards that should be used to calculate a HandValue
     * @return The highest HandValue possible with these 5 cards
     */
    public HandValue calculateHandValue(Set<Card> allCards) {

        List<HandValue> sortedHandValues = new LinkedList<>();

        for (Map.Entry<HandValueType, Function<Set<Card>, Optional<HandValue>>> handValueTypeFunction : handValueFunctionMap.entrySet()) {
            handValueTypeFunction.getValue().apply(allCards).ifPresent(sortedHandValues::add);
        }

        sortedHandValues.sort(Comparator.comparingInt(handValue -> handValue.getHandType().getNumericHandValue()));


        return sortedHandValues.get(sortedHandValues.size() - 1);
    }

    private Optional<HandValue> highCard(Set<Card> allCards) {

        Map<Value, Card> cardsMappedByValue = new EnumMap<>(Value.class);

        allCards.forEach(card -> cardsMappedByValue.put(card.getValue(), card));

        List<Map.Entry<Value, Card>> sorted = cardsMappedByValue
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getValue().getNumericValue())).collect(Collectors.toList());

        Value highestCard = sorted.get(sorted.size() - 1).getKey();
        Value secondHighestCard = sorted.get(sorted.size() - 2).getKey();

        return Optional.of(HandValue.newHandValue(HandValueType.HIGHCARD, highestCard, secondHighestCard));
    }


    private Optional<HandValue> pair(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);

        Value maxPair = null;

        for (Map.Entry<Value, List<Card>> entry : cardsMappedByValue.entrySet()) {
            List<Card> listOfCards = entry.getValue();
            Value cardValue = entry.getKey();

            if (listOfCards.size() == 2 && maxPair == null) {
                maxPair = cardValue;
            }

            // maxPair will never be null here. The reason for this is that the if block above will always be triggered
            // before this if block, resulting in a set maxPair.
            if (listOfCards.size() == 2 && maxPair.getNumericValue() < cardValue.getNumericValue()) {
                maxPair = listOfCards.get(0).getValue();
            }
        }

        if (maxPair != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.PAIR, maxPair, null));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> twoPair(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);

        Value firstPair = null;
        Value secondPair = null;

        for (Map.Entry<Value, List<Card>> entry : cardsMappedByValue.entrySet()) {
            List<Card> listOfCards = entry.getValue();

            Value cardValue = entry.getKey();

            if (listOfCards.size() == 2 && firstPair == null) {
                firstPair = cardValue;
            }

            if (listOfCards.size() == 2 && firstPair != null && secondPair == null && firstPair != cardValue) {
                secondPair = entry.getKey();
            }
        }

        if (firstPair != null && secondPair != null && firstPair.getNumericValue() < secondPair.getNumericValue()) {
            Value tmp = firstPair;
            firstPair = secondPair;
            secondPair = tmp;
        }

        if (firstPair != null && secondPair != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.TWO_PAIR, firstPair, secondPair));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> threeOfAKind(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);

        Value cardValueOfTrips = null;

        for (Map.Entry<Value, List<Card>> entry : cardsMappedByValue.entrySet()) {
            int amountOfCardsInValueEntry = entry.getValue().size();

            if (amountOfCardsInValueEntry == 3) {
                cardValueOfTrips = entry.getKey();
            }
        }

        if (cardValueOfTrips != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.THREE_OF_AKIND, cardValueOfTrips, null));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> straight(Set<Card> allCards) {
        List<Card> sortedCards = new ArrayList<>(allCards);

        sortedCards.sort(Comparator.comparingInt(cardOne -> cardOne.getValue().getNumericValue()));

        int runningTotal = 0;
        int firstValue = 0;

        for (Card sortedCard : sortedCards) {
            if (firstValue == 0) {
                firstValue = sortedCard.getValue().getNumericValue();
                continue;
            }
            runningTotal = Math.abs(firstValue - sortedCard.getValue().getNumericValue());
        }

        int difference = sortedCards
                .get(LAST_LIST_ELEMENT)
                .getValue()
                .getNumericValue() - sortedCards
                .get(FIRST_LIST_ELEMENT)
                .getValue()
                .getNumericValue();

        if (runningTotal == 4 && difference == 4) {
            return Optional.of(HandValue.newHandValue(HandValueType.STRAIGHT,
                    sortedCards.get(LAST_LIST_ELEMENT).getValue(),
                    null));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> flush(Set<Card> allCards) {
        Map<Suit, List<Card>> cardsMappedBySuit = setupSuitCardMap(allCards);

        for (Map.Entry<Suit, List<Card>> mapEntries : cardsMappedBySuit.entrySet()) {

            if (mapEntries.getValue().size() == 5) {
                mapEntries.getValue().sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));
                return Optional.of(HandValue.newHandValue(HandValueType.FLUSH,
                        mapEntries.getValue().get(LAST_LIST_ELEMENT).getValue(),
                        mapEntries.getValue().get(SECOND_LAST_ELEMENT).getValue()));
            }

        }

        return Optional.empty();
    }

    private Optional<HandValue> fullHouse(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);


        Value tripsValue = null;
        Value pairValue = null;

        for (Map.Entry<Value, List<Card>> mapEntry : cardsMappedByValue.entrySet()) {
            int amountOfCardsOfOneValue = mapEntry.getValue().size();

            if (amountOfCardsOfOneValue == 3) {
                tripsValue = mapEntry.getKey();
            }

            if (amountOfCardsOfOneValue == 2) {
                pairValue = mapEntry.getKey();
            }
        }

        if (tripsValue != null && pairValue != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.FULL_HOUSE, tripsValue, pairValue));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> quads(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);

        Value quadsValue = null;

        for (Map.Entry<Value, List<Card>> mapEntry : cardsMappedByValue.entrySet()) {
            int amountOfCardsOfOneValue = mapEntry.getValue().size();

            if (amountOfCardsOfOneValue == 4) {
                quadsValue = mapEntry.getKey();
            }
        }

        if (quadsValue != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.FOUR_OF_A_KIND, quadsValue, null));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> straightFlush(Set<Card> allCards) {
        Optional<HandValue> possibleStraight = straight(allCards);
        Optional<HandValue> possibleFlush = flush(allCards);


        if (possibleStraight.isPresent() && possibleFlush.isPresent() && possibleStraight.get().getFirstCardValue() != Value.ACE &&
                possibleFlush.get().getFirstCardValue() != Value.ACE) {

            return Optional.of(HandValue.newHandValue(HandValueType
                            .STRAIGHT_FLUSH,
                    possibleFlush
                            .get()
                            .getFirstCardValue(),
                    possibleStraight
                            .get()
                            .getFirstCardValue()));
        } else {
            return Optional.empty();
        }
    }

    private Optional<HandValue> royalFlush(Set<Card> allCards) {
        Optional<HandValue> possibleStraight = straight(allCards);
        Optional<HandValue> possibleFlush = flush(allCards);


        if (possibleStraight.isPresent() && possibleFlush.isPresent() && possibleStraight.get().getFirstCardValue() == Value.ACE &&
                possibleFlush.get().getFirstCardValue() == Value.ACE) {
            return Optional.of(HandValue.newHandValue(HandValueType
                            .ROYAL_FLUSH,
                    possibleFlush
                            .get()
                            .getFirstCardValue(),
                    possibleStraight
                            .get()
                            .getFirstCardValue()));
        } else {
            return Optional.empty();
        }
    }

    private Map<Suit, List<Card>> setupSuitCardMap(Set<Card> allCards) {
        Map<Suit, List<Card>> cardsMappedBySuit = new EnumMap<>(Suit.class);

        for (Suit suit : Suit.values()) {
            cardsMappedBySuit.put(suit, new LinkedList<>());
        }

        allCards.forEach(card -> cardsMappedBySuit.get(card.getSuit()).add(card));
        return cardsMappedBySuit;
    }


    private Map<Value, List<Card>> setupValueCardMap(Set<Card> allCards) {
        Map<Value, List<Card>> cardsMappedByValue = new EnumMap<>(Value.class);

        for (Value value : Value.values()) {
            cardsMappedByValue.put(value, new LinkedList<>());
        }
        allCards.forEach(card -> cardsMappedByValue.get(card.getValue()).add(card));
        return cardsMappedByValue;
    }

    /**
     * This method returns the HandValue of an Omaha pre flop hand.
     * This HandValue is either a high card value or a pair value
     *
     * @param handCards The hand cards for which a pre flop hand value should be calculated
     * @return The hand value of the omaha starting hand
     */
    public HandValue calculateOmahaPreFlop(Set<Card> handCards) {
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(handCards);

        Value maxPair = null;
        Value highCard = Value.TWO;
        Value secondHighestCard = Value.TWO;
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
                secondHighestCard = highCard;
                highCard = cardValue;
            }
        }

        if (maxPair == null) {
            return HandValue.newHandValue(HandValueType.HIGHCARD, highCard, secondHighestCard);
        } else {
            return HandValue.newHandValue(HandValueType.PAIR, maxPair, null);
        }
    }

    public static PokerUtils newPokerUtils() {
        return pokerUtils;
    }

}
