package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C10PacketCreativeInventoryAction implements Packet<INetHandlerPlayServer> {

    private int slotId;

    private ItemStack stack;

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_CREATIVE_INVENTORY_ACTION;

    }

public C10PacketCreativeInventoryAction() {}

    public C10PacketCreativeInventoryAction(int slotIdIn, ItemStack stackIn) {
        this.slotId = slotIdIn;

        this.stack = stackIn != null ? stackIn.copy() : null;

    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processCreativeInventoryAction(this);

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.slotId = buf.readShort();

        this.stack = buf.readItemStackFromBuffer();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeShort(this.slotId);

        buf.writeItemStackToBuffer(this.stack);

    }

    public int getSlotId() {
        return this.slotId;

    }

    public ItemStack getStack() {
        return this.stack;

    }

}
