package org.bonn.pokerserver.poker.websocket.events;

public class InvalidInputEvent implements Event {
    private static final EventType EVENT_TYPE = EventType.INVALID;

    private final String message;

    private InvalidInputEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public EventType getEventType() {
        return EVENT_TYPE;
    }

    public static InvalidInputEvent newInvalidInputEvent(String message){
        return new InvalidInputEvent(message);
    }
}
