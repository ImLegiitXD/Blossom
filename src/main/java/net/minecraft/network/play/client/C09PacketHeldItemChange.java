package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C09PacketHeldItemChange implements Packet<INetHandlerPlayServer> {

    private int slotId;

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_HELD_ITEM_CHANGE;

    }

public C09PacketHeldItemChange() {}

    public C09PacketHeldItemChange(int slotId) {
        this.slotId = slotId;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.slotId = buf.readShort();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeShort(this.slotId);

    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processHeldItemChange(this);

    }

    public int getSlotId() {
        return this.slotId;

    }

}
