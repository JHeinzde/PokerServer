package org.bonn.pokerserver.poker.game.utils;

import org.bonn.pokerserver.poker.game.entities.Card;
import org.bonn.pokerserver.poker.game.entities.HandValue;
import org.bonn.pokerserver.poker.game.entities.enums.HandValueType;
import org.bonn.pokerserver.poker.game.entities.enums.Value;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * This is a singleton implementation of the PokerUtils class
 * It calculates different thing for a poker game.
 */
public class PokerUtils {

    private PokerUtils() {
        handValueFunctionMap.put(HandValueType.HIGHCARD, this::highCard);
        handValueFunctionMap.put(HandValueType.PAIR, this::pair);
    }


    private static PokerUtils pokerUtils = new PokerUtils();

    private Map<HandValueType, Function<Set<Card>, HandValue>> handValueFunctionMap = new EnumMap<>(HandValueType.class);

    public  HandValue calculateHandValue(Set<Card> allCards) {
        return null;
    }

    private HandValue highCard(Set<Card> allCards) {

        Map<Value, Card> cardsMappedByValue = new EnumMap<>(Value.class);

        allCards.forEach( card -> cardsMappedByValue.put(card.getValue(), card));


        return null;
    }

    private HandValue pair(Set<Card> cards) {
        return null;
    }


    public static PokerUtils newPokerUtils() {
        return pokerUtils;
    }

}
