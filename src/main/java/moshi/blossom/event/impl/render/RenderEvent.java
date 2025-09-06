package moshi.blossom.event.impl.render;

import lombok.Getter;
import moshi.blossom.event.Event;

@Getter
public class RenderEvent

extends Event {
    private final float partialTicks;

    public RenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
