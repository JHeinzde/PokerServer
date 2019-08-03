package org.bonn.pokerserver.poker.game.entities.player;

import java.util.Optional;

/**
 * This class represents an action that was taken by a player
 */
public class PlayerAction {

    private final ActionType type;
    private final Optional<Integer> amount;

    /**
     * This constructor takes the type of the action and an optional amount of the action(in case of a bet call raise) and wraps
     * this amount in an optional type
     * @param type The type of the action. BET, CALL, RAISE AND FOLD are possible values here
     * @param amount The amount that should be used in possible calculations
     */
    public PlayerAction(ActionType type, Integer amount) {
        this.type = type;
        if (amount != null){
            this.amount = Optional.of(amount);
        } else {
            this.amount = Optional.empty();
        }
    }


    public ActionType getType() {
        return type;
    }

    public Optional<Integer> getAmount() {
        return amount;
    }
}
