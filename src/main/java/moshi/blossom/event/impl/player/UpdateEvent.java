package moshi.blossom.event.impl.player;

import lombok.Getter;
import moshi.blossom.event.Event;
import net.minecraft.client.entity.EntityPlayerSP;

@Getter
public class UpdateEvent extends Event {
    private final EntityPlayerSP player;

    public UpdateEvent(EntityPlayerSP player) {
        this.player = player;
    }
}
