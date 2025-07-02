package moshi.blossom.event.impl.network;

import moshi.blossom.event.Event;
import net.minecraft.network.Packet;

public class LowLevelSendEvent

extends Event {
    private final Packet<?> packet;

    public LowLevelSendEvent(Packet<?> packet) {
        this.packet = packet;

    }

    public Packet<?> getPacket() {
        return this.packet;

    }

}
