package org.bonn.pokerserver.poker.game.entities.player;

import org.bonn.pokerserver.poker.game.exceptions.InvalidBetSizeException;
import org.bonn.pokerserver.poker.game.exceptions.InvalidTopOfAmountException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyStackTest {

    private final Integer stackSize = 200;

    @Test
    public void testSubtractionOfStack() throws Exception {
        Integer bet = 10;
        Integer expectedResult = 190;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);

        assertEquals(expectedResult, moneyStack.getStackSize());
    }

    @Test(expected = InvalidBetSizeException.class)
    public void testSubtractionOfStackThrowsException() throws Exception {
        Integer bet = 230;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);
    }

    @Test
    public void testTopOfStack() throws Exception {
        Integer topOfAmount = 150;

        Integer expectedAmount = 350;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(400, stackSize);
        moneyStack.topOfStack(topOfAmount);

        assertEquals(expectedAmount, moneyStack.getStackSize());
    }

    @Test(expected = InvalidTopOfAmountException.class)
    public void testTopOfStackThrowsException() throws Exception {
        Integer topOfAmount = 300;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(400, stackSize);
        moneyStack.topOfStack(topOfAmount);
    }

    @Test(expected = InvalidBetSizeException.class)
    public void testNegativeBetSizeNotAllowed() throws Exception {
        Integer bet = -300;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.subtractBet(bet);
    }

    @Test(expected = InvalidTopOfAmountException.class)
    public void testNegativeTopOfAmountNotAllowed() throws Exception {
        Integer negativeTopOfAmount = -300;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.topOfStack(negativeTopOfAmount);
    }

    @Test
    public void testResetCorrect() throws Exception {
        Integer reBuyAmount = 150;

        MoneyStack moneyStack = MoneyStack.newMoneyStack(stackSize, stackSize);
        moneyStack.reset(reBuyAmount);

        Integer expectedStackSize = 150;
        assertEquals(expectedStackSize, moneyStack.getStackSize());
    }
}