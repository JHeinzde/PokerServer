package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.MoneyStack;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.game.entities.player.TopOff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PotLimitOmahaTableTest {

    private static final String PLAYER_NAME = "TonyTester";
    private static final String PLAYER_ID = "9d0807ba-d3d8-423e-8fdf-e5ab58cf4154";

    private PotLimitOmahaTable potLimitOmahaTableUnderTest = PotLimitOmahaTable.newPotLimitOmahaTable(StakeLevel.TWO);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayerAdding() {
        BuyIn buyIn = BuyIn.newBuyIn(200);
        Player player = Player.newPlayer(PLAYER_NAME,
                PLAYER_ID,
                MoneyStack.newMoneyStack(StakeLevel.TWO.getNumericLevel(), null));

        potLimitOmahaTableUnderTest.addPlayer(player, buyIn);

        assertNotNull(potLimitOmahaTableUnderTest.getPlayerById(PLAYER_ID));

        Integer actualStackSize = potLimitOmahaTableUnderTest
                .getPlayerById(PLAYER_ID)
                .getMoneyStack()
                .getStackSize();

        assertEquals(200, actualStackSize.longValue());
    }

    @Test
    public void testTopOffPlayer() {
        Player player = Player.newPlayer(PLAYER_NAME, PLAYER_ID,
                MoneyStack.newMoneyStack(StakeLevel.TWO.getNumericLevel(), null));

        TopOff topOff = new TopOff(50);
    }
}