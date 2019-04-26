package org.bonn.pokerserver.poker.game.entities.player;

import org.bonn.pokerserver.poker.game.exceptions.InvalidBetSizeException;
import org.bonn.pokerserver.poker.game.exceptions.InvalidTopOfAmountException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MoneyStackTest {

    private final BigDecimal stackSize = BigDecimal.valueOf(2.0);

    @Test
    public void testSubtractionOfStack() throws Exception {
        BigDecimal bet = BigDecimal.valueOf(0.1);

        BigDecimal expectedResult = BigDecimal.valueOf(1.9);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);

        assertEquals(expectedResult, moneyStack.getStackSize());
    }

    @Test(expected = InvalidBetSizeException.class)
    public void testSubtractionOfStackThrowsException() throws Exception {
        BigDecimal bet = BigDecimal.valueOf(2.3);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);
    }

    @Test
    public void testTopOfStack() throws Exception {
        BigDecimal topOfAmount = BigDecimal.valueOf(1.5);

        BigDecimal expectedAmount = BigDecimal.valueOf(3.5);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(BigDecimal.valueOf(4.0), stackSize);
        moneyStack.topOfStack(topOfAmount);

        assertEquals(expectedAmount, moneyStack.getStackSize());
    }

    @Test(expected = InvalidTopOfAmountException.class)
    public void testTopOfStackThrowsException() throws Exception {
        BigDecimal topOfAmount = BigDecimal.valueOf(3);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(BigDecimal.valueOf(4.0), stackSize);
        moneyStack.topOfStack(topOfAmount);
    }

    @Test(expected = InvalidBetSizeException.class)
    public void testNegativeBetSizeNotAllowed() throws Exception {
        BigDecimal bet = BigDecimal.valueOf(-3.0);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);
    }

    @Test(expected = InvalidTopOfAmountException.class)
    public void testNegativeTopOfAmountNotAllowed() throws Exception {
        BigDecimal negativeTopOfAmount = BigDecimal.valueOf(-3.0);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.topOfStack(negativeTopOfAmount);
    }

    @Test
    public void testResetCorrect() throws Exception {
        BigDecimal reBuyAmount = BigDecimal.valueOf(1.5);

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.reset(reBuyAmount);

        BigDecimal expectedStackSize = BigDecimal.valueOf(1.5);
        assertEquals(expectedStackSize, moneyStack.getStackSize());
    }
}