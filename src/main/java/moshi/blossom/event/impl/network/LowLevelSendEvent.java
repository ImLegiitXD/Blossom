package moshi.blossom.event.impl.network;

import lombok.Getter;
import moshi.blossom.event.Event;
import net.minecraft.network.Packet;

@Getter
public class LowLevelSendEvent extends Event {
    private final Packet<?> packet;

    public LowLevelSendEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
