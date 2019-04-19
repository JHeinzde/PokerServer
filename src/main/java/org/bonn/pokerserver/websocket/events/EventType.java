package org.bonn.pokerserver.websocket.events;

public enum EventType {
    PLAYER_JOIN,
    PLAYER_LEAVE,
    NEW_ROUND,
    BET,
    RAISE,
    ALLIN,
    FOLD,
    CHECK,
    SHOWDOWN,
    SITOUT,
    COMEBACK,
    INVALID
}
