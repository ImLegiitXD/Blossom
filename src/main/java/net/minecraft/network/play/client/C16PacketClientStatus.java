package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C16PacketClientStatus implements Packet<INetHandlerPlayServer> {

    private EnumState status;

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_CLIENT_STATUS;

    }

public C16PacketClientStatus() {}

    public C16PacketClientStatus(EnumState statusIn) {
        this.status = statusIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.status = (EnumState)buf.readEnumValue(EnumState.class);

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeEnumValue(this.status);

    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processClientStatus(this);

    }

    public EnumState getStatus() {
        return this.status;

    }

    public enum EnumState

    {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        OPEN_INVENTORY_ACHIEVEMENT;

    }

}
