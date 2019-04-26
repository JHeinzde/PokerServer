package org.bonn.pokerserver.poker.game.entities.player;

import org.bonn.pokerserver.poker.game.exceptions.InvalidBetSizeException;
import org.bonn.pokerserver.poker.game.exceptions.InvalidTopOfAmountException;
import org.mapstruct.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * This class represents a player on a table
 */
public class Player {

    private static final Logger LOG = LoggerFactory.getLogger(Player.class);

    private final String name;
    private final String id;
    private final MoneyStack moneyStack;

    public Player() {
        this.name = null;
        this.id = null;
        this.moneyStack = null;
    }

    private Player(String name, String id, MoneyStack moneyStack) {
        this.name = name;
        this.id = id;
        this.moneyStack = moneyStack;
    }

    public MoneyStack getMoneyStack() {
        return moneyStack;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * Makes the bet specified by the player with the given bet size
     *
     * @param bet The bet to make
     * @return If the bet was successful or not
     */
    public boolean makeBet(BigDecimal bet) {

        try {
            moneyStack.subtractBet(bet);
        } catch (InvalidBetSizeException e) {
            LOG.info("Invalid bet size detected", e);
            return false;
        }

        return true;
    }

    /**
     * Tops of the players stack with the amount given.
     *
     * @param topOfAmount The amount to top of the stack with
     * @return True if top of was successful, false if the top of must be retried with a different value
     */
    public boolean topOfStack(BigDecimal topOfAmount) {
        try {
            moneyStack.topOfStack(topOfAmount);
        } catch (InvalidTopOfAmountException e) {
            LOG.info("Invalid top of amount detected", e);
            return false;
        }

        return true;
    }


    public boolean reBuy(BigDecimal reBuyAmount) {
        try {
            moneyStack.reset(reBuyAmount);
        } catch (InvalidTopOfAmountException e) {
            LOG.info("Invalid top of amount detected", e);
            return false;
        }

        return true;
    }

    /**
     * Returns a new Player object
     *
     * @param name       The display name of the player
     * @param id         The id of the player
     * @param moneyStack The stack of the player
     * @return The new Player object
     */
    @ObjectFactory
    public static Player newPlayer(String name, String id, MoneyStack moneyStack) {
        return new Player(name, id, moneyStack);
    }

}
