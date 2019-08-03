package org.bonn.pokerserver.poker.game;

import org.bonn.pokerserver.poker.common.exceptions.InvalidAccessException;
import org.bonn.pokerserver.poker.game.entities.enums.Stage;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoundTest {

    private Round roundUnderTest;

    @Before
    public void setUp() throws Exception {
        List<Player> playerList = new ArrayList<>();
        playerList.add(playerOne());
        playerList.add(playerTwo());

        roundUnderTest = new Round(playerList);
    }


    @Test(expected = InvalidAccessException.class)
    public void testAdvanceStage() throws InvalidAccessException {
        assertEquals(Stage.PRE_FLOP, roundUnderTest.getStage());

        roundUnderTest.advanceStage();
        assertEquals(Stage.FLOP, roundUnderTest.getStage());

        roundUnderTest.advanceStage();
        assertEquals(Stage.TURN, roundUnderTest.getStage());

        roundUnderTest.advanceStage();
        assertEquals(Stage.RIVER, roundUnderTest.getStage());

        roundUnderTest.advanceStage();
    }

    private Player playerOne() {
        return Player.newPlayer("TonyTester", "123", null);
    }

    private Player playerTwo() {
        return Player.newPlayer("AntonAdmin", "1234", null);
    }
}