package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;

public class MouseTickEvent

extends Event {
    private final Type type;

    public MouseTickEvent(Type type) {
        this.type = type;

    }

    public Type getType() {
        return this.type;

    }

    public enum Type {

        RIGHT, LEFT, MIDDLE, RIGHT_HOLD;

    }

}
