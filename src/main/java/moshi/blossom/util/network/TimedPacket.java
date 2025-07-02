package moshi.blossom.util.network;

import moshi.blossom.util.Clock;
import net.minecraft.network.Packet;

/**
 * Wrapper class for network packets that tracks when they were sent
 */
public class TimedPacket {
    private Packet<?> packet;
    private final Clock clock;

    public TimedPacket(Packet<?> packet) {
        this.packet = packet;
        this.clock = new Clock();
        this.clock.reset();
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Clock getClock() {
        return clock;
    }
}