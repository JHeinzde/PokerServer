package org.bonn.pokerserver.poker.game.entities.player;

import org.bonn.pokerserver.poker.game.exceptions.InvalidBetSizeException;
import org.bonn.pokerserver.poker.game.exceptions.InvalidTopOfAmountException;

import java.math.BigDecimal;


/**
 * A class representing a players stack.
 */
public class MoneyStack {

    private static final Integer ZERO = 0;

    /**
     * The maximum stack size that can be held after a top off (Winnings from pots are excluded by this)
     */
    private final Integer maxStackSize;

    /**
     * The current stack size
     */
    private Integer stackSize;


    private MoneyStack(Integer maxStackSize, Integer stackSize) {
        this.maxStackSize = maxStackSize;
        this.stackSize = stackSize;
    }

    /**
     * Subtracts the given bet size from the current stack
     *
     * @param bet The bet to subtract from the current stack
     * @throws InvalidBetSizeException If the resulting stack size is negative this exception is thrown
     */
    public void subtractBet(Integer bet) throws InvalidBetSizeException {
        Integer newStackSize = stackSize - bet;

        if (bet < ZERO) {
            throw new InvalidBetSizeException("The argument given was zero. This is not allowed");
        }

        if (newStackSize < 0) {
            throw new InvalidBetSizeException("Bet size is to big for the current stack");
        }

        this.stackSize = newStackSize;
    }

    /**
     * This method adds the top of amount to the players stack. If the new stack size is bigger than the
     * maxStackSize an InvalidTopOfAmountException is thrown to the caller
     *
     * @param topOfAmount The amount to add to the stack
     * @throws InvalidTopOfAmountException When the new stack size is bigger than maxStackSize
     */
    public void topOfStack(Integer topOfAmount) throws InvalidTopOfAmountException {
        Integer newStackSize = this.stackSize + topOfAmount;

        if (topOfAmount < 0) {
            throw new InvalidTopOfAmountException("The argument given was zero. This is not allowed");
        }

        if (newStackSize > maxStackSize) {
            throw new InvalidTopOfAmountException("The top of amount given is to big");
        }


        this.stackSize = newStackSize;
    }

    /**
     * Re-buys the player with the specified amount
     * @param reBuyAmount The amount to re-buy the player
     * @throws InvalidTopOfAmountException If the amount for the re-buy was invalid
     */
    public void reset(Integer reBuyAmount) throws InvalidTopOfAmountException {
        // Reset the StackSize to zero
        this.stackSize = ZERO;
        topOfStack(reBuyAmount);
    }

    /**
     * Returns the maximum stack size configured for this class
     *
     * @return The maximum stack size possible
     */
    public Integer getMaxStackSize() {
        return maxStackSize;
    }

    /**
     * This method returns a copy of the current stack size
     *
     * @return A copy of the current stack size held in this class
     */
    public Integer getStackSize() {
        //TODO: Observe if this is accurate enough. Rounding mistakes in a poker software can be costly.
        return this.stackSize;
    }

    /**
     * Returns a new MoneyStack object
     *
     * @param maxStackSize The maxStackSize to be set in the returned object
     * @param stackSize    The inital stack size to be set in the returned object
     * @return The newly created MoneyStack object
     */
    public static MoneyStack newMoneyStack(Integer maxStackSize, Integer stackSize) {
        return new MoneyStack(maxStackSize, stackSize);
    }
}
