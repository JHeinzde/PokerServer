package org.bonn.pokerserver.poker.websocket;

import io.micronaut.http.annotation.PathVariable;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.bonn.pokerserver.poker.common.ValidationUtils;
import org.bonn.pokerserver.poker.common.interfaces.TableList;
import org.bonn.pokerserver.poker.game.PotLimitOmahaTable;
import org.bonn.pokerserver.poker.game.entities.player.Player;
import org.bonn.pokerserver.poker.websocket.events.Event;
import org.bonn.pokerserver.poker.websocket.events.EventFactory;
import org.bonn.pokerserver.poker.websocket.uihandler.UserServiceClient;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * This class handles all connection to the clients
 */
@ServerWebSocket("/ws/table/{table-id}/{player-id}")
public class WebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);

    private static final ValidationUtils validator = ValidationUtils.getValidationUtils();
    private static final UserServiceClient userClient = UserServiceClient.getUserServiceClient();
    private static final EventFactory eventFactory = EventFactory.getEventFactory();

    private final TableList tableList;
    private WebSocketBroadcaster broadcaster;

    @Inject
    public WebSocketHandler(WebSocketBroadcaster webSocketBroadcaster, TableList tableList) {
        this.broadcaster = webSocketBroadcaster;
        this.tableList = tableList;
    }

    @OnOpen
    public Publisher<Event> onOpen(@PathVariable("table-id") String tableId,
                                   @PathVariable("player-id") String playerId,
                                   WebSocketSession session) {

        if (!(validator.validatePlayerId(playerId) && validator.validateTableId(tableId))) {
            LOG.info("Invalid id detected {} {}", tableId, playerId);
            return broadcaster.broadcast(eventFactory.newInvalidInputEvent("The player or table ID is invalid"));
        }

        if (tableList.getPotLimitOmahaTableById(tableId) == null){
            LOG.info("Invalid table access detected. Player tried to access a non existing table: {}", tableId);
            return broadcaster.broadcast(eventFactory.newInvalidInputEvent("The table Id requested does not exist"));
        }

        Player player = userClient.getPlayerByPlayerId(playerId);
        if (player == null) {
            LOG.warn("Invalid player detected: {}", playerId);
            return broadcaster.broadcast(eventFactory.newInvalidInputEvent("The playerId in the request does not exist"));
        }

        LOG.info("Player with the id {} joined table with the id {}", playerId, tableId);
        return broadcaster.broadcast(eventFactory.newPlayerJoinEvent(player.getName(), player.getId()));
    }

    @OnMessage
    public Publisher<Event> onMessage(@PathVariable("tabel-id") String tableId,
                                      @PathVariable("player-id") String playerId,
                                      Event event,
                                      WebSocketSession session) {
        PotLimitOmahaTable currentTable = tableList.getPotLimitOmahaTableById(tableId);

        if (currentTable == null) {
            LOG.info("Player {} accessed and illegal table {}", playerId, tableId);
            return broadcaster.broadcast(eventFactory.newInvalidInputEvent("Invalid table accessed"));
        }

        Event answerEvent = currentTable.processEvent(event);

        return broadcaster.broadcast(answerEvent);
    }

}
