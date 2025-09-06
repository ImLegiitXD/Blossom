package moshi.blossom.event.impl.player;

import lombok.Getter;
import moshi.blossom.event.Event;

@Getter
public class MouseTickEvent extends Event {
    private final Type type;

    public MouseTickEvent(Type type) {
        this.type = type;
    }

    public enum Type {
        RIGHT, LEFT, MIDDLE, RIGHT_HOLD;
    }
}
