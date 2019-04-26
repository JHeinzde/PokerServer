package org.bonn.pokerserver.poker.game.entities.player;

import org.bonn.pokerserver.poker.game.exceptions.InvalidBetSizeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    private static final String PLAYER_ID = "9d0807ba-d3d8-423e-8fdf-e5ab58cf4154";
    private static final String NAME = "TonyTester";
    private static final BigDecimal INVALID_AMOUNT = BigDecimal.valueOf(-2);

    @Mock
    private MoneyStack mockMoneyStack;

    @Before
    public void setUp() throws InvalidBetSizeException {
        Mockito.doThrow(InvalidBetSizeException.class).when(mockMoneyStack).subtractBet(INVALID_AMOUNT);
    }

    @Test
    public void testBetForExceptionCaught() {
        Player testPlayer = Player.newPlayer(NAME, PLAYER_ID, mockMoneyStack);

        assertFalse(testPlayer.makeBet(INVALID_AMOUNT));
    }

    @Test
    public void testTopOfForExceptionCaught() {
        Player testPlayer = Player.newPlayer(NAME, PLAYER_ID, mockMoneyStack);

        assertFalse(testPlayer.topOfStack(INVALID_AMOUNT));
    }

    @Test
    public void testReBuyForExceptionCaught() {
        Player testPlayer = Player.newPlayer(NAME, PLAYER_ID, mockMoneyStack);

        assertFalse(testPlayer.reBuy(INVALID_AMOUNT));
    }

}