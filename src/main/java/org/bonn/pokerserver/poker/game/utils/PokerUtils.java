package org.bonn.pokerserver.poker.game.utils;

import org.bonn.pokerserver.poker.common.comparators.HandValueComparator;
import org.bonn.pokerserver.poker.game.entities.cards.Card;
import org.bonn.pokerserver.poker.game.entities.cards.HandValue;
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
     * This method calculates a HandValue form the Set of 5 handCards given to the method. It calculates all all possible HandValues,
     * and returns the highest of them. So when this method is called, it returns the highest hand value possible with
     * the given five handCards.
     * <p>
     * Warning this method does not calculate pre flop. It only calculates after the first three handCards are dealt.
     * </p>
     *
     * @param allCards The set of handCards that should be used to calculate a HandValue
     * @return The highest HandValue possible with these 5 handCards
     */
    public HandValue calculateHandValue(Set<Card> allCards) {

        List<HandValue> sortedHandValues = new LinkedList<>();

        for (Map.Entry<HandValueType, Function<Set<Card>, Optional<HandValue>>> handValueTypeFunction : handValueFunctionMap.entrySet()) {
            handValueTypeFunction.getValue().apply(allCards).ifPresent(sortedHandValues::add);
        }

        sortedHandValues.sort(new HandValueComparator());


        return sortedHandValues.get(sortedHandValues.size() - 1);
    }

    /**
     * This method takes a set of five handCards and calculates the highestTwo handCards from this.
     * It does this by mapping the handCards by their respective values and sorting the entry set in a list.
     * @param allCards The five handCards to be used in calculation
     * @return The HIGHCARD HandValueType and the two highest handCards
     */
    private Optional<HandValue> highCard(Set<Card> allCards) {

        Map<Value, Card> cardsMappedByValue = new EnumMap<>(Value.class);

        allCards.forEach(card -> cardsMappedByValue.put(card.getValue(), card));

        List<Map.Entry<Value, Card>> sorted = cardsMappedByValue
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getValue().getNumericValue())).collect(Collectors.toList());

        Value highestCard = sorted.get(sorted.size() - 1).getKey();
        Value secondHighestCard = sorted.get(sorted.size() - 2).getKey();

        return Optional.of(HandValue.newHandValue(HandValueType.HIGHCARD, highestCard, secondHighestCard, allCards));
    }

    /**
     * This method calculates if the five card set given as input contains a pair.
     * @param allCards The handCards to be used in calculation
     * @return  An optional wrapping either the results of the calculation or null if no pair could be found
     */
    private Optional<HandValue> pair(Set<Card> allCards) {

        //Map all handCards in the set by their respective Value(ACE,KING ...)
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);
        Value maxPair = null;

        // Iterate the entry set and check the size of the map resulting from the mapping action tkaen
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
            return Optional.of(HandValue.newHandValue(HandValueType.PAIR, maxPair, null, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method calculates if the five card set given as input contains a two pair
     * @param allCards The five card set to be used in calculation
     * @return An Optional either containing the result of the method or null if now two pair could be found
     */
    private Optional<HandValue> twoPair(Set<Card> allCards) {
        // Map the handCards by their respective value(ACE, KING, QUEEN ...)
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);
        Value firstPair = null;
        Value secondPair = null;

        // Iterate over the mapped handCards and check if two different entries have 2 handCards in them
        // If this is the case a two pair was found and will be returned
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

        // The firstPair should always be the better of the two pairs
        if (firstPair != null && secondPair != null && firstPair.getNumericValue() < secondPair.getNumericValue()) {
            Value tmp = firstPair;
            firstPair = secondPair;
            secondPair = tmp;
        }


        if (firstPair != null && secondPair != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.TWO_PAIR, firstPair, secondPair, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method calculates if the five handCards given as input contain three of a kind.
     * @param allCards The five card set used in the calculation
     * @return An Optional either containing the result of the calculation or null
     */
    private Optional<HandValue> threeOfAKind(Set<Card> allCards) {
        // Map the input handCards by their respective values (ACE, KING, QUEEN ... )
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);
        Value cardValueOfTrips = null;

        // Iterate over the entry set and check if one of the list contains three handCards
        // If so three of a kind where found
        for (Map.Entry<Value, List<Card>> entry : cardsMappedByValue.entrySet()) {
            int amountOfCardsInValueEntry = entry.getValue().size();

            if (amountOfCardsInValueEntry == 3) {
                cardValueOfTrips = entry.getKey();
            }
        }

        if (cardValueOfTrips != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.THREE_OF_AKIND, cardValueOfTrips, null, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method calculates if the five card set given as input contains a straight
     * @param allCards The five card set to be used in the calculation
     * @return An Optional either containing the result of the calculation or null
     */
    private Optional<HandValue> straight(Set<Card> allCards) {
        // Create a list out of the set and sort it by their values (ACE, KING, QUEEN ...)
        List<Card> sortedCards = new ArrayList<>(allCards);

        sortedCards.sort(Comparator.comparingInt(cardOne -> cardOne.getValue().getNumericValue()));

        int runningTotal = 0;
        int firstValue = 0;

        // Calculate the sequential difference of the cards e.g. abs(list[n] - list[(n +1)])
        for (Card sortedCard : sortedCards) {
            if (firstValue == 0) {
                firstValue = sortedCard.getValue().getNumericValue();
                continue;
            }
            runningTotal += Math.abs(firstValue - sortedCard.getValue().getNumericValue());
            firstValue = sortedCard.getValue().getNumericValue();
        }

        // Calculate the difference of the first and the last card
        int difference = sortedCards
                .get(LAST_LIST_ELEMENT)
                .getValue()
                .getNumericValue() - sortedCards
                .get(FIRST_LIST_ELEMENT)
                .getValue()
                .getNumericValue();

        // If both of the above equal four its a straight
        if (runningTotal == 4 && difference == 4) {
            return Optional.of(HandValue.newHandValue(HandValueType.STRAIGHT,
                    sortedCards.get(LAST_LIST_ELEMENT).getValue(),
                    null, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method calculates if the five card set given as input contains a flush
     * @param allCards The five card set to be used in the calculation
     * @return An Optional wrapping either the result of the calculation or null
     */
    private Optional<HandValue> flush(Set<Card> allCards) {
        // Map the cards in the input set by their respective suite
        Map<Suit, List<Card>> cardsMappedBySuit = setupSuitCardMap(allCards);

        // Iterate over the entry set. If one of the lists in the entry set has 5 entries its a flush
        // If it s a flush the two highest card values are given as parameters for a HandValue
        for (Map.Entry<Suit, List<Card>> mapEntries : cardsMappedBySuit.entrySet()) {

            if (mapEntries.getValue().size() == 5) {
                mapEntries.getValue().sort(Comparator.comparingInt(card -> card.getValue().getNumericValue()));
                return Optional.of(HandValue.newHandValue(HandValueType.FLUSH,
                        mapEntries.getValue().get(LAST_LIST_ELEMENT).getValue(),
                        mapEntries.getValue().get(SECOND_LAST_ELEMENT).getValue(),
                        allCards));
            }

        }

        return Optional.empty();
    }

    /**
     * A method that calculates if the five card input set contains a full house
     * @param allCards The five card set used in the calculation
     * @return An Optional wrapping either the result of the calculation or null
     */
    private Optional<HandValue> fullHouse(Set<Card> allCards) {
        //Mapping the cards by their respective values
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);
        Value tripsValue = null;
        Value pairValue = null;

        //Iterating over the entry set. If one of the lists contained contains three and another two cards its a full house.
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
            return Optional.of(HandValue.newHandValue(HandValueType.FULL_HOUSE, tripsValue, pairValue, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method calculate if the given five card input set contains four of a kind.
     * @param allCards The five cards to be used in the calculation
     * @return An Optional wrapping either the value of the calculation or null
     */
    private Optional<HandValue> quads(Set<Card> allCards) {
        // Map the cards by their respective values
        Map<Value, List<Card>> cardsMappedByValue = setupValueCardMap(allCards);
        Value quadsValue = null;

        // Iterate through the mapped cards and if any of the lists contains 4 cards four of a kind are found
        for (Map.Entry<Value, List<Card>> mapEntry : cardsMappedByValue.entrySet()) {
            int amountOfCardsOfOneValue = mapEntry.getValue().size();

            if (amountOfCardsOfOneValue == 4) {
                quadsValue = mapEntry.getKey();
            }
        }

        if (quadsValue != null) {
            return Optional.of(HandValue.newHandValue(HandValueType.FOUR_OF_A_KIND, quadsValue, null, allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * A method that calculates if the five card input set contains a straight flush
     * @param allCards The five cards to be used in the calculation
     * @return An Optional wrapping either the result of the calculation or null
     */
    private Optional<HandValue> straightFlush(Set<Card> allCards) {
        Optional<HandValue> possibleStraight = straight(allCards);
        Optional<HandValue> possibleFlush = flush(allCards);


        // If flush & straight and the highest flush/straight card is != ACE its a straight flush
        if (possibleStraight.isPresent() && possibleFlush.isPresent() && possibleStraight.get().getFirstCardValue() != Value.ACE &&
                possibleFlush.get().getFirstCardValue() != Value.ACE) {

            return Optional.of(HandValue.newHandValue(HandValueType
                            .STRAIGHT_FLUSH,
                    possibleFlush
                            .get()
                            .getFirstCardValue(),
                    possibleStraight
                            .get()
                            .getFirstCardValue(),
                    allCards));
        } else {
            return Optional.empty();
        }
    }


    /**
     * A method that calculates if the five card input set contains a royal flush
     * @param allCards The five cards to be used in calculation
     * @return An Optional either wrapping the result of the calculation or null
     */
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
                            .getFirstCardValue(),
                    allCards));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method takes a five card input set and maps them by their suit
     * @param allCards The five card input set to be used for mapping
     * @return A Map of suit to card list
     */
    private Map<Suit, List<Card>> setupSuitCardMap(Set<Card> allCards) {
        Map<Suit, List<Card>> cardsMappedBySuit = new EnumMap<>(Suit.class);

        for (Suit suit : Suit.values()) {
            cardsMappedBySuit.put(suit, new LinkedList<>());
        }

        allCards.forEach(card -> cardsMappedBySuit.get(card.getSuit()).add(card));
        return cardsMappedBySuit;
    }

    /**
     * This method takes a five card input set and maps them by their value
     * @param allCards The five card input set to be used for mapping
     * @return A Map of value to card list
     */
    public Map<Value, List<Card>> setupValueCardMap(Set<Card> allCards) {
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
     * @param handCards The hand handCards for which a pre flop hand value should be calculated
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
            return HandValue.newHandValue(HandValueType.HIGHCARD, highCard, secondHighestCard, handCards);
        } else {
            return HandValue.newHandValue(HandValueType.PAIR, maxPair, null, null);
        }
    }

    /**
     * Returns the reference for PokerUtils
     * @return The reference to the PokerUtils singleton
     */
    public static PokerUtils getPokerUtils() {
        return pokerUtils;
    }

}
