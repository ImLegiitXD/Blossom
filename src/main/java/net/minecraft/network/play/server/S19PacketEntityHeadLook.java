package net.minecraft.network.play.server;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook implements Packet<INetHandlerPlayClient> {

    private int entityId;

    public PacketDir pDir() {
        return PacketDir.SERVER;

    }

    private byte yaw;

    public Packets pType() {
        return Packets.S_ENTITY_HEAD_LOOK;

    }

public S19PacketEntityHeadLook() {}

    public S19PacketEntityHeadLook(Entity entityIn, byte p_i45214_2_) {
        this.entityId = entityIn.getEntityId();

        this.yaw = p_i45214_2_;

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.entityId = buf.readVarIntFromBuffer();

        this.yaw = buf.readByte();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.entityId);

        buf.writeByte(this.yaw);

    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleEntityHeadLook(this);

    }

    public Entity getEntity(World worldIn) {
        return worldIn.getEntityByID(this.entityId);

    }

    public byte getYaw() {
        return this.yaw;

    }

}
