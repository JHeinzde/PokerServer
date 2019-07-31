package org.bonn.pokerserver.poker.websocket;

import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.StakeLevel;
import org.bonn.pokerserver.poker.game.entities.player.BuyIn;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.websocket.events.Event;
import org.bonn.pokerserver.poker.websocket.events.EventFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
        potLimitOmahaTable.addPlayer(testPlayer(), testBuyIn());

        Mockito.when(tableList.getPotLimitOmahaTableById(TEST_TABLE_ID)).thenReturn(potLimitOmahaTable);
        eventHandler = new EventHandlerImpl(tableList, EventFactory.getEventFactory());
    }

    private Player testPlayer() {
        return Player.newPlayer("TonyTester", "1234", null);
    }

    private BuyIn testBuyIn() {
        return BuyIn.newBuyIn(200);
    }

    @Test
    public void playerJoinEvent() {
        Event testEvent = EventFactory.getEventFactory()
                .newPlayerJoinEvent(testPlayer(), testBuyIn());

        eventHandler.handleEvent(testEvent, TEST_TABLE_ID);

        assert
    }

}