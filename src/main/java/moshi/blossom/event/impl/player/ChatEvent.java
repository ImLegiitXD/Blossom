package moshi.blossom.event.impl.player;

import lombok.Getter;
import moshi.blossom.event.Event;

@Getter
public class ChatEvent extends Event {
    private final String message;

    public ChatEvent(String message) {
        this.message = message;
    }
}
