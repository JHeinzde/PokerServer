package org.bonn.pokerserver.poker.websocket.events;

/**
 * This event represents a bet made by a player
 */
public class BetEvent implements Event {

    private final Integer betAmount;

    private BetEvent(Integer betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * Returns the bet amount
     * @return The amount to bet
     */
    public Integer getBetAmount() {
        return betAmount;
    }

    @Override
    public EventType getEventType() {
        return EventType.BET;
    }

    /**
     * Creates a new bet event
     * @param betAmount The amount of the bet
     * @return A new bet event
     */
    public static Event newBetEvent(Integer betAmount){
        return new BetEvent(betAmount);
    }


}
