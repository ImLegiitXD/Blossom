package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;
import net.minecraft.client.entity.EntityPlayerSP;

public class UpdateEvent

extends Event {
    private final EntityPlayerSP player;

    public UpdateEvent(EntityPlayerSP player) {
        this.player = player;

    }

    public EntityPlayerSP getPlayer() {
        return this.player;

    }

}
