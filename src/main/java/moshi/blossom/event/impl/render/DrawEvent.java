package moshi.blossom.event.impl.render;

import moshi.blossom.event.Event;

public class DrawEvent

extends Event {
    private final float partialTicks;

    private final float width;

    public DrawEvent(float partialTicks, float width, float height) {
        this.partialTicks = partialTicks;

        this.width = width;

        this.height = height;

    }

    private final float height; public float deltaNs;

    public float getPartialTicks() {
        return this.partialTicks;

    }

    public float getWidth() {
        return this.width;

    }

    public float getHeight() {
        return this.height;

    }

}
