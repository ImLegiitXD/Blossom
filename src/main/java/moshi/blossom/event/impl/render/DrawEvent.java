package moshi.blossom.event.impl.render;

import lombok.Getter;
import moshi.blossom.event.Event;

@Getter
public class DrawEvent extends Event {
    private final float partialTicks;
    private final float width;

    public DrawEvent(float partialTicks, float width, float height) {
        this.partialTicks = partialTicks;
        this.width = width;
        this.height = height;
    }

    @Getter

    // why was it like this lol
    private final float height; public float deltaNs;
}
