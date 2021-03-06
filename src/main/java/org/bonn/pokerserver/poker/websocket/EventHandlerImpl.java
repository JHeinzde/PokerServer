package org.bonn.pokerserver.poker.websocket;

import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.websocket.events.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the EventHandler interface
 */
public class EventHandlerImpl implements EventHandler {

    private final Map<EventType, Action> eventActionMap = new HashMap<>();
    private final EventFactory eventFactory;
    private final TableList tableList;

    @Inject
    public EventHandlerImpl(TableList tableList, EventFactory eventFactory) {
        eventActionMap.put(EventType.PLAYER_JOIN, this::processJoinEvent);
        eventActionMap.put(EventType.PLAYER_LEAVE, this::processLeaveEvent);
        eventActionMap.put(EventType.BET, this::processBetEvent);

        this.eventFactory = eventFactory;
        this.tableList = tableList;
    }

    @Override
    public Event handleEvent(Event event, String tableId, String playerId) {
        Action action = eventActionMap.get(event.getEventType());
        return action.processEvent(event, tableId, playerId);
    }

    private Event processJoinEvent(Event event, String tableId, String playerId) {
        if (event.getEventType() != EventType.PLAYER_JOIN) {
            return eventFactory.newInvalidInputEvent("Invalid event passed");
        }

        // Make checked cast to join event
        PlayerJoinEvent joinEvent = (PlayerJoinEvent) event;
        PotLimitOmahaTable table = tableList.getPotLimitOmahaTableById(tableId);
        var player = table.getPlayerById(joinEvent.getPlayer().getId());

        if (player == null){
            return eventFactory.newInvalidInputEvent("Invalid event passed");
        }

        return eventFactory.newPlayerJoinEvent(joinEvent.getPlayer());
    }

    private Event processLeaveEvent(Event event, String tableId, String playerId) {
        if (event.getEventType() != EventType.PLAYER_LEAVE) {
            return eventFactory.newInvalidInputEvent("Invalid event passed");
        }

        PlayerLeaveEvent leaveEvent = (PlayerLeaveEvent) event;

        PotLimitOmahaTable table = tableList.getPotLimitOmahaTableById(tableId);
        if (table.removePlayer(leaveEvent.getPlayer())) {
            return eventFactory.newPlayerLeaveEvent(leaveEvent.getPlayer());
        }

        return eventFactory.newInvalidInputEvent("Could not remove player from table");
    }

    private Event processBetEvent(Event event, String tableId, String playerId) {
        if (event.getEventType() != EventType.BET) {
            return eventFactory.newInvalidInputEvent("Invalid event passed");
        }

        BetEvent betEvent = (BetEvent) event;

        PotLimitOmahaTable table = tableList.getPotLimitOmahaTableById(tableId);
        Player player = table.getPlayerById(playerId);
        if (player == null){
            return eventFactory.newInvalidInputEvent("Invalid event passed");
        }


        return null;
    }

}
