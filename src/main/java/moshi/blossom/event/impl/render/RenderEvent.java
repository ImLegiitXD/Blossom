package moshi.blossom.event.impl.render;

import moshi.blossom.event.Event;

public class RenderEvent

extends Event {
    private final float partialTicks;

    public RenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;

    }

    public float getPartialTicks() {
        return this.partialTicks;

    }

}
