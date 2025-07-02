package net.minecraft.network;

import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;

import java.io.IOException;

public interface Packet<T extends INetHandler> {
           default PacketDir pDir() {
             return null;
           } default Packets pType() {
             return Packets.UNKNOWN;
           }
    void readPacketData(PacketBuffer buf) throws IOException;

    void writePacketData(PacketBuffer buf) throws IOException;

    void processPacket(T handler);
}
