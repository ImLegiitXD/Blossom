package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0APacketAnimation implements Packet<INetHandlerPlayServer> {

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_ANIMATION;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
    }

    public void writePacketData(PacketBuffer buf) throws IOException {
    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.handleAnimation(this);

    }

}
