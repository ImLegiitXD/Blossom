package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;

public class ChatEvent

extends Event {
    private final String message;

    public ChatEvent(String message) {
        this.message = message;

    }

    public String getMessage() {
        return this.message;

    }

}
