package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet<INetHandlerPlayClient> {

    private int entityId;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private NBTTagCompound tagCompound;

    public Packets pType() {
        return Packets.S_UPDATE_ENTITY_NBT;

    }

public S49PacketUpdateEntityNBT() {}

    public S49PacketUpdateEntityNBT(int entityIdIn, NBTTagCompound tagCompoundIn) {
        this.entityId = entityIdIn;

        this.tagCompound = tagCompoundIn;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.entityId = buf.readVarIntFromBuffer();

        this.tagCompound = buf.readNBTTagCompoundFromBuffer();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.entityId);

        buf.writeNBTTagCompoundToBuffer(this.tagCompound);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleEntityNBT(this);

    }

    public NBTTagCompound getTagCompound() {
        return this.tagCompound;

    }

    public Entity getEntity(World worldIn) {
        return worldIn.getEntityByID(this.entityId);

    }

}
