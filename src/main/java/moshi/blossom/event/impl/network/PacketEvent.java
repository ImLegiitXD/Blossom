package moshi.blossom.event.impl.network;

import java.util.Arrays;
import moshi.blossom.event.Event;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.Packet;

public class PacketEvent

extends Event
{
    private final Dir dir;

    private Packet<?> packet;

    public PacketEvent(Packet<?> packet, Dir dir) {
        this.packet = packet;

        this.dir = dir;

    }

    public Packet<?> getPacket() {
        return this.packet;

    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;

    }

    public Dir getDir() {
        return this.dir;

    }

    public boolean is(Packets type) {
        return this.packet.pType().equals(type);

    }

    public boolean is(Packets... types) {
        return Arrays.<Packets>asList(types).contains(this.packet.pType());

    }

    public enum Dir {

        SEND, GET;

    }

}
