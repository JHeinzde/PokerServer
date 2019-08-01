package org.bonn.pokerserver.poker.websocket;

import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.StakeLevel;
import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.websocket.events.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EventHandlerImplTest {

    private static final String TEST_TABLE_ID = "Test-Id-1234";

    private PotLimitOmahaTable potLimitOmahaTable;
    private EventHandlerImpl eventHandler;
    @Mock
    private TableList tableList;

    @Before
    public void setUp() throws Exception {
        potLimitOmahaTable = PotLimitOmahaTable.newPotLimitOmahaTable(StakeLevel.TWO);
        potLimitOmahaTable.addPlayer(playerOne(), buyInPlayerOne());

        Mockito.when(tableList.getPotLimitOmahaTableById(TEST_TABLE_ID)).thenReturn(potLimitOmahaTable);
        eventHandler = new EventHandlerImpl(tableList, EventFactory.getEventFactory());
    }

    private Player playerOne() {
        return Player.newPlayer("TonyTester", "1234", null);
    }

    private Player playerTwo() {
        return Player.newPlayer("AntonAdmin", "12345", null);
    }

    private BuyIn buyInPlayerOne() {
        return BuyIn.newBuyIn(200);
    }
    private BuyIn buyInPlayerTwo() {
        return BuyIn.newBuyIn(100);
    }

    @Test
    public void playerJoinEvent() {
        potLimitOmahaTable.addPlayer(playerTwo(), buyInPlayerTwo());

        Event testEvent = EventFactory.getEventFactory()
                .newPlayerJoinEvent(playerTwo());
        Event joinEventProcessed = eventHandler.handleEvent(testEvent, TEST_TABLE_ID);
        PlayerJoinEvent joinEvent = (PlayerJoinEvent) joinEventProcessed;

        assertEquals(joinEvent.getPlayer(), playerTwo());
    }

    @Test
    public void playerLeaveEvent() {
        Event leaveEvent = EventFactory.getEventFactory()
                .newPlayerLeaveEvent(playerOne());

        Event leaveEventProcessed = eventHandler.handleEvent(leaveEvent, TEST_TABLE_ID);
        PlayerLeaveEvent leaveEventCasted = (PlayerLeaveEvent) leaveEventProcessed;

        assertEquals(leaveEventCasted.getPlayer(), playerOne());
        assertEquals(0, (int) potLimitOmahaTable.size());
    }

}